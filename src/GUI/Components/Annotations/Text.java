package GUI.Components.Annotations;

import GUI.Components.PhotoFrame;
import GUI.Components.Tools.ToolSettings;
import fr.lri.swingstates.canvas.CRectangle;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class Text extends CRectangle implements Annotation<CRectangle> {

    private final static long DELAY_BETWEEN_CARET_BLINKS = 650; // ms

    private final PhotoFrame photoFrame;

    private Color color;
    private String fontFamily;
    private int fontSize;

    private boolean currentlyEditing;
    private StringBuilder stringBuilder;
    private LinkedList<String> stringSplitPerLine;
    private int stringLength;

    private int lineHeight;
    private boolean lineHeightMustBeUpdated;
    private boolean dimensionsMustBeUpdated;

    private int charIndexBeforeCaret;
    private Timer caretBlinkTimer;
    private boolean caretIsVisible;

    private boolean currentlyPainting;

    public Text(Point topLeftCorner, PhotoFrame photoFrame) {
        // Copied and adapted from CRectangularShape class
        super(topLeftCorner, 1, 1);

        this.photoFrame = photoFrame;

        currentlyEditing = false;
        stringBuilder = new StringBuilder();
        stringSplitPerLine = new LinkedList<>();
        stringLength = 0;

        lineHeight = 0;
        lineHeightMustBeUpdated = true;
        dimensionsMustBeUpdated = true;

        charIndexBeforeCaret = -1;
        caretBlinkTimer = new Timer();
        caretIsVisible = true;

        currentlyPainting = false;

        updateStyleFromToolSettings();
        applyStyle();
    }

    @Override
    public void updateStyleFromToolSettings() {
        ToolSettings settings = photoFrame.getToolSettings();
        color = settings.getColor();
        fontFamily = settings.getFontFamily();
        fontSize = settings.getFontSize();

        lineHeightMustBeUpdated = true;
        dimensionsMustBeUpdated = true;

        photoFrame.repaint();
    }

    @Override
    public void applyStyle() {
        setOutlined(false);
        setStroke(new BasicStroke(0));

        setFilled(true);
        setFillPaint(new Color(0, 0,0 ,0));

        photoFrame.repaint();
    }

    public boolean isEditable() {
        return this.currentlyEditing;
    }

    @Override
    public CRectangle getCanvasShape() {
        return this;
    }

    private void startCaretTimer() {
        caretBlinkTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (currentlyEditing) {
                    caretIsVisible = !caretIsVisible;
                    photoFrame.repaint();
                }
            }
        }, 0, DELAY_BETWEEN_CARET_BLINKS);
    }

    private void stopCaretTimer() {
        caretBlinkTimer.cancel();
        caretBlinkTimer = new Timer();
    }

    private void reSplitString(Graphics2D g, FontMetrics metrics) {
        int leftEdge = (int) getBoundingBox().getMinX();
        String singleLineString = stringBuilder.toString();

        stringSplitPerLine.clear();
        stringSplitPerLine.add(singleLineString);

        double photoFrameWidth = photoFrame.getWidth();
        double textWidth = metrics.getStringBounds(singleLineString, g).getWidth();
        boolean textIsTooLarge = leftEdge + textWidth > photoFrameWidth;

        while (textIsTooLarge) {
            String stringToSplit = stringSplitPerLine.getLast();
            String firstPart, secondPart;
            int lastBlankIndex = stringToSplit.lastIndexOf(' ');

            if (lastBlankIndex >= 0) {
                firstPart = stringToSplit.substring(0, lastBlankIndex);
                secondPart = stringToSplit.substring(lastBlankIndex);
            }
            else {
                int splitIndex = stringToSplit.length() - 1;
                while (splitIndex > 0) {
                    textWidth = metrics.getStringBounds(stringToSplit.substring(0, splitIndex), g).getWidth();
                    if (leftEdge + textWidth <= photoFrameWidth) {
                        break;
                    }

                    splitIndex--;
                }

                firstPart = stringToSplit.substring(0, splitIndex);
                secondPart = stringToSplit.substring(splitIndex);
            }

            stringSplitPerLine.removeLast();
            stringSplitPerLine.add(firstPart);
            stringSplitPerLine.add(secondPart);

            textWidth = metrics.getStringBounds(secondPart, g).getWidth();
            textIsTooLarge = leftEdge + textWidth > photoFrameWidth;
        }
    }

    public void insertCharacter(char character) {
        if (charIndexBeforeCaret == stringLength) {
            stringBuilder.append(character);
        }
        else {
            stringBuilder.insert(charIndexBeforeCaret + 1, character);
        }

        stringLength++;
        charIndexBeforeCaret++;

        lineHeightMustBeUpdated = true;
        dimensionsMustBeUpdated = true;
        photoFrame.repaint();
    }

    public void eraseCharacter() {
        if (stringLength == 0) {
            return;
        }

        stringBuilder.deleteCharAt(stringLength - 1);
        stringLength--;
        charIndexBeforeCaret--;

        lineHeightMustBeUpdated = true;
        dimensionsMustBeUpdated = true;
        photoFrame.repaint();
    }

    void moveCaret(int offset) {
        charIndexBeforeCaret += offset;

        if (charIndexBeforeCaret < -1) {
            charIndexBeforeCaret = -1;
        }
        else if (charIndexBeforeCaret > stringLength - 1) {
            charIndexBeforeCaret = stringLength - 1;
        }

        photoFrame.repaint();
    }

    public void moveCaretLeft() {
        moveCaret(-1);
    }

    public void moveCaretRight() {
        moveCaret(+1);
    }

    public void moveCaretBeforeFirstCharacter() {
        moveCaret(-charIndexBeforeCaret - 1);
    }

    public void moveCaretAfterLastCharacter() {
        moveCaret(stringLength - charIndexBeforeCaret - 1);
    }

    public void startEditing() {
        if (currentlyEditing) {
            return;
        }

        if (! photoFrame.hasFocus()) {
            photoFrame.requestFocusInWindow();
        }

        currentlyEditing = true;
        startCaretTimer();
        photoFrame.repaint();

    }

    public void stopEditing() {
        if (! currentlyEditing) {
            return;
        }

        charIndexBeforeCaret = -1;
        currentlyEditing = false;
        stopCaretTimer();
        photoFrame.repaint();
    }

    private void updateLineHeight(Graphics2D g) {
        int previousLineHeight = lineHeight;

        FontMetrics metrics = g.getFontMetrics();
        lineHeight = metrics.getHeight();

        System.out.println("Recomputed line height: " + lineHeight);

        lineHeightMustBeUpdated = false;
    }

    private void configureGraphics(Graphics2D g) {
        // Drawing style
        g.setColor(color);
        g.setFont(new Font(fontFamily, Font.PLAIN, fontSize));
    }

    private Dimension computeDimensions(Graphics2D g) {
        FontMetrics metrics = g.getFontMetrics();

        int width = 1;
        for (String s : stringSplitPerLine) {
            Rectangle2D stringBounds = metrics.getStringBounds(s, g);
            int lineWidth = (int) stringBounds.getWidth();

            if (lineWidth > width) {
                width = lineWidth;
            }
        }

        return new Dimension(width, lineHeight * stringSplitPerLine.size());
    }

    private void updateDimensions(Graphics2D g) {
        Dimension dimensions = computeDimensions(g);
        dimensionsMustBeUpdated = false;

        setWidth(dimensions.width);
        setHeight(dimensions.height);

        System.out.println("recomputed dimensions: " + dimensions.width + ", " + dimensions.height);
    }

    private void drawString(Graphics2D g) {
        int leftEdge = (int) getMinX();
        int topEdge = (int) getMinY();

        int offsetY = 0;
        for (String s : stringSplitPerLine) {
            g.drawString(s, leftEdge, topEdge + lineHeight + offsetY);
            offsetY += lineHeight;
        }
    }

    private void drawCaret(Graphics2D g) {
        if (! (caretIsVisible && currentlyEditing)) {
            return;
        }

        int leftEdge = (int) getMinX();
        int topEdge = (int) getMinY();

        int nbCharsBeforeCurrentLine = 0;
        int caretLineIndex = 0;
        for (String s : stringSplitPerLine) {
            int currentLineLength = s.length();
            if (charIndexBeforeCaret > nbCharsBeforeCurrentLine + currentLineLength) {
                nbCharsBeforeCurrentLine += currentLineLength;
                caretLineIndex++;
            }
        }

        String caretLineString = stringSplitPerLine.get(caretLineIndex);
        String stringToCaret = caretLineString.substring(
            0, Math.min(charIndexBeforeCaret - nbCharsBeforeCurrentLine + 1, caretLineString.length())
        );

        FontMetrics metrics = g.getFontMetrics();
        Rectangle2D stringBounds = metrics.getStringBounds(stringToCaret, g);

        int caretOffsetX = (int)stringBounds.getWidth();
        int caretOffsetY = caretLineIndex * lineHeight;
        int caretHeight = lineHeight;

        g.setColor(Color.BLACK);
        g.fillRect(
            leftEdge + caretOffsetX,
            topEdge + lineHeight + caretOffsetY - caretHeight,
            1,
            caretHeight
        );
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        FontMetrics metrics = g2d.getFontMetrics();

        // Prepare the drawing and perform certain updates if need be
        configureGraphics(g2d);
        reSplitString(g2d, metrics);

        if (lineHeightMustBeUpdated) {
            updateLineHeight(g2d);
        }

        if (dimensionsMustBeUpdated) {
            updateDimensions(g2d);
        }

        // Paint the CShape (rectangle)
        super.paint(g2d);

        // Reconfigure Graphics since the super::paint method can edit it
        configureGraphics(g2d);

        // Paint the text
        drawString(g2d);
        drawCaret(g2d);
    }
}

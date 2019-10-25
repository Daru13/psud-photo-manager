package GUI.Annotations;

import GUI.Components.PhotoFrame;
import GUI.Tools.ToolSettings;
import fr.lri.swingstates.canvas.CRectangle;

import java.awt.Rectangle;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class Text extends CRectangle {

    private final static long DELAY_BETWEEN_CARET_BLINKS = 650; // ms

    private final PhotoFrame photoFrame;

    private Point topLeft;
    private Color color;
    private String fontFamily;
    private int fontSize;

    private boolean currentlyEditing;
    private StringBuilder stringBuilder;
    private LinkedList<String> stringSplitPerLine;
    private int stringLength;

    private int charIndexBeforeCaret;
    private Timer caretBlinkTimer;
    private boolean caretIsVisible;


    public Text(Point topLeft, PhotoFrame photoFrame) {
        this.photoFrame = photoFrame;
        this.topLeft = topLeft;

        stringBuilder = new StringBuilder();
        stringSplitPerLine = new LinkedList<>();
        stringLength = 0;
        currentlyEditing = false;

        charIndexBeforeCaret = -1;
        caretBlinkTimer = new Timer();
        caretIsVisible = true;

        initStyle();
    }

    private void initStyle() {
        ToolSettings settings = photoFrame.getToolSettings();
        color = settings.getColor();
        fontFamily = settings.getFontFamily();
        fontSize = settings.getFontSize();
    }

    public void setColor(Color color) {
        this.color = color;
        photoFrame.repaint();
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
        photoFrame.repaint();
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
        photoFrame.repaint();
    }

    public boolean isEditable() {
        return this.currentlyEditing;
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
        String singleLineString = stringBuilder.toString();

        stringSplitPerLine.clear();
        stringSplitPerLine.add(singleLineString);

        double photoFrameWidth = photoFrame.getWidth();
        double textWidth = metrics.getStringBounds(singleLineString, g).getWidth();
        boolean textIsTooLarge = topLeft.x + textWidth > photoFrameWidth;

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
                    if (topLeft.x + textWidth <= photoFrameWidth) {
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
            textIsTooLarge = topLeft.x + textWidth > photoFrameWidth;
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

        photoFrame.repaint();
    }

    public void eraseCharacter() {
        if (stringLength == 0) {
            return;
        }

        stringBuilder.deleteCharAt(stringLength - 1);
        stringLength--;
        charIndexBeforeCaret--;

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

    private void configureGraphics(Graphics2D g, boolean draft) {
        // Rendering quality
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                draft ? RenderingHints.VALUE_RENDER_SPEED : RenderingHints.VALUE_RENDER_QUALITY);

        // Drawing style
        g.setColor(color);
        g.setFont(new Font(fontFamily, Font.PLAIN, fontSize));
    }

    Rectangle computeBoundingBox(Graphics2D g) {
        FontMetrics metrics = g.getFontMetrics();
        int lineHeight = metrics.getHeight();

        int width = 1;
        for (String s : stringSplitPerLine) {
            Rectangle2D stringBounds = metrics.getStringBounds(s, g);
            int lineWidth = (int) stringBounds.getWidth();

            if (lineWidth > width) {
                width = lineWidth;
            }
        }

        return new Rectangle(topLeft.x, topLeft.y, width, lineHeight * stringSplitPerLine.size());
    }

    private void drawString(Graphics2D g) {
        FontMetrics metrics = g.getFontMetrics();
        int lineHeight = metrics.getHeight();
        int offsetY = 0;

        for (String s : stringSplitPerLine) {
            g.drawString(s, topLeft.x, topLeft.y + offsetY);
            offsetY += lineHeight;
        }
    }

    private void drawCaret(Graphics2D g) {
        if (! (caretIsVisible && currentlyEditing)) {
            return;
        }

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

        int lineHeight = metrics.getHeight();
        int caretOffsetX = (int)stringBounds.getWidth();
        int caretOffsetY = caretLineIndex * lineHeight;
        int caretHeight = lineHeight;

        g.setColor(Color.BLACK);
        g.fillRect(
                topLeft.x + caretOffsetX,
                topLeft.y - caretHeight + caretOffsetY,
                1,
                caretHeight
        );
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        configureGraphics(g2d, false);
        FontMetrics metrics = g2d.getFontMetrics();
        reSplitString(g2d, metrics);

        drawString(g2d);
        drawCaret(g2d);
    }
}

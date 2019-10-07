package GUI.Tools;

import GUI.Components.PhotoFrame;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;


/**
 * A tool to draw text.
 *
 * A caret is displayed at the current edit position.
 * Moreover, the text is automatically split into several lines
 * when it reaches the right edge of the photo frame.
 *
 * Certain keys have special actions (see keyPressed method).
 *
 * @see Tool
 */
public class Text extends ToolAdapter {

    private final static long DELAY_BETWEEN_CARET_BLINKS = 650; // ms

    private PhotoFrame photoFrame;

    private boolean currentlyEditing;
    private StringBuilder stringBuilder;
    private LinkedList<String> stringSplitPerLine;
    private int stringLength;

    private int charIndexBeforeCaret;
    private Timer caretBlinkTimer;
    private boolean caretIsVisible;

    private int firstClickX;
    private int firstClickY;


    public Text(PhotoFrame photoFrame) {
        this.photoFrame = photoFrame;

        stringBuilder = new StringBuilder();
        stringSplitPerLine = new LinkedList<>();
        stringLength = 0;
        currentlyEditing = false;

        charIndexBeforeCaret = -1;
        caretBlinkTimer = new Timer();
        caretIsVisible = true;

        firstClickX = 0;
        firstClickY = 0;
    }

    private void reSplitString(Graphics2D g, FontMetrics metrics) {
        String singleLineString = stringBuilder.toString();

        stringSplitPerLine.clear();
        stringSplitPerLine.add(singleLineString);

        double photoFrameWidth = photoFrame.getWidth();
        double textWidth = metrics.getStringBounds(singleLineString, g).getWidth();
        boolean textIsTooLarge = firstClickX + textWidth > photoFrameWidth;

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
                    if (firstClickX + textWidth <= photoFrameWidth) {
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
            textIsTooLarge = firstClickX + textWidth > photoFrameWidth;
        }
    }

    private void insertCharacter(char character) {
        if (charIndexBeforeCaret == stringLength) {
            stringBuilder.append(character);
        }
        else {
            stringBuilder.insert(charIndexBeforeCaret + 1, character);
        }

        stringLength++;
        charIndexBeforeCaret++;

        drawString(true);
    }

    private void eraseCharacter() {
        if (stringLength == 0) {
            return;
        }

        stringBuilder.deleteCharAt(stringLength - 1);
        stringLength--;
        charIndexBeforeCaret--;

        drawString(true);
    }

    private void moveCaret(int offset) {
        charIndexBeforeCaret += offset;

        if (charIndexBeforeCaret < -1) {
            charIndexBeforeCaret = -1;
        }
        else if (charIndexBeforeCaret > stringLength - 1) {
            charIndexBeforeCaret = stringLength - 1;
        }

        drawString(true);
    }

    private void startEditing(int fromX, int fromY) {
        if (! photoFrame.hasFocus()) {
            photoFrame.requestFocusInWindow();
        }

        firstClickX = fromX;
        firstClickY = fromY;
        currentlyEditing = true;
        caretIsVisible = true;

        drawString(true);
    }

    private void stopEditing() {
        drawString(false);
        photoFrame.clearWorkingCanvas();

        stringBuilder = new StringBuilder();
        stringLength = 0;
        charIndexBeforeCaret = -1;
        currentlyEditing = false;
    }

    @Override
    public void toolSelected() {
        caretBlinkTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (currentlyEditing) {
                    caretIsVisible = !caretIsVisible;
                    drawString(true);
                }
            }
        }, 0, DELAY_BETWEEN_CARET_BLINKS);
    }

    @Override
    public void toolDeselected() {
        caretBlinkTimer.cancel();
        caretBlinkTimer = new Timer();
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        if (event.getClickCount() != 1) {
            return;
        }

        if (currentlyEditing) {
            stopEditing();
        }
        else {
            startEditing(event.getX(), event.getY());
        }
    }

    @Override
    public void keyPressed(KeyEvent event) {
        if (! currentlyEditing) {
            return;
        }

        switch (event.getKeyCode()) {
            case KeyEvent.VK_BACK_SPACE:
                eraseCharacter();
                break;

            case KeyEvent.VK_LEFT:
                moveCaret(-1);
                break;

            case KeyEvent.VK_RIGHT:
                moveCaret(+1);
                break;

            case KeyEvent.VK_DOWN:
                moveCaret(-charIndexBeforeCaret - 1);
                break;

            case KeyEvent.VK_UP:
                moveCaret(stringLength - charIndexBeforeCaret - 1);
                break;

            case KeyEvent.VK_ENTER:
                stopEditing();
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent event) {
        if (! currentlyEditing) {
            return;
        }

        char character = event.getKeyChar();
        if (! Character.isISOControl(character)) {
            insertCharacter(character);
        }
    }

    private void configureRenderingHints(Graphics2D g, boolean draft) {
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                draft ? RenderingHints.VALUE_RENDER_SPEED : RenderingHints.VALUE_RENDER_QUALITY);
    }

    private void applyToolSettings(Graphics2D g) {
        ToolSettings settings = photoFrame.getToolSettings();

        g.setColor(settings.getColor());
        g.setFont(new Font(settings.getFontFamily(), Font.PLAIN, settings.getFontSize()));
    }

    private void configureGraphics(Graphics2D g, boolean draft) {
        configureRenderingHints(g, draft);
        applyToolSettings(g);
    }

    private void drawString(boolean useWorkingCanvas) {
        Graphics2D g = useWorkingCanvas
                ? (Graphics2D)photoFrame.getWorkingCanvas().getGraphics()
                : (Graphics2D)photoFrame.getPhotoBack().getGraphics();

        if (useWorkingCanvas) {
            photoFrame.clearWorkingCanvas();
        }

        configureGraphics(g, useWorkingCanvas);

        FontMetrics metrics = g.getFontMetrics();
        reSplitString(g, metrics);
        int lineHeight = metrics.getHeight();
        int offsetY = 0;

        for (String s : stringSplitPerLine) {
            g.drawString(s, firstClickX, firstClickY + offsetY);
            offsetY += lineHeight;
        }

        if (useWorkingCanvas) {
            drawCaret();
        }

        photoFrame.repaint();
    }

    private void drawCaret() {
        if (! caretIsVisible) {
            return;
        }

        Graphics2D g = (Graphics2D)photoFrame.getWorkingCanvas().getGraphics();
        applyToolSettings(g);

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
            firstClickX + caretOffsetX,
            firstClickY - caretHeight + caretOffsetY,
            1,
            caretHeight
        );
    }
}

package GUI.Tools;

import GUI.Components.PhotoFrame;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class Text extends ToolAdapter {

    private PhotoFrame photoFrame;

    private boolean currentlyEditing;
    private StringBuilder stringBuilder;
    private int stringLength;
    private int charIndexBeforeCaret;

    private int firstClickX;
    private int firstClickY;

    public Text(PhotoFrame photoFrame) {
        this.photoFrame = photoFrame;

        stringBuilder = new StringBuilder();
        stringLength = 0;
        charIndexBeforeCaret = -1;
        currentlyEditing = false;

        firstClickX = 0;
        firstClickY = 0;
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
        if (!photoFrame.hasFocus()) {
            photoFrame.requestFocusInWindow();
        }

        firstClickX = fromX;
        firstClickY = fromY;
        currentlyEditing = true;

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
        if (!currentlyEditing) {
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
        if (!currentlyEditing) {
            return;
        }

        char character = event.getKeyChar();
        if (!Character.isISOControl(character)) {
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
        g.drawString(stringBuilder.toString(), firstClickX, firstClickY);

        if (useWorkingCanvas) {
            drawCaret();
        }

        photoFrame.repaint();
    }

    private void drawCaret() {
        Graphics2D g = (Graphics2D)photoFrame.getWorkingCanvas().getGraphics();
        applyToolSettings(g);

        String stringToCaret = stringBuilder
                .toString()
                .substring(0, Math.min(charIndexBeforeCaret + 1, stringLength));

        FontMetrics metrics = g.getFontMetrics();
        Rectangle2D stringBounds = metrics.getStringBounds(stringToCaret, g);
        int caretOffsetX = (int)stringBounds.getWidth();
        int caretHeight = metrics.getHeight();

        g.setColor(Color.BLACK);
        g.fillRect(firstClickX + caretOffsetX, firstClickY - caretHeight, 1, caretHeight);
    }
}

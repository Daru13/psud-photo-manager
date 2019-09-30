package UI.Tools;

import UI.Components.PhotoFrame;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Text extends AbstractTool {

    private StringBuilder stringBuilder;
    private boolean currentlyEditing;

    private int firstClickX;
    private int firstClickY;

    public Text(PhotoFrame photoFrame) {
        super(photoFrame);

        stringBuilder = new StringBuilder();
        currentlyEditing = false;

        firstClickX = 0;
        firstClickY = 0;
    }

    private void appendCharacter(char character) {
        stringBuilder.append(character);

        drawString(true);
    }

    private void eraseCharacter() {
        int nbCharacters = stringBuilder.length();
        stringBuilder.deleteCharAt(nbCharacters - 1);

        drawString(true);
    }

    private void startEditing(int fromX, int fromY) {
        if (!photoFrame.hasFocus()) {
            photoFrame.requestFocusInWindow();
        }

        firstClickX = fromX;
        firstClickY = fromY;
        currentlyEditing = true;
    }

    private void stopEditing() {
        drawString(false);
        photoFrame.clearWorkingCanvas();

        stringBuilder = new StringBuilder();
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
            appendCharacter(character);
        }
    }

    private void configureGraphics(Graphics2D g, boolean draft) {
        // Rendering quality
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                draft ? RenderingHints.VALUE_RENDER_SPEED : RenderingHints.VALUE_RENDER_QUALITY);

        // Drawing style
        ToolSettings settings = photoFrame.getToolSettings();
        g.setColor(settings.getColor());
        g.setFont(new Font(settings.getFontFamily(), Font.PLAIN, settings.getFontSize()));
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
        photoFrame.repaint();
    }
}

package UI.Tools;

import java.awt.*;

public class ToolSettings {
    public Color color;
    public int thickness;

    public ToolSettings() {
        setDefaultSettings();
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getThickness() {
        return thickness;
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    public void setDefaultSettings() {
        setColor(Color.BLACK);
        setThickness(5);
    }
}

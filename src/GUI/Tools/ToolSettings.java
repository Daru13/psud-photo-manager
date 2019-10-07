package GUI.Tools;

import java.awt.*;

public class ToolSettings {

    public Color color;
    public int thickness;

    private String fontFamily;
    private int fontSize;


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

    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public void setDefaultSettings() {
        setColor(Color.BLACK);
        setThickness(5);

        setFontFamily("Helvetica");
        setFontSize(16);
    }
}

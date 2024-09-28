package core.graphics;

import java.awt.*;

public class button {
    private double centerX;
    private double centerY;
    private double widthX;
    private double widthY;
    private String name;
    private Color color;
    private Font font;
    private String field;
    public button(double centerX, double centerY, double widthX, double widthY, String name, String field, Font font) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.widthX = widthX;
        this.widthY = widthY;
        this.name = name;
        color = Color.WHITE;
        this.font = font;
        this.field = field;
    }

    public double getX() {
        return centerX;
    }
    public double getY() {
        return centerY;
    }
    public double getWidthX() {
        return widthX;
    }
    public double getWidthY() {
        return widthY;
    }
    public String getName() {
        return name;
    }
    public void changeName(String newText) {
        name = newText;
    }
    public void changeName(String newText, Color newColor) {
        name = newText;
        color = newColor;
    }
    public Color getColor() {
        return color;
    }
    public void setColorWhite() {
        color = Color.WHITE;
    }
    public void setColorGreen() {
        color = Color.GREEN;
    }
    public Font getFont() {
        return font;
    }

    public String getField() {
        return field;
    }
}

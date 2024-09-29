package core.graphics;

import java.awt.*;

public class button {
    private rectangle box;
    private String name;
    private Color color;
    private Font font;
    private String field;
    public button(rectangle box, String name, String field, Font font) {
        this.box = box;
        this.name = name;
        color = Color.WHITE;
        this.font = font;
        this.field = field;
    }

    public double getX() {
        return box.getX();
    }
    public double getY() {
        return box.getY();
    }
    public double getWidthX() {
        return box.getWidthX();
    }
    public double getWidthY() {
        return box.getWidthY();
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

package core.graphics;

public class button {
    private double centerX;
    private double centerY;
    private double widthX;
    private double widthY;
    private String name;
    private int id;
    public button(double centerX, double centerY, double widthX, double widthY, String name, int id) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.widthX = widthX;
        this.widthY = widthY;
        this.name = name;
        this.id = id;
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
}

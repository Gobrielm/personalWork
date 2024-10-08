package core.graphics;

public class button {
    private double centerX;
    private double centerY;
    private double widthX;
    private double widthY;
    public button(double centerX, double centerY, double widthX, double widthY) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.widthX = widthX;
        this.widthY = widthY;
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
}

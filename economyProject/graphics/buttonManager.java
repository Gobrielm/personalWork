package core.graphics;

import core.Utils;

import java.awt.*;

public class buttonManager {
    private static button[][] screen;
    private final static int howManyDecimalsRepresented = 3;
    public static void initialize() {
        int slots = (int) Math.pow(10, howManyDecimalsRepresented);
        screen = new button[slots][slots];
    }
    public static button[][] getArray() {
        return screen;
    }
    public static void addButton(button button) {
        double widthX = button.getWidthX();
        double widthY = button.getWidthY();
        int newX = convertDecimalScreenToSlot(button.getX());
        int newY = convertDecimalScreenToSlot(button.getY());
        int newWidthX = convertDecimalScreenToSlot(widthX);
        int newWidthY = convertDecimalScreenToSlot(widthY);
        for (int x = newX - newWidthX; x < newX + newWidthX; x++) {
            for (int y = newY - newWidthY; y < newY + newWidthY; y++) {
                addToScreen(x, y, button);
            }
        }
    }
    private static int convertDecimalScreenToSlot(double decimal) {
        double num = Utils.floor(decimal, howManyDecimalsRepresented);
        return (int) (num * Math.pow(10, howManyDecimalsRepresented));
    }
    public static void addToScreen(int slotX, int slotY, button button) {
        if (screen[slotX][slotY] == null) {
            screen[slotX][slotY] = button;
        }
    }
    public static void changeTextOnScreen(double x, double y, double width, double height, String newText) {
        changeTextOnScreen(x, y, width, height, newText, null);
    }
    public static void changeTextOnScreen(double x, double y, double width, double height, String newText, Color color) {
        int newX = convertDecimalScreenToSlot(x);
        int newY = convertDecimalScreenToSlot(y);
        int newWidthX = convertDecimalScreenToSlot(width);
        int newWidthY = convertDecimalScreenToSlot(height);
        for (int i = newX - newWidthX; i < newX + newWidthX; i++) {
            for (int j = newY - newWidthY; j < newY + newWidthY; j++) {
                button toGet = getButtonClickedSlot(i, j);
                if (toGet == null) {
                    return;
                }
                toGet.changeName(newText, color);
            }
        }
    }
    public static button getButtonClicked(double mouseX, double mouseY) {
        int slotX = convertDecimalScreenToSlot(mouseX);
        int slotY = convertDecimalScreenToSlot(mouseY);
        return screen[slotX][slotY];
    }
    public static button getButtonClickedSlot(int slotX, int slotY) {
        return screen[slotX][slotY];
    }
    public static void changeSelected(button clicked) {
        if (clicked == null || !clicked.getField().equals("GoodName") || !clicked.getField().equals("Basic")) {
            return;
        }
        String field = clicked.getField();
        for (button[] buttonArray: screen) {
            for (button x: buttonArray) {
                if (x != null && x.getField().equals(field)) {
                    x.setColorWhite();
                }
            }
        }
        clicked.setColorGreen();
    }
}

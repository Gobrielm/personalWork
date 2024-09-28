package core.graphics;

import core.Utils;

import java.util.ArrayList;
import java.util.LinkedList;

public class buttonManager {
    private static button[][] screen;
    private final static int howManyDecimalsRepresented = 3;
    public static void initialize() {
        int slots = (int) Math.pow(10, howManyDecimalsRepresented);
        screen = new button[slots][slots];
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
    public static button getButtonClicked(double mouseX, double mouseY) {
        int slotX = convertDecimalScreenToSlot(mouseX);
        int slotY = convertDecimalScreenToSlot(mouseY);
        return screen[slotX][slotY];
    }
}

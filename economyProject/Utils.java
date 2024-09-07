package core;

public class Utils {

    public static double round(double num, int decimal) {
        return (double) Math.round(num * Math.pow(10, decimal)) / Math.pow(10, decimal);
    }
    public static double round(double num, double closest) {
        if (closest >= 1) {
            return Math.round(num);
        }
        double x = (num % closest);
        num -= x;
        if (closest / 2 < x) {
            num += closest;
        }
        return num;
    }
    public static int roundNoZero(double num) {
        int toReturn = num < 0 ? -1: 1;
        toReturn = num == 0 ? 0: toReturn;
        return toReturn;
    }
}

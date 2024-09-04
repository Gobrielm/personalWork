package core;

import edu.princeton.cs.algs4.StdDraw;

public class main {
    public static boolean turnEnd = true;
    public static void main(String[] args) throws InterruptedException {
        graphicalInterface.startGame();
        int playerCount = 4;
        double x = Utils.round(1.3, 0.5);
        boolean turnEnd = true;
        economy economy = new economy(325235, 4);
        while (true) {
            if (turnEnd) {
                turnEnd = economy.tick();
            }
            StdDraw.pause(20);
        }
    }
}
package core;

import edu.princeton.cs.algs4.StdDraw;

public class main {
    public static boolean turnEnd = true;
    public static void main(String[] args) throws InterruptedException {
        int playerCount = 4;

        economy galaxy = new economy(1000, 4);
        while (true) {
            if (turnEnd) {
                galaxy.tick();
                turnEnd = false;
            }

        }
    }
}
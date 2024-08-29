package core;

import edu.princeton.cs.algs4.StdDraw;

public class main {
    public static boolean turnEnd = true;
    public static void main(String[] args) throws InterruptedException {
        graphicalInterface.startGame();
        int playerCount = 4;

        economy economy = new economy(1000, 4);
        while (true) {
            if (StdDraw.isMousePressed()) {
                double x = StdDraw.mouseX() / graphicalInterface.WIDTH;
                double y = StdDraw.mouseY() / graphicalInterface.HEIGHT;
                if (x > 0.91 && x < 0.99 && y > 0.91 && y < 0.99) {
                    turnEnd = true;
                } else if (x > 0.3 && x < 0.51) {
                    int temp = (int) Math.round((y * (good.getGoodList().length + 1) - 1));
                    graphicalInterface.drawGoods(good.getGoodList()[temp]);
                }
                StdDraw.pause(100);
            }
            if (turnEnd) {
                economy.tick();
                turnEnd = false;
            }
            StdDraw.pause(20);
        }
    }
}
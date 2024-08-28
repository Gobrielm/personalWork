package core;

import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;

public class graphicalInterface {
    static final int WIDTH = 60;
    static final int HEIGHT = 30;
    static final int CANVASSIZE = 30;
    public static void drawPlanetMenu(economy galaxy) {
        Font small = new Font("Monaco", Font.BOLD, 15);
        StdDraw.setFont(small);
        StdDraw.setCanvasSize(WIDTH * CANVASSIZE, HEIGHT * CANVASSIZE);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.enableDoubleBuffering();
        StdDraw.line(WIDTH / 3, 0, WIDTH / 3, HEIGHT);
        StdDraw.line(WIDTH / 3 * 2, 0, WIDTH / 3 * 2, HEIGHT);
        StdDraw.show();
    }
}

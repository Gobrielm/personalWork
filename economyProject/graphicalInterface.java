package core;

import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;

public class graphicalInterface {
    static final int WIDTH = 60;
    static final int HEIGHT = 30;
    static final int CANVASSIZE = 30;
    static final Font SMALL = new Font("Monaco", Font.BOLD, 15);
    static final Font BIG = new Font("Monaco", Font.BOLD, 30);

    public static void drawPlanetMenu(player player) {
        int planetID = player.getPlanet();

        StdDraw.setFont(SMALL);
        StdDraw.setCanvasSize(WIDTH * CANVASSIZE, HEIGHT * CANVASSIZE);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.enableDoubleBuffering();
        StdDraw.line(WIDTH / 3, 0, WIDTH / 3, HEIGHT);
        StdDraw.line(WIDTH / 3 * 2, 0, WIDTH / 3 * 2, HEIGHT);

        StdDraw.setFont(BIG);
        StdDraw.text(WIDTH / 6, HEIGHT / 10 * 9, player.getName());
        StdDraw.text(WIDTH / 6, HEIGHT / 10 * 8, Integer.toString((int) Math.round(player.getCash())));

        StdDraw.setFont(SMALL);
        planet planet = economy.getPlanetFromID(planetID);
        String[] goodNames = planet.getGoodList();
        Double[] goodPrices = planet.getPriceList();
        for (int i = 0; i < goodNames.length; i++) {
            StdDraw.text(WIDTH / 12 * 5, (double) HEIGHT / (goodNames.length + 1) * (i + 1), goodNames[i]);
            StdDraw.text(WIDTH / 2, (double) HEIGHT / (goodNames.length + 1) * (i + 1), Integer.toString((int) Math.round(goodPrices[i])));
            StdDraw.line(WIDTH / 3, (double) HEIGHT / (goodNames.length + 1) * (i + 1.5), (double) WIDTH / 100 * 51, (double) HEIGHT / (goodNames.length + 1) * (i + 1.5));
        }
        StdDraw.line(WIDTH / 3, (double) HEIGHT / (goodNames.length + 1) * (0.5), (double) WIDTH / 100 * 51, (double) HEIGHT / (goodNames.length + 1) * (0.5));
        StdDraw.line((double) WIDTH / 100 * 51, (double) HEIGHT / (goodNames.length) / 2, (double) WIDTH / 100 * 51, HEIGHT - (double) HEIGHT / (goodNames.length) / 2);
        StdDraw.show();
    }
}

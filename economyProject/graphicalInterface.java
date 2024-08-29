package core;

import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;

public class graphicalInterface {
    static final double WIDTH = 60;
    static final double HEIGHT = 30;
    static final int CANVASSIZE = 30;
    static final Font SMALL = new Font("Monaco", Font.BOLD, 15);
    static final Font MED = new Font("Monaco", Font.BOLD, 20);
    static final Font BIG = new Font("Monaco", Font.BOLD, 30);

    public static void startGame() {
        StdDraw.setFont(SMALL);
        StdDraw.setCanvasSize((int) (WIDTH * CANVASSIZE), (int) (HEIGHT * CANVASSIZE));
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.enableDoubleBuffering();
    }


    public static void drawPlanetMenu(player player) {
        int planetID = player.getPlanet();
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
            StdDraw.text(WIDTH / 12 * 5,  HEIGHT / (goodNames.length + 1) * (i + 1), goodNames[i]);
            StdDraw.text(WIDTH / 2,  HEIGHT / (goodNames.length + 1) * (i + 1), Integer.toString((int) Math.round(goodPrices[i])));
            StdDraw.line(WIDTH / 3,  HEIGHT / (goodNames.length + 1) * (i + 1.5),  WIDTH / 100 * 51,  HEIGHT / (goodNames.length + 1) * (i + 1.5));
        }
        StdDraw.line(WIDTH / 3,  HEIGHT / (goodNames.length + 1) * (0.5),  WIDTH * 0.51,  HEIGHT / (goodNames.length + 1) * (0.5));
        StdDraw.line( WIDTH * 0.51,  HEIGHT / (goodNames.length) * 0.5,  WIDTH * 0.51, HEIGHT -  HEIGHT / (goodNames.length) * 0.5);
        StdDraw.rectangle( WIDTH * 0.95,  HEIGHT * 0.95, WIDTH * 0.04, HEIGHT * 0.04);

        StdDraw.setFont(MED);
        StdDraw.text( WIDTH / 100 * 95,  HEIGHT / 100 * 95, "Next Turn!");
        StdDraw.show();
    }
}

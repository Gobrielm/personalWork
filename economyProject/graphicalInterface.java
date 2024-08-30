package core;

import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

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
        StdDraw.clear(Color.BLACK);
        int planetID = player.getPlanet();
        StdDraw.line(WIDTH * 0.333, 0, WIDTH * 0.333, HEIGHT);
        StdDraw.line(WIDTH * 0.666, 0, WIDTH * 0.666, HEIGHT);

        StdDraw.setFont(BIG);
        StdDraw.text(WIDTH * 0.166, HEIGHT * 0.9, player.getName());
        StdDraw.text(WIDTH * 0.166, HEIGHT * 0.8, Integer.toString((int) Math.round(player.getCash())));

        StdDraw.setFont(SMALL);
        planet planet = economy.getPlanetFromID(planetID);
        String[] goodNames = planet.getGoodList();
        Double[] goodPrices = planet.getPriceList();
        for (int i = 0; i < goodNames.length; i++) {
            StdDraw.text(WIDTH * 0.42,  HEIGHT / (goodNames.length + 1) * (i + 1), goodNames[i]);
            StdDraw.text(WIDTH * .50,  HEIGHT / (goodNames.length + 1) * (i + 1), Integer.toString((int) Math.round(goodPrices[i])));
            StdDraw.line(WIDTH * 0.333,  HEIGHT / (goodNames.length + 1) * (i + 1.5),  WIDTH * 0.51,  HEIGHT / (goodNames.length + 1) * (i + 1.5));
        }
        StdDraw.line(WIDTH * 0.333,  HEIGHT / (goodNames.length + 1) * (0.5),  WIDTH * 0.51,  HEIGHT / (goodNames.length + 1) * (0.5));
        StdDraw.line( WIDTH * 0.51,  HEIGHT / (goodNames.length) * 0.5,  WIDTH * 0.51, HEIGHT -  HEIGHT / (goodNames.length) * 0.5);
        StdDraw.rectangle( WIDTH * 0.95,  HEIGHT * 0.95, WIDTH * 0.04, HEIGHT * 0.04);

        StdDraw.setFont(MED);
        StdDraw.text( WIDTH / 100 * 95,  HEIGHT / 100 * 95, "Next Turn!");
        StdDraw.show();
    }

    public static void drawGoods(player player, String name) {
        StdDraw.clear(Color.BLACK);
        drawPlanetMenu(player);
        planet planet = economy.getPlanetFromID(player.getPlanet());
        order[] buyOrders = planet.getBuyOrders(name);
        order[] sellOrders = planet.getSellOrders(name);
        int total = buyOrders.length + sellOrders.length;
        int curr = 0;
        for (int i = 0; i < buyOrders.length; i++) {
            StdDraw.text(WIDTH * 0.75,  HEIGHT / (total + 1) * (i + 1), "Buy Order: " + buyOrders[i].toString());
            curr++;
        }
        for (int i = curr; i < total; i++) {
            StdDraw.text(WIDTH * 0.75,  HEIGHT / (total + 1) * (i + 1), "Sell Order: " + sellOrders[i - curr].toString());
        }
        StdDraw.show();
    }

    public static void drawGraph(player player, String name) {
        StdDraw.clear(Color.BLACK);
        drawPlanetMenu(player);
        StdDraw.setFont(MED);
        planet planet = economy.getPlanetFromID(player.getPlanet());
        double startX = 0.75;
        double startY = 0.5;
        HashMap<String, ArrayList<coords>> graph = planet.getGraph();
        StdDraw.line(WIDTH * startX, HEIGHT * startY, WIDTH * (startX + 0.2), HEIGHT * startY);
        StdDraw.line(WIDTH * startX, HEIGHT * startY, WIDTH * startX, HEIGHT * (startY + 0.2));
        for (coords x: graph.get(name)) {
            StdDraw.circle(WIDTH * (startX + x.quantity * 0.002), HEIGHT * (startY + x.price * 0.01), 0.1);
            System.out.println(WIDTH * (startX + x.quantity * 0.002));
            System.out.println(HEIGHT * (startY - x.price * 0.01));
        }
        StdDraw.show();
    }
}

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
    static int button;
    public static void startGame() {
        StdDraw.setFont(SMALL);
        StdDraw.setCanvasSize((int) (WIDTH * CANVASSIZE), (int) (HEIGHT * CANVASSIZE));
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.enableDoubleBuffering();
        button = 1;
    }
    public static void drawStuff(player player, String name) {
        if (button == 1) {
            drawGoods(player, name);
        } else if (button == 2) {
            drawGraph(player, name);
        } else if (button == 3) {
            drawCompanies(player, name);
        } else {
            drawPrice(player, name);
        }
    }

    public static void drawPlanetMenu(player player) {
        StdDraw.clear(Color.BLACK);
        int planetID = player.getPlanet();
        StdDraw.line(WIDTH * 0.333, 0, WIDTH * 0.333, HEIGHT);
        StdDraw.line(WIDTH * 0.666, 0, WIDTH * 0.666, HEIGHT);

        StdDraw.setFont(BIG);
        StdDraw.text(WIDTH * 0.166, HEIGHT * 0.9, player.getName());
        StdDraw.text(WIDTH * 0.166, HEIGHT * 0.8, Integer.toString((int) Math.round(player.getCash())));
        StdDraw.setFont(MED);
        StdDraw.text(WIDTH * 0.0833, HEIGHT * 0.5, "Goods");
        StdDraw.rectangle(WIDTH * 0.0833, HEIGHT * 0.5, WIDTH * 0.0415, HEIGHT * 0.034);
        StdDraw.text(WIDTH * 0.166, HEIGHT * 0.5, "Graph");
        StdDraw.rectangle(WIDTH * 0.166, HEIGHT * 0.5, WIDTH * 0.0415, HEIGHT * 0.034);
        StdDraw.text(WIDTH * 0.249, HEIGHT * 0.5, "Companies");
        StdDraw.rectangle(WIDTH * 0.249, HEIGHT * 0.5, WIDTH * 0.0415, HEIGHT * 0.034);
        StdDraw.text(WIDTH * 0.166, HEIGHT * 0.433, "Price");
        StdDraw.rectangle(WIDTH * 0.166, HEIGHT * 0.433, WIDTH * 0.0415, HEIGHT * 0.034);

        StdDraw.setFont(SMALL);
        planet planet = economy.getPlanetFromID(planetID);
        String[] goodNames = planet.getGoodList();
        Double[] goodPrices = planet.getPriceList();
        for (int i = 0; i < goodNames.length; i++) {
            StdDraw.text(WIDTH * 0.42,  HEIGHT / (goodNames.length + 1) * (i + 1), goodNames[i]);
            StdDraw.text(WIDTH * .50,  HEIGHT / (goodNames.length + 1) * (i + 1), Double.toString((double) Math.round(goodPrices[i] * 10) / 10));
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
        StdDraw.line(WIDTH * startX, HEIGHT * startY, WIDTH * (startX + 0.2), HEIGHT * startY);
        StdDraw.line(WIDTH * startX, HEIGHT * startY, WIDTH * startX, HEIGHT * (startY + 0.2));

        int[] buyGraph = new int[100];
        int[] sellGraph = new int[100];

        for (company x: planet.getCompanies()) {
            String[] listGoods = x.getRecipe().getOutput();
            for (int i = 0; i < listGoods.length; i++) {
                if (listGoods[i].equals(name)) {
                    int num = (int) Math.round(x.minSellPrice(name) * 2.5);
                    if (num < 50) {
                        sellGraph[num] += x.getRecipe().getOutputAmount()[i];
                    }

                    break;
                }
            }
        }
        for (company x: planet.getCompanies()) {
            String[] listGoods = x.getRecipe().getInput();
            for (int i = 0; i < listGoods.length; i++) {
                if (listGoods[i].equals(name)) {
                    int num = (int) Math.round(x.maxBuyPrice(name) * 2.5);
                    if (num < 50) {
                        buyGraph[num] += x.getRecipe().getInputAmount()[i];
                    }
                    break;
                }
            }
        }
        StdDraw.setPenColor(Color.GREEN);
        for (int i = 0; i < buyGraph.length; i++) {
            if (buyGraph[i] == 0) {
                continue;
            }
            double newX = WIDTH * (startX + buyGraph[i] * 0.025);
            double newY = HEIGHT * (startY + i * 0.004);
            if (newX > 0.95 * WIDTH || newY > 0.7 * HEIGHT) {
                System.out.println("OFBs" + "x: " +  newX + " y: " + newY);
            } else {
                StdDraw.circle(newX, newY, 0.1);
            }
        }
        StdDraw.setPenColor(Color.RED);
        for (int i = 0; i < sellGraph.length; i++) {
            if (sellGraph[i] == 0) {
                continue;
            }
            double newX = WIDTH * (startX + sellGraph[i] * 0.025);
            double newY = HEIGHT * (startY + i * 0.004);
            if (newX > 0.95 * WIDTH || newY > 0.7 * HEIGHT) {
                System.out.println("OFBs" + "x: " +  newX + " y: " + newY);
            } else {
                StdDraw.circle(newX, newY, 0.1);
            }
        }

        StdDraw.setPenColor(Color.WHITE);
        StdDraw.show();
    }

    public static void drawPrice(player player, String name) {
        StdDraw.clear(Color.BLACK);
        drawPlanetMenu(player);
        planet planet = economy.getPlanetFromID(player.getPlanet());
        StdDraw.setFont(MED);
        StdDraw.text(WIDTH * 0.75, HEIGHT * 0.5, String.valueOf(planet.getPriceSold(name)));

        StdDraw.show();
    }

    public static void drawCompanies(player player, String name) {
        StdDraw.clear(Color.BLACK);
        drawPlanetMenu(player);
        planet planet = economy.getPlanetFromID(player.getPlanet());
        company[] companies = planet.getCompanies();
        int size = companies.length;
        for (int i = 0; i < size; i++) {
            company x = companies[i];
            for (String k: x.getRecipe().getInput()) {
                if (k.equals(name)) {
                    StdDraw.text(WIDTH * 0.8, HEIGHT / (size + 1) * (i + 1), x.toString());
                }
            }
            for (String k: x.getRecipe().getOutput()) {
                if (k.equals(name)) {
                    StdDraw.text(WIDTH * 0.8, HEIGHT / (size + 1) * (i + 1), x.toString());
                }
            }

        }

        StdDraw.show();
    }
}

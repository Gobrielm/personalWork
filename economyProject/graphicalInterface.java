package core;

import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.util.Objects;

public class graphicalInterface {
    static final double WIDTH = 60;
    static final double HEIGHT = 30;
    static final int CANVASSIZE = 30;
    static final Font SMALL = new Font("Monaco", Font.BOLD, 15);
    static final Font MED = new Font("Monaco", Font.BOLD, 20);
    static final Font BIG = new Font("Monaco", Font.BOLD, 30);
    static final int precision = 2; // How many slots every dollar
    static String textboxAmount = "";
    static String textboxPrice = "";
    static String goodSelected = "";
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
    public static boolean changeButton(int button) {
        if (button <= 0) {
            return false;
        }
        if (graphicalInterface.button == button) {
            return false;
        }
        graphicalInterface.button = button;
        return true;
    }
    public static boolean setGoodSelected(String goodName) {
        boolean toReturn = !Objects.equals(goodName, goodSelected);
        goodSelected = goodName;
        return toReturn;
    }
    public static void resetStrings() {
        textboxAmount = "";
        textboxPrice = "";
        goodSelected = "";
    }
    public static void drawStuff(player player, int newButton) {
        boolean change = changeButton(newButton);
        if (change) {
            textboxAmount = "";
            textboxPrice = "";
        }

        if (button == 1) {
            drawGoods(player);
        } else if (button == 2) {
            drawGraph(player);
        } else if (button == 3) {
            drawCompanies(player);
        } else if (button == 4) {
            drawSupplyDemand(player);
        } else if (button == 5) {
            drawPrice(player);
        } else if (button == 6) {
            drawOrderScreen(player);
        } else if (button == 7) {
            drawOrderScreen(player);
        } else if (button == 8) {

        }
    }

    public static void drawPlanetMenu(player player) {
        StdDraw.clear(Color.BLACK);
        planet planet = player.getPlanet();
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
        StdDraw.text(WIDTH * 0.0833, HEIGHT * 0.433, "Demand");
        StdDraw.rectangle(WIDTH * 0.0833, HEIGHT * 0.433, WIDTH * 0.0415, HEIGHT * 0.034);
        StdDraw.text(WIDTH * 0.166, HEIGHT * 0.433, "Price");
        StdDraw.rectangle(WIDTH * 0.166, HEIGHT * 0.433, WIDTH * 0.0415, HEIGHT * 0.034);


        StdDraw.setFont(SMALL);
        String[] goodNames = good.getGoodList();
        Double[] goodPrices = planet.getPriceList();
        for (int i = 0; i < goodNames.length; i++) {
            if (goodNames[i].equals(goodSelected)) {
                StdDraw.setPenColor(StdDraw.GREEN);
            }
            StdDraw.text(WIDTH * 0.42,  HEIGHT / (goodNames.length + 1) * (i + 1), goodNames[i]);
            StdDraw.text(WIDTH * .50,  HEIGHT / (goodNames.length + 1) * (i + 1), Double.toString(Utils.round(goodPrices[i], 1)));
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.line(WIDTH * 0.333,  HEIGHT / (goodNames.length + 1) * (i + 1.5),  WIDTH * 0.51,  HEIGHT / (goodNames.length + 1) * (i + 1.5));
        }
        StdDraw.line(WIDTH * 0.333,  HEIGHT / (goodNames.length + 1) * (0.5),  WIDTH * 0.51,  HEIGHT / (goodNames.length + 1) * (0.5));
        StdDraw.line( WIDTH * 0.51,  HEIGHT / (goodNames.length) * 0.5,  WIDTH * 0.51, HEIGHT -  HEIGHT / (goodNames.length) * 0.5);
        StdDraw.rectangle( WIDTH * 0.95,  HEIGHT * 0.95, WIDTH * 0.04, HEIGHT * 0.04);

        if (!goodSelected.isEmpty()) {
            StdDraw.text(WIDTH * 0.588, HEIGHT * 0.567, "Make Buy Order");
            StdDraw.rectangle(WIDTH * 0.588, HEIGHT * 0.567, WIDTH * 0.0415, HEIGHT * 0.034);
            StdDraw.text(WIDTH * 0.588, HEIGHT * 0.5, "Make Sell Order");
            StdDraw.rectangle(WIDTH * 0.588, HEIGHT * 0.5, WIDTH * 0.0415, HEIGHT * 0.034);
            StdDraw.text(WIDTH * 0.588, HEIGHT * 0.433, "Build Building");
            StdDraw.rectangle(WIDTH * 0.588, HEIGHT * 0.433, WIDTH * 0.0415, HEIGHT * 0.034);
        }


        StdDraw.setFont(MED);
        StdDraw.text( WIDTH / 100 * 95,  HEIGHT / 100 * 95, "Next Turn!");

        StdDraw.show();
    }

    public static void drawGoods(player player) {
        String name = goodSelected;
        StdDraw.clear(Color.BLACK);
        drawPlanetMenu(player);
        planet planet = player.getPlanet();
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
    public static void createGraph(int[] buyPairs, int[] sellPairs, double startX, double startY, double maxX, double maxY, double maxValue) {
        StdDraw.line(WIDTH * startX, HEIGHT * startY, WIDTH * (startX + 0.2), HEIGHT * startY);
        StdDraw.line(WIDTH * startX, HEIGHT * startY, WIDTH * startX, HEIGHT * (startY + 0.2));
        StdDraw.text(WIDTH * 0.73, HEIGHT * 0.6, "Price");
        StdDraw.text(WIDTH * 0.85, HEIGHT * 0.46, "Amount");
        StdDraw.setPenColor(Color.GREEN);
        for (int i = 0; i < buyPairs.length; i++) {
            int a = buyPairs[i];
            if (a == 0) {
                continue;
            }
            double x = startX + ((double) a / maxValue) * maxX;
            double y = startY + ((double) i / buyPairs.length * precision) * maxY;
            StdDraw.circle(x * WIDTH, y * HEIGHT, 0.02);
        }
        StdDraw.setPenColor(Color.RED);
        for (int i = 0; i < sellPairs.length; i++) {
            int a = sellPairs[i];
            if (a == 0) {
                continue;
            }
            double x = startX + ((double) a / maxValue) * maxX;
            double y = startY + ((double) i / sellPairs.length * precision) * maxY;
            StdDraw.circle(x * WIDTH, y * HEIGHT, 0.02);
        }
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(WIDTH * (startX + maxX), HEIGHT * (startY - 0.02), String.valueOf(maxValue));
        StdDraw.text(WIDTH * (startX - 0.01), HEIGHT * (startY + maxY), String.valueOf((sellPairs.length - precision) * precision));
    }
    public static void drawGraph(player player) {
        String name = goodSelected;
        StdDraw.clear(Color.BLACK);
        drawPlanetMenu(player);
        StdDraw.setFont(MED);
        planet planet = player.getPlanet();

        int maxValue = 0;
        for (order x: planet.getBuyOrders(name)) {
            if (x.getLimitPrice() > maxValue) {
                maxValue = (int) Math.round(x.getLimitPrice());
            }
        }
        //Used to make (price, amount) graph
        int[] buyPairs = new int[precision * (maxValue + 1)]; //Represents mapping price -> amount
        int[] sellPairs = new int[precision * (maxValue + 1)];

        for (order x: planet.getBuyOrders(name)) {
            buyPairs[(int) Utils.round(x.getLimitPrice(), 1 / precision)] += 1;
        }
        for (order x: planet.getSellOrders(name)) {
            sellPairs[(int) Utils.round(x.getLimitPrice(), 1 / precision)] += 1;
        }
        createGraph(buyPairs, sellPairs, 0.75, 0.5, 0.2, 0.2, maxValue);
        StdDraw.show();
    }

    public static void drawPrice(player player) {
        String name = goodSelected;
        StdDraw.clear(Color.BLACK);
        drawPlanetMenu(player);
        planet planet = player.getPlanet();
        StdDraw.setFont(MED);
        StdDraw.text(WIDTH * 0.75, HEIGHT * 0.5, String.valueOf(planet.getBasePrice(name)));

        StdDraw.show();
    }

    public static void drawCompanies(player player) {
        String name = goodSelected;
        StdDraw.clear(Color.BLACK);
        drawPlanetMenu(player);
        planet planet = player.getPlanet();
        company[] companies = planet.getCompanies();
        int size = companies.length;
        for (int i = 0; i < size; i++) {
            company x = companies[i];
            for (String k: x.getRecipe().getInputName()) {
                if (k.equals(name)) {
                    StdDraw.text(WIDTH * 0.8, HEIGHT / (size + 1) * (i + 1), x.toString());
                }
            }
            for (String k: x.getRecipe().getOutputName()) {
                if (k.equals(name)) {
                    StdDraw.text(WIDTH * 0.8, HEIGHT / (size + 1) * (i + 1), x.toString());
                }
            }

        }

        StdDraw.show();
    }

    public static void drawSupplyDemand(player player) {
        String name = goodSelected;
        StdDraw.clear(Color.BLACK);
        drawPlanetMenu(player);
        planet planet = player.getPlanet();
        int demand = 0;
        int supply = 0;
        for (company x: planet.getCompanies()) {
            String[] Recipe = x.getRecipe().getInputName();
            for (int i = 0; i < Recipe.length; i++) {
                String k = Recipe[i];
                if (k.equals(name)) {
                    demand += x.getRecipe().getInputAmount()[i];
                }
            }
            Recipe = x.getRecipe().getOutputName();
            for (int i = 0; i < Recipe.length; i++) {
                String k = Recipe[i];
                if (k.equals(name)) {
                    supply += x.getRecipe().getOutputAmount()[i];
                }
            }
        }
        StdDraw.setFont(MED);
        StdDraw.text(WIDTH * 0.75, HEIGHT * 0.45, "Demand: " + demand);
        StdDraw.text(WIDTH * 0.75, HEIGHT * 0.55, "Supply: " + supply);
        StdDraw.show();
    }

    public static void drawOrderScreen(player player) {
        String name = goodSelected;
        drawPlanetMenu(player);
        if (name.isEmpty()) {
            return;
        }
        StdDraw.text(0.833 * WIDTH, 0.59 * HEIGHT, "Amount");
        StdDraw.rectangle(0.833 * WIDTH, 0.55 * HEIGHT, 0.133 * WIDTH, 0.025 * HEIGHT);
        StdDraw.text(0.833 * WIDTH, 0.49 * HEIGHT, "Limit Price");
        StdDraw.rectangle(0.833 * WIDTH, 0.45 * HEIGHT, 0.133 * WIDTH, 0.025 * HEIGHT);

        StdDraw.rectangle(0.833 * WIDTH, 0.35 * HEIGHT, 0.03 * WIDTH, 0.025 * HEIGHT);
        StdDraw.text(0.833 * WIDTH, 0.35 * HEIGHT, "Confirm");

        updateTextbox(player);
        StdDraw.show();
    }
    public static void updateTextbox(player player) {
        StdDraw.text(0.833 * WIDTH, 0.55 * HEIGHT, textboxAmount);
        StdDraw.text(0.833 * WIDTH, 0.45 * HEIGHT, textboxPrice);
    }


    public static void drawTextbox(player player, char text, int whichOne) {
        if (whichOne == 1) {
            if ((int) text == 8 && !textboxAmount.isEmpty()) {
                textboxAmount = textboxAmount.substring(0, textboxAmount.length() - 1);
            } else if ((int) text >= 48 && (int) text <= 57) {
                textboxAmount += text;
            } else {
                return;
            }
        } else {
            if ((int) text == 8 && !textboxPrice.isEmpty()) {
                textboxPrice = textboxPrice.substring(0, textboxPrice.length() - 1);
            } else if ((int) text >= 48 && (int) text <= 57) {
                textboxPrice += text;
            } else if (text == 46 && !textboxPrice.contains(".")) {
                textboxPrice += text;
            } else {
                return;
            }
        }

        drawPlanetMenu(player);
        drawOrderScreen(player);
        StdDraw.text(0.833 * WIDTH, 0.55 * HEIGHT, textboxAmount);
        StdDraw.text(0.833 * WIDTH, 0.45 * HEIGHT, textboxPrice);

        StdDraw.show();
    }

    public static void createOrder(player player) {
        if (textboxAmount.isEmpty() || textboxPrice.isEmpty()) {
            return;
        }
        boolean isBuyOrder = (button == 6);
        planet planet = player.getPlanet();
        String goodName = goodSelected;
        int amount = Integer.parseInt(textboxAmount);
        double price = Double.parseDouble(textboxPrice);
        order newOrder = new order(player, new good(goodName, amount), planet.getBasePrice(goodName), price, isBuyOrder);
        planet.addOrder(newOrder);
        textboxAmount = "";
        textboxPrice = "";
        drawOrderScreen(player);
        updateTextbox(player);
    }
}

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
    static final int precision = 2; // How many slots every dollar
    static String textbox1 = "";
    static String textbox2 = "";
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
    public static void drawStuff(player player) {
        if (!(button == 6 || button == 7)) {
            textbox1 = "";
            textbox2 = "";
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
        StdDraw.text(WIDTH * 0.0833, HEIGHT * 0.433, "Demand");
        StdDraw.rectangle(WIDTH * 0.0833, HEIGHT * 0.433, WIDTH * 0.0415, HEIGHT * 0.034);
        StdDraw.text(WIDTH * 0.166, HEIGHT * 0.433, "Price");
        StdDraw.rectangle(WIDTH * 0.166, HEIGHT * 0.433, WIDTH * 0.0415, HEIGHT * 0.034);


        StdDraw.setFont(SMALL);
        planet planet = economy.getPlanetFromID(planetID);
        String[] goodNames = planet.getGoodList();
        Double[] goodPrices = planet.getPriceList();
        for (int i = 0; i < goodNames.length; i++) {
            if (goodNames[i].equals(player.getGoodSelected())) {
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

        if (!player.getGoodSelected().isEmpty()) {
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
        String name = player.getGoodSelected();
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

    public static void drawGraph(player player) {
        String name = player.getGoodSelected();
        StdDraw.clear(Color.BLACK);
        drawPlanetMenu(player);
        StdDraw.setFont(MED);
        planet planet = economy.getPlanetFromID(player.getPlanet());
        double startX = 0.75;
        double diffX = 0.2;
        double startY = 0.5;
        double diffY = 0.2;
        StdDraw.line(WIDTH * startX, HEIGHT * startY, WIDTH * (startX + 0.2), HEIGHT * startY);
        StdDraw.line(WIDTH * startX, HEIGHT * startY, WIDTH * startX, HEIGHT * (startY + 0.2));
        StdDraw.text(WIDTH * 0.73, HEIGHT * 0.6, "Price");
        StdDraw.text(WIDTH * 0.85, HEIGHT * 0.46, "Amount");
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
        StdDraw.setPenColor(Color.GREEN);
        for (int i = 0; i < buyPairs.length; i++) {
            int a = buyPairs[i];
            if (a == 0) {
                continue;
            }
            double x = startX + ((double) a / maxValue) * diffX;
            double y = startY + ((double) i / buyPairs.length * precision) * diffY;
            StdDraw.circle(x * WIDTH, y * HEIGHT, 0.02);
        }
        StdDraw.setPenColor(Color.RED);
        for (int i = 0; i < sellPairs.length; i++) {
            int a = sellPairs[i];
            if (a == 0) {
                continue;
            }
            double x = startX + ((double) a / maxValue) * diffX;
            double y = startY + ((double) i / sellPairs.length * precision) * diffY;
            StdDraw.circle(x * WIDTH, y * HEIGHT, 0.02);
        }

        StdDraw.setPenColor(Color.WHITE);

        StdDraw.text(WIDTH * (startX + diffX), HEIGHT * (startY - 0.02), String.valueOf(maxValue));
        StdDraw.text(WIDTH * (startX - 0.01), HEIGHT * (startY + diffY), String.valueOf(sellPairs.length * precision));
        StdDraw.show();
    }

    public static void drawPrice(player player) {
        String name = player.getGoodSelected();
        StdDraw.clear(Color.BLACK);
        drawPlanetMenu(player);
        planet planet = economy.getPlanetFromID(player.getPlanet());
        StdDraw.setFont(MED);
        StdDraw.text(WIDTH * 0.75, HEIGHT * 0.5, String.valueOf(planet.getPriceSold(name)));

        StdDraw.show();
    }

    public static void drawCompanies(player player) {
        String name = player.getGoodSelected();
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

    public static void drawSupplyDemand(player player) {
        String name = player.getGoodSelected();
        StdDraw.clear(Color.BLACK);
        drawPlanetMenu(player);
        planet planet = economy.getPlanetFromID(player.getPlanet());
        int demand = 0;
        int supply = 0;
        for (company x: planet.getCompanies()) {
            String[] Recipe = x.getRecipe().getInput();
            for (int i = 0; i < Recipe.length; i++) {
                String k = Recipe[i];
                if (k.equals(name)) {
                    demand += x.getRecipe().getInputAmount()[i];
                }
            }
            Recipe = x.getRecipe().getOutput();
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
        planet planet = economy.getPlanetFromID(player.getPlanet());
        String name = player.getGoodSelected();
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

        drawTextbox(player, null, 1);
        StdDraw.show();
    }

    public static void drawTextbox(player player, Character text, int whichOne) {
        if (text == null) {
            StdDraw.text(0.833 * WIDTH, 0.55 * HEIGHT, textbox1);
            StdDraw.text(0.833 * WIDTH, 0.45 * HEIGHT, textbox2);
            return;
        }
        if (whichOne == 1) {
            if ((int) text == 8 && !textbox1.isEmpty()) {
                textbox1 = textbox1.substring(0, textbox1.length() - 1);
            } else if ((int) text >= 48 && (int) text <= 57) {
                textbox1 += text;
            } else {
                return;
            }
        } else {
            if ((int) text == 8 && !textbox2.isEmpty()) {
                textbox2 = textbox2.substring(0, textbox2.length() - 1);
            } else if ((int) text >= 48 && (int) text <= 57) {
                textbox2 += text;
            } else if (text == 46 && !textbox2.contains(".")) {
                textbox2 += text;
            } else {
                return;
            }
        }

        drawPlanetMenu(player);
        drawOrderScreen(player);
        StdDraw.text(0.833 * WIDTH, 0.55 * HEIGHT, textbox1);
        StdDraw.text(0.833 * WIDTH, 0.45 * HEIGHT, textbox2);

        StdDraw.show();
    }

    public static void createOrder(player player) {
        boolean isBuyOrder = (button == 6);
        planet planet = economy.getPlanetFromID(player.getPlanet());
        String goodName = player.getGoodSelected();
        int amount = Integer.parseInt(textbox1);
        double price = Double.parseDouble(textbox2);
        order newOrder = new order(player, new good(goodName, amount), planet.getBasePrice(goodName), price, isBuyOrder);
        textbox1 = "";
        textbox2 = "";
    }
}

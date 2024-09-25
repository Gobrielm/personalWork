package core;

import core.playerPackage.playerCompany;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;

import static core.good.getRecipesWithGood;

public class graphicalInterface {
    static final double WIDTH = 60;
    static final double HEIGHT = 30;
    static final int CANVASSIZE = 30;
    static final Font SMALL = new Font("Monaco", Font.BOLD, 15);
    static final Font MED = new Font("Monaco", Font.BOLD, 20);
    static final Font BIG = new Font("Monaco", Font.BOLD, 30);
    static final int precision = 2; // How many slots every dollar
    static final boolean testing = false;
    static int typing = 0;
    static String textboxAmount = "";
    static String textboxPrice = "";
    static String goodSelected = "";
    static int planetSelected = 0;
    static recipe selected = null;
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
    public static boolean changeGoodSelected(player player, String goodName) {
        if (graphicalInterface.goodSelected.equals(goodName)) {
            return false;
        }
        graphicalInterface.goodSelected = goodName;
        updateScreen(player);
        return true;
    }
    public static boolean changeButton(int button) {
        if (button <= 0 || graphicalInterface.button == button) {
            return false;
        }
        graphicalInterface.button = button;
        return true;
    }
    public static void resetStrings() {
        textboxAmount = "";
        textboxPrice = "";
        goodSelected = "";
        typing = 0;
        selected = null;
        planetSelected = 0;
    }
    public static boolean keyBoardInput(player player, char input) {

        if (input == 48) {
            //End Turn
            return true;
        } else if (input >= 49 && input <= 57) {
            //Is 0-9 int
            input -= 48;
            changeButton(input);
        }
        updateScreen(player);
        return false;
    }
    public static void selectPlanet(player player, int num) {
        planetSelected = num;
        updateScreen(player);
    }
    private static void appendCharToTextbox(char input) {
        //Checking if done
        if ((int) input == 27 || (int) input == 10) {
            typing = 0;
        }
        if (typing == 1) {
            if ((int) input == 8 && !textboxAmount.isEmpty()) {
                textboxAmount = textboxAmount.substring(0, textboxAmount.length() - 1);
            } else if ((int) input >= 48 && (int) input <= 57) {
                textboxAmount += input;
            }
        } else {
            if ((int) input == 8 && !textboxPrice.isEmpty()) {
                textboxPrice = textboxPrice.substring(0, textboxPrice.length() - 1);
            } else if ((int) input >= 48 && (int) input <= 57) {
                textboxPrice += input;
            } else if (input == 46 && !textboxPrice.contains(".")) {
                textboxPrice += input;
            }
        }
    }
    public static void updateScreen(player player) {
        drawPlanetMenu(player);
        showButton(player);
        StdDraw.show();
    }
    private static void showButton(player player) {
        if (testing) {
            if (button == 1) {
                drawGraph(player);
            } else if (button == 2) {
                drawGoods(player);
            } else if (button == 3) {
                drawCompanies(player);
            } else if (button == 4) {
                drawSupplyDemand(player);
            } else if (button == 5) {
            } else if (button == 6) {
//           drawStockScreen(player);
            } else if (button == 7 || button == 8) {
                drawOrderScreen(player);
            } else if (button == 9) {
                drawBuildScreen(player);
            }
        } else {
            if (button == 1) {
                drawGraph(player);
            } else if (button == 2) {
                drawFactories(player);
            } else if (button == 3) {

            } else if (button == 4) {

            } else if (button == 5) {

            } else if (button == 6) {

            } else if (button == 7 || button == 8) {

            } else if (button == 9) {

            }
        }

    }

    private static void drawMedButton(double x, double y, String text) {
        double width = 0.0415;
        double height = 0.034;
        StdDraw.text(WIDTH * x, HEIGHT * y, text);
        StdDraw.rectangle(WIDTH * x, HEIGHT * y, WIDTH * width, HEIGHT * height);
    }
    private static void drawLongSmallButton(double x, double y, String text, boolean green) {
        double width = 0.05;
        double height = (double) 1 / (2 * (good.getGoodArray().length + 1));
        if (green) {
            StdDraw.setPenColor(StdDraw.GREEN);
        }
        StdDraw.text(WIDTH * x, HEIGHT * y, text);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.rectangle(WIDTH * x, HEIGHT * y, WIDTH * width, HEIGHT * height);
    }
    private static void drawSmallButton(double x, double y, String text, boolean green) {
        if (green) {
            drawSmallButton(x, y, text, Color.GREEN);
        } else {
            drawSmallButton(x, y, text, Color.WHITE);
        }

    }
    private static void drawSmallButton(double x, double y, String text, Color color) {
        double width = 0.01;
        double height = (double) 1 / (2 * (good.getGoodArray().length + 1));
        StdDraw.setPenColor(color);
        StdDraw.text(WIDTH * x, HEIGHT * y, text);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.rectangle(WIDTH * x, HEIGHT * y, WIDTH * width, HEIGHT * height);
    }
    private static void drawPlanetMenu(player player) {
        StdDraw.clear(Color.BLACK);
        StdDraw.line(WIDTH * 0.333, 0, WIDTH * 0.333, HEIGHT);
        StdDraw.line(WIDTH * 0.666, 0, WIDTH * 0.666, HEIGHT);
        StdDraw.setFont(BIG);

        StdDraw.text(WIDTH * 0.166, HEIGHT * 0.9, player.getName());
        StdDraw.text(WIDTH * 0.166, HEIGHT * 0.8, "Cash: " + (int) Math.round(player.getCash()));
        StdDraw.text(WIDTH * 0.166, HEIGHT * 0.7, "Planet: " + (player.getPlanetId() + 1));

        StdDraw.setFont(MED);

        drawBasicButtons();

        drawPlanetSelector();

        drawAllGoodNames(player);

        drawBuySellBuildButtons();

        drawEndTurnButton();
    }
    private static void drawBasicButtons() {
        if (testing) {
            drawMedButton(0.0833, 0.5, "Graph" + "(" + 1 + ")");
            drawMedButton(0.166, 0.5, "Goods" + "(" + 2 + ")");
            drawMedButton(0.249, 0.5, "Companies" + "(" + 3 + ")");
            drawMedButton(0.0833, 0.433,  "Demand" + "(" + 4 + ")");
            drawMedButton(0.166, 0.433, "Filler" + "(" + 5 + ")");
            drawMedButton(0.249, 0.433, "Stock" + "(" + 6 + ")");
        } else {
            drawMedButton(0.0833, 0.5, "Graph" + "(" + 1 + ")");
            drawMedButton(0.166, 0.5, "ShowFactories" + "(" + 2 + ")");
        }

    }
    private static void drawPlanetSelector() {
        StdDraw.text(WIDTH * 0.166, HEIGHT * 0.3, "Travel Planets");
        double amountX = 0.0;
        double amountY = 0.0;
        for (int i = 1; i < 10; i++) {
            boolean green = false;
            if (planetSelected == i) {
                green = true;
            }
            drawSmallButton(0.146 + amountX, 0.25 + amountY, String.valueOf(i), green);
            amountX += 0.02;
            if (amountX % 0.06 == 0) {
                amountX = 0;
                amountY -= 0.041667;
            }
        }
    }
    private static void drawAllGoodNames(player player) {
        StdDraw.setFont(SMALL);
        planet thisPlanet = player.getPlanet();
        String[] goodNames = good.getGoodArray();
        Double[] goodPrices = thisPlanet.getPriceList();
        int num = (goodNames.length + 1);
        for (int i = 0; i < goodNames.length; i++) {
            boolean green = false;
            if (goodNames[i].equals(goodSelected)) {
                green = true;
            }
            drawLongSmallButton(0.38333, (double) 1 / num * (i + 1), goodNames[i], green);
            Color color = Color.WHITE;
            double val = Utils.round(goodPrices[i], 1);
            color = thisPlanet.getPriceBigger(goodNames[i]) ? Color.RED: color;
            color = thisPlanet.getPriceSmaller(goodNames[i]) ? Color.GREEN: color;
            drawSmallButton(0.44333, (double) 1 / num * (i + 1), String.valueOf(val), color);
            drawSmallButton(0.46333, (double) 1 / num * (i + 1), String.valueOf(player.getAmount(goodNames[i])), false);
        }
        StdDraw.setFont(MED);
    }
    private static void drawBuySellBuildButtons() {
        if (!goodSelected.isEmpty()) {
            drawMedButton(0.6, 0.567, "Make Buy Order" + "(b)");
            drawMedButton(0.6, 0.5, "Make Sell Order" + "(s)");
            drawMedButton(0.6, 0.433, "Build Factory" + "(f)");
        }
    }
    private static void drawEndTurnButton() {
        StdDraw.rectangle( WIDTH * 0.95,  HEIGHT * 0.95, WIDTH * 0.04, HEIGHT * 0.04);
        StdDraw.text( WIDTH / 100 * 95,  HEIGHT / 100 * 95, "Next Turn!" + "(" + 0 + ")");
    }
    private static void createGraph(Double[] values, double startX, double startY, double maxX, double maxY) {
        if (values.length == 0) {
            return;
        }
        StdDraw.line(WIDTH * startX, HEIGHT * startY, WIDTH * (startX + 0.2), HEIGHT * startY);
        StdDraw.line(WIDTH * startX, HEIGHT * startY, WIDTH * startX, HEIGHT * (startY + 0.2));
        StdDraw.text(WIDTH * 0.73, HEIGHT * 0.6, "Price");
        StdDraw.text(WIDTH * 0.85, HEIGHT * 0.46, "Weeks");
        double maxValue = values[0];
        double minValue = values[0];

        for (double x : values) {
            maxValue = Math.max(maxValue, x);
            minValue = Math.min(minValue, x);
        }

        for (int i = 0; i < values.length; i++) {
            double currValue = values[i];
            double normalizedValue = (currValue - minValue) / (maxValue - minValue);
            double x = (startX + maxX * ((double) i / values.length)) * WIDTH;
            double y = (startY + normalizedValue * maxY) * HEIGHT;

            if (i != 0) {
                double prevValue = values[i - 1];
                double normalizedPrevValue = (prevValue - minValue) / (maxValue - minValue);

                double x0 = (startX + maxX * ((double) (i - 1) / values.length)) * WIDTH;
                double y0 = (startY + normalizedPrevValue * maxY) * HEIGHT;
                StdDraw.line(x0, y0, x, y);
            }
        }


        StdDraw.text(WIDTH * (startX - 0.01), HEIGHT * (startY + maxY), String.valueOf(maxValue));
        StdDraw.text(WIDTH * (startX - 0.01), HEIGHT * (startY), String.valueOf(minValue));
    }
    private static void drawGraph(player player) {
        String goodName = goodSelected;
        StdDraw.setFont(MED);
        planet planet = player.getPlanet();
        Double [] toGraph = planet.getLastPriceArray(goodName, 50);

        createGraph(toGraph, 0.75, 0.5, 0.2, 0.2);
    }
    private static void drawFactories(player player) {
        playerCompany[] ownedFactories = player.getCompaniesByGoodName(goodSelected);
        int size = ownedFactories.length;
        for (int i = 0; i < size; i++) {
            playerCompany companyToPrint = ownedFactories[i];
            StdDraw.text(0.8325 * WIDTH, (double) (i + 1) / (size + 1) * HEIGHT, companyToPrint.toString());
        }
    }
    private static void drawOrderScreen(player player) {
        String name = goodSelected;
        if (name.isEmpty()) {
            return;
        }
        StdDraw.text(0.833 * WIDTH, 0.59 * HEIGHT, "Amount" + "(" + "y" + ")");
        StdDraw.rectangle(0.833 * WIDTH, 0.55 * HEIGHT, 0.133 * WIDTH, 0.025 * HEIGHT);
        StdDraw.text(0.833 * WIDTH, 0.49 * HEIGHT, "Limit Price" + "(" + "z" + ")");
        StdDraw.rectangle(0.833 * WIDTH, 0.45 * HEIGHT, 0.133 * WIDTH, 0.025 * HEIGHT);

        StdDraw.rectangle(0.833 * WIDTH, 0.35 * HEIGHT, 0.03 * WIDTH, 0.025 * HEIGHT);
        StdDraw.text(0.833 * WIDTH, 0.35 * HEIGHT, "Confirm" + "(" + "9" + ")");

        updateTextbox();
    }
    public static void updateTextbox() {
        StdDraw.text(0.833 * WIDTH, 0.55 * HEIGHT, textboxAmount);
        StdDraw.text(0.833 * WIDTH, 0.45 * HEIGHT, textboxPrice);
    }

    private static void createOrder(player player) {
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
    }

    private static void drawBuildScreen(player player) {
        StdDraw.rectangle(0.8325 * WIDTH, 0.6 * HEIGHT, 0.14 * WIDTH, 0.2 * HEIGHT);
        StdDraw.rectangle(0.833 * WIDTH, 0.35 * HEIGHT, 0.03 * WIDTH, 0.025 * HEIGHT);
        StdDraw.text(0.833 * WIDTH, 0.35 * HEIGHT, "Confirm");

        recipe[] toDisplay = getRecipesWithGood(goodSelected);
        int n = toDisplay.length;
        for (int i = 0; i < n; i++) {
            double y = 0.75 - (0.35 * (i) / (n));
            StdDraw.text(0.8325 * WIDTH, y * HEIGHT, toDisplay[i].toString());
        }
    }


    //Note: ALL for Testing
    private static void drawGoods(player player) {
        String name = goodSelected;
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
    }

    private static void drawCompanies(player player) {
        String name = goodSelected;
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

    }

    private static void drawSupplyDemand(player player) {
        String goodName = goodSelected;
        planet planet = player.getPlanet();
        int demand = planet.getDemand(goodName);
        int supply = planet.getSupply(goodName);
        StdDraw.setFont(MED);
        StdDraw.text(WIDTH * 0.75, HEIGHT * 0.45, "Demand: " + demand);
        StdDraw.text(WIDTH * 0.75, HEIGHT * 0.55, "Supply: " + supply);
    }
}

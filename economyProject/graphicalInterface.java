package core;

import core.graphics.button;
import core.graphics.rectangle;
import core.playerPackage.playerCompany;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.util.HashSet;

import static core.good.getRecipesWithGood;
import core.graphics.buttonManager;
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
    static boolean isTyping = false;
    static String textboxAmount = "";
    static String textboxPrice = "";
    static String goodSelected = "";
    static int planetSelected = 0;
    static recipe selected = null;
    static int button;

    static button basicButton;

    public static void startGame() {
        StdDraw.setFont(SMALL);
        StdDraw.setCanvasSize((int) (WIDTH * CANVASSIZE), (int) (HEIGHT * CANVASSIZE));
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.enableDoubleBuffering();
        buttonManager.initialize();
        basicButton = null;
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
    public static boolean changeButton(int newButton) {
        if (newButton <= 0 || button == newButton) {
            return false;
        }
        exitTypingMode();
        button = newButton;
        return true;
    }
    public static void resetStrings() {
        textboxAmount = "";
        textboxPrice = "";
        typing = 0;
        selected = null;
        planetSelected = 0;
    }
    public static boolean mouseInput(player player, double mouseX, double mouseY) {
        button clicked = buttonManager.getButtonClicked(mouseX, mouseY);
        if (clicked == null) {
            exitTypingMode();
            return false;
        }
        buttonManager.changeSelected(clicked);
        switch (clicked.getField()) {
            case "Basic" -> {
                int id = clicked.getId();
                changeButton(id);
            }
            case "End" -> {
                return true;
            }
            case "GoodName" -> changeGoodSelected(player, clicked.getName());

            case "Order" -> {
                int id = clicked.getId();
                clickOnOrderScreen(player, id);
            }
        }
        updateScreen(player);
        return false;
    }

    private static void clickOnOrderScreen(player player, int id) {
        if (id == 3) {
            createOrder(player);
            exitTypingMode();
            return;
        }
        enterTypingMode();
        typing = id;
    }
    public static boolean keyBoardInput(player player, char input) {
        if (!isTyping) {
            if (input == 48) {
                //End Turn
                return true;
            } else if (input >= 49 && input <= 57) {
                //Is 0-9 int
                input -= 48;
                changeButton(input);
            } else if (input == 115) {
                changeButton(7);
            } else if (input == 98) {
                changeButton(8);
            } else if (input == 102) {
                changeButton(9);
            }
        } else {
            keyBoardInputTypingTrue(input);
        }
        updateScreen(player);
        return false;
    }
    private static void keyBoardInputTypingTrue(char input) {
        appendCharToTextbox(input);
    }
    public static void selectPlanet(player player, int num) {
        planetSelected = num;
        updateScreen(player);
    }
    private static void appendCharToTextbox(char input) {
        //Checking if done
        if ((int) input == 27 || (int) input == 10) {
            exitTypingMode();
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
        drawButtonsFromArray();
        updateTextbox();
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
                drawOrderScreen();
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
                drawOrderScreen();
            } else if (button == 9) {
                drawBuildScreen(player);
            }
        }

    }
    private static void drawButtonsFromArray() {
        button[][] screen = buttonManager.getArray();
        HashSet<button> created = new HashSet<>();
        for (button[] row: screen) {
            for (button currButton: row) {
                if (created.contains(currButton)) {
                    continue;
                }
                if (drawButton(currButton)) {
                    created.add(currButton);
                }
            }
        }
    }
    private static boolean drawButton(button currButton) {
        if (currButton == null) {
            return false;
        } else if (currButton.getField().equals("Order")) {
            return drawOrderButton(currButton);
        }
        double x = currButton.getX();
        double y = currButton.getY();
        double width = currButton.getWidthX();
        double height = currButton.getWidthY();
        String text = currButton.getName();
        StdDraw.setFont(currButton.getFont());
        StdDraw.setPenColor(currButton.getColor());
        StdDraw.text(WIDTH * x, HEIGHT * y, text);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.rectangle(WIDTH * x, HEIGHT * y, WIDTH * width, HEIGHT * height);
        return true;
    }
    private static boolean drawOrderButton(button currButton) {
        if (button == 7 || button == 8) {
            double x = currButton.getX();
            double y = currButton.getY();
            int id = currButton.getId();
            double yOffset = (id == 3) ? 0.0: 0.04;
            double width = currButton.getWidthX();
            double height = currButton.getWidthY();
            String text = currButton.getName();
            StdDraw.setFont(currButton.getFont());
            StdDraw.setPenColor(currButton.getColor());
            StdDraw.text(WIDTH * x, HEIGHT * (y + yOffset), text);
            StdDraw.setPenColor(Color.WHITE);
            StdDraw.rectangle(WIDTH * x, HEIGHT * y, WIDTH * width, HEIGHT * height);
            return true;
        }
        return false;
    }
    private static void createEndButton() {
        addButtonToManger(0.95, 0.95, 0.04, 0.04, "EndTurn" + "(" + 0 + ")", "End", MED, 0);
    }
    private static void createBasicButton(double x, double y, String name, int id) {
        double width = 0.0415;
        double height = 0.034;
        addButtonToManger(x, y, width, height, name, "Basic", MED, id);
    }
    private static void createOrderButton(double x, double y, double width, double height, String name, int id) {

        addButtonToManger(x, y, width, height, name, "Order", MED, id);
    }
    private static void createLongSmallButton(double x, double y, String name) {
        double width = 0.05;
        double height = (double) 1 / (2 * (good.getGoodArray().length + 1));
        addButtonToManger(x, y, width, height, name, "GoodName", MED, 0);
    }
    private static void createSmallButton(double x, double y, String name, String field) {
        double width = 0.01;
        double height = (double) 1 / (2 * (good.getGoodArray().length + 1));
        addButtonToManger(x, y, width, height, name, field, SMALL, 0);
    }
    private static void changeSmallButton(double x, double y, String name, Color color) {
        double width = 0.01;
        double height = (double) 1 / (2 * (good.getGoodArray().length + 1));
        buttonManager.changeTextOnScreen(x, y, width, height, name, color);
    }
    private static void addButtonToManger(double x, double y, double widthX, double widthY, String name, String field, Font font, int id) {
        rectangle newRectangle = new rectangle(x, y, widthX, widthY);
        button newButton = new button(newRectangle, name, field, font, id);
        buttonManager.addButton(newButton);
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

        drawAllGoodNames(player);
    }
    //Note: Creation of all buttons
    public static void createButtons() {
        createBasicButtons();
        createEndButton();

        planet thisPlanet = economy.getPlanetFromID(0);
        String[] goodNames = good.getGoodArray();
        Double[] goodPrices = thisPlanet.getPriceList();
        int num = (goodNames.length + 1);
        for (int i = 0; i < goodNames.length; i++) {
            createLongSmallButton(0.38333, (double) 1 / num * (i + 1), goodNames[i]);
            double val = Utils.round(goodPrices[i], 1);
            createSmallButton(0.44333, (double) 1 / num * (i + 1), String.valueOf(val), "goodPrice");
            createSmallButton(0.46333, (double) 1 / num * (i + 1), "0", "goodAmount");
        }
        createOrderScreenButtons();
    }
    private static void createBasicButtons() {
        if (testing) {
            createBasicButton(0.0833, 0.5, "Graph" + "(" + 1 + ")", 1);
            createBasicButton(0.166, 0.5, "Goods" + "(" + 2 + ")", 2);
            createBasicButton(0.249, 0.5, "Companies" + "(" + 3 + ")", 3);
            createBasicButton(0.0833, 0.433,  "Demand" + "(" + 4 + ")", 4);
            createBasicButton(0.166, 0.433, "Filler" + "(" + 5 + ")", 5);
            createBasicButton(0.249, 0.433, "Stock" + "(" + 6 + ")", 6);
        } else {
            createBasicButton(0.0833, 0.5, "Graph" + "(" + 1 + ")", 1);
            createBasicButton(0.166, 0.5, "ShowFactories" + "(" + 2 + ")", 2);
        }
        createBasicButton(0.6, 0.567, "Make Buy Order" + "(7)", 7);
        createBasicButton(0.6, 0.5, "Make Sell Order" + "(8)", 8);
        createBasicButton(0.6, 0.433, "Build Factory" + "(9)", 9);
    }
    private static void createOrderScreenButtons() {
        createOrderButton(0.833, 0.55, 0.133, 0.025, "Amount", 1);
        createOrderButton(0.833, 0.45, 0.133, 0.025, "LimitPrice", 2);
        createOrderButton(0.833, 0.35, 0.03, 0.025, "Confirm", 3);
    }
    private static void drawAllGoodNames(player player) {
        StdDraw.setFont(SMALL);
        planet thisPlanet = player.getPlanet();
        String[] goodNames = good.getGoodArray();
        Double[] goodPrices = thisPlanet.getPriceList();
        int num = (goodNames.length + 1);
        for (int i = 0; i < goodNames.length; i++) {
            Color color = Color.WHITE;
            double val = Utils.round(goodPrices[i], 1);
            color = thisPlanet.getPriceBigger(goodNames[i]) ? Color.RED: color;
            color = thisPlanet.getPriceSmaller(goodNames[i]) ? Color.GREEN: color;
            changeSmallButton(0.44333, (double) 1 / num * (i + 1), String.valueOf(val), color);
            changeSmallButton(0.46333, (double) 1 / num * (i + 1), String.valueOf(player.getAmount(goodNames[i])), null);
        }
        StdDraw.setFont(MED);
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
    private static void drawOrderScreen() {
        String name = goodSelected;
        if (name.isEmpty()) {
            return;
        }

        updateTextbox();
    }
    private static void enterTypingMode() {
        isTyping = true;
    }
    private static void exitTypingMode() {
        isTyping = false;
    }
    public static void updateTextbox() {
        if (button == 7 || button == 8) {
            StdDraw.text(0.833 * WIDTH, 0.55 * HEIGHT, textboxAmount);
            StdDraw.text(0.833 * WIDTH, 0.45 * HEIGHT, textboxPrice);
        }
    }

    private static void createOrder(player player) {
        if (textboxAmount.isEmpty() || textboxPrice.isEmpty() || goodSelected.isEmpty()) {
            return;
        }
        boolean isBuyOrder = (button == 7);
        planet planet = player.getPlanet();
        String goodName = goodSelected;
        int amount = Integer.parseInt(textboxAmount);
        double price = Double.parseDouble(textboxPrice);
        if (!isBuyOrder && amount > player.getAmount(goodName)) {
            return;
        }
        order newOrder = new order(player, new good(goodName, amount), planet.getBasePrice(goodName), price, isBuyOrder);
        planet.addOrder(newOrder);
        textboxAmount = "";
        textboxPrice = "";
    }

    private static void drawBuildScreen(player player) {
        StdDraw.rectangle(0.8325 * WIDTH, 0.6 * HEIGHT, 0.14 * WIDTH, 0.2 * HEIGHT);
        StdDraw.rectangle(0.833 * WIDTH, 0.35 * HEIGHT, 0.03 * WIDTH, 0.025 * HEIGHT);
        StdDraw.text(0.833 * WIDTH, 0.35 * HEIGHT, "Confirm");
        if (goodSelected == null || goodSelected.isEmpty()) {
            return;
        }
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

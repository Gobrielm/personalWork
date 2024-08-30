package core;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public class company {
    private String name;
    private double cash;
    private recipe recipe;
    private int order;
    private double[] incomeWeeks;
    private static long seed;
    private static Random rand;
    private planet planet;
    private HashMap<String, Double[]> lastBuyPrices;
    private HashMap<String, Integer> lastBuyPricesSize;
    private HashMap<String, Double[]> lastSellPrices;
    private HashMap<String, Integer> lastSellPricesSize;
    //Higher number is more aggressive
    private int personality;
    public company(String name, recipe recipe, planet planet) {
        this.name = name;
        this.recipe = new recipe(recipe.getInput(), recipe.getOutput(), recipe.getInputAmount(), recipe.getOutputAmount(), recipe.getExpenses(), recipe.getIncome());
        cash = 100;
        if (recipe.getInput() == null || Arrays.equals(recipe.getInput(), new String[]{})) {
            this.order = 1;
        } else if (recipe.getOutput() == null || Arrays.equals(recipe.getOutput(), new String[]{})) {
            this.order = 3;
        } else {
            this.order = 2;
        }
        lastBuyPricesSize = new HashMap<>();
        lastSellPricesSize = new HashMap<>();
        for (String x: good.getGoodList()) {
            lastBuyPricesSize.put(x, 0);
            lastSellPricesSize.put(x, 0);
        }
        lastSellPrices = new HashMap<>();
        lastBuyPrices = new HashMap<>();
        Random rand = new Random(seed);
        incomeWeeks = new double[20];
        this.personality = rand.nextInt(1, 4);
        this.planet = planet;
    }
    public static void setSeed(long newSeed) {
        seed = newSeed;
        rand = new Random(seed);
    }
    public String getName() {
        return name;
    }
    public recipe getRecipe() {
        return recipe;
    }
    //order1 is the same as the company
    public boolean checkDeal(order order1, order order2) {
        double priceDiff = order1.getPrice() - order2.getPrice();
        double price = order1.getPrice();
        double change = Math.abs(priceDiff / price);
        return change < 0.01 * personality;
    }

    public void buyGood(int amount, String good, double price) {
        recipe.changeInput(good, amount);
        addLastBoughtPrice(good, price);
    }
    private void addLastBoughtPrice(String name, double price) {
        int size = lastBuyPricesSize.get(name);
        if (size == 0) {
            lastBuyPrices.put(name, new Double[5]);
        }
        lastBuyPrices.get(name)[size] = price;
        if (size < lastBuyPrices.get(name).length - 1) {
            lastBuyPricesSize.put(name, size + 1);
        }
    }
    private double getExpectBuyPrice(String name) {
        int temp = lastBuyPricesSize.get(name);
        double total = 0;
        for (int i = 0; i < temp; i++) {
            total += lastBuyPrices.get(name)[i];
        }
        if (total == 0) {
            return good.getBasePrice(name);
        }
        return total;
    }
    public double maxBuyPrice(String name) {
        double percentage = 1;
        double baseTotalBuy = 0;
        double baseTotalSell = 0;
        double expenses = recipe.getExpenses();
        for (good x: recipe.getInputGood()) {
            baseTotalBuy += x.getAmount() * good.getBasePrice(x.getName());
        }
        for (good x: recipe.getOutputGood()) {
            baseTotalSell += x.getAmount() * good.getBasePrice(x.getName());
        }
        while (expenses + baseTotalBuy * percentage < baseTotalSell) {
            percentage += 0.01;
        }
        return good.getBasePrice(name) * percentage;
    }
    private void addLastSoldPrice(String name, double price) {
        int size = lastSellPricesSize.get(name);
        if (size == 0) {
            lastSellPrices.put(name, new Double[5]);
        }
        lastSellPrices.get(name)[size] = price;
        if (size < lastSellPrices.get(name).length - 1) {
            lastSellPricesSize.put(name, size + 1);
        }
    }
    private double getExpectSellPrice(String name) {
        int temp = lastSellPricesSize.get(name);
        double total = 0;
        for (int i = 0; i < temp; i++) {
            total += lastSellPrices.get(name)[i];
        }
        if (total == 0) {
            return good.getBasePrice(name);
        }
        return total;
    }
    public double minSellPrice(String name) {
        double percentage = 1;
        double baseTotalBuy = 0;
        double baseTotalSell = 0;
        double expenses = recipe.getExpenses();
        for (good x: recipe.getInputGood()) {
            baseTotalBuy += x.getAmount() * good.getBasePrice(x.getName());
        }
        for (good x: recipe.getOutputGood()) {
            baseTotalSell += x.getAmount() * good.getBasePrice(x.getName());
        }
        while (expenses + baseTotalBuy < baseTotalSell * percentage) {
            percentage -= 0.01;
        }
        System.out.println(good.getBasePrice(name) * percentage);
        return good.getBasePrice(name) * percentage;
    }
    public void sellGood(int amount, String name, double price) {
        cash += amount * price;
        addLastSoldPrice(name, price);
    }

    private void payExpenses() {
        cash -= recipe.getExpenses();
    }

    private void createBuyOrders() {
        good[] input = recipe.getInputGood();
        for (int i = 0; i < input.length; i++) {
            good temp = input[i];
            double limit = good.getBasePrice(temp.getName());
            if (recipe.getInput(i) * limit <= cash) {
                planet.addBuyOrder(new order(this, temp, getExpectBuyPrice(temp.getName()), maxBuyPrice(temp.getName())));
            }
        }
    }
    private void createSellOrders() {
        good[] output = recipe.getOutputGood();
        for (int i = 0; i < output.length; i++) {
            good temp = output[i];
            if (recipe.getOutput(i) >= temp.getAmount()) {
                planet.addSellOrder(new order(this, temp, getExpectSellPrice(temp.getName()), minSellPrice(temp.getName())));
                recipe.changeOutput(i, -temp.getAmount());
            }
        }
    }

    public void tick() {
        payExpenses();
        recipe.createRecipe();
        if (order == 1) {
            createSellOrders();
        } else if (order == 2) {
            createBuyOrders();
            createSellOrders();
        } else {
            createBuyOrders();
        }
    }
}

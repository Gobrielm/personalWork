package core;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class company {
    private String name;
    private double cash;
    private recipe recipe;
    private int order;
    private static long seed;
    private static Random rand;
    private planet planet;
    private double[] lastBuyPrices;
    private int lastBuyPricesSize;
    private double[] lastSellPrices;
    private int lastSellPricesSize;
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
        lastBuyPrices = new double[5];
        lastBuyPricesSize = 0;
        lastSellPrices = new double[5];
        lastSellPricesSize = 0;
        Random rand = new Random(seed);
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
        return false;
//        double priceDiff = order1.getPrice() - order2.getPrice();
//        double price = order1.getPrice();
//        double change = Math.abs(priceDiff / price);
//        return change < 0.03;
    }

    public void buyGood(int amount, String good) {
        recipe.changeInput(good, amount);
    }
    private void addLastBoughtPrice(double price) {
        lastBuyPrices[lastBuyPricesSize] = price;
        if (lastBuyPricesSize < lastBuyPrices.length - 1) {
            lastBuyPricesSize++;
        }
    }
    private double getExpectBuyPrice(String name) {
        int temp = lastBuyPricesSize;
        double total = 0;
        for (int i = 0; i < temp; i++) {
            total += lastBuyPrices[i];
        }
        if (total == 0) {
            return good.getBasePrice(name);
        }
        return total;
    }
    private void addLastSoldPrice(double price) {
        lastSellPrices[lastSellPricesSize] = price;
        if (lastSellPricesSize < lastSellPrices.length - 1) {
            lastSellPricesSize++;
        }
    }
    private double getExpectSellPrice(String name) {
        int temp = lastSellPricesSize;
        double total = 0;
        for (int i = 0; i < temp; i++) {
            total += lastSellPrices[i];
        }
        if (total == 0) {
            return good.getBasePrice(name);
        }
        return total;
    }
    public double minSellPrice(String name) {
        double percentage = 1;
        double baseTotalPrice = 0;
        double expenses = recipe.getExpenses();
        for (good x: recipe.getInputGood()) {
            baseTotalPrice += x.getAmount() * good.getBasePrice(x.getName());
        }
        while (expenses < baseTotalPrice * percentage) {
            percentage -= 0.01;
        }
        return good.getBasePrice(name) * percentage;
    }
    public void sellGood(int amount, double price) {
        cash += amount * price;
        addLastSoldPrice(price);
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
                planet.addBuyOrder(new order(this, temp, getExpectBuyPrice(temp.getName()), rand.nextInt(1, 20)));
            }
        }
    }
    private void createSellOrders() {
        good[] output = recipe.getOutputGood();
        for (int i = 0; i < output.length; i++) {
            good temp = output[i];
            if (recipe.getOutput(i) >= temp.getAmount()) {
                planet.addSellOrder(new order(this, temp, getExpectSellPrice(temp.getName()), minSellPrice(temp.getName())));
                temp.changeAmount(-temp.getAmount());
            }
        }
    }
    private void createGoods() {
        good[] output = recipe.getOutputGood();
        for (int i = 0; i < output.length; i++) {
            good temp = output[i];
            recipe.changeOutput(i, temp.getAmount());
        }
    }
    public void tick() {
        payExpenses();
        if (order == 1) {
            createGoods();
            createSellOrders();
        } else if (order == 2) {
            createBuyOrders();
            createSellOrders();
        } else {
            createBuyOrders();
        }
    }
}

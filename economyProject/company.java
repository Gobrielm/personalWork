package core;

import java.util.*;

public class company {
    private String name;
    private double cash;
    private recipe recipe;
    private int order;
    private planet planet;
    private HashMap<String, ArrayList<Double>> lastBuyPrices;
    private HashMap<String, ArrayList<Double>> lastSellPrices;
    //Higher number is more aggressive
    private int personality;
    public company(String name, recipe recipe, planet planet) {
        this.name = name;
        if (recipe.getExpenses() > 0) {
            this.recipe = new recipe(recipe.getInput(), recipe.getOutput(), recipe.getInputAmount(), recipe.getOutputAmount(), economy.rand.nextDouble(1, recipe.getExpenses()), recipe.getIncome());
        } else {
            this.recipe = new recipe(recipe.getInput(), recipe.getOutput(), recipe.getInputAmount(), recipe.getOutputAmount(), recipe.getExpenses(), recipe.getIncome());
        }
        cash = 100;
        if (recipe.getInput() == null || Arrays.equals(recipe.getInput(), new String[]{})) {
            this.order = 1;
        } else if (recipe.getOutput() == null || Arrays.equals(recipe.getOutput(), new String[]{})) {
            this.order = 3;
        } else {
            this.order = 2;
        }
        lastSellPrices = new HashMap<>();
        lastBuyPrices = new HashMap<>();
        this.personality = economy.rand.nextInt(1, 4);
        this.planet = planet;
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
        lastBuyPrices.computeIfAbsent(name, k -> new ArrayList<>());
        lastBuyPrices.get(name).add(price);
        if (lastBuyPrices.get(name).size() > 20) {
            lastBuyPrices.get(name).removeFirst();
        }

    }
    private double getExpectBuyPrice(String name) {
        double total = 0;
        if (lastBuyPrices.get(name) != null) {
            for (Double x: lastBuyPrices.get(name)) {
                total += x;
            }
        } else {
            return good.getBasePrice(name);
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
        lastSellPrices.computeIfAbsent(name, k -> new ArrayList<>());
        lastSellPrices.get(name).add(price);
        if (lastSellPrices.get(name).size() > 20) {
            lastSellPrices.get(name).removeFirst();
        }
    }
    private double getExpectSellPrice(String name) {
        double total = 0;
        if (lastSellPrices.get(name) != null) {
            for (Double x: lastSellPrices.get(name)) {
                total += x;
            }
        } else {
            return good.getBasePrice(name);
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

    @Override
    public String toString() {
        String toReturn = name + ": $" + Math.round(cash) + "----";
        for (String x: recipe.getInput()) {
            toReturn += x + "--";
        }
        for (String x: recipe.getOutput()) {
            toReturn += x + "--";
        }
        return toReturn;
    }
}

package core;

import core.Managers.confidenceManager;
import core.Managers.personalPriceManager;
import core.Managers.financeManager;

import java.util.*;

public class company implements business{
    private String name;
    private double cash;
    private recipe recipe;
    private int order;
    private planet planet;

    private financeManager financeManager;
    private personalPriceManager priceManager;
    private confidenceManager confidenceObject;
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
        confidenceObject = new confidenceManager();
        financeManager = new financeManager(this);
        priceManager = new personalPriceManager();
        this.personality = economy.rand.nextInt(1, 4);
        this.planet = planet;
    }
    @Override
    public String getName() {
        return name;
    }
    public recipe getRecipe() {
        return recipe;
    }
    @Override
    public double getCash() {
        return cash;
    }
    @Override
    public planet getPlanet() {
        return planet;
    }
    //order1 is the same as the company
    @Override
    public boolean checkDeal(order order1, order order2) {
        double priceDiff = order1.getPrice() - order2.getPrice();
        double price = order1.getPrice();
        double change = Math.abs(priceDiff / price);
        if (order1.isBuyOrder() && priceDiff > 0) {
            return true;
        } else if (!order1.isBuyOrder() && priceDiff < 0) {
            return true;
        }
        return change < 0.01 * personality;
    }
    public void adjustDeal(order order1) {
        if (order1.isBuyOrder()) {
            changeConfidenceB(-1, order1.getGood());
            if (getBuyConfidence(order1.getGood()) < 7) {
                order1.setPrice(order1.getPrice() * (1 + 0.01 * personality));
            }
        } else {
            changeConfidenceS(-1, order1.getGood());
            if (getSellConfidence(order1.getGood()) < 7) {
                order1.setPrice(order1.getPrice() * (1 - 0.01 * personality));
            }
        }
    }
    public void changeConfidenceB(int num, String goodName) {
        confidenceObject.changeBuyConfidence(num, goodName);
    }
    public void changeConfidenceS(int num, String goodName) {
        confidenceObject.changeSellConfidence(num, goodName);
    }
    private int getBuyConfidence(String goodName) {
        return confidenceObject.getBuyConfidence(goodName);
    }
    private int getSellConfidence(String goodName) {
        return confidenceObject.getSellConfidence(goodName);
    }
    private double getExpectBuyPrice(String name) {
        return priceManager.getExpectBuyPrice(name, planet);
    }
    public double maxBuyPrice(String name) {
        double baseTotalBuy = 0;
        double baseTotalSell = 0;
        double income = recipe.getIncome();
        double expenses = recipe.getExpenses();
        for (good x: recipe.getInputGood()) {
            baseTotalBuy += x.getAmount() * planet.getBasePrice(x.getName());
        }
        for (good x: recipe.getOutputGood()) {
            baseTotalSell += x.getAmount() * planet.getBasePrice(x.getName());
        }
        double minProfit = 0.5;
        if (getBuyConfidence(name) > 9) {
            minProfit = 2;
        } else if (getBuyConfidence(name) > 7) {
            minProfit = 1.5;
        } else if (getBuyConfidence(name) > 5) {
            minProfit = 1;
        }

        double percentage = (baseTotalSell - expenses - minProfit + income) / baseTotalBuy;
        return planet.getBasePrice(name) * (percentage);
    }
    private double getExpectSellPrice(String name) {
        return priceManager.getExpectSellPrice(name, planet);
    }
    public double minSellPrice(String name) {
        double baseTotalBuy = 0;
        double baseTotalSell = 0;
        double expenses = recipe.getExpenses();
        for (good x: recipe.getInputGood()) {
            baseTotalBuy += x.getAmount() * planet.getBasePrice(x.getName());
        }
        for (good x: recipe.getOutputGood()) {
            baseTotalSell += x.getAmount() * planet.getBasePrice(x.getName());
        }
        double minProfit = 0.5;
        if (getSellConfidence(name) > 9) {
            minProfit = 2;
        } else if (getSellConfidence(name) > 7) {
            minProfit = 1.5;
        } else if (getSellConfidence(name) > 5) {
            minProfit = 1;
        }
        if (financeManager.getIncome() < minProfit) {
            //SUPER BROKEN IN THEORY BUT KINDA WORKS
            double diff = minProfit - financeManager.getIncome();
            minProfit *= diff / minProfit;
        }

        double percentage = (expenses + minProfit + baseTotalBuy) / (baseTotalSell);
        return planet.getBasePrice(name) * (percentage);
    }
    public void buyGood(int amount, String good, double price) {
        recipe.changeInput(good, amount);
        priceManager.addLastBoughtPrice(good, price);
        changeConfidenceB(2, good);
        cash -= (price * amount);
        financeManager.incomeEditLast(-price * amount);
    }
    public void sellGood(int amount, String good, double price) {
        cash += amount * price;
        financeManager.incomeEditLast(amount * price);
        priceManager.addLastSoldPrice(good, price);
        changeConfidenceS(2, good);
    }
    public void returnBuy(order order) {
        changeConfidenceB(-1, order.getGood());
    }
    public void returnSell(order order) {
        recipe.changeOutput(order.getGood(), order.getAmount());
        changeConfidenceS(-1, order.getGood());
    }
    private void payExpenses() {
        financeManager.incomeEditLast(-recipe.getExpenses() + recipe.getIncome());
        cash -= recipe.getExpenses();
        cash += recipe.getIncome();
        financeManager.incomeRemoveFirst();
    }
    public void askToChange(order order) { // This could be anti - helpful since basePrice is inaccurate
        if (order.isBuyOrder()) {
            if (getBuyConfidence(order.getGood()) < 5) {
                if (order.getPrice() < planet.getBasePrice(order.getGood())) {
                    order.setPrice(planet.getBasePrice(order.getGood()));
                }
            }
        } else {
            if (getSellConfidence(order.getGood()) < 5) {
                if (order.getPrice() > planet.getBasePrice(order.getGood())) {
                    order.setPrice(planet.getBasePrice(order.getGood()));
                }
            }
        }
    }

    private void createBuyOrders() {
        good[] input = recipe.getInputGood();
        for (int i = 0; i < input.length; i++) {
            good temp = input[i];
            double limit = maxBuyPrice(temp.getName());
            if (recipe.getInputAmount()[i] * limit <= cash) {
                double price = getExpectBuyPrice(temp.getName());
                if (getBuyConfidence(temp.getName()) == 10) {
                    price *= 0.98;
                } else if (getBuyConfidence(temp.getName()) > 8) {
                    price *= 0.99;
                } else if (getBuyConfidence(temp.getName()) < 3) {
                    price = planet.getBasePrice(temp.getName()) * 1.01;
                }
                price = Math.min(price, limit);
                order newOrder = new order(this, temp, price, limit, true);
                planet.addBuyOrder(newOrder);
            }
        }
    }
    private void createSellOrders() {
        good[] output = recipe.getOutputGood();
        for (int i = 0; i < output.length; i++) {
            good temp = output[i];
            if (recipe.getOutput(i) >= temp.getAmount()) {
                double price = getExpectSellPrice(temp.getName());
                if (getSellConfidence(temp.getName()) == 10) {
                    price *= 1.02;
                } else if (getSellConfidence(temp.getName()) > 8) {
                    price *= 1.01;
                } else if (getSellConfidence(temp.getName()) < 3) {
                    price = planet.getBasePrice(temp.getName()) * 0.99;
                }
                double limit = minSellPrice(temp.getName());
                price = Math.max(price, limit);
                order newOrder = new order(this, temp, price, limit, false);
                planet.addSellOrder(newOrder);
                recipe.changeOutput(i, -temp.getAmount());
            }
        }
    }

    public void tick() {
        payExpenses();
        confidenceObject.degradeConfidence();
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
        String toReturn = name + ": $" + Math.round(cash) + "- ";
        toReturn += "Income" + ": $" + financeManager.getIncome() + "- ";
        if (order == 2) {
            for (String good: recipe.getInput()) {
                toReturn += good + " CB: " + getBuyConfidence(good) + " ";
            }
            for (String good: recipe.getOutput()) {
                toReturn += good + " CS: " + getSellConfidence(good) + " ";
            }
        } else if (order == 1) {
            for (String good: recipe.getOutput()) {
                toReturn += good + " CS: " + getSellConfidence(good) + " ";
            }
        } else {
            for (String good: recipe.getInput()) {
                toReturn += good + " CB: " + getBuyConfidence(good) + " ";
            }
        }

        return toReturn;
    }
}

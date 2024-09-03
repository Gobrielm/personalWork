package core;

import java.util.*;

public class company {
    private String name;
    private double cash;
    private recipe recipe;
    private int order;
    private planet planet;
    private LinkedList<Double> incomeList;
    private HashMap<String, Double> lastBuyPrices;
    private HashMap<String, Double> lastSellPrices;
    private HashMap<String, Integer> confidenceB;
    private HashMap<String, Integer> confidenceS;
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
        incomeList = new LinkedList<>();
        int sizeOfIncomeList = 10;
        for (int i = 0; i < sizeOfIncomeList; i++) {
            incomeList.add(0.0);
        }
        confidenceB = new HashMap<>();
        confidenceS = new HashMap<>();
        for (String good: good.getGoodList()) {
            confidenceB.put(good, 5);
            confidenceS.put(good, 5);
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
    private void incomeEditLast(double num) {
        double last = incomeList.getLast() + num;
        incomeList.removeLast();
        incomeList.add(last);
    }
    private void incomeRemoveFirst() {
        incomeList.removeFirst();
        incomeList.addLast(0.0);
    }
    public double getIncome() {
        double total = 0.0;
        for (double x: incomeList) {
            total += x;
        }
        return (double) Math.round(total / incomeList.size() * 10) / 10;
    }
    //order1 is the same as the company
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
            if (confidenceB.get(order1.getGood()) < 7) {
                order1.setPrice(order1.getPrice() * (1 + 0.01 * personality));
            }
        } else {
            changeConfidenceS(-1, order1.getGood());
            if (confidenceS.get(order1.getGood()) < 7) {
                order1.setPrice(order1.getPrice() * (1 - 0.01 * personality));
            }
        }
    }
    private void addLastBoughtPrice(String name, double price) {
        lastBuyPrices.put(name, price);
    }
    private double getExpectBuyPrice(String name) {
        if (lastBuyPrices.containsKey(name)) {
            return lastBuyPrices.get(name);
        } else {
            return planet.getBasePrice(name);
        }
    }
    public void changeConfidenceB(int num, String good) {
        confidenceB.put(good, confidenceB.get(good) + num);
        if (confidenceB.get(good) > 10) {
            confidenceB.put(good, 10);
        } else if (confidenceB.get(good) < 0) {
            confidenceB.put(good, 0);
        }
    }
    public void changeConfidenceS(int num, String good) {
        confidenceS.put(good, confidenceS.get(good) + num);
        if (confidenceS.get(good) > 10) {
            confidenceS.put(good, 10);
        } else if (confidenceS.get(good) < 0) {
            confidenceS.put(good, 0);
        }
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
        double percentage = (baseTotalSell - expenses + income) / baseTotalBuy;
        return planet.getBasePrice(name) * (percentage - 0.05);
    }
    private void addLastSoldPrice(String name, double price) {
        lastSellPrices.put(name, price);
    }
    private double getExpectSellPrice(String name) {
        if (lastSellPrices.containsKey(name)) {
            return lastSellPrices.get(name);
        } else {
            return planet.getBasePrice(name);
        }
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
        double percentage = (expenses + baseTotalBuy) / baseTotalSell;
        return planet.getBasePrice(name) * (percentage - 0.05);
    }
    public void buyGood(int amount, String good, double price) {
        recipe.changeInput(good, amount);
        addLastBoughtPrice(good, price);
        changeConfidenceB(2, good);
        cash -= (price * amount);
        incomeEditLast(-price * amount);
    }
    public void sellGood(int amount, String good, double price) {
        cash += amount * price;
        incomeEditLast(amount * price);
        addLastSoldPrice(good, price);
        changeConfidenceS(2, good);
    }
    public void returnBuy(order order) {
        changeConfidenceB(-1, order.getGood());
    }
    public void returnSell(order order) {
        recipe.changeOutput(order.getGood(), order.getAmount());
        changeConfidenceS(-1, order.getGood());
    }
    public planet getPlanet() {
        return planet;
    }
    private void payExpenses() {
        incomeEditLast(-recipe.getExpenses() + recipe.getIncome());
        cash -= recipe.getExpenses();
        cash += recipe.getIncome();
        incomeRemoveFirst();
    }
    public void askToChange(order order) {
        if (order.isBuyOrder()) {
            if (confidenceB.get(order.getGood()) < 5) {
                if (order.getPrice() < planet.getBasePrice(order.getGood())) {
                    order.setPrice(planet.getBasePrice(order.getGood()));
                }
            }
        } else {
            if (confidenceS.get(order.getGood()) < 5) {
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
                if (confidenceB.get(temp.getName()) >= 9) {
                    price *= 0.98;
                } else if (confidenceB.get(temp.getName()) > 7) {
                    price *= 0.99;
                } else if (confidenceB.get(temp.getName()) < 3) {
                    price = planet.getBasePrice(temp.getName()) * 1.01;
                }
                if ((price > limit)) {
                    price = limit;
                }
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
                if (confidenceS.get(temp.getName()) >= 9) {
                    price *= 1.02;
                } else if (confidenceS.get(temp.getName()) > 7) {
                    price *= 1.01;
                } else if (confidenceS.get(temp.getName()) < 3) {
                    price = planet.getBasePrice(temp.getName()) * 0.99;
                }
                double limit = minSellPrice(temp.getName());
                if ((price < limit)) {
                    price = limit;
                }
                planet.addSellOrder(new order(this, temp, price, limit, false));
                recipe.changeOutput(i, -temp.getAmount());
            }
        }
    }

    private void degradeConfidence() {
        for (String good: good.getGoodList()) {
            if (confidenceB.get(good) > 7) {
                changeConfidenceB(-1, good);
            } else if (confidenceB.get(good) < 3) {
                changeConfidenceB(1, good);
            }
            if (confidenceS.get(good) > 7) {
                changeConfidenceS(-1, good);
            } else if (confidenceS.get(good) < 3) {
                changeConfidenceS(1, good);
            }
        }

    }

    public void tick() {
        payExpenses();
        degradeConfidence();
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
        String toReturn = name + ": $" + Math.round(cash) + "--- ";
        toReturn += "Income" + ": $" + getIncome() + "--- ";
//        for (String x: recipe.getInput()) {
//            toReturn += x + "--";
//        }
//        for (String x: recipe.getOutput()) {
//            toReturn += x + "--";
//        }
        if (order == 2) {
            for (String good: recipe.getInput()) {
                toReturn += good + " ConfB: " + confidenceB.get(good) + " ";
            }
            for (String good: recipe.getOutput()) {
                toReturn += good + " ConfS: " + confidenceS.get(good) + " ";
            }
        } else if (order == 1) {
            for (String good: recipe.getOutput()) {
                toReturn += good + " ConfS: " + confidenceS.get(good) + " ";
            }
        } else {
            for (String good: recipe.getInput()) {
                toReturn += good + " ConfB: " + confidenceB.get(good) + " ";
            }
        }

        return toReturn;
    }
}

package core;

import core.managers.confidenceManager;
import core.managers.companyManager;
import core.managers.personalPriceManager;
import core.managers.financeManager;
import core.constants.goodAcronyms;

import java.util.*;

public class company implements business, Comparable<company> {
    private financeManager financeManager;
    private personalPriceManager priceManager;
    private confidenceManager confidenceObject;
    private companyManager companyManager;
    //Higher number is more aggressive

    public company(String name, recipe recipe, planet planet) {
        companyManager = new companyManager(name, recipe, planet);
        confidenceObject = new confidenceManager();
        financeManager = new financeManager(this);
        priceManager = new personalPriceManager();
    }
    @Override
    public int getMaxAge() {
        return companyManager.getMaxAge();
    }
    @Override
    public void changeCash(double amount) {
        companyManager.changeCash(amount);
    }
    @Override
    public double getCash() {
        return companyManager.getCash();
    }
    @Override
    public String getName() {
        return companyManager.getName();
    }
    public recipe getRecipe() {
        return companyManager.getRecipe();
    }
    @Override
    public planet getPlanet() {
        return companyManager.getPlanet();
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
        return change < 0.01 * getPersonality();
    }
    public boolean getBankrupt() {
        return companyManager.getBankrupt();
    }
    public int getPersonality() {
        return companyManager.getPersonality();
    }
    public void adjustDeal(order order1) {
        if (order1.isBuyOrder()) {
            changeConfidenceB(-1, order1.getGood());
            if (getBuyConfidence(order1.getGood()) < 7) {
                order1.setPrice(order1.getPrice() * (1 + 0.01 * getPersonality()));
            }
        } else {
            changeConfidenceS(-1, order1.getGood());
            if (getSellConfidence(order1.getGood()) < 7) {
                order1.setPrice(order1.getPrice() * (1 - 0.01 * getPersonality()));
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
        return priceManager.getExpectBuyPrice(name, getPlanet());
    }
    public double getMaxBuyPrice(String name) {
        double baseTotalBuy = 0;
        double baseTotalSell = 0;
        recipe thisRecipe = getRecipe();
        planet thisPlanet = getPlanet();
        double income = thisRecipe.getIncome();
        double expenses = thisRecipe.getExpenses();

        for (good x: thisRecipe.getInputGoodArray()) {
            baseTotalBuy += x.getAmount() * thisPlanet.getBasePrice(x.getName());
        }
        for (good x: thisRecipe.getOutputGoodArray()) {
            baseTotalSell += x.getAmount() * thisPlanet.getBasePrice(x.getName());
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
        return thisPlanet.getBasePrice(name) * (percentage);
    }
    private double getExpectSellPrice(String name) {
        return priceManager.getExpectSellPrice(name, companyManager.getPlanet());
    }
    public double getMinSellPrice(String name) {
        double baseTotalBuy = 0;
        double baseTotalSell = 0;
        recipe thisRecipe = companyManager.getRecipe();
        planet thisPlanet = companyManager.getPlanet();
        double expenses = thisRecipe.getExpenses();
        for (good x: thisRecipe.getInputGoodArray()) {
            baseTotalBuy += x.getAmount() * thisPlanet.getBasePrice(x.getName());
        }
        for (good x: thisRecipe.getOutputGoodArray()) {
            baseTotalSell += x.getAmount() * thisPlanet.getBasePrice(x.getName());
        }
        double minProfit = 0.5;
        if (getSellConfidence(name) > 9) {
            minProfit = 2;
        } else if (getSellConfidence(name) > 7) {
            minProfit = 1.5;
        } else if (getSellConfidence(name) > 5) {
            minProfit = 1;
        }

        double percentage = (expenses + minProfit + baseTotalBuy) / (baseTotalSell);
        return thisPlanet.getBasePrice(name) * (percentage);
    }
    public double getProfit() {
        return financeManager.getProfit();
    }
    public void buyGood(int amount, String good, double price) {
        recipe thisRecipe = companyManager.getRecipe();
        thisRecipe.changeInputAmount(good, amount);
        priceManager.addLastBoughtPrice(good, price);
        changeConfidenceB(2, good);
        companyManager.changeCash(-price * amount);
        financeManager.profitEditLast(-price * amount);
    }
    public void sellGood(int amount, String good, double price) {
        companyManager.changeCash(price * amount);
        financeManager.profitEditLast(amount * price);
        priceManager.addLastSoldPrice(good, price);
        changeConfidenceS(2, good);
    }
    public void returnBuy(order order) {
        changeConfidenceB(-1, order.getGood());
    }
    public void returnSell(order order) {
        recipe thisRecipe = companyManager.getRecipe();
        thisRecipe.changeOutputAmount(order.getGood(), order.getAmount());
        changeConfidenceS(-1, order.getGood());
    }
    private void payExpenses() {
        recipe thisRecipe = companyManager.getRecipe();
        financeManager.profitEditLast(-thisRecipe.getExpenses() + thisRecipe.getIncome());
        companyManager.changeCash(-thisRecipe.getExpenses());
        companyManager.changeCash(thisRecipe.getIncome());
        financeManager.profitRemoveFirst();
    }

    private void createBuyOrders() {
        recipe thisRecipe = companyManager.getRecipe();
        planet thisPlanet = companyManager.getPlanet();
        double thisCash = companyManager.getCash();
        good[] input = thisRecipe.getInputGoodArray();
        for (int i = 0; i < input.length; i++) {
            good temp = input[i];
            double limit = getMaxBuyPrice(temp.getName());
            if (thisRecipe.getInputAmount()[i] * limit <= thisCash) {
                double price = getExpectBuyPrice(temp.getName());
                if (getBuyConfidence(temp.getName()) >= 9) {
                    price *= 0.99;
                } else if (getBuyConfidence(temp.getName()) < 3) {
                    price = thisPlanet.getBasePrice(temp.getName()) * 1.01;
                }
                price = Math.min(price, limit);
                order newOrder = new order(this, temp, price, limit, true);
                thisPlanet.addOrder(newOrder);
            }
        }
    }
    private void createSellOrders() {
        recipe thisRecipe = companyManager.getRecipe();
        planet thisPlanet = companyManager.getPlanet();
        good[] output = thisRecipe.getOutputGoodArray();
        for (int i = 0; i < output.length; i++) {
            good temp = output[i];
            if (thisRecipe.getOutputName(i) >= temp.getAmount()) {
                double price = getExpectSellPrice(temp.getName());
                if (getSellConfidence(temp.getName()) >= 9) {
                    price *= 1.02;
                } else if (getSellConfidence(temp.getName()) < 3) {
                    price = thisPlanet.getBasePrice(temp.getName()) * 0.99;
                }
                double limit = getMinSellPrice(temp.getName());
                price = Math.max(price, limit);
                order newOrder = new order(this, temp, price, limit, false);
                thisPlanet.addOrder(newOrder);
                thisRecipe.changeOutputAmount(i, -temp.getAmount());
            }
        }
    }

    private void sellStock() {
        planet thisPlanet = companyManager.getPlanet();
        for (share x: thisPlanet.getOwnedShares(this)) {
            //Selling stock of the company
            if (x.getPrice() == 0.0 && getProfit() > 2 && x.getAmount() > 20.0) {
                thisPlanet.sellShare(this, this, 10.0, getProfit() * 40);
            //Previously Bought
            } else {

            }
        }
    }

    private void buyStock() {
        //Todo implement more intelligent way of checking to buy instead of flat numbers
        recipe thisRecipe = companyManager.getRecipe();
        planet thisPlanet = companyManager.getPlanet();
        double thisCash = companyManager.getCash();

        if (thisCash > 1000 && getProfit() > 0) {
            String[] goodNamesOfCompaniesToBuy = thisRecipe.getBothName();
            double amountToSpend = thisCash - 1000;
            for (String x: goodNamesOfCompaniesToBuy) {
                share toPonder = thisPlanet.buyShare(x, amountToSpend);
                company companyToPonder = toPonder.getPieceOf();
                boolean buy = false;
                double price = toPonder.getPrice();
                if (companyToPonder.getBankrupt() && companyToPonder.getExpectSellPrice(x) > thisPlanet.getBasePrice(x)) {
                    buy = true;
                } else if (companyToPonder.getProfit() > 1 && companyToPonder.getProfit() * 40 > price) {
                    buy = true;
                }

                if (buy) {
                    amountToSpend -= toPonder.getPrice();
                    thisPlanet.buyShare(toPonder, this);
                }
            }
        }
    }

    public void tick() {
        payExpenses();
//        sellStock();
        recipe thisRecipe = companyManager.getRecipe();
        int thisOrder = companyManager.getOrder();
        confidenceObject.degradeConfidence();
        thisRecipe.createRecipe();
        if (thisOrder == 1) {
            createSellOrders();
        } else if (thisOrder == 2) {
            createBuyOrders();
            createSellOrders();
        } else {
            createBuyOrders();
        }
    }

    @Override
    public String toString() {
        recipe thisRecipe = companyManager.getRecipe();
        int thisOrder = companyManager.getOrder();
        double thisCash = companyManager.getCash();
        String thisName = companyManager.getName();

        String toReturn = thisName.substring(0, 3) + ".: $" + Math.round(thisCash) + "- ";
        toReturn += "Income" + ": $" + getProfit() + "- ";
        if (thisOrder == 2) {
            for (String good: thisRecipe.getInputName()) {
                toReturn += goodAcronyms.getAcronym(good) + " CB: " + getBuyConfidence(good) + " ";
            }
            for (String good: thisRecipe.getOutputName()) {
                toReturn += goodAcronyms.getAcronym(good) + " CS: " + getSellConfidence(good) + " ";
            }
        } else if (thisOrder == 1) {
            for (String good: thisRecipe.getOutputName()) {
                toReturn += goodAcronyms.getAcronym(good) + " CS: " + getSellConfidence(good) + " ";
            }
        } else {
            for (String good: thisRecipe.getInputName()) {
                toReturn += goodAcronyms.getAcronym(good) + " CB: " + getBuyConfidence(good) + " ";
            }
        }
        return toReturn;
    }

    @Override
    public int compareTo(company o) {
        long toReturn = Utils.roundNoZero(getProfit() - o.getProfit());
        toReturn = toReturn == 0 ? (Utils.roundNoZero(getCash() - o.getCash())): toReturn;
        toReturn = toReturn == 0 ? (getName().compareTo(o.getName())): toReturn;
        return (int) toReturn;
    }
}

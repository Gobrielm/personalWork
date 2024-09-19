package core;

import core.managers.orderManager;
import core.managers.stockManager;

import java.util.ArrayList;

public class planet {
    private int size;
    private ArrayList<company> companies;
    private ArrayList<company> bankruptCompanies;
    private orderManager orderManager;
    private stockManager stockManager;
    public planet() {
        companies = new ArrayList<>();
        bankruptCompanies = new ArrayList<>();
        orderManager = new orderManager();
        stockManager = new stockManager();
        size = economy.rand.nextInt(30, 60);
        for (int i = 0; i < size / 10; i++) {
            for (int k = 0; k < 5; k++) {
                String randGood = good.randGoodName();
                recipe buyer = good.getBuyer(randGood);
                recipe[] primary = good.primaryRecipeWithGood(randGood);
                addCompany(new company("Filler", buyer, this));
                for (recipe r: primary) {
                    addCompany(new company("Filler", r, this));
                }
            }
        }
    }
    //NOTE:Order Manager
    public void addLastPrice(String goodName) {
        orderManager.addLastPrice(goodName);
    }
    public boolean getPriceBigger(String goodName) {
        return orderManager.getPriceBigger(goodName);
    }
    public boolean getPriceSmaller(String goodName) {
        return orderManager.getPriceSmaller(goodName);
    }
    public Double[] getLastPriceArray(String goodName, int weeks) {
        return orderManager.getLastPriceArray(goodName, weeks);
    }
    public company[] getCompanies() {
        return companies.toArray(new company[0]);
    }
    public Double[] getPriceList() {
        return orderManager.getBasePrices();
    }
    public void addOrder(order order) {
        if (order.getAmount() > 0) {
            if (order.isBuyOrder()) {
                orderManager.addBuyOrder(order);
            } else {
                orderManager.addSellOrder(order);
            }
        }
    }
    public order[] getBuyOrders(String goodName) {
        return orderManager.getBuyOrders(goodName);
    }
    public order[] getSellOrders(String goodName) {
        return orderManager.getSellOrders(goodName);
    }
    public void completeOrders() {
        orderManager.completeOrders();
    }
    public double getBasePrice(String goodName) {
        return orderManager.getBasePrice(goodName);
    }
    public void changeBasePrice(String goodName, double price, int amount) {
        orderManager.changeBasePrice(goodName, price, amount);
    }
    //NOTE:Stock Manager
    public void addCompanyToPrivateMarket(company toAdd) {
        stockManager.addPrivateCompany(new share(toAdd));
    }
    public share[] getOwnedShares(business owner) {
        return stockManager.getOwnedShares(owner);
    }
    public share buyShare(String goodName, double priceMax) {
        share toReturn = stockManager.getRandomShare(goodName);
        while(toReturn.getPrice() < priceMax) {
            toReturn = stockManager.getRandomShare(goodName);
        }
        return toReturn;
    }
    public void buyShare(share share, business newOwner) {
        stockManager.buyShare(share, newOwner);
    }

    public void sellShare(company pieceOf, business owner, double amount, double price) {
        stockManager.sellShare(pieceOf, owner, amount, price);
    }

    //NOTE:Companies
    public void addCompany(company toAdd) {
        companies.add(toAdd);
        addCompanyToPrivateMarket(toAdd);
    }
    public void planetTick() {
        completeOrders();
        for (int i = 0; i < companies.size(); i++) {
            company x = companies.get(i);
            x.tick();
            if (x.getBankrupt()) {
                i--;
                bankruptCompanies.add(x);
                companies.remove(x);
            }
        }
        for (String goodName: good.getGoodList()) {
            addLastPrice(goodName);
        }
    }

    private void testCompanies() {
        companies.add(new company("MineA", new recipe(new good[]{}, new good[]{new good("Copper", 1)}, 3, 0), this));
        companies.add(new company("MineB", new recipe(new good[]{}, new good[]{new good("Copper", 1)}, 3, 0), this));
        companies.add(new company("MineC", new recipe(new good[]{}, new good[]{new good("Zinc", 1)}, 3, 0), this));
        companies.add(new company("Factory", new recipe(new good[]{new good("Zinc", 2), new good("Copper", 4)}, new good[]{new good("Brass", 2)}, 3, 0), this));
        companies.add(new company("Buyer", new recipe(new good[]{new good("Brass", 1)}, new good[]{}, 0, 35), this));
    }
}

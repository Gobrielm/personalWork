package core;

import core.Managers.orderManager;

import java.util.ArrayList;

public class planet {
    private int size;
    private ArrayList<company> companies;
    private ArrayList<company> bankruptCompanies;
    private orderManager manager;
    public planet() {
        companies = new ArrayList<>();
        size = economy.rand.nextInt(30, 60);
        for (int i = 0; i < size / 10; i++) {
            for (int k = 0; k < 5; k++) {
                String randGood = good.randGoodName();
                recipe buyer = good.getBuyer(randGood);
                recipe[] primary = good.primaryRecipeWithGood(randGood);
                companies.add(new company("Filler", buyer, this));
                for (recipe r: primary) {
                    companies.add(new company("Filler", r, this));
                }
            }


        }

        manager = new orderManager();
    }

    public company[] getCompanies() {
        return companies.toArray(new company[0]);
    }
    public Double[] getPriceList() {
        return manager.getBasePrices();
    }
    public void addOrder(order order) {
        if (order.getAmount() > 0) {
            if (order.isBuyOrder()) {
                manager.addBuyOrder(order);
            } else {
                manager.addSellOrder(order);
            }
        }

    }
    public order[] getBuyOrders(String goodName) {
        return manager.getBuyOrders(goodName);
    }
    public order[] getSellOrders(String goodName) {
        return manager.getSellOrders(goodName);
    }
    public void addCompany(company toAdd) {
        companies.add(toAdd);
    }

    public void completeOrders() {
        manager.completeOrders();
    }

    public double getBasePrice(String goodName) {
        return manager.getBasePrice(goodName);
    }

    public void changeBasePrice(String goodName, double price, int amount) {
        manager.changeBasePrice(goodName, price, amount);
    }

    public void planetTick() {
        completeOrders();
        for (company x: companies) {
            x.tick();
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

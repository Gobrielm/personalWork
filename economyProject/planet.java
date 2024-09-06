package core;

import core.Managers.orderManager;

import java.util.ArrayList;
import java.util.HashMap;

public class planet {
    private int id;
    private int size;
    private ArrayList<company> companies;
    private orderManager manager;
    public planet(int id) {
        this.id = id;
        companies = new ArrayList<>();
        size = economy.rand.nextInt(30, 60);
        testCompanies();
//        for (int i = 0; i < size / 2; i++) {
//            recipe temp = good.randPrimaryRecipe();
//            String name = good.pickRandName(1);
//            company newCompany = new company(name, temp, this);
//            companies.add(newCompany);
//        }
//        for (int i = 0; i < size / 2; i++) {
//            recipe temp = good.randSecondaryRecipe();
//            String name = good.pickRandName(3);
//            company newCompany = new company(name, temp, this);
//            companies.add(newCompany);
//        }
        manager = new orderManager();
    }

    public company[] getCompanies() {
        return companies.toArray(new company[0]);
    }
    public Double[] getPriceList() {
        return manager.getBasePrices();
    }
    public void addBuyOrder(order order) {
        manager.addBuyOrder(order);
    }
    public order[] getBuyOrders(String goodName) {
        return manager.getBuyOrders(goodName);
    }
    public void addSellOrder(order order) {
        manager.addSellOrder(order);
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

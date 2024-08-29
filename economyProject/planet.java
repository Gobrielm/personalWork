package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class planet {
    private int id;
    private int size;
    private static long seed;
    private static Random rand;
    private ArrayList<company> companies;
    private HashMap<String, Double> prices;
    private HashMap<String, ArrayList<order>> buyOrders;
    private HashMap<String, ArrayList<order>> sellOrders;
    public planet(int id) {
        this.id = id;
        companies = new ArrayList<>();
        rand = new Random(seed);
        size = rand.nextInt(30, 60);
        for (int i = 0; i < size * 2 / 3; i++) {
            recipe temp = good.randPrimaryRecipe();
            company newCompany = new company("Filler" , temp, this);
            companies.add(newCompany);
        }
        for (int i = 0; i < size / 3; i++) {
            recipe temp = good.randSecondaryRecipe();
            company newCompany = new company("Filler", temp, this);
            companies.add(newCompany);
        }
        String[] goods = good.getGoodList();
        Double[] basePrice = good.getPriceList();
        prices = new HashMap<>();
        buyOrders = new HashMap<>();
        sellOrders = new HashMap<>();
        for (int i = 0; i < goods.length; i++) {
            prices.put(goods[i], basePrice[i]);
            buyOrders.put(goods[i], new ArrayList<>());
            sellOrders.put(goods[i], new ArrayList<>());
        }
    }
    public static void setSeed(long newSeed) {
        seed = newSeed;
    }
    public String[] getGoodList() {
        return prices.keySet().toArray(new String[0]);
    }
    public Double[] getPriceList() {
        return prices.values().toArray(new Double[0]);
    }
    public void addBuyOrder(order order) {
        buyOrders.get(order.getGood()).add(order);
    }
    public order[] getBuyOrders(String name) {
        if (buyOrders.get(name) == null) {
            return new order[0];
        }
        return buyOrders.get(name).toArray(new order[0]);
    }
    public void addSellOrder(order order) {
        sellOrders.get(order.getGood()).add(order);
    }
    public order[] getSellOrders(String name) {
        if (sellOrders.get(name) == null) {
            return new order[0];
        }
        return sellOrders.get(name).toArray(new order[0]);
    }
    public void addCompany(company toAdd) {
        companies.add(toAdd);
    }

    public void completeOrders() {
        for (String good: prices.keySet()) {
            for (order buy: buyOrders.get(good)) {
                if (buy.getAmount() == 0) {
                    buyOrders.get(good).remove(buy);
                    continue;
                }
                if (sellOrders.get(good).isEmpty()) {
                    break;
                }
                order sell = sellOrders.get(good).get(rand.nextInt(0, sellOrders.get(good).size()));
                while (sell.getAmount() == 0) {
                    sellOrders.get(good).remove(sell);
                    sell = sellOrders.get(good).get(rand.nextInt(0, sellOrders.get(good).size()));
                }
                order.makeDeal(buy, sell);
            }
        }
    }

    public void planetTick() {
        for (company x: companies) {
            x.tick();
        }
        completeOrders();
    }
}

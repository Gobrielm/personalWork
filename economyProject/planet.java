package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class planet {
    private int id;
    private int size;
    private static long seed;
    private ArrayList<company> companies;
    private HashMap<String, Double> prices;
    private HashMap<String, ArrayList<order>> buyOrders;
    private HashMap<String, ArrayList<order>> sellOrders;
    public planet(int id) {
        this.id = id;
        companies = new ArrayList<>();
        Random rand = new Random(seed);
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
        for (int i = 0; i < goods.length; i++) {
            prices.put(goods[i], basePrice[i]);
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
    public void addSellOrder(order order) {
        sellOrders.get(order.getGood()).add(order);
    }

    public void addCompany(company toAdd) {
        companies.add(toAdd);
    }

}

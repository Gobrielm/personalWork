package core;

import edu.princeton.cs.algs4.In;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class planet {
    private int id;
    private int size;
    private ArrayList<company> companies;
    private HashMap<String, Double> prices;
    private HashMap<String, ArrayList<order>> buyOrders;
    private HashMap<String, ArrayList<order>> sellOrders;
    private HashMap<String, ArrayList<Double>> priceSold;
    public planet(int id) {
        this.id = id;
        companies = new ArrayList<>();
        priceSold = new HashMap<>();
        size = economy.rand.nextInt(30, 60);
        for (int i = 0; i < size / 2; i++) {
            recipe temp = good.randPrimaryRecipe();
            company newCompany = new company("Filler" , temp, this);
            companies.add(newCompany);
        }
        for (int i = 0; i < size / 2; i++) {
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
    public company[] getCompanies() {
        return companies.toArray(new company[0]);
    }
    public double getPriceSold(String good) {
        double total = 0.0;
        int amount = 0;
        for (double x: priceSold.get(good)) {
            total += x;
            amount ++;
        }
        return total / amount;
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
        for (String x: good.getGoodList()) {
            priceSold.put(x, new ArrayList<>());
        }
        for (String good: prices.keySet()) {
            ArrayList<Integer> pickFromBuy = new ArrayList<>();
            for (int i = 0; i < buyOrders.get(good).size(); i++) {
                pickFromBuy.add(i);
            }
            if (pickFromBuy.isEmpty()) {
                continue;
            }
            int num1 = 0;
            int toChoose1 = 0;
            order buy = null;

            ArrayList<Integer> pickFromSell = new ArrayList<>();
            for (int i = 0; i < sellOrders.get(good).size(); i++) {
                pickFromSell.add(i);
            }
            if (pickFromSell.isEmpty()) {
                continue;
            }
            int num = 0;
            int toChoose = 0;
            order sell = null;

            while (!pickFromBuy.isEmpty() && !pickFromSell.isEmpty()) {
                while(!pickFromBuy.isEmpty()) {
                    num1 = economy.rand.nextInt(0, pickFromBuy.size());
                    toChoose1 = pickFromBuy.get(num1);
                    buy = buyOrders.get(good).get(toChoose1);
                    if (buy.checkValid()) {
                        break;
                    } else {
                        pickFromBuy.remove(num1);
                    }
                }

                while (!pickFromSell.isEmpty()) {
                    num = economy.rand.nextInt(0, pickFromSell.size());
                    toChoose = pickFromSell.get(num);
                    sell = sellOrders.get(good).get(toChoose);
                    if (sell.checkValid()) {
                        break;
                    } else {
                        pickFromSell.remove(num);
                    }
                }
                if (sell.checkValid() && buy.checkValid()) {
                    order.makeDeal(buy, sell);
                    double num2 = order.makeDeal(buy, sell);
                    if (num2 != 0) {
                        priceSold.get(good).add(num2);
                    }
                }
            }
        }
        cleanOrders();
    }

    public void cleanOrders() {
        for (String good: prices.keySet()) {
            int size = buyOrders.get(good).size();
            for (int i = 0; i < size; i++) {
                order order = buyOrders.get(good).get(i);
                if (order.checkOut()) {
                    buyOrders.get(good).remove(i);
                    i--;
                    size--;
                } else if (order.checkOutPriced()) {
                    order.getOwner().returnBuy(order);
                    buyOrders.get(good).remove(i);
                    i--;
                    size--;
                }
            }

            int size1 = sellOrders.get(good).size();
            for (int i = 0; i < size1; i++) {
                order order = sellOrders.get(good).get(i);
                if (order.checkOut()) {
                    sellOrders.get(good).remove(i);
                    i--;
                    size1--;
                } else if (order.checkOutPriced()) {
                    order.getOwner().returnSell(order);
                    sellOrders.get(good).remove(i);
                    i--;
                    size1--;
                } else {
                    order.getOwner().askToChange(order);
                }
            }
        }
    }

    public double getBasePrice(String name) {
        return prices.get(name);
    }

    public void changeBasePrice(String name, double price, int amount) {
        double percentage = (double) amount / 20;
        if (percentage > 0.5) {
            percentage = 0.5;
        }
        double value = prices.get(name) * (1 - percentage) + price * percentage;
        prices.put(name, value);
    }

    public void planetTick() {
        for (company x: companies) {
            x.tick();
        }
        completeOrders();
    }
}

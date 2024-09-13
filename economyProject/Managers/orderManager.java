package core.Managers;

import core.Utils;
import core.economy;
import core.good;
import core.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class orderManager {
    private HashMap<String, ArrayList<order>> buyOrders;
    private HashMap<String, ArrayList<order>> sellOrders;
    private HashMap<String, Double> prices;
    private Map<String, ArrayList<Double>> lastPrices;

    public orderManager() {
        buyOrders = new HashMap<>();
        sellOrders = new HashMap<>();
        prices = new HashMap<>();
        lastPrices = new HashMap<>();
        for (String goodName: good.getGoodList()) {
            buyOrders.put(goodName, new ArrayList<>());
            sellOrders.put(goodName, new ArrayList<>());
            prices.put(goodName, good.getBasePrice(goodName));
            lastPrices.put(goodName, new ArrayList<>());
            for (int x = 0; x < 2; x++) {
                lastPrices.get(goodName).add(good.getBasePrice(goodName));
            }
        }
    }
    public void addLastPrice(String goodName) {
        ArrayList<Double> goodNameArray = lastPrices.get(goodName);
        goodNameArray.add(Utils.round(prices.get(goodName), 1));
        if (goodNameArray.size() > 50) {
            lastPrices.get(goodName).removeFirst();
        }
    }
    public boolean getPriceBigger(String goodName) {
        ArrayList<Double> goodNameArray = lastPrices.get(goodName);
        return Utils.round(prices.get(goodName), 1) < Utils.round(goodNameArray.get(goodNameArray.size() - 2), 1);
    }
    public boolean getPriceSmaller(String goodName) {
        ArrayList<Double> goodNameArray = lastPrices.get(goodName);
        return Utils.round(prices.get(goodName), 1) > Utils.round(goodNameArray.get(goodNameArray.size() - 2), 1);
    }
    public Double[] getLastPriceArray(String goodName) {
        return lastPrices.get(goodName).toArray(new Double[0]);
    }
    public Double[] getLastPriceArray(String goodName, int weeks) {

        ArrayList<Double> goodNameArray = lastPrices.get(goodName);
        int size = goodNameArray.size() - 1;
        weeks = Math.min(weeks, size);
        Double[] toReturn = new Double[weeks];
        int index = weeks - 1;
        for (int i = size; i > size - weeks; i--) {
            toReturn[index] = goodNameArray.get(i);
            index--;
        }
        return toReturn;
    }
    public void addBuyOrder(order newOrder) {
        buyOrders.get(newOrder.getGood()).add(newOrder);
    }
    public order[] getBuyOrders(String goodName) {
        order[] toReturn = new order[0];
        if (buyOrders.containsKey(goodName)) {
            toReturn = buyOrders.get(goodName).toArray(new order[0]);
        }
        return toReturn;
    }
    public void addSellOrder(order newOrder) {
        sellOrders.get(newOrder.getGood()).add(newOrder);
    }
    public order[] getSellOrders(String goodName) {
        order[] toReturn = new order[0];
        if (sellOrders.containsKey(goodName)) {
            toReturn = sellOrders.get(goodName).toArray(new order[0]);
        }
        return toReturn;
    }
    public double getBasePrice(String name) {
        return prices.get(name);
    }
    public Double[] getBasePrices() {
        return prices.values().toArray(new Double[0]);
    }
    public void changeBasePrice(String goodName, double price, int amount) {
        double percentage = Math.min((double) amount / 8, 0.5); // Number could be up for change
        double value = prices.get(goodName) * (1 - percentage) + price * percentage;
        prices.put(goodName, value);
    }

    private void cleanOrders() {
        for (String goodName: good.getGoodList()) {
            cleanBuyOrders(goodName);
            cleanSellOrders(goodName);
        }
    }

    private void cleanBuyOrders(String goodName) {
        int size = buyOrders.get(goodName).size();
        for (int i = 0; i < size; i++) {
            order order = buyOrders.get(goodName).get(i);
            if (order.checkOut()) {
                buyOrders.get(goodName).remove(i);
                i--;
                size--;
            } else if (order.incrementAge()) {
                order.getOwner().returnBuy(order);
                buyOrders.get(goodName).remove(i);
                i--;
                size--;
            }
        }
    }
    private void cleanSellOrders(String goodName) {
        int size = sellOrders.get(goodName).size();
        for (int i = 0; i < size; i++) {
            order order = sellOrders.get(goodName).get(i);
            if (order.checkOut()) {
                sellOrders.get(goodName).remove(i);
                i--;
                size--;
            }  else if (order.incrementAge()) {
                order.getOwner().returnSell(order);
                sellOrders.get(goodName).remove(i);
                i--;
                size--;
            }
        }
    }

    public void completeOrders() {
        for (String goodName: prices.keySet()) {
            ArrayList<order> pickFromBuy = buyOrders.get(goodName);
            ArrayList<order> pickFromSell = sellOrders.get(goodName);
            int check = 1;
            while (!pickFromBuy.isEmpty() && !pickFromSell.isEmpty()) {
                check++;
                if (check > 1000) {
                    System.out.println("Exceeded max order limit, something went wrong");
                    System.exit(1);
                }
                order buyOrder = null;
                order sellOrder = null;
                while(!pickFromBuy.isEmpty()) {
                    int num = economy.rand.nextInt(0, pickFromBuy.size());
                    buyOrder = buyOrders.get(goodName).get(num);
                    if (buyOrder.checkValid()) {
                        break;
                    } else {
                        pickFromBuy.remove(num);
                    }
                }

                while (!pickFromSell.isEmpty()) {
                    int num = economy.rand.nextInt(0, pickFromSell.size());
                    sellOrder = sellOrders.get(goodName).get(num);
                    if (sellOrder.checkValid()) {
                        break;
                    } else {
                        pickFromSell.remove(num);
                    }
                }
                if (sellOrder != null && buyOrder.checkValid() && sellOrder.checkValid()) {
                    order.makeDeal(buyOrder, sellOrder);
                }
            }
        }
        cleanOrders();
    }
}

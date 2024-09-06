package core.Managers;

import core.economy;
import core.good;
import core.order;

import java.util.ArrayList;
import java.util.HashMap;

public class orderManager {
    private HashMap<String, ArrayList<order>> buyOrders;
    private HashMap<String, ArrayList<order>> sellOrders;
    private HashMap<String, Double> prices;

    public orderManager() {
        for (String goodName: good.getGoodList()) {
            buyOrders.put(goodName, new ArrayList<>());
            sellOrders.put(goodName, new ArrayList<>());
            prices.put(goodName, 0.0);
        }
    }

    public void addBuyOrder(order newOrder) {
        buyOrders.get(newOrder.getGood()).add(newOrder);
    }
    public void addSellOrder(order newOrder) {
        sellOrders.get(newOrder.getGood()).add(newOrder);
    }
    public double getBasePrice(String name) {
        return prices.get(name);
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
            } else {
                order.getOwner().askToChange(order);
            }
        }
    }

    public void completeOrders() {
        for (String goodName: prices.keySet()) {
            ArrayList<order> pickFromBuy = buyOrders.get(goodName);
            ArrayList<order> pickFromSell = sellOrders.get(goodName);

            while (!pickFromBuy.isEmpty() && !pickFromSell.isEmpty()) {
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

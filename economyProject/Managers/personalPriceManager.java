package core.Managers;

import core.good;
import core.planet;

import java.util.HashMap;

public class personalPriceManager {

    private HashMap<String, Double> lastBuyPrices;
    private HashMap<String, Double> lastSellPrices;
    public personalPriceManager() {
        lastBuyPrices = new HashMap<>();
        lastSellPrices = new HashMap<>();
    }

    public void addLastSoldPrice(String name, double price) {
        lastSellPrices.put(name, price);
    }
    public void addLastBoughtPrice(String name, double price) {
        lastBuyPrices.put(name, price);
    }
    public double getExpectSellPrice(String goodName, planet currPlanet) {
        if (lastSellPrices.containsKey(goodName)) {
            return lastSellPrices.get(goodName);
        } else {
            return currPlanet.getBasePrice(goodName);
        }
    }
    public double getExpectBuyPrice(String goodName, planet currPlanet) {
        if (lastBuyPrices.containsKey(goodName)) {
            return lastBuyPrices.get(goodName);
        } else {
            return currPlanet.getBasePrice(goodName);
        }
    }
}

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
        double basePrice = currPlanet.getBasePrice(goodName);
        double expectPrice = lastSellPrices.getOrDefault(goodName, basePrice);
        if (Math.abs(expectPrice - basePrice) > 0.03) {
            expectPrice = (basePrice + expectPrice) / 2;
        }
        return expectPrice;
    }
    public double getExpectBuyPrice(String goodName, planet currPlanet) {
        double basePrice = currPlanet.getBasePrice(goodName);
        double expectPrice = lastBuyPrices.getOrDefault(goodName, basePrice);
        if (Math.abs(expectPrice - basePrice) > 0.03) {
            expectPrice = (basePrice + expectPrice) / 2;
        }
        return expectPrice;
    }
}

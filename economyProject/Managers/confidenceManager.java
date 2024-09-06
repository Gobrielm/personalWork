package core.Managers;

import core.good;

import java.util.HashMap;

public class confidenceManager {

    private HashMap<String, Integer> buyConfidence;
    private HashMap<String, Integer> sellConfidence;
    public confidenceManager() {
        buyConfidence = new HashMap<>();
        sellConfidence = new HashMap<>();
        for (String good: good.getGoodList()) {
            buyConfidence.put(good, 5);
            sellConfidence.put(good, 5);
        }
    }
    public void changeBuyConfidence(int change, String good) {
        int num = (buyConfidence.get(good) + change);
        num = Math.min(num, 10);
        num = Math.max(num, 0);
        buyConfidence.put(good, num);
    }
    public void changeSellConfidence(int change, String good) {
        int num = (sellConfidence.get(good) + change);
        num = Math.min(num, 10);
        num = Math.max(num, 0);
        sellConfidence.put(good, num);
    }
    public int getBuyConfidence(String goodName) {
        return buyConfidence.get(goodName);
    }
    public int getSellConfidence(String goodName) {
        return sellConfidence.get(goodName);
    }
}

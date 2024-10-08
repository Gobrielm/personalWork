package core.managers;

import core.good;

import java.util.HashMap;

public class confidenceManager {

    private HashMap<String, Integer> buyConfidence;
    private HashMap<String, Integer> sellConfidence;
    public confidenceManager() {
        buyConfidence = new HashMap<>();
        sellConfidence = new HashMap<>();
        for (String good: good.getGoodArray()) {
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
        try {
            return sellConfidence.get(goodName);
        } catch (NullPointerException e) {
            System.out.println(goodName);
            throw e;

        }
    }
    public void degradeConfidence() {
        for (String good: good.getGoodArray()) {
            if (getBuyConfidence(good) > 7) {
                changeBuyConfidence(-1, good);
            } else if (getBuyConfidence(good) < 3) {
                changeBuyConfidence(1, good);
            }
            if (getSellConfidence(good) > 7) {
                changeSellConfidence(-1, good);
            } else if (getSellConfidence(good) < 3) {
                changeSellConfidence(1, good);
            }
        }

    }
}

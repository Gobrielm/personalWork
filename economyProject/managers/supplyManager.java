package core.managers;

import core.company;
import core.good;
import core.recipe;

import java.util.HashMap;

public class supplyManager {
    HashMap<String, info> supplyDemandInfo;

    public supplyManager() {
        supplyDemandInfo = new HashMap<>();
        for (String goodName: good.getGoodList()) {
            supplyDemandInfo.put(goodName, new info());
        }
    }
    public void updateValues(company company) {
        recipe thisRecipe = company.getRecipe();
        String[] goodNamesToChange = thisRecipe.getInputName();
        int[] goodNamesAmounts = thisRecipe.getInputAmount();
        for (int i = 0; i < goodNamesToChange.length; i++) {
            changeDemand(goodNamesToChange[i], goodNamesAmounts[i]);
        }
        goodNamesToChange = thisRecipe.getInputName();
        goodNamesAmounts = thisRecipe.getInputAmount();
        for (int i = 0; i < goodNamesToChange.length; i++) {
            changeSupply(goodNamesToChange[i], goodNamesAmounts[i]);
        }
    }
    public void changeDemand(String goodName, int toChangeBy) {
        supplyDemandInfo.get(goodName).changeDemand(toChangeBy);
    }
    public void changeSupply(String goodName, int toChangeBy) {
        supplyDemandInfo.get(goodName).changeSupply(toChangeBy);
    }
    public int getDemand(String goodName) {
        return supplyDemandInfo.get(goodName).getDemand();
    }
    public int getSupply(String goodName) {
        return supplyDemandInfo.get(goodName).getSupply();
    }

    public class info {
        private int demand;
        private int supply;
        public info() {
            demand = 0;
            supply = 0;
        }
        public void changeDemand(int toChangeBy) {
            demand += toChangeBy;
        }
        public void changeSupply(int toChangeBy) {
            supply += toChangeBy;
        }
        public int getDemand() {
            return demand;
        }
        public int getSupply() {
            return supply;
        }
    }
}

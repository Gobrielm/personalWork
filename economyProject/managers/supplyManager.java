package core.managers;

import core.company;
import core.good;
import core.recipe;

import java.util.HashMap;

public class supplyManager {
    HashMap<String, info> supplyDemandInfo;

    public supplyManager() {
        supplyDemandInfo = new HashMap<>();
        for (String goodName: good.getGoodArray()) {
            supplyDemandInfo.put(goodName, new info());
        }
    }
    public void updateValues(company company, boolean add) {
        recipe thisRecipe = company.getRecipe();
        int removeOrAdd = add ? 1: -1;
        String[] goodNamesToChange = thisRecipe.getInputName();
        int[] goodNamesAmounts = thisRecipe.getInputAmount();
        for (int i = 0; i < goodNamesToChange.length; i++) {
            changeDemand(goodNamesToChange[i], removeOrAdd * goodNamesAmounts[i]);
        }
        goodNamesToChange = thisRecipe.getOutputName();
        goodNamesAmounts = thisRecipe.getOutputAmount();
        for (int i = 0; i < goodNamesToChange.length; i++) {
            changeSupply(goodNamesToChange[i], removeOrAdd * goodNamesAmounts[i]);
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
    public String getGoodNameNoSupplyYesDemand() {
        return getGoodNameSupplyDemand(1);
    }
    public String getGoodNameNoSupplyNoDemand() {
        return getGoodNameSupplyDemand(2);
    }
    public String getGoodNameYesSupplyNoDemand() {
        return getGoodNameSupplyDemand(3);
    }
    private String getGoodNameSupplyDemand(int whichOne) {
        String toReturn = "";
        String[] goodNameArray = good.getGoodArray();
        for (int i = 0; i < goodNameArray.length; i++) {
            String goodName = goodNameArray[i];
            int demand = getDemand(goodName);
            int supply = getSupply(goodName);
            if (supplyDemandCheck(whichOne, demand, supply)) {
                toReturn = goodName;
                break;
            }
        }
        return toReturn;
    }
    private boolean supplyDemandCheck(int whichOne, int demand, int supply) {
        if (whichOne == 1) {
            return demand > 0 && supply == 0;
        } else if (whichOne == 2) {
            return demand == 0 && supply == 0;
        } else {
            return demand == 0 && supply > 0;
        }
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

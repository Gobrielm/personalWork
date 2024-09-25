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
    public int getDiff(String goodName) {
        return getDemand(goodName) - getSupply(goodName);
    }
    public recipe getGoodNameForNewCompany() {
        if (isEconomyComplete()) {
            return getGoodNameYesSupplyYesDemand();
        }
        return getGoodNameForNewCompanyIncompleteEconomy();
    }
    private boolean isEconomyComplete() {
        for (String goodName: good.getGoodArray()) {
            if (supplyDemandInfo.get(goodName).getDemand() == 0 || supplyDemandInfo.get(goodName).getSupply() == 0) {
                return false;
            }
        }
        return true;
    }
    private recipe getGoodNameYesSupplyYesDemand() {
        String toSupply = getGoodNameMaxSupplyDemandDiff();
        return good.getRandRecipeThatCreatesGoodName(toSupply);

    }
    private recipe getGoodNameForNewCompanyIncompleteEconomy() {
        recipe toReturn = null;
        String[] goodNameArray = good.getGoodArray();
        for (int i = 0; i < goodNameArray.length; i++) {
            String goodName = goodNameArray[i];
            int demand = getDemand(goodName);
            int supply = getSupply(goodName);
            toReturn = supplyDemandCheck(demand, supply, goodName);
            if (toReturn != null) {
                break;
            }
        }
        return toReturn;
    }
    private String getGoodNameMaxSupplyDemandDiff() {
        String toReturn = "";
        double maxDiff = 0.0;
        for (String goodName: good.getGoodArray()) {
            double currDiff = getDiff(goodName);
            if (currDiff > maxDiff) {
                maxDiff = currDiff;
                toReturn = goodName;
            }
        }
        return toReturn;
    }
    private recipe supplyDemandCheck(int demand, int supply, String goodName) {
        if (demand > 0 && supply == 0) {
            return good.getRandRecipeThatCreatesGoodName(goodName);
        } else if (demand == 0 && supply > 0) {
            return good.getRandRecipeThatUsesGoodName(goodName);
        } else if (demand == 0 && supply == 0) {
            return good.getRandRecipeThatCreatesGoodName(goodName);
        }
        return null;
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

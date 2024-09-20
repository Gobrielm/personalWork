package core.managers;

import core.*;

import java.util.ArrayList;
import java.util.HashMap;

public class stockManager {
    //GoodName -> List of shares for sale
    private HashMap<String, ArrayList<share>> publicMarket;
    //company -> MapOf ownersNames -> their Share
    private HashMap<company, HashMap<String, share>> privateMarket;
    private HashMap<business, ArrayList<share>> privateOwnership;
    public stockManager() {
        publicMarket = new HashMap<>();
        privateMarket = new HashMap<>();
        privateOwnership = new HashMap<>();
        for (String goodName: good.getGoodArray()) {
            publicMarket.put(goodName, new ArrayList<>());
        }
    }

    public void addShareToStockMarket(share toAdd) {
        company shareOf = toAdd.getPieceOf();
        recipe companyRecipe = shareOf.getRecipe();
        for (String x: companyRecipe.getBothName()) {
            publicMarket.get(x).add(toAdd);
        }
    }
    public void deleteShareFromStockMarket(share toRemove) {
        company shareOf = toRemove.getPieceOf();
        recipe companyRecipe = shareOf.getRecipe();
        for (String x: companyRecipe.getBothName()) {
            publicMarket.get(x).remove(toRemove);
        }
    }
    public void addPrivateCompany(share share) {
        HashMap<String, share> toAdd = new HashMap<>();
        ArrayList<share> listOfOwnedShares = new ArrayList<>();
        toAdd.put(share.getOwner().getName(), share);
        privateMarket.put((company) share.getOwner(), toAdd);
        listOfOwnedShares.add(share);
        privateOwnership.put(share.getOwner(), listOfOwnedShares);
    }

    public void sellShare(company target, business owner, double amount, double price) {
        HashMap<String, share> owners = privateMarket.get(target);
        share shareToSplit = owners.get(owner.getName());
        share shareToSell = splitShare(shareToSplit, amount);
        shareToSell.changePrice(price);
        addShareToStockMarket(shareToSell);
    }

    public share splitShare(share share, double amount) {
        return new share(share, amount);
    }

    public void buyShare(share share, business futureBuyer) {
        business oldOwner = share.getOwner();
        double price = share.getPrice();
        if (futureBuyer.getCash() >= price) {
            futureBuyer.changeCash(-price);
            oldOwner.changeCash(price);
            share.changeOwner(futureBuyer);
            deleteShareFromStockMarket(share);
        }
    }

    public double getOwnershipAmount(business owner, company toCheck) {
        double toReturn = 0.0;
        if (privateMarket.containsKey(toCheck) && privateMarket.get(toCheck).containsKey(owner.getName())) {
            toReturn = privateMarket.get(toCheck).get(owner.getName()).getAmount();
        }
        return toReturn;
    }

    public share[] getOwnedShares(business owner) {
        return privateOwnership.get(owner).toArray(new share[0]);
    }

    public share getRandomShare(String goodName) {
        ArrayList<share> listOfShares = publicMarket.get(goodName);
        int randNum = economy.rand.nextInt(0, listOfShares.size());
        return listOfShares.get(randNum);
    }

}

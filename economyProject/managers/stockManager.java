package core.managers;

import core.*;

import java.util.ArrayList;
import java.util.HashMap;

public class stockManager {
    //GoodName -> List of shares for sale
    private HashMap<String, ArrayList<share>> stockMarket;
    //company -> MapOf ownersNames -> their Share
    private HashMap<company, HashMap<String, share>> stockManager;
    public stockManager() {
        stockMarket = new HashMap<>();
        stockManager = new HashMap<>();
        for (String goodName: good.getGoodList()) {
            stockMarket.put(goodName, new ArrayList<>());
        }
    }

    public void addShareToStockMarket(share toAdd) {
        company shareOf = toAdd.getPieceOf();
        recipe companyRecipe = shareOf.getRecipe();
        for (String x: companyRecipe.getBothName()) {
            stockMarket.get(x).add(toAdd);
        }
    }
    public void deleteShareFromStockMarket(share toRemove) {
        company shareOf = toRemove.getPieceOf();
        recipe companyRecipe = shareOf.getRecipe();
        for (String x: companyRecipe.getBothName()) {
            stockMarket.get(x).remove(toRemove);
        }
    }
    public void addPrivateCompany(share share) {
        HashMap<String, share> toAdd = new HashMap<>();
        toAdd.put(share.getOwner().getName(), share);
        stockManager.put((company) share.getOwner(), toAdd);
    }

    public void sellShare(company target, business owner, double amount, double price) {
        HashMap<String, share> owners = stockManager.get(target);
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

}

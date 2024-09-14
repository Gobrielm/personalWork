package core.managers;

import core.business;
import core.company;
import core.good;
import core.share;

import java.util.ArrayList;
import java.util.HashMap;

public class stockManager {
    //GoodName -> List of shares for sale
    HashMap<String, ArrayList<share>> stockMarket;
    //company -> MapOf ownersNames -> their Share
    HashMap<company, HashMap<String, share>> stockManager;
    public stockManager() {
        stockMarket = new HashMap<>();
        stockManager = new HashMap<>();
        for (String goodName: good.getGoodList()) {
            stockMarket.put(goodName, new ArrayList<>());
        }
    }

    public void addShareToStockMarket(share toAdd) {

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
}

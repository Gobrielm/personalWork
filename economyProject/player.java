package core;

import core.playerPackage.playerCompany;
import core.playerPackage.playerCompanyStorage;

import java.util.HashMap;

public class player implements business{
    private int id;
    private String name;
    private static int numPlayers = 0;
    private double cash;
    private int planet;
    private HashMap<String, Integer> storage;
    private playerCompanyStorage ownedCompanies;
    public player(String name) {
        this.id = numPlayers;
        storage = new HashMap<>();
        ownedCompanies = new playerCompanyStorage(this);
        for (String goodName: good.getGoodArray()) {
            storage.put(goodName, 0);
        }
        this.name = name;
        numPlayers++;
        cash = 500;
        planet = 0;
        //Todo determine a way for the player to change current Orders or have ai manage them
        //Todo for now ai will manage them
    }
    @Override
    public void changeCash(double amount) {
        cash += amount;
    }
    public planet getPlanet() {
        return economy.getPlanetFromID(planet);
    }
    public int getPlanetId() {
        return planet;
    }
    public void setPlanet(int id) {
        planet = id;
    }
    public String getName() {
        return name;
    }
    public double getCash() {
        return cash;
    }
    public void changeStorage(String goodName, int amount) {
        storage.put(goodName, storage.get(goodName) + amount);
    }
    public int getAmount(String goodName) {
        return storage.get(goodName);
    }
    @Override
    public boolean checkDeal(order thisOrder, order otherOrder) {
        double priceDiff = thisOrder.getPrice() - otherOrder.getPrice();
        double price = thisOrder.getPrice();
        double change = Math.abs(priceDiff / price);
        if (thisOrder.isBuyOrder() && priceDiff > 0) {
            return true;
        } else if (!thisOrder.isBuyOrder() && priceDiff < 0) {
            return true;
        }
        return change < 0.02;
    }

    @Override
    public void adjustDeal(order thisOrder) {
        if (thisOrder.isBuyOrder()) {
            thisOrder.setPrice(thisOrder.getPrice() * (1.01));
        } else {
            thisOrder.setPrice(thisOrder.getPrice() * (0.99));
        }
    }

    @Override
    public void buyGood(int amount, String goodName, double price) {
        changeStorage(goodName, amount);
        cash -= price * amount;
    }

    @Override
    public void sellGood(int amount, String goodName, double price) {
        changeStorage(goodName, -amount);
        cash += price * amount;
    }

    @Override
    public void returnBuy(order order) {
        //Nothing to return
    }

    @Override
    public void returnSell(order order) {
        changeStorage(order.getGood(), order.getAmount());
    }

    @Override
    public int getMaxAge() {
        return 20;
    }

    //Note: playerCompanies
    public void addNewCompany(recipe recipe) {
        ownedCompanies.addNewCompany(recipe);
    }
    public void tick() {
        ownedCompanies.tick();
    }
    public playerCompany[] getCompaniesByGoodName(String goodName) {
        return ownedCompanies.getCompaniesByGoodName(goodName);
    }
}

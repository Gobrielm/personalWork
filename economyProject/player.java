package core;

public class player implements business{
    private int id;
    private String name;
    private static int numPlayers = 0;
    private double cash;
    private int planet;

    public player(String name) {
        this.id = numPlayers;
        this.name = name;
        numPlayers++;
        cash = 500;
        planet = 0;
    }
    public void changeCash(double amount) {
        cash += amount;
    }
    public planet getPlanet() {
        return economy.getPlanetFromID(planet);
    }
    public String getName() {
        return name;
    }
    public double getCash() {
        return cash;
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
}

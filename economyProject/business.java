package core;

public interface business {

    public String getName();

    public planet getPlanet();

    public double getCash();

    public boolean checkDeal(order order1, order order2);

    public void adjustDeal(order thisOrder);

    public void buyGood(int amount, String good, double price);

    public void sellGood(int amount, String good, double price);
}

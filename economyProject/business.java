package core;

public interface business {

    public String getName();

    public planet getPlanet();

    public double getCash();

    public boolean checkDeal(order order1, order order2);
}

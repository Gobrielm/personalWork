package core;

public class player {
    private int id;
    private String name;
    private static int numPlayers = 0;
    private double cash;
    private int planet;
    private String goodSelected;
    public player(String name) {
        this.id = numPlayers;
        this.name = name;
        numPlayers++;
        cash = 500;
        planet = 0;
        goodSelected = "";
    }
    public void setGoodSelected(String goodSelected) {
        this.goodSelected = goodSelected;
    }
    public String getGoodSelected() {
        return goodSelected;
    }
    public int getPlanet() {
        return planet;
    }
    public String getName() {
        return name;
    }
    public double getCash() {
        return cash;
    }
}

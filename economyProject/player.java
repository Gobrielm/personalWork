package core;

public class player {
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
}

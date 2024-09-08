package core;

import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class economy {
    private static ArrayList<planet> galacticMarket;
    private static ArrayList<player> playerList;
    public static int dayCount = 0;
    public static Random rand;
    public economy(long seed, int playerCount) {
        rand = new Random(seed);
        good.createGoodList();
        good.createRecipes();
        good.createNames();
        galacticMarket = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            planet temp = new planet();
            galacticMarket.add(temp);
        }
        playerList = new ArrayList<>();
        for (int i = 0; i < playerCount; i++) {
            playerList.add(new player("Player" + i));
        }
    }
    public planet getPlanet(int id) {
        return galacticMarket.get(id);
    }
    public boolean tick() {
        dayCount++;
        playerTurn();
        computerTurn();
        return true;
    }
    public void playerTurn() {
        int index = 0;
        boolean done = false;
        while (index < playerList.size()) {
            player currPlayer = playerList.get(index);
            graphicalInterface.drawPlanetMenu(currPlayer);
            while (!done) {
                if (StdDraw.hasNextKeyTyped()) {
                    char c = StdDraw.nextKeyTyped();
                    if (graphicalInterface.keyBoardInput(currPlayer, c)) {
                        done = true;
                    }
                }
                StdDraw.pause(10);
            }
            done = false;
            index++;
            graphicalInterface.resetStrings();
        }
    }
    public void computerTurn() {
        for (planet x: galacticMarket) {
            x.planetTick();
        }
    }

    public static planet getPlanetFromID(int ID) {
        return galacticMarket.get(ID);
    }
}

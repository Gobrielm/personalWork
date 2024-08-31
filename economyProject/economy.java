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
        galacticMarket = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            planet temp = new planet(i);
            galacticMarket.add(temp);
        }
        playerList = new ArrayList<>();
        for (int i = 0; i < playerCount; i++) {
            playerList.add(new player("Player" + i));
        }
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
                if (StdDraw.isMousePressed()) {
                    double x = StdDraw.mouseX() / graphicalInterface.WIDTH;
                    double y = StdDraw.mouseY() / graphicalInterface.HEIGHT;
                    if (x > 0.91 && x < 0.99 && y > 0.91 && y < 0.99) {
                        done = true;
                    } else if (x > 0.3 && x < 0.51) {
                        int temp = (int) Math.round((y * (good.getGoodList().length + 1) - 1));
                        graphicalInterface.drawGoods(currPlayer, good.getGoodList()[temp]);
                    }
                    StdDraw.pause(100);
                }
            }
            done = false;
            index++;
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

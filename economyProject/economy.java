package core;

import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.Random;

public class economy {
    private static ArrayList<planet> galacticMarket;
    private static ArrayList<player> playerList;
    public static int dayCount = 0;
    public static Random rand;
    public static void initialize() {
        good.createGoodList();
        good.createRecipes();
        good.createNames();
    }
    public economy(long seed, int playerCount) {
        rand = new Random(seed);
        initialize();
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
                if (StdDraw.isMousePressed()) {
                    double x = StdDraw.mouseX() / graphicalInterface.WIDTH;
                    double y = StdDraw.mouseY() / graphicalInterface.HEIGHT;
                    if (x > 0.333 && x < 0.47333) {
                        int order = (int) ((y * 24.5) - 1);
                        if (order >= 0 && order < good.getGoodList().length) {
                            String goodName = good.getGoodList()[order];
                            graphicalInterface.changeGoodSelected(currPlayer, goodName);
                        }
                    } else if (x > 0.136 && x < 0.196 && y < (0.25 + 0.041667) && y > (0.1667 - 0.041667)) {
                        int column = (int) (Utils.round(x - 0.146, 0.02) / 0.02) + 1;
                        int row = (int) (Utils.round(0.25 - y, 0.041667) / 0.041667);
                        int num = row * 3 + column;
                        graphicalInterface.selectPlanet(currPlayer, num);

                    }
                    StdDraw.pause(100);
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

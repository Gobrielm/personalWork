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
        good.initialize();
    }
    public economy(long seed, int playerCount) {
        rand = new Random(seed);
        initialize();
        galacticMarket = new ArrayList<>();
        planet temp = new planet();
        galacticMarket.add(temp);
        playerList = new ArrayList<>();
        for (int i = 0; i < playerCount; i++) {
            player newPlayer = new player("Player" + i);
            //Each player starts with one random primary industry
            newPlayer.addNewCompany(good.randBeginNodeRecipe());
            playerList.add(newPlayer);
        }
        graphicalInterface.createButtons();
    }
    public boolean tick() {
        dayCount++;
        playerTurn();
        computerTurn();
        return true;
    }
    public boolean mouseActions(player currPlayer, double x, double y) {
        return graphicalInterface.mouseInput(currPlayer, x, y);
    }
    public void playerTurn() {
        int index = 0;
        while (index < playerList.size()) {
            player currPlayer = playerList.get(index);
            currPlayer.tick();
            graphicalInterface.updateScreen(currPlayer);
            while (true) {
                if (StdDraw.hasNextKeyTyped()) {
                    char c = StdDraw.nextKeyTyped();
                    if (graphicalInterface.keyBoardInput(currPlayer, c)) {
                        break;
                    }
                }
                if (StdDraw.isMousePressed()) {
                    double x = StdDraw.mouseX() / graphicalInterface.WIDTH;
                    double y = StdDraw.mouseY() / graphicalInterface.HEIGHT;
                    if (mouseActions(currPlayer, x, y)) {
                        StdDraw.pause(100);
                        break;
                    }
                    StdDraw.pause(100);
                }
                StdDraw.pause(10);
            }
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

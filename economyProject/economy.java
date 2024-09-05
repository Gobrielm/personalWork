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
        boolean typing1 = false;
        boolean typing2 = false;
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
                        if (currPlayer.setGoodSelected(good.getGoodList()[temp])) {
                            graphicalInterface.textboxAmount = "";
                            graphicalInterface.textboxPrice = "";
                        }
                        graphicalInterface.drawStuff(currPlayer, 0);
                        //For buttons 1-3
                    } else if (x > (0.0833 - 0.0415) && x < (0.249 + 0.0415) && y > (0.5 - 0.034) && y < (0.5 + 0.034)) {
                        double newX = (x - 0.0833 + 0.0415) / (2 * 0.0415);
                        graphicalInterface.drawStuff(currPlayer, (int) Math.ceil(newX));
                        //For buttons 4-5
                    } else if (x > (0.0833 - 0.0415) && x < (0.166 + 0.0415) && y > (0.433 - 0.034) && y < (0.433 + 0.034)) {
                        double newX = (x - 0.0833 + 0.0415) / (2 * 0.0415) + 3;
                        graphicalInterface.drawStuff(currPlayer, (int) Math.ceil(newX));
                        //For buttons 6-8
                    } else if (x > (0.588 - 0.0415) && x < (0.588 + 0.0415) && y > (0.433 - 0.034) && y < (0.567 + 0.034)) {
                        double newY = -(y - 0.433 + 0.034) / (2 * 0.034) + 8;
                        graphicalInterface.drawStuff(currPlayer, (int) Math.ceil(newY));
                    } else if (x > (0.833 - 0.133) && x < (0.833 + 0.133) && y > (0.55 - 0.025) && y < (0.55 + 0.025)) {
                        typing1 = true;
                        typing2 = false;
                    } else if (x > (0.833 - 0.133) && x < (0.833 + 0.133) && y > (0.45 - 0.025) && y < (0.45 + 0.025)) {
                        typing1 = false;
                        typing2 = true;
                    } else if (x > (0.833 - 0.03) && x < (0.833 + 0.03) && y > (0.35 - 0.025) && y < (0.35 + 0.025)) {
                        graphicalInterface.createOrder(currPlayer);
                    } else {
                        typing1 = false;
                        typing2 = false;
                    }
                    StdDraw.pause(100);
                }
                if (StdDraw.hasNextKeyTyped() && (typing1 || typing2)) {
                    char c = StdDraw.nextKeyTyped();
                    if (graphicalInterface.button == 6) {
                        if (typing1) {
                            graphicalInterface.drawTextbox(currPlayer, c, 1);
                        } else {
                            graphicalInterface.drawTextbox(currPlayer, c, 2);
                        }

                    } else if (graphicalInterface.button == 7) {
                        if (typing1) {
                            graphicalInterface.drawTextbox(currPlayer, c, 1);
                        } else {
                            graphicalInterface.drawTextbox(currPlayer, c, 2);
                        }
                    }
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

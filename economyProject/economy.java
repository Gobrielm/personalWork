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
                            graphicalInterface.textbox1 = "";
                            graphicalInterface.textbox2 = "";
                        }
                        graphicalInterface.drawStuff(currPlayer);
                    } else if (x > (0.0833 - 0.0415) && x < (0.0833 + 0.0415) && y > (0.5 - 0.034) && y < (0.5 + 0.034)) {
                        graphicalInterface.button = 1;
                        graphicalInterface.drawStuff(currPlayer);
                    } else if (x > (0.166 - 0.0415) && x < (0.166 + 0.0415) && y > (0.5 - 0.034) && y < 0.5 + 0.034) {
                        graphicalInterface.button = 2;
                        graphicalInterface.drawStuff(currPlayer);
                    } else if (x > (0.249 - 0.0415) && x < (0.249 + 0.0415) && y > (0.5 - 0.034) && y < 0.5 + 0.034) {
                        graphicalInterface.button = 3;
                        graphicalInterface.drawStuff(currPlayer);
                    } else if (x > (0.0833 - 0.0415) && x < (0.0833 + 0.0415) && y > (0.433 - 0.034) && y < (0.433 + 0.034)) {
                        graphicalInterface.button = 4;
                        graphicalInterface.drawStuff(currPlayer);
                    } else if (x > (0.166 - 0.0415) && x < (0.166 + 0.0415) && y > (0.433 - 0.034) && y < (0.433 + 0.034)) {
                        graphicalInterface.button = 5;
                        graphicalInterface.drawStuff(currPlayer);
                    } else if (x > (0.588 - 0.0415) && x < (0.588 + 0.0415) && y > (0.567 - 0.034) && y < (0.567 + 0.034)) {
                        graphicalInterface.button = 6;
                        graphicalInterface.drawStuff(currPlayer);
                        graphicalInterface.drawStuff(currPlayer);
                    } else if (x > (0.588 - 0.0415) && x < (0.588 + 0.0415) && y > (0.5 - 0.034) && y < (0.5 + 0.034)) {
                        graphicalInterface.button = 7;
                        graphicalInterface.drawStuff(currPlayer);
                    } else if (x > (0.588 - 0.0415) && x < (0.588 + 0.0415) && y > (0.433 - 0.034) && y < (0.433 + 0.034)) {
                        graphicalInterface.button = 8;
                        graphicalInterface.drawStuff(currPlayer);
                    } else if (x > (0.833 - 0.133) && x < (0.833 + 0.133) && y > (0.55 - 0.025) && y < (0.55 + 0.025)) {
                        typing1 = true;
                        typing2 = false;
                    } else if (x > (0.833 - 0.133) && x < (0.833 + 0.133) && y > (0.45 - 0.025) && y < (0.45 + 0.025)) {
                        typing1 = false;
                        typing2 = true;
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

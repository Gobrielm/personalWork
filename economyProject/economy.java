package core;

import java.util.ArrayList;
import java.util.HashMap;

public class economy {
    private static ArrayList<planet> galacticMarket;
    private static HashMap<String, ArrayList<order>> buyOrders;
    private static HashMap<String, ArrayList<order>> sellOrders;
    private static ArrayList<player> playerList;
    public economy(long seed, int playerCount) {
        good.createGoodList();
        good.createRecipes();
        planet.setSeed(seed);
        good.setSeed(seed);
        company.setSeed(seed);
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

    public void tick() {
        int currentPlayer = 0;

    }
}

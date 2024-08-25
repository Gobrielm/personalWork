package core;

import java.util.ArrayList;
import java.util.HashMap;

public class economy {
    private static ArrayList<planet> galacticMarket;
    private static HashMap<String, ArrayList<order>> buyOrders;
    private static HashMap<String, ArrayList<order>> sellOrders;
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
    }
}

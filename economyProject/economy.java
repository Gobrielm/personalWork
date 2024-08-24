package core;

import java.util.ArrayList;

public class economy {
    private static ArrayList<planet> galacticMarket;

    public economy(long seed) {
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

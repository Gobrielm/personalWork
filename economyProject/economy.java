package core;

import java.util.ArrayList;

public class economy {
    private static ArrayList<planet> galacticMarket;

    public economy() {
        good.createGoodList();
        galacticMarket = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            planet temp = new planet(i);
            galacticMarket.add(temp);
        }
    }
}

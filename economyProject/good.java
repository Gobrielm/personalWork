package core;

import java.util.*;

public class good {
    private String name;
    private int basePrice;
    private int amount;
    private static ArrayList<good> goodList = new ArrayList<>();
    private static TreeSet<good> primarySet;
    private static HashMap<good[], good> recipes = new HashMap<good[], good>();
    private static long seed;
    public static void createGoodList() {
        goodList.add(new good("Bismuth", 5, 0));
        goodList.add(new good("Osmium", 5, 0));
        goodList.add(new good("Copper", 5, 0));
        goodList.add(new good("Gold", 5, 0));
        goodList.add(new good("Zinc", 5, 0));
        goodList.add(new good("Manganese", 5, 0));
        goodList.add(new good("Argon", 5, 0));
        goodList.add(new good("Oxygen", 5, 0));
        goodList.add(new good("Hydrogen", 5, 0));
        goodList.add(new good("Xenon", 5, 0));
        goodList.add(new good("Carbon", 5, 0));
        goodList.add(new good("Water", 5, 0));
        primarySet = new TreeSet<>(goodList);
        goodList.add(new good("Argonium", 5, 0));
        goodList.add(new good("Wires", 5, 0));
        goodList.add(new good("Bismanol", 5, 0));
        goodList.add(new good("Brass", 5, 0));
        goodList.add(new good("Batteries", 5, 0));
        goodList.add(new good("CopperHydride", 5, 0));
        goodList.add(new good("XenonTetroxide", 5, 0));
        goodList.add(new good("Butane", 5, 0));
        goodList.add(new good("IonFuel", 5, 0));
        goodList.add(new good("Weapons", 5, 0));
        goodList.add(new good("PerxenicAcid", 5, 0));
    }

    public good(String newName, int newPrice, int amount) {
        name = newName;
        basePrice = newPrice;
        this.amount = amount;
    }
    public static good randGood() {
        Random rand = new Random(seed);
        return primaryList.get(rand.nextInt(0, primaryList.size()));
    }
    public static good randGood(good primary) {
        Random rand = new Random(seed);
        return primaryList.get(rand.nextInt(0, primaryList.size()));
    }

    public void setSeed(long newSeed) {
        seed = newSeed;
    }
    public int getAmount() {
        return this.amount;
    }
    public void changeAmount(int amount) {
        this.amount += amount;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof good) {
            return Objects.equals(name, ((good) obj).name);
        }
        return false;
    }
}

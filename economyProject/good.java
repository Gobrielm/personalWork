package core;

import java.util.*;

public class good {
    private String name;
    private int amount;
    private int basePrice;
    private static ArrayList<recipe> recipeList = new ArrayList<>();
    private static HashMap<String, good> goodMap;
    private static long seed;
    public static void createGoodList() {
        goodMap = new HashMap<>();
        goodMap.put("Bismuth", new good("Bismuth", 5, 0));
        goodMap.put("Osmium", new good("Osmium", 5, 0));
        goodMap.put("Copper", new good("Copper", 5, 0));
        goodMap.put("Gold", new good("Gold", 5, 0));
        goodMap.put("Zinc", new good("Zinc", 5, 0));
        goodMap.put("Manganese", new good("Manganese", 5, 0));
        goodMap.put("Argon", new good("Argon", 5, 0));
        goodMap.put("Oxygen", new good("Oxygen", 5, 0));
        goodMap.put("Hydrogen", new good("Hydrogen", 5, 0));
        goodMap.put("Xenon", new good("Xenon", 5, 0));
        goodMap.put("Carbon", new good("Carbon", 5, 0));
        goodMap.put("Water", new good("Water", 5, 0));
        goodMap.put("Argonium", new good("Argonium", 5, 0));
        goodMap.put("Wires", new good("Wires", 5, 0));
        goodMap.put("Bismanol", new good("Bismanol", 5, 0));
        goodMap.put("Brass", new good("Brass", 5, 0));
        goodMap.put("Batteries", new good("Batteries", 5, 0));
        goodMap.put("CopperHydride", new good("CopperHydride", 5, 0));
        goodMap.put("XenonTetroxide", new good("XenonTetroxide", 5, 0));
        goodMap.put("Butane", new good("Butane", 5, 0));
        goodMap.put("IonFuel", new good("IonFuel", 5, 0));
        goodMap.put("Weapons", new good("Weapons", 5, 0));
        goodMap.put("PerxenicAcid", new good("PerxenicAcid", 5, 0));

    }
    public static void createRecipes() {
        recipeList.add(new recipe(null, goodMap.get("Bismuth"), 5, 0));
    }

    public good(String newName, int basePrice, int amount) {
        name = newName;
        this.amount = amount;
        this.basePrice = basePrice;
    }
    public String getName() {
        return name;
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

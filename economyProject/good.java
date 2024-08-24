package core;

import java.util.*;

public class good {
    private String name;
    private int amount;
    private static ArrayList<recipe> primaryRecipes = new ArrayList<>();
    private static ArrayList<recipe> secondaryRecipes = new ArrayList<>();
    private static HashMap<String, Integer> basePrices;
    private static long seed;
    public static void createGoodList() {
        basePrices = new HashMap<>();
        basePrices.put("Bismuth", 5);
        basePrices.put("Osmium", 5);
        basePrices.put("Copper", 5);
        basePrices.put("Gold", 5);
        basePrices.put("Zinc", 5);
        basePrices.put("Manganese", 5);
        basePrices.put("Argon", 5);
        basePrices.put("Oxygen", 5);
        basePrices.put("Hydrogen", 5);
        basePrices.put("Xenon", 5);
        basePrices.put("Carbon", 5);
        basePrices.put("Water", 5);
        basePrices.put("Argonium", 5);
        basePrices.put("Wires", 5);
        basePrices.put("Bismanol", 5);
        basePrices.put("Brass", 5);
        basePrices.put("Batteries", 5);
        basePrices.put("CopperHydride", 5);
        basePrices.put("XenonTetroxide", 5);
        basePrices.put("Butane", 5);
        basePrices.put("IonFuel", 5);
        basePrices.put("Weapons", 5);
        basePrices.put("PerxenicAcid", 5);
    }
    public static void createRecipes() {
        primaryRecipes.add(new recipe(new good[]{}, new good[]{new good("Copper", 1)}, 2, 0));
        primaryRecipes.add(new recipe(new good[]{}, new good[]{new good("Zinc", 1)}, 2, 0));
        primaryRecipes.add(new recipe(new good[]{}, new good[]{new good("Gold", 1)}, 2, 0));
        primaryRecipes.add(new recipe(new good[]{}, new good[]{new good("Bismuth", 1)}, 0, 0));
        secondaryRecipes.add(new recipe(new good[]{new good("Zinc", 1), new good("Copper", 2)}, new good[]{new good("Brass", 1)}, 3, 0));
        secondaryRecipes.add(new recipe(new good[]{new good("Bismuth", 1)}, new good[]{}, 0, 10));
        secondaryRecipes.add(new recipe(new good[]{new good("Brass", 1)}, new good[]{}, 0, 35));
    }

    public good(String newName, int amount) {
        name = newName;
        this.amount = amount;
    }
    public String getName() {
        return name;
    }
    public static recipe randPrimaryRecipe() {
        Random rand = new Random(seed);
        return primaryRecipes.get(rand.nextInt(0, primaryRecipes.size()));
    }
    public static recipe randSecondaryRecipe() {
        Random rand = new Random(seed);
        return secondaryRecipes.get(rand.nextInt(0, secondaryRecipes.size()));
    }

    public static void setSeed(long newSeed) {
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

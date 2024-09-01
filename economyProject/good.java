package core;

import java.util.*;

public class good {
    private String name;
    private int amount;
    private static ArrayList<recipe> primaryRecipes = new ArrayList<>();
    private static ArrayList<recipe> secondaryRecipes = new ArrayList<>();
    private static HashMap<String, Double> basePrices;
    public static void createGoodList() {
        basePrices = new HashMap<>();
        basePrices.put("Bismuth", 8.0);
        basePrices.put("Osmium", 5.0);
        basePrices.put("Copper", 5.0);
        basePrices.put("Gold", 10.0);
        basePrices.put("Zinc", 5.0);
        basePrices.put("Manganese", 5.0);
        basePrices.put("Argon", 5.0);
        basePrices.put("Oxygen", 5.0);
        basePrices.put("Hydrogen", 5.0);
        basePrices.put("Xenon", 5.0);
        basePrices.put("Carbon", 5.0);
        basePrices.put("Water", 5.0);
        basePrices.put("Argonium", 5.0);
        basePrices.put("Wires", 5.0);
        basePrices.put("Bismanol", 5.0);
        basePrices.put("Brass", 15.0);
        basePrices.put("Batteries", 5.0);
        basePrices.put("CopperHydride", 5.0);
        basePrices.put("XenonTetroxide", 5.0);
        basePrices.put("Butane", 5.0);
        basePrices.put("IonFuel", 5.0);
        basePrices.put("Weapons", 5.0);
        basePrices.put("PerxenicAcid", 5.0);
    }
    public static void createRecipes() {
        primaryRecipes.add(new recipe(new good[]{}, new good[]{new good("Copper", 1)}, 3, 0));
        primaryRecipes.add(new recipe(new good[]{}, new good[]{new good("Zinc", 1)}, 3, 0));
        primaryRecipes.add(new recipe(new good[]{}, new good[]{new good("Gold", 1)}, 3, 0));
        primaryRecipes.add(new recipe(new good[]{}, new good[]{new good("Bismuth", 1)}, 3, 0));
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
    public static String[] getGoodList() {
        return basePrices.keySet().toArray(new String[0]);
    }
    public static Double[] getPriceList() {
        return basePrices.values().toArray(new Double[0]);
    }
    public static double getBasePrice(String name) {
        return basePrices.get(name);
    }
    public static void changeBasePrice(String name, double price, int amount) {
        double percentage = (double) amount / 20;
        if (percentage > 0.5) {
            percentage = 0.5;
        }
        double value = basePrices.get(name) * (1 - percentage) + price * percentage;
        basePrices.put(name, value);
    }
    public static recipe randPrimaryRecipe() {
        return primaryRecipes.get(economy.rand.nextInt(0, primaryRecipes.size()));
    }
    public static recipe randSecondaryRecipe() {
        return secondaryRecipes.get(economy.rand.nextInt(0, secondaryRecipes.size()));
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

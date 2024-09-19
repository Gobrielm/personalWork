package core;

import core.constants.companyNames;
import org.checkerframework.checker.units.qual.A;

import java.util.*;

public class good {
    private String name;
    private int amount;
    private static HashMap<String, ArrayList<recipe>> recipes = new HashMap<>();
    private static HashMap<String, Double> basePrices;
    private static companyNames companyNames = new companyNames();
    public static void createGoodList() {
        basePrices = new HashMap<>();
        basePrices.put("Osmium", 5.0);
        basePrices.put("Copper", 5.0);
        basePrices.put("Zinc", 5.0);
        basePrices.put("Manganese", 5.0);
        basePrices.put("Argon", 5.0);
        basePrices.put("Oxygen", 5.0);
        basePrices.put("Hydrogen", 5.0);
        basePrices.put("Carbon", 5.0);
        basePrices.put("Water", 5.0);
        basePrices.put("Bismuth", 5.0);
        basePrices.put("Xenon", 5.0);

        basePrices.put("Gold", 10.0);
        basePrices.put("Butane", 10.0);

        basePrices.put("Wires", 20.0);
        basePrices.put("Bismanol", 20.0);
        basePrices.put("CopperHydride", 20.0);
        basePrices.put("IonFuel", 20.0);

        basePrices.put("Batteries", 25.0);
        basePrices.put("Argonium", 25.0);
        basePrices.put("Brass", 25.0);

        basePrices.put("XenonTetroxide", 30.0);
        basePrices.put("PerxenicAcid", 40.0);

        basePrices.put("Weapons", 150.0);

        for (String x: basePrices.keySet()) {
            recipes.put(x, new ArrayList<>());
        }
    }
    public static void primaryRecipeMaker(String goodName, int amount, int expenses) {
        recipes.get(goodName).add(new recipe(new good[]{}, new good[]{new good(goodName, amount)}, expenses, 0));
    }
    public static void secondaryRecipeMaker(String goodName1, String goodName2, int amount1, int amount2, String output, int amount3, int expenses) {
        recipe toAdd = new recipe(new good[]{new good(goodName1, amount1), new good(goodName2, amount2)}, new good[]{new good(output, amount3)}, expenses, 0);
        recipes.get(goodName1).add(toAdd);
        recipes.get(goodName2).add(toAdd);
        recipes.get(output).add(toAdd);
    }
    public static void secondaryRecipeMaker(String[] input, int[] inputAmount, String[] output, int[] outputAmount, int expenses, double income) {
        recipe toAdd = new recipe(input, inputAmount, output, outputAmount, expenses, income);
        for (String x: input) {
            recipes.get(x).add(toAdd);
        }
        for (String x: output) {
            recipes.get(x).add(toAdd);
        }
    }
    public static void secondaryRecipeMaker(String goodName, int amount, String output, int amount1, int expenses) {
        recipe toAdd = new recipe(new good[]{new good(goodName, amount)}, new good[]{new good(output, amount1)}, expenses, 0);
        recipes.get(goodName).add(toAdd);
        recipes.get(output).add(toAdd);
    }
    public static void createRecipes() {
        primaryRecipeMaker("Copper", 1, 3);
        primaryRecipeMaker("Zinc", 1, 3);
        primaryRecipeMaker("Gold", 1, 6);
        primaryRecipeMaker("Bismuth", 1, 4);
        primaryRecipeMaker("Osmium", 1, 8);
        primaryRecipeMaker("Manganese", 1, 5);
        primaryRecipeMaker("Oxygen", 1, 1);
        primaryRecipeMaker("Hydrogen", 1, 2);
        primaryRecipeMaker("Xenon", 1, 5);
        primaryRecipeMaker("Water", 1, 1);
        primaryRecipeMaker("Argon", 1, 1);
        primaryRecipeMaker("Carbon", 1, 1);

        secondaryRecipeMaker("Zinc", "Copper", 1, 2, "Brass", 1, 3);
        secondaryRecipeMaker("Osmium", "Argon", 1, 2, "Argonium", 1, 3);
        secondaryRecipeMaker("Zinc", "Manganese", 2, 1, "Batteries", 1, 3);
        secondaryRecipeMaker("Xenon", "Water", 3, 1, "PerxenicAcid", 1, 5);
        secondaryRecipeMaker("Carbon", "Hydrogen", 1, 2, "Butane", 2, 3);
        secondaryRecipeMaker("Xenon", "Hydrogen", 4, 1, "IonFuel", 2, 5);
        secondaryRecipeMaker("Xenon", "Oxygen", 1, 3, "XenonTetroxide", 1, 4);
        secondaryRecipeMaker("Copper", "Hydrogen", 2, 3, "CopperHydride", 2, 4);
        secondaryRecipeMaker("Bismuth", "Manganese", 1, 1, "Bismanol", 1, 5);
        secondaryRecipeMaker("Copper", 2, "Wires", 1, 4);
        secondaryRecipeMaker(new String[]{"Argonium", "PerxenicAcid", "Bismanol"}, new int[]{1, 2, 1}, new String[]{"Weapons"}, new int[]{1}, 3, 0);
        //Add a buyer for every type of good
        for (String key: basePrices.keySet()) {
            secondaryRecipeMaker(new String[]{key}, new int[]{1}, new String[]{}, new int[]{}, 0, basePrices.get(key));
        }
    }


    public static String randGoodName() {
        int randNum = economy.rand.nextInt(0, basePrices.size());
        return basePrices.keySet().toArray(new String[0])[randNum];
    }

    public static String pickRandName() {
        int randNum = economy.rand.nextInt(0, companyNames.getSize());
        return companyNames.getIndex(randNum);
    }
    public static recipe[] primaryRecipeWithGood(String goodName) {
        ArrayList<recipe> toReturn = new ArrayList<>();
        for (recipe x: recipes.get(goodName)) {
            if (doesRecipeMakeGoodName(x, goodName)) {
                toReturn.add(x);
                for (String s : x.getInputName()) {
                    ArrayList<recipe> toAdd = new ArrayList<>(List.of(primaryRecipeWithGood(s)));
                    toReturn.addAll(toAdd);
                }
            }
        }
        return toReturn.toArray(new recipe[0]);
    }
    public static recipe getBuyer(String goodName) {
        for (recipe x: recipes.get(goodName)) {
            if (x.getOutputName().length == 0) {
                return x;
            }
        }
        System.out.println("Broken");
        System.exit(1);
        return null;
    }
    private static boolean doesRecipeMakeGoodName(recipe given, String goodName) {
        boolean toReturn = false;
        for (String x: given.getOutputName()) {
            if (x.equals(goodName)) {
                toReturn = true;
                break;
            }
        }
        return toReturn;
    }

    public static recipe randRecipe(String goodName, boolean primary) {
        boolean matches = false;
        recipe toReturn = null;
        while (!matches) {
            toReturn = recipes.get(goodName).get(economy.rand.nextInt(0, recipes.get(goodName).size()));
            String[] goods = null;
            if (primary) {
                goods = toReturn.getInputName();
            } else {
                goods = toReturn.getOutputName();
            }
            for (String x: goods) {
                if (x.equals(goodName)) {
                    matches = true;
                    break;
                }
            }
        }
        return toReturn;
    }
    public static recipe[] getRecipesWithGood(String goodName) {
        return recipes.get(goodName).toArray(new recipe[0]);
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

    @Override
    public String toString() {
        String toReturn = "";
        toReturn += name + ": " + amount;
        return toReturn;
    }
}

package core;

import core.constants.companyNames;
import core.constants.goodAcronyms;
import core.constants.premadeRecipes;

import java.util.*;
import java.util.function.Predicate;

public class good {
    private String name;
    private int amount;
    private static HashMap<String, Double> basePrices;
    private static companyNames companyNames;
    private static premadeRecipes premadeRecipes;
    public static void initialize() {
        premadeRecipes = new premadeRecipes();
        good.createGoodList();
        premadeRecipes.initialize();
        companyNames = new companyNames();
        goodAcronyms.initialize();
    }
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
            getRecipes().put(x, new ArrayList<>());
        }
    }
    private static HashMap<String, ArrayList<recipe>> getRecipes() {
        return premadeRecipes.getRecipes();
    }
    public static recipe randEndNodeRecipe() {
        return core.constants.premadeRecipes.randEndNodeGoodName();
    }
    public static String endNodeRecipeToGoodName(recipe buyer) {
        return buyer.getInputName()[0];
    }
    public static String pickRandName() {
        return companyNames.getRandName();
    }

    private static recipe[] getRecipesThatCreate(String goodName) {
        return getRecipes(goodName, recipe -> doesRecipeMakeGoodName(recipe, goodName));
    }
    private static recipe[] getRecipesThatUse(String goodName) {
        return getRecipes(goodName, recipe -> doesRecipeUseGoodName(recipe, goodName));
    }
    private static recipe[] getRecipes(String goodName, Predicate<recipe> condition) {
        recipe[] allRecipesThatHaveGoodName = getRecipesWithGood(goodName);
        ArrayList<recipe> toReturn = new ArrayList<>();
        for (recipe toCheck : allRecipesThatHaveGoodName) {
            if (condition.test(toCheck)) {
                toReturn.add(toCheck);
            }
        }
        return toReturn.toArray(new recipe[0]);
    }

    public static recipe[] primaryRecipeWithGood(String goodName) {
        ArrayList<recipe> toReturn = new ArrayList<>();
        for (recipe x: getRecipes().get(goodName)) {
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
    public static recipe getRandRecipeThatCreatesGoodName(String goodName) {
        return randRecipe(goodName, getRecipesThatCreate(goodName));
    }
    public static recipe getRandRecipeThatUsesGoodName(String goodName) {
        return randRecipe(goodName, getRecipesThatUse(goodName));
    }
    private static boolean doesRecipeMakeGoodName(recipe given, String goodName) {
        return doesRecipeHaveGoodName(given.getOutputName(), goodName);
    }
    private static boolean doesRecipeUseGoodName(recipe given, String goodName) {
        return doesRecipeHaveGoodName(given.getInputName(), goodName);
    }
    private static boolean doesRecipeHaveGoodName(String[] goodsToCheck, String goodName) {
        boolean toReturn = false;
        for (String x: goodsToCheck) {
            if (x.equals(goodName)) {
                toReturn = true;
                break;
            }
        }
        return toReturn;
    }

    private static recipe randRecipe(String goodName, recipe[] recipesThatContainGoodName) {
        int totalRecipes = recipesThatContainGoodName.length;
        int randomIndexForRecipes = totalRecipes == 1 ? 0: economy.rand.nextInt(0, totalRecipes);
        return recipesThatContainGoodName[randomIndexForRecipes];
    }

    public static recipe[] getRecipesWithGood(String goodName) {
        return getRecipes().get(goodName).toArray(new recipe[0]);
    }

    public good(String newName, int amount) {
        name = newName;
        this.amount = amount;
    }
    public String getName() {
        return name;
    }
    public static String[] getGoodArray() {
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

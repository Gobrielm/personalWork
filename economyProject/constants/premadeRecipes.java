package core.constants;

import core.economy;
import core.good;
import core.recipe;

import java.util.ArrayList;
import java.util.HashMap;

public class premadeRecipes {
    private static HashMap<String, ArrayList<recipe>> recipes;
    private static ArrayList<recipe> endNodes;
    private static ArrayList<recipe> beginNodes;
    public premadeRecipes() {
        recipes = new HashMap<>();
        endNodes = new ArrayList<>();
        beginNodes = new ArrayList<>();
    }
    public void initialize() {
        createRecipes();
    }
    public HashMap<String, ArrayList<recipe>> getRecipes() {
        return recipes;
    }
    public static recipe randEndNodeGoodName() {
        return endNodes.get(economy.rand.nextInt(0, endNodes.size()));
    }
    public static recipe randBeginNodeRecipe() {
        recipe toReturn = beginNodes.get(economy.rand.nextInt(0, beginNodes.size()));
        return toReturn;
    }
    public static void primaryRecipeMaker(String goodName, int amount, int expenses) {
        recipe newRecipe = new recipe(new good[]{}, new good[]{new good(goodName, amount)}, expenses, 0);
        recipes.get(goodName).add(newRecipe);
        beginNodes.add(newRecipe);
    }
    public static void secondaryRecipeMaker(String goodName1, String goodName2, int amount1, int amount2, String output, int amount3, int expenses) {
        recipe toAdd = new recipe(new good[]{new good(goodName1, amount1), new good(goodName2, amount2)}, new good[]{new good(output, amount3)}, expenses, 0);
        recipes.get(goodName1).add(toAdd);
        recipes.get(goodName2).add(toAdd);
        recipes.get(output).add(toAdd);
    }
    private static recipe secondaryRecipeMaker(String[] input, int[] inputAmount, String[] output, int[] outputAmount, int expenses, double income) {
        recipe toAdd = new recipe(input, inputAmount, output, outputAmount, expenses, income);
        for (String x: input) {
            recipes.get(x).add(toAdd);
        }
        for (String x: output) {
            recipes.get(x).add(toAdd);
        }
        return toAdd;
    }
    private static void secondaryRecipeMakerAndAddToEndNode(String[] input, int[] inputAmount, String[] output, int[] outputAmount, int expenses, double income) {
        double randVariation = economy.rand.nextDouble(0.95, 1.05);
        endNodes.add(secondaryRecipeMaker(input, inputAmount, output, outputAmount, expenses, income * randVariation));
    }
    private static void secondaryRecipeMaker(String goodName, int amount, String output, int amount1, int expenses) {
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
        //Add a buyer for every consumable good
        secondaryRecipeMakerAndAddToEndNode(new String[]{"Gold"}, new int[]{1}, new String[]{}, new int[]{}, 0, good.getBasePrice("Gold"));
        secondaryRecipeMakerAndAddToEndNode(new String[]{"Weapons"}, new int[]{1}, new String[]{}, new int[]{}, 0, good.getBasePrice("Weapons"));
        secondaryRecipeMakerAndAddToEndNode(new String[]{"Butane"}, new int[]{1}, new String[]{}, new int[]{}, 0, good.getBasePrice("Butane"));
        secondaryRecipeMakerAndAddToEndNode(new String[]{"IonFuel"}, new int[]{1}, new String[]{}, new int[]{}, 0, good.getBasePrice("IonFuel"));
        secondaryRecipeMakerAndAddToEndNode(new String[]{"XenonTetroxide"}, new int[]{1}, new String[]{}, new int[]{}, 0, good.getBasePrice("XenonTetroxide"));
        secondaryRecipeMakerAndAddToEndNode(new String[]{"CopperHydride"}, new int[]{1}, new String[]{}, new int[]{}, 0, good.getBasePrice("CopperHydride"));
        secondaryRecipeMakerAndAddToEndNode(new String[]{"Wires"}, new int[]{1}, new String[]{}, new int[]{}, 0, good.getBasePrice("Wires"));
        secondaryRecipeMakerAndAddToEndNode(new String[]{"Batteries"}, new int[]{1}, new String[]{}, new int[]{}, 0, good.getBasePrice("Batteries"));
        secondaryRecipeMakerAndAddToEndNode(new String[]{"Brass"}, new int[]{1}, new String[]{}, new int[]{}, 0, good.getBasePrice("Brass"));
    }
}

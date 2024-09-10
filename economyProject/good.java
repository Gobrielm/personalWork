package core;

import org.checkerframework.checker.units.qual.A;

import java.util.*;

public class good {
    private String name;
    private int amount;
    private static ArrayList<recipe> primaryRecipes = new ArrayList<>();
    private static ArrayList<recipe> secondaryRecipes = new ArrayList<>();
    private static HashMap<String, Double> basePrices;
    private static ArrayList<String> companyName1 = new ArrayList<>();
    private static ArrayList<String> companyName2 = new ArrayList<>();
    private static ArrayList<String> companyName3 = new ArrayList<>();
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
        basePrices.put("Xenon", 8.0);
        basePrices.put("Carbon", 5.0);
        basePrices.put("Water", 5.0);
        basePrices.put("Argonium", 8.0);
        basePrices.put("Wires", 15.0);
        basePrices.put("Bismanol", 15.0);
        basePrices.put("Brass", 25.0);
        basePrices.put("Batteries", 15.0);
        basePrices.put("CopperHydride", 15.0);
        basePrices.put("XenonTetroxide", 15.0);
        basePrices.put("Butane", 15.0);
        basePrices.put("IonFuel", 15.0);
        basePrices.put("Weapons", 50.0);
        basePrices.put("PerxenicAcid", 15.0);
    }
    public static void primaryRecipeMaker(String goodName, int amount, int expenses) {
        primaryRecipes.add(new recipe(new good[]{}, new good[]{new good(goodName, amount)}, expenses, 0));
    }
    public static void secondaryRecipeMaker(String goodName1, String goodName2, int amount1, int amount2, String output, int amount3, int expenses) {
        secondaryRecipes.add(new recipe(new good[]{new good(goodName1, amount1), new good(goodName2, amount2)}, new good[]{new good(output, amount3)}, expenses, 0));
    }
    public static void secondaryRecipeMaker(String goodName1, int amount1, String output, int amount3, int expenses) {
        secondaryRecipes.add(new recipe(new good[]{new good(goodName1, amount1)}, new good[]{new good(output, amount3)}, expenses, 0));
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

        secondaryRecipeMaker("Zinc", "Copper", 1, 2, "Brass", 1, 3);
        secondaryRecipeMaker("Osmium", "Argon", 1, 2, "Argonium", 1, 3);
        secondaryRecipeMaker("Zinc", "Manganese", 2, 1, "Batteries", 1, 3);
        secondaryRecipeMaker("Xenon", "Water", 3, 1, "PerxenicAcid", 1, 5);
        secondaryRecipeMaker("Carbon", "Hydrogen", 2, 5, "Butane", 3, 3);
        secondaryRecipeMaker("Xenon", "Hydrogen", 4, 1, "IonFuel", 2, 5);
        secondaryRecipeMaker("Xenon", "Oxygen", 1, 3, "XenonTetroxide", 1, 4);
        secondaryRecipeMaker("Copper", 2, "Wires", 1, 4);

        secondaryRecipes.add(new recipe(new good[]{new good("Argonium", 1), new good("PerxenicAcid", 2), new good("Bismanol", 1)}, new good[]{new good("Weapons", 1)}, 3, 0));

        for (String key: basePrices.keySet()) {
            secondaryRecipes.add(new recipe(new good[]{new good(key, 1)}, new good[]{}, 0, 10));
        }
    }
    public static void createNames() {
        companyName3.add("Starlight Innovations");
        companyName3.add("Quantum Dynamics");
        companyName3.add("Aurora Tech Solutions");
        companyName3.add("Nebula Enterprises");
        companyName3.add("Vertex Industries");
        companyName3.add("Phoenix Systems");
        companyName3.add("Solara Ventures");
        companyName3.add("Lunar Engineering");
        companyName3.add("NexGen Labs");
        companyName3.add("Zenith Global");
        companyName3.add("Orion Technologies");
        companyName3.add("AetherWorks");
        companyName3.add("CosmoCore");
        companyName3.add("Hyperion Dynamics");
        companyName3.add("Vortex Solutions");
        companyName3.add("Greensboro Inc.");
        companyName3.add("Charlesworth Group");
        companyName3.add("Hamilton & Co.");
        companyName3.add("Westbridge Holdings");
        companyName3.add("Evergreen Partners");
        companyName3.add("Harrington Enterprises");
        companyName3.add("Sterling Capital");
        companyName3.add("Watson & Moore LLC");
        companyName3.add("Brighton & Associates");
        companyName3.add("Summit Ventures");
        companyName3.add("Riverside Global");
        companyName3.add("Kingston & Sons");
        companyName3.add("Oakridge Investments");
        companyName3.add("Barrington Group");
        companyName3.add("Redwood Partners");

        companyName1.add("IronClad Mining Co.");
        companyName1.add("Titan Foundry");
        companyName1.add("Granite Ridge Industries");
        companyName1.add("Crimson Peak Mining");
        companyName1.add("CoalStone Extractors");
        companyName1.add("Amber Valley Mines");
        companyName1.add("BlueRock Industries");
        companyName1.add("Onyx Ore Corporation");
        companyName1.add("CobaltWorks");
        companyName1.add("MetalWave Foundries");
        companyName1.add("StoneMason Manufacturing");
        companyName1.add("Vulcan Mining & Metals");
        companyName1.add("IronBend Industries");

        companyName2.add("Quantum Refinery");
        companyName2.add("SteelForge Manufacturing");
        companyName2.add("IronWorks Fabrication");
        companyName2.add("HeavyMetal Industries");
        companyName2.add("ForgeLine Manufacturing");
        companyName2.add("MetalCraft Foundry");
        companyName2.add("Titanium Forge Inc.");
        companyName2.add("AlloyWorks Manufacturing");
        companyName2.add("Precision Metalworks");
        companyName2.add("SteelCore Industries");
        companyName2.add("MetalMaster Fabricators");
        companyName2.add("Industrial Forge Co.");
        companyName2.add("IronForge Industries");
        companyName2.add("PowerSteel Manufacturing");
        companyName2.add("MetalFlow Systems");
        companyName2.add("QuantumSteel Works");
        companyName2.add("ForgeTech Solutions");

    }

    //1 is Mining Stuff, 2 is Factories, 3 is general stuff
    public static String pickRandName(int category) {
        int randNum;
        String toReturn;
        if (category == 1) {
            randNum = economy.rand.nextInt(0, companyName1.size());
            toReturn = companyName1.get(randNum);
        } else if (category == 2) {
            randNum = economy.rand.nextInt(0, companyName2.size());
            toReturn = companyName2.get(randNum);
        } else {
            randNum = economy.rand.nextInt(0, companyName3.size());
            toReturn = companyName3.get(randNum);
        }
        return toReturn;
    }
    public static recipe randPrimaryRecipe() {
        return primaryRecipes.get(economy.rand.nextInt(0, primaryRecipes.size()));
    }
    public static recipe randSecondaryRecipe() {
        return secondaryRecipes.get(economy.rand.nextInt(0, secondaryRecipes.size()));
    }
    public static recipe[] getRecipesWithGood(String goodName) {
        ArrayList<recipe> toReturn = new ArrayList<>();
        for (recipe x: primaryRecipes) {
            if (doesRecipeContainGood(x, goodName)) {
                toReturn.add(x);
            }
        }
        for (recipe x: secondaryRecipes) {
            if (doesRecipeContainGood(x, goodName)) {
                toReturn.add(x);
            }
        }
        return toReturn.toArray(new recipe[0]);
    }
    private static boolean doesRecipeContainGood(recipe given, String goodName) {
        for (good x: given.getInputGoodArray()) {
            if (x.getName().equals(goodName)) {
                return true;
            }
        }
        for (good x: given.getOutputGoodArray()) {
            if (x.getName().equals(goodName)) {
                return true;
            }
        }
        return false;
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

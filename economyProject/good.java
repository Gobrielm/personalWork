package core;

import org.checkerframework.checker.units.qual.A;

import java.util.*;

public class good {
    private String name;
    private int amount;
    private static HashMap<String, ArrayList<recipe>> recipes = new HashMap<>();
    private static HashMap<String, Double> basePrices;
    private static ArrayList<String> companyName = new ArrayList<>();
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
    public static void secondaryRecipeMaker(String[] input, int[] inputAmount, String[] output, int[] outputAmount, int expenses, int income) {
        recipe toAdd = new recipe(input, inputAmount, output, outputAmount, expenses, 0);
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
        secondaryRecipeMaker("Carbon", "Hydrogen", 2, 5, "Butane", 3, 3);
        secondaryRecipeMaker("Xenon", "Hydrogen", 4, 1, "IonFuel", 2, 5);
        secondaryRecipeMaker("Xenon", "Oxygen", 1, 3, "XenonTetroxide", 1, 4);
        secondaryRecipeMaker("Copper", "Hydrogen", 2, 3, "CopperHydride", 2, 4);
        secondaryRecipeMaker("Bismuth", "Manganese", 1, 1, "Bismanol", 1, 5);
        secondaryRecipeMaker("Copper", 2, "Wires", 1, 4);
        secondaryRecipeMaker(new String[]{"Argonium", "PerxenicAcid", "Bismanol"}, new int[]{1, 2, 1}, new String[]{"Weapons"}, new int[]{1}, 3, 0);
        //Add a buyer for every type of good
        for (String key: basePrices.keySet()) {
            secondaryRecipeMaker(new String[]{key}, new int[]{1}, new String[]{}, new int[]{}, 0, 10);
        }
    }
    public static void createNames() {
        companyName.add("Starlight Innovations");
        companyName.add("Quantum Dynamics");
        companyName.add("Aurora Tech Solutions");
        companyName.add("Nebula Enterprises");
        companyName.add("Vertex Industries");
        companyName.add("Phoenix Systems");
        companyName.add("Solara Ventures");
        companyName.add("Lunar Engineering");
        companyName.add("NexGen Labs");
        companyName.add("Zenith Global");
        companyName.add("Orion Technologies");
        companyName.add("AetherWorks");
        companyName.add("CosmoCore");
        companyName.add("Hyperion Dynamics");
        companyName.add("Vortex Solutions");
        companyName.add("Greensboro Inc.");
        companyName.add("Charlesworth Group");
        companyName.add("Hamilton & Co.");
        companyName.add("Westbridge Holdings");
        companyName.add("Evergreen Partners");
        companyName.add("Harrington Enterprises");
        companyName.add("Sterling Capital");
        companyName.add("Watson & Moore LLC");
        companyName.add("Brighton & Associates");
        companyName.add("Summit Ventures");
        companyName.add("Riverside Global");
        companyName.add("Kingston & Sons");
        companyName.add("Oakridge Investments");
        companyName.add("Barrington Group");
        companyName.add("Redwood Partners");
        companyName.add("IronClad Mining Co.");
        companyName.add("Titan Foundry");
        companyName.add("Granite Ridge Industries");
        companyName.add("Crimson Peak Mining");
        companyName.add("CoalStone Extractors");
        companyName.add("Amber Valley Mines");
        companyName.add("BlueRock Industries");
        companyName.add("Onyx Ore Corporation");
        companyName.add("CobaltWorks");
        companyName.add("MetalWave Foundries");
        companyName.add("StoneMason Manufacturing");
        companyName.add("Vulcan Mining & Metals");
        companyName.add("IronBend Industries");
        companyName.add("Quantum Refinery");
        companyName.add("SteelForge Manufacturing");
        companyName.add("IronWorks Fabrication");
        companyName.add("HeavyMetal Industries");
        companyName.add("ForgeLine Manufacturing");
        companyName.add("MetalCraft Foundry");
        companyName.add("Titanium Forge Inc.");
        companyName.add("AlloyWorks Manufacturing");
        companyName.add("Precision Metalworks");
        companyName.add("SteelCore Industries");
        companyName.add("MetalMaster Fabricators");
        companyName.add("Industrial Forge Co.");
        companyName.add("IronForge Industries");
        companyName.add("PowerSteel Manufacturing");
        companyName.add("MetalFlow Systems");
        companyName.add("QuantumSteel Works");
        companyName.add("ForgeTech Solutions");
    }

    public static String randGoodName() {
        int randNum = economy.rand.nextInt(0, basePrices.size());
        return basePrices.keySet().toArray(new String[0])[randNum];
    }

    public static String pickRandName() {
        int randNum = economy.rand.nextInt(0, companyName.size());
        return companyName.get(randNum);
    }
    public static recipe[] primaryRecipeWithGood(String goodName) {
        ArrayList<recipe> toReturn = new ArrayList<>(List.of(recipes.get(goodName).toArray(new recipe[0])));
        int size = toReturn.size();
        for (int i = 0; i < size; i++) {
            if (!doesRecipeMakeGoodName(toReturn.get(i), goodName)) {
                toReturn.remove(i);
                i--;
                size--;
            }
        }
        return toReturn.toArray(new recipe[0]);
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

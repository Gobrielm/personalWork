package core;

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
        basePrices.put("Xenon", 5.0);
        basePrices.put("Carbon", 5.0);
        basePrices.put("Water", 5.0);
        basePrices.put("Argonium", 5.0);
        basePrices.put("Wires", 5.0);
        basePrices.put("Bismanol", 5.0);
        basePrices.put("Brass", 25.0);
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

    @Override
    public String toString() {
        String toReturn = "";
        toReturn += name + ": " + amount;
        return toReturn;
    }
}

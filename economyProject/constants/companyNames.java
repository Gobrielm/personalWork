package core.constants;

import core.economy;

import java.util.ArrayList;

public class companyNames {
    private static ArrayList<String> companyName;
    public companyNames() {
        companyName = new ArrayList<>();
        createNames();
    }
    private static void createNames() {
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

    public int getSize() {
        return companyName.size();
    }

    public String getRandName() {
        int randNum = economy.rand.nextInt(0, getSize());
        String toReturn = companyName.get(randNum);
        companyName.remove(toReturn);
        return toReturn;
    }
}

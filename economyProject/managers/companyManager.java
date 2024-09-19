package core.managers;

import core.constants.goodAcronyms;
import core.economy;
import core.planet;
import core.recipe;

import java.util.Arrays;

public class companyManager {
    private String name;
    private double cash;
    private core.recipe recipe;
    private int order;
    private core.planet planet;
    private int maxAge;
    private int personality;
    public companyManager(String name, recipe recipe, planet planet) {
        this.name = name;
        this.recipe = new recipe(recipe.getInputName(), recipe.getInputAmount(), recipe.getOutputName(), recipe.getOutputAmount(), recipe.getExpenses(), recipe.getIncome());
        cash = 1000;
        maxAge = 3;
        if (recipe.getInputName() == null || Arrays.equals(recipe.getInputName(), new String[]{})) {
            this.order = 1;
        } else if (recipe.getOutputName() == null || Arrays.equals(recipe.getOutputName(), new String[]{})) {
            this.order = 3;
        } else {
            this.order = 2;
        }
        this.personality = economy.rand.nextInt(1, 4);
        this.planet = planet;
    }

    public int getMaxAge() {
        return maxAge;
    }
    public void changeCash(double amount) {
        cash += amount;
    }
    public String getName() {
        return name;
    }
    public recipe getRecipe() {
        return recipe;
    }
    public double getCash() {
        return cash;
    }
    public planet getPlanet() {
        return planet;
    }
    public boolean getBankrupt() {
        return cash < -50;
    }
    public int getPersonality() {
        return personality;
    }
    public int getOrder() {
        return order;
    }

}

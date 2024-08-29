package core;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class company {
    private String name;
    private double cash;
    private recipe recipe;
    private int order;
    private static long seed;
    private planet planet;
    //Higher number is more aggressive
    private int personality;
    public company(String name, recipe recipe, planet planet) {
        this.name = name;
        this.recipe = new recipe(recipe.getInput(), recipe.getOutput(), recipe.getInputAmount(), recipe.getOutputAmount(), recipe.getExpenses(), recipe.getIncome());
        cash = 100;
        if (recipe.getInput() == null || Arrays.equals(recipe.getInput(), new String[]{})) {
            this.order = 1;
        } else if (recipe.getOutput() == null || Arrays.equals(recipe.getOutput(), new String[]{})) {
            this.order = 3;
        } else {
            this.order = 2;
        }
        Random rand = new Random(seed);
        this.personality = rand.nextInt(1, 4);
        this.planet = planet;
    }
    public static void setSeed(long newSeed) {
        seed = newSeed;
    }

    //order1 is the same as the company
    public boolean checkDeal(order order1, order order2) {
        double priceDiff = order1.getPrice() - order2.getPrice();
        double price = order1.getPrice();
        double change = Math.abs(priceDiff / price);
        return change > 0.03;
    }

    public void buyGood(int amount, String good) {
        recipe.changeInput(good, amount);
    }

    public void sellGood(int amount, double price) {
        cash += amount * price;
    }

    private void payExpenses() {
        cash -= recipe.getExpenses();
    }

    private void createBuyOrders() {
        good[] input = recipe.getInputGood();
        for (int i = 0; i < input.length; i++) {
            good temp = input[i];
            planet.addBuyOrder(new order(this, temp, 10, 10));
        }
    }

    public void tick() {
        payExpenses();
        if (order == 1) {

        } else if (order == 2) {

        } else {

        }
    }
}

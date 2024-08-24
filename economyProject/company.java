package core;

import java.util.Random;

public class company {
    private String name;
    private double cash;
    private recipe recipe;
    private int order;
    private static long seed;
    //Higher number is more aggressive
    private int personality;
    public company(String name, recipe recipe) {
        this.name = name;
        this.recipe = new recipe(recipe.getInput(), recipe.getOutput(), recipe.getExpenses(), recipe.getIncome());
        cash = 100;
        if (recipe.getInput() == null) {
            this.order = 1;
        } else if (recipe.getOutput() == null) {
            this.order = 3;
        } else {
            this.order = 2;
        }
        Random rand = new Random(seed);
        this.personality = rand.nextInt(1, 4);
    }
    public void setSeed(long newSeed) {
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
}

package core;

import java.util.Random;

public class company {
    private String name;
    private double cash;
    private good primary;
    private good secondary;
    private int order;
    //Higher number is more aggressive
    private int personality;
    public company(String name, good primary, good secondary) {
        this.name = name;
        this.primary = primary;
        this.secondary = secondary;
        cash = 100;
        if (primary == null) {
            this.order = 1;
        } else if (secondary == null) {
            this.order = 3;
        } else {
            this.order = 2;
        }
        Random rand = new Random();
        this.personality = rand.nextInt(0, 3);
    }
    //order1 is the same as the company
    public boolean checkDeal(order order1, order order2) {
        double priceDiff = order1.getPrice() - order2.getPrice();
        double price = order1.getPrice();
        double change = Math.abs(priceDiff / price);
        return change > 0.03;
    }

    public void buyGood(int amount, double price) {
        primary.changeAmount(amount);
    }

    public void sellGood(int amount, double price) {
        cash += amount * price;
    }
}

package core;

public class order {
    private good good;
    private double price;
    private company owner;
    public order(company owner, good good, int amount, double price) {
        this.good = good;
        good.changeAmount(amount);
        this.price = price;
        this.owner = owner;
    }
    public boolean checkGoods(order other) {
        return this.good == other.good;
    }
    public int getAmount() {
        return good.getAmount();
    }
    //order1 is the buyOrder, order2 is the seller
    public static void makeDeal(order order1, order order2) {
        company company1 = order1.owner;
        company company2 = order2.owner;
        if (company2.checkDeal(order2, order1) && company1.checkDeal(order1, order2)) {
            double price1 = order1.price;
            double price2 = order2.price;
            double price = (price1 + price2) / 2;
            int amount = Math.min(order1.getAmount(), order2.getAmount());
            company1.buyGood(amount, price);
            company2.sellGood(amount, price);
            order1.good.changeAmount(-amount);
            order2.good.changeAmount(-amount);
        }
        //No deal
    }

    public double getPrice() {
        return this.price;
    }
}

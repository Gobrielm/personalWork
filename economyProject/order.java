package core;

public class order {
    private good good;
    private double expectedPrice;
    private double limitPrice;
    private company owner;
    public order(company owner, good good, double expectedPrice, double limitPrice) {
        
        this.good = new good(good.getName(), good.getAmount());
        this.expectedPrice = expectedPrice;
        this.limitPrice = limitPrice;
        this.owner = owner;
    }
    public boolean checkGoods(order other) {
        return this.good.equals(other.good);
    }
    public int getAmount() {
        return good.getAmount();
    }
    //order1 is the buyOrder, order2 is the seller
    public static coords makeDeal(order order1, order order2) {
        company company1 = order1.owner;
        company company2 = order2.owner;
        if (order1.getAmount() == 0 || order2.getAmount() == 0) {
            return null;
        }
        coords toReturn = null;
        if (order1.limitPrice >= order2.limitPrice) {
            if (company2.checkDeal(order2, order1) && company1.checkDeal(order1, order2)) {
                double price1 = order1.expectedPrice;
                double price2 = order2.expectedPrice;
                double price = (price1 + price2) / 2;
                int amount = Math.min(order1.getAmount(), order2.getAmount());
                toReturn = new coords(amount, price);
                company1.buyGood(amount, order1.good.getName());
                company2.sellGood(amount, price);
                order1.good.changeAmount(-amount);
                order2.good.changeAmount(-amount);
            }
        }
        return toReturn;
    }

    @Override
    public String toString() {
        return owner.getName() + ": " + good.getAmount() + "----" + Math.round(expectedPrice);
    }

    public double getPrice() {
        return expectedPrice;
    }
    public double getLimitPrice() {
        return limitPrice;
    }
    public String getGood() {
        return good.getName();
    }
}

package core;

public class order {
    private good good;
    private double expectedPrice;
    private double limitPrice;
    private business owner;
    private boolean out;
    private boolean isBuyOrder;
    private int age;
    //Ai's orders
    public order(business owner, good good, double expectedPrice, double limitPrice, boolean isBuyOrder) {
        this.good = new good(good.getName(), good.getAmount());
        this.expectedPrice = expectedPrice;
        this.limitPrice = limitPrice;
        this.owner = owner;
        out = false;
        this.isBuyOrder = isBuyOrder;
        this.age = 0;
    }
    public boolean checkGoods(order other) {
        return this.good.equals(other.good);
    }
    public int getAmount() {
        return good.getAmount();
    }


    public void changeOut() {
        if (good.getAmount() == 0) {
            out = true;
        }
    }
    public boolean incrementAge() {
        this.age ++;
        int maxAge = this.owner.getMaxAge();
        return age > maxAge;
    }
    public boolean isBuyOrder() {
        return isBuyOrder;
    }
    public boolean checkOut() {
        return out;
    }
    public void checkOutPriced() {
        if (isBuyOrder) {
            if (expectedPrice > limitPrice) {
                out = true;
            }
        } else {
            if (expectedPrice < limitPrice) {
                out = true;
            }
        }
    }

    public boolean checkValid() {
        return !out;
    }

    //order1 is the buyOrder, order2 is the seller
    public static void makeDeal(order order1, order order2) {
        business company1 = order1.owner;
        business company2 = order2.owner;
        if (company2.checkDeal(order2, order1) && company1.checkDeal(order1, order2)) {
            double price1 = order1.expectedPrice;
            double price2 = order2.expectedPrice;
            double price = (price1 + price2) / 2;
            int amount = Math.min(order1.getAmount(), order2.getAmount());
            company1.buyGood(amount, order1.good.getName(), price);
            company2.sellGood(amount, order2.good.getName(), price);
            order1.good.changeAmount(-amount);
            order2.good.changeAmount(-amount);
            order1.changeOut();
            order2.changeOut();
            order1.getOwner().getPlanet().changeBasePrice(order1.good.getName(), price, amount);
        } else {
            company1.adjustDeal(order1);
            company2.adjustDeal(order2);
            order1.checkOutPriced();
            order2.checkOutPriced();
        }
    }

    @Override
    public String toString() {
        return owner.getName() + ": " + good.getAmount() + "--" + Utils.round(expectedPrice, 2) + "--" + Utils.round(limitPrice, 2);
    }
    public double getPrice() {
        return expectedPrice;
    }
    public void setPrice(double price) {
         expectedPrice = price;
    }
    public double getLimitPrice() {
        return limitPrice;
    }
    public String getGood() {
        return good.getName();
    }
    public business getOwner() {
        return owner;
    }
}

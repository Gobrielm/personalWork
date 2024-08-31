package core;

public class order {
    private good good;
    private double expectedPrice;
    private double limitPrice;
    private company owner;
    private boolean out;
    private boolean outPriced;
    private boolean isBuyOrder;
    public order(company owner, good good, double expectedPrice, double limitPrice, boolean isBuyOrder) {
        this.good = new good(good.getName(), good.getAmount());
        this.expectedPrice = expectedPrice;
        this.limitPrice = limitPrice;
        this.owner = owner;
        out = false;
        outPriced = false;
        this.isBuyOrder = isBuyOrder;
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
    public boolean checkOut() {
        return out;
    }
    public void changeOutPriced() {
        if (isBuyOrder) {
            if (expectedPrice > limitPrice) {
                outPriced = true;
            }
        } else {
            if (expectedPrice < limitPrice) {
                outPriced = true;
            }
        }
    }
    public boolean checkOutPriced() {
        return outPriced;
    }
    public boolean checkValid() {
        return !outPriced || !out;
    }

    //order1 is the buyOrder, order2 is the seller
    public static double makeDeal(order order1, order order2) {
        company company1 = order1.owner;
        company company2 = order2.owner;
        if (order1.checkValid() && order2.checkValid()) {
            if (order1.limitPrice >= order2.limitPrice) {
                if (company2.checkDeal(order2, order1) && company1.checkDeal(order1, order2)) {
                    double price1 = order1.expectedPrice;
                    double price2 = order2.expectedPrice;
                    double price = (price1 + price2) / 2;
                    int amount = Math.min(order1.getAmount(), order2.getAmount());
                    company1.buyGood(amount, order1.good.getName(), price);
                    company2.sellGood(amount, order2.good.getName(), price);
                    order1.good.changeAmount(-amount);
                    order2.good.changeAmount(-amount);
                    return  price;
                } else if (company2.checkDeal(order2, order1)) {
                    order1.expectedPrice += order1.expectedPrice * 0.01;
                } else if (company1.checkDeal(order1, order2)) {
                    order2.expectedPrice -= order2.expectedPrice * 0.01;
                } else {
                    order1.expectedPrice += order1.expectedPrice * 0.01;
                    order2.expectedPrice -= order2.expectedPrice * 0.01;
                }
            }
        }

        return 0;
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

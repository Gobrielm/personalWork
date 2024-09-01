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
    public boolean isBuyOrder() {
        return isBuyOrder;
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
        if (out) {
            return false;
        } else {
            return !outPriced;
        }
    }

    //order1 is the buyOrder, order2 is the seller
    public static double makeDeal(order order1, order order2) {
        company company1 = order1.owner;
        company company2 = order2.owner;
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
            return  price;
//        } else if (company2.checkDeal(order2, order1)) {
//            order1.expectedPrice += order1.expectedPrice * 0.01;
//            order1.changeOutPriced();
//        } else if (company1.checkDeal(order1, order2)) {
//            order2.expectedPrice -= order2.expectedPrice * 0.01;
//            order2.changeOutPriced();
        } else {
            order1.expectedPrice += order1.expectedPrice * 0.01;
            order2.expectedPrice -= order2.expectedPrice * 0.01;
            order1.changeOutPriced();
            order2.changeOutPriced();
        }
        return 0;
    }

    @Override
    public String toString() {
        return owner.getName() + ": " + good.getAmount() + "----" + ((double) Math.round(expectedPrice * 100) / 100);
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
    public company getOwner() {
        return owner;
    }
}

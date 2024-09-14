package core;

public class share {
    private business owner;
    private company pieceOf;
    private double price;
    private double amount;
    public share(company theCompany) {
        owner = theCompany;
        pieceOf = theCompany;
        price = 0.0;
        amount = 100.0;
    }
    public share(share toSplitOff, double amount) {
        amount = Math.min(toSplitOff.amount, amount);
        if (amount == 0) {
            System.out.println("Cannot sell 0 shares");
            System.exit(0);
        }
        owner = toSplitOff.owner;
        pieceOf = toSplitOff.pieceOf;
        price = 0.0;
        this.amount = amount;
        toSplitOff.amount -= amount;
    }

    public business getOwner() {
        return owner;
    }

    public void changePrice(double price) {
        this.amount = price;
    }
}

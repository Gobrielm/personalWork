package core;

import java.util.Objects;

public class good {
    private String name;
    private int basePrice;
    private int amount;
    public good(String newName, int newPrice, int amount) {
        name = newName;
        basePrice = newPrice;
        this.amount = amount;
    }
    public good() {
        name = null;
        basePrice = 0;
        amount = 0;
    }
    public int getAmount() {
        return this.amount;
    }
    public void changeAmount(int amount) {
        this.amount += amount;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof good) {
            return Objects.equals(name, ((good) obj).name);
        }
        return false;
    }
}

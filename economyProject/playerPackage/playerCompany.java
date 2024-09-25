package core.playerPackage;

import core.player;
import core.recipe;

public class playerCompany {
    private recipe recipe;
    private player owner;
    public playerCompany(recipe recipe, player owner) {
        this.recipe = recipe;
        this.owner = owner;
    }

    private void payExpenses() {
        owner.changeCash(-recipe.getExpenses());
    }

    public void tick() {
        payExpenses();
    }

    @Override
    public String toString() {
        return owner.getName() + ": " + recipe.toString();
    }
}

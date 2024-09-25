package core.playerPackage;

import core.good;
import core.player;
import core.recipe;

public class playerCompany {
    private recipe recipe;
    private player owner;
    public playerCompany(recipe recipe, player owner) {
        this.recipe = new recipe(recipe.getInputName(), recipe.getInputAmount(), recipe.getOutputName(), recipe.getOutputAmount(), recipe.getExpenses(), recipe.getIncome());
        this.owner = owner;
    }

    private void payExpenses() {
        owner.changeCash(-recipe.getExpenses());
    }

    public void tick() {
        payExpenses();
        createGoods();
    }
    private void createGoods() {
        for (good ToCreate: recipe.getOutputGoodArray()) {
            owner.changeStorage(ToCreate.getName(), ToCreate.getAmount());
        }
    }

    @Override
    public String toString() {
        return owner.getName() + ": " + recipe.toString();
    }
}

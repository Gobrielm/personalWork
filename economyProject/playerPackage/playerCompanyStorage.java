package core.playerPackage;

import core.good;
import core.player;
import core.recipe;

import java.util.ArrayList;
import java.util.HashMap;

public class playerCompanyStorage {
    private HashMap<String, ArrayList<playerCompany>> ownedCompanies;
    private player owner;
    public playerCompanyStorage(player owner) {
        this.owner = owner;
        ownedCompanies = new HashMap<>();
        for (String goodName: good.getGoodArray()) {
            ownedCompanies.put(goodName, new ArrayList<>());
        }
    }

    public void addNewCompany(recipe recipe) {
        playerCompany newCompany = new playerCompany(recipe, owner);
        for (String goodName: recipe.getBothName()) {
            ownedCompanies.get(goodName).add(newCompany);
        }
    }
    public playerCompany[] getCompaniesByGoodName(String goodName) {
        if (ownedCompanies.containsKey(goodName)) {
            return ownedCompanies.get(goodName).toArray(new playerCompany[0]);
        }
        return new playerCompany[0];
    }
    public void tick() {
        for (String goodName: good.getGoodArray()) {
            ArrayList<playerCompany> companiesOfGoodName = ownedCompanies.get(goodName);
            for (playerCompany companyToTick: companiesOfGoodName) {
                companyToTick.tick();
            }
        }
    }
}

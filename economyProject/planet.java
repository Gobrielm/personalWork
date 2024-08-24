package core;

import java.util.ArrayList;

public class planet {
    int id;
    ArrayList<company> companies;
    public planet(int id) {
        this.id = id;
        companies = new ArrayList<>();
    }

    public void addCompany(company toAdd) {
        companies.add(toAdd);
    }
}

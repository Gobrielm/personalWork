package core;

import java.util.ArrayList;
import java.util.Random;

public class planet {
    private int id;
    private int size;
    private static long seed;
    ArrayList<company> companies;
    public planet(int id) {
        this.id = id;
        companies = new ArrayList<>();
        Random rand = new Random(seed);
        size = rand.nextInt(30, 60);
        for (int i = 0; i < size * 2 / 3; i++) {
            recipe temp = good.randPrimaryRecipe();
            company newCompany = new company("Filler" , temp);
            companies.add(newCompany);
        }
        for (int i = 0; i < size / 3; i++) {
            recipe temp = good.randSecondaryRecipe();
            company newCompany = new company("Filler", temp);
            companies.add(newCompany);
        }
    }
    public static void setSeed(long newSeed) {
        seed = newSeed;
    }

    public void addCompany(company toAdd) {
        companies.add(toAdd);
    }
}

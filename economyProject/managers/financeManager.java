package core.managers;

import core.Utils;
import core.company;

import java.util.HashMap;
import java.util.LinkedList;

public class financeManager {
    private LinkedList<Double> incomeList;
    private static final int INCOMELISTSIZE = 10;
    private HashMap<String, Double> stock;
    private company parent;
    public financeManager(company parent) {
        incomeList = new LinkedList<>();
        stock = new HashMap<>();
        this.parent = parent;
        stock.put(parent.getName(), 100.0);
        for (int i = 0; i < INCOMELISTSIZE; i++) {
            incomeList.add(0.0);
        }
    }

    public void profitEditLast(double num) {
        double last = incomeList.removeLast() + num;
        incomeList.add(last);
    }
    public void profitRemoveFirst() {
        incomeList.removeFirst();
        incomeList.addLast(0.0);
    }
    public double getProfit() {
        double total = 0.0;
        for (double x: incomeList) {
            total += x;
        }
        return Utils.round(total / incomeList.size(), 1);
    }
}

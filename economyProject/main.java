package core;

import edu.princeton.cs.algs4.StdDraw;

public class main {
    public static void main(String[] args) {
        int playerCount = 4;
        economy galaxy = new economy(1000, 4);
        graphicalInterface.drawMenu(galaxy);
    }
}
package core;

import edu.princeton.cs.algs4.StdDraw;

public class main {
    public void main(String[] args) throws InterruptedException {
        int playerCount = 4;
        economy galaxy = new economy(1000, 4);
        while (true) {
            galaxy.tick();
            wait(1000);
        }
    }
}
package Simulation;

import Cell.Cell;
import Cell.GameOfLifeCell;

import java.util.Collection;
import java.util.Random;

/**
 * Created by rhondusmithwick on 1/31/16.
 *
 * @author Rhondu Smithwick
 */
public class GameOfLifeSimulation extends Simulation {

    private final double probStartDead = .3;

    public GameOfLifeSimulation(Collection<Cell> theCells) {
        super(theCells);
    }


    public void init() {
        Random rn = new Random();
        int minimum = 1;
        int maximum = 100;
        int range = maximum - minimum + 1;
        for (Cell c : theCells) {
            int randomNum = rn.nextInt(range) + minimum;
            if (randomNum <= probStartDead * 100) {
                ((GameOfLifeCell) c).destroy();
            }
        }
    }

}

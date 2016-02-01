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

    /**
     * NEEDED FROM XML!
     */
    private final double probStartDead = 30;

    public GameOfLifeSimulation(Collection<Cell> theCells) {
        super(theCells);
    }



    public void assignInitialState(int randomNum, Cell c) {
        GameOfLifeCell gc = (GameOfLifeCell) c;
        if (randomNum <= probStartDead) {
            gc.destroy();
        } else {
            gc.restore();
        }
    }

    @Override
    protected void step() {
        super.step();
        for (Cell c: theCells) {
            GameOfLifeCell gc = (GameOfLifeCell) c;
            gc.transform();
        }
    }
}

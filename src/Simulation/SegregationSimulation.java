package Simulation;

import Cell.Cell;
import Cell.SegregationCell;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Created by rhondusmithwick on 2/1/16.
 *
 * @author Rhondu Smithwick
 */
public class SegregationSimulation extends Simulation {
    private final List<Cell> emptyCells = new ArrayList<>();
    /**
     * NEEDED FROM XML!
     */
    private final double threshold = 30;
    private final Color empty = Color.WHITE;
    private final Color group1 = Color.RED;
    private final Color group2 = Color.BLUE;
    private final double emptyPercent = 10;
    private final double group1Percent = 40;
//    double group2Percent = 50;

    public SegregationSimulation(Collection<Cell> theCells) {
        super(theCells);
    }



    @Override
    protected void assignInitialState(int randomNum, Cell c) {
        SegregationCell sc = (SegregationCell) c;
        sc.setThreshold(threshold);
        if (randomNum <= emptyPercent) {
            sc.setFill(empty);
            emptyCells.add(sc);
        } else if (randomNum > emptyPercent && randomNum <= emptyPercent + group1Percent) {
            sc.setFill(group1);
        } else {
            sc.setFill(group2);
        }
    }

    @Override
    protected void step() {
        super.step();
        for (Cell c : theCells) {
            SegregationCell sc = (SegregationCell) c;
            if (!sc.getSatisfied()) {
                move(sc);
            }
        }
    }

    private void move(SegregationCell sc) {
        Random rn = new Random();
        int minimum = 0;
        int maximum = emptyCells.size() - 1;
        int range = maximum - minimum + 1;
        int randomNum = rn.nextInt(range) + minimum;
        Cell c = emptyCells.get(randomNum);
        c.setFill(sc.getFill());
        sc.setFill(empty);
        sc.setSatisfied(true);
        emptyCells.set(randomNum, sc);
    }
}

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
    List<Cell> emptyCells = new ArrayList<>();

    public SegregationSimulation(Collection<Cell> theCells) {
        super(theCells);
    }

    /**
     * NEEDED FROM XML!
     */
    double threshold = .3;
    Color empty = Color.WHITE;
    Color group1 = Color.RED;
    Color group2 = Color.BLUE;
    double emptyPercent = 20;
    double group1Percent = 30;
    double group2Percent = 50;

    public void init() {
        Random rn = new Random();
        int minimum = 1;
        int maximum = 100;
        int range = maximum - minimum + 1;
        for (Cell c : theCells) {
            ((SegregationCell) c).setThreshold(threshold);
            int randomNum = rn.nextInt(range) + minimum;
            assignGroup(c, randomNum);
        }
    }

    public void assignGroup(Cell c, int randomNum) {
        if (randomNum <= emptyPercent) {
            c.setFill(empty);
            emptyCells.add(c);
        } else if (randomNum > emptyPercent && randomNum <= emptyPercent + group1Percent) {
            c.setFill(group1);
        } else {
            c.setFill(group2);
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

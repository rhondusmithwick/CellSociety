package Simulation;

import Cell.Cell;
import Cell.SegregationCell;
import javafx.scene.paint.Color;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rhondusmithwick on 2/1/16.
 *
 * @author Rhondu Smithwick
 */
public class SegregationSimulation extends Simulation {
    private static final double DEFAULT_THRESHOLD = 30;
    private static final Color DEFAULT_EMPTY = Color.WHITE;
    private static final Color DEFAULT_GROUP1 = Color.RED;
    private static final Color DEFAULT_GROUP2 = Color.BLUE;
    private static final double DEFAULT_EMPTYPERCENT = 10;
    private static final double DEFAULT_GROUP1PERCENT = 40;
    private final List<Cell> emptyCells = new ArrayList<>();
    private double threshold = DEFAULT_THRESHOLD;
    private Color empty = DEFAULT_EMPTY;
    private Color group1 = DEFAULT_GROUP1;
    private Color group2 = DEFAULT_GROUP2;
    private double emptyPercent = DEFAULT_EMPTYPERCENT;
    private double group1Percent = DEFAULT_GROUP1PERCENT;

    public SegregationSimulation() {
        super();
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
        for (Cell c : getTheCells()) {
            SegregationCell sc = (SegregationCell) c;
            if (!sc.getSatisfied()) {
                move(sc);
            }
        }
    }

    private void move(SegregationCell sc) {
        int randomIndex = getRandomNum(0, emptyCells.size() - 1);
        Cell c = emptyCells.get(randomIndex);
        c.setFill(sc.getFill());
        sc.setFill(empty);
        sc.setSatisfied(true);
        emptyCells.set(randomIndex, sc);
    }


    @Override
    protected void getTypeProperties(Element simElem) {
        // TODO Auto-generated method stub

    }


}

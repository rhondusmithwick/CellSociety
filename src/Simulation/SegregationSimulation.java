package Simulation;

import Cell.Cell;
import Cell.SegregationCell;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rhondusmithwick on 2/1/16.
 *
 * @author Rhondu Smithwick
 */
public class SegregationSimulation extends Simulation {
    private static final int DEFAULT_THRESHOLD = 30;
    private static final int DEFAULT_EMPTY_PERCENT = 10;
    private static final int DEFAULT_GROUP1_PERCENT = 40;

    private static final Paint DEFAULT_EMPTY = Color.WHITE;
    private static final Paint DEFAULT_GROUP1 = Color.RED;
    private static final Paint DEFAULT_GROUP2 = Color.BLUE;

    private final List<SegregationCell> emptyCells = new ArrayList<>();
    private int threshold = DEFAULT_THRESHOLD;
    private int emptyPercent = DEFAULT_EMPTY_PERCENT;
    private int group1Percent = DEFAULT_GROUP1_PERCENT;

    private Paint empty = DEFAULT_EMPTY;
    private Paint group1 = DEFAULT_GROUP1;
    private Paint group2 = DEFAULT_GROUP2;


    public SegregationSimulation() {
        super();
        parseXmlFile("resources/" + "GameOfLife.xml");
    }


    @Override
    void assignInitialState(int randomNum, Cell c) {
        final SegregationCell sc = (SegregationCell) c;
        sc.setThreshold(threshold);
        if (randomNum <= emptyPercent) {
            sc.setFill(empty);
            emptyCells.add(sc);
        } else if (randomNum > emptyPercent
                && randomNum <= emptyPercent + group1Percent) {
            sc.setFill(group1);
        } else {
            sc.setFill(group2);
        }
    }

    @Override
    protected void step() {
        super.step();
        SegregationCell sc;
        for (Cell c : getTheCells()) {
            sc = (SegregationCell) c;
            if (!sc.getIsEmpty() && !sc.getSatisfied()) {
                move(sc);
            }
        }
    }

    private void move(SegregationCell cellToMove) {
        final int randomIndex = getRandomNum(0, emptyCells.size() - 1);
        final SegregationCell emptyCell = emptyCells.get(randomIndex);
        emptyCell.setFill(cellToMove.getFill());
        emptyCell.setIsEmpty(false);

        cellToMove.setFill(empty);
        cellToMove.makeSatisfied();
        cellToMove.setIsEmpty(true);
        emptyCells.set(randomIndex, cellToMove);
    }


    @Override
    void setSpecificProperties(Element simElem) {
//        if (getType() == null || !getType().equals("Segregation")) {
//            threshold = DEFAULT_THRESHOLD;
//            emptyPercent = DEFAULT_EMPTY_PERCENT;
//            group1Percent = DEFAULT_GROUP1_PERCENT;
//        } else {
//            threshold = getIntValue(simElem, "threshold");
//            emptyPercent = getIntValue(simElem, "emptyPercent");
//            group1Percent = getIntValue(simElem, "group1Percent");
//        }
    }


}

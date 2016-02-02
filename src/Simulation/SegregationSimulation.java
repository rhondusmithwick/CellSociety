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
    private static final double DEFAULT_EMPTY_PERCENT = 10;
    private static final double DEFAULT_GROUP1_PERCENT = 40;


    private final List<Cell> emptyCells = new ArrayList<>();
    private double threshold = DEFAULT_THRESHOLD;
    private Color empty = DEFAULT_EMPTY;
    private Color group1 = DEFAULT_GROUP1;
    private Color group2 = DEFAULT_GROUP2;
    private double emptyPercent = DEFAULT_EMPTY_PERCENT;
    private double group1Percent = DEFAULT_GROUP1_PERCENT;

    public SegregationSimulation() {
        super();
        parseXmlFile("resources/" + "Segregation.xml");
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
            if (!sc.getSatisfied()) {
                move(sc);
            }
        }
    }

    private void move(SegregationCell sc) {
        final int randomIndex = getRandomNum(0, emptyCells.size() - 1);
        Cell c = emptyCells.get(randomIndex);
        c.setFill(sc.getFill());
        sc.setFill(empty);
        sc.makeSatisfied();
        emptyCells.set(randomIndex, sc);
    }


    @Override
    void setTypeProperties(Element simElem) {
        // TODO Auto-generated method stub

    }


}

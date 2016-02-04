package Simulation;

import Cell.Cell;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.w3c.dom.Element;
import Cell.FireCell;
import Cell.FireCell.State;


/**
 * Created by rhondusmithwick on 2/4/16.
 *
 * @author Rhondu Smithwick
 */
// TO FINISH
public class FireSimulation extends Simulation {
    private static final int DEFAULT_BURN_TIME = 30;
    private static final Paint DEFAULT_EMPTY_VISUAL = Color.YELLOW;
    private static final Paint DEFAULT_BURNING_VISUAL = Color.RED;
    private static final Paint DEFAULT_TREE_VISUAL = Color.GREEN;

    private int burnTime = DEFAULT_BURN_TIME;
    private Paint emptyVisual = DEFAULT_EMPTY_VISUAL;
    private Paint burningVisual = DEFAULT_BURNING_VISUAL;
    private Paint treeVisual = DEFAULT_TREE_VISUAL;

    public FireSimulation() {
        super();
        parseXmlFile("resources/" + "GameOfLife.xml");
    }

    void step() {
        super.step();
        for (Cell c: getTheCells()) {
            FireCell fc = (FireCell) c;
            if (fc.getState() == State.TREE) {

            }
        }
    }
    void assignInitialState(int randomNum, Cell c) {
        FireCell fc = (FireCell) c;
        fc.setBurnTime(burnTime);
        if (fc.getRow() == getCellsPerRow()
                || fc.getColumn() == getCellsPerColumn()) {
            fc.setFill(emptyVisual);
        } else if (fc.getRow() == getCellsPerRow() / 2
                && fc.getColumn() == getCellsPerColumn() / 2) {
            fc.setFill(burningVisual);
        } else {
            fc.setFill(treeVisual);
        }

    }

    @Override
    void setSpecificProperties(Element simElem) {
//        if (getType() == null || !getType().equals("PredatorPrey")) {
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

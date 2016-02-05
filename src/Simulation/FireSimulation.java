package Simulation;

import Cell.Cell;
import Cell.FireCell;
import Cell.FireCell.Mark;
import Cell.FireCell.State;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.w3c.dom.Element;


/**
 * Created by rhondusmithwick on 2/4/16.
 *
 * @author Rhondu Smithwick
 */
public class FireSimulation extends Simulation {
    private static final int DEFAULT_BURN_TIME = 5;
    private static final int DEFAULT_PROB_CATCH = 30;
    private static final Paint DEFAULT_EMPTY_VISUAL = Color.YELLOW;
    private static final Paint DEFAULT_BURNING_VISUAL = Color.RED;
    private static final Paint DEFAULT_TREE_VISUAL = Color.GREEN;

    private int burnTime;
    private int probCatch;

    private Paint emptyVisual;
    private Paint burningVisual;
    private Paint treeVisual;

    public FireSimulation() {
        super();
        parseXmlFile("resources/" + "Fire.xml");
    }

    @Override
    void assignInitialState(int randomNum, Cell c) {
        FireCell fc = (FireCell) c;
        fc.removeDiagonals();
        fc.setVisuals(emptyVisual, burningVisual, treeVisual);
        fc.setBurnTime(burnTime);
        if (checkOnEdge(fc)) {
            fc.setMark(Mark.TO_EMPTY);
        } else if (checkInMiddle(fc)) {
            fc.setMark(Mark.TO_BURNING);
        } else {
            fc.setMark(Mark.TO_TREE);
        }
    }

    @Override
    void step() {
        super.step();
        getAllUpdates();
        changeStates();
    }


    private void getAllUpdates() {
        FireCell fc;
        for (Cell c : getTheCells()) {
            fc = (FireCell) c;
            getUpdate(fc);
        }
    }

    private void getUpdate(FireCell fc) {
        if (treeShouldBurn(fc)) {
            fc.setMark(Mark.TO_BURNING);
        } else if (treeDoneBurning(fc)) {
            fc.setMark(Mark.TO_EMPTY);
        }
    }

    @Override
    void setSpecificProperties(Element simElem) {
        if (getType() == null || !getType().equals("Fire")) {
            burnTime = DEFAULT_BURN_TIME;
            probCatch = DEFAULT_PROB_CATCH;
            emptyVisual = DEFAULT_EMPTY_VISUAL;
            burningVisual = DEFAULT_BURNING_VISUAL;
            treeVisual = DEFAULT_TREE_VISUAL;
        } else {
            burnTime = getIntValue(simElem, "burnTime");
            probCatch = getIntValue(simElem, "probCatch");
            emptyVisual = getPaintValue(simElem, "emptyVisual");
            burningVisual = getPaintValue(simElem, "burningVisual");
            treeVisual = getPaintValue(simElem, "treeVisual");
        }
    }


    private boolean treeShouldBurn(FireCell fc) {
        int randomNum = getRandomNum(1, 100);
        return (fc.getState() == State.TREE)
                && (fc.hasBurningNeighbor())
                && (randomNum < probCatch);
    }

    private boolean treeDoneBurning(FireCell fc) {
        return (fc.getState() == State.BURNING)
                && (fc.getBurnTime() > burnTime);
    }

    private boolean checkOnEdge(FireCell fc) {
        return (fc.getRow() == getCellsPerRow() - 1)
                || (fc.getColumn() == getCellsPerColumn() - 1)
                || (fc.getRow() == 0)
                || (fc.getColumn() == 0);
    }

    private boolean checkInMiddle(FireCell fc) {
        return (fc.getRow() == getCellsPerRow() / 2)
                && (fc.getColumn() == getCellsPerColumn() / 2);
    }
}

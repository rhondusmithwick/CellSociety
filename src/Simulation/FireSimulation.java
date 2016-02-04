package Simulation;

import Cell.Cell;
import Cell.FireCell;
import Cell.FireCell.State;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by rhondusmithwick on 2/4/16.
 *
 * @author Rhondu Smithwick
 */
// TO FINISH
public class FireSimulation extends Simulation {
    private static final int DEFAULT_BURN_TIME = 5;
    private static final int DEFAULT_PROB_CATCH = 30;

    private static final Paint DEFAULT_EMPTY_VISUAL = Color.YELLOW;
    private static final Paint DEFAULT_BURNING_VISUAL = Color.RED;
    private static final Paint DEFAULT_TREE_VISUAL = Color.GREEN;

    private int burnTime = DEFAULT_BURN_TIME;
    private int probCatch = DEFAULT_PROB_CATCH;

    private Paint emptyVisual = DEFAULT_EMPTY_VISUAL;
    private Paint burningVisual = DEFAULT_BURNING_VISUAL;
    private Paint treeVisual = DEFAULT_TREE_VISUAL;

    public FireSimulation() {
        super();
        parseXmlFile("resources/" + "GameOfLife.xml");
    }

    void step() {
        super.step();
        Map<FireCell, State> changes = new HashMap<>();
        for (Cell c : getTheCells()) {
            getUpdate(changes, c);
        }
        for (FireCell fc : changes.keySet()) {
            changeState(fc, changes.get(fc));
        }
    }


    private void getUpdate(Map<FireCell, State> changes, Cell c) {
        FireCell fc = (FireCell) c;
        if (treeShouldBurn(fc)) {
            changes.put(fc, State.BURNINING);
        } else if (treeDoneBurning(fc)) {
            changes.put(fc, State.EMPTY);
        }
    }

    private void changeState(FireCell fc, State state) {
        switch (state) {
            case EMPTY:
                fc.setFill(emptyVisual);
                break;
            case BURNINING:
                fc.setFill(burningVisual);
                break;
            case TREE:
                fc.setFill(treeVisual);
                break;
        }
        fc.setState(state);
        fc.setBurnTime(0);
    }

    @Override
    void assignInitialState(int randomNum, Cell c) {
        FireCell fc = (FireCell) c;
        if (checkOnEdge(fc)) {
            changeState(fc, State.EMPTY);
        } else if (checkInMiddle(fc)) {
            changeState(fc, State.BURNINING);
        } else {
            changeState(fc, State.TREE);
        }
    }



    @Override
    void setSpecificProperties(Element simElem) {
//        if (getType() == null || !getType().equals("FIre")) {
//            burnTime = DEFAULT_BURN_TIME;
//            probCatch = DEFAULT_PROB_CATCH;
//            emptyVisual = DEFAULT_EMPTY_VISUAL;
//            burningVisual = DEFAULT_BURNING_VISUAL;
//            treeVisual = DEFAULT_TREE_VISUAL;
//        } else {
//            burnTime = getIntValue(simElem, "burnTime");
//            probCatch = getIntValue(simElem, "probCatch");
//            emptyVisual = getPaintValue(simElem, "emptyVisual");
//            burningVisual = getPaintValue(simElem, "burningVisual");
//            treeVisual = getPaintValue(simElem, "treeVisual");
//        }
    }


    private boolean treeShouldBurn(FireCell fc) {
        if ((fc.getState() == State.TREE)
                && (fc.hasBurningNeighbor())) {
            int randomNum = getRandomNum(1, 100);
            return (randomNum < probCatch);
        }
        return false;
    }

    private boolean treeDoneBurning(FireCell fc) {
        return (fc.getState() == State.BURNINING)
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

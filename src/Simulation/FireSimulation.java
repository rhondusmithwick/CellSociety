package Simulation;

import Cell.Cell;
import Cell.FireCell;
import Cell.FireCell.Mark;
import Cell.FireCell.State;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private Map<String,Object> properties = new HashMap<String,Object>();
    
    private int burnTime;
    private int probCatch;

    private Paint emptyVisual;
    private Paint burningVisual;
    private Paint treeVisual;

    public FireSimulation() {
        super();
        addProperties();
        properties = setProperties(XMLParser.getXmlElement("resources/" + "Fire.xml"),properties);

    }
    
    void addProperties(){
    	properties.put("burnTime",DEFAULT_BURN_TIME);
    	properties.put("probCatch",DEFAULT_PROB_CATCH);
    	properties.put("emptyVisual",DEFAULT_EMPTY_VISUAL);
    	properties.put("burningVisual",DEFAULT_BURNING_VISUAL);
    	properties.put("treeVisual", DEFAULT_TREE_VISUAL);
    }


    @Override
    void assignInitialState(Cell c) {
        FireCell fc = (FireCell) c;
        fc.removeDiagonals();
        fc.setVisuals(emptyVisual, burningVisual, treeVisual);
        fc.setBurnTimer(burnTime);
        if (checkOnEdge(fc)) {
            fc.setMark(Mark.TO_EMPTY);
        } else if (checkInMiddle(fc)) {
            fc.setMark(Mark.TO_BURNING);
        } else {
            fc.setMark(Mark.TO_TREE);
        }
    }

    @Override
    public void step() {
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

 
    private boolean treeShouldBurn(FireCell fc) {
        int randomNum = getRandomNum(1, 100);
        return (fc.getState() == State.TREE)
                && (fc.hasBurningNeighbor())
                && (randomNum < probCatch);
    }

    private boolean treeDoneBurning(FireCell fc) {
        return (fc.getState() == State.BURNING)
                && (fc.getBurnTimer() > burnTime);
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

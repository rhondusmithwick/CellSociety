package Simulation;

import Cell.Cell;
import Cell.FireCell;
import Cell.FireCell.Mark;
import Cell.FireCell.State;
import XML.XMLException;
import XML.XMLParser;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


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

    public FireSimulation() throws XMLException {
        super();
        setProperties(XMLParser.getXmlElement("resources/" + "Fire.xml"));
    }


    @Override
    void assignInitialState(Cell c) {
        FireCell fc = (FireCell) c;
        fc.removeDiagonals();
        fc.setVisuals(emptyVisual, burningVisual, treeVisual);
        fc.setBurnTimer(burnTime);
        if (checkOnEdge(fc)) {
            fc.setMark(Mark.EMPTY);
        } else if (checkInMiddle(fc)) {
            fc.setMark(Mark.BURNING);
        } else {
            fc.setMark(Mark.TREE);
        }
    }
    @Override
    void assignLoadState(Cell c){
    	 FireCell fc = (FireCell) c;
    fc.removeDiagonals();
    fc.setVisuals(emptyVisual, burningVisual, treeVisual);
    fc.setBurnTimer(burnTime);
    setLoadState(fc);
    }

    @Override
    public void step() {
        super.step();
        getAllUpdates();
        changeStates();
    }


    private void getAllUpdates() {
        getTheCells().stream().map(c -> (FireCell) c).forEach(this::getUpdate);
    }

    private void getUpdate(FireCell fc) {
        if (treeShouldBurn(fc)) {
            fc.setMark(Mark.BURNING);
        }

        else if (treeDoneBurning(fc)) {
            fc.setMark(Mark.EMPTY);
        }
        setLoadState(fc);

    }
    private void setLoadState(FireCell fc){
        if(fc.getState()==State.BURNING){
        	fc.setFill(burningVisual);
        }
        if(fc.getState()==State.TREE){
        	fc.setFill(treeVisual);
        }
        if(fc.getState()==State.EMPTY){
        	fc.setFill(emptyVisual);
        }

    }

    @Override
    void setSpecificProperties() {
        if (doesTypeMatch("Fire")) {
            burnTime = DEFAULT_BURN_TIME;
            probCatch = DEFAULT_PROB_CATCH;
            emptyVisual = DEFAULT_EMPTY_VISUAL;
            burningVisual = DEFAULT_BURNING_VISUAL;
            treeVisual = DEFAULT_TREE_VISUAL;
        } else {
            burnTime = xmlProperties.getIntValue("burnTime");
            probCatch = xmlProperties.getIntValue("probCatch");
            emptyVisual = xmlProperties.getPaintValue("emptyVisual");
            burningVisual = xmlProperties.getPaintValue("burningVisual");
            treeVisual = xmlProperties.getPaintValue("treeVisual");
        }
    }

    @Override
    void saveSpecificValues() {
        savedValues.put("burnTime", burnTime);
        savedValues.put("probCatch", probCatch);
        savedValues.put("emptyVisual", emptyVisual);
        savedValues.put("burningVisual", burningVisual);
        savedValues.put("treeVisual", treeVisual);
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


    public double getBurnTime() {
        return burnTime;
    }

    public void setBurnTime(int newTime) {
        burnTime = newTime;
    }

    public double getCatchFire() {
        return probCatch;
    }

    public void setProbCatch(int newProb) {
        probCatch = newProb;
    }


	@Override
	boolean hasGraph() {
		return false;
	}
}

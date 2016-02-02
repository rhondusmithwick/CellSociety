package Simulation;

import Cell.Cell;
import Cell.GameOfLifeCell;

import java.util.Collection;
import java.util.Random;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Created by rhondusmithwick on 1/31/16.
 *
 * @author Rhondu Smithwick, Tavo Loaiza
 */
public class GameOfLifeSimulation extends Simulation {

	private static final double DEFAULT_START_DEAD = 30;
    private static double probStartDead;

    public GameOfLifeSimulation(Collection<Cell> theCells) {
        super(theCells);
    }
    public GameOfLifeSimulation() {
        super();
    }


    public void assignInitialState(int randomNum, Cell c) {
    	if(getType()==null || !getType().equals("GameOfLife")){
    		probStartDead = DEFAULT_START_DEAD;
    	}

        GameOfLifeCell gc = (GameOfLifeCell) c;
        if (randomNum <= probStartDead) {
            gc.destroy();
        } else {
            gc.restore();
        }
    }

    @Override
    protected void step() {
        super.step();
        for (Cell c: getTheCells()) {
            GameOfLifeCell gc = (GameOfLifeCell) c;
            gc.transform();
        }
    }


	@Override
	protected void getTypeProperties(Element simElem) {
		probStartDead = getIntValue(simElem,"probStartDead");

	}
}

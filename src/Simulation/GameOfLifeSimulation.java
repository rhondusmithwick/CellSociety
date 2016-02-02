package Simulation;

import Cell.Cell;
import Cell.GameOfLifeCell;
import org.w3c.dom.Element;

/**
 * Created by rhondusmithwick on 1/31/16.
 *
 * @author Rhondu Smithwick, Tavo Loaiza
 */
public class GameOfLifeSimulation extends Simulation {

    private static final double DEFAULT_START_DEAD = 30;

    private double probStartDead;

    public GameOfLifeSimulation() {
        super();
        parseXmlFile("resources/" + "GameOfLife.xml");
    }


    @Override
    protected void assignInitialState(int randomNum, Cell c) {
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
        for (Cell c : getTheCells()) {
            GameOfLifeCell gc = (GameOfLifeCell) c;
            gc.transform();
        }
    }


    @Override
    protected void setTypeProperties(Element simElem) {
        if (getType() == null || !getType().equals("GameOfLife")) {
            probStartDead = DEFAULT_START_DEAD;
        } else {
            probStartDead = getIntValue(simElem, "probStartDead");
        }

    }
}

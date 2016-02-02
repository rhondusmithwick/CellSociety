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
        super.parseXmlFile("resources/" + "GameOfLife.xml");
    }


    @Override
    void assignInitialState(int randomNum, Cell c) {
        final GameOfLifeCell gc = (GameOfLifeCell) c;
        if (randomNum <= probStartDead) {
            gc.destroy();
        } else {
            gc.restore();
        }
    }

    @Override
    protected void step() {
        super.step();
        GameOfLifeCell gc;
        for (Cell c : getTheCells()) {
            gc = (GameOfLifeCell) c;
            gc.transform();
        }
    }


    @Override
    void setTypeProperties(Element simElem) {
        if (getType() == null || !getType().equals("GameOfLife")) {
            probStartDead = DEFAULT_START_DEAD;
        } else {
            probStartDead = getIntValue(simElem, "probStartDead");
        }

    }
}

package Simulation;

import Cell.Cell;
import Cell.GameOfLifeCell;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.w3c.dom.Element;

/**
 * Created by rhondusmithwick on 1/31/16.
 *
 * @author Rhondu Smithwick, Tavo Loaiza
 */
public class GameOfLifeSimulation extends Simulation {

    private static final double DEFAULT_START_DEAD = 30;
    private static final Paint DEFAULT_DEAD_VISUAL = Color.WHITE;
    private static final Paint DEFAULT_ALIVE_VISUAL = Color.BLACK;


    private double probStartDead;

    private Paint deadVisual = DEFAULT_DEAD_VISUAL;
    private Paint aliveVisual = DEFAULT_ALIVE_VISUAL;

    public GameOfLifeSimulation() {
        super();
        parseXmlFile("resources/" + "GameOfLife.xml");
    }


    @Override
    void assignInitialState(int randomNum, Cell c) {
        final GameOfLifeCell gc = (GameOfLifeCell) c;
        if (randomNum <= probStartDead) {
            gc.destroy(deadVisual);
        } else {
            gc.restore(aliveVisual);
        }
    }

    @Override
    protected void step() {
        super.step();
        GameOfLifeCell gc;
        for (Cell c : getTheCells()) {
            gc = (GameOfLifeCell) c;
            gc.transform(deadVisual, aliveVisual);
        }
    }


    @Override
    void setSpecificProperties(Element simElem) {
//        if (getType() == null || !getType().equals("GameOfLife")) {
//            probStartDead = DEFAULT_START_DEAD;
//            deadVisual = DEFAULT_DEAD_VISUAL;
//            aliveVisual = DEFAULT_ALIVE_VISUAL;
//        } else {
//            probStartDead = getIntValue(simElem, "probStartDead");
//            deadVisual = getPaintValue(simElem, "deadVisual");
//            aliveVisual = getPaintValue(simElem, "aliveVisual");
//        }
    }
}

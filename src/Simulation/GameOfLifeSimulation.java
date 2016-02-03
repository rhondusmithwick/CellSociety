package Simulation;

import Cell.Cell;
import Cell.GameOfLifeCell;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Created by rhondusmithwick on 1/31/16.
 *
 * @author Rhondu Smithwick, Tavo Loaiza
 */
public class GameOfLifeSimulation extends Simulation {

    private static final double DEFAULT_START_DEAD = 30;
    private static final Paint DEFAULT_DEAD_COLOR = Color.WHITE;
    private static final Paint DEFAULT_ALIVE_COLOR = Color.BLACK;


    private double probStartDead;
    private Paint deadColor = DEFAULT_DEAD_COLOR;
    private Paint aliveColor = DEFAULT_ALIVE_COLOR;

    public GameOfLifeSimulation() {
        super();
        parseXmlFile("resources/" + "GameOfLife.xml");
    }


    @Override
    void assignInitialState(int randomNum, Cell c) {
        final GameOfLifeCell gc = (GameOfLifeCell) c;
        if (randomNum <= probStartDead) {
            gc.destroy(deadColor);
        } else {
            gc.restore(aliveColor);
        }
    }

    @Override
    protected void step() {
        super.step();
        GameOfLifeCell gc;
        for (Cell c : getTheCells()) {
            gc = (GameOfLifeCell) c;
            gc.transform(deadColor, aliveColor);
        }
    }


    @Override
    void setSpecificProperties(Element simElem) {
        if (getType() == null || !getType().equals("GameOfLife")) {
            probStartDead = DEFAULT_START_DEAD;
            deadColor = DEFAULT_DEAD_COLOR;
            aliveColor = DEFAULT_ALIVE_COLOR;
        } else {
            probStartDead = getIntValue(simElem, "probStartDead");
//            deadColor = getPaintValue(simElem, "deadColor");
//            aliveColor = getPaintValue(simElem, "aliveColor");
        }

    }
}

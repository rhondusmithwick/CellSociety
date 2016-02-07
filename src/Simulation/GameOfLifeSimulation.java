package Simulation;

import Cell.Cell;
import Cell.GameOfLifeCell;
import Cell.GameOfLifeCell.Mark;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.w3c.dom.Element;

/**
 * Created by rhondusmithwick on 1/31/16.
 *
 * @author Rhondu Smithwick, Tavo Loaiza
 */
public class GameOfLifeSimulation extends Simulation {

    private static final double DEFAULT_START_DEAD = 50;
    private static final Paint DEFAULT_DEAD_VISUAL = Color.WHITE;
    private static final Paint DEFAULT_ALIVE_VISUAL = Color.GREEN;

    private double probStartDead;

    private Paint deadVisual;
    private Paint aliveVisual;

    public GameOfLifeSimulation() {
        super();
        setProperties(XMLParser.getXmlElement("resources/" + "GameOfLife.xml"));
    }

    @Override
    public void step() {
        super.step();
        changeStates();
    }

    @Override
    void assignInitialState(int randomNum, Cell c) {
        final GameOfLifeCell gc = (GameOfLifeCell) c;
        gc.setVisuals(deadVisual, aliveVisual);
        if (randomNum <= probStartDead) {
            gc.setMark(Mark.DESTROY);
        } else {
            gc.setMark(Mark.RESURECT);
        }
    }

    @Override
    void setSpecificProperties(Element simElem) {
        if (getType() == null || !getType().equals("GameOfLife")) {
            probStartDead = DEFAULT_START_DEAD;
            deadVisual = DEFAULT_DEAD_VISUAL;
            aliveVisual = DEFAULT_ALIVE_VISUAL;
        } else {
            probStartDead = XMLParser.getIntValue(simElem, "probStartDead");
            deadVisual = XMLParser.getPaintValue(simElem, "deadVisual");
            aliveVisual = XMLParser.getPaintValue(simElem, "aliveVisual");
        }
    }
}

package Simulation;

import Cell.Cell;
import Cell.GameOfLifeCell;
import Cell.GameOfLifeCell.Mark;
import XML.XMLException;
import XML.XMLParser;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Created by rhondusmithwick on 1/31/16.
 *
 * @author Rhondu Smithwick
 */
public class GameOfLifeSimulation extends Simulation {

    private static final double DEFAULT_START_DEAD = 50;
    private static final Paint DEFAULT_DEAD_VISUAL = Color.WHITE;
    private static final Paint DEFAULT_ALIVE_VISUAL = Color.GREEN;

    private double probStartDead;

    private Paint deadVisual;
    private Paint aliveVisual;

    public GameOfLifeSimulation() throws XMLException {
        super();
        setProperties(XMLParser.getXmlElement("resources/" + "GameOfLife.xml"));
    }

    @Override
    public void step() {
        super.step();
        changeStates();
    }

    @Override
    void assignInitialState(Cell c) {
        int randomNum = getRandomNum(1, 100);
        final GameOfLifeCell gc = (GameOfLifeCell) c;
        gc.setVisuals(deadVisual, aliveVisual);
        if (randomNum <= probStartDead) {
            gc.setMark(Mark.DEAD);
        } else {
            gc.setMark(Mark.ALIVE);
        }
    }


    @Override
    void setSpecificProperties() {
        if (doesTypeMatch("GameOfLife")) {
            probStartDead = DEFAULT_START_DEAD;
            deadVisual = DEFAULT_DEAD_VISUAL;
            aliveVisual = DEFAULT_ALIVE_VISUAL;
        } else {
            probStartDead = xmlProperties.getIntValue("probStartDead");
            deadVisual = xmlProperties.getPaintValue("deadVisual");
            aliveVisual = xmlProperties.getPaintValue("aliveVisual");
        }
    }

    @Override
    void saveSpecificValues() {
        savedValues.put("probStartDead", probStartDead);
        savedValues.put("deadVisual", deadVisual);
        savedValues.put("aliveVisual", aliveVisual);
    }

    @Override
    void assignLoadState(Cell c) {
        // TODO Auto-generated method stub
    }

    boolean hasGraph() {
        return false;
    }
}

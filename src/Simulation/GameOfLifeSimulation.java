package Simulation;

import Cell.Cell;
import Cell.GameOfLifeCell;
import Cell.GameOfLifeCell.Mark;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;

/**
 * Created by rhondusmithwick on 1/31/16.
 *
 * @author Rhondu Smithwick
 */
public class GameOfLifeSimulation extends Simulation {

    private static final double DEFAULT_START_DEAD = 50;
    private static final Paint DEFAULT_DEAD_VISUAL = Color.WHITE;
    private static final Paint DEFAULT_ALIVE_VISUAL = Color.GREEN;

    private Map<String,Object> properties = new HashMap<String,Object>();
    
    private double probStartDead;

    private Paint deadVisual;
    private Paint aliveVisual;

    public GameOfLifeSimulation() {
        super();
        addProperties();
        properties = setProperties(XMLParser.getXmlElement("resources/" + "GameOfLife.xml"),properties);
    }
    
    void addProperties(){
    	properties.put("probStartDead",DEFAULT_START_DEAD);
    	properties.put("deadVisual",DEFAULT_DEAD_VISUAL);
    	properties.put("aliveVisual",DEFAULT_ALIVE_VISUAL);

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
            gc.setMark(Mark.DESTROY);
        } else {
            gc.setMark(Mark.RESURECT);
        }
    }


}

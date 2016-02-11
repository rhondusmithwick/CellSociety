package Simulation;

import Cell.Cell;
import Cell.PredatorPreyCell;
import Cell.PredatorPreyCell.Mark;
import Cell.PredatorPreyCell.State;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;

/**
 * Created by rhondusmithwick on 2/3/16.
 *
 * @author Rhondu Smithwick
 */
public class PredatorPreySimulation extends Simulation {
    private static final int DEFAULT_FISH_BREED_TIME = 10;
    private static final int DEFAULT_SHARK_BREED_TIME = 15;
    private static final int DEFAULT_STARVE_TIME = 5;
    private static final int DEFAULT_EMPTY_PERCENT = 40;
    private static final int DEFAULT_FISH_PERCENT = 50;

    private static final Paint DEFAULT_EMPTY_VISUAL = Color.BLUE;
    private static final Paint DEFAULT_FISH_VISUAL = Color.LIMEGREEN;
    private static final Paint DEFAULT_SHARK_VISUAL = Color.YELLOW;

    private Map<String,Object> properties = new HashMap<String,Object>();
    
    private int sharkBreedTime;
    private int fishBreedTime;
    private int starveTime;
    private int emptyPercent;
    private int fishPercent;

    private Paint emptyVisual;
    private Paint fishVisual;
    private Paint sharkVisual;


    public PredatorPreySimulation() {
        super();
        addProperties();
        properties = setProperties(XMLParser.getXmlElement("resources/" + "PredatorPrey.xml"),properties);
    }
    
    void addProperties(){
    	properties.put("sharkBreedTime",DEFAULT_SHARK_BREED_TIME);
    	properties.put("fishBreedTime",DEFAULT_FISH_BREED_TIME);
    	properties.put("starveTime", DEFAULT_STARVE_TIME);
    	properties.put("emptyPercent",DEFAULT_EMPTY_PERCENT);
    	properties.put("fishPercent", DEFAULT_FISH_PERCENT);
    	properties.put("emptyVisual", DEFAULT_EMPTY_VISUAL);
    	properties.put("fishVisual", DEFAULT_FISH_VISUAL);
    	properties.put("sharkVisual", DEFAULT_SHARK_VISUAL);
    }

    @Override
    void assignInitialState(Cell c) {
        int randomNum = getRandomNum(1, 100);
        final PredatorPreyCell ppc = (PredatorPreyCell) c;
        ppc.setVisuals(emptyVisual, fishVisual, sharkVisual);
        if (randomNum <= emptyPercent) {
            ppc.setMark(Mark.TO_EMPTY);
        } else if (randomNum > emptyPercent
                && randomNum <= emptyPercent + fishPercent) {
            ppc.setMark(Mark.TO_FISH);
        } else {
            ppc.setMark(Mark.TO_SHARK);
        }
    }

    @Override
    public void step() {
        super.step();
        breedAll();
        moveAll();
        changeStates();
    }


    private void breedAll() {
        PredatorPreyCell ppc;
        for (Cell c : getTheCells()) {
            ppc = (PredatorPreyCell) c;
            if (ppc.shouldBreed(fishBreedTime, sharkBreedTime)) {
                ppc.setShouldBreed(true);
                ppc.setBreedTimer(0);
            }
        }
    }

    private void moveAll() {
        PredatorPreyCell ppc;
        for (Cell c : getTheCells()) {
            ppc = (PredatorPreyCell) c;
            if (ppc.getState() == State.SHARK) {
                sharkUpdate(ppc);
            } else if (ppc.canMoveOrSpawn()) {
                ppc.move();
            }
        }
    }


    private void sharkUpdate(PredatorPreyCell shark) {
        if (shark.shouldStarve(starveTime)) {
            shark.setMark(Mark.TO_EMPTY);
        } else if (!shark.sharkEat()) {
            if (shark.canMoveOrSpawn()) {
                shark.move();
            }
        }
    }

}

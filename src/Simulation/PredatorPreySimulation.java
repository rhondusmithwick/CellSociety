package Simulation;

import Cell.Cell;
import Cell.PredatorPreyCell;
import Cell.PredatorPreyCell.Mark;
import Cell.PredatorPreyCell.State;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
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
        setProperties(XMLParser.getXmlElement("resources/" + "PredatorPrey.xml"));
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

    @Override
    void setSpecificProperties(Element simElem) {
        if (getType() == null || !getType().equals("PredatorPrey")) {
            sharkBreedTime = DEFAULT_SHARK_BREED_TIME;
            fishBreedTime = DEFAULT_FISH_BREED_TIME;
            starveTime = DEFAULT_STARVE_TIME;
            emptyPercent = DEFAULT_EMPTY_PERCENT;
            fishPercent = DEFAULT_FISH_PERCENT;
            emptyVisual = DEFAULT_EMPTY_VISUAL;
            fishVisual = DEFAULT_FISH_VISUAL;
            sharkVisual = DEFAULT_SHARK_VISUAL;
        } else {
            sharkBreedTime = XMLParser.getIntValue(simElem, "sharkBreedTime");
            fishBreedTime = XMLParser.getIntValue(simElem, "fishBreedTime");
            starveTime = XMLParser.getIntValue(simElem, "starveTime");
            emptyPercent = XMLParser.getIntValue(simElem, "emptyPercent");
            fishPercent = XMLParser.getIntValue(simElem, "fishPercent");
            emptyVisual = XMLParser.getPaintValue(simElem, "emptyVisual");
            fishVisual = XMLParser.getPaintValue(simElem, "fishVisual");
            sharkVisual = XMLParser.getPaintValue(simElem, "sharkVisual");
        }
    }


	public double getSharkBreedTime() {
		
		return sharkBreedTime;
	}


	public double getFishBreedTime() {
		return fishBreedTime;
	}


	public double getStarveTime() {
		return starveTime;
	}


	public void setSharkBreed(int intValue) {
		sharkBreedTime = intValue;
		
	}


	public void setFishBreed(int intValue) {
		fishBreedTime = intValue;
		
	}


	public void setStarveTime(int intValue) {
		starveTime = intValue;
	}
}

package Simulation;

import Cell.Cell;
import Cell.PredatorPreyCell;
import Cell.PredatorPreyCell.Mark;
import Cell.PredatorPreyCell.State;
import XML.XMLException;
import XML.XMLParser;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

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


    public PredatorPreySimulation() throws XMLException {
        super();
        setProperties(XMLParser.getXmlElement("resources/" + "PredatorPrey.xml"));
    }


    @Override
    void assignInitialState(Cell c) {
        int randomNum = getRandomNum(1, 100);
        final PredatorPreyCell ppc = (PredatorPreyCell) c;
        ppc.setVisuals(emptyVisual, fishVisual, sharkVisual);
        if (randomNum <= emptyPercent) {
            ppc.setMark(Mark.EMPTY);
        } else if (randomNum <= emptyPercent + fishPercent) {
            ppc.setMark(Mark.FISH);
        } else {
            ppc.setMark(Mark.SHARK);
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
        getTheCells().stream()
                .map(c -> (PredatorPreyCell) c)
                .forEach(ppc -> ppc.breedIfShould(fishBreedTime, sharkBreedTime));
    }


    private void moveAll() {
        getTheCells().stream()
                .map(c -> (PredatorPreyCell) c)
                .forEach(this::doMove);
    }

    private void doMove(PredatorPreyCell ppc) {
        if (ppc.getState() == State.SHARK) {
            sharkUpdate(ppc);
        } else if (ppc.canMoveOrSpawn()) {
            ppc.move();
        }
    }

    private void sharkUpdate(PredatorPreyCell shark) {
        if (shark.shouldStarve(starveTime)) {
            shark.setMark(Mark.EMPTY);
        } else if (!shark.sharkEat()) {
            if (shark.canMoveOrSpawn()) {
                shark.move();
            }
        }
    }

    @Override
    void setSpecificProperties() {
        if (doesTypeMatch("PredatorPrey")) {
            sharkBreedTime = DEFAULT_SHARK_BREED_TIME;
            fishBreedTime = DEFAULT_FISH_BREED_TIME;
            starveTime = DEFAULT_STARVE_TIME;
            emptyPercent = DEFAULT_EMPTY_PERCENT;
            fishPercent = DEFAULT_FISH_PERCENT;
            emptyVisual = DEFAULT_EMPTY_VISUAL;
            fishVisual = DEFAULT_FISH_VISUAL;
            sharkVisual = DEFAULT_SHARK_VISUAL;
        } else {
            sharkBreedTime = xmlProperties.getIntValue("sharkBreedTime");
            fishBreedTime = xmlProperties.getIntValue("fishBreedTime");
            starveTime = xmlProperties.getIntValue("starveTime");
            emptyPercent = xmlProperties.getIntValue("emptyPercent");
            fishPercent = xmlProperties.getIntValue("fishPercent");
            emptyVisual = xmlProperties.getPaintValue("emptyVisual");
            fishVisual = xmlProperties.getPaintValue("fishVisual");
            sharkVisual = xmlProperties.getPaintValue("sharkVisual");
        }
    }

    @Override
    void saveSpecificValues() {
        savedValues.put("sharkBreedTime", sharkBreedTime);
        savedValues.put("fishBreedTime", fishBreedTime);
        savedValues.put("starveTime", starveTime);
        savedValues.put("emptyPercent", emptyPercent);
        savedValues.put("fishPercent", fishPercent);
        savedValues.put("emptyVisual", emptyVisual);
        savedValues.put("fishVisual", fishVisual);
        savedValues.put("sharkVisual", sharkVisual);
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

    public void setStarveTime(int intValue) {
        starveTime = intValue;
    }

    public void setSharkBreed(int intValue) {
        sharkBreedTime = intValue;

    }

    public void setFishBreed(int intValue) {
        fishBreedTime = intValue;

    }

}

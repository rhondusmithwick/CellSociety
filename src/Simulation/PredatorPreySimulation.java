package Simulation;

import Cell.Cell;
import Cell.PredatorPreyCell;
import Cell.PredatorPreyCell.Mark;
import Cell.PredatorPreyCell.State;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.w3c.dom.Element;

import java.util.List;

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

    private static boolean canMoveOrSpawn(PredatorPreyCell ppc) {
        return (ppc.getMark() != Mark.TO_EMPTY)
                || (ppc.getBreeding());
    }

    @Override
    void assignInitialState(int randomNum, Cell c) {
        final PredatorPreyCell ppc = (PredatorPreyCell) c;
        ppc.removeDiagonals();
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
        haveSharksEat();
        moveAll();
        changeStates();
    }


    private void breedAll() {
        PredatorPreyCell ppc;
        for (Cell c : getTheCells()) {
            ppc = (PredatorPreyCell) c;
            if (shouldBreed(ppc)) {
                ppc.setBreeding(true);
                ppc.setBreedTimer(0);
            }
        }
    }

    private void haveSharksEat() {
        PredatorPreyCell ppc;
        for (Cell c : getTheCells()) {
            ppc = (PredatorPreyCell) c;
            if (ppc.getState() == State.SHARK) {
                if (ppc.getStarveCounter() >= starveTime) {
                    ppc.setMark(Mark.TO_EMPTY);
                } else {
                    sharkEat(ppc);
                }
            }
        }
    }

    private void sharkEat(PredatorPreyCell shark) {
        List<PredatorPreyCell> fishNeighbors = shark.getNeighborsOfState(State.FISH);
        if (!fishNeighbors.isEmpty()) {
            int randomNum = getRandomNum(0, fishNeighbors.size() - 1);
            PredatorPreyCell fish = fishNeighbors.get(randomNum);
            fish.setMark(Mark.TO_EMPTY);
            shark.setStarveCounter(0);
        }
    }

    private void moveAll() {
        PredatorPreyCell ppc;
        for (Cell c : getTheCells()) {
            ppc = (PredatorPreyCell) c;
            if (ppc.getState() != State.EMPTY) {
                move(ppc);
            }
        }
    }

    private void move(PredatorPreyCell ppc) {
        if (canMoveOrSpawn(ppc)) {
            List<PredatorPreyCell> emptyNeighbors = ppc.getNeighborsOfState(State.EMPTY);
            if (!emptyNeighbors.isEmpty()) {
                int randomIndex = getRandomNum(0, emptyNeighbors.size() - 1);
                PredatorPreyCell emptyNeighbor = emptyNeighbors.get(randomIndex);
                moveSpawn(ppc, emptyNeighbor);
            }
        }
    }

    private void moveSpawn(PredatorPreyCell ppc, PredatorPreyCell emptyNeighbor) {
        swap(ppc, emptyNeighbor);
        if (shouldMakeEmpty(ppc)) {
            ppc.setMark(Mark.TO_EMPTY);
        }
        ppc.setBreeding(false);
    }

    private void swap(PredatorPreyCell ppc, PredatorPreyCell emptyNeighbor) {
        switch (ppc.getState()) {
            case FISH:
                emptyNeighbor.setMark(Mark.TO_FISH);
                break;
            case SHARK:
                emptyNeighbor.setMark(Mark.TO_SHARK);
                break;
        }
        emptyNeighbor.setBreedTimer(ppc.getBreedTimer());
        emptyNeighbor.setStarveCounter(ppc.getStarveCounter());
        ppc.setBreedTimer(0);
        ppc.setStarveCounter(0);
    }

    private boolean shouldBreed(PredatorPreyCell ppc) {
        return fishShouldBreed(ppc) || sharkShouldBreed(ppc);
    }

    private boolean fishShouldBreed(PredatorPreyCell ppc) {
        return (ppc.getState() == State.FISH)
                && (ppc.getBreedTimer() >= fishBreedTime);
    }

    private boolean sharkShouldBreed(PredatorPreyCell ppc) {
        return (ppc.getState() == State.SHARK)
                && (ppc.getBreedTimer() >= sharkBreedTime);
    }

    private boolean shouldMakeEmpty(PredatorPreyCell ppc) {
        return (!ppc.getBreeding())
                || (ppc.getBreeding() && ppc.getMark() == Mark.TO_EMPTY);
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
}

package Simulation;

import Cell.Cell;
import Cell.PredatorPreyCell;
import Cell.PredatorPreyCell.Mark;
import Cell.PredatorPreyCell.State;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.w3c.dom.Element;

import java.util.Collections;
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


    @Override
    void assignInitialState(int randomNum, Cell c) {
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
                ppc.setBreeding(true);
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
                move(ppc);
            }
        }
    }


    private void sharkUpdate(PredatorPreyCell shark) {
        if (shark.shouldStarve(starveTime)) {
            shark.setMark(Mark.TO_EMPTY);
        } else if (!sharkEat(shark)) {
            if (shark.canMoveOrSpawn()) {
                move(shark);
            }
        }
    }

    private static boolean sharkEat(PredatorPreyCell ppc) {
        List<PredatorPreyCell> fishNeighbors = ppc.getNeighborsOfState(State.FISH);
        if (!fishNeighbors.isEmpty()) {
            Collections.shuffle(fishNeighbors);
            PredatorPreyCell fish = fishNeighbors.get(0);
            ppc.setStarveCounter(0);
            moveSpawn(ppc, fish);
            return true;
        }
        return false;
    }

    private static void move(PredatorPreyCell ppc) {
        List<PredatorPreyCell> emptyNeighbors = ppc.getNeighborsOfState(State.EMPTY);
        if (!emptyNeighbors.isEmpty()) {
            Collections.shuffle(emptyNeighbors);
            PredatorPreyCell emptyNeighbor = emptyNeighbors.get(0);
            moveSpawn(ppc, emptyNeighbor);
        }
    }

    private static void moveSpawn(PredatorPreyCell ppc, PredatorPreyCell cellToMoveTo) {
        swap(ppc, cellToMoveTo);
        if (ppc.shouldMakeEmpty()) {
            ppc.setMark(Mark.TO_EMPTY);
        }
        ppc.setBreeding(false);
    }

    private static void swap(PredatorPreyCell cellToMove, PredatorPreyCell cellToMoveTo) {
        switch (cellToMove.getState()) {
            case FISH:
                cellToMoveTo.setMark(Mark.TO_FISH);
                break;
            case SHARK:
                cellToMoveTo.setMark(Mark.TO_SHARK);
                break;
        }
        cellToMoveTo.setBreedTimer(cellToMove.getBreedTimer());
        cellToMoveTo.setStarveCounter(cellToMove.getStarveCounter());
        cellToMove.setBreedTimer(0);
        cellToMove.setStarveCounter(0);
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

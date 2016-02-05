package Simulation;

import Cell.Cell;
import Cell.PredatorPreyCell;
<<<<<<< HEAD
=======
import Cell.PredatorPreyCell.Mark;
>>>>>>> Rhondu-Branch
import Cell.PredatorPreyCell.State;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.w3c.dom.Element;

<<<<<<< HEAD
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
=======
>>>>>>> Rhondu-Branch
import java.util.List;
import java.util.Map;

/**
 * Created by rhondusmithwick on 2/3/16.
 *
 * @author Rhondu Smithwick
 */
// TO FINISH
public class PredatorPreySimulation extends Simulation {
    private static final int DEFAULT_BREED_TIME = 10;
    private static final int DEFAULT_STARVE_TIME = 2;
    private static final int DEFAULT_EMPTY_PERCENT = 40;
    private static final int DEFAULT_FISH_PERCENT = 50;

    private static final Paint DEFAULT_EMPTY = Color.BLUE;
    private static final Paint DEFAULT_FISH = Color.LIMEGREEN;
    private static final Paint DEFAULT_SHARK = Color.YELLOW;

<<<<<<< HEAD

    private int breedTime = DEFAULT_BREED_TIME;
    private int emptyPercent = DEFAULT_EMPTY_PERCENT;
    private int fishPercent = DEFAULT_FISH_PERCENT;
=======
    private int breedTime;
    private int starveTime;
    private int emptyPercent;
    private int fishPercent;
>>>>>>> Rhondu-Branch

    private Paint emptyVisual = DEFAULT_EMPTY;
    private Paint fishVisual = DEFAULT_FISH;
    private Paint sharkVisual = DEFAULT_SHARK;


    private Collection<PredatorPreyCell> createFishCells;
    private Collection<PredatorPreyCell> createSharkCells;
    private Map<PredatorPreyCell, PredatorPreyCell> moves;
    private Map<PredatorPreyCell, PredatorPreyCell> theMoves;

    public PredatorPreySimulation() {
        super();
        parseXmlFile("resources/" + "GameOfLife.xml");
    }

    private static boolean canMove(PredatorPreyCell ppc) {
        return (ppc.getMark() != Mark.TO_EMPTY)
                || (ppc.getBreeding());
    }

    @Override
    void assignInitialState(int randomNum, Cell c) {
        final PredatorPreyCell ppc = (PredatorPreyCell) c;
        if (randomNum <= emptyPercent) {
<<<<<<< HEAD
            ppc.setFill(emptyVisual);
            ppc.setState(State.EMPTY);
=======
            ppc.setMark(Mark.TO_EMPTY);
>>>>>>> Rhondu-Branch
        } else if (randomNum > emptyPercent
                && randomNum <= emptyPercent + fishPercent) {
        	setAsFish(ppc);
        } else {
<<<<<<< HEAD
           setAsShark(ppc);
        }
    }

    protected void step() {
        super.step();
        createFishCells = new LinkedList<>();
        createSharkCells = new LinkedList<>();
        moves = new HashMap<>();
        PredatorPreyCell ppc;
        for (Cell c : getTheCells()) {
            ppc = (PredatorPreyCell) c;
            switch (ppc.getState()) {
                case SHARK:
                    sharkUpdate(ppc);
                    break;
                case FISH:
                    fishUpdate(ppc);
                    break;
                default:
=======
            ppc.setMark(Mark.TO_SHARK);
        }
    }

    @Override
    void step() {
        super.step();
        haveSharksEat();
        breedAll();
        moveAll();
        changeStates();
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
>>>>>>> Rhondu-Branch
            }
        }
    }

<<<<<<< HEAD
    private void setAsFish(PredatorPreyCell ppc){
    	 ppc.setFill(fishVisual);
         ppc.setState(State.FISH);
         ppc.setEdible(true);
    }
    private void setAsShark(PredatorPreyCell ppc ){
    	 ppc.setFill(sharkVisual);
         ppc.setState(State.SHARK);
=======
    private void sharkEat(PredatorPreyCell shark) {
        List<PredatorPreyCell> fishNeighbors = shark.getNeighborsOfState(State.FISH);
        if (!fishNeighbors.isEmpty()) {
            int randomNum = getRandomNum(0, fishNeighbors.size() - 1);
            PredatorPreyCell fish = fishNeighbors.get(randomNum);
            fish.setMark(Mark.TO_EMPTY);
            shark.setStarveCounter(0);
        }
>>>>>>> Rhondu-Branch
    }
    private void fishUpdate(PredatorPreyCell fish) {
    	fish.handleUpdate();
    	List<PredatorPreyCell> freeSpace = fish.countNeighbors(State.EMPTY);

    	if(freeSpace.size()>1){
    		if(fish.canBreed()){
        		setAsFish(freeSpace.get((int)(Math.random() * ( freeSpace.size() ))));
        	}
    		else{
    			;//TODO: Move
    		}
    	}

<<<<<<< HEAD
    }


    private void sharkUpdate(PredatorPreyCell shark) {
    	shark.handleUpdate();
    	List<PredatorPreyCell> freeSpace = shark.countNeighbors(State.EMPTY);

    	if(freeSpace.size()>1){
    		if(shark.canBreed()){
        		setAsShark(freeSpace.get((int)(Math.random() * ( freeSpace.size() ))));
        	}
    		else{
    			;//TODO: Move
    		}
    	}
=======
    private void breedAll() {
        PredatorPreyCell ppc;
        for (Cell c : getTheCells()) {
            ppc = (PredatorPreyCell) c;
            if ((ppc.getState() != State.EMPTY)
                    && (ppc.getBreedCounter() >= breedTime)) {
                ppc.setBreeding(true);
                ppc.setBreedCounter(0);
            }
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
        if (canMove(ppc)) {
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
        if (onlyMoving(ppc)) {
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
        emptyNeighbor.setBreedCounter(ppc.getBreedCounter());
        emptyNeighbor.setStarveCounter(ppc.getStarveCounter());
        ppc.setBreedCounter(0);
        ppc.setStarveCounter(0);
    }

    private boolean onlyMoving(PredatorPreyCell ppc) {
        return (!ppc.getBreeding())
                || (ppc.getBreeding() && ppc.getMark() == Mark.TO_EMPTY);
>>>>>>> Rhondu-Branch
    }

    @Override
    void setSpecificProperties(Element simElem) {
<<<<<<< HEAD
//        if (getType() == null || !getType().equals("PredatorPrey")) {
//            threshold = DEFAULT_THRESHOLD;
//            emptyPercent = DEFAULT_EMPTY_PERCENT;
//            group1Percent = DEFAULT_GROUP1_PERCENT;
//        } else {
//            threshold = getIntValue(simElem, "threshold");
//            emptyPercent = getIntValue(simElem, "emptyPercent");
//            group1Percent = getIntValue(simElem, "group1Percent");
//        }
=======
        if (getType() == null || !getType().equals("PredatorPrey")) {
            breedTime = DEFAULT_BREED_TIME;
            starveTime = DEFAULT_STARVE_TIME;
            emptyPercent = DEFAULT_EMPTY_PERCENT;
            fishPercent = DEFAULT_FISH_PERCENT;
            emptyVisual = DEFAULT_EMPTY_VISUAL;
            fishVisual = DEFAULT_FISH_VISUAL;
            sharkVisual = DEFAULT_SHARK_VISUAL;
        } else {
            breedTime = getIntValue(simElem, "breedTime");
            starveTime = getIntValue(simElem, "starveTime");
            emptyPercent = getIntValue(simElem, "emptyPercent");
            fishPercent = getIntValue(simElem, "fishPercent");
            emptyVisual = getPaintValue(simElem, "emptyVisual");
            fishVisual = getPaintValue(simElem, "fishVisual");
            sharkVisual = getPaintValue(simElem, "sharkVisual");
        }
>>>>>>> Rhondu-Branch
    }
}

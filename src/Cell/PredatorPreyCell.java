package Cell;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by rhondusmithwick on 2/1/16.
 *
 * @author Rhondu Smithwick
 */
// TO FINISH
public class PredatorPreyCell extends Cell {

<<<<<<< HEAD
    private State state;
    private int turnsSurvived;
    private int breedCountdown;
    private int starveCountdown;
    private boolean isEdible = false;
=======
    private int breedCounter;
    private int starveCounter;
    private boolean breeding = false;

    private Paint emptyVisual;
    private Paint fishVisual;
    private Paint sharkVisual;
>>>>>>> Rhondu-Branch

    public PredatorPreyCell() {
        super();
        removeDiagonals();
    }

    public void handleUpdate() {
<<<<<<< HEAD
        turnsSurvived++;
        breedCountdown--;
        starveCountdown--;
=======
        breedCounter++;
        starveCounter++;
>>>>>>> Rhondu-Branch
    }

    public void setStarveCountdown(int countdown){
    	starveCountdown = countdown;
    }

    public boolean isStarved()
    {
    	return (starveCountdown<1);
    }

    public void setBreedCountdown(int countdown){
    	breedCountdown = countdown;
    }

    public boolean canBreed()
    {
    	return (breedCountdown<1);
    }



    public List<PredatorPreyCell> countNeighbors(State state) {
        List<PredatorPreyCell> neighborsOfState = new LinkedList<>();
        PredatorPreyCell neighbor;
        for (Cell c : neighbors) {
            neighbor = (PredatorPreyCell) c;
            if (neighbor.getMark() == Mark.NONE
                    && neighbor.getState() == state) {
                neighborsOfState.add(neighbor);
            }
        }
        return neighborsOfState;
    }

<<<<<<< HEAD
    public boolean getEdible() {
        return isEdible;
=======
    public void changeState() {
        switch (getMark()) {
            case NONE:
                return;
            case TO_FISH:
                setFill(fishVisual);
                setState(State.FISH);
                break;
            case TO_SHARK:
                setFill(sharkVisual);
                setState(State.SHARK);
                break;
            case TO_EMPTY:
                setFill(emptyVisual);
                setState(State.EMPTY);
                break;
            default:
        }
        setMark(Mark.NONE);
    }

    public boolean getBreeding() {
        return breeding;
>>>>>>> Rhondu-Branch
    }

    public void setBreeding(boolean t) {
        breeding = t;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
        turnsSurvived = 0;
    }

    public int getBreedCounter() {
        return breedCounter;
    }

    public void setBreedCounter(int breedCounter) {
        this.breedCounter = breedCounter;
    }

    public int getStarveCounter() {
        return starveCounter;
    }

    public void setStarveCounter(int starveCounter) {
        this.starveCounter = starveCounter;
    }

    public enum State {
        SHARK, FISH, EMPTY
    }
}

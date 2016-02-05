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

    private State state;
    private int turnsSurvived;
    private int breedCountdown;
    private int starveCountdown;
    private boolean isEdible = false;

    public PredatorPreyCell() {
        super();
        removeDiagonals();
    }

    public void handleUpdate() {
        turnsSurvived++;
        breedCountdown--;
        starveCountdown--;
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
            if (neighbor.getState() == state) {
                neighborsOfState.add(neighbor);
            }
        }
        return neighborsOfState;
    }

    public boolean getEdible() {
        return isEdible;
    }

    public void setEdible(boolean t) {
        isEdible = t;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
        turnsSurvived = 0;
    }

    public int getTurnsSurvived() {
        return turnsSurvived;
    }

    public void setTurnsSurvived(int turnsSurvived) {
        this.turnsSurvived = turnsSurvived;
    }

    public enum State {
        SHARK, FISH, EMPTY
    }
}

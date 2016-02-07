package Cell;

import javafx.scene.paint.Paint;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by rhondusmithwick on 2/1/16.
 *
 * @author Rhondu Smithwick
 */
public class PredatorPreyCell extends Cell {
    /**
     * This cell's state.
     */
    private State state;
    /**
     * This cell's mark.
     */
    private Mark mark;

    private int breedTimer;
    private int starveCounter;
    private boolean breeding = false;

    private Paint emptyVisual;
    private Paint fishVisual;
    private Paint sharkVisual;

    public PredatorPreyCell() {
        super();
    }

    public void handleUpdate() {
        breedTimer++;
        starveCounter++;
    }


    public List<PredatorPreyCell> getNeighborsOfState(State state) {
        List<PredatorPreyCell> neighborsOfState = new LinkedList<>();
        PredatorPreyCell neighbor;
        for (Cell c : getNeighbors()) {
            neighbor = (PredatorPreyCell) c;
            if (neighbor.getMark() == Mark.NONE
                    && neighbor.getState() == state) {
                neighborsOfState.add(neighbor);
            }
        }
        return neighborsOfState;
    }

    public void changeState() {
        switch (getMark()) {
            case NONE:
                return;
            case TO_FISH:
                setFill(fishVisual);
                state = State.FISH;
                break;
            case TO_SHARK:
                setFill(sharkVisual);
                state = State.SHARK;
                break;
            case TO_EMPTY:
                setFill(emptyVisual);
                state = State.EMPTY;
                break;
            default:
        }
        setMark(Mark.NONE);
    }

    public boolean getBreeding() {
        return breeding;
    }

    public void setBreeding(boolean t) {
        breeding = t;
    }

    public State getState() {
        return state;
    }

    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }

    public int getBreedTimer() {
        return breedTimer;
    }

    public void setBreedTimer(int breedTimer) {
        this.breedTimer = breedTimer;
    }

    public int getStarveCounter() {
        return starveCounter;
    }

    public void setStarveCounter(int starveCounter) {
        this.starveCounter = starveCounter;
    }

    @Override
    public void setVisuals(Paint... visuals) {
        emptyVisual = visuals[0];
        fishVisual = visuals[1];
        sharkVisual = visuals[2];
    }

    public enum State {
        SHARK, FISH, EMPTY
    }

    public enum Mark {
        TO_FISH, TO_SHARK, TO_EMPTY, NONE
    }

}

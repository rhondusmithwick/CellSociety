package Cell;

import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.Collections;
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
    private boolean shouldBreed = false;

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


    private List<PredatorPreyCell> getNeighborsOfState(State stateWanted) {
        List<PredatorPreyCell> neighborsOfState = new ArrayList<>();
        PredatorPreyCell neighbor;
        for (Cell c : getNeighbors()) {
            neighbor = (PredatorPreyCell) c;
            if (neighbor.getMark() == Mark.NONE
                    && neighbor.getState() == stateWanted) {
                neighborsOfState.add(neighbor);
            }
        }
        return neighborsOfState;
    }


    public boolean sharkEat() {
        List<PredatorPreyCell> fishNeighbors = getNeighborsOfState(State.FISH);
        if (!fishNeighbors.isEmpty()) {
            Collections.shuffle(fishNeighbors);
            PredatorPreyCell fish = fishNeighbors.get(0);
            setStarveCounter(0);
            moveSpawn(fish);
            return true;
        }
        return false;
    }

    public void move() {
        List<PredatorPreyCell> emptyNeighbors = getNeighborsOfState(State.EMPTY);
        if (!emptyNeighbors.isEmpty()) {
            Collections.shuffle(emptyNeighbors);
            PredatorPreyCell emptyNeighbor = emptyNeighbors.get(0);
            moveSpawn(emptyNeighbor);
        }
    }

    private void moveSpawn(PredatorPreyCell cellToMoveTo) {
        swap(cellToMoveTo);
        if (shouldMakeEmpty()) {
            setMark(Mark.TO_EMPTY);
        }
        setShouldBreed(false);
    }

    private void swap(PredatorPreyCell cellToMoveTo) {
        switch (getState()) {
            case FISH:
                cellToMoveTo.setMark(Mark.TO_FISH);
                break;
            case SHARK:
                cellToMoveTo.setMark(Mark.TO_SHARK);
                break;
        }
        cellToMoveTo.setBreedTimer(breedTimer);
        cellToMoveTo.setStarveCounter(starveCounter);
        setBreedTimer(0);
        setStarveCounter(0);
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


    public void setShouldBreed(boolean t) {
        shouldBreed = t;
    }

    public State getState() {
        return state;
    }

    private Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }

    public void setBreedTimer(int breedTimer) {
        this.breedTimer = breedTimer;
    }

    private void setStarveCounter(int starveCounter) {
        this.starveCounter = starveCounter;
    }

    @Override
    public void setVisuals(Paint... visuals) {
        emptyVisual = visuals[0];
        fishVisual = visuals[1];
        sharkVisual = visuals[2];
    }

    private boolean shouldMakeEmpty() {
        return (!shouldBreed)
                || (mark == Mark.TO_EMPTY);
    }

    public void breedIfShould(int fishBreedTime, int sharkBreedTime) {
        if (shouldBreed(fishBreedTime, sharkBreedTime)) {
            setShouldBreed(true);
            setBreedTimer(0);
        }
    }

    private boolean shouldBreed(int fishBreedTime, int sharkBreedTime) {
        return fishShouldBreed(fishBreedTime) ||
                sharkShouldBreed(sharkBreedTime);
    }

    private boolean fishShouldBreed(int fishBreedTime) {
        return (state == State.FISH)
                && (breedTimer >= fishBreedTime);
    }

    private boolean sharkShouldBreed(int sharkBreedTime) {
        return (state == State.SHARK)
                && (breedTimer >= sharkBreedTime);
    }

    public boolean shouldStarve(int starveTime) {
        return starveCounter >= starveTime;
    }

    public boolean canMoveOrSpawn() {
        return (state != State.EMPTY)
                && ((mark != Mark.TO_EMPTY) || (shouldBreed));
    }

    public enum State {
        SHARK, FISH, EMPTY
    }


    public enum Mark {
        TO_FISH, TO_SHARK, TO_EMPTY, NONE
    }


}

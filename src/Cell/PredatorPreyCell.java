package Cell;

import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rhondusmithwick on 2/1/16.
 *
 * @author Rhondu Smithwick
 */
public class PredatorPreyCell extends Cell {
    private int starveCounter = 0;
    /**
     * This cell's state.
     */
    private State state;
    /**
     * This cell's mark.
     */
    private Mark mark;
    private int breedTimer = 0;
    private boolean shouldBreed = false;

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
            setMark(Mark.EMPTY);
        }
        setShouldBreed(false);
    }

    private void swap(PredatorPreyCell cellToMoveTo) {
        Mark cellToMoveToMark = Mark.valueOf(state.toString());
        cellToMoveTo.setMark(cellToMoveToMark);
        cellToMoveTo.setBreedTimer(breedTimer);
        cellToMoveTo.setStarveCounter(starveCounter);
        setBreedTimer(0);
        setStarveCounter(0);
    }

    public void changeState() {
        if (mark == Mark.NONE) {
            return;
        }
        state = State.valueOf(mark.toString());
        setFill(getVisual(state));
        setMark(Mark.NONE);
    }


    private void setShouldBreed(boolean t) {
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

    private void setBreedTimer(int breedTimer) {
        this.breedTimer = breedTimer;
    }

    private void setStarveCounter(int starveCounter) {
        this.starveCounter = starveCounter;
    }

    @Override
    public void setVisuals(Paint... visuals) {
        addToVisualMap(State.EMPTY, visuals[0]);
        addToVisualMap(State.FISH, visuals[1]);
        addToVisualMap(State.SHARK, visuals[2]);
    }

    private boolean shouldMakeEmpty() {
        return (!shouldBreed)
                || (mark == Mark.EMPTY);
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
                && ((mark != Mark.EMPTY) || (shouldBreed));
    }

    public enum State {
        SHARK, FISH, EMPTY
    }


    public enum Mark {
        FISH, SHARK, EMPTY, NONE
    }


}

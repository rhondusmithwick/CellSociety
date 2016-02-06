package Cell;

import javafx.scene.paint.Paint;

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
    private Mark mark;

    private int breedCounter;
    private int starveCounter;
    private boolean breeding = false;

    private Paint emptyVisual;
    private Paint fishVisual;
    private Paint sharkVisual;

    public PredatorPreyCell() {
        super();
    }

    public void handleUpdate() {
        breedCounter++;
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
    }

    public void setBreeding(boolean t) {
        breeding = t;
    }

    public State getState() {
        return state;
    }

    private void setState(State state) {
        this.state = state;
    }

    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
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

    @Override
    public void setVisuals(Paint... visuals) {
        emptyVisual = visuals[0];
        fishVisual = visuals[1];
        sharkVisual = visuals[2];
        //        setStroke(Color.BLACK);

    }

    public enum State {
        SHARK, FISH, EMPTY
    }

    public enum Mark {
        TO_FISH, TO_SHARK, TO_EMPTY, NONE
    }

}
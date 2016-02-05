package Cell;

import javafx.scene.paint.Color;
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

    private int turnsSurvived;
    private boolean isEdible;

    private Paint emptyVisual;
    private Paint fishVisual;
    private Paint sharkVisual;

    public PredatorPreyCell() {
        super();
        setStroke(Color.BLACK);
    }

    public void handleUpdate() {
        turnsSurvived++;
    }


    public List<PredatorPreyCell> getNeighborsOfState(State state) {
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

    public void changeState() {
        switch (getMark()) {
            case NONE:
                return;
            case TO_FISH:
                setFill(fishVisual);
                setState(State.FISH);
                setEdible(true);
                break;
            case TO_SHARK:
                setFill(sharkVisual);
                setState(State.SHARK);
                setEdible(false);
                break;
            case TO_EMPTY:
                setFill(emptyVisual);
                setState(State.EMPTY);
                setEdible(false);
                break;
            default:
        }
        setMark(Mark.NONE);
        setTurnsSurvived(0);
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
    }

    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }

    public int getTurnsSurvived() {
        return turnsSurvived;
    }

    public void setTurnsSurvived(int turnsSurvived) {
        this.turnsSurvived = turnsSurvived;
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

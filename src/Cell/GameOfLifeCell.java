package Cell;

import javafx.scene.paint.Paint;

/**
 * Created by rhondusmithwick on 1/31/16.
 *
 * @author Rhondu Smithwick
 */
public class GameOfLifeCell extends Cell {

    private State state;
    private Mark mark;

    private Paint deadVisual;
    private Paint aliveVisual;

    public GameOfLifeCell() {
        super();
    }

    @Override
    public void handleUpdate() {
        int aliveNeighbors = countAliveNeighbors();
        if (shouldDie(aliveNeighbors)) {
            setMark(Mark.DESTROY);
        } else if (shouldResurect(aliveNeighbors)) {
            setMark(Mark.RESURECT);
        }
    }

    private int countAliveNeighbors() {
        int count = 0;
        GameOfLifeCell neighbor;
        for (Cell c : neighbors) {
            neighbor = (GameOfLifeCell) c;
            if (neighbor.getState() == State.ALIVE) {
                count++;
            }
        }
        return count;
    }

    @Override
    public void changeState() {
        switch (getMark()) {
            case NONE:
                return;
            case DESTROY:
                setFill(deadVisual);
                setState(State.DEAD);
                break;
            case RESURECT:
                setFill(aliveVisual);
                setState(State.ALIVE);
                break;
            default:
        }
        setMark(Mark.NONE);
    }


    private boolean shouldDie(int aliveNeighbors) {
        return (getState() == State.ALIVE)
                && ((aliveNeighbors < 2) || (aliveNeighbors > 3));
    }

    private boolean shouldResurect(int aliveNeighbors) {
        return (getState() == State.DEAD)
                && (aliveNeighbors == 3);
    }

    @Override
    public void setVisuals(Paint... visuals) {
        deadVisual = visuals[0];
        aliveVisual = visuals[1];
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

    public enum State {
        ALIVE, DEAD
    }

    public enum Mark {
        DESTROY, RESURECT, NONE
    }
}

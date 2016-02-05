package Cell;

import javafx.scene.paint.Paint;

/**
 * Created by rhondusmithwick on 1/31/16.
 *
 * @author Rhondu Smithwick
 */
public class GameOfLifeCell extends Cell {

    private Paint deadVisual;
    private Paint aliveVisual;

    private State state;
    private Mark mark;

    public GameOfLifeCell() {
        super();
    }

    @Override
    public void handleUpdate() {
        int aliveNeighbors = countAliveNeighbors();
        if (aliveNeighbors < 2 || aliveNeighbors > 3) {
            setMark(Mark.DESTROY);
        } else if ((aliveNeighbors == 3) && (getState() == State.DEAD)) {
            setMark(Mark.RESTORE);
        }
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
            case RESTORE:
                setFill(aliveVisual);
                setState(State.ALIVE);
                break;
            default:
        }
        setMark(Mark.NONE);
    }

    private int countAliveNeighbors() {
        int count = 0;
        for (Cell c : neighbors) {
            GameOfLifeCell gc = (GameOfLifeCell) c;
            if (gc.getState() == State.ALIVE) {
                count++;
            }
        }
        return count;
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


    public void setVisuals(Paint... visuals) {
        this.deadVisual = visuals[0];
        this.aliveVisual = visuals[1];
    }

    public enum State {
        ALIVE, DEAD
    }

    public enum Mark {
        RESTORE, DESTROY, NONE
    }
}

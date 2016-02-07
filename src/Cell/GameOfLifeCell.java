package Cell;

import javafx.scene.paint.Paint;

/**
 * Created by rhondusmithwick on 1/31/16.
 *
 * @author Rhondu Smithwick
 */
public class GameOfLifeCell extends Cell {
    /**
     * This game of life cell's state.
     */
    private State state;
    /**
     * This game of life cell's mark that determines how it will be udpated.
     */
    private Mark mark;

    /**
     * The dead visual.
     */
    private Paint deadVisual;
    /**
     * The alive visual.
     */
    private Paint aliveVisual;

    /**
     * Construct the game of life cell.
     */
    public GameOfLifeCell() {
        super();
    }

    /**
     * Update this game of life cell.
     */
    @Override
    public void handleUpdate() {
        int aliveNeighbors = countAliveNeighbors();
        if (shouldDie(aliveNeighbors)) {
            setMark(Mark.DESTROY);
        } else if (shouldResurect(aliveNeighbors)) {
            setMark(Mark.RESURECT);
        }
    }

    /**
     * Count the number of neighbors this game of life cell has.
     *
     * @return the number of neighbors this cell has.
     */
    private int countAliveNeighbors() {
        int count = 0;
        GameOfLifeCell neighbor;
        for (Cell c : getNeighbors()) {
            neighbor = (GameOfLifeCell) c;
            if (neighbor.getState() == State.ALIVE) {
                count++;
            }
        }
        return count;
    }

    /**
     * Change this cell's state based on its mark.
     */
    @Override
    public void changeState() {
        switch (mark) {
            case NONE:
                return;
            case DESTROY:
                setFill(deadVisual);
                state = State.DEAD;
                break;
            case RESURECT:
                setFill(aliveVisual);
                state = State.ALIVE;
                break;
            default:
        }
        setMark(Mark.NONE);
    }

    /**
     * Determine if this cell should die.
     *
     * @param aliveNeighbors the number of alive neighbors this cell has.
     * @return true if this cell should die
     */
    private boolean shouldDie(int aliveNeighbors) {
        return ((getState() == State.ALIVE)
                && ((aliveNeighbors < 2) || (aliveNeighbors > 3)));
    }

    /**
     * Determine if this cell should come back to life.
     *
     * @param aliveNeighbors the number of alive neighbors this cell has.
     * @return true if this cell should come back to life
     */
    private boolean shouldResurect(int aliveNeighbors) {
        return (getState() == State.DEAD)
                && (aliveNeighbors == 3);
    }


    /**
     * Set this fire cell's visuals.
     *
     * @param visuals this cell's visuals
     */
    public void setVisuals(Paint... visuals) {
        deadVisual = visuals[0];
        aliveVisual = visuals[1];
    }

    /**
     * Get this cell's state.
     *
     * @return this cell's state
     */
    private State getState() {
        return state;
    }

    /**
     * Set this cell's mark.
     *
     * @param mark this cell's new mark
     */
    public void setMark(Mark mark) {
        this.mark = mark;
    }

    /**
     * Game of Life cell's State enum.
     */
    public enum State {
        ALIVE, DEAD
    }

    /**
     * Game of Life's mark enum.
     */
    public enum Mark {
        DESTROY, RESURECT, NONE
    }
}

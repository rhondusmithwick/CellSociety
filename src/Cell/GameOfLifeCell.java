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
     * Construct the game of life cell.
     */
    public GameOfLifeCell() {
        super();
    }

    @Override
    void saveTypeCellState() {
    /*	cellState.put("",);
		cellState.put("",);
		cellState.put("",);
		*/
    }

    /**
     * Update this game of life cell.
     */
    @Override
    public void handleUpdate() {
        int aliveNeighbors = countAliveNeighbors();
        if (shouldDie(aliveNeighbors)) {
            setMark(Mark.DEAD);
        } else if (shouldResurect(aliveNeighbors)) {
            setMark(Mark.ALIVE);
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
        if (mark == Mark.NONE) {
            return;
        }
        state = State.valueOf(mark.toString());
        setFill(getVisual(state));
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
        addToVisualMap(State.DEAD, visuals[0]);
        addToVisualMap(State.ALIVE, visuals[1]);
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
        ALIVE, DEAD, NONE
    }
}

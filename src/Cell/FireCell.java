package Cell;

import javafx.scene.paint.Paint;

/**
 * Created by rhondusmithwick on 2/4/16.
 *
 * @author Rhondu Smithwick
 */
public class FireCell extends Cell {

    /**
     * This fire cell's state.
     */
    private State state;
    /**
     * This fire cell's mark that determines how it will be udpated.
     */
    private Mark mark;

    /**
     * This fire cell's burnTimer.
     */
    private int burnTimer;

    /**
     * Construct a fire cell.
     */
    public FireCell() {
        super();
    }

    /**
     * Update this fire cell.
     */
    @Override
    public void handleUpdate() {
        burnTimer++;
    }


    /**
     * Determine if this fire cell has burning neighbors
     *
     * @return true if this fire cell has a burning neighbor
     */
    public boolean hasBurningNeighbor() {
        FireCell fc;
        for (Cell c : getNeighbors()) {
            fc = (FireCell) c;
            if (fc.getState() == State.BURNING) {
                return true;
            }
        }
        return false;
    }

    /**
     * Chase this fire cell's state based on it's mark.
     */
    @Override
    public void changeState() {
        if (mark == Mark.NONE) {
            return;
        }
        state = State.valueOf(mark.toString());
        setFill(getVisual(state));
        setBurnTimer(0);
        setMark(Mark.NONE);
    }

    /**
     * Set this fire cell's visuals.
     *
     * @param visuals this cell's visuals
     */
    @Override
    public void setVisuals(Paint... visuals) {
        addToVisualMap(State.EMPTY, visuals[0]);
        addToVisualMap(State.BURNING, visuals[1]);
        addToVisualMap(State.TREE, visuals[2]);
    }


    /**
     * Get this fire cell's state.
     *
     * @return this fire cell's state
     */
    public State getState() {
        return state;
    }

    /**
     * Set this fire cell's mark.
     *
     * @param mark this fire cell's mark.
     */
    public void setMark(Mark mark) {
        this.mark = mark;
    }

    /**
     * Get this cell's burn timer.
     *
     * @return this cell's burn timer.
     */
    public int getBurnTimer() {
        return burnTimer;
    }

    /**
     * Set this cell's burn timer.
     *
     * @param burnTimer this cell's new burn timer.
     */
    public void setBurnTimer(int burnTimer) {
        this.burnTimer = burnTimer;
    }

    /**
     * The fire cell State enum.
     */
    public enum State {
        BURNING, TREE, EMPTY
    }

    /**
     * The fire cell Mark enum.
     */
    public enum Mark {
        BURNING, TREE, EMPTY, NONE
    }
}

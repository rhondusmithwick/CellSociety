package Cell;


import Grid.CellShape;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.Map;

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
     * This fire cell's mark that determines how it will be updated.
     */
    private Mark mark;

    /**
     * This fire cell's burnTimer.
     */
    private int burnTimer;

    public FireCell(CellShape shape, int row, int column) {
        super(shape, row, column);
    }

    public void loadCellState(Map<String, String> cellState) {
        state = State.valueOf(cellState.get("state"));
        mark = Mark.valueOf(cellState.get("state"));
        burnTimer = Integer.parseInt(cellState.get("burnTimer"));
        setFill(getVisual(state));
    }

    @Override
    void saveTypeCellState() {
        if (state == null || mark == null) {
            state = State.TREE;
            mark = Mark.TREE;
        }
        cellState.put("state", state.name());
        cellState.put("mark", mark.name());
        cellState.put("burnTimer", burnTimer);

    }

    /**
     * Update this fire cell.
     */
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
        setStroke(Color.BLACK);
    }


    /**
     * Get this fire cell's state.
     *
     * @return this fire cell's state
     */
    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
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
        BURNING, TREE, EMPTY;

        public static State type(String token) {
            return State.valueOf(token);
        }

        public static String token(State t) {
            return t.name();
        }
    }

    /**
     * The fire cell Mark enum.
     */
    public enum Mark {
        BURNING, TREE, EMPTY, NONE;

        public static Mark type(String token) {
            return Mark.valueOf(token);
        }

        public static String token(Mark t) {
            return t.name();
        }
    }


}

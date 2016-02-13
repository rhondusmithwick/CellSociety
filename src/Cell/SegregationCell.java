package Cell;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Created by rhondusmithwick on 2/1/16.
 *
 * @author Rhondu Smithwick
 */
public class SegregationCell extends Cell {
    private State state;
    private Mark mark;

    private int threshold;

    private Paint emptyVisual;
    private Paint group1Visual;
    private Paint group2Visual;

    public SegregationCell() {
        super();
    }

    @Override
    public void handleUpdate() {
        if (getState() != State.EMPTY) {
            double likeMePercent = getLikeMePercent();
            if (likeMePercent < threshold) {
                setMark(Mark.EMPTY);
            }
        }
    }

    private double getLikeMePercent() {
        int count = 0;
        int num = 0;
        SegregationCell sc;
        for (Cell c : getNeighbors()) {
            sc = (SegregationCell) c;
            if (sc.getState() != State.EMPTY) {
                if (sc.getState() == getState()) {
                    count++;
                }
                num++;
            }
        }
        return ((double) count / num) * 100;
    }

    @Override
    public void changeState() {
        if (mark == Mark.NONE) {
            return;
        }
        state = State.valueOf(mark.toString());
        setFill(getVisual(state));
        setMark(Mark.NONE);
        setMark(Mark.NONE);
    }

    @Override
    public void setVisuals(Paint... visuals) {
        addToVisualMap(State.EMPTY, visuals[0]);
        addToVisualMap(State.GROUP1, visuals[1]);
        addToVisualMap(State.GROUP2, visuals[2]);
        setStroke(Color.BLACK);
    }

    public void setThreshold(int t) {
        threshold = t;
    }

    public State getState() {
        return state;
    }

    private void setState(SegregationCell.State state) {
        this.state = state;
    }

    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }

    public enum State {
        EMPTY, GROUP1, GROUP2
    }

    public enum Mark {
        EMPTY, GROUP1, GROUP2, NONE
    }
}

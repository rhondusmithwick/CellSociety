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
	void saveTypeCellState() {
		/*
		cellState.put("",);
		cellState.put("",);
		cellState.put("",);
		cellState.put("",);
		cellState.put("",);
		cellState.put("",);
		*/
	}

    @Override
    public void handleUpdate() {
        if (getState() != State.EMPTY) {
            double likeMePercent = getLikeMePercent();
            if (likeMePercent < threshold) {
                setMark(Mark.TO_EMPTY);
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
        switch (getMark()) {
            case NONE:
                return;
            case TO_EMPTY:
                setFill(emptyVisual);
                setState(State.EMPTY);
                break;
            case TO_GROUP1:
                setFill(group1Visual);
                setState(State.GROUP1);
                break;
            case TO_GROUP2:
                setFill(group2Visual);
                setState(State.GROUP2);
                break;
        }
        setMark(Mark.NONE);
    }

    @Override
    public void setVisuals(Paint... visuals) {
        emptyVisual = visuals[0];
        group1Visual = visuals[1];
        group2Visual = visuals[2];
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
        TO_EMPTY, TO_GROUP1, TO_GROUP2, NONE
    }
}

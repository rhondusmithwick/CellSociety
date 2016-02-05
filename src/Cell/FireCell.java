package Cell;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Created by rhondusmithwick on 2/4/16.
 *
 * @author Rhondu Smithwick
 */
// TO FINISH
public class FireCell extends Cell {

    private State state;
    private Mark mark;

    private int burnTime;

    private Paint emptyVisual;
    private Paint burningVisual;
    private Paint treeVisual;

    public FireCell() {
        super();
        setStroke(Color.BLACK);
    }

    @Override
    public void handleUpdate() {
        burnTime++;
    }

    @Override
    public void setVisuals(Paint... visuals) {
        emptyVisual = visuals[0];
        burningVisual = visuals[1];
        treeVisual = visuals[2];
    }

    public boolean hasBurningNeighbor() {
        FireCell fc;
        for (Cell c : neighbors) {
            fc = (FireCell) c;
            if (fc.getState() == State.BURNING) {
                return true;
            }
        }
        return false;
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
            case TO_BURNING:
                setFill(burningVisual);
                setState(State.BURNING);
                break;
            case TO_TREE:
                setFill(treeVisual);
                setState(State.TREE);
                break;
        }
        setBurnTime(0);
        setMark(Mark.NONE);
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

    public int getBurnTime() {
        return burnTime;
    }

    public void setBurnTime(int burnTime) {
        this.burnTime = burnTime;
    }

    public enum State {
        BURNING, TREE, EMPTY
    }

    public enum Mark {
        TO_BURNING, TO_TREE, TO_EMPTY, NONE
    }
}

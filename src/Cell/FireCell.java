package Cell;

import javafx.scene.paint.Color;

/**
 * Created by rhondusmithwick on 2/4/16.
 *
 * @author Rhondu Smithwick
 */
// TO FINISH
public class FireCell extends Cell {

    private State state;

    private int burnTime;

    public FireCell() {
        super();
        removeDiagonals();
        this.setStroke(Color.BLACK);
    }

    @Override
    public void handleUpdate() {
        burnTime++;
    }

    public boolean hasBurningNeighbor() {
        for (Cell c : neighbors) {
            FireCell fc = (FireCell) c;
            if (fc.getState() == State.BURNING) {
                return true;
            }
        }
        return false;
    }

    public enum State {
        BURNING, TREE, EMPTY
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }




    public int getBurnTime() {
        return burnTime;
    }

    public void setBurnTime(int burnTime) {
        this.burnTime = burnTime;
    }
}

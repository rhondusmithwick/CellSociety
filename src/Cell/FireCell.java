package Cell;

/**
 * Created by rhondusmithwick on 2/4/16.
 *
 * @author Rhondu Smithwick
 */
// TO FINISH
public class FireCell extends Cell {

    private State state;

    public void setBurnTime(int burnTime) {
        this.burnTime = burnTime;
    }

    private int burnTime;

    public FireCell() {
        super();
        removeDiagonals();
    }

    @Override
    public void handleUpdate() {
        burnTime++;
    }

    public enum State {
        BURNINING, TREE, EMPTY
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public boolean hasBurningNeighbor() {
        for (Cell c : neighbors) {
            FireCell fc = (FireCell) c;
            if (fc.getState() == State.BURNINING) {
                return true;
            }
        }
        return false;
    }
}

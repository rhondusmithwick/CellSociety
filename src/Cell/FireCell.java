package Cell;

import java.util.LinkedList;
import java.util.List;

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

    public List<FireCell> countNeighbors(State state) {
        List<FireCell> neighborsOfState = new LinkedList<>();
        FireCell neighbor;
        for (Cell c : neighbors) {
            neighbor = (FireCell) c;
            if (neighbor.getState() == state) {
                neighborsOfState.add(neighbor);
            }
        }
        return neighborsOfState;
    }
}

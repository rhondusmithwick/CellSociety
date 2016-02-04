package Cell;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by rhondusmithwick on 2/1/16.
 *
 * @author Rhondu Smithwick
 */
public class PredatorPreyCell extends Cell {

    private State state;
    private int turnsSurvived;
    private boolean isEdible = false;

    public PredatorPreyCell() {
        super();
    }

    public void handleUpdate() {
        turnsSurvived++;
    }

    public void removeDiagonals() {
        for (Cell c : neighbors) {
            int rowDiff = Math.abs(c.getRow() - getRow());
            int columnDiff = Math.abs(c.getColumn() - getColumn());
            if (rowDiff == 1 && columnDiff == 1) {
                neighbors.remove(c);
            }
        }
    }

    public List<PredatorPreyCell> countNeighbors(State state) {
        List<PredatorPreyCell> neighborsOfState = new LinkedList<>();
        PredatorPreyCell neighbor;
        for (Cell c : neighbors) {
            neighbor = (PredatorPreyCell) c;
            if (neighbor.getState() == state) {
                neighborsOfState.add(neighbor);
            }
        }
        return neighborsOfState;
    }

    public boolean getEdible() {
        return isEdible;
    }

    public void setEdible(boolean t) {
        isEdible = t;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
        turnsSurvived = 0;
    }

    public int getTurnsSurvived() {
        return turnsSurvived;
    }

    public void setTurnsSurvived(int turnsSurvived) {
        this.turnsSurvived = turnsSurvived;
    }

    public enum State {
        SHARK, FISH, EMPTY
    }
}

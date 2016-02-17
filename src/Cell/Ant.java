// This entire file is part of my masterpiece.
// Rhondu Smithwick
package Cell;

import Cell.ForagingAntsCell.Mark;
import Cell.ForagingAntsCell.State;

import java.util.ArrayList;
import java.util.List;

/*
    I chose this class as my masterpiece because it was the most challenging class to write. It shows
    my ability to use lambda expressions to sort and choose maximums. It also demonstrates my ability
    to give abstract methods away as mentioned in my analysis. See the Ant algorithms class for the
    mathy functions this class depends on. The reason why I chose to segregate these two (one into what is
    an essentially static class) is that I want all ants to share those methods: no specific ant
    has a unique method for that. Plus, it helps keep this class focused and clean.
    This was also a very different simulation: for all the other simulations, we didn't need an agent class.
    Here, the agent was so complex that we did, and I think we would have needed one for SugarScape if we
    had done it.
    Run Foraging Ants in the drop down to test this.
 */

/**
 * Created by rhondusmithwick on 2/8/16.
 *
 * @author Rhondu Smithwick
 */
class Ant {
    private final double myLifeTime;
    private int deathTicker = 0;
    private ForagingAntsCell myCell;
    private ForagingAntsCell prevCell = null;
    private boolean hasFoodItem = false;
    private boolean isDead = false;
    private boolean needsToMove = false;

    Ant(ForagingAntsCell myCell, double myLifeTime) {
        this.myCell = myCell;
        this.myLifeTime = myLifeTime;
    }

    public void handleUpdate() {
        deathTicker++;
        if (needsToDie()) {
            die();
        } else {
            antForage();
        }
    }

    private void antForage() {
        if (!hasFoodItem) {
            antForage(true);
        } else {
            antForage(false);
        }
    }

    private void antForage(boolean searchForFood) {
        ForagingAntsCell cellToMoveTo = getCellToMoveTo(true, searchForFood);
        if (cellToMoveTo == null) {
            cellToMoveTo = getCellToMoveTo(false, searchForFood);
        }
        if (cellToMoveTo != null) {
            myCell.dropPheromones(searchForFood);
            move(cellToMoveTo);
            foodUpdateIfShould(searchForFood);
        }
    }

    private ForagingAntsCell getCellToMoveTo(boolean forwardOnly, boolean searchForFood) {
        List<ForagingAntsCell> locSet = createLocSet(forwardOnly);
        if (searchForFood) {
            return AntAlgorithms.getNeighborTowardsFood(locSet);
        } else {
            return AntAlgorithms.getNeighborTowardsHome(locSet);
        }
    }

    private List<ForagingAntsCell> createLocSet(boolean forwardOnly) {
        List<ForagingAntsCell> locSet = new ArrayList<>();
        myCell.getNeighbors().stream()
                .map(c -> (ForagingAntsCell) c)
                .forEach(fac -> addToLocSetIfShould(fac, forwardOnly, locSet));
        return locSet;
    }

    private void addToLocSetIfShould(ForagingAntsCell fac, boolean forwardOnly,
                                     List<ForagingAntsCell> locSet) {
        if (moveCheck(fac, forwardOnly)) {
            locSet.add(fac);
        }
    }

    private void foodUpdateIfShould(boolean searchForFood) {
        if (searchForFood) {
            pickUpFoodIfShould();
        } else {
            dropFoodIfShould();
        }
    }

    private void move(ForagingAntsCell cellToMoveTo) {
        setNeedsToMove(true);
        myCell.setMark(Mark.CHANGE_ANTS);
        cellToMoveTo.addAnt(this);
        cellToMoveTo.setMark(Mark.CHANGE_ANTS);
        prevCell = myCell;
        myCell = cellToMoveTo;
    }

    private void die() {
        isDead = true;
        myCell.setMark(Mark.CHANGE_ANTS);
    }

    private void pickUpFoodIfShould() {
        if (myCell.getState() == State.FOOD) {
            hasFoodItem = true;
        }
    }

    private void dropFoodIfShould() {
        if (myCell.getState() == State.NEST) {
            hasFoodItem = false;
        }
    }


    private boolean moveCheck(ForagingAntsCell neighbor, boolean forwardOnly) {
        return (neighbor != prevCell)
                && neighbor.isValid()
                && (forwardCheck(neighbor, forwardOnly));
    }

    private boolean forwardCheck(ForagingAntsCell neighbor, boolean forwardOnly) {
        return (forwardOnly && !myCell.checkDiagonal(neighbor))
                || !forwardOnly;
    }

    private boolean needsToDie() {
        return (deathTicker >= myLifeTime);
    }

    boolean getDeadOrMove() {
        return isDead || needsToMove;
    }

    void setNeedsToMove(boolean needsToMove) {
        this.needsToMove = needsToMove;
    }

}

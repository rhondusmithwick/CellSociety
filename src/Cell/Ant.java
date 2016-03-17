// This entire file is part of my masterpiece.
// Rhondu Smithwick
package Cell;

import Cell.ForagingAntsCell.Mark;
import Cell.ForagingAntsCell.State;

import java.util.ArrayList;
import java.util.List;


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
    private final AntAlgorithms antAlgorithms;

    Ant(ForagingAntsCell myCell, double myLifeTime) {
        antAlgorithms = new AntAlgorithms();
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
            return antAlgorithms.getNeighborTowardsFood(locSet);
        } else {
            return antAlgorithms.getNeighborTowardsHome(locSet);
        }
    }

    private List<ForagingAntsCell> createLocSet(boolean forwardOnly) {
        List<ForagingAntsCell> locSet = new ArrayList<>();
        myCell.getNeighbors().parallelStream()
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

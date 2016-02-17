package Cell;

import Cell.ForagingAntsCell.Mark;
import Cell.ForagingAntsCell.State;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


/**
 * Created by rhondusmithwick on 2/8/16.
 *
 * @author Rhondu Smithwick
 */
class Ant {
    private final double myLifeTime;
    private int deathTicker = 0;
    private boolean hasFoodItem = false;
    private ForagingAntsCell myCell;
    private ForagingAntsCell prevCell = null;
    private boolean isDead = false;
    private boolean moving = false;


    Ant(ForagingAntsCell myCell, double myLifeTime) {
        this.myCell = myCell;
        this.myLifeTime = myLifeTime;
        hasFoodItem = false;
    }


    public void handleUpdate() {
        deathTicker++;
        if (deathTicker >= myLifeTime) {
            die();
        } else {
            antForage();
        }
    }

    private void move(ForagingAntsCell cellToMoveTo) {
        setMoving(true);
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

    private void antForage() {
        if (hasFoodItem) {
            antReturnToNest();
        } else {
            antFindFooSource();
        }
    }

    private void antReturnToNest() {
        ForagingAntsCell mostForward = getNeighborMaxPhero(true);
        if (mostForward == null) {
            mostForward = getNeighborMaxPhero(false);
        }
        if (mostForward != null) {
            myCell.dropPheromones(false);
            move(mostForward);
            if (myCell.getState() == State.NEST) {
                hasFoodItem = false;
            }
        }
    }

    private void antFindFooSource() {
        ForagingAntsCell mostForward = selectLocations(true);
        if (mostForward == null) {
            mostForward = selectLocations(false);
        }
        if (mostForward != null) {
            myCell.dropPheromones(true);
            move(mostForward);
            if (myCell.getState() == State.FOOD) {
                hasFoodItem = true;
            }
        }
    }

    private ForagingAntsCell selectLocations(boolean forwardOnly) {
        List<ForagingAntsCell> locSet = createLocSet(forwardOnly);
        double totalProb = getTotalProb(locSet);
        if (!locSet.isEmpty()) {
            return chooseNeighbor(locSet, totalProb);
        }
        return null;
    }

    private ForagingAntsCell chooseNeighbor(List<ForagingAntsCell> locSet, double totalProb) {
        double prob = new Random().nextDouble() * totalProb;
        double probTracker = 0;
        double currProb;
        Collections.sort(locSet, (c1, c2) -> (compare(c1, c2)));
        for (ForagingAntsCell cell : locSet) {
            currProb = cell.getProbChoice();
            if (isRightProbability(prob, probTracker, currProb)) {
                return cell;
            }
            probTracker += currProb;
        }
        return locSet.get(locSet.size() / 2);
    }

    private ForagingAntsCell getNeighborMaxPhero(boolean forwardOnly) {
        ForagingAntsCell maxFac = null;
        double currMax = -1;
        ForagingAntsCell neighbor;
        for (Cell c : myCell.getNeighbors()) {
            neighbor = (ForagingAntsCell) c;
            if (moveCheck(neighbor, forwardOnly)) {
                double phero = neighbor.getHomePheromones();
                if (phero > currMax) {
                    currMax = phero;
                    maxFac = neighbor;
                }
            }
        }
        return maxFac;
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

    private double getTotalProb(List<ForagingAntsCell> locSet) {
        double total = 0.0;
        for (ForagingAntsCell fac : locSet) {
            total += fac.getProbChoice();
        }
        return total;
    }


    private int compare(ForagingAntsCell c1, ForagingAntsCell c2) {
        return (int) (c1.getProbChoice() - c2.getProbChoice());
    }


    private boolean moveCheck(ForagingAntsCell neighbor, boolean forwardOnly) {
        return (neighbor != prevCell)
                && neighbor.isValid()
                && ((forwardOnly && !myCell.checkDiagonal(neighbor)) || !forwardOnly);
    }

    private boolean isRightProbability(double prob, double probTracker, double currProb) {
        return (prob >= probTracker)
                && (prob <= probTracker + currProb);
    }

    boolean getDeadOrMove() {
        return isDead || moving;
    }

    void setMoving(boolean moving) {
        this.moving = moving;
    }

}

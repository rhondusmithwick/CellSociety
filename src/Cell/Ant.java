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


    Ant(ForagingAntsCell myCell, double mylifeTime) {
        this.myCell = myCell;
        this.myLifeTime = mylifeTime;
        hasFoodItem = false;
    }

    private static boolean isRightProbability(double prob, double probTracker, double currProb) {
        return (prob >= probTracker)
                && (prob <= probTracker + currProb);
    }

    private static int compare(ForagingAntsCell c1, ForagingAntsCell c2) {
        return (int) (c1.getProbChoice() - c2.getProbChoice());
    }

    private ForagingAntsCell simulateProbalisticChoice(List<ForagingAntsCell> locSet, double total) {
        double prob = new Random().nextDouble() * total;
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

    public void handleUpdate() {
        deathTicker++;
        if (deathTicker >= myLifeTime) {
            die();
        } else {
            antForage();
        }
    }

    private void move(ForagingAntsCell cellToMoveTo) {
        moving = true;
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

    private double populateLocSet(List<ForagingAntsCell> locSet, boolean forwardOnly) {
        ForagingAntsCell neighbor;
        double total = 0;
        for (Cell c : myCell.getNeighbors()) {
            neighbor = (ForagingAntsCell) c;
            if (moveCheck(neighbor, forwardOnly)) {
                double probChoice = neighbor.getProbChoice();
                total += probChoice;
                locSet.add(neighbor);
            }
        }
        return total;
    }

    private ForagingAntsCell selectLocations(boolean forwardOnly) {
        List<ForagingAntsCell> locSet = new ArrayList<>();
        double total = populateLocSet(locSet, forwardOnly);
        if (!locSet.isEmpty()) {
            return simulateProbalisticChoice(locSet, total);
        }
        return null;
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

    private boolean moveCheck(ForagingAntsCell neighbor, boolean forwardOnly) {
        return (neighbor != prevCell)
                && neighbor.isValid()
                && ((forwardOnly && !myCell.checkDiagonal(neighbor)) || !forwardOnly);
    }

    boolean getDeadOrMove() {
        return isDead || moving;
    }
}

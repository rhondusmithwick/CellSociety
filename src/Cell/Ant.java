//package Cell;
//
//import Cell.ForagingAntsCell.Mark;
//import Cell.ForagingAntsCell.State;
//
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Random;
//
//
///**
// * Created by rhondusmithwick on 2/8/16.
// *
// * @author Rhondu Smithwick
// */
//class Ant {
//    private final double myLifeTime;
//    private int deathTicker = 0;
//    private boolean hasFoodItem = false;
//    private ForagingAntsCell myCell;
//    private boolean isDead = false;
//    private boolean moving = false;
//
//
//    Ant(ForagingAntsCell myCell, double mylifeTime) {
//        this.myCell = myCell;
//        this.myLifeTime = mylifeTime;
//        hasFoodItem = false;
//    }
//
//    public void handleUpdate() {
//        deathTicker++;
//        if (deathTicker >= myLifeTime) {
//            die();
//        } else {
//            antForage();
//        }
//    }
//
//    private void move(ForagingAntsCell cellToMoveTo) {
//        moving = true;
//        myCell.setMark(Mark.CHANGE_ANTS);
//        cellToMoveTo.addAnt(this);
//        cellToMoveTo.setMark(Mark.CHANGE_ANTS);
//        myCell = cellToMoveTo;
//    }
//
//    private void die() {
//        isDead = true;
//        myCell.setMark(Mark.CHANGE_ANTS);
//    }
//
//    private void antForage() {
//        if (hasFoodItem) {
//            antReturnToNest();
//        } else {
//            antFindFooSource();
//        }
//    }
//
//    private void antReturnToNest() {
//        ForagingAntsCell mostForward = getNeighborMaxPhero(true);
//        if (mostForward == null) {
//            mostForward = getNeighborMaxPhero(false);
//        }
//        if (mostForward != null) {
//            myCell.dropFoodPheromones();
//            move(mostForward);
//            if (myCell.getState() == State.NEST) {
//                hasFoodItem = false;
//            }
//        }
//    }
//
//
//    private void antFindFooSource() {
//        ForagingAntsCell mostForward = selectLocations(true);
//        if (mostForward == null) {
//            mostForward = selectLocations(false);
//        }
//        if (mostForward != null) {
//            myCell.dropHomesPheromones();
//            move(mostForward);
//            if (myCell.getState() == State.FOOD) {
//                hasFoodItem = true;
//            }
//        }
//    }
//
//
//    private double populateMap(Map<ForagingAntsCell, Double> locSet, boolean forwardOnly) {
//        ForagingAntsCell neighbor;
//        double total = 0;
//        for (Cell c : myCell.getNeighbors()) {
//            if (moveCheck(c, forwardOnly)) {
//                neighbor = (ForagingAntsCell) c;
//                if (neighbor.isValid()) {
//                    double probChoice = neighbor.getProbChoice();
//                    total += probChoice;
//                    locSet.put(neighbor, probChoice);
//                }
//            }
//        }
//        return total;
//    }
//
//    private ForagingAntsCell selectLocations(boolean forwardOnly) {
//        Map<ForagingAntsCell, Double> locSet = new HashMap<>();
//        double total = populateMap(locSet, forwardOnly);
//        if (!locSet.isEmpty()) {
//            double prob = new Random().nextDouble() * total;
//            ForagingAntsCell[] cells = locSet.keySet().toArray(new ForagingAntsCell[locSet.size()]);
//            Arrays.sort(cells, (c1, c2) -> (int) (locSet.get(c1) - locSet.get(c2)));
//            double curr = 0;
//            for (ForagingAntsCell cell : cells) {
//                if (prob >= curr && prob <= curr + locSet.get(cell)) {
//                    return cell;
//                }
//                curr += locSet.get(cell);
//            }
//        }
//        return null;
//    }
//
//    private ForagingAntsCell getNeighborMaxPhero(boolean forwardOnly) {
//        ForagingAntsCell maxFac = null;
//        double currMax = -1;
//        ForagingAntsCell neighbor;
//        for (Cell c : myCell.getNeighbors()) {
//            neighbor = (ForagingAntsCell) c;
//            if (neighbor.isValid()) {
//                if (moveCheck(neighbor, forwardOnly)) {
//                    double phero = neighbor.getHomePheromones();
//                    if (phero > currMax) {
//                        currMax = phero;
//                        maxFac = neighbor;
//                    }
//                }
//            }
//        }
//        return maxFac;
//    }
//
//
//    private boolean moveCheck(Cell neighbor, boolean forwardOnly) {
//        return (forwardOnly && !myCell.checkDiagonal(neighbor))
//                || !forwardOnly;
//    }
//
//    boolean getDeadOrMove() {
//        return isDead || moving;
//    }
//}

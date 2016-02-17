// This entire file is part of my masterpiece.
// Rhondu Smithwick
package Cell;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/*
    See Ant.java for explanation.
 */

/**
 * Created by rhondusmithwick on 2/17/16.
 *
 * @author Rhondu Smithwick
 */
final class AntAlgorithms {
    private AntAlgorithms() {
    }

    static ForagingAntsCell getNeighborTowardsHome(List<ForagingAntsCell> locSet) {
        return locSet.stream()
                .max((loc1, loc2) -> Double.compare(loc1.getHomePheromones(), loc2.getHomePheromones()))
                .get();
    }

    static ForagingAntsCell getNeighborTowardsFood(List<ForagingAntsCell> locSet) {
        if (!locSet.isEmpty()) {
            return chooseNeighbor(locSet);
        }
        return null;
    }

    private static ForagingAntsCell chooseNeighbor(List<ForagingAntsCell> locSet) {
        double totalProb = getTotalProbability(locSet);
        double prob = new Random().nextDouble() * totalProb;
        double probTracker = 0;
        double currProb;
        Collections.sort(locSet, (loc1, loc2) -> Double.compare(loc1.getProbChoice(), loc2.getProbChoice()));
        for (ForagingAntsCell cell : locSet) {
            currProb = cell.getProbChoice();
            boolean isRightProbability = isRightProbability(prob, probTracker, currProb);
            if (isRightProbability) {
                return cell;
            }
            probTracker += currProb;
        }
        return locSet.get(locSet.size() / 2);
    }


    private static boolean isRightProbability(double prob, double probTracker, double currProb) {
        return (prob >= probTracker)
                && (prob <= probTracker + currProb);
    }

    private static double getTotalProbability(List<ForagingAntsCell> locSet) {
        return locSet.stream()
                .mapToDouble(c -> c.getProbChoice())
                .sum();
    }
}

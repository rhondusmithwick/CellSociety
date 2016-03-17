// This entire file is part of my masterpiece.
// Rhondu Smithwick
package Cell;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by rhondusmithwick on 2/17/16.
 *
 * @author Rhondu Smithwick
 */
class AntAlgorithms {

    ForagingAntsCell getNeighborTowardsHome(List<ForagingAntsCell> locSet) {
        return locSet.parallelStream()
                .max((loc1, loc2) -> Double.compare(loc1.getHomePheromones(), loc2.getHomePheromones()))
                .get();
    }

    ForagingAntsCell getNeighborTowardsFood(List<ForagingAntsCell> locSet) {
        if (!locSet.isEmpty()) {
            return chooseNeighbor(locSet);
        }
        return null;
    }

    private ForagingAntsCell chooseNeighbor(List<ForagingAntsCell> locSet) {
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


    private boolean isRightProbability(double prob, double probTracker, double currProb) {
        return (prob >= probTracker)
                && (prob <= probTracker + currProb);
    }

    private double getTotalProbability(List<ForagingAntsCell> locSet) {
        return locSet.parallelStream()
                .mapToDouble(ForagingAntsCell::getProbChoice)
                .sum();
    }
}

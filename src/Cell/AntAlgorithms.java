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
        return Collections.max(locSet, (loc1, loc2) -> homePheromonesCompare(loc1, loc2));
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
        Collections.sort(locSet, (loc1, loc2) -> (probChoiceCompare(loc1, loc2)));
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
        double total = 0.0;
        for (ForagingAntsCell fac : locSet) {
            total += fac.getProbChoice();
        }
        return total;
    }

    private static int homePheromonesCompare(ForagingAntsCell loc1, ForagingAntsCell loc2) {
        double diffHomePheromones = loc1.getHomePheromones() - loc2.getHomePheromones();
        return (int) diffHomePheromones;
    }

    private static int probChoiceCompare(ForagingAntsCell location1, ForagingAntsCell location2) {
        double diffInProbChoice = location1.getProbChoice() - location2.getProbChoice();
        return (int) diffInProbChoice;
    }
}

package Simulation;

import Cell.Cell;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

import java.util.Collection;
import java.util.Random;

/**
 * Created by rhondusmithwick on 1/31/16.
 *
 * @author Rhondu Smithwick
 */
public abstract class Simulation {
    /**
     * The game's frames per second.
     */
    private static final int FRAMES_PER_SECOND = 60;
    /**
     * The game's millisecond delay that will be used in its timers
     */
    private static final double MILLISECOND_DELAY = 1000 / (double) FRAMES_PER_SECOND;
    /**
     * The game's second delay that will be used in its timers
     */
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

    final Collection<Cell> theCells;
    private final Timeline simulationLoop;
    private boolean isPlaying = false;

    Simulation(Collection<Cell> theCells) {
        this.theCells = theCells;
        simulationLoop = buildLoop();
    }


    private Timeline buildLoop() {
        EventHandler<ActionEvent> handler = (t -> step());
        final KeyFrame keyFrame = new KeyFrame(Duration.seconds(.5), handler);
        Timeline simulationLoop = new Timeline();
        simulationLoop.setCycleCount(Animation.INDEFINITE);
        simulationLoop.getKeyFrames().addAll(keyFrame);
        return simulationLoop;
    }

    protected void step() {
        theCells.forEach(c -> c.handleUpdate());
    }

    private void beginLoop() {
        simulationLoop.play();
        isPlaying = true;
    }

    private void stopLoop() {
        simulationLoop.stop();
        isPlaying = false;
    }

    private boolean getPlaying() {
        return isPlaying;
    }

    public void playOrStop() {
        if (!getPlaying()) {
            beginLoop();
        } else {
            stopLoop();
        }
    }

    public void init() {
        Random rn = new Random();
        int minimum = 1;
        int maximum = 100;
        int range = maximum - minimum + 1;
        for (Cell c : theCells) {
            int randomNum = rn.nextInt(range) + minimum;
            assignInitialState(randomNum, c);
        }
    }

    protected abstract void assignInitialState(int randomNum, Cell c);
}

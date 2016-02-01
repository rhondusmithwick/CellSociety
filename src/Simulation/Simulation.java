package Simulation;

import Cell.Cell;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

import java.util.Collection;

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

    Simulation(Collection<Cell> theCells) {
        this.theCells = theCells;
        simulationLoop = buildLoop();
    }


    private Timeline buildLoop() {
        EventHandler<ActionEvent> handler = (t -> step());
        final KeyFrame keyFrame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), handler);
        Timeline simulationLoop = new Timeline();
        simulationLoop.setCycleCount(Animation.INDEFINITE);
        simulationLoop.getKeyFrames().addAll(keyFrame);
        return simulationLoop;
    }

    protected void step() {
        theCells.forEach(c -> c.handleUpdate());
    }

    public abstract void init();

    public void beginLoop() {
        simulationLoop.play();
    }
}

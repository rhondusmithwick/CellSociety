package Simulation;

import Cell.Cell;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import org.w3c.dom.Element;

import java.util.Collection;
import java.util.Random;

/**
 * Created by rhondusmithwick on 1/31/16.
 *
 * @author Rhondu Smithwick, Tavo Loaiza
 */
public abstract class Simulation {
    private final Random rn;
    private final Timeline simulationLoop;

    private int gridWidth;
    private int gridHeight;
    private int cellsPerRow;
    private int cellsPerColumn;
    private String type;
    private Collection<Cell> theCells;
    private boolean isPlaying = false;

    Simulation() {
        simulationLoop = buildLoop();
        rn = new Random();
    }

    Simulation(Element simElem) {
        this();
        setProperties(simElem);
    }

    final void setProperties(Element simElem) {
        setGenericProperties(simElem);
        setSpecificProperties(simElem);
    }

    private Timeline buildLoop() {
        final KeyFrame keyFrame = new KeyFrame(Duration.seconds(.5), t -> step());
        final Timeline simulationLoop = new Timeline();
        simulationLoop.setCycleCount(Animation.INDEFINITE);
        simulationLoop.getKeyFrames().addAll(keyFrame);
        return simulationLoop;
    }

    public final void init() {
        int randomNum;
        for (Cell c : getTheCells()) {
            randomNum = getRandomNum(1, 100);
            assignInitialState(randomNum, c);
        }
        changeStates();
    }

    abstract void assignInitialState(int randomNum, Cell c);

    public void step() {
        getTheCells().forEach(c -> c.handleUpdate());
    }

    final void changeStates() {
        for (Cell c : getTheCells()) {
            c.changeState();
        }
    }

    private void beginLoop() {
        simulationLoop.play();
        isPlaying = true;
    }

    public final void stopLoop() {
        simulationLoop.stop();
        isPlaying = false;
    }

    public boolean getPlaying() {
        return isPlaying;
    }

    public final void playOrStop() {
        if (!getPlaying()) {
            beginLoop();
        } else {
            stopLoop();
        }
    }


    final int getRandomNum(int min, int max) {
        int range = max - min + 1;
        return rn.nextInt(range) + min;
    }

    private void setGenericProperties(Element simElem) {
        String simType = XMLParser.getSimType(simElem);
        setType(simType);
        gridWidth = XMLParser.getIntValue(simElem, "gridWidth");
        gridHeight = XMLParser.getIntValue(simElem, "gridHeight");
        cellsPerRow = XMLParser.getIntValue(simElem, "numCellsPerRow");
        cellsPerColumn = XMLParser.getIntValue(simElem, "numCellsPerColumn");
    }

    abstract void setSpecificProperties(Element simElem);


    public final int getGridWidth() {
        return gridWidth;
    }


    public final int getGridHeight() {
        return gridHeight;
    }


    public final int getCellsPerRow() {
        return cellsPerRow;
    }


    public final int getCellsPerColumn() {
        return cellsPerColumn;
    }


    final String getType() {
        return type;
    }


    private void setType(String type) {
        this.type = type;
    }

    final Collection<Cell> getTheCells() {
        return theCells;
    }

    public final void setTheCells(Collection<Cell> theCells) {
        this.theCells = theCells;
    }

    public final void increaseRate() {
        double currentRate = simulationLoop.getRate();
        if (currentRate <= 10) {
            simulationLoop.setRate(currentRate + .1);
        }
    }

    public final void decreaseRate() {
        double currentRate = simulationLoop.getRate();
        if (currentRate > 0) {
            simulationLoop.setRate(currentRate - .1);

        }
    }

    public final void resetRate() {
        simulationLoop.setRate(1.0);
    }

	public void resetCellSize(int numCells) {
        cellsPerRow = numCells;
        cellsPerColumn = numCells;
	}

}


package Simulation;

import Cell.Cell;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import org.w3c.dom.Element;

import java.util.Collection;
import java.util.Map;
import java.util.Random;

/**
 * Created by rhondusmithwick on 1/31/16.
 *
 * @author Rhondu Smithwick
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
	private Map<String, Object> properties;

    Simulation() {
        simulationLoop = buildLoop();
        rn = new Random();
    }

    public final Map<String, Object> setProperties(Element simElem, Map<String,Object> properties) {
        setGenericProperties(simElem);
        return setSpecificProperties(simElem, properties);
    }

    private Timeline buildLoop() {
        final KeyFrame keyFrame = new KeyFrame(Duration.seconds(.5), t -> step());
        final Timeline simulationLoop = new Timeline();
        simulationLoop.setCycleCount(Animation.INDEFINITE);
        simulationLoop.getKeyFrames().addAll(keyFrame);
        return simulationLoop;
    }

    public void init() {
        for (Cell c : getTheCells()) {
            assignInitialState(c);
        }
        changeStates();
    }

    abstract void assignInitialState(Cell c);

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

    private void stopLoop() {
        simulationLoop.stop();
        isPlaying = false;
    }

    private boolean getPlaying() {
        return isPlaying;
    }

    public final void playOrStop() {
        if (!getPlaying()) {
            beginLoop();
        } else {
            stopLoop();
        }
    }

    public void stop() {
        if (getPlaying()) {
            stopLoop();
        }
    }

    final int getRandomNum(int min, int max) {
        int range = max - min + 1;
        return rn.nextInt(range) + min;
    }

    private void setGenericProperties(Element simElem) {
        gridWidth = XMLParser.getIntValue(simElem, "gridWidth");
        gridHeight = XMLParser.getIntValue(simElem, "gridHeight");
        cellsPerRow = XMLParser.getIntValue(simElem, "numCellsPerRow");
        cellsPerColumn = XMLParser.getIntValue(simElem, "numCellsPerColumn");
    }

    Map<String, Object> setSpecificProperties(Element simElem, Map<String,Object> properties) {
        if (getType() != null && getType().equals("Fire")) {
        	for (Map.Entry<String, Object> entry : properties.entrySet()){
				Object myValue = entry.getValue();
			    myValue = XMLParser.getIntValue(simElem,entry.getKey());
			    properties.put(entry.getKey(),myValue);
			}
        }
        return properties;
    }


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


    String getType() {
        return type;
    }


    public void setType(String type) {
        this.type = type;
    }

    final Collection<Cell> getTheCells() {
        return theCells;
    }

    public final void setTheCells(Collection<Cell> theCells) {
        this.theCells = theCells;
    }

    public final boolean increaseRate() {
        double currentRate = simulationLoop.getRate();
        if (currentRate <= 10) {
            simulationLoop.setRate(currentRate + .5);
            return true;
        }
        return false;
    }

    public final boolean decreaseRate() {
        double currentRate = simulationLoop.getRate();
        if (currentRate > 0) {
            simulationLoop.setRate(currentRate - .5);
            return true;
        }
        return false;

    }

    public final void resetRate() {
        simulationLoop.setRate(1.0);
    }


    public final boolean resetCellSize(int numCells) {
        if (numCells > 1) {
            cellsPerRow = numCells;
            cellsPerColumn = numCells;
            return true;
        } else {
            return false;
        }
    }

	public Map<String, Object> getProperties() {
		return properties;
	}

}


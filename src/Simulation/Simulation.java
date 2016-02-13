package Simulation;

import Cell.Cell;
import Cell.Grid.EdgeType;
import Main.CellSocietyProperties;
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
 * @author Rhondu Smithwick
 */
public abstract class Simulation {
    private final CellSocietyProperties simProperties = new CellSocietyProperties();
    private final Random rn;
    private final Timeline simulationLoop;

    private EdgeType edgeType = EdgeType.NORMAL; // for testing; remove later
    private String type;
    private Collection<Cell> theCells;
    private boolean isPlaying = false;

    Simulation() {
        simulationLoop = buildLoop();
        rn = new Random();
    }

    public final void setProperties(Element simElem) {
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

    public void init() {
        getTheCells().stream().forEach(this::assignInitialState);
        changeStates();
    }

    abstract void assignInitialState(Cell c);

    public void step() {
        getTheCells().stream().forEach(Cell::handleUpdate);
    }

    final void changeStates() {
        getTheCells().stream().forEach(Cell::changeState);
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
        int gridWidth = XMLParser.getIntValue(simElem, "gridWidth");
        simProperties.put("gridWidth", gridWidth);
        int gridHeight = XMLParser.getIntValue(simElem, "gridHeight");
        simProperties.put("gridHeight", gridHeight);
        int cellsPerRow = XMLParser.getIntValue(simElem, "numCellsPerRow");
        simProperties.put("cellsPerRow", cellsPerRow);
        int cellsPerColumn = XMLParser.getIntValue(simElem, "numCellsPerColumn");
        simProperties.put("cellsPerColumn", cellsPerColumn);
//        edgeType = EdgeType.valueOf(XMLParser.getTextValue(simElem, "edgeType"));
    }

    abstract void setSpecificProperties(Element simElem);


    public final boolean increaseRate() {
        double currentRate = simulationLoop.getRate();
        if (currentRate <= 15) {
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
            simProperties.put("cellsPerRow", numCells);
            simProperties.put("cellsPerColumn" , numCells);
            return true;
        } else {
            return false;
        }
    }



    String getType() {
      return  simProperties.get("type");
    }


    public void setType(String type) {
        simProperties.put("type", type);
    }

    final Collection<Cell> getTheCells() {
        return theCells;
    }

    public final void setTheCells(Collection<Cell> theCells) {
        this.theCells = theCells;
    }


    public final EdgeType getEdgeType() {
        return edgeType;
    }

    public final int getGridWidth() {
        return simProperties.getInt("gridWidth");
    }

    public final int getGridHeight() {
        return simProperties.getInt("gridHeight");
    }


    public final int getCellsPerRow() {
        return simProperties.getInt("cellsPerRow");

    }

    public final int getCellsPerColumn() {
        return simProperties.getInt("cellsPerColumn");
    }

}


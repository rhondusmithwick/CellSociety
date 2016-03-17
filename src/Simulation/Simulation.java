package Simulation;

import Cell.Cell;
import Config.Config;
import Grid.Grid.EdgeType;
import XML.XMLException;
import XML.XMLParser;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import org.w3c.dom.Element;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Created by rhondusmithwick on 1/31/16.
 *
 * @author Rhondu Smithwick
 */
public abstract class Simulation {
    private static final String GUI_PROPERTY_PATH = "GUIstrings";
    private final ResourceBundle myResources;
    private final Random rn;
    private final Timeline simulationLoop;
    private final EdgeType edgeType = EdgeType.NORMAL; // for testing; remove later
    XMLParser xmlProperties;
    Map<String, Object> savedValues;
    private int gridWidth;
    private int gridHeight;
    private int numCellsPerRow;
    private int numCellsPerColumn;
    private String type;
    private Collection<Cell> theCells;
    private boolean isPlaying = false;
	private Config myConfig;
	private int frame = 0;

    Simulation() {
        myResources = ResourceBundle.getBundle(GUI_PROPERTY_PATH);
        simulationLoop = buildLoop();
        rn = new Random();
    }


    ResourceBundle getResources() {
        return myResources;
    }

    public void setProperties(Element simElem) throws XMLException {
        xmlProperties = new XMLParser(simElem);
        setGenericProperties();
        setSpecificProperties();
        if (xmlProperties.tagExists(type + "Cells")) {
            saveValues();
            theCells = xmlProperties.getCells((type + "Cells"));
        }
    }

    public Map<String, Object> getSavedValues() {
        return savedValues;
    }

    public void saveValues() {
        savedValues = new HashMap<>();
        //savedValues.put("type", type);
        savedValues.put("gridWidth", gridWidth);
        savedValues.put("gridHeight", gridHeight);
        savedValues.put("numCellsPerRow", numCellsPerRow);
        savedValues.put("numCellsPerColumn", numCellsPerColumn);
        saveSpecificValues();
        saveCellStates();
    }

    abstract void saveSpecificValues();

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

    public void initLoad() {
        setSpecificProperties();
        getTheCells().stream().forEach(this::assignLoadState);
        changeStates();
    }

    abstract void assignInitialState(Cell c);

    abstract void assignLoadState(Cell c);

    public void step() {
    	frame ++;
        getTheCells().stream().forEach(Cell::handleUpdate);
    }

    public final void changeStates() {
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

    private void setGenericProperties() {
        type = xmlProperties.getSimType();
        gridWidth = xmlProperties.getIntValue("gridWidth");
        gridHeight = xmlProperties.getIntValue("gridHeight");
        numCellsPerRow = xmlProperties.getIntValue("numCellsPerRow");
        numCellsPerColumn = xmlProperties.getIntValue("numCellsPerColumn");
//	edgeType = EdgeType.valueOf(xmlProperties.getTextValue("edgeType"));
    }

    boolean doesTypeMatch(String myType) {
        return (getType() == null || !getType().equals(myType));
    }

    abstract void setSpecificProperties();

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
            numCellsPerRow = numCells;
            numCellsPerColumn = numCells;
            return true;
        } else {
            return false;
        }
    }


    public final int getGridWidth() {
        return gridWidth;
    }

    public final EdgeType getEdgeType() {
        return edgeType;
    }

    public final int getGridHeight() {
        return gridHeight;
    }


    public final int getCellsPerRow() {
        return numCellsPerRow;
    }


    public final int getCellsPerColumn() {
        return numCellsPerColumn;
    }


    public String getType() {
        return type;
    }


    public void setType(String type) {
        this.type = type;
    }

    public final Collection<Cell> getTheCells() {
        return theCells;
    }

    public void setTheCells(Collection<Cell> theCells) {
        this.theCells = theCells;
    }

    public XMLParser getXmlProperties() {
        return xmlProperties;
    }

    public double getSpeed() {
        return simulationLoop.getRate();
    }

    public final void changeRate(int rate) {
        simulationLoop.setRate(rate);
    }

    public double getSize() {
        return numCellsPerRow;
    }
<<<<<<< HEAD

    public void saveCellStates() {
        for (Cell c : theCells)
            c.saveCellState();
    }


    private void createGraph() {
        xAxis.setLabel(myResources.getString("Frame"));
        yAxis.setLabel(myResources.getString("NumberOfCells"));
        lineChart = new LineChart<Number, Number>(xAxis, yAxis);
    }

    public LineChart<Number, Number> getGraph() {
        return lineChart;
    }

    abstract boolean hasGraph();

    public boolean setGraph(GridPane graph) {
        graph.getChildren().add(lineChart);
        return hasGraph();
    }

=======
    
	public Object getFrame() {
		return frame;
	}
    
    public Config getConfig(){
    	return myConfig;
    }


	public void setConfig(Config config) {
		myConfig = config;	
	}
	
	public Object getNumOfState(String string){
		return null;
	}
		
        
>>>>>>> analysis_bml27
}


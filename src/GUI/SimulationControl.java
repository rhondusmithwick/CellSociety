package GUI;

import Cell.CellManager;
import Simulation.FireSimulation;
import Simulation.Simulation;
import Simulation.XMLParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import org.w3c.dom.Element;

import java.io.File;
import java.util.ResourceBundle;

public class SimulationControl {
    private static final String DEFAULT_GUUI_PROPERTY = "GUIstrings";
    private static final String DEFAULT_SIM_TYPE = "Fire";

    private final ResourceBundle myResources;
    private final GridPane display;
    private final ObservableList<String> mySimulations;
    private Simulation sim;
    private String simType = DEFAULT_SIM_TYPE;
    private CellManager cellManager;
    private int newSize = 0;


    public SimulationControl(GridPane display) {
        this.display = display;
        myResources = ResourceBundle.getBundle(DEFAULT_GUUI_PROPERTY);
        mySimulations = createSimulationsList();
        switchSimulation(DEFAULT_SIM_TYPE);
    }

    public void switchSimulation(Object o) {
        simType = o.toString();
        sim = getSimulation();
        setSimulation();
    }

    public void switchSimulation(Element simElem) {
        simType = XMLParser.getSimType(simElem);
        sim = getSimulation();
        sim.setType(simType);
        sim.setProperties(simElem);
        setSimulation();
    }

    private void setSimulation() {
        displayNewCells();
        sim.setTheCells(cellManager.getCells());
        sim.init();
    }

    private void displayNewCells() {
        display.getChildren().remove(cellManager);
        cellManager = createCellManager(simType);
        GridPane.setConstraints(cellManager, 0, 0);
        GridPane.setRowSpan(cellManager, 6);
        display.getChildren().add(cellManager);
    }


    private Simulation getSimulation() {
        Simulation sim;
        try {
            String simClassName = "Simulation." + simType + "Simulation";
            Class c = Class.forName(simClassName);
            sim = (Simulation) c.newInstance();
        } catch (InstantiationException
                | IllegalAccessException
                | ClassNotFoundException e) {
            sim = new FireSimulation();
        }
        return sim;
    }

    public ObservableList<String> getSimulations() {
        return mySimulations;
    }

    public void slowDown() {
        if (!sim.decreaseRate()) {
            showError(myResources.getString("DecreaseError"));
        }

    }

    public void speedUp() {
        if (!sim.increaseRate()) {
            showError(myResources.getString("IncreaseError"));
        }
    }

    public void step() {
        stop();
        sim.step();
    }

    public void stop() {
        sim.stop();
    }

    public void playPause() {
        sim.playOrStop();
    }

    public void reset() {
        sim = getSimulation();
        setSimulation();
    }

    public void playAgain() {
        sim = getSimulation();
        sim.resetCellSize(newSize);
        setSimulation();
        sim.playOrStop();
    }

    private CellManager createCellManager(String simType) {
        CellManager cellManager = new CellManager();
        cellManager.setGrid(sim.getGridWidth(), sim.getGridHeight(),
                sim.getCellsPerRow(), sim.getCellsPerColumn());
        cellManager.init(simType);
        return cellManager;
    }


    public void sizeChange(String string) {
        try {
            newSize = Integer.parseInt(string);
            sim = getSimulation();

            if (!sim.resetCellSize(newSize)) {
                throw new Exception();
            }

            setSimulation();
        }
        catch (Exception e){
            showError(myResources.getString("SizeError"));
        }
    }

    public void openFile(File file) {
        System.out.println("File Path: " + file.getPath());
        switchSimulation(XMLParser.getXmlElement(file.getPath()));
    }

    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(myResources.getString("ErrorTitle"));
        alert.setContentText(message);
        alert.showAndWait();
    }

    private ObservableList<String> createSimulationsList() {
        return FXCollections.observableArrayList(
                myResources.getString("GameOfLifeSim"),
                myResources.getString("SegregationSim"),
                myResources.getString("FireSim"),
                myResources.getString("PredatorPreySim")
        );
    }
}



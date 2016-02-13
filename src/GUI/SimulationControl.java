package GUI;

import Grid.Grid;
import Grid.TriangleGrid;
import Simulation.FireSimulation;
import Simulation.Simulation;
import XML.XMLException;
import XML.XMLOutput;
import XML.XMLParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.w3c.dom.Element;

import java.io.File;
import java.util.ResourceBundle;

/**
 * Simulation Control Class: controls all simulation such as loading, switching,
 * speed, size change, reset, and play again by coordinating the actions of the
 * GUI and Simulations classes.
 * <p>
 * Created by bliborio on 2/2/16.
 *
 * @author Bruna Liborio
 */

public class SimulationControl {
    private static final String DEFAULT_GUI_PROPERTY = "GUIstrings";
    private static final String DEFAULT_SIM_TYPE = "Fire";
    private final ResourceBundle myResources;
    private final GridPane display;
    private final ObservableList<String> mySimulations;
    private final Label simLabel = new Label();
    private String simType = DEFAULT_SIM_TYPE;
    private Simulation sim;
    private Config config = new FireConfig();
    private Grid grid;
    private Group gridGroup;
    private int newSize = 0;
    private File myXMLFile = null;

    /**
     * Sets starting simulation control parameters.
     *
     * @param display the main GridPane
     */
    public SimulationControl(GridPane display) {
        this.display = display;
        myResources = ResourceBundle.getBundle(DEFAULT_GUI_PROPERTY);
        mySimulations = createSimulationsList();
        switchSimulation(DEFAULT_SIM_TYPE);
    }

    /**
     * Switches between simulations and sets the newest one when the new
     * simulation is a default simulation.
     *
     * @param o Simulation object to switch t
     */
    public void switchSimulation(Object o) {
        //display.getChildren().remove(sim.getGraph());
        myXMLFile = null;
        newSize = 0;
        simType = o.toString();
        sim = getSimulation();
        setSimulation();
        config = getConfig();
        setConfigControls();
        //display.getChildren().add(sim.getGraph());

    }


    private Config getConfig() {
        removeConfigControls();
        Config config;
        try {
            Class myClass = Class.forName("GUI." + simType + "Config");
            config = (Config) myClass.newInstance();
        } catch (InstantiationException
                | IllegalAccessException
                | ClassNotFoundException e) {
            System.out.println("in catch");
            config = new FireConfig();
        }
        config.setSim(this, sim);
        config.init();
        return config;
    }


    /**
     * Switches between simulations when the new simulation is a loaded XML
     * files.
     *
     * @param simElem Simulation element from the XML file parser.
     */
    private void switchSimulation(Element simElem) {
        //display.getChildren().remove(sim.getGraph());
        simType = XMLParser.getSimType(simElem);
        sim = getSimulation();

        assert sim != null;
        sim.setType(simType);
        sim.setProperties(simElem);
        config = getConfig();
        setConfigControls();
        //display.getChildren().add(sim.getGraph());
    }

    /**
     * Sets and displays new simulation parameters.
     */
    private void setSimulation() {
        setSimLabel();
        displayNewCells();
        sim.setTheCells(grid.getCells());
        sim.init();
    }

    public void saveSimulation(File file) {
        sim.saveValues();
        XMLOutput simSave = new XMLOutput(sim);
        simSave.theCells = grid.getCells();
        simSave.writeXML(file);

    }

    /**
     * Displays cells and sets the grid.
     */
    private void displayNewCells() {
        if (gridGroup != null) display.getChildren().remove(gridGroup);
        grid = createGrid(simType);
        gridGroup = grid.getGroup();
        GridPane.setConstraints(gridGroup, 0, 0);
        GridPane.setRowSpan(gridGroup, 9);
        display.getChildren().add(gridGroup);
    }

    /**
     * Sets a new simulation to sim based on the current simType.
     *
     * @return sim a new simulation from default parameters
     */
    private Simulation getSimulation() {
        Simulation sim;
        try {
            String simClassName = "Simulation." + simType + "Simulation";
            Class c = Class.forName(simClassName);
            sim = (Simulation) c.newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            try {
                sim = new FireSimulation();
            } catch (XMLException e1) {
                showError("XML read error");
                return null;
            }
        }
        return sim;
    }

    /**
     * Returns the simulation list.
     *
     * @return mySimulations a list of string of all the simulations available
     */
    public ObservableList<String> getSimulations() {
        return mySimulations;
    }

    public void speed(int new_val, int old_val) {
        if (new_val != old_val) {
            sim.changeRate(new_val);
        }
    }

    /**
     * Decreases simulation rate. Displays error when rate can no longer be
     * decreased.
     */
    public void slowDown() {
        if (!sim.decreaseRate()) {
            showError(myResources.getString("DecreaseError"));
        }
    }

    /**
     * Increases simulation rate. Displays error when rate can no longer be
     * increased.
     */
    public void speedUp() {
        if (!sim.increaseRate()) {
            showError(myResources.getString("IncreaseError"));
        }
    }


    /**
     * Steps through simulation one frame at a time.
     */
    public void step() {
        stop();
        sim.step();
    }

    /**
     * Stops simulation.
     */
    public void stop() {
        sim.stop();
    }

    /**
     * Plays or stops the simulation based on the simulation's current state.
     */
    public void playPause() {
        sim.playOrStop();
    }

    /**
     * Resets simulation to default parameters of current simType. Disregards
     * all user changes.
     */
    public void reset() {
        myXMLFile = null;
        newSize = 0;
        sim = getSimulation();
        setSimulation();
    }

    /**
     * Replays the same simulation type current running with same parameters but
     * different initial, random placement .
     */
    public void playAgain() {
        try {
            switchSimulation(XMLParser.getXmlElement(myXMLFile.getPath()));
        } catch (Exception e) {
            sim = getSimulation();
        }
        assert sim != null;
        sim.resetCellSize(newSize);
        setSimulation();
    }

    /**
     * Creates and initializes the Grid for the program.
     *
     * @param simType the currently saved simulation type
     * @result grid the new grid
     */
    private Grid createGrid(String simType) {
        Grid grid = new TriangleGrid(); // testing
        grid.setGrid(sim.getGridWidth(), sim.getGridHeight(),
                sim.getCellsPerRow(),
                sim.getCellsPerColumn(), sim.getEdgeType());
        grid.init(simType);
        return grid;
    }

    /**
     * Changes number of rows and columns per line on the grid based on user
     * input.
     *
     * @param size string from the user
     */
    public void sizeChange(int size) {
        try {
            try {
                switchSimulation(XMLParser.getXmlElement(myXMLFile.getPath()));
            } catch (Exception e) {
                sim = getSimulation();
            }
            assert sim != null;
            if (!sim.resetCellSize(size)) {
                throw new Exception();
            }
            newSize = size;
            setSimulation();
        } catch (Exception e) {
            showError(myResources.getString("SizeError"));
        }
    }

    /**
     * Opens new XML file and sets the chosen simulation
     *
     * @param file File returned from fileChooser
     */
    public void openFile(File file) {
        newSize = 0;
        myXMLFile = file;
        try {
            switchSimulation(XMLParser.getXmlElement(myXMLFile.getPath()));
        } catch (XMLException e) {
            showError(myResources.getString("XMLReadError"));
        }
        setSimulation();
    }

    /**
     * Displays an error box with a given message
     *
     * @param message message to display on the error screen
     */
    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(myResources.getString("ErrorTitle"));
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Creates the simulation list of possible simulations.
     */
    private ObservableList<String> createSimulationsList() {
        return FXCollections.observableArrayList(myResources.getString("GameOfLifeSim"),
                myResources.getString("SegregationSim"), myResources.getString("FireSim"),
                myResources.getString("PredatorPreySim"), "ForagingAnts", "SlimeMold");
    }

    /**
     * Returns the simulation title Label.
     *
     * @return simLabel the simulation title label
     */
    public Label getSimLabel() {
        simLabel.setStyle("-fx-font-size: 2em;");
        return simLabel;
    }

    private void setConfigControls() {
        display.getChildren().addAll(config.getControls());
    }

    private void removeConfigControls() {
        display.getChildren().removeAll(config.getControls());
    }

    /**
     * Resets the text inside the simulation title label.
     */
    private void setSimLabel() {
        simLabel.setText(simType);
    }
}

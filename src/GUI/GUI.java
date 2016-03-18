package GUI;

import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * GUI class: handles the displaying and set up of all GUI elements besides the
 * grid. Handles buttons, drop down bars, and user interaction boxes.
 * <p>
 * Created by bliborio on 2/2/16.
 *
 * @author Bruna Liborio
 */

public class GUI {

    private static final String GUI_PROPERTY_PATH = "GUIstrings";
    private final static List<Node> controlList = new ArrayList<>();
    private final ResourceBundle myResources;
    private final SimulationControl mySimControl;
    private Label simLabel;
    private ComboBox<String> defaultSims;
    private ComboBox<String> edgeType;
    private Button myFileButton;
    private Button myPlayPauseButton;
    private Button myStepButton;
    private Button myResetButton;
    private Button myPlayAgainButton;
    private Button mySaveToFileButton;
    private Button myGraphButton;


    /**
     * Sets starting GUI parameters and links GUI to simulation control class
     * instance.
     *
     * @param mySimControl the simulation control class instance for the program
     */
    public GUI(SimulationControl simControl) {
        myResources = ResourceBundle.getBundle(GUI_PROPERTY_PATH);
        mySimControl = simControl;
        createControls();
    }

    /**
     * Creates a new button. Sets the button's text and action.
     *
     * @param property the string for the button's displayed text
     * @param handles  event/action button executes upon being clicked
     * @return result
     * the new button
     */
    private static Button makeButton(String property, EventHandler<ActionEvent> handler, int col, int row, int colSpan, int rowSpan) {
        Button result = new Button();
        result.setText(property);
        result.setOnAction(handler);
        setAndAdd(result, col, row, colSpan, rowSpan);
        return result;
    }

    /**
     * Sets all necessary constraints on all the controls while adding them to the controlList.
     *
     * @param node    the control
     * @param col     column index for location on GridPane
     * @param row     row index for location on GridPane
     * @param colSpan number of columns to be spanned by the control
     * @param row     number of rows to be spanned by the control
     */
    private static void setAndAdd(Node node, int col, int row, int colSpan, int rowSpan) {
        GridPane.setConstraints(node, col, row, colSpan, rowSpan, HPos.CENTER, VPos.CENTER);
        controlList.add(node);
        ((Region) node).setMaxWidth(Double.MAX_VALUE);
    }

    /**
     * Sets the local directory as the first to appear in the file chooser interaction box.
     *
     * @return dir
     * the new directory
     */
    private static File getLocalDir() {
        ProtectionDomain pd = GUI.class.getProtectionDomain();
        CodeSource cs = pd.getCodeSource();
        URL localDir = cs.getLocation();
        File dir;
        try {
            dir = new File(localDir.toURI());
        } catch (URISyntaxException e) {
            dir = new File(localDir.getPath());
        }
        return dir;
    }

    /**
     * Creates and sets up all controls with their necessary parameters.
     */
    private void createControls() {
        createLabels();
        createComboBoxes();
        createButtons();
    }

    private void createLabels() {
        simLabel = mySimControl.getSimLabel();
        setAndAdd(simLabel, 0, 0, 1, 1);
    }

    /**
     * Creates the comboBox with a change listener.
     */
    private void createComboBoxes() {
        defaultSims = makeComboBox(myResources.getString("SelectionPrompt"), mySimControl.getSimulations(), (observable, oldValue, newValue) -> mySimControl.switchSimulation(newValue), 1, 1, 5, 1);
        edgeType = makeComboBox(myResources.getString("EdgePrompt"), mySimControl.getEdgeType(), (observable, oldValue, newValue) -> mySimControl.changeEdgeType(newValue), 1, 2, 5, 1);
    }

    private ComboBox<String> makeComboBox(String prompt, ObservableList<String> choices, ChangeListener<? super String> event, int col, int row, int colSpan, int rowSpan) {
        ComboBox<String> comboBox = new ComboBox<String>(choices);
        comboBox.setEditable(false);
        comboBox.setPromptText(prompt);
        comboBox.getSelectionModel().selectedItemProperty()
                .addListener(event);
        setAndAdd(comboBox, col, row, colSpan, rowSpan);
        return comboBox;
    }

    /**
     * Creates all buttons using makeButton.
     */
    private void createButtons() {
        myFileButton = makeButton(myResources.getString("XMLLoadPrompt"), event -> openFile(getFileChooser()), 1, 0, 5, 1);
        myPlayPauseButton = makeButton(myResources.getString("PlayPauseButton"), event -> mySimControl.playPause(), 1, 3, 2, 1);
        myStepButton = makeButton(myResources.getString("StepButton"), event -> mySimControl.step(), 4, 3, 2, 1);
        myResetButton = makeButton(myResources.getString("ResetButton"), event -> mySimControl.reset(), 1, 5, 5, 1);
        myPlayAgainButton = makeButton(myResources.getString("PlayAgainButton"), event -> mySimControl.playAgain(), 1, 4, 5, 1);
        mySaveToFileButton = makeButton(myResources.getString("SaveToFileButton"), event -> saveFile(getFileChooser()), 0, 12, 1, 1);
        myGraphButton = makeButton(myResources.getString("DisplayGraph"), event -> mySimControl.startGraph(), 0, 13, 1, 1);
    }

    /**
     * Gets the list containing all controls.
     *
     * @return the complete control list
     */
    public List<Node> getControls() {
        return controlList;
    }

    /**
     * Sets up and displays the file chooser interactin box.
     */

    private FileChooser getFileChooser() {
        mySimControl.stop();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(myResources.getString("XMLChoosePrompt"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML", "*.xml"));
        fileChooser.setInitialDirectory(getLocalDir());
        return fileChooser;
    }

    private void openFile(FileChooser fileChooser) {
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            mySimControl.openFile(file);
        }
    }

    private void saveFile(FileChooser fileChooser) {
        File file = fileChooser.showSaveDialog(new Stage());
        mySimControl.saveSimulation(file);
    }
}

package GUI;

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

    private final ResourceBundle myResources;
    private final List<Node> controlList = new ArrayList<>();
    private final SimulationControl mySimControl;
    private final Label simLabel;
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
    public GUI(SimulationControl mySimControl) {
        myResources = ResourceBundle.getBundle(GUI_PROPERTY_PATH);
        this.mySimControl = mySimControl;
        simLabel = mySimControl.getSimLabel();
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
    private static Button makeButton(String property, EventHandler<ActionEvent> handler) {
        Button result = new Button();
        result.setText(property);
        result.setOnAction(handler);
        return result;
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
        createComboBoxes();
        createButtons();
        setAndAdd();
    }

    /**
     * Creates the comboBox with a change listener.
     */
    private void createComboBoxes() {
        defaultSims = makeComboBox(myResources.getString("SelectionPrompt"), mySimControl.getSimulations());
        defaultSims.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> mySimControl.switchSimulation(newValue));
        edgeType = makeComboBox(myResources.getString("EdgePrompt"), mySimControl.getEdgeType());
        edgeType.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> mySimControl.changeEdgeType(newValue));
    }

    private ComboBox<String> makeComboBox(String prompt, ObservableList<String> choices) {
        ComboBox<String> comboBox = new ComboBox<String>(choices);
        comboBox.setEditable(false);
        comboBox.setPromptText(prompt);
        return comboBox;
    }

    /**
     * Creates all buttons using makeButton.
     */
    private void createButtons() {
        myFileButton = makeButton(myResources.getString("XMLLoadPrompt"), event -> openFile(getFileChooser()));
        myPlayPauseButton = makeButton(myResources.getString("PlayPauseButton"), event -> mySimControl.playPause());
        myStepButton = makeButton(myResources.getString("StepButton"), event -> mySimControl.step());
        myResetButton = makeButton(myResources.getString("ResetButton"), event -> mySimControl.reset());
        myPlayAgainButton = makeButton(myResources.getString("PlayAgainButton"), event -> mySimControl.playAgain());
        mySaveToFileButton = makeButton(myResources.getString("SaveToFileButton"), event -> saveFile(getFileChooser()));
        myGraphButton = makeButton(myResources.getString("DisplayGraph"), event-> mySimControl.startGraph());

    }

    /**
     * Calls setAndAdd for all controls.
     */
    private void setAndAdd() {
        setAndAdd(myFileButton, 1, 0, 5, 1);
        setAndAdd(defaultSims, 1, 1, 5, 1);
        setAndAdd(edgeType, 1, 2, 5, 1);
        setAndAdd(myPlayPauseButton, 1, 3, 2, 1);
        setAndAdd(myStepButton, 4, 3, 2, 1);
        setAndAdd(myPlayAgainButton, 1, 4, 5, 1);
        setAndAdd(myResetButton, 1, 5, 5, 1);

        setAndAdd(simLabel, 0, 0, 1, 1);
        setAndAdd(mySaveToFileButton, 0, 12, 1, 1);
        setAndAdd(myGraphButton,0,13,1,1);

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
    private void setAndAdd(Node node, int col, int row, int colSpan, int rowSpan) {
        GridPane.setConstraints(node, col, row, colSpan, rowSpan, HPos.CENTER, VPos.CENTER);
        controlList.add(node);
        ((Region) node).setMaxWidth(Double.MAX_VALUE);
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

    /**
     * Gets the list containing all controls.
     *
     * @return the complete control list
     */
    public List<Node> getControls() {
        return controlList;
    }

}

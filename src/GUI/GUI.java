package GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
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
import java.util.Optional;
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
    private ComboBox<String> comboBox;
    private Button myFileButton;
    private Button myPlayPauseButton;
    private Button myStepButton;
    private Button mySpeedUpButton;
    private Button mySlowDownButton;
    private Button mySetSizeButton;
    private Button myResetButton;
    private Button myPlayAgainButton;

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
        createComboBox();
        createButtons();
        setAndAdd();
    }

    /**
     * Creates the comboBox with a change listener.
     */
    private void createComboBox() {
        comboBox = new ComboBox<>(mySimControl.getSimulations());
        comboBox.setEditable(false);
        comboBox.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> mySimControl.switchSimulation(newValue));
        comboBox.setPromptText(myResources.getString("SelectionPrompt"));
    }

    /**
     * Creates all buttons using makeButton.
     */
    private void createButtons() {
        myFileButton = makeButton(myResources.getString("XMLLoadPrompt"), event -> setUpFileChooser());
        mySetSizeButton = makeButton(myResources.getString("SetSizeButton"), event -> setUpSizeInput());
        myPlayPauseButton = makeButton(myResources.getString("PlayPauseButton"), event -> mySimControl.playPause());
        myStepButton = makeButton(myResources.getString("StepButton"), event -> mySimControl.step());
        mySpeedUpButton = makeButton(myResources.getString("FasterButton"), event -> mySimControl.speedUp());
        mySlowDownButton = makeButton(myResources.getString("SlowerButton"), event -> mySimControl.slowDown());
        myResetButton = makeButton(myResources.getString("ResetButton"), event -> mySimControl.reset());
        myPlayAgainButton = makeButton(myResources.getString("PlayAgainButton"), event -> mySimControl.playAgain());
    }

    /**
     * Calls setAndAdd for all controls.
     */
    private void setAndAdd() {
        setAndAdd(myFileButton, 1, 0, 2, 1);
        setAndAdd(myResetButton, 1, 6, 2, 1);
        setAndAdd(myPlayAgainButton, 1, 5, 2, 1);
        setAndAdd(mySetSizeButton, 1, 2, 2, 1);
        setAndAdd(myPlayPauseButton, 1, 3, 1, 1);
        setAndAdd(myStepButton, 2, 3, 1, 1);
        setAndAdd(mySlowDownButton, 1, 4, 1, 1);
        setAndAdd(mySpeedUpButton, 2, 4, 1, 1);
        setAndAdd(comboBox, 1, 1, 2, 1);
        setAndAdd(simLabel, 1, 7, 3, 3);
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
     * Sets up and displays a size input interaction box.
     */
    private void setUpSizeInput() {
        mySimControl.stop();
        TextInputDialog input = new TextInputDialog("");
        input.setTitle(myResources.getString("SizePromptTitle"));
        input.setContentText(myResources.getString("SizePrompt"));
        Optional<String> response = input.showAndWait();
        if (response.isPresent()) {
            mySimControl.sizeChange(response.get());
        }
    }

    /**
     * Sets up and displays the file chooser interactin box.
     */
    private void setUpFileChooser() {
        mySimControl.stop();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(myResources.getString("XMLChoosePrompt"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML", "*.xml"));
        fileChooser.setInitialDirectory(getLocalDir());
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            mySimControl.openFile(file);
        }
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

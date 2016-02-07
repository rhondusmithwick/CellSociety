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
    private Button myFileButton;
    private Button myPlayPauseButton;
    private Button myStepButton;
    private Button mySpeedUpButton;
    private Button mySlowDownButton;
    private Button mySetSizeButton;
    private Button myResetButton;
    private Button myPlayAgainButton;
    private ComboBox<String> comboBox;

    public GUI(SimulationControl mySimControl) {
        myResources = ResourceBundle.getBundle(GUI_PROPERTY_PATH);
        this.mySimControl = mySimControl;
        simLabel = mySimControl.getSimLabel();
        createControls();
    }

    private static Button makeButton(String property, EventHandler<ActionEvent> handler) {
        Button result = new Button();
        result.setText(property);
        result.setOnAction(handler);
        return result;
    }

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

    private void createControls() {
        createComboBox();
        createButtons();
        setAndAdd();
    }

    private void createComboBox() {
        comboBox = new ComboBox<>(mySimControl.getSimulations());
        comboBox.setEditable(false);
        comboBox.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> mySimControl.switchSimulation(newValue)
        );
        comboBox.setPromptText(myResources.getString("SelectionPrompt"));
    }

    private void createButtons() {
        myFileButton = makeButton(myResources.getString("XMLLoadPrompt"),
                event -> setUpFileChooser());
        mySetSizeButton = makeButton(myResources.getString("SetSizeButton"),
                event -> setUpSizeInput());
        myPlayPauseButton = makeButton(myResources.getString("PlayPauseButton"),
                event -> mySimControl.playPause());
        myStepButton = makeButton(myResources.getString("StepButton"),
                event -> mySimControl.step());
        mySpeedUpButton = makeButton(myResources.getString("FasterButton"),
                event -> mySimControl.speedUp());
        mySlowDownButton = makeButton(myResources.getString("SlowerButton"),
                event -> mySimControl.slowDown());
        myResetButton = makeButton(myResources.getString("ResetButton"),
                event -> mySimControl.reset());
        myPlayAgainButton = makeButton(myResources.getString("PlayAgainButton"),
                event -> mySimControl.playAgain());
    }
    
    private void setAndAdd(){
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
    
    private void setAndAdd(Node node, int col, int row, int colSpan, int rowSpan){
    	GridPane.setConstraints(node, col, row, colSpan, rowSpan, HPos.CENTER, VPos.CENTER);
    	controlList.add(node);
    	((Region) node).setMaxWidth(Double.MAX_VALUE);
    }

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

    private void setUpFileChooser() {
        mySimControl.stop();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(myResources.getString("XMLChoosePrompt"));
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("XML", "*.xml")
        );
        fileChooser.setInitialDirectory(getLocalDir());
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            mySimControl.openFile(file);
        }
    }

    public List<Node> getControls() {
        return controlList;
    }

}


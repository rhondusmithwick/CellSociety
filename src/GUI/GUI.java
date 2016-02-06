package GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

public class GUI {

    private static final String GUI_PROPERTY_PATH = "Guistrings";

    private final ResourceBundle myResources;
    private final List<Node> controlList = new ArrayList<>();
    private final SimulationControl mySimControl;
    private Button myFileButton;
    private Button myPlayPauseButton;
    private Button myStepButton;
    private Button mySpeedUpButton;
    private Button mySlowDownButton;
    private Button mySetSizeButton;
    private ComboBox<String> comboBox;

    public GUI(SimulationControl mySimulationControl) {
        myResources = ResourceBundle.getBundle(GUI_PROPERTY_PATH);
        mySimControl = mySimulationControl;
        createControls();
        setWidths();
        setLocations();
    }


    private void setWidths() {
        for (Node node : this.getControls()) {
            ((Region) node).setMaxWidth(Double.MAX_VALUE);
        }
    }


    private void createControls() {
        createComboBox();
        createButtons();
        addButtons();
    }

    private void createComboBox(){
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
                event -> setUpSizeBox());
        myPlayPauseButton = makeButton(myResources.getString("PlayPauseButton"),
                event -> mySimControl.playPause());
        myStepButton = makeButton(myResources.getString("StepButton"),
                event -> mySimControl.step());
        mySpeedUpButton = makeButton(myResources.getString("FasterButton"),
                event -> mySimControl.speedUp());
        mySlowDownButton = makeButton(myResources.getString("SlowerButton"),
                event -> mySimControl.slowDown());
    }

    private void addButtons() {
        controlList.add(myFileButton);
        controlList.add(comboBox);
        controlList.add(mySetSizeButton);
        controlList.add(myPlayPauseButton);
        controlList.add(myStepButton);
        controlList.add(mySpeedUpButton);
        controlList.add(mySlowDownButton);
    }

    private void setLocations() {
        GridPane.setConstraints(myFileButton, 1, 0, 2, 1, HPos.CENTER, VPos.CENTER);
        GridPane.setConstraints(comboBox, 1, 1, 2, 1, HPos.CENTER, VPos.CENTER);
        GridPane.setConstraints(mySetSizeButton, 1, 2, 2, 1, HPos.CENTER, VPos.CENTER);
        GridPane.setConstraints(myPlayPauseButton, 1, 3, 1, 1, HPos.CENTER, VPos.CENTER);
        GridPane.setConstraints(myStepButton, 2, 3, 1, 1, HPos.CENTER, VPos.CENTER);
        GridPane.setConstraints(mySlowDownButton, 1, 4, 1, 1, HPos.CENTER, VPos.CENTER);
        GridPane.setConstraints(mySpeedUpButton, 2, 4, 1, 1, HPos.CENTER, VPos.CENTER);
    }

    private Button makeButton(String property, EventHandler<ActionEvent> handler) {
        Button result = new Button();
        result.setText(property);
        result.setOnAction(handler);
        return result;
    }

    private void setUpSizeBox(){
        mySimControl.stop();
        TextInputDialog input = new TextInputDialog("");
        input.setTitle(myResources.getString("SizePromptTitle"));
        input.setContentText(myResources.getString("SizePrompt"));
        Optional<String> response = input.showAndWait();
        if (response.isPresent()){
            //if (response.isPresent() && response.get()=="") {
            System.out.println(response.get());
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
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            mySimControl.openFile(file);
        }
    }

    public List<Node> getControls() {
        return controlList;
    }

}


package GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GUI {

    private final ResourceBundle myResources;
    private final List<Node> controlList = new ArrayList<>();
    private final SimulationControl mySimControl;
    private Label outputLabel;
    private TextField inputTextField;
    private Button myFileButton;
    private Button myPlayPauseButton;
    private Button myStepButton;
    private Button mySpeedUpButton;
    private Button mySlowDownButton;
    private Button mySetSizeButton;
    private ComboBox<String> comboBox;

    public GUI(SimulationControl mySimulationControl, String resource) {
        myResources = ResourceBundle.getBundle(resource);
        mySimControl = mySimulationControl;
        createControls();
        setLocations();
    }


    private void createControls() {
        comboBox = new ComboBox<>(mySimControl.getSimulations());
        comboBox.setEditable(false);
        comboBox.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> mySimControl.switchSimulation(newValue)
        );
        comboBox.setPromptText(myResources.getString("SelectionPrompt"));
        outputLabel = new Label();
        inputTextField = new TextField();
        inputTextField.setText(myResources.getString("SizePrompt"));
        createButtons();
        addButtons();
    }

    private void createButtons() {
        myFileButton = makeButton(myResources.getString("XMLLoadPrompt"),
                event -> setUpFileChooser());
        mySetSizeButton = makeButton(myResources.getString("SetSizeButton"),
                event -> mySimControl.sizeChange(inputTextField.getText()));
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
        controlList.add(outputLabel);
        controlList.add(inputTextField);
        controlList.add(mySetSizeButton);
        controlList.add(myPlayPauseButton);
        controlList.add(myStepButton);
        controlList.add(mySpeedUpButton);
        controlList.add(mySlowDownButton);
    }

    private void setLocations() {
        GridPane.setConstraints(myFileButton, 1, 0);
        GridPane.setColumnSpan(myFileButton, 2);
        GridPane.setConstraints(comboBox, 1, 1);
        GridPane.setColumnSpan(comboBox, 2);
        GridPane.setConstraints(inputTextField, 1, 2);
        GridPane.setConstraints(mySetSizeButton, 2, 2);
        GridPane.setConstraints(myPlayPauseButton, 1, 3);
        GridPane.setConstraints(myStepButton, 2, 3);
        GridPane.setConstraints(mySlowDownButton, 1, 4);
        GridPane.setConstraints(mySpeedUpButton, 2, 4);
        GridPane.setConstraints(outputLabel, 1, 5);
        GridPane.setColumnSpan(outputLabel, 2);
    }

    private Button makeButton(String property, EventHandler<ActionEvent> handler) {
        Button result = new Button();
        result.setText(property);
        result.setOnAction(handler);
        return result;
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

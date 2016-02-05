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


import Main.CellSociety;

public class Controls {

    private Label outputLabel;
    private TextField inputTextField;

    private Button myFileButton;
    private Button myPlayPauseButton;
    private Button mySkipForwardButton;
    private Button mySpeedUpButton;
    private Button mySlowDownButton;
    private Button myGoButton;

    private ComboBox<String> comboBox;
    private FileChooser fileChooser;

    private ArrayList<Node> controlList = new ArrayList<Node>();

    private SimulationControl mySimControl;

    public Controls(SimulationControl mySimulationControl) {
        mySimControl = mySimulationControl;

        createControls();
        setLocations();

    }

    public List<Node> getControls() {
        return controlList;
    }

    private void createControls() {
    
        comboBox = new ComboBox<>(mySimControl.getSimulations());
        comboBox.setPromptText("Select a simulation.");
        comboBox.setEditable(false);
        comboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                mySimControl.switchSimulation(newValue));

        outputLabel = new Label();
        inputTextField = new TextField();
        inputTextField.setText("Enter a size.");
        
        myFileButton = makeButton("Load XML File.", event -> setUpFileChooser());
        myGoButton = makeButton("Go", event -> mySimControl.sizeChange(inputTextField.getText()));
        myPlayPauseButton = makeButton("Play/Pause", event -> mySimControl.playPause());
        mySkipForwardButton = makeButton("Skip", event -> mySimControl.skip());
        mySpeedUpButton = makeButton("Faster", event -> mySimControl.speedUp());
        mySlowDownButton = makeButton("Slower", event -> mySimControl.slowDown());
        
        controlList.add(myFileButton);
        controlList.add(comboBox);
        controlList.add(outputLabel);
        controlList.add(inputTextField);
        controlList.add(myGoButton);
        controlList.add(myPlayPauseButton);
        controlList.add(mySkipForwardButton);
        controlList.add(mySpeedUpButton);
        controlList.add(mySlowDownButton);

    }

    private void setLocations() {
    	GridPane.setConstraints(myFileButton,1, 0);
    	GridPane.setColumnSpan(myFileButton, 2);
        GridPane.setConstraints(comboBox, 1, 1);
        GridPane.setColumnSpan(comboBox, 2);
        GridPane.setConstraints(inputTextField, 1, 2);
        GridPane.setConstraints(myGoButton, 2, 1);
        GridPane.setConstraints(myPlayPauseButton, 1, 3);
        GridPane.setConstraints(mySkipForwardButton, 2, 3);
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
    
    private void setUpFileChooser(){
    	fileChooser = new FileChooser();
    	fileChooser.setTitle("Choose an XML File");
    	fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("XML", "*.xml")
            );
    	Stage dialogStage = new Stage();
        File file = fileChooser.showOpenDialog(dialogStage);
        if (file != null) {
           mySimControl.openFile(file);
        }
    }

}

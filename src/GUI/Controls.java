package GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class Controls {

    private Label outputLabel;
    private TextField inputTextField;

    private Button myPlayPauseButton;
    private Button mySkipForwardButton;
    private Button mySpeedUpButton;
    private Button mySlowDownButton;

    private ComboBox<String> comboBox;

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

        myPlayPauseButton = makeButton("Play/Pause", event -> mySimControl.playPause());
        mySkipForwardButton = makeButton("Skip", event -> mySimControl.skip());
        mySpeedUpButton = makeButton("Faster", event -> mySimControl.speedUp());
        mySlowDownButton = makeButton("Slower", event -> mySimControl.slowDown());

        controlList.add(comboBox);
        controlList.add(outputLabel);
        controlList.add(inputTextField);
        controlList.add(myPlayPauseButton);
        controlList.add(mySkipForwardButton);
        controlList.add(mySpeedUpButton);
        controlList.add(mySlowDownButton);

    }

    private void setLocations() {
        GridPane.setConstraints(comboBox, 1, 0);
        GridPane.setConstraints(inputTextField, 1, 1);
        GridPane.setConstraints(outputLabel, 1, 2);
        GridPane.setColumnSpan(comboBox, 2);
        GridPane.setColumnSpan(inputTextField, 2);
        GridPane.setColumnSpan(outputLabel, 2);
        GridPane.setConstraints(myPlayPauseButton, 1, 3);
        GridPane.setConstraints(mySkipForwardButton, 2, 3);
        GridPane.setConstraints(mySlowDownButton, 1, 4);
        GridPane.setConstraints(mySpeedUpButton, 2, 4);

    }

    private Button makeButton(String property, EventHandler<ActionEvent> handler) {
        Button result = new Button();
        result.setText(property);
        result.setOnAction(handler);
        return result;
    }

}

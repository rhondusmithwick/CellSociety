package GUI;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;

import java.util.List;

public class GUI {

    private Controls myControls;
    private final GridPane myDisplay;
    //private ResourceBundle myResources;

    // public GUI(CellSociety mySociety, Stage controlStage) {
    public GUI(GridPane display) {
        //myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "GUIstrings");
        myDisplay = display;
    }

    public GridPane init() {
        SimulationControl mySimControl = new SimulationControl(myDisplay);
        myControls = new Controls(mySimControl);
        addToControlPanel(myDisplay);
        return myDisplay;
    }

    private void addToControlPanel(GridPane controlPanel) {
        List<Node> myControlList = myControls.getControls();
        for (Node control : myControlList) {
            controlPanel.getChildren().add(control);
        }
    }

}

	
	
	
	


package GUI;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;

import java.util.List;

public class GUI {
    private Controls myControls;
    private GridPane myDisplay;

    // public GUI(CellSociety mySociety, Stage controlStage) {
    public GUI() {
    }

    public GridPane init(GridPane display, String resource) {
    	myDisplay = display;
        SimulationControl mySimControl = new SimulationControl(myDisplay, resource);
        myControls = new Controls(mySimControl, resource);
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

	
	
	
	


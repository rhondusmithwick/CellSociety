package GUI;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;

import java.util.List;

public class GUI {
    private Controls myControls;
    private final GridPane myDisplay;

    // public GUI(CellSociety mySociety, Stage controlStage) {
    public GUI(GridPane display) {
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

	
	
	
	


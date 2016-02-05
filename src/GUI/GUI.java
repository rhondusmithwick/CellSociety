package GUI;

import Main.CellSociety;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

import java.util.List;

public class GUI {

    private CellSociety myCellSociety;
    private Controls myControls;
    private SimulationControl mySimControl;
    private GridPane myDisplay;

    // public GUI(CellSociety mySociety, Stage controlStage) {
    public GUI(CellSociety CS, GridPane display) {
        myDisplay = display;
        myCellSociety = CS;
    }

    public GridPane init() {

        mySimControl = new SimulationControl(myDisplay);
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

	
	
	
	


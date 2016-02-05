package GUI;

import Main.CellSociety;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

import java.util.List;
import java.util.ResourceBundle;

public class GUI {

	//public static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
    private CellSociety myCellSociety;
    private Controls myControls;
    private SimulationControl mySimControl;
    private GridPane myDisplay;
    //private ResourceBundle myResources;

    // public GUI(CellSociety mySociety, Stage controlStage) {
    public GUI(CellSociety CS, GridPane display) {
    	//myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "GUIstrings");
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

	
	
	
	


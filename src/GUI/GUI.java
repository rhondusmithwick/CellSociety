package GUI;

import javafx.scene.layout.GridPane;

public class GUI {

    public GUI() {
    }

    public GridPane init(GridPane display, String resource) {
        SimulationControl mySimControl = new SimulationControl(display, resource);
        Controls myControls = new Controls(mySimControl, resource);
        display.getChildren().addAll(myControls.getControls());
        return display;
    }

}

	
	
	
	


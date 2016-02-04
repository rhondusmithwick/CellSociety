package GUI;

import Main.CellSociety;

import java.awt.Dimension;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

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
	    
	    public GridPane init(){
	    	
	    	mySimControl = new SimulationControl(myDisplay);
	    	myControls = new Controls(mySimControl);
	    	
	    	addToControlPanel(myDisplay);
	    	
	    	return myDisplay;
	        
	    }
 
	    private void addToControlPanel(GridPane controlPanel) {
	    	
	    	ArrayList<Node> myControlList = myControls.getControls();
	       
	    	for (Node control: myControlList){
	        controlPanel.getChildren().add(control);
	    	}
	        
	    
	    }

	}

	
	
	
	


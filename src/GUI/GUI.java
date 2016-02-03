package GUI;

import Main.CellSociety;

import java.awt.Dimension;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class GUI {

	    public static final Dimension DEFAULT_SIZE = new Dimension(800, 600);
	   
	    private Scene myScene;

	    private Label outputLabel;
	    private TextField inputTextField;
	    
	    private Button myPlayPauseButton;
	    private Button mySkipForwardButton;
	    private Button mySpeedUpButton;
	    private Button mySlowDownButton;
	    
	    public GUI(CellSociety mySociety) {
	    	
	    	GridPane controlPanel = new GridPane();

	    	setControlLayout(controlPanel);
	    	setcontrolPanell(controlPanel);
	       
	        enableButtons();
	     
	        myScene = new Scene(controlPanel, DEFAULT_SIZE.width, DEFAULT_SIZE.height);
	        
	    }

	    /**
	     * Returns scene for this view so it can be added to stage.
	     */
	    public Scene getScene () {
	        return myScene;
	    }

	    /**
	     * Display given message as information in the GUI.
	     */
	    public void displayTextField (String message) {
	        inputTextField.setText(message);
	    }

	    /**
	     * Display given message as an error in the GUI.
	     */
	    public void showError (String message) {
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.setContentText(message);
	        alert.showAndWait();
	    }

	    // only enable buttons when useful to user
	    private void enableButtons () {
	       // myPlayPauseButton.setDisable(!mySim.isRunning);
	    }

	    private void setControlLayout (GridPane controlPanel) {
	    	//drop box at 0,0
	    	
	    	GridPane.setConstraints(inputTextField,0,1);
	    	GridPane.setConstraints(outputLabel,0,2);
	    	
	    	GridPane.setColumnSpan(inputTextField, 2);
	    	GridPane.setColumnSpan(outputLabel, 2);
	    	
	    	GridPane.setConstraints(myPlayPauseButton,0,3);
	    	GridPane.setConstraints(mySkipForwardButton,1,3);
	    	
	    	GridPane.setConstraints(mySlowDownButton,0,4);
	    	GridPane.setConstraints(mySpeedUpButton,1,4);
	    	 
	    }

	    // make user-entered URL/text field and back/next buttons
	    private void setcontrolPanell (GridPane controlPanel) {
	       
	        myPlayPauseButton = makeButton("Play/Pause", event -> playPause());
	        mySkipForwardButton = makeButton("Skip", event -> skip());
	        mySpeedUpButton = makeButton("Faster", event -> speedUp());
	        mySlowDownButton = makeButton("Slower", event -> slowDown());
	       
	        controlPanel.getChildren().addAll(myPlayPauseButton,mySkipForwardButton,mySpeedUpButton,mySlowDownButton);
	        controlPanel.getChildren().addAll(outputLabel,inputTextField);
	    
	    }


	    private Object slowDown() {
			// TODO Auto-generated method stub
			return null;
		}

		private Object speedUp() {
			// TODO Auto-generated method stub
			return null;
		}

		private Object skip() {
			// TODO Auto-generated method stub
			return null;
		}

		private Object playPause() {
			// TODO Auto-generated method stub
			return null;
		}

		// makes a button using either an image or a label
	    private Button makeButton (String property, EventHandler<ActionEvent> handler) {
	        
	        Button result = new Button();
	        
	        //String label = myResources.getString(property);
	        //result.setText(label);
	        result.setText(property);

	        result.setOnAction(handler);
	        return result;
	    }


	}

	
	
	
	


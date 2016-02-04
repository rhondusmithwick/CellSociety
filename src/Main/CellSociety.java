package Main;

import java.awt.Dimension;

import Cell.CellManager;
import Simulation.GameOfLifeSimulation;
import Simulation.Simulation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import GUI.GUI;

/**
 * Created by rhondusmithwick on 1/30/16.
 *
 * @author Rhondu Smithwick
 */
public class CellSociety {

	public static final Dimension DEFAULT_SIZE = new Dimension(800, 600);
    private GUI gui;
    private Scene myScene;
    
    public CellSociety() {

    }

    public void init(Stage primaryStage) {
        
    	GridPane display = new GridPane();
    	gui = new GUI(this, display);
    	display = gui.init();
    	
        myScene = new Scene(display, DEFAULT_SIZE.getWidth(), DEFAULT_SIZE.getHeight());
         
        primaryStage.setScene(myScene);
        primaryStage.setResizable(false);
        
    }
    
    /**
     * Returns scene for this view so it can be added to stage.
     */
    public Scene getScene () {
        return myScene;
    }

}

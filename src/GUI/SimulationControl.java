package GUI;

import java.util.ArrayList;

import Cell.CellManager;
import Simulation.GameOfLifeSimulation;
import Simulation.Simulation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;

public class SimulationControl {
	
	private static final String DEFAULT_TYPE = "GameOfLife";
    private CellManager cellManager;
    private Simulation sim;
	
	ObservableList<String> mySimulations = 
		    FXCollections.observableArrayList(
		        "Game Of Life",
		        "Segregation",
		        "Fire"
		    );
	
	public SimulationControl(GridPane display){
		
		
		 sim = startSimulation(DEFAULT_TYPE);
	     cellManager = createCellManager(DEFAULT_TYPE);
	     GridPane.setConstraints(cellManager,0,0);
	     display.getChildren().add(cellManager);

	     sim.setTheCells(cellManager.getCells());
	     sim.init();	
	     
	}
		
	public ObservableList<String> getSimulations(){
		return mySimulations;
	}

    public void displayTextField (String message) {
        //inputTextField.setText(message);
    }

	public void slowDown() {
		sim.decreaseRate();
	}

	public void speedUp() {
		sim.increaseRate();
	}

	public Object skip() {
		// TODO Auto-generated method stub
		return null;
	}

	public void playPause() {
		sim.playOrStop();
	}

		public Simulation startSimulation(Object o) {
			String simType = o.toString();
	        Simulation sim;
	        try {
	            Class c = Class.forName("Simulation." + simType + "Simulation");
	            sim = (Simulation) c.newInstance();
	        } catch (InstantiationException
	                | IllegalAccessException
	                | ClassNotFoundException e) {
	            System.out.println(e);
	            sim = new GameOfLifeSimulation();
	        }
	        return sim;
	    }
		
		
	    private CellManager createCellManager(String simType) {
	        CellManager cellManager = new CellManager();
	        cellManager.setGrid(sim.getGridWidth(), sim.getGridHeight(),
	                sim.getCellsPerRow(), sim.getCellsPerColumn());
	        cellManager.init(simType);
	        return cellManager;
	    }

		public void switchSimulation(Object o) {
			
			String simType = o.toString();
	        Simulation sim;
	        try {
	            Class c = Class.forName("Simulation." + simType + "Simulation");
	            sim = (Simulation) c.newInstance();
	        } catch (InstantiationException
	                | IllegalAccessException
	                | ClassNotFoundException e) {
	            System.out.println(e);
	            sim = new GameOfLifeSimulation();
	        }
	        
	        sim.setTheCells(cellManager.getCells());
		    sim.init();
	        
		}
		
		
		
	}
	


package GUI;

import java.io.File;

import Cell.CellManager;
import Simulation.GameOfLifeSimulation;
import Simulation.Simulation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.GridPane;

public class SimulationControl {

    private static final String DEFAULT_TYPE = "Fire";
    ObservableList<String> mySimulations =
            FXCollections.observableArrayList(
                    "GameOfLife",
                    "Segregation",
                    "Fire",
                    "PredatorPrey"
            );
    private String simType;
    private CellManager cellManager;
    private Simulation sim;
    private GridPane display;

    public SimulationControl(GridPane display) {
        simType = DEFAULT_TYPE;
        this.display = display;
        initNewSimulation();
    }

    public void initNewSimulation() {
        sim = startSimulation(simType);
        display.getChildren().remove(cellManager);
        cellManager = createCellManager(simType);
        GridPane.setConstraints(cellManager, 0, 0);
        GridPane.setRowSpan(cellManager, 6);
        display.getChildren().add(cellManager);

        sim.setTheCells(cellManager.getCells());
        sim.init();
    }

    public ObservableList<String> getSimulations() {
        return mySimulations;
    }

    public void slowDown() {
        sim.decreaseRate();
    }

    public void speedUp() {
        sim.increaseRate();
    }

    public void step() {
    	if (!sim.getPlaying()){
        sim.step();
    	}
    	else{
    		sim.stopLoop();
    		sim.step();
    	}
    }

    public void playPause() {
        sim.playOrStop();
    }

    public Simulation startSimulation(Object o) {
        simType = o.toString();
        //System.out.println(simType);
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

        simType = o.toString();
        Simulation sim;
        try {
            Class c = Class.forName("Simulation." + simType + "Simulation");
            sim = (Simulation) c.newInstance();
            initNewSimulation();
        } catch (InstantiationException
                | IllegalAccessException
                | ClassNotFoundException e) {
            System.out.println(e);
            sim = new GameOfLifeSimulation();
        }

        sim.setTheCells(cellManager.getCells());
        sim.init();

    }

	public void sizeChange(String string) {
		int mySize = Integer.parseInt(string);
		System.out.println("This size: " + mySize);
		
	}

	public void openFile(File file) {
		System.out.println("File Path: " + file.getPath());
		file.getPath();
	}


}



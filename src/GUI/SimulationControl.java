package GUI;

import Cell.CellManager;
import Simulation.FireSimulation;
import Simulation.Simulation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.GridPane;

import java.io.File;

public class SimulationControl {
    private static final String DEFAULT_SIM_TYPE = "Fire";

    private final GridPane display;
    private final ObservableList<String> mySimulations = FXCollections.observableArrayList(
            "GameOfLife",
            "Segregation",
            "Fire",
            "PredatorPrey"
    );
    private Simulation sim;
    private String simType;
    private CellManager cellManager;


    public SimulationControl(GridPane display, String resource) {
        simType = DEFAULT_SIM_TYPE;
        this.display = display;
        sim = getSimulation();
        initNewSimulation();
    }

    private void initNewSimulation() {
        display.getChildren().remove(cellManager);
        cellManager = createCellManager(simType);
        GridPane.setConstraints(cellManager, 0, 0);
        GridPane.setRowSpan(cellManager, 6);
        display.getChildren().add(cellManager);
        sim.setTheCells(cellManager.getCells());
        sim.init();
    }

    public void switchSimulation(Object o) {
        simType = o.toString();
        sim = getSimulation();
        initNewSimulation();
    }


    private Simulation getSimulation() {
        Simulation sim;
        try {
            String simClassName = "Simulation." + simType + "Simulation";
            Class c = Class.forName(simClassName);
            sim = (Simulation) c.newInstance();
        } catch (InstantiationException
                | IllegalAccessException
                | ClassNotFoundException e) {
            sim = new FireSimulation();
        }
        return sim;
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
        if (!sim.getPlaying()) {
            sim.step();
        } else {
            sim.stopLoop();
            sim.step();
        }
    }

    public void stop() {
        if (sim.getPlaying()) {
            sim.stopLoop();
        }
    }

    public void playPause() {
        sim.playOrStop();
    }


    private CellManager createCellManager(String simType) {
        CellManager cellManager = new CellManager();
        cellManager.setGrid(sim.getGridWidth(), sim.getGridHeight(),
                sim.getCellsPerRow(), sim.getCellsPerColumn());
        cellManager.init(simType);
        return cellManager;
    }

    // TODO
    public void sizeChange(String string) {
        sim = getSimulation();
        sim.resetCellSize(Integer.parseInt(string));
        initNewSimulation();

        //int mySize = ;
        //System.out.println("This size: " + mySize);
    }

    // TODO
    public void openFile(File file) {
        System.out.println("File Path: " + file.getPath());
        file.getPath();
    }
}



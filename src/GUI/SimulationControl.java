package GUI;

import Cell.CellManager;
import Cell.FireCell;
import Simulation.Simulation;
import Simulation.FireSimulation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.GridPane;

import java.io.File;

class SimulationControl {

    private static final String DEFAULT_SIM_TYPE = "Fire";

    private final ObservableList<String> mySimulations = FXCollections.observableArrayList(
            "GameOfLife",
            "Segregation",
            "Fire",
            "PredatorPrey"
    );

    private String simType;
    private CellManager cellManager;
    private Simulation sim;
    private final GridPane display;

    public SimulationControl(GridPane display) {
        simType = DEFAULT_SIM_TYPE;
        this.display = display;
        initNewSimulation();
    }

    private void initNewSimulation() {
        sim = getSimulation();
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
        if (!sim.getPlaying()) {
            sim.step();
        } else {
            sim.stopLoop();
            sim.step();
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

    public void switchSimulation(Object o) {
        simType = o.toString();
        initNewSimulation();
    }


    private Simulation getSimulation() {
        Simulation sim;
        try {
            Class c = Class.forName("Simulation." + simType + "Simulation");
            sim = (Simulation) c.newInstance();
        } catch (InstantiationException
                | IllegalAccessException
                | ClassNotFoundException e) {
            System.out.println(e);
//            sim = new GameOfLifeSimulation();
            sim = new FireSimulation();
        }
        return sim;
    }

    // TODO
    public void sizeChange(String string) {
        int mySize = Integer.parseInt(string);
        System.out.println("This size: " + mySize);
    }

    // TODO
    public void openFile(File file) {
        System.out.println("File Path: " + file.getPath());
        file.getPath();
    }
}



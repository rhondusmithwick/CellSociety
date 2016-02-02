package Main;

import Cell.CellManager;
import Simulation.GameOfLifeSimulation;
import Simulation.SegregationSimulation;
import Simulation.Simulation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

/**
 * Created by rhondusmithwick on 1/30/16.
 *
 * @author Rhondu Smithwick
 */
class CellSociety {

    private static final String SIM_TYPE = "GameOfLife";
    private final Group group;
    private CellManager cellManager;
    private Simulation sim;

    public CellSociety() {
        group = new Group();
    }

    public void init(Stage primaryStage) {
        cellManager = new CellManager();
        sim = createSimulation(SIM_TYPE);

        cellManager.setGrid(sim.getGridWidth(), sim.getGridHeight(),
                sim.getCellsPerRow(), sim.getCellsPerColumn());
        cellManager.init(SIM_TYPE);
        group.getChildren().add(cellManager);

        sim.setTheCells(cellManager.getCells());
        sim.init();

        Scene scene = new Scene(group, cellManager.getWidth(), cellManager.getHeight());
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        
        scene.setOnMouseClicked(e -> sim.playOrStop());
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
    }

    private void handleKeyInput(KeyCode code) {
        switch (code) {
            case SPACE:
                sim.playOrStop();
                break;
            default:
        }
    }

    private Simulation createSimulation(String simType) {
        Simulation sim;
        switch (simType) {
            case "GameOfLife":
                //cellManager.init("GameOfLife");
                sim = new GameOfLifeSimulation();
                break;
            case "Segregation":
                //cellManager.init("Segregation");
                sim = new SegregationSimulation();
                break;
            default:
                cellManager.init("GameOfLife");
                sim = new GameOfLifeSimulation();
        }
        return sim;
    }
}

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

    private final Group group;
    private final CellManager cellManager;

    private Simulation sim;

    public CellSociety() {
        group = new Group();
        cellManager = new CellManager(750, 750, 150, 150);
        group.getChildren().add(cellManager);

    }

    public void init(Stage primaryStage) {
        sim = createSimulation("GameOfLife");
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
            case "GameOfLife" :
                cellManager.init("GameOfLife");
                sim = new GameOfLifeSimulation(cellManager.getCells());
                break;
            case "Segregation":
                cellManager.init("Segregation");
                sim = new SegregationSimulation(cellManager.getCells());
                break;
            default:
                cellManager.init("GameOfLife");
                sim = new GameOfLifeSimulation(cellManager.getCells());
        }
        return sim;
    }
}

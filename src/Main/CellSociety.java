package Main;

import Cell.CellManager;
import Simulation.GameOfLifeSimulation;
import Simulation.Simulation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by rhondusmithwick on 1/30/16.
 *
 * @author Rhondu Smithwick
 */
class CellSociety {

    private final Group group;
    private final CellManager cellManager;

    public CellSociety() {
        group = new Group();
        cellManager = new CellManager(500, 500, 50, 50);
        group.getChildren().add(cellManager);

    }

    public void init(Stage primaryStage) {
        Simulation sim = createSimulation("GameOfLife");
        sim.init();
        Scene scene = new Scene(group, cellManager.getWidth(), cellManager.getHeight());
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        sim.beginLoop();
    }


    private Simulation createSimulation(String simType) {
        Simulation sim;
        switch (simType) {
            default:
                cellManager.init("GameOfLife");
                sim = new GameOfLifeSimulation(cellManager.getCells());
        }
        return sim;
    }
}

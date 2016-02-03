package Main;

import Cell.CellManager;
import Simulation.GameOfLifeSimulation;
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
public class CellSociety {

    private static final String SIM_TYPE = "GameOfLife";
    private final Group group;
    private CellManager cellManager;
    private Simulation sim;

    public CellSociety() {
        group = new Group();
    }

    public void init(Stage primaryStage) {
        sim = createSimulation(SIM_TYPE);
        cellManager = createCellManager(SIM_TYPE);

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
            case UP:
                sim.increaseRate();
                break;
            case DOWN:
                sim.decreaseRate();
                break;
            case SHIFT:
                sim.resetRate();
                break;
            default:
        }
    }

    private Simulation createSimulation(String simType) {
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
}

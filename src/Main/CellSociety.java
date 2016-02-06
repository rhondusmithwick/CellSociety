package Main;

import GUI.GUI;
import GUI.SimulationControl;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.awt.*;

/**
 * The class for Cell Society that holds a GUI.
 *
 * @author Rhondu Smithwick
 */
class CellSociety {
    /**
     * The dimensions of the Scene.
     */
    private static final Dimension DEFAULT_SIZE = new Dimension(810, 600);

    /**
     * The empty constructor.
     */
    CellSociety() {
    }

    /**
     * Create the scene and set the primaryStage to it.
     *
     * @param primaryStage the primary stage
     * @param resource     the filename of the GUI properties
     */
    void init(Stage primaryStage, String resource) {
        GridPane display = createDisplay(resource);
        Scene myScene = new Scene(display, DEFAULT_SIZE.getWidth(), DEFAULT_SIZE.getHeight());
        primaryStage.setScene(myScene);
        primaryStage.setResizable(false);
    }


    private GridPane createDisplay(String resource) {
        GridPane display = new GridPane();
        SimulationControl mySimControl = new SimulationControl(display, resource);
        GUI gui = new GUI(mySimControl, resource);
        display.getChildren().addAll(gui.getControls());
        return display;
    }
}

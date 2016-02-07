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
    private static final Dimension DEFAULT_SIZE = new Dimension(870, 650);

    /**
     * The empty constructor.
     */
    CellSociety() {
    }

    /**
     * Create the scene and set the primaryStage to it.
     *
     * @param primaryStage the primary stage
     */
    void init(Stage primaryStage) {
        GridPane display = createDisplay();
        Scene myScene = new Scene(display, DEFAULT_SIZE.getWidth(), DEFAULT_SIZE.getHeight());
        primaryStage.setScene(myScene);
        primaryStage.setResizable(false);
    }


    private GridPane createDisplay() {
        GridPane display = new GridPane();
        SimulationControl mySimControl = new SimulationControl(display);
        display.setHgap(10);
        display.setVgap(10);
        GUI gui = new GUI(mySimControl);
        display.getChildren().addAll(gui.getControls());
        return display;
    }
}

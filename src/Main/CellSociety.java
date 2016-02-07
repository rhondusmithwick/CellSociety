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
<<<<<<< HEAD
    private static final Dimension DEFAULT_SIZE = new Dimension(870, 650);
=======
<<<<<<< HEAD
    private static final Dimension DEFAULT_SIZE = new Dimension(800, 600);
=======
    private static final Dimension DEFAULT_SIZE = new Dimension(870, 600);
>>>>>>> 4ef5c6b647464fdf89096beca1aeaa8c29702f5f
>>>>>>> f796b143fce359180f96fe44a9039cf1718a05a9

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
<<<<<<< HEAD
        display.setHgap(10);
        display.setVgap(10);
        SimulationControl mySimControl = new SimulationControl(display, "GUIstrings");
=======
        SimulationControl mySimControl = new SimulationControl(display);
        display.setHgap(10);
        display.setVgap(10);
>>>>>>> 4ef5c6b647464fdf89096beca1aeaa8c29702f5f
        GUI gui = new GUI(mySimControl);
        display.getChildren().addAll(gui.getControls());
        return display;
    }
}

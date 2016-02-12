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
     * Stylesheet option.
     */
    private static final String STYLESHEET = "heart.css";
    /**
     * The dimensions of the Scene.
     */
    private static final Dimension DEFAULT_SIZE = new Dimension(1200, 800);

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
        myScene.getStylesheets().add(STYLESHEET);
        primaryStage.setScene(myScene);
        primaryStage.setResizable(false);
    }

    /**
     * Sets up displays and initialized cell society and gui.
     *
     * @return display
     * the edited GridPane with necessary objects
     */
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

package Main;

import GUI.GUI;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.*;

/**
 * Created by rhondusmithwick on 1/30/16.
 *
 * @author Rhondu Smithwick
 */
class CellSociety {

//    private static final Dimension DEFAULT_SIZE = new Dimension(810, 600);

    Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
    int width = (int) (primaryScreenBounds.getWidth() / 1.5);
    int height = (int) (primaryScreenBounds.getHeight() / 1.5);

    private final Dimension DEFAULT_SIZE = new Dimension(width, height);
    private Scene myScene;

    public CellSociety() {
    }

    public void init(Stage primaryStage, String resource) {
        GridPane display = new GridPane();
        GUI gui = new GUI();
        display = gui.init(display,resource);
        myScene = new Scene(display, DEFAULT_SIZE.getWidth(), DEFAULT_SIZE.getHeight());
        primaryStage.setScene(myScene);
        primaryStage.setResizable(false);
    }

    /**
     * Returns scene for this view so it can be added to stage.
     */
    public Scene getScene() {
        return myScene;
    }

}

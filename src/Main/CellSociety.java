package Main;

import GUI.GUI;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.awt.*;

/**
 * Created by rhondusmithwick on 1/30/16.
 *
 * @author Rhondu Smithwick
 */
class CellSociety {
    private static final Dimension DEFAULT_SIZE = new Dimension(800, 600);

    private Scene myScene;

    public CellSociety() {
    }

    public void init(Stage primaryStage) {
        GridPane display = new GridPane();
        GUI gui = new GUI(display);
        display = gui.init();
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

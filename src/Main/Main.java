package Main;

import Cell.Cell;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by rhondusmithwick on 1/28/16.
 *
 * @author Rhondu Smithwick
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    /**
     * Start the application.
     *
     * @param primaryStage the primary stage
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Cell Society");
        CellSociety cs = new CellSociety();
        cs.init(primaryStage);
        primaryStage.show();
    }
}

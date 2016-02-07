package Main;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by rhondusmithwick on 1/28/16.
 *
 * @author Rhondu Smithwick
 */
public class Main extends Application {

    /**
     * A classic main method.
     *
     * @param args the standard string array
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initializes cell society and shows the stage.
     *
     * @param primaryStage the primary screen containing the scene
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Cell Society");
        CellSociety cs = new CellSociety();
        cs.init(primaryStage);
        primaryStage.show();
    }
}

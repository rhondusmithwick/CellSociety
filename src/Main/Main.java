package Main;

import javafx.application.Application;
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

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Cell Society");
        CellSociety cs = new CellSociety();
        cs.init(primaryStage);
        primaryStage.show();
    }
}

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
        primaryStage.setTitle("Cell.Cell Society");
        Grid grid = new Grid(500, 500);
        grid.addCell(new Cell(5, 100, 400, 400));
        Scene scene = new Scene(grid, grid.getHeight(), grid.getWidth());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

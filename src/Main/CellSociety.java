package Main;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by rhondusmithwick on 1/30/16.
 *
 * @author Rhondu Smithwick
 */
public class CellSociety {
    private final Group group;
    private final Grid grid;
    public CellSociety() {
        group = new Group();
        grid = new Grid();
        group.getChildren().add(grid);

    }

    public void init(Stage primaryStage) {
        grid.init(500, 500);
        Scene scene = new Scene(group, 500, 500);
        primaryStage.setScene(scene);
    }
}

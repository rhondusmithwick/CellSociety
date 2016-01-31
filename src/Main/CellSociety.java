package Main;

import Cell.CellManager;
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
    private final CellManager cellManager;

    public CellSociety() {
        group = new Group();
        cellManager = new CellManager(500, 500, 10, 10);
        group.getChildren().add(cellManager);

    }

    public void init(Stage primaryStage) {
        cellManager.init("");
        Scene scene = new Scene(group, cellManager.getWidth(), cellManager.getHeight());
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
    }


}

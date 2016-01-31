package Main;

import Cell.Cell;
import javafx.scene.Group;

/**
 * Created by rhondusmithwick on 1/30/16.
 *
 * @author Rhondu Smithwick
 */
public class Grid extends Group {
    private final double width;
    private final double height;

    public Grid(double width, double height) {
        super();
        this.width = width;
        this.height = height;
    }

    public void addCell(Cell c) {
        this.getChildren().add(c);
    }

    public double getWidth() {
        return width;
    }


    public double getHeight() {
        return height;
    }
}

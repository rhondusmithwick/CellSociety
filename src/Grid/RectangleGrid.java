package Grid;

import Cell.Cell;
import javafx.scene.shape.Rectangle;

/**
 * Created by rhondusmithwick on 2/13/16.
 *
 * @author Rhondu Smithwick
 */
public class RectangleGrid extends Grid {

    @Override
    public void init(String cellType) {
        double cellWidth = ((double) getGridWidth() / getCellsPerRow());
        double cellHeight = ((double) getGridHeight()) / getCellsPerColumn();
        for (int row = 0; row < getCellsPerColumn(); row++) {
            for (int column = 0; column < getCellsPerRow(); column++) {
                double x = column * cellWidth;
                double y = row * cellHeight;
                Rectangle rectangle = new Rectangle(x, y, cellWidth, cellHeight);
                CellShape shape = new RectangleShape(rectangle);
                Cell myCell = createCell(shape, cellType, row, column);
                add(myCell);
            }
        }
        populateNeighbors();
    }
}

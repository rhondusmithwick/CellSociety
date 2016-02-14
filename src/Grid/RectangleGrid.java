package Grid;

import Cell.Cell;

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
        for (int row = 0; row < getCellsPerRow(); row++) {
            for (int column = 0; column < getCellsPerColumn(); column++) {
                double x = column * cellWidth;
                double y = row * cellHeight;
                CellShape shape = new RectangleShape(x, y, cellWidth, cellHeight);
                Cell myCell = createCell(shape, cellType, row, column);
                add(myCell);
            }
        }
        populateNeighbors();
    }
}

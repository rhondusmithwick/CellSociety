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
                Cell myCell = createCell(cellType, cellWidth, cellHeight, row, column);
                addCell(row, column, myCell);
                group.getChildren().add(myCell.getShape());
            }
        }
        populateNeighbors();
    }

    @Override
    public CellShape getShape() {
        return new RectangleShape();
    }
}

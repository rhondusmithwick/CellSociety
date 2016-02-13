package Grid;

import Cell.Cell;

/**
 * Created by rhondusmithwick on 2/13/16.
 *
 * @author Rhondu Smithwick
 */
public class TriangleGrid extends Grid {

    @Override
    public void init(String cellType) {
        double cellWidth = ((double) getGridWidth() / getCellsPerRow());
        double cellHeight = ((double) getGridHeight()) / getCellsPerColumn();
        for (int row = 0; row < getCellsPerRow(); row++) {
            Triangle prevTriangle = null;
            for (int column = 0; column < getCellsPerColumn(); column++) {
                double x = column * cellWidth;
                double y = row * cellHeight;
                Triangle triangle = new Triangle(x, y, cellWidth, cellHeight, prevTriangle);
                CellShape shape = new TriangleShape(triangle);
                Cell myCell = createCell(shape, cellType, row, column);
                addCell(row, column, myCell);
                group.getChildren().add(myCell.getShape());
                prevTriangle = triangle;
            }
        }
        populateNeighbors();
    }

}

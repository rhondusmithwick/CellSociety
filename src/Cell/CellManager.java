package Cell;

import javafx.scene.Group;

import java.util.Collection;
import java.util.LinkedList;

/**
 * The class for the CellManager, which contains all the cells.
 * Created by rhondusmithwick on 1/30/16.
 *
 * @author Rhondu Smithwick
 */
public class CellManager extends Group {
    private final Collection<Cell> theCells;
    private Cell[][] grid;
    private int gridWidth;
    private int gridHeight;
    private int cellsPerRow;
    private int cellsPerColumn;

    public CellManager() {
        super();
        theCells = new LinkedList<>();
    }

    private static Cell createCell(String cellType, double cellWidth, double cellHeight, int row, int column) {
        Cell myCell;
        try {
            Class cellClass = Class.forName("Cell." + cellType + "Cell");
            myCell = (Cell) cellClass.newInstance();
        } catch (InstantiationException
                | IllegalAccessException
                | ClassNotFoundException e) {
            myCell = new GameOfLifeCell();
        }
        double x = row * cellWidth;
        double y = column * cellHeight;
        myCell.init(cellWidth, cellHeight, x, y, row, column);
        return myCell;
    }

    public void setGrid(int gridWidth, int gridHeight, int cellsPerRow, int cellsPerColumn) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.cellsPerRow = cellsPerRow;
        this.cellsPerColumn = cellsPerColumn;
        grid = new Cell[cellsPerRow][cellsPerColumn];
    }

    private boolean inBounds(int r, int c) {
        return (r >= 0)
                && (r < cellsPerRow)
                && (c >= 0)
                && (c < cellsPerColumn);
    }

    public void init(String cellType) {
        double cellWidth = ((double) gridWidth) / cellsPerRow;
        double cellHeight = ((double) gridHeight) / cellsPerColumn;
        for (int row = 0; row < cellsPerRow; row++) {
            for (int column = 0; column < cellsPerColumn; column++) {
                Cell myCell = createCell(cellType, cellWidth, cellHeight, row, column);
                grid[row][column] = myCell;
                this.getChildren().add(myCell);
                theCells.add(myCell);
            }
        }
        populateNeighbors();
    }

    private void populateNeighbors() {
        for (int r = 0; r < cellsPerRow; r++) {
            for (int c = 0; c < cellsPerColumn; c++) {
                traverseNeighbors(r, c);
            }
        }
    }


    private void traverseNeighbors(int r, int c) {
        Cell myCell = grid[r][c];
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (!(j == 0 && i == 0)) {
                    int neighborR = r + i;
                    int neighborC = c + j;
                    if (inBounds(neighborR, neighborC)) {
                        Cell neighbor = grid[neighborR][neighborC];
                        myCell.addNeighbor(neighbor);
                    }
                }
            }
        }
    }

    public Collection<Cell> getCells() {
        return theCells;
    }
}

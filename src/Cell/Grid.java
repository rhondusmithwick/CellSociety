package Cell;

import javafx.scene.Group;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

/**
 * The class for the Grid, which contains all the cells.
 * Created by rhondusmithwick on 1/30/16.
 *
 * @author Rhondu Smithwick
 */
public class Grid extends Group {
    /**
     * The cells.
     */
    private final Collection<Cell> theCells = new LinkedList<>();
    /**
     * The internal 2D array for keeping track of neighbors.
     */
    private Cell[][] grid;
    /**
     * This grid's width.
     */
    private int gridWidth;
    /**
     * This grid's height.
     */
    private int gridHeight;
    /**
     * This grid's number of cells per row.
     */
    private int cellsPerRow;
    /**
     * This grid's number of cells per column.
     */
    private int cellsPerColumn;

    private EdgeType edgeType;

    /**
     * Construct a cellmanager.
     */
    public Grid() {
        super();
    }

    /**
     * Create a cell of type cellType.
     *
     * @param cellType   the type of cell
     * @param cellWidth  the cell's width
     * @param cellHeight the cell's height
     * @param row        the cell's row
     * @param column     the cell's column
     * @return a cell of type cellType OR a Fire cell if exception
     */
    private static Cell createCell(String cellType, double cellWidth,
                                   double cellHeight, int row, int column) {
        Cell myCell;
        try {
            Class cellClass = Class.forName("Cell." + cellType + "Cell");
            myCell = (Cell) cellClass.newInstance();
        } catch (InstantiationException
                | IllegalAccessException
                | ClassNotFoundException e) {
            myCell = new FireCell();
        }
        double x = row * cellWidth;
        double y = column * cellHeight;
        myCell.init(cellWidth, cellHeight, x, y, row, column);
        return myCell;
    }

    /**
     * Set the internal array and grid.
     *
     * @param gridWidth      the grid's width.
     * @param gridHeight     the grid's height.
     * @param cellsPerRow    the number of cells per row
     * @param cellsPerColumn the number of cells per column
     */
    public void setGrid(int gridWidth, int gridHeight, int cellsPerRow, int cellsPerColumn, EdgeType edgeType) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.cellsPerRow = cellsPerRow;
        this.cellsPerColumn = cellsPerColumn;
        this.edgeType = edgeType;
        grid = new Cell[cellsPerRow][cellsPerColumn];
    }

    /**
     * Initialize this cell manager.
     *
     * @param cellType this Cell Manager's cell type.
     */
    public void init(String cellType) {
        double cellWidth = ((double) gridWidth) / cellsPerRow;
        double cellHeight = ((double) gridHeight) / cellsPerColumn;
        for (int row = 0; row < cellsPerRow; row++) {
            for (int column = 0; column < cellsPerColumn; column++) {
                Cell myCell = createCell(cellType, cellWidth, cellHeight, row, column);
                grid[row][column] = myCell;
                this.getChildren().add(myCell.getShape());
                theCells.add(myCell);
            }
        }
        populateNeighbors();
    }

    /**
     * Populate neighbors of all cells.
     */
    private void populateNeighbors() {
        for (int r = 0; r < cellsPerRow; r++) {
            for (int c = 0; c < cellsPerColumn; c++) {
                traverseNeighbors(r, c);
            }
        }
    }


    /**
     * Populate the neighbors of a single cell.
     *
     * @param r this cell's row
     * @param c this cell's column
     */
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
                    } else if (edgeType == EdgeType.TORODIAL) {
                        addTorodialNeighbor(myCell, neighborR, neighborC);
                    }
                }
            }
        }
    }

    private void addTorodialNeighbor(Cell myCell, int neighborR, int neighborC) {
        if (!checkBound(neighborR, cellsPerRow)) {
            neighborR = getTorodialVal(neighborR, cellsPerRow);

        }
        if (!checkBound(neighborC, cellsPerColumn)) {
            neighborC = getTorodialVal(neighborC, cellsPerColumn);
        }
        Cell neighbor = grid[neighborR][neighborC];
        myCell.addNeighbor(neighbor);
    }

    private int getTorodialVal(int neighborVal, int boundaryVal) {
        if (neighborVal < 0) {
            return boundaryVal - 1;
        }
        return 0;
    }

    /**
     * Test if this is in the grid.
     *
     * @param r the tested row
     * @param c the tested column
     * @return triue if in the grid
     */
    private boolean inBounds(int r, int c) {
        return checkBound(r, cellsPerRow)
                && checkBound(c, cellsPerColumn);
    }


    private boolean checkBound(int val, int boundaryVal) {
        return (val >= 0)
                && (val < boundaryVal);
    }

    /**
     * Get this Cell Manager's cells.
     *
     * @return this cell manager's cells
     */
    public Collection<Cell> getCells() {
        return Collections.unmodifiableCollection(theCells);
    }


    public enum EdgeType {
        NORMAL, TORODIAL
    }
}

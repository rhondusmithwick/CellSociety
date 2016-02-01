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
    /**
     * The grid, whose purpose is to allow access for a cell's neighbors.
     */
    private final Cell[][] grid;
    /**
     * the width of the grid.
     */
    private final int gridWidth;
    /**
     * The height of the grid.
     */
    private final int gridHeight;

    /**
     * The number of cells per row.
     */
    private final int cellsPerRow;

    /**
     * The number of cells per column.
     */
    private final int cellsPerColumn;

    private final Collection<Cell> theCells;

    /**
     * Create a new CellManager.
     *
     * @param gridWidth      the width of the grid
     * @param gridHeight     the height of the grid
     * @param cellsPerRow    the number of cells per row
     * @param cellsPerColumn the number of cells per column
     */
    public CellManager(int gridWidth, int gridHeight, int cellsPerRow, int cellsPerColumn) {
        super();
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.cellsPerRow = cellsPerRow;
        this.cellsPerColumn = cellsPerColumn;
        grid = new Cell[cellsPerRow][cellsPerColumn];
        theCells = new LinkedList<>();
    }

    /**
     * Create a cell of type Celltype.
     *
     * @param cellType   the subclass of Cell to be created
     * @param cellWidth  the width of the cell
     * @param cellHeight the height of the cell
     * @param r          this cell's row position
     * @param c          this cell's column position
     * @return a cell with the specified parameters
     */
    private static Cell createCell(String cellType, int cellWidth, int cellHeight, int r, int c) {
        Cell myCell;
        switch (cellType) {
            case "Segregation":
                myCell = new SegregationCell();
                break;
//            case "FIRE":
//                myCell = new FireCell();
//                break;
//            case "PredatorPrey":
//                myCell = new PredatorPreyCell();
//                break;
            case "GameOfLife":
                myCell = new GameOfLifeCell();
                break;
            default:
                myCell = new GameOfLifeCell();
                break;
        }
        myCell.setWidth(cellWidth);
        myCell.setHeight(cellHeight);
        int x = r * cellWidth;
        int y = c * cellHeight;
        myCell.setX(x);
        myCell.setY(y);
        return myCell;
    }

    private boolean inBounds(int r, int c) {
        return (r >= 0)
                && (r < cellsPerRow)
                && (c >= 0)
                && (c < cellsPerColumn);
    }

    /**
     * Create all the cells and populate their neighbors list.
     *
     * @param cellType the type of cell to be created
     */
    public void init(String cellType) {
        int cellWidth = gridWidth / cellsPerRow;
        int cellHeight = gridHeight / cellsPerColumn;
        for (int r = 0; r < cellsPerRow; r++) {
            for (int c = 0; c < cellsPerColumn; c++) {
                Cell myCell = createCell(cellType, cellWidth, cellHeight, r, c);
                myCell.init();
                grid[r][c] = myCell;
                this.getChildren().add(myCell);
                theCells.add(myCell);
            }
        }
        populateNeighbors();
    }

    /**
     * Populate the neighbors list of each cell.
     */
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

    /**
     * Clear this cellManager.
     */
    public void clear() {
        this.getChildren().clear();
    }


    /**
     * Return the width of the grid.
     *
     * @return the width of the grid
     */
    public int getWidth() {
        return gridWidth;
    }

    /**
     * Return the height of the grid.
     *
     * @return the height of the grid
     */
    public int getHeight() {
        return gridHeight;
    }

    public Collection<Cell> getCells() {
        return theCells;
    }
}

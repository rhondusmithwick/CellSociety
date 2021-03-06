package Grid;

import Cell.Cell;
import Cell.FireCell;
import javafx.scene.Group;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

/**
 * The class for the Grid, which contains all the cells.
 * Created by rhondusmithwick on 1/30/16.
 *
 * @author Rhondu Smithwick
 */
public abstract class Grid {
    private final Group group = new Group();
    private Map<List<Integer>, Cell> grid = new HashMap<>();
  //  private String shapeType;
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
     * Set the internal array and grid.
     *
     * @param gridWidth      the grid's width.
     * @param gridHeight     the grid's height.
     * @param cellsPerRow    the number of cells per row
     * @param cellsPerColumn the number of cells per column
     */
    public final void setGrid(int gridWidth, int gridHeight, int cellsPerRow, int cellsPerColumn, EdgeType edgeType) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.cellsPerRow = cellsPerRow;
        this.cellsPerColumn = cellsPerColumn;
        this.edgeType = edgeType;
    }

    /**
     * Initialize this cell manager.
     */

    public void init(Collection<Cell> theCells) {
    	grid = new HashMap<>();
    	for(Cell c: theCells){
    		add(c);
    	}
    	populateNeighbors();
    }

    public abstract void init(String cellType);

    /**
     * Populate neighbors of all cells.
     */
    final void populateNeighbors() {
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
        Cell myCell = getCell(r, c);
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (!(j == 0 && i == 0)) {
                    int neighborR = r + i;
                    int neighborC = c + j;
                    if (inBounds(neighborR, neighborC)) {
                        Cell neighbor = getCell(neighborR, neighborC);
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
        Cell neighbor = getCell(neighborR, neighborC);
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
     * @return true if in the grid
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
    public final Collection<Cell> getCells() {
        return Collections.unmodifiableCollection(grid.values());
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

    public Cell createCell(CellShape shape, String cellType, int row, int column) {
        Cell myCell;
        try {
            Class<?> cellClass = Class.forName("Cell." + cellType + "Cell");
            Constructor<?> constructor = cellClass.getConstructor(CellShape.class, int.class, int.class);
            myCell = (Cell) constructor.newInstance(shape, row, column);
        } catch (Exception e) {
            myCell = new FireCell(shape, row, column);
        }
        return myCell;
    }

    public final Group getGroup() {
        return group;
    }

    final int getCellsPerRow() {
        return cellsPerRow;
    }

    final int getGridWidth() {
        return gridWidth;
    }

    final int getGridHeight() {
        return gridHeight;
    }

    final int getCellsPerColumn() {
        return cellsPerColumn;
    }

    final void add(Cell c) {
        addCellToGrid(c.getRow(), c.getColumn(), c);
        group.getChildren().add(c.getGroup());
    }


    private void addCellToGrid(int r, int c, Cell cell) {
        grid.put(getKey(r, c), cell);
    }

    private Cell getCell(int r, int c) {
        return grid.get(getKey(r, c));
    }

    private List<Integer> getKey(int r, int c) {
        return asList(r, c);
    }


    public final void changeEdgeType(EdgeType edgeType) {
        this.edgeType = edgeType;
        populateNeighbors();
    }

    public enum EdgeType {
        NORMAL, TORODIAL
    }
}

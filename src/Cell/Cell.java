package Cell;

import Grid.CellShape;
import Grid.RectangleShape;
import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/**
 * The base class for a cell.
 *
 * @author Rhondu Smithwick
 */
public abstract class Cell {
    private final Collection<Cell> neighbors = new LinkedList<>();
	private final Map<Enum, Paint> visualMap = new HashMap<>();
    private final Group group = new Group();
    /**
     * This cell's shape.
     */
    private CellShape shape;
    protected Map<String, Object> cellState;
    /**
     * This cell's row in the grid.
     */
    private int row;
    /**
     * This cell's column in the grid.
     */
    private int column;

    /**
     * Construct a cell.
     */
    Cell() {
        super();
    }


	public void saveCellState() {

        cellState = new HashMap<>();
        if(shape == null){
        	shape= new RectangleShape(0, 0, 0, 0);
        }
        cellState.put("cellWidth", shape.getWidth());
        cellState.put("cellHeight", shape.getHeight());
        cellState.put("x", shape.getX());
        cellState.put("y", shape.getY());
        cellState.put("row", row);
        cellState.put("column", column);
        saveTypeCellState();
    }

    abstract void saveTypeCellState();

    /**
     * Initialize this cell with these parameters.
     *
     * @param row    the cell's row
     * @param column the cell's column
     */

    public Cell(CellShape shape, int row, int column) {
    	init(shape, row, column);
    }
    public void init(CellShape shape, int row, int column) {
        this.shape = shape;
        group.getChildren().add(shape.getMyShape());
        this.row = row;
        this.column = column;
    }


    /**
     * Remove this cell's diagonal neighbors.
     */
    public final void removeDiagonals() {
        Iterator<Cell> iter = neighbors.iterator();
        while (iter.hasNext()) {
            Cell neighbor = iter.next();
            if (checkDiagonal(neighbor)) {
                iter.remove();
            }
        }
    }

    /**
     * Change this cell's state.
     */
    public abstract void changeState();

    /**
     * Add a neighbor to this cell's neighbor list.
     *
     * @param neighbor the neighbor to add
     */
    public final void addNeighbor(Cell neighbor) {
        neighbors.add(neighbor);
    }

    /**
     * Update this cell at a step.
     */
    public abstract void handleUpdate();

    /**
     * Get this cell's row.
     *
     * @return the row of this cell
     */
    public final int getRow() {
        return row;
    }

    /**
     * Get this cell's column.
     *
     * @return this cell's column
     */
    public final int getColumn() {
        return column;
    }

    /**
     * Set this cell's visuals.
     *
     * @param visuals this cell's visuals.
     */
    public abstract void setVisuals(Paint... visuals);


    final boolean checkDiagonal(Cell neighbor) {
        int rowDiff = Math.abs(neighbor.getRow() - getRow());
        int columnDiff = Math.abs(neighbor.getColumn() - getColumn());
        return (rowDiff >= 1)
                && (columnDiff >= 1);
    }


    final void addToVisualMap(Enum state, Paint visual) {
        visualMap.put(state, visual);
    }

    final Paint getVisual(Enum state) {
        return visualMap.get(state);
    }

    public Map<String, Object> getCellState() {
        return cellState;
    }

    /**
     * Get this cell's neighbors.
     *
     * @return this cell's neighbors.
     */
    Collection<Cell> getNeighbors() {
        return neighbors;
    }

    /**
     * Get this cell's shape.
     *
     * @return this cell's shape
     */
    final public Group getGroup() {
        return group;
    }

    /**
     * Set this cell's background.
     *
     * @param value this cell's new background
     */

    public void setFill(Paint value) {
        shape.setFill(value);
    }

    /**
     * Set this cell's outline.
     *
     * @param value this cell's new outline
     */
    final void setStroke(Paint value) {
        shape.setStroke(value);
    }
}

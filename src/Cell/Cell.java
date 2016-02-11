package Cell;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * The base class for a cell.
 *
 * @author Rhondu Smithwick
 */
public abstract class Cell {
    /**
     * This cell's neighbors.
     */
    private final Collection<Cell> neighbors = new LinkedList<>();
    /**
     * This cell's shape.
     */
    private final Rectangle shape = new Rectangle();
    /**
     * This cell's row in the grid.
     */
    private int row;
    /**
     * This cell's column in the grid.
     */
    private int column;

    private State state;
    private Mark mark;


    /**
     * Construct a cell.
     */
    Cell() {
        super();
    }

    /**
     * Initialize this cell with these parameters.
     *
     * @param cellWidth  the cell's width
     * @param cellHeight the cell's height
     * @param x          the cell's x-coordinate
     * @param y          the cell's y-coordinate
     * @param row        te cell's row
     * @param column     the cell's column
     */
    public final void init(double cellWidth, double cellHeight, double x, double y, int row, int column) {
        shape.setWidth(cellWidth);
        shape.setHeight(cellHeight);
        shape.setX(x);
        shape.setY(y);
        setRow(row);
        setColumn(column);
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
     * Set this cell's row.
     *
     * @param row this cell's new row
     */
    private void setRow(int row) {
        this.row = row;
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
     * Set this cell's column.
     *
     * @param column this cell's new column
     */
    private void setColumn(int column) {
        this.column = column;
    }


    /**
     * Set this cell's visuals.
     *
     * @param visuals this cell's visuals.
     */
    public abstract void setVisuals(Paint... visuals);

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
    Shape getShape() {
        return shape;
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
    void setStroke(Paint value) {
        shape.setStroke(value);
    }


    boolean checkDiagonal(Cell neighbor) {
        int rowDiff = Math.abs(neighbor.getRow() - getRow());
        int columnDiff = Math.abs(neighbor.getColumn() - getColumn());
        return (rowDiff == 1)
                && (columnDiff == 1);
    }

    /**
     * Get this fire cell's state.
     *
     * @return this fire cell's state
     */
    public State getState() {
        return state;
    }

    public void setState(State s) {
        state = s;
    }

    /**
     * Set this fire cell's mark.
     *
     * @param mark this fire cell's mark.
     */
    public void setMark(Mark mark) {
        this.mark = mark;
    }

    interface State {

    }

    interface Mark {
    }

//    abstract void changeVisual();

//    void changeState() {
//        if (mark.toString() == "NONE") return;
//        State state =
//    }


    public Mark getMark() {
        return mark;
    }
}

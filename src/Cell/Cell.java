package Cell;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by rhondusmithwick on 1/30/16.
 *
 * @author Rhondu Smithwick
 */
public abstract class Cell {
    private final Collection<Cell> neighbors = new LinkedList<>();
    private Rectangle rectangle;

    private int row;
    private int column;

    Cell() {
        super();
        rectangle = new Rectangle();
    }

    public final void removeDiagonals() {
        Iterator<Cell> iter = neighbors.iterator();
        while (iter.hasNext()) {
            Cell neighbor = iter.next();
            int rowDiff = Math.abs(neighbor.getRow() - getRow());
            int columnDiff = Math.abs(neighbor.getColumn() - getColumn());
            if (rowDiff == 1 && columnDiff == 1) {
                iter.remove();
            }
        }
    }

    public abstract void changeState();

    public final void addNeighbor(Cell neighbor) {
        neighbors.add(neighbor);
    }

    public abstract void handleUpdate();

    public final int getRow() {
        return row;
    }

    private void setRow(int row) {
        this.row = row;
    }

    public final int getColumn() {
        return column;
    }

    private void setColumn(int column) {
        this.column = column;
    }

    public final void init(double cellWidth, double cellHeight, double x, double y, int row, int column) {
        rectangle.setWidth(cellWidth);
        rectangle.setHeight(cellHeight);
        rectangle.setX(x);
        rectangle.setY(y);
        setRow(row);
        setColumn(column);
    }

    public abstract void setVisuals(Paint... visuals);


    Collection<Cell> getNeighbors() {
        return neighbors;
    }

    Shape getShape() {
        return rectangle;
    }


    void setFill(Paint value) {
        rectangle.setFill(value);
    }

    void setStroke(Paint value) {
        rectangle.setStroke(value);
    }
}

package Cell;

import javafx.scene.shape.Rectangle;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by rhondusmithwick on 1/30/16.
 *
 * @author Rhondu Smithwick
 */
public abstract class Cell extends Rectangle {

    final Collection<Cell> neighbors;
    private boolean isEmpty = false;
    private int row;
    private int column;

    Cell() {
        super();
        neighbors = new LinkedList<>();
    }

    public Collection<Cell> getNeighbors() {
        return neighbors;
    }

    public final void addNeighbor(Cell neighbor) {
        neighbors.add(neighbor);
    }

    public abstract void handleUpdate();

    public int getRow() {
        return row;
    }

    void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    void setColumn(int column) {
        this.column = column;
    }

    public void init(int cellWidth, int cellHeight, int x, int y, int row, int column) {
        setWidth(cellWidth);
        setHeight(cellHeight);
        setX(x);
        setY(y);
        setRow(row);
        setColumn(column);
    }

    public boolean getIsEmpty() {
        return isEmpty;
    }

    public void setIsEmpty(boolean t) {
        isEmpty = t;
    }

}

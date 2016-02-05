package Cell;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by rhondusmithwick on 1/30/16.
 *
 * @author Rhondu Smithwick
 */
public abstract class Cell extends Rectangle {

    final Collection<Cell> neighbors;
    private int row;
    private int column;

    Cell() {
        super();
        neighbors = new LinkedList<>();
    }

    public void removeDiagonals() {
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

    public int getRow() {
        return row;
    }

    private void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    private void setColumn(int column) {
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

    public abstract void setVisuals(Paint... visuals);
}

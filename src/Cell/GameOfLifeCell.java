package Cell;

import javafx.scene.paint.Color;


/**
 * Created by rhondusmithwick on 1/31/16.
 *
 * @author Rhondu Smithwick
 */
public class GameOfLifeCell extends Cell {
    private boolean isAlive;

    public GameOfLifeCell() {
        super();
        isAlive = true;
    }

    @Override
    public void handleUpdate() {
        int aliveNeighbors = countAliveNeighbors();
        if (aliveNeighbors < 2 || aliveNeighbors > 3) {
            destroy();
        }
        if (aliveNeighbors == 3 && !isAlive) {
            restore();
        }
    }


    private int countAliveNeighbors() {
        int count = 0;
        for (Cell c : neighbors) {
            if (((GameOfLifeCell) c).getAlive()) {
                count++;
            }
        }
        return count;
    }


    private boolean getAlive() {
        return isAlive;
    }

    public void destroy() {
        isAlive = false;
        setFill(Color.BLACK);
    }

    private void restore() {
        isAlive = true;
        setFill(Color.WHITE);
    }

}

package Cell;

import javafx.scene.paint.Color;


/**
 * Created by rhondusmithwick on 1/31/16.
 *
 * @author Rhondu Smithwick
 */
public class GameOfLifeCell extends Cell {
    private boolean isAlive;
    private boolean markForDeath = false;
    private boolean markForRestore = false;
    public GameOfLifeCell() {
        super();
        isAlive = true;
    }

    @Override
    public void handleUpdate() {
        int aliveNeighbors = countAliveNeighbors();
        if (aliveNeighbors < 2 || aliveNeighbors > 3) {
            markForDeath = true;
        } else if (aliveNeighbors == 3 && !isAlive) {
            markForRestore = true;
        }
    }


    public void transform() {
        if (markForDeath) {
            destroy();
        }
        if (markForRestore) {
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
        markForDeath = false;
        setFill(Color.BLACK);
    }

    public void restore() {
        isAlive = true;
        markForRestore = false;
        setFill(Color.WHITE);
    }

}

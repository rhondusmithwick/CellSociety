package Cell;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


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


    public void transform(Paint deadColor, Paint aliveColor) {
        if (markForDeath) {
            destroy(deadColor);
        } else if (markForRestore) {
            restore(aliveColor);
        }
    }

    private int countAliveNeighbors() {
        int count = 0;
        for (Cell c : neighbors) {
            GameOfLifeCell gc = (GameOfLifeCell) c;
            if (gc.getAlive()) {
                count++;
            }
        }
        return count;
    }


    private boolean getAlive() {
        return isAlive;
    }

    public void destroy(Paint deadColor) {
        isAlive = false;
        markForDeath = false;
        setFill(deadColor);
    }

    public void restore(Paint aliveColor) {
        isAlive = true;
        markForRestore = false;
        setFill(aliveColor);
    }

}

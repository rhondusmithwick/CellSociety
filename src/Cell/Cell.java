package Cell;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
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

    Cell() {
        super();
        neighbors = new LinkedList<>();
//        this.setStroke(Color.BLACK);
    }


    public final void setImage(Image image) {
        ImagePattern imagePattern = new ImagePattern(image);
        this.setFill(imagePattern);
    }

    public final void addNeighbor(Cell neighbor) {
        neighbors.add(neighbor);
    }

    public abstract void handleUpdate();


}

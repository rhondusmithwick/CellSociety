package Cell;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * Created by rhondusmithwick on 1/30/16.
 *
 * @author Rhondu Smithwick
 */
public class Cell extends Rectangle {

    public Cell(double width, double height) {
        super(width, height);
        this.setFill(Color.WHITE);
    }

    public Cell(double x, double y, double width, double height) {
        super(x, y, width, height);
        this.setFill(Color.WHITE);
        this.setStroke(Color.BLACK);
    }

    public void setImage(Image image) {
        ImagePattern imagePattern = new ImagePattern(image);
        this.setFill(imagePattern);
    }
}

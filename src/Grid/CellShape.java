package Grid;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
// This class is my masterpiece.

/**
 * Created by rhondusmithwick on 2/13/16.
 *
 * @author Rhondu Smithwick
 */
public abstract class CellShape {

    private final Shape myShape;

    CellShape(Shape myShape) {
        this.myShape = myShape;
    }

    public Shape getMyShape() {
        return myShape;
    }

    public abstract double getX();

    public abstract double getY();

    public abstract double getWidth();

    public abstract double getHeight();

    public final void setStroke(Paint value) {
        myShape.setStroke(value);
    }

    public final void setFill(Paint value) {
        myShape.setFill(value);
    }

}

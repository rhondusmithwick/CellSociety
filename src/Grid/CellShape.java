package Grid;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;

/**
 * Created by rhondusmithwick on 2/13/16.
 *
 * @author Rhondu Smithwick
 */
public abstract class CellShape {

    private Shape myShape;

    public Shape getMyShape() {
        return myShape;
    }

    void setMyShape(Shape myShape) {
        this.myShape = myShape;
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

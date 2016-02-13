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

    public abstract void setX(double x);

    public abstract double getY();

    public abstract void setY(double y);

    public abstract double getWidth();

    public abstract void setWidth(double width);

    public abstract double getHeight();

    public abstract void setHeight(double height);

    public void setStroke(Paint value) {
        myShape.setStroke(value);
    }

    public void setFill(Paint value) {
        myShape.setFill(value);
    }

}

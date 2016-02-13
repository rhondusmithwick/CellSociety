package Grid;

import javafx.scene.shape.Rectangle;

/**
 * Created by rhondusmithwick on 2/6/16.
 *
 * @author Rhondu Smithwick
 */
public class RectangleShape extends CellShape {

    private final Rectangle rectangle;

    public RectangleShape() {
        setMyShape(new Rectangle());
        rectangle = (Rectangle) getMyShape();
    }

    @Override
    public double getX() {
        return rectangle.getX();
    }

    @Override
    public void setX(double x) {
        rectangle.setX(x);
    }

    @Override
    public double getY() {
        return rectangle.getY();
    }

    @Override
    public void setY(double y) {
        rectangle.setY(y);
    }

    @Override
    public double getWidth() {
        return rectangle.getWidth();
    }

    @Override
    public void setWidth(double width) {
        rectangle.setWidth(width);
    }

    @Override
    public double getHeight() {
        return rectangle.getHeight();
    }

    @Override
    public void setHeight(double height) {
        rectangle.setHeight(height);
    }
}
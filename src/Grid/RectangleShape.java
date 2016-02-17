package Grid;

import javafx.scene.shape.Rectangle;

/**
 * Created by rhondusmithwick on 2/6/16.
 *
 * @author Rhondu Smithwick
 */
public class RectangleShape extends CellShape {

    private final Rectangle rectangle;

    public RectangleShape(Rectangle rectangle) {
        super(rectangle);
        this.rectangle = rectangle;
    }

    @Override
    public double getX() {
        return rectangle.getX();
    }

    @Override
    public double getY() {
        return rectangle.getY();
    }

    @Override
    public double getWidth() {
        return rectangle.getWidth();
    }


    @Override
    public double getHeight() {
        return rectangle.getHeight();
    }
}
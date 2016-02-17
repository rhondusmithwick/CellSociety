package Grid;

/**
 * Created by rhondusmithwick on 2/13/16.
 *
 * @author Rhondu Smithwick
 */
public class TriangleShape extends CellShape {

    private final Triangle triangle;

    public TriangleShape(Triangle triangle) {
        super(triangle);
        this.triangle = triangle;
    }

    @Override
    public double getX() {
        return triangle.getX();
    }


    @Override
    public double getY() {
        return triangle.getY();
    }


    @Override
    public double getWidth() {
        return triangle.getWidth();
    }


    @Override
    public double getHeight() {
        return triangle.getHeight();
    }

}

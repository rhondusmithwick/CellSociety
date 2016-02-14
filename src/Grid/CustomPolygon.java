package Grid;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

/**
 * Created by rhondusmithwick on 2/13/16.
 *
 * @author Rhondu Smithwick
 */
abstract class CustomPolygon extends Polygon {
    private final double x;
    private final double y;
    private final double width;
    private final double height;
    private Point2D[] points;

    CustomPolygon(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }


    abstract void createPoints();

    final void setPoints() {
        for (Point2D point : points) {
            getPoints().addAll(point.getX(), point.getY());
        }
    }

    public final double getY() {
        return y;
    }

    public final double getX() {
        return x;
    }

    public final double getWidth() {
        return width;
    }


    public final double getHeight() {
        return height;
    }


    final void addPoint(int i, Point2D point) {
        points[i] = point;
    }

    final Point2D getPoint(int i) {
        return points[i];
    }

    final void setPoints(Point2D[] points) {
        this.points = points;
    }
}

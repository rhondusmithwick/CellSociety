package Grid;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    private final List<Point2D> points = new ArrayList<>();

    CustomPolygon(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    abstract void createPoints();

    final void addPoints(Point2D... pointsToAdd) {
        Collections.addAll(points, pointsToAdd);
    }

    final void setPoints() {
        points.stream().forEach(this::addPointToPolygon);
    }

    final Point2D getPoint(int i) {
        return points.get(i);
    }

    private void addPointToPolygon(Point2D point) {
        getPoints().addAll(point.getX(), point.getY());
    }


    public final double getX() {
        return x;
    }

    public final double getY() {
        return y;
    }

    public final double getWidth() {
        return width;
    }


    public final double getHeight() {
        return height;
    }

}

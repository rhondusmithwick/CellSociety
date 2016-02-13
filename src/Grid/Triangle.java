package Grid;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

class Triangle extends Polygon {
    private final double x;
    private final double y;
    private final double width;
    private final double height;
    private final Triangle prevTriangle;
    private Point2D point1;
    private Point2D point2;
    private Point2D point3;

    Triangle(double x, double y, double width, double height,
             Triangle prevTriangle) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.prevTriangle = prevTriangle;
        createPoints();
        setPoints();
    }

    private void createPoints() {
        if (prevTriangle == null) {
            point1 = new Point2D(x, y);
            point2 = new Point2D(x + width / 2, y + height);
            point3 = new Point2D(x + width, y);
        } else {
            point1 = prevTriangle.point2;
            point2 = prevTriangle.point3;
            point3 = new Point2D(point1.getX() + width, point1.getY());
        }
    }

    private void setPoints() {
        getPoints().setAll(
                point1.getX(), point1.getY(),
                point2.getX(), point2.getY(),
                point3.getX(), point3.getY()
        );
    }


    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }

    public double getWidth() {
        return width;
    }


    public double getHeight() {
        return height;
    }
}
package Grid;

import javafx.geometry.Point2D;

class Triangle extends CustomPolygon {
    private final Triangle prevTriangle;

    Triangle(double x, double y, double width, double height,
             Triangle prevTriangle) {
        super(x, y, width, height);
        setPoints(new Point2D[3]);
        this.prevTriangle = prevTriangle;
        createPoints();
        setPoints();
    }

    @Override
    void createPoints() {
        Point2D point1, point2, point3;
        if (prevTriangle == null) {
            point1 = new Point2D(getX(), getY());
            point2 = new Point2D(getX() + getWidth() / 2, getY() + getHeight());
            point3 = new Point2D(getX() + getWidth(), getY());
        } else {
            point1 = prevTriangle.getPoint(1);
            point2 = prevTriangle.getPoint(2);
            point3 = new Point2D(point1.getX() + getWidth(), point1.getY());
        }
        addPoint(0, point1);
        addPoint(1, point2);
        addPoint(2, point3);
    }

}
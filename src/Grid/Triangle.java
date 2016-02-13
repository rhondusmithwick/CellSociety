package Grid;

import javafx.geometry.Point2D;

class Triangle extends CustomPolygon {
    private final Triangle prevTriangle;

    Triangle(double x, double y, double width, double height,
             Triangle prevTriangle) {
        super(x, y, width, height);
        points = new Point2D[3];
        this.prevTriangle = prevTriangle;
        createPoints();
        setPoints();
    }

    @Override
    void createPoints() {
        if (prevTriangle == null) {
            points[0] = new Point2D(getX(), getY());
            points[1] = new Point2D(getX() + getWidth() / 2, getY() + getHeight());
            points[2] = new Point2D(getX() + getWidth(), getY());
        } else {
            points[0] = prevTriangle.points[1];
            points[1] = prevTriangle.points[2];
            points[2] = new Point2D(points[0].getX() + getWidth(), points[0].getY());
        }
    }

}
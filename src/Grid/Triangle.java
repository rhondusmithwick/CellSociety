// This entire file is part of my masterpiece.
// Rhondu Smithwick
package Grid;


import javafx.geometry.Point2D;

class Triangle extends CustomPolygon {
    private final Triangle prevTriangle;

    Triangle(double x, double y, double width, double height,
             Triangle prevTriangle) {
        super(x, y, width, height);
        this.prevTriangle = prevTriangle;
        createPoints();
        setPoints();
    }

    @Override
    void createPoints() {
        Point2D point1, point2, point3;
        if (atBeginningOfRow()) {
            point1 = new Point2D(getX(), getY());
            point2 = new Point2D(getX() + getWidth() / 2, getY() + getHeight());
            point3 = new Point2D(getX() + getWidth(), getY());
        } else {
            point1 = prevTriangle.getPoint(1);
            point2 = prevTriangle.getPoint(2);
            point3 = new Point2D(point1.getX() + getWidth(), point1.getY());
        }
        addPoints(point1, point2, point3);
    }

    private boolean atBeginningOfRow() {
        return (prevTriangle == null);
    }

}
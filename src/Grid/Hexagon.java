package Grid;

/**
 * Created by rhondusmithwick on 2/13/16.
 *
 * @author Rhondu Smithwick
 */
class Hexagon extends CustomPolygon {
    Hexagon prevHexagon;

    public Hexagon(double x, double y, double width, double height, Hexagon prevHexagon) {
        super(x, y, width, height);
    }

    @Override
    void createPoints() {

    }
}

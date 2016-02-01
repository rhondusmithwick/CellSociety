package Cell;

import javafx.scene.paint.Color;

/**
 * Created by rhondusmithwick on 2/1/16.
 *
 * @author Rhondu Smithwick
 */
public class SegregationCell extends Cell {
    private double threshold;
    private boolean isSatisfied;

    public SegregationCell() {
        super();
    }

    public void handleUpdate() {
        double likeMe = countLikeMe();
        if (likeMe * 100 < threshold) {
            isSatisfied = false;
        }
    }

    private double countLikeMe() {
        int count = 0;
        int num = 0;
        for (Cell c : neighbors) {
            if (c.getFill() != Color.WHITE) {
                if (c.getFill() != this.getFill()) {
                    count++;
                }
                num++;
            }
        }
        return (double) count / num;
    }

    public void setThreshold(double t) {
        threshold = t;
    }

    public boolean getSatisfied() {
        return isSatisfied;
    }

    public void setSatisfied(boolean t) {
        isSatisfied = t;
    }
}

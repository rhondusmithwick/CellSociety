package Cell;

import javafx.scene.paint.Color;

/**
 * Created by rhondusmithwick on 2/1/16.
 *
 * @author Rhondu Smithwick
 */
public class SegregationCell extends Cell {
    private int threshold;
    private boolean isSatisfied;
    private boolean isEmpty = false;

    public SegregationCell() {
        super();
    }

    public void handleUpdate() {
        if (getFill() != Color.WHITE) {
            double likeMePercent = getLikeMePercent();
            if (likeMePercent < threshold) {
                isSatisfied = false;
            }
        }
    }

    private double getLikeMePercent() {
        int count = 0;
        int num = 0;
        for (Cell c : neighbors) {
            SegregationCell sc = (SegregationCell) c;
            if (!sc.getIsEmpty()) {
                if (sc.getFill() != this.getFill()) {
                    count++;
                }
                num++;
            }
        }
        return ((double) count / num) * 100;
    }

    public void setThreshold(int t) {
        threshold = t;
    }

    public boolean getSatisfied() {
        return isSatisfied;
    }

    public void makeSatisfied() {
        isSatisfied = true;
    }

    public void setIsEmpty(boolean t) {
        isEmpty = t;
    }

    public boolean getIsEmpty() {
        return isEmpty;
    }
}

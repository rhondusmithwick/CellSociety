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

    public SegregationCell() {
        super();
        this.setStroke(Color.BLACK);

    }

    public void handleUpdate() {
        if (!getIsEmpty()) {
            double likeMePercent = getLikeMePercent();
            if (likeMePercent < threshold) {
                setSatisfied(false);
            }
        }
    }

    private double getLikeMePercent() {
        int count = 0;
        int num = 0;
        SegregationCell sc;
        for (Cell c : neighbors) {
            sc = (SegregationCell) c;
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

    public void setSatisfied(boolean t) {
        isSatisfied = t;
    }


}

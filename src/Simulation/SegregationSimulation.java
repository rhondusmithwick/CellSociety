package Simulation;

import Cell.Cell;
import Cell.SegregationCell;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rhondusmithwick on 2/1/16.
 *
 * @author Rhondu Smithwick
 */
public class SegregationSimulation extends Simulation {
    private static final int DEFAULT_THRESHOLD = 30;
    private static final int DEFAULT_EMPTY_PERCENT = 30;
    private static final int DEFAULT_GROUP1_PERCENT = 45;

    private static final Paint DEFAULT_EMPTY_VISUAL = Color.BLACK;
    private static final Paint DEFAULT_GROUP1_VISUAL = Color.RED;
    private static final Paint DEFAULT_GROUP2_VISUAL = Color.BLUE;

    private final List<SegregationCell> emptyCells = new ArrayList<>();

    private int threshold;
    private int emptyPercent;
    private int group1Percent;

    private Paint emptyVisual;
    private Paint group1Visual;
    private Paint group2Visual;


    public SegregationSimulation() {
        super();
        parseXmlFile("resources/" + "Segregation.xml");
    }


    @Override
    void assignInitialState(int randomNum, Cell c) {
        final SegregationCell sc = (SegregationCell) c;
        sc.setThreshold(threshold);
        if (randomNum <= emptyPercent) {
            sc.setFill(emptyVisual);
            sc.setIsEmpty(true);
            emptyCells.add(sc);
        } else if (randomNum > emptyPercent
                && randomNum <= emptyPercent + group1Percent) {
            sc.setFill(group1Visual);
        } else {
            sc.setFill(group2Visual);
        }
    }


    @Override
    protected void step() {
        super.step();
        SegregationCell sc;
        for (Cell c : getTheCells()) {
            sc = (SegregationCell) c;
            if (!sc.getIsEmpty() && !sc.getSatisfied()) {
                move(sc);
            }
        }
    }

    private void move(SegregationCell cellToMove) {
        final int randomIndex = getRandomNum(0, emptyCells.size() - 1);
        final SegregationCell emptyCell = emptyCells.get(randomIndex);

        emptyCell.setFill(cellToMove.getFill());
        emptyCell.setIsEmpty(false);
        emptyCell.setSatisfied(true);

        cellToMove.setFill(emptyVisual);
        cellToMove.setSatisfied(true);
        cellToMove.setIsEmpty(true);

        emptyCells.set(randomIndex, cellToMove);
    }

//
//    @Override
//    protected void step() {
//        super.step();
//        Map<SegregationCell, SegregationCell> moveMap = new HashMap<>();
//        SegregationCell sc;
//        for (Cell c : getTheCells()) {
//            sc = (SegregationCell) c;
//            if (!sc.getIsEmpty() && !sc.getSatisfied()) {
//                if (!emptyCells.isEmpty()) {
//                    final int randomIndex = getRandomNum(0, emptyCells.size() - 1);
//                    final SegregationCell emptyCell = emptyCells.get(randomIndex);
//                    emptyCells.remove(randomIndex);
//                    moveMap.put(sc, emptyCell);
//                }
//            }
//        }
//        doSwaps(moveMap);
//    }
//
//
//    private void doSwaps(Map<SegregationCell, SegregationCell> moveMap) {
//        for (SegregationCell cellToMove : moveMap.keySet()) {
//            SegregationCell emptyCell = moveMap.get(cellToMove);
//            swap(cellToMove, emptyCell);
//        }
//    }
//
//    private void swap(SegregationCell cellToMove, SegregationCell emptyCell) {
//        emptyCell.setFill(cellToMove.getFill());
//        emptyCell.setIsEmpty(false);
//        emptyCell.setSatisfied(true);
//
//        cellToMove.setFill(emptyVisual);
//        cellToMove.setSatisfied(true);
//        cellToMove.setIsEmpty(true);
//        emptyCells.add(cellToMove);
//    }


    @Override
    void setSpecificProperties(Element simElem) {
        if (getType() == null || !getType().equals("Segregation")) {
            threshold = DEFAULT_THRESHOLD;
            emptyPercent = DEFAULT_EMPTY_PERCENT;
            group1Percent = DEFAULT_GROUP1_PERCENT;
            emptyVisual = DEFAULT_EMPTY_VISUAL;
            group1Visual = DEFAULT_GROUP1_VISUAL;
            group2Visual = DEFAULT_GROUP2_VISUAL;
        } else {
            threshold = getIntValue(simElem, "threshold");
            emptyPercent = getIntValue(simElem, "emptyPercent");
            group1Percent = getIntValue(simElem, "group1Percent");
            emptyVisual = getPaintValue(simElem, "emptyVisual");
            group1Visual = getPaintValue(simElem, "group1Visual");
            group2Visual = getPaintValue(simElem, "group2Visual");
        }
    }


}

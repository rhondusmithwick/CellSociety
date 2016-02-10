package Simulation;

import Cell.Cell;
import Cell.SegregationCell;
import Cell.SegregationCell.Mark;
import Cell.SegregationCell.State;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.Collections;
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
    private List<SegregationCell> emptyCellsToAdd;

    private int threshold;
    private int emptyPercent;
    private int group1Percent;

    private Paint emptyVisual;
    private Paint group1Visual;
    private Paint group2Visual;


    public SegregationSimulation() {
        super();
        setProperties(XMLParser.getXmlElement("resources/" + "Segregation.xml"));
    }

    private static void cleanUp(List<SegregationCell> cellsToMove) {
        for (SegregationCell sc : cellsToMove) {
            sc.setMark(Mark.NONE);
        }
    }

    @Override
    void assignInitialState( Cell c) {
        int randomNum = getRandomNum(1, 100);
        final SegregationCell sc = (SegregationCell) c;
        sc.setThreshold(threshold);
        sc.setVisuals(emptyVisual, group1Visual, group2Visual);
        if (randomNum <= emptyPercent) {
            sc.setMark(Mark.TO_EMPTY);
            emptyCells.add(sc);
        } else if (randomNum > emptyPercent
                && randomNum <= emptyPercent + group1Percent) {
            sc.setMark(Mark.TO_GROUP1);
        } else {
            sc.setMark(Mark.TO_GROUP2);
        }
    }

    @Override
    public void step() {
        super.step();
        emptyCellsToAdd = new ArrayList<>();
        getAllUpdates();
        changeStates();
        emptyCells.addAll(emptyCellsToAdd);
    }

    private void getAllUpdates() {
        List<SegregationCell> cellsToMove = getCellToMove();
        randomMover(cellsToMove);
        cleanUp(cellsToMove);
    }

    private void randomMover(List<SegregationCell> cellsToMove) {
        Collections.shuffle(cellsToMove);
        Collections.shuffle(emptyCells);
        while (!cellsToMove.isEmpty() && !emptyCells.isEmpty()) {
            swap(cellsToMove.get(0), emptyCells.get(0));
            emptyCells.remove(0);
            cellsToMove.remove(0);
        }
    }

    private void swap(SegregationCell sc, SegregationCell emptyCell) {
        if (sc.getState() == State.GROUP1) {
            emptyCell.setMark(Mark.TO_GROUP1);
        } else {
            emptyCell.setMark(Mark.TO_GROUP2);
        }
        emptyCellsToAdd.add(sc);
    }

    private List<SegregationCell> getCellToMove() {
        List<SegregationCell> cellsToMove = new ArrayList<>();
        for (Cell c : getTheCells()) {
            SegregationCell sc = (SegregationCell) c;
            if (sc.getMark() == Mark.TO_EMPTY) {
                cellsToMove.add(sc);
            }
        }
        return cellsToMove;
    }

    @Override
    void setSpecificProperties() {
        if (getType() == null || !getType().equals("Segregation")) {
            threshold = DEFAULT_THRESHOLD;
            emptyPercent = DEFAULT_EMPTY_PERCENT;
            group1Percent = DEFAULT_GROUP1_PERCENT;
            emptyVisual = DEFAULT_EMPTY_VISUAL;
            group1Visual = DEFAULT_GROUP1_VISUAL;
            group2Visual = DEFAULT_GROUP2_VISUAL;
        } else {
            threshold = xmlProperties.getIntValue("threshold");
            emptyPercent = xmlProperties.getIntValue("emptyPercent");
            group1Percent = xmlProperties.getIntValue("group1Percent");
            emptyVisual = xmlProperties.getPaintValue("emptyVisual");
            group1Visual = xmlProperties.getPaintValue("group1Visual");
            group2Visual = xmlProperties.getPaintValue("group2Visual");
        }
    }
	@Override
	void saveSpecificValues() {
		savedValues.put("threshold",threshold);
		savedValues.put("emptyPercent",emptyPercent);
		savedValues.put("group1Percent",group1Percent);
		savedValues.put("emptyVisual",emptyVisual);
		savedValues.put("group1Visual",group1Visual);
		savedValues.put("group2Visual",group2Visual);
	}

}

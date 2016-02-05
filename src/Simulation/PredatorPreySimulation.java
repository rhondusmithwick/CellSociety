package Simulation;

import Cell.Cell;
import Cell.PredatorPreyCell;
import Cell.PredatorPreyCell.State;
import Cell.PredatorPreyCell.Mark;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.w3c.dom.Element;
/**
 * Created by rhondusmithwick on 2/3/16.
 *
 * @author Rhondu Smithwick
 */
// TO FINISH
public class PredatorPreySimulation extends Simulation {
    private static final int DEFAULT_BREED_TIME = 30;
    private static final int DEFAULT_EMPTY_PERCENT = 10;
    private static final int DEFAULT_FISH_PERCENT = 45;

    private static final Paint DEFAULT_EMPTY_VISUAL = Color.BLUE;
    private static final Paint DEFAULT_FISH_VISUAL = Color.LIMEGREEN;
    private static final Paint DEFAULT_SHARK_VISUAL = Color.YELLOW;

    private int breedTime;
    private int emptyPercent;
    private int fishPercent;

    private Paint emptyVisual;
    private Paint fishVisual;
    private Paint sharkVisual;


    public PredatorPreySimulation() {
        super();
        parseXmlFile("resources/" + "PredatorPrey.xml");
    }


    @Override
    void assignInitialState(int randomNum, Cell c) {
        final PredatorPreyCell ppc = (PredatorPreyCell) c;
        ppc.removeDiagonals();
        ppc.setVisuals(emptyVisual, fishVisual, sharkVisual);
        if (randomNum <= emptyPercent) {
            ppc.setMark(Mark.TO_EMPTY);
        } else if (randomNum > emptyPercent
                && randomNum <= emptyPercent + fishPercent) {
            ppc.setMark(Mark.TO_FISH);
        } else {
           ppc.setMark(Mark.TO_SHARK);
        }
    }


    @Override
    void setSpecificProperties(Element simElem) {
        if (getType() == null || !getType().equals("PredatorPrey")) {
            breedTime = DEFAULT_BREED_TIME;
            emptyPercent = DEFAULT_EMPTY_PERCENT;
            fishPercent = DEFAULT_FISH_PERCENT;
            emptyVisual = DEFAULT_EMPTY_VISUAL;
            fishVisual = DEFAULT_FISH_VISUAL;
            sharkVisual = DEFAULT_SHARK_VISUAL;
        } else {
            breedTime = getIntValue(simElem, "breedTime");
            emptyPercent = getIntValue(simElem, "emptyPercent");
            fishPercent = getIntValue(simElem, "fishPercent");
            emptyVisual = getPaintValue(simElem, "emptyVisual");
            fishVisual = getPaintValue(simElem, "fishVisual");
            sharkVisual = getPaintValue(simElem, "sharkVisual");
        }
    }
}

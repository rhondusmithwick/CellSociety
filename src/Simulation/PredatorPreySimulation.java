//package Simulation;
//
//import Cell.Cell;
//import Cell.PredatorPreyCell;
//import Cell.PredatorPreyCell.State;
//import javafx.scene.paint.Color;
//import javafx.scene.paint.Paint;
//import org.w3c.dom.Element;
//
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.Map;
//
///**
// * Created by rhondusmithwick on 2/3/16.
// *
// * @author Rhondu Smithwick
// */
//// TO FINISH
//public class PredatorPreySimulation extends Simulation {
//    private static final int DEFAULT_BREED_TIME = 30;
//    private static final int DEFAULT_EMPTY_PERCENT = 10;
//    private static final int DEFAULT_FISH_PERCENT = 45;
//
//    private static final Paint DEFAULT_EMPTY = Color.BLUE;
//    private static final Paint DEFAULT_FISH = Color.LIMEGREEN;
//    private static final Paint DEFAULT_SHARK = Color.YELLOW;
//
//
//    private int breedTime = DEFAULT_BREED_TIME;
//    private int emptyPercent = DEFAULT_EMPTY_PERCENT;
//    private int fishPercent = DEFAULT_FISH_PERCENT;
//
//    private Paint emptyVisual = DEFAULT_EMPTY;
//    private Paint fishVisual = DEFAULT_FISH;
//    private Paint sharkVisual = DEFAULT_SHARK;
//
//
//    private Collection<PredatorPreyCell> createFishCells;
//    private Collection<PredatorPreyCell> createSharkCells;
//    private Map<PredatorPreyCell, PredatorPreyCell> moves;
//    private Map<PredatorPreyCell, PredatorPreyCell> theMoves;
//
//    public PredatorPreySimulation() {
//        super();
//        parseXmlFile("resources/" + "GameOfLife.xml");
//    }
//
//
//    @Override
//    void assignInitialState(int randomNum, Cell c) {
//        final PredatorPreyCell ppc = (PredatorPreyCell) c;
//        ppc.removeDiagonals();
//        if (randomNum <= emptyPercent) {
//            ppc.setFill(emptyVisual);
//            ppc.setState(State.EMPTY);
//        } else if (randomNum > emptyPercent
//                && randomNum <= emptyPercent + fishPercent) {
//            ppc.setFill(fishVisual);
//            ppc.setState(State.FISH);
//            ppc.setEdible(true);
//        } else {
//            ppc.setFill(sharkVisual);
//            ppc.setState(State.SHARK);
//        }
//        ppc.removeDiagonals();
//    }
//
//    protected void step() {
//        super.step();
//        createFishCells = new LinkedList<>();
//        createSharkCells = new LinkedList<>();
//        moves = new HashMap<>();
//        PredatorPreyCell ppc;
//        for (Cell c : getTheCells()) {
//            ppc = (PredatorPreyCell) c;
//            switch (ppc.getState()) {
//                case SHARK:
//                    sharkUpdate(ppc);
//                    break;
//                case FISH:
//                    fishUpdate(ppc);
//                    break;
//                default:
//            }
//        }
//    }
//
//
//    private void fishUpdate(PredatorPreyCell fish) {
//
//    }
//
//
//    private void sharkUpdate(PredatorPreyCell shark) {
//
//    }
//
//    @Override
//    void setSpecificProperties(Element simElem) {
////        if (getType() == null || !getType().equals("PredatorPrey")) {
////            threshold = DEFAULT_THRESHOLD;
////            emptyPercent = DEFAULT_EMPTY_PERCENT;
////            group1Percent = DEFAULT_GROUP1_PERCENT;
////        } else {
////            threshold = getIntValue(simElem, "threshold");
////            emptyPercent = getIntValue(simElem, "emptyPercent");
////            group1Percent = getIntValue(simElem, "group1Percent");
////        }
//    }
//}

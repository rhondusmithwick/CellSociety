package Simulation;

import Cell.Cell;
import Cell.SlimeMoldCell;
import Cell.SlimeMoldCell.Mark;
import Cell.SlimeMoldCell.State;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.w3c.dom.Element;

/**
 * Created by rhondusmithwick on 2/11/16.
 *
 * @author Rhondu Smithwick
 */
public class SlimeMoldSimulation extends Simulation {
    private static final int DEFAULT_SLIME_AMOUNT = 30;
    private static final int DEFAULT_OBSTACLES_AMOUNT = 20;
    private static final double DEFAULT_SNIFF_THRESHOLD = 10;
    private static final Point2D[] DEFAULT_SNIFF_ANGLES = {
            new Point2D(0, 1), new Point2D(1, 1),
            new Point2D(-1, 1), new Point2D(-1, 0)
    };
    private static final Point2D[] DEFAULT_WIGGLE_ANGLES = {
            new Point2D(0, 1), new Point2D(1, 1),
            new Point2D(-1, 1), new Point2D(-1, 0),
            new Point2D(-1, -1), new Point2D(0, -1),
            new Point2D(1, 0), new Point2D(1, -1)
    };
    private static final double DEFAULT_EVAPORATION_RATE = 1;
    private static final double DEFAULT_DIFFUSION_RATE = 0;
    private static final double DEFAULT_CHEMICAL_DROPS = 2;


    private static final Paint DEFAULT_EMPTY_VISUAL = Color.WHITE;
    private static final Paint DEFAULT_SLIME_VISUAL = Color.GREEN;
    private static final Paint DEFAULT_cAMP_VISUAL = Color.RED;
    private static final Paint DEFAULT_OBSTACLES_VISUAL = Color.BLUE;

    private final int slimePercent = DEFAULT_SLIME_AMOUNT;
    private final int obstaclePercent = DEFAULT_OBSTACLES_AMOUNT;
    private final double sniffThreshold = DEFAULT_SNIFF_THRESHOLD;
    private final Point2D[] sniffAngles = DEFAULT_SNIFF_ANGLES;
    private final Point2D[] wiggleAngles = DEFAULT_WIGGLE_ANGLES;
    private final double evaporationRate = DEFAULT_EVAPORATION_RATE;
    private final double diffusionRate = DEFAULT_DIFFUSION_RATE;
    private final double chemicalDrops = DEFAULT_CHEMICAL_DROPS;

    private final Paint emptyVisual = DEFAULT_EMPTY_VISUAL;
    private final Paint slimeVisual = DEFAULT_SLIME_VISUAL;
    private final Paint cAMPVisual = DEFAULT_cAMP_VISUAL;
    private final Paint obstacleVisual = DEFAULT_OBSTACLES_VISUAL;


    public SlimeMoldSimulation() {
        super();
        setProperties(XMLParser.getXmlElement("resources/SlimeMold.xml"));

    }

    @Override
    public void step() {
        updateAllcAMP();
        dropChemicals();
        moveAll();
        changeStates();
    }

    private void moveAll() {
        getTheCells().stream().map(c -> (SlimeMoldCell) c).forEach(this::move);
    }

    private void move(SlimeMoldCell smc) {
        if (smc.getState() == State.SLIME) {
            SlimeMoldCell cellToMoveTo;
            if (smc.sniffCheck(sniffThreshold)) {
                cellToMoveTo = smc.chemicalGradientMove(sniffAngles);
            } else {
                int randomIndex = getRandomNum(0, wiggleAngles.length - 1);
                cellToMoveTo = smc.randomMoveHelp(wiggleAngles[randomIndex]);
            }
            if (cellToMoveTo != null) {
                swap(smc, cellToMoveTo);
            }
        }
    }

    private void swap(SlimeMoldCell cellToMove, SlimeMoldCell cellToMoveTo) {
        cellToMoveTo.setMark(Mark.TO_SLIME);
        cellToMove.setMark(Mark.TO_EMPTY);
    }

    private void dropChemicals() {
        getTheCells().stream()
                .map(c -> (SlimeMoldCell) c)
                .forEach(smc -> smc.dropChemicals(chemicalDrops));
    }

    private void updateAllcAMP() {
        getTheCells().stream()
                .map(c -> (SlimeMoldCell) c)
                .forEach(this::updatecAMP);
    }

    private void updatecAMP(SlimeMoldCell smc) {
        smc.evaporate(evaporationRate);
        smc.diffuse(diffusionRate);
    }

    @Override
    void assignInitialState(Cell c) {
        c.setVisuals(emptyVisual, slimeVisual, cAMPVisual, obstacleVisual);
        SlimeMoldCell smc = (SlimeMoldCell) c;
        int randomNum = getRandomNum(1, 100);
        if (randomNum <= slimePercent) {
            smc.setMark(Mark.TO_SLIME);
        } else if (randomNum <= slimePercent + obstaclePercent){
            smc.setMark(Mark.TO_OBSTACLE);
        } else {
            smc.setMark(Mark.TO_EMPTY);
        }
    }

    @Override
    void setSpecificProperties(Element simElem) {

    }
}
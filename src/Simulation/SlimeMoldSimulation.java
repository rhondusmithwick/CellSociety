package Simulation;

import Cell.Cell;
import Cell.SlimeMoldCell;
import Cell.SlimeMoldCell.Mark;
import Cell.SlimeMoldCell.State;
import XML.XMLException;
import XML.XMLParser;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Created by rhondusmithwick on 2/11/16.
 *
 * @author Rhondu Smithwick
 */
public class SlimeMoldSimulation extends Simulation {
    private static final int DEFAULT_SLIME_AMOUNT = 30;
    private static final int DEFAULT_OBSTACLES_AMOUNT = 10;
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
    private static final double DEFAULT_DIFFUSION_RATE = .5;
    private static final double DEFAULT_CHEMICAL_DROPS = 2;


    private static final Paint DEFAULT_EMPTY_VISUAL = Color.WHITE;
    private static final Paint DEFAULT_SLIME_VISUAL = Color.GREEN;
    private static final Paint DEFAULT_cAMP_VISUAL = Color.RED;
    private static final Paint DEFAULT_OBSTACLES_VISUAL = Color.BLUE;

    private final int slimePercent = DEFAULT_SLIME_AMOUNT;
    private final int obstaclePercent = DEFAULT_OBSTACLES_AMOUNT;
    private final Point2D[] sniffAngles = DEFAULT_SNIFF_ANGLES;
    private final Point2D[] wiggleAngles = DEFAULT_WIGGLE_ANGLES;
    private final Paint emptyVisual = DEFAULT_EMPTY_VISUAL;
    private final Paint slimeVisual = DEFAULT_SLIME_VISUAL;
    private final Paint cAMPVisual = DEFAULT_cAMP_VISUAL;
    private final Paint obstacleVisual = DEFAULT_OBSTACLES_VISUAL;
    private double sniffThreshold = DEFAULT_SNIFF_THRESHOLD;
    private double evaporationRate = DEFAULT_EVAPORATION_RATE;
    private double diffusionRate = DEFAULT_DIFFUSION_RATE;
    private double chemicalDrops = DEFAULT_CHEMICAL_DROPS;


    public SlimeMoldSimulation() throws XMLException {
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
            SlimeMoldCell cellToMoveTo = getCellToMoveTo(smc);
            if (cellToMoveTo != null) {
                swap(smc, cellToMoveTo);
            }
        }
    }

    private SlimeMoldCell getCellToMoveTo(SlimeMoldCell smc) {
        SlimeMoldCell cellToMoveTo;
        if (smc.sniffCheck(sniffThreshold)) {
            cellToMoveTo = smc.chemicalGradientMove(sniffAngles);
        } else {
            int randomIndex = getRandomNum(0, wiggleAngles.length - 1);
            cellToMoveTo = smc.randomMoveHelp(wiggleAngles[randomIndex]);
        }
        return cellToMoveTo;
    }

    private void swap(SlimeMoldCell cellToMove, SlimeMoldCell cellToMoveTo) {
        cellToMoveTo.setMark(Mark.SLIME);
        cellToMove.setMark(Mark.EMPTY);
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
            smc.setMark(Mark.SLIME);
        } else if (randomNum <= slimePercent + obstaclePercent) {
            smc.setMark(Mark.OBSTACLE);
        } else {
            smc.setMark(Mark.EMPTY);
        }
    }

    /*
    @Override
    void setSpecificProperties(Element simElem) {

    }*/

    @Override
    void saveSpecificValues() {
        // TODO Auto-generated method stub

    }

    @Override
    void setSpecificProperties() {
        // TODO Auto-generated method stub

    }

    public double getSniff() {
        return sniffThreshold;
    }

    public void setSniff(int newSniff) {
        sniffThreshold = newSniff;

    }

    public double getDiffusion() {
        return diffusionRate;
    }

    public void setDiffusion(double newRate) {
        diffusionRate = newRate;

    }

    public double getChemicalDrops() {
        return chemicalDrops;
    }

    public void setChemicalDrops(int newChem) {
        chemicalDrops = newChem;
    }

    public double getEvaporation() {
        return evaporationRate;
    }

    public void setEvaporation(int newRate) {
        evaporationRate = newRate;

    }

	@Override
	boolean hasGraph() {
		return false;
	}



	@Override
	void assignLoadState(Cell c) {
		// TODO Auto-generated method stub

	}

}

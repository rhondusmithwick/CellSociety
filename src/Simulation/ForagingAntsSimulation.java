package Simulation;

import Cell.Cell;
import Cell.ForagingAntsCell;
import Cell.ForagingAntsCell.Mark;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.w3c.dom.Element;


/**
 * Created by rhondusmithwick on 2/7/16.
 *
 * @author Rhondu Smithwick
 */
public class ForagingAntsSimulation extends Simulation {

    private static final Point2D DEFAULT_NEST_LOCATION = new Point2D(0, 0);
    private static final Point2D DEFAULT_FOOD_LOCATION = new Point2D(99, 99);
    private static final int DEFAULT_MAX_ANTS_PER_LOCATION = 10;
    private static final int DEFAULT_ANT_LIFETIME = 500;
    private static final int DEFAULT_ANTS_BORN = 20;
    private static final double DEFAULT_MIN_AMOUNT_PHEROMONE = 0.0;
    private static final double DEFAULT_MAX_PHEROMONE = 100.0;
    private static final double DEFAULT_EVAPORATION_RATE = .001;
    private static final double DEFAULT_DIFFUSION_RATE = .001;
    private static final double DEFAULT_K = .001;
    private static final double DEFAULT_N = 10.0;
    private static final Paint DEFAULT_EMPTY_VISUAL = Color.WHITE;
    private static final Paint DEFAULT_NEST_VISUAL = Color.LIGHTGREEN;
    private static final Paint DEFAULT_FOOD_VISUAL = Color.PALEGOLDENROD;
    private ForagingAntsCell nest;
    private final Point2D nestLocation = DEFAULT_NEST_LOCATION;
    private final Point2D foodLocation = DEFAULT_FOOD_LOCATION;
    private final int maxAntsPer = DEFAULT_MAX_ANTS_PER_LOCATION;
    private final int antLifetime = DEFAULT_ANT_LIFETIME;
    private final int antsBorn = DEFAULT_ANTS_BORN;
    private final double minAmountPheromone = DEFAULT_MIN_AMOUNT_PHEROMONE;
    private final double maxAmountPheromone = DEFAULT_MAX_PHEROMONE;
    private final double evaporationRate = DEFAULT_EVAPORATION_RATE;
    private final double diffusionRate = DEFAULT_DIFFUSION_RATE;
    private final double K = DEFAULT_K;
    private final double N = DEFAULT_N;
    private final Paint emptyVisual = DEFAULT_EMPTY_VISUAL;
    private final Paint nestVisual = DEFAULT_NEST_VISUAL;
    private final Paint foodVisual = DEFAULT_FOOD_VISUAL;

    public ForagingAntsSimulation() {
        super();
        setProperties(XMLParser.getXmlElement("resources/ForagingAnts.xml"));
    }

    public void step() {
        updatePheros();
        nest.spawn(antsBorn);
        super.step();
        changeStates();
    }

    private void updatePheros() {
        getTheCells().stream().map(c -> (ForagingAntsCell) c).forEach(this::pheroStep);
    }

    private void pheroStep(ForagingAntsCell c) {
        c.setProbChoice(K, N);
        c.pheroUpdate(evaporationRate, diffusionRate);
    }

    @Override
    void assignInitialState(Cell c) {
        c.setVisuals(emptyVisual, nestVisual, foodVisual);
        ForagingAntsCell fac = (ForagingAntsCell) c;
        fac.setMaxAntsPer(maxAntsPer);
        fac.setPheromones(minAmountPheromone, maxAmountPheromone);
        fac.setAntLifeTime(antLifetime);
        if (isLocation(fac, nestLocation)) {
            fac.setMark(Mark.TO_NEST);
            nest = fac;
        } else if (isLocation(fac, foodLocation)) {
            fac.setMark(Mark.TO_FOOD);
        } else {
            fac.setMark(Mark.TO_EMPTY);
        }
    }

    @Override
    void setSpecificProperties(Element simElem) {

    }

    private boolean isLocation(Cell c, Point2D loc) {
        return (c.getRow() == loc.getY())
                && (c.getColumn() == loc.getX());
    }

}

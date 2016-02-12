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
    private static final Point2D[] DEFAULT_FOOD_LOCATIONS = {new Point2D(0, 99), new Point2D(99, 0), new Point2D(99, 99)};
    private static final int DEFAULT_MAX_ANTS = 1000;
    private static final int DEFAULT_MAX_ANTS_PER_LOCATION = 10;
    private static final int DEFAULT_ANT_LIFETIME = 500;
    private static final int DEFAULT_ANTS_BORN = 20;
    private static final double DEFAULT_MIN_AMOUNT_PHEROMONE = 0.0;
    private static final double DEFAULT_MAX_PHEROMONE = 100000.0;
    private static final double DEFAULT_EVAPORATION_RATE = .001;
    private static final double DEFAULT_DIFFUSION_RATE = .001;
    private static final double DEFAULT_K = .001;
    private static final double DEFAULT_N = 60.0;
    private static final Paint DEFAULT_EMPTY_VISUAL = Color.WHITE;
    private static final Paint DEFAULT_NEST_VISUAL = Color.LIGHTGREEN;
    private static final Paint DEFAULT_FOOD_VISUAL = Color.PALEGOLDENROD;

    private final Point2D nestLocation = DEFAULT_NEST_LOCATION;
    private final Point2D[] foodLocations = DEFAULT_FOOD_LOCATIONS;
    private final double minAmountPheromone = DEFAULT_MIN_AMOUNT_PHEROMONE;
    private final double maxAmountPheromone = DEFAULT_MAX_PHEROMONE;
    private final int maxAntsPer = DEFAULT_MAX_ANTS_PER_LOCATION;
    private final Paint emptyVisual = DEFAULT_EMPTY_VISUAL;
    private final Paint nestVisual = DEFAULT_NEST_VISUAL;
    private final Paint foodVisual = DEFAULT_FOOD_VISUAL;
    private final int maxAnts = DEFAULT_MAX_ANTS;
    private final int antLifetime = DEFAULT_ANT_LIFETIME;
    private final int antsBorn = DEFAULT_ANTS_BORN;
    private final double evaporationRate = DEFAULT_EVAPORATION_RATE;
    private final double diffusionRate = DEFAULT_DIFFUSION_RATE;
    private final double K = DEFAULT_K;
    private final double N = DEFAULT_N;
    private ForagingAntsCell nest;
    private int currAnts = 0;

    public ForagingAntsSimulation() {
        super();
        setProperties(XMLParser.getXmlElement("resources/ForagingAnts.xml"));
    }

    private static boolean isLocation(Cell c, Point2D loc) {
        return (c.getRow() == loc.getY())
                && (c.getColumn() == loc.getX());
    }

    public void step() {
        stepSetup();
        spawnAnts();
        super.step();
        changeStates();
    }

    private void stepSetup() {
        currAnts = 0;
        getTheCells().stream()
                .map(c -> (ForagingAntsCell) c)
                .forEach(this::preStep);
    }

    private void preStep(ForagingAntsCell c) {
        currAnts += c.getNumAnts();
        c.setProbChoice(K, N);
        c.pheroUpdate(evaporationRate, diffusionRate);
    }

    private void spawnAnts() {
        if (currAnts < maxAnts) {
            int allowed = maxAnts - currAnts;
            if (allowed < antsBorn) {
                nest.spawn(allowed, antLifetime);
            } else {
                nest.spawn(antsBorn, antLifetime);
            }
        }
    }

    @Override
    void assignInitialState(Cell c) {
        c.setVisuals(emptyVisual, nestVisual, foodVisual);
        ForagingAntsCell fac = (ForagingAntsCell) c;
        fac.setMaxAntsPer(maxAntsPer);
        fac.setInitialPheromones(minAmountPheromone, maxAmountPheromone);
        if (isLocation(fac, nestLocation)) {
            fac.setMark(Mark.TO_NEST);
            nest = fac;
        } else if (isFoodLocation(fac)) {
            fac.setMark(Mark.TO_FOOD);
        } else {
            fac.setMark(Mark.TO_EMPTY);
        }
    }

    @Override
    void setSpecificProperties(Element simElem) {

    }

    private boolean isFoodLocation(Cell c) {
        for (Point2D food : foodLocations) {
            if (isLocation(c, food)) {
                return true;
            }
        }
        return false;
    }

}
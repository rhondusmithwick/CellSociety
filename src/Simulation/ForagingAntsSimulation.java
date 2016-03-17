package Simulation;

import Cell.Cell;
import Cell.ForagingAntsCell;
import Cell.ForagingAntsCell.Mark;
import XML.XMLException;
import XML.XMLParser;
import javafx.geometry.Point2D;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Created by rhondusmithwick on 2/7/16.
 *
 * @author Rhondu Smithwick
 */
public class ForagingAntsSimulation extends Simulation {

	private static final Point2D DEFAULT_NEST_LOCATION = new Point2D(0, 0);
	private static final Point2D[] DEFAULT_FOOD_LOCATIONS = { new Point2D(0, 99), new Point2D(99, 0),
			new Point2D(99, 99) };
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
	private int maxAnts = DEFAULT_MAX_ANTS;
	private int antLifetime = DEFAULT_ANT_LIFETIME;
	private int antsBorn = DEFAULT_ANTS_BORN;
	private double evaporationRate = DEFAULT_EVAPORATION_RATE;
	private double diffusionRate = DEFAULT_DIFFUSION_RATE;
	private double K = DEFAULT_K;
	private double N = DEFAULT_N;
	private ForagingAntsCell nest;
	private int currAnts = 0;

	


	public ForagingAntsSimulation() throws XMLException {
		super();
		setProperties(XMLParser.getXmlElement("resources/ForagingAnts.xml"));
	}

	private static boolean isLocation(Cell c, Point2D loc) {
		return (c.getRow() == loc.getY()) && (c.getColumn() == loc.getX());
	}

	@Override
	void saveSpecificValues() {

	}

	@Override
	void setSpecificProperties() {

	}

	public void step() {
		stepSetup();
		spawnAnts();
		super.step();
		changeStates();
		getConfig().updateGraph();
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
			fac.setMark(Mark.NEST);
			nest = fac;
		} else if (isFoodLocation(fac)) {
			fac.setMark(Mark.FOOD);
		} else {
			fac.setMark(Mark.OPEN);
		}
	}

	private void stepSetup() {
		currAnts = 0;
		getTheCells().stream().map(c -> (ForagingAntsCell) c).forEach(this::preStep);
	}

	private void preStep(ForagingAntsCell c) {
		currAnts += c.getNumAnts();
		c.setProbChoice(K, N);
		c.pheroUpdate(evaporationRate, diffusionRate);
	}

	/*
	 * @Override void setSpecificProperties(Element simElem) {
	 * 
	 * }
	 */
	private boolean isFoodLocation(Cell c) {
		for (Point2D food : foodLocations) {
			if (isLocation(c, food)) {
				return true;
			}
		}
		return false;
	}

	public double getDiffusionRate() {
		return diffusionRate;
	}

	public void setDiffusionRate(double d) {
		diffusionRate = d;
	}

	public double getEvaporationRate() {
		return evaporationRate;
	}

	public void setEvaporationRate(double d) {
		evaporationRate = d;
	}

	public double getAntsBorn() {
		return antsBorn;
	}

	public void setAntsBorn(double d) {
		antsBorn = (int) d;
	}

	public double getLifeTime() {
		return antLifetime;
	}

	public void setLifeTime(double d) {
		antLifetime = (int) d;
	}

	public double getMaxAnts() {
		return maxAnts;
	}

	public void setMaxAnts(double d) {
		maxAnts = (int) d;
	}

	public double getK() {
		return K;
	}

	public void setK(double d) {
		K = d;
	}

	public double getN() {
		return N;
	}

	public void setN(double d) {
		N = d;
	}

	public Object getCurrAnts() {
		return currAnts;
	}

}

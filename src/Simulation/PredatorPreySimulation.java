package Simulation;

import Cell.Cell;
import Cell.PredatorPreyCell;
import Cell.PredatorPreyCell.Mark;
import Cell.PredatorPreyCell.State;
import XML.XMLException;
import XML.XMLParser;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rhondusmithwick on 2/3/16.
 *
 * @author Rhondu Smithwick
 */
public class PredatorPreySimulation extends Simulation {
    private static final int DEFAULT_FISH_BREED_TIME = 10;
    private static final int DEFAULT_SHARK_BREED_TIME = 15;
    private static final int DEFAULT_STARVE_TIME = 5;
    private static final int DEFAULT_EMPTY_PERCENT = 40;
    private static final int DEFAULT_FISH_PERCENT = 50;

    private static final Paint DEFAULT_EMPTY_VISUAL = Color.BLUE;
    private static final Paint DEFAULT_FISH_VISUAL = Color.LIMEGREEN;
    private static final Paint DEFAULT_SHARK_VISUAL = Color.YELLOW;

    private int sharkBreedTime;
    private int fishBreedTime;
    private int starveTime;
    private int emptyPercent;
    private int fishPercent;

    private Paint emptyVisual;
    private Paint fishVisual;
    private Paint sharkVisual;

    private XYChart.Series fishSeries = new XYChart.Series();
    private XYChart.Series sharkSeries = new XYChart.Series();
    private int frame = 0;
    private LineChart lineChart;

    private Map<String,Integer> graphMap = new HashMap<String,Integer>();


    public PredatorPreySimulation() throws XMLException {
        super();
        setProperties(XMLParser.getXmlElement("resources/" + "PredatorPrey.xml"));
        lineChart = this.getGraph();
        setUpChart();
    }


    private void setUpChart() {
        sharkSeries.setName(this.getResources().getString("Shark"));
        fishSeries.setName(this.getResources().getString("Fish"));
        this.getGraph().getData().add(fishSeries);
        this.getGraph().getData().add(sharkSeries);
    }

    @Override
    void assignInitialState(Cell c) {
        int randomNum = getRandomNum(1, 100);
        final PredatorPreyCell ppc = (PredatorPreyCell) c;
        ppc.setVisuals(emptyVisual, fishVisual, sharkVisual);
        if (randomNum <= emptyPercent) {
            ppc.setMark(Mark.EMPTY);
        } else if (randomNum <= emptyPercent + fishPercent) {
            ppc.setMark(Mark.FISH);
        } else {
            ppc.setMark(Mark.SHARK);
        }
    }

    @Override
    public void step() {
        super.step();
        frame ++;
        breedAll();
        moveAll();
        changeStates();
        updateMap();
        updateGraph();
        clearMap();
    }

    private void updateMap(){
        for (Cell cell: getTheCells()){
            addToMap((PredatorPreyCell) cell);
        }
    }

    private void addToMap(PredatorPreyCell cell) {
        String state = cell.getStateString();
        if (graphMap.containsKey(state)){
            graphMap.put(state,graphMap.get(state)+1);
        }
        else{
            graphMap.put(state, 1);
        }
    }

    private void updateGraph() {
        sharkSeries.getData().add(new XYChart.Data(frame,graphMap.get(getResources().getString("SHARK"))));
        fishSeries.getData().add(new XYChart.Data(frame,graphMap.get(getResources().getString("FISH"))));
    }

    private void clearMap() {
        for (Map.Entry entry : graphMap.entrySet()){
            graphMap.put((String) entry.getKey(),0);
        }
    }

    private void breedAll() {
        getTheCells().stream()
                .map(c -> (PredatorPreyCell) c)
                .forEach(ppc -> ppc.breedIfShould(fishBreedTime, sharkBreedTime));
    }

    private void moveAll() {
        getTheCells().stream()
                .map(c -> (PredatorPreyCell) c)
                .forEach(this::doMove);
    }

    private void doMove(PredatorPreyCell ppc) {
        if (ppc.getState() == State.SHARK) {
            sharkUpdate(ppc);
        } else if (ppc.canMoveOrSpawn()) {
            ppc.move();
        }
    }

    private void sharkUpdate(PredatorPreyCell shark) {
        if (shark.shouldStarve(starveTime)) {
            shark.setMark(Mark.EMPTY);
        } else if (!shark.sharkEat()) {
            if (shark.canMoveOrSpawn()) {
                shark.move();
            }
        }
    }

    @Override
    void setSpecificProperties() {
        if (doesTypeMatch("PredatorPrey")) {
            sharkBreedTime = DEFAULT_SHARK_BREED_TIME;
            fishBreedTime = DEFAULT_FISH_BREED_TIME;
            starveTime = DEFAULT_STARVE_TIME;
            emptyPercent = DEFAULT_EMPTY_PERCENT;
            fishPercent = DEFAULT_FISH_PERCENT;
            emptyVisual = DEFAULT_EMPTY_VISUAL;
            fishVisual = DEFAULT_FISH_VISUAL;
            sharkVisual = DEFAULT_SHARK_VISUAL;
        } else {
            sharkBreedTime = xmlProperties.getIntValue("sharkBreedTime");
            fishBreedTime = xmlProperties.getIntValue("fishBreedTime");
            starveTime = xmlProperties.getIntValue("starveTime");
            emptyPercent = xmlProperties.getIntValue("emptyPercent");
            fishPercent = xmlProperties.getIntValue("fishPercent");
            emptyVisual = xmlProperties.getPaintValue("emptyVisual");
            fishVisual = xmlProperties.getPaintValue("fishVisual");
            sharkVisual = xmlProperties.getPaintValue("sharkVisual");
        }
    }

    @Override
    void saveSpecificValues() {
        savedValues.put("sharkBreedTime", sharkBreedTime);
        savedValues.put("fishBreedTime", fishBreedTime);
        savedValues.put("starveTime", starveTime);
        savedValues.put("emptyPercent", emptyPercent);
        savedValues.put("fishPercent", fishPercent);
        savedValues.put("emptyVisual", emptyVisual);
        savedValues.put("fishVisual", fishVisual);
        savedValues.put("sharkVisual", sharkVisual);
    }


    public double getSharkBreedTime() {

        return sharkBreedTime;
    }

    public void setSharkBreed(int newBreedTime) {
        sharkBreedTime = newBreedTime;

    }

    public double getFishBreedTime() {
        return fishBreedTime;
    }

    public void setFishBreed(int newBreedTime) {
        fishBreedTime = newBreedTime;
    }

    public double getStarveTime() {
        return starveTime;
    }

    public void setStarveTime(int newStarveTime) {
        starveTime = newStarveTime;
    }




	@Override
	void assignLoadState(Cell c) {
		// TODO Auto-generated method stub

	}

	@Override
	boolean hasGraph() {
		return true;
	}

}

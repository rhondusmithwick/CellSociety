package Config;

import java.util.ArrayList;
import java.util.List;

import Simulation.PredatorPreySimulation;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Slider;

/**
 *PredatorPreyConfig Class: Class allowing for the addition of sliders to dynamically control this simulation's parameters, 
 * as well as the addition of a graph to monitor the simulation's progress. 
 * <p>
 * Created by bliborio on 2/11/16.
 *
 * @author Bruna Liborio
 */

public class PredatorPreyConfig extends Config {

    private Slider sharkBreedTime;
    private Slider fishBreedTime;
    private Slider starveTime;
    private PredatorPreySimulation ppSim;
    
    @SuppressWarnings("rawtypes")
	private XYChart.Series fishSeries = new XYChart.Series();
    @SuppressWarnings("rawtypes")
	private XYChart.Series sharkSeries = new XYChart.Series();

    private List<XYChart.Series> mySeries = new ArrayList();
    private List<String> mySeriesName = new ArrayList();

    
    public PredatorPreyConfig() {
        super();
        setUpSeriesList();
        setUpGraph(mySeries,mySeriesName);
    }

    @Override
    public void init() {
        ppSim = (PredatorPreySimulation) this.getSimulation();
        createControls();
        createLabels();
    }

    @Override
    public void createLabels() {
        makeLabel(getResources().getString("fishBreed"),4,6,1,1);
        makeLabel(getResources().getString("sharkBreed"),4,7,1,1);
        makeLabel(getResources().getString("starveTime"),4,8,1,1);
    }

    @Override
    public void createControls() {
        sharkBreedTime = createSlider((ov, oldVal, newVal) -> changeSharkBreed(newVal.intValue()),1,30,1,5,7,1,1);
        sharkBreedTime.setValue(ppSim.getSharkBreedTime());
        fishBreedTime = createSlider((ov, oldVal, newVal) -> changeFishBreed(newVal.intValue()),1,30,1,5,6,1,1);
        fishBreedTime.setValue(ppSim.getFishBreedTime());
        starveTime = createSlider((ov, oldVal, newVal) -> changeStarveTime(newVal.intValue()),1,30,1,5,8,1,1);
        starveTime.setValue(ppSim.getStarveTime());
    }
    
    @Override
	boolean hasGraph() {
		return true;
	}
    
	@Override
 	public void updateGraph() {
		updateAllSeries(mySeries,mySeriesName);		
 	}
	
	private void setUpSeriesList(){
 		mySeries.add(sharkSeries);
 		mySeriesName.add(getResources().getString("SHARK"));
 		mySeries.add(fishSeries);
 		mySeriesName.add(getResources().getString("FISH"));
 	}

    private void changeSharkBreed(int intValue) {
        ppSim.setSharkBreed(intValue);
    }

    private void changeFishBreed(int intValue) {
        ppSim.setFishBreed(intValue);

    }

    private void changeStarveTime(int intValue) {
        ppSim.setStarveTime(intValue);
    }
    	  
}

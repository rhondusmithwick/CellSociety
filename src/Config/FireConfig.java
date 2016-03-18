// This entire file is part of my masterpiece.
// Bruna Liborio

package Config;

import Simulation.FireSimulation;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Slider;

import java.util.ArrayList;
import java.util.List;

/**
 * FireConfig Class: Class allowing for the addition of sliders to dynamically control this simulation's parameters.
 * as well as the addition of a graph to monitor the simulation's progress.
 * <p>
 * Created by bliborio on 2/11/16.
 *
 * @author Bruna Liborio
 */

public class FireConfig extends Config {

    private Slider probCatchFire;
    private Slider burnTime;
    private FireSimulation fireSim;

    private XYChart.Series emptySeries = new XYChart.Series();
    private XYChart.Series burningSeries = new XYChart.Series();
    private XYChart.Series treeSeries = new XYChart.Series();

    private List<XYChart.Series> mySeries = new ArrayList();
    private List<String> mySeriesName = new ArrayList();


    public FireConfig() {
        super();
        setUpSeriesList();
        setUpGraph(mySeries, mySeriesName);
    }

    @Override
    public void init() {
        fireSim = (FireSimulation) this.getSimulation();
        createControls();
        createLabels();
    }

    @Override
    public void createLabels() {
        makeLabel(getResources().getString("catchFire"), 4, 6, 1, 1);
        makeLabel(getResources().getString("burnTime"), 4, 7, 1, 1);
    }

    @Override
    public void createControls() {
        probCatchFire = createSlider((ov, oldVal, newVal) -> changeCatchFire(newVal.intValue()), 0, 100, 10, 5, 6, 1, 1);
        probCatchFire.setValue(fireSim.getCatchFire());
        burnTime = createSlider((ov, oldVal, newVal) -> changeBurnTime(newVal.intValue()), 1, 10, 1, 5, 7, 1, 1);
        burnTime.setValue(fireSim.getBurnTime());
    }

    @Override
    boolean hasGraph() {
        return true;
    }

    @Override
    public void updateGraph() {
        updateAllSeries(mySeries, mySeriesName);
    }

    private void setUpSeriesList() {
        mySeries.add(emptySeries);
        mySeriesName.add(getResources().getString("EMPTY"));
        mySeries.add(burningSeries);
        mySeriesName.add(getResources().getString("BURNING"));
        mySeries.add(treeSeries);
        mySeriesName.add(getResources().getString("TREE"));
    }

    private void changeBurnTime(int new_val) {
        fireSim.setBurnTime(new_val);
    }

    private void changeCatchFire(int new_val) {
        fireSim.setProbCatch(new_val);
    }

}
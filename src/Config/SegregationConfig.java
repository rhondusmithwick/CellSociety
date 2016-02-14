package Config;

import Simulation.SegregationSimulation;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

/**
 *SegregationConfig Class: Class allowing for the addition of sliders to dynamically control this simulation's parameters.
 * <p>
 * Created by bliborio on 2/11/16.
 *
 * @author Bruna Liborio
 */

public class SegregationConfig extends Config {

    private SegregationSimulation segSim;
    private Slider threshold;
    private Label thresholdLabel;

    public SegregationConfig() {
        super();
    }

    @Override
    public void init() {
        segSim = (SegregationSimulation) this.getSimulation();
        createControls();
        createLabels();
        setAndAddAll();
    }

    @Override
    public void createLabels() {
        thresholdLabel = makeLabel(getResources().getString("ThresholdLabel"));
    }

    @Override
    public void createControls() {
        createThresholdSlider();
    }

    private void createThresholdSlider() {
        threshold = makeSlider(5, 100, 1);
        ChangeListener<Number> changer = (ov, oldVal, newVal) -> changeThreshold(newVal.intValue());
        threshold.valueProperty().addListener(changer);
        threshold.setValue(segSim.getThreshold());
    }

    private void changeThreshold(int intValue) {
        segSim.setThreshold(intValue);
    }

    @Override
    public void setAndAddAll() {
        setAndAdd(threshold, 5, 6, 1, 1);
        setAndAdd(thresholdLabel, 4, 6, 1, 1);
    }

}

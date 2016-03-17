
package Config;

import Simulation.SegregationSimulation;
import javafx.scene.control.Slider;

/**
 * SegregationConfig Class: Class allowing for the addition of sliders to dynamically control this simulation's parameters.
 * <p>
 * Created by bliborio on 2/11/16.
 *
 * @author Bruna Liborio
 */

public class SegregationConfig extends Config {

    private SegregationSimulation segSim;
    private Slider threshold;

    public SegregationConfig() {
        super();
    }

    @Override
    public void init() {
        segSim = (SegregationSimulation) this.getSimulation();
        createControls();
        createLabels();
    }

    @Override
    public void createLabels() {
        makeLabel(getResources().getString("ThresholdLabel"),4,6,1,1);
    }

    @Override
    public void createControls() {
        threshold = createSlider((ov, oldVal, newVal) -> changeThreshold(newVal.intValue()),5,100,1,5,6,1,1);
        threshold.setValue(segSim.getThreshold());
    }

    private void changeThreshold(int intValue) {
        segSim.setThreshold(intValue);
    }
    
	@Override
	boolean hasGraph() {
		return false;
	}

	@Override
	public void updateGraph() {
		
	}

}

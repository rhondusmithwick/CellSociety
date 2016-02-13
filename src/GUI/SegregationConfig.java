package GUI;

import Simulation.SegregationSimulation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class SegregationConfig extends Config{

	private SegregationSimulation segSim;
	private Slider threshold;
	private Label thresholdLabel;
	
	public SegregationConfig(){
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
		threshold = makeSlider(5,100,1);
	        threshold.valueProperty().addListener(new ChangeListener<Number>() {
	            public void changed(ObservableValue<? extends Number> ov,
	                                Number old_val, Number new_val) {
	                changeThreshold(new_val.intValue());
	            }
	        });
	        threshold.setValue(segSim.getThreshold());
	}
	
	private void changeThreshold(int intValue) {
		segSim.setThreshold(intValue);
	}

	@Override
	public void setAndAddAll() {
		setAndAdd(threshold,5,5,1,1);
		setAndAdd(thresholdLabel,4,5,1,1);
	}

}

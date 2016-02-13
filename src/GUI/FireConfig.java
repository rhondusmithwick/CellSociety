package GUI;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Simulation.FireSimulation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;

public class FireConfig extends Config {
	
	private ResourceBundle myResources = ResourceBundle.getBundle("GUIstrings");
	
	 private Slider probCatchFire;
	 private Slider burnTime;
	 private Label catchFireLabel;
	 private Label burnTimeLabel;
	 private FireSimulation fireSim;
	
	public FireConfig(){
		super();
	}
	
	@Override
	public void init(){
		fireSim = (FireSimulation) this.getSimulation();
		createControls();
		createLabels();
		setAndAddAll();
	}

	@Override
	public void setAndAddAll() {
		setAndAdd(probCatchFire, 5, 5, 1, 1);
		setAndAdd(burnTime, 5, 6, 1, 1);
		setAndAdd(burnTimeLabel, 4, 6, 1, 1);
		setAndAdd(catchFireLabel, 4, 5, 1, 1);
	}
		
	@Override
	public void createLabels() {
    	catchFireLabel = makeLabel(getResources().getString("catchFire"));
    	burnTimeLabel = makeLabel(getResources().getString("burnTime"));
	}

	@Override
	public void createControls() {
	    	probCatchFire = makeSlider(0,100,10);
	    	probCatchFire.valueProperty().addListener(new ChangeListener<Number>() {
	            public void changed(ObservableValue<? extends Number> ov,
	                Number old_val, Number new_val) {
	            		changeCatchFire(new_val.intValue());
	            }
	        });
	    	//probCatchFire.setValue(fireSim.getCatchFire());
	    	burnTime = makeSlider(1,10,1);
	    	burnTime.valueProperty().addListener(new ChangeListener<Number>() {
	    	public void changed(ObservableValue<? extends Number> ov,
	                Number old_val, Number new_val) {
	    			changeBurnTime(new_val.intValue());
	            }
	        });
	    	//burnTime.setValue(fireSim.getBurnTime());
	    }
		
		
	private void changeBurnTime(int new_val) {
		//FireSimulation fireSim = (FireSimulation) this.getSimulation();
		fireSim.setBurnTime(new_val);
	}

	private void changeCatchFire(int new_val) {
		//FireSimulation fireSim = (FireSimulation) this.getSimulation();
		fireSim.setProbCatch(new_val);
	}
		

}
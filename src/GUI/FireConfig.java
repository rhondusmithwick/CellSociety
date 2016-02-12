package GUI;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
	
	public FireConfig(){
		
	}
	
	@Override
	public void init(){
		createControls();
		createLabels();
		setAll();
		addAll();
	}
	
	private void setAll() {
		GridPane.setConstraints(probCatchFire, 5, 5, 1, 1,HPos.CENTER, VPos.CENTER);
		GridPane.setConstraints(burnTime, 5, 6, 1, 1,HPos.CENTER, VPos.CENTER);
		GridPane.setConstraints(burnTimeLabel, 4, 6, 1, 1,HPos.CENTER, VPos.CENTER);
		GridPane.setConstraints(catchFireLabel, 4, 5, 1, 1,HPos.CENTER, VPos.CENTER);
	}
	
	private void addAll(){
		this.addControl(probCatchFire);
		this.addControl(burnTime);
		this.addControl(catchFireLabel);
		this.addControl(burnTimeLabel);
	}
	
	private void createLabels() {
    	catchFireLabel = new Label(myResources.getString("catchFire"));
    	burnTimeLabel = new Label(myResources.getString("burnTime"));
	}

	private void createControls() {
	    	probCatchFire = new Slider(0,1,.1);
	    	probCatchFire.setShowTickMarks(true);
	    	probCatchFire.setShowTickLabels(true);
	    	probCatchFire.valueProperty().addListener(new ChangeListener<Number>() {
	            public void changed(ObservableValue<? extends Number> ov,
	                Number old_val, Number new_val) {
	            		changeCatchFire(new_val.intValue());
	            }
	        });
	    	burnTime = new Slider(2,10,1);
	    	burnTime.setShowTickMarks(true);
	    	burnTime.setShowTickLabels(true);
	    	burnTime.valueProperty().addListener(new ChangeListener<Number>() {
	    	public void changed(ObservableValue<? extends Number> ov,
	                Number old_val, Number new_val) {
	    			changeBurnTime(new_val.intValue());
	            }
	        });
	    }
		
		
	protected void changeBurnTime(int new_val) {
		this.getSimControl().changeBurnTime(new_val);
	}

	protected void changeCatchFire(int new_val) {
		this.getSimControl().changeCatchingFire(new_val);
	}
		

}

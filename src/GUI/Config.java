package GUI;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Simulation.Simulation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;

public abstract class Config {
	
	private static final String GUI_PROPERTY_PATH = "GUIstrings";
	
	private final List<Node> newControls = new ArrayList<>();
	private SimulationControl mySimControl;
	private Simulation mySimulation;
    private Slider mySpeedSlider;
    private Slider mySizeSlider;
    private Label sizeLabel;
    private Label speedLabel;
    private final ResourceBundle myResources;
	
	public Config(){
		super();
		myResources = ResourceBundle.getBundle(GUI_PROPERTY_PATH);
	}
	
    private void changeSize(int new_val){
    	mySimControl.stop();
    	mySimControl.sizeChange(new_val);
    }
	
	public void setSim(SimulationControl newControl, Simulation newSimulation){
		mySimControl = newControl;
		mySimulation = newSimulation;
	}
	
	public SimulationControl getSimControl(){
		return mySimControl;
	}
	
	public Simulation getSimulation(){
		return mySimulation;
	}
	
	public void addControl(Node e){
		newControls.add(e);
	}
	
	public List<Node> getControls(){
		return newControls;
	}
	
	public void createMainLabels(){
    	sizeLabel = new Label(myResources.getString("SizeLabel"));
    	speedLabel = new Label(myResources.getString("SpeedLabel"));
	}
	
    public void createMainSliders(){
    	mySpeedSlider = makeSlider(0,10,1);
    	mySpeedSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
            		mySimControl.speed(new_val.intValue(),old_val.intValue());
            }
        });
    	//mySpeedSlider.setValue(mySimulation.getSpeed());
    	mySizeSlider = makeSlider(2,150,10);
    	mySizeSlider.valueProperty().addListener(new ChangeListener<Number>() {
    	public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
    			changeSize(new_val.intValue());
            }
        });
    	//mySizeSlider.setValue(mySimulation.getSize());
    }
    
    private Slider makeSlider(int start, int end, int incr){
    	Slider slider = new Slider(start,end,incr);
    	slider.setShowTickMarks(true);
    	slider.setShowTickLabels(true);
    	return slider;
    }
    
    public void setMain(){
    	GridPane.setConstraints(speedLabel,1,5,1,1, HPos.CENTER, VPos.CENTER);
    	GridPane.setConstraints(sizeLabel,1,6,1,1, HPos.CENTER, VPos.CENTER );
    	GridPane.setConstraints(mySpeedSlider,2,5,1,1, HPos.CENTER, VPos.CENTER);
    	GridPane.setConstraints(mySizeSlider,2,6,1,1, HPos.CENTER, VPos.CENTER);    	
    }
    
    public void addMain(){
    	this.addControl(speedLabel);
		this.addControl(sizeLabel);
		this.addControl(mySpeedSlider);
		this.addControl(mySizeSlider);
    }

	public abstract void init();
	
	public abstract void createLabels();
	
	public abstract void createControls();
	
	public abstract void addAll();
	
	public abstract void setAll();

}

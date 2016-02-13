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
import javafx.scene.layout.Region;

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
		createMainSliders();
		createMainLabels();
		setAndAddMain();
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
	
	public ResourceBundle getResources(){
		return myResources;
	}
	
    private void changeSize(int new_val){
    	mySimControl.stop();
    	mySimControl.sizeChange(new_val);
    }
	
	public void addControl(Node e){
		newControls.add(e);
	}
	
	public List<Node> getControls(){
		return newControls;
	}
	
	public void createMainLabels(){
    	sizeLabel = makeLabel(getResources().getString("SizeLabel"));
    	speedLabel = makeLabel(getResources().getString("SpeedLabel"));
	}
	
	public Label makeLabel(String string){
		Label newLabel = new Label(string);
		return newLabel;
	}
	
    public void createMainSliders(){
    	mySpeedSlider = makeSlider(0,10,1);
    	mySpeedSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
            		mySimControl.speed(new_val.intValue(),old_val.intValue());
            }
        });
    	mySizeSlider = makeSlider(2,150,10);
    	mySizeSlider.valueProperty().addListener(new ChangeListener<Number>() {
    	public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
    			changeSize(new_val.intValue());
            }
        });
    }
    
    public Slider makeSlider(int start, int end, int incr){
    	Slider slider = new Slider(start,end,incr);
    	slider.setShowTickMarks(true);
    	slider.setShowTickLabels(true);
    	return slider;
    }
    
    public void setAndAddMain(){
    	setAndAdd(speedLabel,1,5,1,1);
    	setAndAdd(sizeLabel,1,6,1,1);
    	setAndAdd(mySpeedSlider,2,5,1,1);
    	setAndAdd(mySizeSlider,2,6,1,1);    	
    }
    
    public void setAndAdd(Node node, int col, int row, int colSpan, int rowSpan) {
        GridPane.setConstraints(node, col, row, colSpan, rowSpan, HPos.CENTER, VPos.CENTER);
        this.addControl(node);
        ((Region) node).setMaxWidth(Double.MAX_VALUE);
    }
    
	public abstract void init();
	
	public abstract void createLabels();
	
	public abstract void createControls();
	
	public abstract void setAndAddAll();

}
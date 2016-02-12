package GUI;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;

public abstract class Config {
	
	private final List<Node> newControls = new ArrayList<>();
	private SimulationControl mySimControl;
	
	public Config(){
		super();
	}
	
	public void setSimControl(SimulationControl newControl){
		mySimControl = newControl;
	}
	
	public SimulationControl getSimControl(){
		return mySimControl;
	}
	
	public void addControl(Node e){
		newControls.add(e);
	}
	
	public List<Node> getControls(){
		return newControls;
	}

	public abstract void init();

}

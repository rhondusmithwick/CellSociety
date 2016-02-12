package GUI;

import java.util.ArrayList;
import java.util.List;

import Simulation.Simulation;
import javafx.scene.Node;

public abstract class Config {
	
	private final List<Node> newControls = new ArrayList<>();
	private SimulationControl mySimControl;
	private Simulation mySimulation;
	
	public Config(){
		super();
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

	public abstract void init();
	
	public abstract void createLabels();
	
	public abstract void createControls();
	
	public abstract void addAll();
	
	public abstract void setAll();

}

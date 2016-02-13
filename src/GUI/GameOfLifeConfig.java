package GUI;

import Simulation.GameOfLifeSimulation;

public class GameOfLifeConfig extends Config {
<<<<<<< HEAD
	
	public GameOfLifeConfig(){
		super();
	}
=======
>>>>>>> 679c17c4e6c13cdbd1200e52a1e80bfe91fd49f8

    @Override
    public void init() {
        GameOfLifeSimulation lifeSim = (GameOfLifeSimulation) this.getSimulation();
        createMainSliders();
        createMainLabels();
<<<<<<< HEAD
        setAndAddMain();
=======
        setMain();
        addMain();
>>>>>>> 679c17c4e6c13cdbd1200e52a1e80bfe91fd49f8
    }

    @Override
    public void createLabels() {
        // TODO Auto-generated method stub

    }

    @Override
    public void createControls() {
        // TODO Auto-generated method stub

    }

    @Override
<<<<<<< HEAD
    public void setAndAddAll() {
=======
    public void addAll() {
>>>>>>> 679c17c4e6c13cdbd1200e52a1e80bfe91fd49f8
        // TODO Auto-generated method stub

    }

<<<<<<< HEAD
=======
    @Override
    public void setAll() {
        // TODO Auto-generated method stub

    }
>>>>>>> 679c17c4e6c13cdbd1200e52a1e80bfe91fd49f8

}
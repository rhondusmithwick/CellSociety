package GUI;

import Simulation.GameOfLifeSimulation;

public class GameOfLifeConfig extends Config {

    public GameOfLifeConfig() {
        super();
    }

    @Override
    public void init() {
        GameOfLifeSimulation lifeSim = (GameOfLifeSimulation) this.getSimulation();
        createMainSliders();
        createMainLabels();
        setAndAddMain();
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
    public void setAndAddAll() {
        // TODO Auto-generated method stub

    }


}
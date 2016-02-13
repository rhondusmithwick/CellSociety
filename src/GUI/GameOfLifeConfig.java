package GUI;

import Simulation.GameOfLifeSimulation;

public class GameOfLifeConfig extends Config {

    @Override
    public void init() {
        GameOfLifeSimulation lifeSim = (GameOfLifeSimulation) this.getSimulation();
        createMainSliders();
        createMainLabels();
        setMain();
        addMain();
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
    public void addAll() {
        // TODO Auto-generated method stub

    }

    @Override
    public void setAll() {
        // TODO Auto-generated method stub

    }

}
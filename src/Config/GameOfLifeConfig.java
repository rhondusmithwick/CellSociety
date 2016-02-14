package Config;

import Simulation.GameOfLifeSimulation;

/**
 *GameOfLifeConfig Class: Class allowing for the addition of sliders to dynamically control this simulation's parameters.
 * <p>
 * Created by bliborio on 2/11/16.
 *
 * @author Bruna Liborio
 */

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
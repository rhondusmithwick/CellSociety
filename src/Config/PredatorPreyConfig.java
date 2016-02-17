// This entire file is part of my masterpiece.
// Bruna Liborio

package Config;

import Simulation.PredatorPreySimulation;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

/**
 *PredatorPreyConfig Class: Class allowing for the addition of sliders to dynamically control this simulation's parameters.
 * <p>
 * Created by bliborio on 2/11/16.
 *
 * @author Bruna Liborio
 */

public class PredatorPreyConfig extends Config {

    private Slider sharkBreedTime;
    private Slider fishBreedTime;
    private Slider starveTime;
    private Label sharkBreedLabel;
    private Label fishBreedLabel;
    private Label starveLabel;
    private PredatorPreySimulation ppSim;

    public PredatorPreyConfig() {
        super();
    }

    @Override
    public void init() {
        ppSim = (PredatorPreySimulation) this.getSimulation();
        createControls();
        createLabels();
    }

    @Override
    public void createLabels() {
        fishBreedLabel = makeLabel(getResources().getString("fishBreed"),4,6,1,1);
        sharkBreedLabel = makeLabel(getResources().getString("sharkBreed"),4,7,1,1);
        starveLabel = makeLabel(getResources().getString("starveTime"),4,8,1,1);
    }

    @Override
    public void createControls() {
        sharkBreedTime = createSlider((ov, oldVal, newVal) -> changeSharkBreed(newVal.intValue()),1,30,1,5,7,1,1);
        sharkBreedTime.setValue(ppSim.getSharkBreedTime());
        fishBreedTime = createSlider((ov, oldVal, newVal) -> changeFishBreed(newVal.intValue()),1,30,1,5,6,1,1);
        fishBreedTime.setValue(ppSim.getFishBreedTime());
        starveTime = createSlider((ov, oldVal, newVal) -> changeStarveTime(newVal.intValue()),1,30,1,5,8,1,1);
        starveTime.setValue(ppSim.getStarveTime());
    }

    private void changeSharkBreed(int intValue) {
        ppSim.setSharkBreed(intValue);
    }

    private void changeFishBreed(int intValue) {
        ppSim.setFishBreed(intValue);

    }

    private void changeStarveTime(int intValue) {
        ppSim.setStarveTime(intValue);
    }

}
// This entire file is part of my masterpiece.
// Bruna Liborio

package Config;

import Simulation.FireSimulation;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import java.util.ResourceBundle;

/**
 *FireConfig Class: Class allowing for the addition of sliders to dynamically control this simulation's parameters.
 * <p>
 * Created by bliborio on 2/11/16.
 *
 * @author Bruna Liborio
 */

public class FireConfig extends Config {

    private Slider probCatchFire;
    private Slider burnTime;
    private Label catchFireLabel;
    private Label burnTimeLabel;
    private FireSimulation fireSim;

    public FireConfig() {
        super();
    }

    @Override
    public void init() {
        fireSim = (FireSimulation) this.getSimulation();
        createControls();
        createLabels();
    }

    @Override
    public void createLabels() {
        catchFireLabel = makeLabel(getResources().getString("catchFire"),4,6,1,1);
        burnTimeLabel = makeLabel(getResources().getString("burnTime"),4,7,1,1);
    }

    @Override
    public void createControls() {
        probCatchFire = createSlider((ov, oldVal, newVal) -> changeCatchFire(newVal.intValue()),0,100,10,5,6,1,1);
        probCatchFire.setValue(fireSim.getCatchFire());
        burnTime = createSlider((ov, oldVal, newVal) -> changeBurnTime(newVal.intValue()),1,10,1,5,7,1,1);
        burnTime.setValue(fireSim.getBurnTime());
    }

    private void changeBurnTime(int new_val) {
        fireSim.setBurnTime(new_val);
    }

    private void changeCatchFire(int new_val) {
        fireSim.setProbCatch(new_val);
    }

}
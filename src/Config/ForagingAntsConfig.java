// This entire file is part of my masterpiece.
// Bruna Liborio

package Config;

import Simulation.ForagingAntsSimulation;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

/**
 *ForagingAntsConfig Class: Class allowing for the addition of sliders to dynamically control this simulation's parameters.
 * <p>
 * Created by bliborio on 2/11/16.
 *
 * @author Bruna Liborio
 */

public class ForagingAntsConfig extends Config {

    private Slider diffusionRate;
    private Slider evaporationRate;
    private Slider antsBorn;
    private Slider antLifeTime;
    private Slider maxAnts;
    private Slider K;
    private Slider N;
    private Label diffusionLabel;
    private Label evaporationLabel;
    private Label antsBornLabel;
    private Label antsLifeTimeLabel;
    private Label maxAntsLabel;
    private Label NLabel;
    private Label KLabel;
    private ForagingAntsSimulation antsSim;

    public ForagingAntsConfig() {
        super();
    }

    @Override
    public void init() {
        antsSim = (ForagingAntsSimulation) this.getSimulation();
        createControls();
        createLabels();
        this.deactivateSize();
    }

    @Override
    public void createLabels() {
        diffusionLabel = makeLabel(getResources().getString("DiffusionLabel"), 4,7,1,1);
        evaporationLabel = makeLabel(getResources().getString("EvaporationLabel"), 4,6,1,1);
        antsBornLabel = makeLabel(getResources().getString("AntsBornLabel"), 4,10,1,1);
        antsLifeTimeLabel = makeLabel(getResources().getString("AntsLifeTimeLabel"),4,9,1,1);
        maxAntsLabel = makeLabel(getResources().getString("MaxAntsLabel"),4,8,1,1);
        NLabel = makeLabel(getResources().getString("NLabel"),1,8,1,1);
        KLabel = makeLabel(getResources().getString("KLabel"),1,9,1,1);     
    }

    @Override
    public void createControls() {
        diffusionRate = createSlider((ov, oldVal, newVal) -> changeDiffusionRate(newVal.doubleValue()),.001,1,.01,5,7,1,1);
        diffusionRate.setValue(antsSim.getDiffusionRate());
        evaporationRate = createSlider((ov, oldVal, newVal) -> changeEvaporationRate(newVal.doubleValue()),.001,1,.01,5,6,1,1);
        evaporationRate.setValue(antsSim.getEvaporationRate());
        antsBorn = createSlider((ov, oldVal, newVal) -> changeAntsBorn(newVal.doubleValue()), 0,500,20,5,10,1,1);
        antsBorn.setValue(antsSim.getAntsBorn());
        antLifeTime = createSlider((ov, oldVal, newVal) -> changeAntLifeTime(newVal.doubleValue()),100,4900,400,5,9,1,1);
        antLifeTime.setValue(antsSim.getLifeTime());
        maxAnts = createSlider((ov, oldVal, newVal) -> changeMaxAnts(newVal.doubleValue()),500,10000,500,5,8,1,1);
        maxAnts.setValue(antsSim.getMaxAnts());
        K = createSlider((ov, oldVal, newVal) -> changeK(newVal.doubleValue()),.001,1,.01,2,9,1,1);
        K.setValue(antsSim.getK());
        N = createSlider((ov, oldVal, newVal) -> changeN(newVal.doubleValue()),5,100,5,2,8,1,1);
        N.setValue(antsSim.getN());
    }

    private void changeN(double d) {
        antsSim.setN(d);
    }

    private void changeK(double d) {
        antsSim.setK(d);
    }

    private void changeMaxAnts(double d) {
        antsSim.setMaxAnts(d);
    }

    private void changeAntLifeTime(double d) {
        antsSim.setLifeTime(d);
    }

    private void changeAntsBorn(double d) {
        antsSim.setAntsBorn(d);
    }

    private void changeEvaporationRate(double d) {
        antsSim.setEvaporationRate(d);
    }

    private void changeDiffusionRate(double d) {
        antsSim.setDiffusionRate(d);
    }

}

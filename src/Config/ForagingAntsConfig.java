package Config;

import Simulation.ForagingAntsSimulation;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

/**
 * ForagingAntsConfig Class: Class allowing for the addition of sliders to dynamically control this simulation's parameters.
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
        setAndAddAll();
    }

    @Override
    public void createLabels() {
        diffusionLabel = makeLabel(getResources().getString("DiffusionLabel"));
        evaporationLabel = makeLabel(getResources().getString("EvaporationLabel"));
        antsBornLabel = makeLabel(getResources().getString("AntsBornLabel"));
        antsLifeTimeLabel = makeLabel(getResources().getString("AntsLifeTimeLabel"));
        maxAntsLabel = makeLabel(getResources().getString("MaxAntsLabel"));
        NLabel = makeLabel(getResources().getString("NLabel"));
        KLabel = makeLabel(getResources().getString("KLabel"));
    }

    @Override
    public void createControls() {
        createDiffusionSlider();
        createEvaporationSlider();
        createAntsBornSlider();
        createAntLifeTimeSlider();
        createMaxAntsSlider();
        createKSlider();
        createNSlider();
    }

    private void createDiffusionSlider() {
        diffusionRate = makeSlider(.001, 1, .01);
        ChangeListener<Number> diffusionListener = (ov, oldVal, newVal) -> changeDiffusionRate(newVal.doubleValue());
        diffusionRate.valueProperty().addListener(diffusionListener);
        diffusionRate.setValue(antsSim.getDiffusionRate());
    }

    private void createEvaporationSlider() {
        evaporationRate = makeSlider(.001, 1, .01);
        ChangeListener<Number> evaporationListener = (ov, oldVal, newVal) -> changeEvaporationRate(newVal.doubleValue());
        evaporationRate.valueProperty().addListener(evaporationListener);
        evaporationRate.setValue(antsSim.getEvaporationRate());
    }

    private void createAntsBornSlider() {
        antsBorn = makeSlider(0, 500, 20);
        ChangeListener<Number> antsBornListener = (ov, oldVal, newVal) -> changeAntsBorn(newVal.doubleValue());
        antsBorn.valueProperty().addListener(antsBornListener);
        antsBorn.setValue(antsSim.getAntsBorn());
    }


    private void createAntLifeTimeSlider() {
        antLifeTime = makeSlider(100, 4900, 400);
        ChangeListener<Number> antLifeTimeListener = (ov, oldVal, newVal) -> changeAntLifeTime(newVal.doubleValue());
        antLifeTime.valueProperty().addListener(antLifeTimeListener);
        antLifeTime.setValue(antsSim.getLifeTime());
    }

    private void createMaxAntsSlider() {
        maxAnts = makeSlider(500, 10000, 500);
        ChangeListener<Number> maxAntsListener = (ov, oldVal, newVal) -> changeMaxAnts(newVal.doubleValue());
        maxAnts.valueProperty().addListener(maxAntsListener);
        maxAnts.setValue(antsSim.getMaxAnts());
    }

    private void createKSlider() {
        K = makeSlider(.001, 1, .01);
        ChangeListener<Number> kListener = (ov, oldVal, newVal) -> changeK(newVal.doubleValue());
        K.valueProperty().addListener(kListener);
        K.setValue(antsSim.getK());
    }

    private void createNSlider() {
        N = makeSlider(5, 100, 5);
        ChangeListener<Number> nListener = (ov, oldVal, newVal) -> changeN(newVal.doubleValue());
        N.valueProperty().addListener(nListener);
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

    @Override
    public void setAndAddAll() {
        setAndAdd(evaporationRate, 5, 6, 1, 1);
        setAndAdd(diffusionRate, 5, 7, 1, 1);
        setAndAdd(maxAnts, 5, 8, 1, 1);
        setAndAdd(antLifeTime, 5, 9, 1, 1);
        setAndAdd(antsBorn, 5, 10, 1, 1);
        setAndAdd(antsBornLabel, 4, 10, 1, 1);
        setAndAdd(antsLifeTimeLabel, 4, 9, 1, 1);
        setAndAdd(maxAntsLabel, 4, 8, 1, 1);
        setAndAdd(diffusionLabel, 4, 7, 1, 1);
        setAndAdd(N, 2, 8, 1, 1);
        setAndAdd(K, 2, 9, 1, 1);
        setAndAdd(NLabel, 1, 8, 1, 1);
        setAndAdd(KLabel, 1, 9, 1, 1);
        setAndAdd(evaporationLabel, 4, 6, 1, 1);
        this.deactivateSize();
    }

}

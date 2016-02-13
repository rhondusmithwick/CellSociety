package GUI;

import Simulation.FireSimulation;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import java.util.ResourceBundle;

public class FireConfig extends Config {

    private ResourceBundle myResources = ResourceBundle.getBundle("GUIstrings");

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
        setAndAddAll();
    }

    @Override
    public void setAndAddAll() {
        setAndAdd(probCatchFire, 5, 5, 1, 1);
        setAndAdd(burnTime, 5, 6, 1, 1);
        setAndAdd(burnTimeLabel, 4, 6, 1, 1);
        setAndAdd(catchFireLabel, 4, 5, 1, 1);
    }

    @Override
    public void createLabels() {
        catchFireLabel = makeLabel(getResources().getString("catchFire"));
        burnTimeLabel = makeLabel(getResources().getString("burnTime"));
    }

    @Override
    public void createControls() {
        createProbCatchFireSlider();
        createBurnTimeSlider();
    }

    private void createProbCatchFireSlider() {
        probCatchFire = makeSlider(0, 100, 10);
        ChangeListener<Number> catchFireChanger = (ov, oldVal, newVal) -> changeCatchFire(newVal.intValue());
        probCatchFire.valueProperty().addListener(catchFireChanger);
        probCatchFire.setValue(fireSim.getCatchFire());
    }

    private void createBurnTimeSlider() {
        burnTime = makeSlider(1, 10, 1);
        ChangeListener<Number> burnTimeChanger = (ov, oldVal, newVal) -> changeBurnTime(newVal.intValue());
        burnTime.valueProperty().addListener(burnTimeChanger);
        burnTime.setValue(fireSim.getBurnTime());
    }

    private void changeBurnTime(int new_val) {
        fireSim.setBurnTime(new_val);
    }

    private void changeCatchFire(int new_val) {
        fireSim.setProbCatch(new_val);
    }


}
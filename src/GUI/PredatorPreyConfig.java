package GUI;

import Simulation.PredatorPreySimulation;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

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
        setAndAddAll();
    }

    @Override
    public void createLabels() {
        fishBreedLabel = makeLabel(getResources().getString("fishBreed"));
        sharkBreedLabel = makeLabel(getResources().getString("sharkBreed"));
        starveLabel = makeLabel(getResources().getString("starveTime"));
    }

    @Override
    public void createControls() {
        createSharkBreedTimeSlider();
        createFishBreedTimeSlider();
        createStarveTimeSlider();
    }


    private void createSharkBreedTimeSlider() {
        sharkBreedTime = makeSlider(1, 30, 1);
        ChangeListener<Number> sharkChanger = (ov, oldVal, newVal) -> changeSharkBreed(newVal.intValue());
        sharkBreedTime.valueProperty().addListener(sharkChanger);
        sharkBreedTime.setValue(ppSim.getSharkBreedTime());
    }

    private void createFishBreedTimeSlider() {
        fishBreedTime = makeSlider(1, 30, 1);
        ChangeListener<Number> fishChanger = (ov, oldVal, newVal) -> changeFishBreed(newVal.intValue());
        fishBreedTime.valueProperty().addListener(fishChanger);
        fishBreedTime.setValue(ppSim.getFishBreedTime());
    }

    private void createStarveTimeSlider() {
        starveTime = makeSlider(1, 30, 1);
        ChangeListener<Number> starveChanger = (ov, oldVal, newVal) -> changeStarveTime(newVal.intValue());
        starveTime.valueProperty().addListener(starveChanger);
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

    @Override
    public void setAndAddAll() {
        setAndAdd(fishBreedTime, 5, 5, 1, 1);
        setAndAdd(sharkBreedTime, 5, 6, 1, 1);
        setAndAdd(starveTime, 5, 7, 1, 1);
        setAndAdd(starveLabel, 4, 7, 1, 1);
        setAndAdd(sharkBreedLabel, 4, 6, 1, 1);
        setAndAdd(fishBreedLabel, 4, 5, 1, 1);
    }

}
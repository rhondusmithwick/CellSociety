package GUI;

import Simulation.PredatorPreySimulation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;

import java.util.ResourceBundle;

public class PredatorPreyConfig extends Config {

    private Slider sharkBreedTime;
    private Slider fishBreedTime;
    private Slider starveTime;
    private Label sharkBreedLabel;
    private Label fishBreedLabel;
    private Label starveLabel;
    private PredatorPreySimulation ppSim;
    
    public PredatorPreyConfig(){
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
        sharkBreedTime = makeSlider(1, 30, 1);
        sharkBreedTime.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                changeSharkBreed(new_val.intValue());
            }
        });
        sharkBreedTime.setValue(ppSim.getSharkBreedTime());
        fishBreedTime = makeSlider(1, 30, 1);
        fishBreedTime.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                changeFishBreed(new_val.intValue());
            }
        });
        fishBreedTime.setValue(ppSim.getFishBreedTime());
        starveTime = makeSlider(1, 30, 1);
        starveTime.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                changeStarveTime(new_val.intValue());
            }
        });
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
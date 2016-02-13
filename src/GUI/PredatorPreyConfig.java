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

    private final ResourceBundle myResources = ResourceBundle.getBundle("GUIstrings");

    private Slider sharkBreedTime;
    private Slider fishBreedTime;
    private Slider starveTime;
    private Label sharkBreedLabel;
    private Label fishBreedLabel;
    private Label starveLabel;
    private PredatorPreySimulation ppSim;

    @Override
    public void init() {
        ppSim = (PredatorPreySimulation) this.getSimulation();
        createMainSliders();
        createMainLabels();
        setMain();
        addMain();
        createControls();
        createLabels();
        setAll();
        addAll();
    }

    @Override
    public void createLabels() {
        fishBreedLabel = new Label(myResources.getString("fishBreed"));
        sharkBreedLabel = new Label(myResources.getString("sharkBreed"));
        starveLabel = new Label(myResources.getString("starveTime"));
    }

    @Override
    public void createControls() {
        sharkBreedTime = new Slider(1, 30, 1);
        sharkBreedTime.setShowTickMarks(true);
        sharkBreedTime.setShowTickLabels(true);
        sharkBreedTime.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                changeSharkBreed(new_val.intValue());
            }
        });
        sharkBreedTime.setValue(ppSim.getSharkBreedTime());
        fishBreedTime = new Slider(1, 30, 1);
        fishBreedTime.setShowTickMarks(true);
        fishBreedTime.setShowTickLabels(true);
        fishBreedTime.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                changeFishBreed(new_val.intValue());
            }
        });
        fishBreedTime.setValue(ppSim.getFishBreedTime());
        starveTime = new Slider(1, 30, 1);
        starveTime.setShowTickMarks(true);
        starveTime.setShowTickLabels(true);
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
    public void addAll() {
        this.addControl(fishBreedTime);
        this.addControl(sharkBreedTime);
        this.addControl(starveTime);
        this.addControl(starveLabel);
        this.addControl(fishBreedLabel);
        this.addControl(sharkBreedLabel);
    }

    @Override
    public void setAll() {
        GridPane.setConstraints(fishBreedTime, 5, 5, 1, 1, HPos.CENTER, VPos.CENTER);
        GridPane.setConstraints(sharkBreedTime, 5, 6, 1, 1, HPos.CENTER, VPos.CENTER);
        GridPane.setConstraints(starveTime, 5, 7, 1, 1, HPos.CENTER, VPos.CENTER);
        GridPane.setConstraints(starveLabel, 4, 7, 1, 1, HPos.CENTER, VPos.CENTER);
        GridPane.setConstraints(sharkBreedLabel, 4, 6, 1, 1, HPos.CENTER, VPos.CENTER);
        GridPane.setConstraints(fishBreedLabel, 4, 5, 1, 1, HPos.CENTER, VPos.CENTER);
    }

}
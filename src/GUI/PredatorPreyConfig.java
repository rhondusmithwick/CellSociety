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

<<<<<<< HEAD
=======
    private final ResourceBundle myResources = ResourceBundle.getBundle("GUIstrings");

>>>>>>> 679c17c4e6c13cdbd1200e52a1e80bfe91fd49f8
    private Slider sharkBreedTime;
    private Slider fishBreedTime;
    private Slider starveTime;
    private Label sharkBreedLabel;
    private Label fishBreedLabel;
    private Label starveLabel;
    private PredatorPreySimulation ppSim;
<<<<<<< HEAD
    
    public PredatorPreyConfig(){
    	super();
    }
=======
>>>>>>> 679c17c4e6c13cdbd1200e52a1e80bfe91fd49f8

    @Override
    public void init() {
        ppSim = (PredatorPreySimulation) this.getSimulation();
<<<<<<< HEAD
        createControls();
        createLabels();
        setAndAddAll();
=======
        createMainSliders();
        createMainLabels();
        setMain();
        addMain();
        createControls();
        createLabels();
        setAll();
        addAll();
>>>>>>> 679c17c4e6c13cdbd1200e52a1e80bfe91fd49f8
    }

    @Override
    public void createLabels() {
<<<<<<< HEAD
        fishBreedLabel = makeLabel(getResources().getString("fishBreed"));
        sharkBreedLabel = makeLabel(getResources().getString("sharkBreed"));
        starveLabel = makeLabel(getResources().getString("starveTime"));
=======
        fishBreedLabel = new Label(myResources.getString("fishBreed"));
        sharkBreedLabel = new Label(myResources.getString("sharkBreed"));
        starveLabel = new Label(myResources.getString("starveTime"));
>>>>>>> 679c17c4e6c13cdbd1200e52a1e80bfe91fd49f8
    }

    @Override
    public void createControls() {
<<<<<<< HEAD
        sharkBreedTime = makeSlider(1, 30, 1);
=======
        sharkBreedTime = new Slider(1, 30, 1);
        sharkBreedTime.setShowTickMarks(true);
        sharkBreedTime.setShowTickLabels(true);
>>>>>>> 679c17c4e6c13cdbd1200e52a1e80bfe91fd49f8
        sharkBreedTime.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                changeSharkBreed(new_val.intValue());
            }
        });
        sharkBreedTime.setValue(ppSim.getSharkBreedTime());
<<<<<<< HEAD
        fishBreedTime = makeSlider(1, 30, 1);
=======
        fishBreedTime = new Slider(1, 30, 1);
        fishBreedTime.setShowTickMarks(true);
        fishBreedTime.setShowTickLabels(true);
>>>>>>> 679c17c4e6c13cdbd1200e52a1e80bfe91fd49f8
        fishBreedTime.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                changeFishBreed(new_val.intValue());
            }
        });
        fishBreedTime.setValue(ppSim.getFishBreedTime());
<<<<<<< HEAD
        starveTime = makeSlider(1, 30, 1);
=======
        starveTime = new Slider(1, 30, 1);
        starveTime.setShowTickMarks(true);
        starveTime.setShowTickLabels(true);
>>>>>>> 679c17c4e6c13cdbd1200e52a1e80bfe91fd49f8
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
<<<<<<< HEAD
    public void setAndAddAll() {
        setAndAdd(fishBreedTime, 5, 5, 1, 1);
        setAndAdd(sharkBreedTime, 5, 6, 1, 1);
        setAndAdd(starveTime, 5, 7, 1, 1);
        setAndAdd(starveLabel, 4, 7, 1, 1);
        setAndAdd(sharkBreedLabel, 4, 6, 1, 1);
        setAndAdd(fishBreedLabel, 4, 5, 1, 1);
=======
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
>>>>>>> 679c17c4e6c13cdbd1200e52a1e80bfe91fd49f8
    }

}
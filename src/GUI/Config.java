package GUI;

import Simulation.Simulation;
import javafx.beans.value.ChangeListener;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

abstract class Config {

    private static final String GUI_PROPERTY_PATH = "GUIstrings";

    private final List<Node> newControls = new ArrayList<>();
    private final ResourceBundle myResources;
    private SimulationControl mySimControl;
    private Simulation mySimulation;
    private Slider mySpeedSlider;
    private Slider mySizeSlider;
    private Label sizeLabel;
    private Label speedLabel;

    Config() {
        super();
        myResources = ResourceBundle.getBundle(GUI_PROPERTY_PATH);
        createMainSliders();
        createMainLabels();
        setAndAddMain();
    }

    public void setSim(SimulationControl newControl, Simulation newSimulation) {
        mySimControl = newControl;
        mySimulation = newSimulation;
    }

    public SimulationControl getSimControl() {
        return mySimControl;
    }

    Simulation getSimulation() {
        return mySimulation;
    }

    ResourceBundle getResources() {
        return myResources;
    }

    private void changeSize(int new_val) {
        mySimControl.stop();
        mySimControl.sizeChange(new_val);
    }

    private void addControl(Node e) {
        newControls.add(e);
    }

    public List<Node> getControls() {
        return newControls;
    }

    void createMainLabels() {
        sizeLabel = makeLabel(getResources().getString("SizeLabel"));
        speedLabel = makeLabel(getResources().getString("SpeedLabel"));
    }

    Label makeLabel(String string) {
        return new Label(string);
    }

    void createMainSliders() {
        createSpeedSlider();
        createSizeSlider();
    }

    private void createSpeedSlider() {
        mySpeedSlider = makeSlider(0, 10, 1);
        ChangeListener<Number> speedChanger = (ov, oldVal, newVal)
                -> mySimControl.speed(newVal.intValue(), oldVal.intValue());
        mySpeedSlider.valueProperty().addListener(speedChanger);
    }

    private void createSizeSlider() {
        mySizeSlider = makeSlider(2, 150, 10);
        ChangeListener<Number> sizeChanger = (ov, oldVal, newVal)
                -> changeSize(newVal.intValue());
        mySizeSlider.valueProperty().addListener(sizeChanger);
    }

    Slider makeSlider(int start, int end, int incr) {
        Slider slider = new Slider(start, end, incr);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        return slider;
    }

    void setAndAddMain() {
        setAndAdd(speedLabel, 1, 5, 1, 1);
        setAndAdd(sizeLabel, 1, 6, 1, 1);
        setAndAdd(mySpeedSlider, 2, 5, 1, 1);
        setAndAdd(mySizeSlider, 2, 6, 1, 1);
    }

    void setAndAdd(Node node, int col, int row, int colSpan, int rowSpan) {
        GridPane.setConstraints(node, col, row, colSpan, rowSpan, HPos.CENTER, VPos.CENTER);
        this.addControl(node);
        ((Region) node).setMaxWidth(Double.MAX_VALUE);
    }

    public abstract void init();

    public abstract void createLabels();

    public abstract void createControls();

    public abstract void setAndAddAll();


}
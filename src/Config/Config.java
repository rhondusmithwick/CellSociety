package Config;

import GUI.SimulationControl;
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

/**
 * Config Class: Abstract class allowing for the addition of sliders to dynamically control the simulation.
 * <p>
 * Created by bliborio on 2/11/16.
 *
 * @author Bruna Liborio
 */

public abstract class Config {

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

    public final void setSim(SimulationControl newControl, Simulation newSimulation) {
        mySimControl = newControl;
        mySimulation = newSimulation;
    }

    public final SimulationControl getSimControl() {
        return mySimControl;
    }

    final Simulation getSimulation() {
        return mySimulation;
    }

    final ResourceBundle getResources() {
        return myResources;
    }

    private void changeSize(int newSize) {
        mySimControl.stop();
        mySimControl.sizeChange(newSize);
    }

    private void addControl(Node e) {
        newControls.add(e);
    }

    public final List<Node> getControls() {
        return newControls;
    }

    final void createMainLabels() {
        sizeLabel = makeLabel(getResources().getString("SizeLabel"));
        speedLabel = makeLabel(getResources().getString("SpeedLabel"));
    }

    final Label makeLabel(String string) {
        return new Label(string);
    }

    final void createMainSliders() {
        createSpeedSlider();
        createSizeSlider();
    }

    private void createSpeedSlider() {
        mySpeedSlider = makeSlider(0, 10, 1);
        ChangeListener<Number> speedChanger = (ov, oldVal, newVal)
                -> mySimControl.speed(newVal.intValue(), oldVal.intValue());
        mySpeedSlider.valueProperty().addListener(speedChanger);
    }

    final void deactivateSize() {
        mySizeSlider.setDisable(true);
    }

    private void createSizeSlider() {
        mySizeSlider = makeSlider(2, 150, 10);
        ChangeListener<Number> sizeChanger = (ov, oldVal, newVal)
                -> changeSize(newVal.intValue());
        mySizeSlider.valueProperty().addListener(sizeChanger);
    }

    Slider makeSlider(double start, double end, double inc) {
        Slider slider = new Slider(start, end, inc);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        return slider;
    }

    final void setAndAddMain() {
        setAndAdd(speedLabel, 1, 6, 1, 1);
        setAndAdd(sizeLabel, 1, 7, 1, 1);
        setAndAdd(mySpeedSlider, 2, 6, 1, 1);
        setAndAdd(mySizeSlider, 2, 7, 1, 1);
    }

    final void setAndAdd(Node node, int col, int row, int colSpan, int rowSpan) {
        GridPane.setConstraints(node, col, row, colSpan, rowSpan, HPos.CENTER, VPos.CENTER);
        this.addControl(node);
        ((Region) node).setMaxWidth(Double.MAX_VALUE);
    }

    public abstract void init();

    public abstract void createLabels();

    public abstract void createControls();

    public abstract void setAndAddAll();


}
// This entire file is part of my masterpiece.
// Bruna Liborio

package Config;

import GUI.SimulationControl;
import Simulation.Simulation;
import javafx.beans.value.ChangeListener;
import javafx.event.Event;
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
        myResources = ResourceBundle.getBundle(GUI_PROPERTY_PATH);
        createMainSliders();
        createMainLabels();
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

    private void addControl(Node control) {
        newControls.add(control);
    }

    public final List<Node> getControls() {
        return newControls;
    }

    public void createMainLabels() {
        sizeLabel = makeLabel(getResources().getString("SizeLabel"), 1,7,1,1);
        speedLabel = makeLabel(getResources().getString("SpeedLabel"), 1,6,1,1);
    }
    
    final Label makeLabel(String string, int col, int row, int colSpan, int rowSpan) {
        Label label = new Label(string);
        setAndAdd(label,col,row,colSpan,rowSpan);
    	return label;
    }

   public void createMainSliders() {
        mySpeedSlider = createSlider((ov, oldVal, newVal)
                -> mySimControl.speed(newVal.intValue(), oldVal.intValue()), 1, 10, 1, 2, 6, 1, 1);
        mySizeSlider = createSlider((ov, oldVal, newVal)
                -> changeSize(newVal.intValue()), 2, 150, 10, 2, 7, 1, 1);
    }

    public Slider createSlider(ChangeListener<Number> listener, double d, double end, double e, int col, int row, int colSpan, int rowSpan ){
    	Slider slider = makeSlider(d, end, e);
        ChangeListener<Number> changer = listener;
        slider.valueProperty().addListener (changer);
        setAndAdd(slider,col,row,colSpan,rowSpan);
        return slider;
    }

    Slider makeSlider(double start, double end, double inc) {
        Slider slider = new Slider(start, end, inc);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        return slider;
    }

    final void setAndAdd(Node node, int col, int row, int colSpan, int rowSpan) {
        GridPane.setConstraints(node, col, row, colSpan, rowSpan, HPos.CENTER, VPos.CENTER);
        this.addControl(node);
        ((Region) node).setMaxWidth(Double.MAX_VALUE);
    }
    
    final void deactivateSize() {
        mySizeSlider.setDisable(true);
    }

    public abstract void init();

    public abstract void createLabels();

    public abstract void createControls();

}
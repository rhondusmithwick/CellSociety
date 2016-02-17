// This entire file is part of my masterpiece.
// Bruna Liborio

/*This config class heirarchy is my masterpiece. I edited the code to include graphing withint the config classes
instead of within the simulation classes as it was before. I also refactored the code so that sliders and labels
can be added with just a single line change to the code either within the createControls or createLabels methods 
of the subclasses by calling the createLabels or createSliders method in this class and giving the appropriate
parameters as specified by the user. The changes have made it much easier to add controls and graphs within
the config file. Changes also had to be made to simulation classes in order to get this new code to work but the 
simulation class changes are not as prestine and are not included in this masterpiece. Three example config files
have also been included to show the changes in action.*/ 

package Config;

import GUI.SimulationControl;
import Simulation.Simulation;
import javafx.beans.value.ChangeListener;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Config Class: Abstract class allowing for the addition of sliders to dynamically control the simulation.
 * as well as the addition of a graph to monitor the simulation's progress. 
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
    
    private final NumberAxis xAxis = new NumberAxis();
    private final NumberAxis yAxis = new NumberAxis();
    private LineChart<Number, Number> lineChart;

    Config() {
        myResources = ResourceBundle.getBundle(GUI_PROPERTY_PATH);
        createMainSliders();
        createMainLabels();
        createGraph();
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
        makeLabel(getResources().getString("SizeLabel"), 1,7,1,1);
        makeLabel(getResources().getString("SpeedLabel"), 1,6,1,1);
    }
    
    /**
     * Creates a new label, setting all necessary locations. 
     * 
     * @param string 								the text displayed on the label
     *    @param col 								column index for the label on the gridpane
     *     @param row 								row index for the label on the gridpane
     *      @param colSpan 								the number of columns spanned by the label on the gridpane
     *      @param rowSpan 								the number of rows spanned by the label on the gridpane
     * @return label							the new label
     * 
     */
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

   
   /**
    * Creates a new slider, setting all necessary intervals and locations. 
    *
    * @param ChangeListener<Number>				the change the slider is listening to and event/method it calls when the change occurs
    * @param start 								the start of the slider range
    *  @param end 							the end of the slider range
    *   @param incr 								the increment of the slider range
    *    @param col 								column index for the slider on the gridpane
    *     @param row 								row index for the slider on the gridpane
    *      @param colSpan 								the number of columns spanned by the slider on the gridpane
    *      @param rowSpan 								the number of rows spanned by the slider on the gridpane
    * @return slider 							the new slider
    * 
    */
    public Slider createSlider(ChangeListener<Number> listener, double start, double end, double incr, int col, int row, int colSpan, int rowSpan ){
    	Slider slider = new Slider(start, end, incr);
    	slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        ChangeListener<Number> changer = listener;
        slider.valueProperty().addListener (changer);
        setAndAdd(slider,col,row,colSpan,rowSpan);
        return slider;
    }

    public void setAndAdd(Node node, int col, int row, int colSpan, int rowSpan) {
        GridPane.setConstraints(node, col, row, colSpan, rowSpan, HPos.CENTER, VPos.CENTER);
        this.addControl(node);
        ((Region) node).setMaxWidth(Double.MAX_VALUE);
    }
    
    public void deactivateSize() {
        mySizeSlider.setDisable(true);
    }
    
    public void deactivateSpeed(){
    	mySpeedSlider.setDisable(true);
    }
    
	public void setUpGraph(List<XYChart.Series> series, List<String> strings) {
    	int i = 0;
		for (XYChart.Series serie : series){
		serie.setName(strings.get(i));
		this.getGraph().getData().add(serie);
		i ++;
    	}
	}
	
	public void updateAllSeries(List<XYChart.Series> series, List<String> strings){
		int i = 0;
		for (XYChart.Series serie : series){
			serie.getData().add(new XYChart.Data(getSimulation().getFrame(), getSimulation().getNumOfState(strings.get(i))));
		i ++;
    	}
	}
    
    private void createGraph() {
        xAxis.setLabel(myResources.getString("Frame"));
        yAxis.setLabel(myResources.getString("NumberOfCells"));
        lineChart = new LineChart<Number, Number>(xAxis, yAxis);
    }

    public LineChart<Number, Number> getGraph() {
        return lineChart;
    }
    
    public boolean setGraph(GridPane graph) {
        graph.getChildren().add(lineChart);
        return hasGraph();
    }
        
    abstract boolean hasGraph();

    public abstract void init();

    public abstract void createLabels();

    public abstract void createControls();
    
    public abstract void updateGraph();

}
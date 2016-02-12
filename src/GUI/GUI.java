package GUI;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import Cell.Cell;
import Cell.FireCell;

/**
 * GUI class: handles the displaying and set up of all GUI elements besides the
 * grid. Handles buttons, drop down bars, and user interaction boxes.
 * <p>
 * Created by bliborio on 2/2/16.
 *
 * @author Bruna Liborio
 */

public class GUI {

    private static final String GUI_PROPERTY_PATH = "GUIstrings";

    private final ResourceBundle myResources;
    private final List<Node> controlList = new ArrayList<>();
    private final SimulationControl mySimControl;
    private final Label simLabel;
    private ComboBox<String> comboBox;
    private Button myFileButton;
    private Button myPlayPauseButton;
    private Button myStepButton;
    private Button mySpeedUpButton;
    private Button mySlowDownButton;
    private Button mySetSizeButton;
    private Button myResetButton;
    private Button myPlayAgainButton;
    private Slider mySpeedSlider;
    private Slider mySizeSlider;
    private Label sizeLabel;
    private Label speedLabel;
    private Config myConfigClass;

    /**
     * Sets starting GUI parameters and links GUI to simulation control class
     * instance.
     *
     * @param mySimControl the simulation control class instance for the program
     */
    public GUI(SimulationControl mySimControl) {
        myResources = ResourceBundle.getBundle(GUI_PROPERTY_PATH);
        this.mySimControl = mySimControl;
        simLabel = mySimControl.getSimLabel();
        createControls();
    }

    /**
     * Creates a new button. Sets the button's text and action.
     *
     * @param property the string for the button's displayed text
     * @param handles  event/action button executes upon being clicked
     * @return result
     * the new button
     */
    private static Button makeButton(String property, EventHandler<ActionEvent> handler) {
        Button result = new Button();
        result.setText(property);
        result.setOnAction(handler);
        return result;
    }

    /**
     * Sets the local directory as the first to appear in the file chooser interaction box.
     *
     * @return dir
     * the new directory
     */
    private static File getLocalDir() {
        ProtectionDomain pd = GUI.class.getProtectionDomain();
        CodeSource cs = pd.getCodeSource();
        URL localDir = cs.getLocation();
        File dir;
        try {
            dir = new File(localDir.toURI());
        } catch (URISyntaxException e) {
            dir = new File(localDir.getPath());
        }
        return dir;
    }

    /**
     * Creates and sets up all controls with their necessary parameters.
     */
    private void createControls() {
        createComboBox();
        createButtons();
        createSliders();
        createLabels();
        setAndAdd();
        extraControls();
    }

    private void extraControls() {
		Label simLabel = mySimControl.getSimLabel();
		String simText = simLabel.getText();
		try {
            Class myClass = Class.forName("Config." + simText + "Config");
            myConfigClass = (Config) myClass.newInstance();
        } catch (InstantiationException
                | IllegalAccessException
                | ClassNotFoundException e) {
        	myConfigClass = new FireConfig();
        }
		myConfigClass.setSimControl(mySimControl);
		myConfigClass.init();
		for (Node node : myConfigClass.getControls()){
			setAndAdd(node);
		}
	}

	private void createLabels() {
    	sizeLabel = new Label(myResources.getString("SizeLabel"));
    	speedLabel = new Label(myResources.getString("SpeedLabel"));
	}

	/**
     * Creates the comboBox with a change listener.
     */
    private void createComboBox() {
        comboBox = new ComboBox<>(mySimControl.getSimulations());
        comboBox.setEditable(false);
        comboBox.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> mySimControl.switchSimulation(newValue));
        comboBox.setPromptText(myResources.getString("SelectionPrompt"));
    }

    /**
     * Creates all buttons using makeButton.
     */
    private void createButtons() {
        myFileButton = makeButton(myResources.getString("XMLLoadPrompt"), event -> setUpFileChooser());
        //mySetSizeButton = makeButton(myResources.getString("SetSizeButton"), event -> setUpSizeInput());
        myPlayPauseButton = makeButton(myResources.getString("PlayPauseButton"), event -> mySimControl.playPause());
        myStepButton = makeButton(myResources.getString("StepButton"), event -> mySimControl.step());
        //mySpeedUpButton = makeButton(myResources.getString("FasterButton"), event -> mySimControl.speedUp());
        //mySlowDownButton = makeButton(myResources.getString("SlowerButton"), event -> mySimControl.slowDown());
        myResetButton = makeButton(myResources.getString("ResetButton"), event -> mySimControl.reset());
        myPlayAgainButton = makeButton(myResources.getString("PlayAgainButton"), event -> mySimControl.playAgain());
    }
    
    private void createSliders(){
    	mySpeedSlider = makeSlider(0,10,1);
    	mySpeedSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
            		mySimControl.speed(new_val.intValue(),old_val.intValue());
            }
        });
    	mySizeSlider = makeSlider(2,150,10);
    	mySizeSlider.valueProperty().addListener(new ChangeListener<Number>() {
    	public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
    			changeSize(new_val.intValue());
            }
        });
    }
    
    private Slider makeSlider(int start, int end, int incr){
    	Slider slider = new Slider(start,end,incr);
    	slider.setShowTickMarks(true);
    	slider.setShowTickLabels(true);
    	return slider;
    }

    /**
     * Calls setAndAdd for all controls.
     */
    private void setAndAdd() {
        setAndAdd(myFileButton, 1, 0, 5, 1);
        setAndAdd(myResetButton, 1, 4, 5, 1);
        setAndAdd(myPlayAgainButton, 1, 3, 5, 1);
        //setAndAdd(mySetSizeButton, 1, 2, 2, 1);
        setAndAdd(mySpeedSlider,2,5,1,1);
        setAndAdd(mySizeSlider,2,6,1,1);
        setAndAdd(myPlayPauseButton, 1, 2, 2, 1);
        setAndAdd(myStepButton, 4, 2, 2, 1);
        //setAndAdd(mySlowDownButton, 1, 4, 1, 1);
        //setAndAdd(mySpeedUpButton, 2, 4, 2, 1);
        setAndAdd(comboBox, 1, 1, 5, 1);
        setAndAdd(simLabel, 1, 7, 3, 3);
        setAndAdd(speedLabel,1,5,1,1);
        setAndAdd(sizeLabel,1,6,1,1 );
    }

    /**
     * Sets all necessary constraints on all the controls while adding them to the controlList.
     *
     * @param node    the control
     * @param col     column index for location on GridPane
     * @param row     row index for location on GridPane
     * @param colSpan number of columns to be spanned by the control
     * @param row     number of rows to be spanned by the control
     */
    private void setAndAdd(Node node, int col, int row, int colSpan, int rowSpan) {
        GridPane.setConstraints(node, col, row, colSpan, rowSpan, HPos.CENTER, VPos.CENTER);
        controlList.add(node);
        ((Region) node).setMaxWidth(Double.MAX_VALUE);
    }
    
    private void setAndAdd(Node node) {
        controlList.add(node);
        ((Region) node).setMaxWidth(Double.MAX_VALUE);
    }
    
    private void changeSize(int new_val){
    	mySimControl.stop();
    	mySimControl.sizeChange(new_val);
    }

    /**
     * Sets up and displays a size input interaction box.
     */
    private void setUpSizeInput() {
        mySimControl.stop();
        TextInputDialog input = new TextInputDialog("");
        input.setTitle(myResources.getString("SizePromptTitle"));
        input.setContentText(myResources.getString("SizePrompt"));
        Optional<String> response = input.showAndWait();
        if (response.isPresent()) {
            mySimControl.sizeChange(response.get());
        }
    }

    /**
     * Sets up and displays the file chooser interacting box.
     */
    private void setUpFileChooser() {
        mySimControl.stop();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(myResources.getString("XMLChoosePrompt"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML", "*.xml"));
        fileChooser.setInitialDirectory(getLocalDir());
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            mySimControl.openFile(file);
        }
    }

    /**
     * Gets the list containing all controls.
     *
     * @return the complete control list
     */
    public List<Node> getControls() {
        return controlList;
    }

}

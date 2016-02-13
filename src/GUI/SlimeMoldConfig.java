package GUI;

import Simulation.SlimeMoldSimulation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class SlimeMoldConfig extends Config {

    private Slider sniff;
    private Slider evaporation;
    private Slider diffusion;
    private Slider chemicalDrops;
    private Label sniffLabel;
    private Label evaporationLabel;
    private Label diffusionLabel;
    private Label chemicalLabel;
    private SlimeMoldSimulation slimeSim;

    public SlimeMoldConfig() {
        super();
    }

    @Override
    public void init() {
        slimeSim = (SlimeMoldSimulation) this.getSimulation();
        createControls();
        createLabels();
        setAndAddAll();
    }

    @Override
    public void createLabels() {
        sniffLabel = makeLabel(getResources().getString("SniffLabel"));
        evaporationLabel = makeLabel(getResources().getString("EvaporationLabel"));
        diffusionLabel = makeLabel(getResources().getString("DiffusionLabel"));
        chemicalLabel = makeLabel(getResources().getString("ChemicalLabel"));
    }

    @Override
    public void createControls() {
        sniff = makeSlider(1, 50, 1);
        sniff.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                changeSniff(new_val.intValue());
            }
        });
        sniff.setValue(slimeSim.getSniff());
        diffusion = makeSlider(0, 1, .1);
        diffusion.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                changeDiffusion(new_val.intValue());
            }
        });
        diffusion.setValue(slimeSim.getDiffusion());
        chemicalDrops = makeSlider(0, 10, 1);
        chemicalDrops.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                changeChemicalDrops(new_val.intValue());
            }
        });
        chemicalDrops.setValue(slimeSim.getChemicalDrops());
        evaporation = makeSlider(0, 10, 1);
        evaporation.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                changeEvaporation(new_val.intValue());
            }
        });
        evaporation.setValue(slimeSim.getEvaporation());
    }

    private void changeEvaporation(int intValue) {
        slimeSim.setEvaporation(intValue);

    }

    private void changeChemicalDrops(int intValue) {
        slimeSim.setChemicalDrops(intValue);
    }

    private void changeDiffusion(double intValue) {
        slimeSim.setDiffusion(intValue);
    }

    private void changeSniff(int intValue) {
        slimeSim.getSniff(intValue);
    }

    @Override
    public void setAndAddAll() {
        setAndAdd(sniff, 5, 5, 1, 1);
        setAndAdd(diffusion, 5, 6, 1, 1);
        setAndAdd(evaporation, 5, 7, 1, 1);
        setAndAdd(evaporationLabel, 4, 7, 1, 1);
        setAndAdd(diffusionLabel, 4, 6, 1, 1);
        setAndAdd(sniffLabel, 4, 5, 1, 1);
        setAndAdd(chemicalDrops, 5, 8, 1, 1);
        setAndAdd(chemicalLabel, 4, 8, 1, 1);
    }

}

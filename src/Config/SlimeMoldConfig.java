package Config;

import Simulation.SlimeMoldSimulation;
import javafx.beans.value.ChangeListener;
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
        createSniffSlider();
        createDiffusionSlider();
        createChemicalDropsSlider();
        createEvaporationSlider();
    }

    private void createSniffSlider() {
        sniff = makeSlider(1, 50, 1);
        ChangeListener<Number> sniffListener = (ov, oldVal, newVal) -> changeSniff(newVal.intValue());
        sniff.valueProperty().addListener(sniffListener);
        sniff.setValue(slimeSim.getSniff());
    }

    private void createDiffusionSlider() {
        diffusion = makeSlider(0, 1, .1);
        ChangeListener<Number> diffusionListener = (ov, oldVal, newVal) -> changeDiffusion(newVal.intValue());
        diffusion.valueProperty().addListener(diffusionListener);
        diffusion.setValue(slimeSim.getDiffusion());
    }

    private void createChemicalDropsSlider() {
        chemicalDrops = makeSlider(0, 10, 1);
        ChangeListener<Number> chemicalDropsListener = (ov, oldVal, newVal) -> changeChemicalDrops(newVal.intValue());
        chemicalDrops.valueProperty().addListener(chemicalDropsListener);
        chemicalDrops.setValue(slimeSim.getChemicalDrops());
    }

    private void createEvaporationSlider() {
        evaporation = makeSlider(0, 20, 1);
        ChangeListener<Number> evaporationListener = (ov, oldVal, newVal) -> changeEvaporation(newVal.intValue());
        evaporation.valueProperty().addListener(evaporationListener);
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
        slimeSim.setSniff(intValue);
    }

    @Override
    public void setAndAddAll() {
        setAndAdd(sniff, 5, 6, 1, 1);
        setAndAdd(diffusion, 5, 7, 1, 1);
        setAndAdd(evaporation, 5, 8, 1, 1);
        setAndAdd(evaporationLabel, 4, 8, 1, 1);
        setAndAdd(diffusionLabel, 4, 7, 1, 1);
        setAndAdd(sniffLabel, 4, 6, 1, 1);
        setAndAdd(chemicalDrops, 5, 9, 1, 1);
        setAndAdd(chemicalLabel, 4, 9, 1, 1);
    }

}

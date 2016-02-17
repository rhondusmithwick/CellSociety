// This entire file is part of my masterpiece.
// Bruna Liborio

package Config;

import Simulation.SlimeMoldSimulation;
import javafx.scene.control.Slider;

/**
 *SlimeMoldConfig Class: Class allowing for the addition of sliders to dynamically control this simulation's parameters.
 * <p>
 * Created by bliborio on 2/11/16.
 *
 * @author Bruna Liborio
 */

public class SlimeMoldConfig extends Config {

    private Slider sniff;
    private Slider evaporation;
    private Slider diffusion;
    private Slider chemicalDrops;
    private SlimeMoldSimulation slimeSim;

    public SlimeMoldConfig() {
        super();
    }

    @Override
    public void init() {
        slimeSim = (SlimeMoldSimulation) this.getSimulation();
        createControls();
        createLabels();
    }

    @Override
    public void createLabels() {
        makeLabel(getResources().getString("SniffLabel"),4,6,1,1);
        makeLabel(getResources().getString("EvaporationLabel"),4,8,1,1);
        makeLabel(getResources().getString("DiffusionLabel"),4,7,1,1);
        makeLabel(getResources().getString("ChemicalLabel"),4,9,1,1);
    }
    
    @Override
    public void createControls() {
        sniff = createSlider((ov, oldVal, newVal) -> changeSniff(newVal.intValue()),1,50,1,5,6,1,1);
        sniff.setValue(slimeSim.getSniff());
        diffusion = createSlider((ov, oldVal, newVal) -> changeDiffusion(newVal.intValue()),0,1,.1,5,7,1,1);
        diffusion.setValue(slimeSim.getDiffusion());
        chemicalDrops = createSlider((ov, oldVal, newVal) -> changeChemicalDrops(newVal.intValue()),0,10,1,5,9,1,1);
        chemicalDrops.setValue(slimeSim.getChemicalDrops());
        evaporation = createSlider((ov, oldVal, newVal) -> changeEvaporation(newVal.intValue()),0,20,1,5,8,1,1);
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
	boolean hasGraph() {
		return false;
	}

	@Override
	public void updateGraph() {

		
	}

}

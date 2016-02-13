package GUI;

import Simulation.ForagingAntsSimulation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class ForagingAntsConfig extends Config {


    private Slider diffusionRate;
    private Slider evaporationRate;
    private Slider antsBorn;
    private Slider antLifeTime;
    private Slider maxAnts;
    private Slider K;
    private Slider N;
    private Label diffusionLabel;
    private Label evaporationLabel;
    private Label antsBornLabel;
    private Label antsLifeTimeLabel;
    private Label maxAntsLabel;
    private Label NLabel;
    private Label KLabel;
    private ForagingAntsSimulation antsSim;

    public ForagingAntsConfig() {
        super();
    }

    @Override
    public void init() {
        antsSim = (ForagingAntsSimulation) this.getSimulation();
        createControls();
        createLabels();
        setAndAddAll();
    }

    @Override
    public void createLabels() {
        diffusionLabel = makeLabel(getResources().getString("DiffusionLabel"));
        evaporationLabel = makeLabel(getResources().getString("EvaporationLabel"));
        antsBornLabel = makeLabel(getResources().getString("AntsBornLabel"));
        antsLifeTimeLabel = makeLabel(getResources().getString("AntsLifeTimeLabel"));
        maxAntsLabel = makeLabel(getResources().getString("MaxAntsLabel"));
        NLabel = makeLabel(getResources().getString("NLabel"));
        KLabel = makeLabel(getResources().getString("KLabel"));
    }

    @Override
    public void createControls() {
        diffusionRate = makeSlider(.001, 1, .01);
        diffusionRate.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                changeDiffusionRate(new_val.doubleValue());
            }
        });
        diffusionRate.setValue(antsSim.getDiffusionRate());
        evaporationRate = makeSlider(.001, 1, .01);
        evaporationRate.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                changeEvaporationRate(new_val.doubleValue());
            }
        });
        evaporationRate.setValue(antsSim.getEvaporationRate());
        antsBorn = makeSlider(0, 500, 20);
        antsBorn.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                changeAntsBorn(new_val.doubleValue());
            }
        });
        antsBorn.setValue(antsSim.getAntsBorn());
        antLifeTime = makeSlider(100, 4900, 400);
        antsBorn.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                changeAntLifeTime(new_val.doubleValue());
            }
        });
        antLifeTime.setValue(antsSim.getLifeTime());
        maxAnts = makeSlider(500, 10000, 500);
        maxAnts.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                changeMaxAnts(new_val.doubleValue());
            }
        });
        maxAnts.setValue(antsSim.getMaxAnts());
        K = makeSlider(.001, 1, .01);
        K.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                changeK(new_val.doubleValue());
            }
        });
        K.setValue(antsSim.getK());
        N = makeSlider(5, 100, 5);
        N.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                changeN(new_val.doubleValue());
            }
        });
        N.setValue(antsSim.getN());
    }

    protected void changeN(double d) {
        antsSim.setN(d);

    }

    protected void changeK(double d) {
        antsSim.setK(d);
    }

    protected void changeMaxAnts(double d) {
        antsSim.setMaxAnts(d);
    }

    protected void changeAntLifeTime(double d) {
        antsSim.setLifeTime(d);
    }

    protected void changeAntsBorn(double d) {
        antsSim.setAntsBorn(d);
    }

    protected void changeEvaporationRate(double d) {
        antsSim.setEvaporationRate(d);
    }

    protected void changeDiffusionRate(double d) {
        antsSim.setDiffusionRate(d);
    }

    @Override
    public void setAndAddAll() {
        setAndAdd(evaporationRate, 5, 5, 1, 1);
        setAndAdd(diffusionRate, 5, 6, 1, 1);
        setAndAdd(maxAnts, 5, 7, 1, 1);
        setAndAdd(antLifeTime, 5, 8, 1, 1);
        setAndAdd(antsBorn, 5, 9, 1, 1);
        setAndAdd(antsBornLabel, 4, 9, 1, 1);
        setAndAdd(antsLifeTimeLabel, 4, 8, 1, 1);
        setAndAdd(maxAntsLabel, 4, 7, 1, 1);
        setAndAdd(diffusionLabel, 4, 6, 1, 1);
        setAndAdd(N, 2, 7, 1, 1);
        setAndAdd(K, 2, 8, 1, 1);
        setAndAdd(NLabel, 1, 7, 1, 1);
        setAndAdd(KLabel, 1, 8, 1, 1);
        setAndAdd(evaporationLabel, 4, 5, 1, 1);
        this.deactivateSize();
    }

}

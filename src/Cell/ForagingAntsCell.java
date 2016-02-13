package Cell;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by rhondusmithwick on 2/7/16.
 *
 * @author Rhondu Smithwick
 */
public class ForagingAntsCell extends Cell {

    private final List<Ant> myAnts = new LinkedList<>();
    private final List<Ant> antsToAdd = new LinkedList<>();
    private State state;
    private Mark mark;
    private double homePheromones;
    private double foodPheromones;
    private double maxAmountPhero;

    private int maxAntsPer;
    private double probChoice;

    public ForagingAntsCell() {
        super();
    }

    @Override
    public void changeState() {
        switch (mark) {
            case NONE:
                return;
            case CHANGE_ANTS:
                updateAnts();
                break;
            default:
                state = State.valueOf(mark.toString());
        }
        changeVisual();
        setMark(Mark.NONE);
    }

    @Override
    public void handleUpdate() {
        myAnts.stream().forEach(Ant::handleUpdate);
    }


    public void spawn(int antsBorn, int antLifeTime) {
        for (int i = 0; i < antsBorn; i++) {
            if (canSpawn()) {
                Ant ant = new Ant(this, antLifeTime);
                antsToAdd.add(ant);
                setMark(Mark.CHANGE_ANTS);
            }
        }
    }


    private boolean canSpawn() {
        return (state == State.NEST)
                && myAnts.size() < maxAntsPer;
    }


    private void updateAnts() {
        Iterator<Ant> iter = myAnts.iterator();
        while (iter.hasNext()) {
            Ant currAnt = iter.next();
            if (currAnt.getDeadOrMove()) {
                iter.remove();
            }
        }
        myAnts.addAll(antsToAdd);
        antsToAdd.clear();
        changeVisual();
    }

    private void changeVisual() {
        Color currVisual = (Color) getVisual(state);
        for (int i = 0; i < myAnts.size(); i++) {
            currVisual = currVisual.darker();
        }
        setFill(currVisual);
    }


    public void pheroUpdate(double evaporationRate, double diffusionRate) {
        if (homePheromones > 0) {
            homePheromones -= evaporationRate * homePheromones;
        }
        if (foodPheromones > 0) {
            foodPheromones -= evaporationRate * foodPheromones;
        }
        getNeighbors().stream()
                .map(c -> (ForagingAntsCell) c)
                .forEach(c -> transferPhero(c, diffusionRate));
    }

    private void transferPhero(ForagingAntsCell neighbor, double diffusionRate) {
        if (homePheromones > 0) {
            neighbor.homePheromones += homePheromones * diffusionRate;
            homePheromones -= diffusionRate * homePheromones;
        }
        if (foodPheromones > 0) {
            neighbor.foodPheromones += foodPheromones * diffusionRate;
            foodPheromones -= diffusionRate * foodPheromones;
        }
    }

    @Override
    public void setVisuals(Paint... visuals) {
        addToVisualMap(State.OPEN, visuals[0]);
        addToVisualMap(State.NEST, visuals[1]);
        addToVisualMap(State.FOOD, visuals[2]);
        setStroke(Color.BLACK);
    }

    public void setMaxAntsPer(int maxAntsPer) {
        this.maxAntsPer = maxAntsPer;
    }

    public int getNumAnts() {
        return myAnts.size();
    }

    void addAnt(Ant ant) {
        antsToAdd.add(ant);
    }


    public void setProbChoice(double K, double N) {
        probChoice = Math.pow(K + foodPheromones, N);
    }

    public double getProbChoice() {
        return probChoice;
    }

    double getHomePheromones() {
        return homePheromones;
    }

    public void setInitialPheromones(double min, double max) {
        foodPheromones = min;
        homePheromones = min;
        maxAmountPhero = max;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }

    public State getState() {
        return state;
    }

    boolean isValid() {
        return myAnts.size() < maxAntsPer;
    }

    void dropPheromones(boolean home) {
        double currPhero = getAreaPheromones(home);
        double max = maxAmountPhero - currPhero;
        addPheros(home, max);
    }

    private double getAreaPheromones(boolean home) {
        ForagingAntsCell fac;
        double currPhero = 0.0;
        for (Cell c : getNeighbors()) {
            fac = (ForagingAntsCell) c;
            double increase = home ? fac.homePheromones : fac.foodPheromones;
            currPhero += increase;
        }
        return currPhero;
    }

    private void addPheros(boolean home, double max) {
        if (home && state == State.NEST) {
            homePheromones += max;
        } else if (!home && state == State.FOOD) {
            foodPheromones += max;
        } else {
            double desired = max - 2;
            double offset = home ? homePheromones : foodPheromones;
            double d = desired - offset;
            if (d > 0) {
                incrementPheros(home, d);
            }
        }
    }

    private void incrementPheros(boolean home, double d) {
        if (home) {
            homePheromones += d;
        } else {
            foodPheromones += d;
        }
    }

    @Override
    void saveTypeCellState() {
        // TODO Auto-generated method stub

    }

    public enum State {
        FOOD, NEST, OPEN
    }

    public enum Mark {
        FOOD, NEST, OPEN, CHANGE_ANTS, NONE
    }

}


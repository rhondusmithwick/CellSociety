//package Cell;
//
//import javafx.scene.paint.Color;
//import javafx.scene.paint.Paint;
//
//import java.util.Iterator;
//import java.util.LinkedList;
//import java.util.List;
//
//
///**
// * Created by rhondusmithwick on 2/7/16.
// *
// * @author Rhondu Smithwick
// */
//public class ForagingAntsCell extends Cell {
//
//    private State state;
//    private Mark mark;
//
//    private final List<Ant> myAnts = new LinkedList<>();
//    private final List<Ant> antsToadd = new LinkedList<>();
//
//    private double homePheromones ;
//    private double foodPheromones;
//    private double maxAmountPhero;
//
//    private double antLifeTime;
//    private int maxAntsPer;
//    private double probChoice;
//
//    private Paint emptyVisual;
//    private Paint nestVisual;
//    private Paint foodVisual;
//    private Color currVisual;
//
//
//    public ForagingAntsCell() {
//        super();
//    }
//
//    @Override
//    public void changeState() {
//        switch (mark) {
//            case NONE:
//                return;
//            case CHANGE_ANTS:
//                updateAnts();
//                break;
//            case TO_FOOD:
//                state = State.FOOD;
//                break;
//            case TO_EMPTY:
//                state = State.OPEN;
//                break;
//            case TO_NEST:
//                state = State.NEST;
//                break;
//            default:
//        }
//        setMark(Mark.NONE);
//        changeVisual();
//    }
//
//    @Override
//    public void handleUpdate() {
//        myAnts.stream().forEach(Ant::handleUpdate);
//    }
//
//
//    public void spawn(int antsBorn) {
//        for (int i = 0; i < antsBorn; i++) {
//            if (state == State.NEST && myAnts.size() < maxAntsPer) {
//                Ant ant = new Ant(this, antLifeTime);
//                antsToadd.add(ant);
//                setMark(Mark.CHANGE_ANTS);
//            }
//        }
//    }
//
//    private void updateAnts() {
//        Iterator<Ant> iter = myAnts.iterator();
//        while (iter.hasNext()) {
//            Ant currAnt = iter.next();
//            if (currAnt.getDeadOrMove()) {
//                iter.remove();
//            }
//        }
//        myAnts.addAll(antsToadd);
//        antsToadd.clear();
//        changeVisual();
//    }
//
//    private void changeVisual() {
//        switch (state) {
//            case OPEN:
//                currVisual = (Color) emptyVisual;
//                break;
//            case NEST:
//                currVisual = (Color) nestVisual;
//                break;
//            case FOOD:
//                currVisual = (Color) foodVisual;
//                break;
//        }
//        for (int i = 0; i < myAnts.size(); i++) {
//            currVisual = currVisual.darker();
//        }
//        setFill(currVisual);
//    }
//
//
//    public void pheroUpdate(double evaporationRate, double diffusionRate) {
//        if (homePheromones > 0) {
//            homePheromones -= evaporationRate * homePheromones;
//        }
//        if (foodPheromones > 0) {
//            foodPheromones -= evaporationRate * foodPheromones;
//        }
//        getNeighbors().stream()
//                .map(c -> (ForagingAntsCell) c)
//                .forEach(c -> transferPhero(c, diffusionRate));
//    }
//
//    private void transferPhero(ForagingAntsCell neighbor, double diffusionRate) {
//        if (homePheromones > 0) {
//            neighbor.homePheromones += homePheromones * diffusionRate;
//            homePheromones -= diffusionRate * homePheromones;
//        }
//        if (foodPheromones > 0) {
//            neighbor.foodPheromones += foodPheromones * diffusionRate;
//            foodPheromones -= diffusionRate * foodPheromones;
//        }
//    }
//
//    @Override
//    public void setVisuals(Paint... visuals) {
//        emptyVisual = visuals[0];
//        nestVisual = visuals[1];
//        foodVisual = visuals[2];
//        setStroke(Color.BLACK);
//    }
//
//    public void setMaxAntsPer(int maxAntsPer) {
//        this.maxAntsPer = maxAntsPer;
//    }
//
//    void addAnt(Ant ant) {
//        antsToadd.add(ant);
//    }
//
//    public void setAntLifeTime(double antLifeTime) {
//        this.antLifeTime = antLifeTime;
//    }
//
//
//    public void setProbChoice(double K, double N) {
//        probChoice = Math.pow(K + foodPheromones, N);
//    }
//
//    public double getProbChoice() {
//        return probChoice;
//    }
//
//    double getHomePheromones() {
//        return homePheromones;
//    }
//
//    public void setPheromones(double min, double max) {
//        foodPheromones = min;
//        homePheromones = min;
//        maxAmountPhero = max;
//    }
//
//    public void setMark(Mark mark) {
//        this.mark = mark;
//    }
//
//    public State getState() {
//        return state;
//    }
//
//    boolean isValid() {
//        return myAnts.size() < maxAntsPer;
//    }
//
//    void dropHomesPheromones() {
//        ForagingAntsCell fac;
//        double currPhero = 0.0;
//        for (Cell c : getNeighbors()) {
//            fac = (ForagingAntsCell) c;
//            double curr = fac.homePheromones;
//            currPhero += curr;
//        }
//        double max = maxAmountPhero - currPhero;
//        if (state == State.NEST) {
//            homePheromones += max;
//        } else {
//            double desired = max - 2;
//            double d = desired - homePheromones;
//            if (d > 0) {
//                homePheromones += d;
//            }
//        }
//    }
//
//    void dropFoodPheromones() {
//        ForagingAntsCell fac;
//        double currPhero = 0.0;
//        for (Cell c : getNeighbors()) {
//            fac = (ForagingAntsCell) c;
//            double curr = fac.foodPheromones;
//            currPhero += curr;
//        }
//        double max = maxAmountPhero - currPhero;
//        if (state == State.FOOD) {
//            foodPheromones += max;
//        } else {
//            double desired = max - 2;
//            double d = desired - foodPheromones;
//            if (d > 0) {
//                foodPheromones += d;
//            }
//        }
//    }
//
//    public enum State {
//        FOOD, NEST, OPEN
//    }
//
//    public enum Mark {
//        TO_FOOD, TO_NEST, CHANGE_ANTS, TO_EMPTY, NONE
//    }
//
//}
//

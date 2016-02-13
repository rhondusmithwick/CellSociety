package Cell;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Created by rhondusmithwick on 2/11/16.
 *
 * @author Rhondu Smithwick
 */
public class SlimeMoldCell extends Cell {
    private State state;
    private Mark mark;

    private double cAMPAmount = 0;

    private Paint emptyVisual;
    private Paint slimeVisual;
    private Paint cAMPVisual;
    private Paint obstacleVisual;

    public SlimeMoldCell() {
        super();
    }

    @Override
    public void changeState() {
        switch (mark) {
            case NONE:
                return;
            case TO_EMPTY:
                state = State.EMPTY;
                modifyColor();
                break;
            case TO_OBSTACLE:
                state = State.OBSTACLE;
                setFill(obstacleVisual);
                break;
            case TO_SLIME:
                state = State.SLIME;
                setFill(slimeVisual);
                break;
        }
        setMark(Mark.NONE);
    }

    @Override
    public void handleUpdate() {
    }

    @Override
    public void setVisuals(Paint... visuals) {
        emptyVisual = visuals[0];
        slimeVisual = visuals[1];
        cAMPVisual = visuals[2];
        obstacleVisual = visuals[3];
    }


    public void setMark(Mark mark) {
        this.mark = mark;
    }


    private void modifyColor() {
        if (cAMPAmount == 0) {
            setFill(emptyVisual);
        } else {
            Color visual = (Color) cAMPVisual;
            for (int i = 0; i < Math.ceil(cAMPAmount); i++) {
                visual = visual.darker();
            }
            setFill(visual);
        }
    }

    public void evaporate(double evaporationRate) {
        if (cAMPAmount - evaporationRate > 0) {
            cAMPAmount -= evaporationRate;
        }
    }

    public void diffuse(double diffusionRate) {
        SlimeMoldCell smc;
        for (Cell neighbor : getNeighbors()) {
            smc = (SlimeMoldCell) neighbor;
            if (cAMPAmount - diffusionRate > 0) {
                cAMPAmount -= diffusionRate;
                smc.cAMPAmount += diffusionRate;
            }
        }
    }

    public void dropChemicals(double chemicalDrops) {
        if (state == State.SLIME) {
            cAMPAmount += chemicalDrops;
        }
    }

    public boolean sniffCheck(double sniffThreshold) {
        double totalcAMP = 0.0;
        SlimeMoldCell smc;
        for (Cell c : getNeighbors()) {
            smc = (SlimeMoldCell) c;
            totalcAMP += smc.cAMPAmount;
        }
        return (totalcAMP >= sniffThreshold);
    }

    public SlimeMoldCell chemicalGradientMove(Point2D[] sniffAngles) {
        SlimeMoldCell bestNeighbor = null;
        double highestcAMP = 0.0;
        for (Cell c : getNeighbors()) {
            SlimeMoldCell smc = (SlimeMoldCell) c;
            if (neighborCheck(smc, sniffAngles)) {
                if (smc.cAMPAmount > highestcAMP) {
                    highestcAMP = smc.cAMPAmount;
                    bestNeighbor = smc;
                }
            }
        }
        return bestNeighbor;
    }

    public SlimeMoldCell randomMoveHelp(Point2D angle) {
        for (Cell c : getNeighbors()) {
            SlimeMoldCell smc = (SlimeMoldCell) c;
            if (neighborCheck(smc, angle)) {
                return smc;
            }
        }
        return null;
    }

    private boolean neighborCheck(SlimeMoldCell neighbor, Point2D... sniffAngles) {
        for (Point2D angle : sniffAngles) {
            double r = getRow() + angle.getY();
            double c = getColumn() + angle.getX();
            if (r == neighbor.getRow() && c == neighbor.getColumn()) {
                return notFull(neighbor);

            }
        }
        return false;
    }

    public State getState() {
        return state;
    }

    private boolean notFull(SlimeMoldCell smc) {
        return (smc.state != State.SLIME)
                && (smc.state != State.OBSTACLE)
                && (smc.mark != Mark.TO_SLIME);
    }

    public enum State {
        EMPTY, SLIME, OBSTACLE
    }

    public enum Mark {
        TO_EMPTY, TO_SLIME, NONE, TO_OBSTACLE
    }

	@Override
	void saveTypeCellState() {
		// TODO Auto-generated method stub

	}

}

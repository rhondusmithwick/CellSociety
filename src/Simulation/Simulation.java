package Simulation;

import Cell.Cell;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Collection;
import java.util.Random;

/**
 * Created by rhondusmithwick on 1/31/16.
 *
 * @author Rhondu Smithwick, Tavo Loaiza
 */
public abstract class Simulation {
    private final Random rn;
    private final Timeline simulationLoop;

    private int gridWidth;
    private int gridHeight;
    private int cellsPerRow;
    private int cellsPerColumn;
    private String type;
    private Collection<Cell> theCells;
    private boolean isPlaying = false;

    Simulation() {
        simulationLoop = buildLoop();
        rn = new Random();
    }


    private Timeline buildLoop() {
        final KeyFrame keyFrame = new KeyFrame(Duration.seconds(.5), t -> step());
        final Timeline simulationLoop = new Timeline();
        simulationLoop.setCycleCount(Animation.INDEFINITE);
        simulationLoop.getKeyFrames().addAll(keyFrame);
        return simulationLoop;
    }

    public final void init() {
        int randomNum;
        for (Cell c : getTheCells()) {
            randomNum = getRandomNum(1, 100);
            assignInitialState(randomNum, c);
        }
        changeStates();
    }

    abstract void assignInitialState(int randomNum, Cell c);

    public void step() {
        getTheCells().forEach(c -> c.handleUpdate());
    }

    final void changeStates() {
        for (Cell c : getTheCells()) {
            c.changeState();
        }
    }

    private void beginLoop() {
        simulationLoop.play();
        isPlaying = true;
    }

    public void stopLoop() {
        simulationLoop.stop();
        isPlaying = false;
    }

    public boolean getPlaying() {
        return isPlaying;
    }

    public final void playOrStop() {
        if (!getPlaying()) {
            beginLoop();
        } else {
            stopLoop();
        }
    }


    final int getRandomNum(int min, int max) {
        int range = max - min + 1;
        return rn.nextInt(range) + min;
    }

    final void parseXmlFile(String xmlFilename) {
        //get the factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            //Using factory get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            //parse using builder to get DOM representation of the XML file
            Document xmlDoc = db.parse(xmlFilename);
            Element simulationElem = xmlDoc.getDocumentElement();
            setGenericProperties(simulationElem);
            setSpecificProperties(simulationElem);
        } catch (ParserConfigurationException
                | SAXException
                | IOException pce) {
            pce.printStackTrace();
        }
    }


    private void setGenericProperties(Element simElem) {
        String simType = simElem.getAttribute("SimulationType");
        setType(simType);
        gridWidth = getIntValue(simElem, "gridWidth");
        gridHeight = getIntValue(simElem, "gridHeight");
        cellsPerRow = getIntValue(simElem, "numCellsPerRow");
        cellsPerColumn = getIntValue(simElem, "numCellsPerColumn");
    }

    abstract void setSpecificProperties(Element simElem);

    final String getTextValue(Element ele, String tagName) {
        String textVal = null;
        NodeList nl = ele.getElementsByTagName(tagName);
        if (nl != null && nl.getLength() > 0) {
            Element el = (Element) nl.item(0);
            textVal = el.getFirstChild().getNodeValue();
        }
        return textVal;
    }

    final Paint getPaintValue(Element ele, String tagName) {
        return Paint.valueOf(getTextValue(ele, tagName));
    }

    final int getIntValue(Element ele, String tagName) {
        return Integer.parseInt(getTextValue(ele, tagName));
    }


    public final int getGridWidth() {
        return gridWidth;
    }


    public final int getGridHeight() {
        return gridHeight;
    }


    public final int getCellsPerRow() {
        return cellsPerRow;
    }


    public final int getCellsPerColumn() {
        return cellsPerColumn;
    }


    final String getType() {
        return type;
    }


    private void setType(String type) {
        this.type = type;
    }

    final Collection<Cell> getTheCells() {
        return theCells;
    }

    public final void setTheCells(Collection<Cell> theCells) {
        this.theCells = theCells;
    }

    public final void increaseRate() {
        double currentRate = simulationLoop.getRate();
        if (currentRate <= 10) {
            simulationLoop.setRate(currentRate + .1);
        }
    }

    public final void decreaseRate() {
        double currentRate = simulationLoop.getRate();
        if (currentRate > 0) {
            simulationLoop.setRate(currentRate - .1);

        }
    }

    public final void resetRate() {
        simulationLoop.setRate(1.0);
    }
}


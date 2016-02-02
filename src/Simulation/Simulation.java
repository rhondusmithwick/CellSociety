package Simulation;

import Cell.Cell;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

    protected Simulation() {
        simulationLoop = buildLoop();
        rn = new Random();
    }

    protected Simulation(Collection<Cell> theCells) {
        this();
        setTheCells(theCells);
    }

    private Timeline buildLoop() {
        EventHandler<ActionEvent> handler = (t -> step());
        final KeyFrame keyFrame = new KeyFrame(Duration.seconds(.5), handler);
        Timeline simulationLoop = new Timeline();
        simulationLoop.setCycleCount(Animation.INDEFINITE);
        simulationLoop.getKeyFrames().addAll(keyFrame);
        return simulationLoop;
    }

    protected void step() {
        getTheCells().forEach(c -> c.handleUpdate());
    }

    private void beginLoop() {
        simulationLoop.play();
        isPlaying = true;
    }

    private void stopLoop() {
        simulationLoop.stop();
        isPlaying = false;
    }

    private boolean getPlaying() {
        return isPlaying;
    }

    public void playOrStop() {
        if (!getPlaying()) {
            beginLoop();
        } else {
            stopLoop();
        }
    }


    public void init() {
        for (Cell c : getTheCells()) {
            int randomNum = getRandomNum(1, 100);
            assignInitialState(randomNum, c);
        }
    }

    protected abstract void assignInitialState(int randomNum, Cell c);

    protected int getRandomNum(int min, int max) {
        int range = max - min + 1;
        return rn.nextInt(range) + min;
    }

    public void parseXmlFile(String xmlFilename) {
        //get the factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            //Using factory get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            //parse using builder to get DOM representation of the XML file
            Document xmlDoc = db.parse(xmlFilename);
            Element simulationElem = xmlDoc.getDocumentElement();
            getGenericProperties(simulationElem);
            getTypeProperties(simulationElem);
        } catch (ParserConfigurationException
                | SAXException
                | IOException pce)
        {
            pce.printStackTrace();
        }
    }

    private void getGenericProperties(Element simElem) {
        setType(simElem.getAttribute("SimulationType"));
        gridWidth = getIntValue(simElem, "gridWidth");
        gridHeight = getIntValue(simElem, "gridHeight");
        cellsPerRow = getIntValue(simElem, "numCellsPerRow");
        cellsPerColumn = getIntValue(simElem, "numCellsPerColumn");
    }

    protected abstract void getTypeProperties(Element simElem);

    private String getTextValue(Element ele, String tagName) {
        String textVal = null;
        NodeList nl = ele.getElementsByTagName(tagName);
        if (nl != null && nl.getLength() > 0) {
            Element el = (Element) nl.item(0);
            textVal = el.getFirstChild().getNodeValue();
        }
        return textVal;
    }

    protected int getIntValue(Element ele, String tagName) {
        //in production application you would catch the exception
        return Integer.parseInt(getTextValue(ele, tagName));
    }


    public int getGridWidth() {
        return gridWidth;
    }


    public int getGridHeight() {
        return gridHeight;
    }


    public int getCellsPerRow() {
        return cellsPerRow;
    }


    public int getCellsPerColumn() {
        return cellsPerColumn;
    }


    public String getType() {
        return type;
    }


    public void setType(String type) {
        this.type = type;
    }

    public Collection<Cell> getTheCells() {
        return theCells;
    }

    public void setTheCells(Collection<Cell> theCells) {
        this.theCells = theCells;
    }
}

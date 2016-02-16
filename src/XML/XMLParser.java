package XML;

import Cell.Cell;
import Cell.FireCell;
import Grid.Grid;
import Grid.RectangleGrid;
import Grid.RectangleShape;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by SudoTavo on 02/05/16.
 *
 * @author Tavo Loaiza
 */
public class XMLParser {

    private final Element rootElement;
    private Collection<Cell> cells;

    public XMLParser(Element rootElement) {
        this.rootElement = rootElement;
    }

    public static String getSimType(Element simElem) {
        return simElem.getAttribute("SimulationType");
    }

    public static Element getXmlElement(String xmlFilename) throws XMLException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document xmlDoc = db.parse(xmlFilename);
            return (xmlDoc.getDocumentElement());
        } catch (ParserConfigurationException
                | SAXException
                | IOException pce) {
            //return null;
            throw new XMLException("XML Read error");
        }
    }

    private static String getTextValue(Element ele, String tagName) {
        String textVal = null;
        NodeList nl = ele.getElementsByTagName(tagName);
        if (nl != null && nl.getLength() > 0) {
            Element el = (Element) nl.item(0);
            textVal = el.getFirstChild().getNodeValue();
        }
        return textVal;
    }

    public static Paint getPaintValue(Element ele, String tagName) {
        return Paint.valueOf(getTextValue(ele, tagName));
    }

    public static int getIntValue(Element ele, String tagName) {
        return Integer.parseInt(getTextValue(ele, tagName));
    }

    public static double getDoubleValue(Element ele, String tagName) {
        return Double.parseDouble(getTextValue(ele, tagName));
    }

    public Collection<Cell> getCells(String cellType) throws XMLException {
        cells = new LinkedList<Cell>();
        //Cell base = getCell(cellType);
        //base.saveCellState();
        Element cellsElem = getElement("Cells");
        int cellCount = getIntValue(cellsElem, "cellCount");
        for (int i = 0; i < cellCount; i++) {
            String tag = "cell" + Integer.toString(i);
            Cell newCell = makeCell(cellType, getElement(cellsElem, tag));
            cells.add(newCell);
            //System.out.println("New Cell #"+i);
            //System.out.println("row: "+newCell.getRow());
        }

        return cells;
    }

    public Cell makeCell(String cellType, Element cellElem) throws XMLException {

        double x = getDoubleValue(cellElem, "x");
        double y = getDoubleValue(cellElem, "y");
        double cellWidth = getDoubleValue(cellElem, "cellWidth");
        double cellHeight = getDoubleValue(cellElem, "cellHeight");
        int row = getIntValue(cellElem, "row");
        int column = getIntValue(cellElem, "column");
        //System.out.println(cellHeight);
        Grid g = new RectangleGrid();
        Rectangle rectangle = new Rectangle(x, y, cellWidth, cellHeight);
        RectangleShape rectangleShape = new RectangleShape(rectangle);
        Cell cell = g.createCell(rectangleShape, cellType, row, column);
        //Cell//getCell(cellType);

        cell.saveCellState();

        Map<String, String> stateMap = new HashMap<String, String>();
        for (String tag : cell.getCellState().keySet()) {
            stateMap.put(tag, getTextValue(cellElem, tag));
        }

        //cell.init(new RectangleShape(x,y , cellWidth, cellHeight),row, column);

        if (cell instanceof FireCell) {
            ((FireCell) cell).loadCellState(stateMap);
            // ((FireCell)cell).changeState();
        }

        return cell;
    }

    private Element getElement(String tagName) {
        return getElement(rootElement, tagName);
    }

    private Element getElement(Element parent, String tagName) {
        Element el = null;
        NodeList nl = parent.getElementsByTagName(tagName);
        if (nl != null && nl.getLength() > 0) {
            el = (Element) nl.item(0);
        }
        return el;
    }

    public boolean tagExists(String tagName) {
        NodeList nl = rootElement.getElementsByTagName(tagName);
        return (nl != null && nl.getLength() > 0);
    }

    public Element getRootElement() {
        return rootElement;
    }

    public String getSimType() {
        return rootElement.getAttribute("SimulationType");
    }

    public String getTextValue(String tagName) {

        return getTextValue(rootElement, tagName);
    }

    public Paint getPaintValue(String tagName) {
        return Paint.valueOf(getTextValue(rootElement, tagName));

    }

    public int getIntValue(String tagName) {
        return Integer.parseInt(getTextValue(rootElement, tagName));
    }


}

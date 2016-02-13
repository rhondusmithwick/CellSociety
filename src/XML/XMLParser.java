package XML;

import javafx.scene.paint.Paint;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Created by SudoTavo on 02/05/16.
 *
 * @author Tavo Loaiza
 */
public class XMLParser {

    private final Element rootElement;

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
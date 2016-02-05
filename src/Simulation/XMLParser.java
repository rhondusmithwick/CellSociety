package Simulation;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javafx.scene.paint.Paint;

public class XMLParser {


	public XMLParser() {

	}
	public static String getSimType(Element simElem){
		return simElem.getAttribute("SimulationType");
	}

	public static Element getXmlElement(String xmlFilename) {
        //get the factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            //Using factory get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            //parse using builder to get DOM representation of the XML file
            Document xmlDoc = db.parse(xmlFilename);
            return( xmlDoc.getDocumentElement());

        } catch (ParserConfigurationException
                | SAXException
                | IOException pce) {

            pce.printStackTrace();//Bad
            return null;
        }
    }

    public static String getTextValue(Element ele, String tagName) {
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

}

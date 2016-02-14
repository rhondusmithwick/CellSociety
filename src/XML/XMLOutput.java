package XML;

import Cell.Cell;
import Simulation.Simulation;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.beans.XMLEncoder;
import java.io.File;
import java.util.Collection;

public class XMLOutput {

    private static Document doc;
    private final Element rootElement;
    private DocumentBuilder docBuild;
    private Element cellsElement;


    public XMLOutput(Simulation sim) {
        init();
        rootElement = doc.createElement("Simulation");
        rootElement.setAttribute("SimulationType", sim.getType());
        doc.appendChild(rootElement);
        for (String tag : sim.getSavedValues().keySet()) {
        	addObjectElement(rootElement, tag,sim.getSavedValues().get(tag));
        }
      }
    private Element addObjectElement(Element parent,String tag, Object value){
             parent.appendChild(objectElement(tag, value));
         return parent;
    }

    private Element objectElement(String tag, Object value){
    	Element elem = null;
   	 	if ( value instanceof Integer) {
           elem = makeIntElement(tag, (Integer) value);
        }
   	 	if ( value instanceof Double) {
         elem = makeDoubleElement(tag, (Double) value);
   	 	}
        if (value instanceof String) {
        	 elem = makeTextElement(tag, (String) value);
        }
        if (value instanceof Paint) {
        	 elem = makePaintElement(tag, (Paint) value);
        }
        return elem;
   }

    public void addCells(String cellType, Collection<Cell> cells){
    	cellsElement = doc.createElement("Cells");
   	 	cellsElement.appendChild(makeIntElement("cellCount",cells.size()));
   	 	int cellCount = 0;
   	 	for(Cell c: cells){
   	 		c.saveCellState();
   	 		String tag = "cell"+Integer.toString(cellCount);
   	 		cellsElement.appendChild(makeCellElement(tag,c));
   	 		cellCount++;
   	 	}
   	 rootElement.appendChild(cellsElement);
    }


    public Element makeCellElement(String tag, Cell c) {
    	 Element elem = doc.createElement(tag);
	 		for(String t: c.getCellState().keySet()){
	 			addObjectElement(elem,t,c.getCellState().get(t));
	 		}
    	return elem;

    }

    private void init() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            docBuild = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        doc = docBuild.newDocument();
    }

    private void addElement(Element child) {
        rootElement.appendChild(child);
    }

    public void writeXML(File file) throws XMLException {
        Transformer transformer;
        try {
            transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult output = new StreamResult(file);
            try {
                transformer.transform(source, output);
            } catch (TransformerException e) {
               throw new XMLException();
            }
        } catch (NullPointerException | TransformerConfigurationException | TransformerFactoryConfigurationError e) {
        	throw new XMLException();
        }


    }

    private Element makeIntElement(String name, int value) {
        return makeTextElement(name, Integer.toString(value));
    }
    private Element makeDoubleElement(String name, Double value) {
        return makeTextElement(name, Double.toString(value));
    }



    private Element makeTextElement(String tag, String value) {
        Element elem = doc.createElement(tag);
        elem.appendChild(doc.createTextNode(value));
        return elem;
    }

    private Element makePaintElement(String tag, Paint value) {
        return makeTextElement(tag, value.toString());
    }

}



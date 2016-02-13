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
    private final String WRITE_DIR = "resources";
    private final Element rootElement;
    public Collection<Cell> theCells;
    private DocumentBuilder docBuild;
    private XMLEncoder encoder;

    public XMLOutput(Simulation sim) {
        init();
        rootElement = doc.createElement("Simulation");
        rootElement.setAttribute("SimulationType", sim.getType());
        doc.appendChild(rootElement);
        for (String tag : sim.getSavedValues().keySet()) {
            Object value = sim.getSavedValues().get(tag);

            if (value instanceof Integer) {
                addElement(makeIntElement(tag, (int) value));
            }
            if (value instanceof String) {
                addElement(makeTextElement(tag, (String) value));
            }
            if (value instanceof Paint) {
                addElement(makePaintElement(tag, (Paint) value));
            }
        }
        /*
         try {
			Class simClass = Class.forName("Simulation."+sim.getType()+"Simulation");
			Field[] fields = simClass.getDeclaredFields(); //includes fields declared in the Shape abstract class
			System.out.println("Fields: ");
			for (int i=fields.length/2; i<fields.length; i++) {
	            System.out.println("field name = " + fields[i].getName()); //prints 'numberOfSides' and 'radius'
	        }
		 }
		 catch (Exception e) {
		        System.out.println("Exception: " + e);
		     }
		     */

		/*
		makeIntElement("gridWidth",sim.getGridWidth());
		makeIntElement("gridHeight",sim.getGridHeight());
		makeIntElement("cellsPerRow",sim.getCellsPerRow());
		makeIntElement("cellsPerColumn",sim.getCellsPerColumn());
		*/
    }

    /*
        public void initSimXML(String simType){
            Element simElem = makeTextElement("String", simType);
            init(simElem);
        }
        */
/*
	public XMLOutput(Element rootElement) {
		init(rootElement);
	}
	*/
    private void init() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            docBuild = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        doc = docBuild.newDocument();
        //this.rootElement=rootElement;
        //doc.appendChild(rootElement);
    }

    private void addElement(Element child) {
        rootElement.appendChild(child);
    }


    public void writeCell(Rectangle c, File file) throws Exception {

        encoder.writeObject(c);
        encoder.close();
    }

    public void addCell(XMLEncoder encoder, Cell c) {
        encoder.writeObject(c);
    }
/*
	    public void writeXML(File file){
	    	try {
				encoder = new XMLEncoder(new BufferedOutputStream (new FileOutputStream(file))) ;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace(); //Bad
			}
	    	encoder.close();
	    }
	    */

    public void writeXML(File file) {
        //File file = new File(WRITE_DIR,filename);
        Transformer transformer;
        try {
            transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult output = new StreamResult(file);
            try {
                transformer.transform(source, output);
            } catch (TransformerException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (TransformerConfigurationException | TransformerFactoryConfigurationError e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    private Element makeIntElement(String name, int value) {
        return makeTextElement(name, Integer.toString(value));
    }

    // utility method to create text node
    private Element makeTextElement(String tag, String value) {
        Element elem = doc.createElement(tag);
        elem.appendChild(doc.createTextNode(value));
        return elem;
    }

    private Element makePaintElement(String tag, Paint value) {
        return makeTextElement(tag, value.toString());
    }

}



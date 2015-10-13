package it.polito.dp2.FDS.sol1;

import it.polito.dp2.FDS.FlightMonitorException;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class FlightMonitorFactory extends it.polito.dp2.FDS.FlightMonitorFactory {
	
	private FlightMonitor fm;
	private Element root;
	public static Document doc;

	@Override
	public FlightMonitor newFlightMonitor() throws FlightMonitorException {
		String fname = System.getProperty("it.polito.dp2.FDS.sol1.FlightInfo.file");
		
		try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setValidating(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            db.setErrorHandler(new DomParseVHandler());
            
            doc = db.parse(new File(fname));
            root = doc.getDocumentElement();		
            
            fm = new FlightMonitor(root);
        }
		catch (NullPointerException npe){
			System.out.println("Null pointer exception: " + npe.getMessage());
			npe.printStackTrace();
            throw new FlightMonitorException();
		}
        catch (IOException ioe) {
            System.out.println("Input/Output error: " + ioe.getMessage());
            ioe.printStackTrace();
            throw new FlightMonitorException();
        }
        catch (SAXParseException spe) {
            System.out.println("Parsing exception for entity " + spe.getPublicId() +
                " at line: " + spe.getLineNumber() +
                " column: " + spe.getColumnNumber());
            throw new FlightMonitorException();
        }
        catch (SAXException se) {
            System.out.println("General SAX exception: " + se.getMessage());
            throw new FlightMonitorException();
        }
        catch (ParserConfigurationException pce) {
            System.out.println("Parser configuration exception: " + pce.getMessage());
            throw new FlightMonitorException();
        }
		catch (Exception e) {
			System.out.println("General exception: " + e.getMessage());
			e.printStackTrace();
			throw new FlightMonitorException();
		}
		
		return fm;
	}
}

class DomParseVHandler extends org.xml.sax.helpers.DefaultHandler {
	
	  // Validation errors are treated as fatal
	  public void error (SAXParseException e) throws SAXParseException
	  {
	    throw e;
	  }

	  // Warnings are displayed
	  public void warning (SAXParseException e) throws SAXParseException
	  {
	    System.out.println("** Warning"
	            + ", file " + e.getSystemId()
	            + ", line " + e.getLineNumber());
	    System.out.println("   " + e.getMessage() );
	    throw e;
	  }
}
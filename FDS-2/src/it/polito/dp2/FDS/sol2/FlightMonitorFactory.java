package it.polito.dp2.FDS.sol2;

import it.polito.dp2.FDS.MalformedArgumentException;
import it.polito.dp2.FDS.sol2.jaxb.FlightMonitorType;

import java.io.File;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.ValidationEventLocator;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

public class FlightMonitorFactory extends it.polito.dp2.FDS.FlightMonitorFactory {
		
	@Override
	public FlightMonitor newFlightMonitor(){
		
		FlightMonitor f = null;
		FlightMonitorType fmt = null; 
		String fname = System.getProperty("it.polito.dp2.FDS.sol2.FlightInfo.file");
			
            JAXBContext jc;
			try {
				jc = JAXBContext.newInstance("it.polito.dp2.FDS.sol2.jaxb");
			    Unmarshaller u = jc.createUnmarshaller();
				
				SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	                Schema schema = sf.newSchema(new File("xsd/FDSInfo.xsd"));
	                u.setSchema(schema);
	                u.setEventHandler(new ValidationEventHandler() {
	                        
	                        public boolean handleEvent(ValidationEvent ve) {
	                            if (ve.getSeverity() == ValidationEvent.WARNING ||
	                            	ve.getSeverity() == ValidationEvent.ERROR ||
	                            	ve.getSeverity() == ValidationEvent.FATAL_ERROR) {
	                            	
	                                ValidationEventLocator vel = ve.getLocator();
	                                System.out.println("Line:Col[" + vel.getLineNumber() +
	                                    ":" + vel.getColumnNumber() +
	                                    "]:" + ve.getMessage());
	                                
	                                return false;
	                            }
	                            return true;
	                        }
	                    }
	                );
	          
	            
	            JAXBElement<FlightMonitorType> j = (JAXBElement<FlightMonitorType>) u.unmarshal(new File(fname));
	            
				fmt = (FlightMonitorType) j.getValue();
				
				f = new FlightMonitor(fmt);
			} catch (JAXBException je) {
				// TODO Auto-generated catch block
				je.printStackTrace();
			} catch (SAXException se) {
				// TODO Auto-generated catch block
				se.printStackTrace();
			} catch (MalformedArgumentException me) {
				// TODO Auto-generated catch block
				me.printStackTrace();
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
   
        return f;

}
	
}

package it.polito.dp2.FDS.sol4.client;

import it.polito.dp2.FDS.lab4.gen.FDSBoard;
import it.polito.dp2.FDS.lab4.gen.FDSBoardService;
import it.polito.dp2.FDS.lab4.gen.FDSInfoService;
import it.polito.dp2.FDS.lab4.gen.FdsError_Exception;
import it.polito.dp2.FDS.lab4.gen.FdsInfoError_Exception;
import it.polito.dp2.FDS.lab4.gen.FlightInstanceType;
import it.polito.dp2.FDS.lab4.gen.FlightType;
import it.polito.dp2.FDS.lab4.gen.PassengerType;
import it.polito.dp2.FDS.sol4.jaxb.blist.BoardListType;
import it.polito.dp2.FDS.sol4.jaxb.blist.ObjectFactory;
import it.polito.dp2.FDS.sol4.jaxb.fdsboarding.BoardingType;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.ValidationEventLocator;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.ws.WebServiceException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;


public class FDSControlClient {
	
	private FDSBoardService fbs;
	private FDSBoard fb;
	
	
	public void setService(FDSBoardService fbs){
		this.fbs = fbs;
		this.fb = fbs.getFDSBoardSOAP();
		
	}
	
	public static void main(String[] args) {
		
		
		if(args.length != 2){
			System.err.println("Main args must be two");
			System.exit(2);
		}
		

		final FDSControlClient fcc = new FDSControlClient();
		BoardingType bt = fcc.boardingFactory(args[0]);
		URL url;
	
		try {
			url = new URL(bt.getEndpoint());
			FDSBoardService fbs = new FDSBoardService(url);
			fcc.setService(fbs);			
			
			
			if(bt.getStartBoarding() != null)
			{
				fcc.board(bt.getFlight(), bt.getDate());
			}
			
			for(String s : bt.getPassenger())
			{
				fcc.boardPassenger(s, bt.getFlight(), bt.getDate());
			}
			
			fcc.serialize(new File(args[1]), fcc.getBoardedPassengers(bt.getFlight(), bt.getDate()));
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.exit(2);
		} catch (FdsError_Exception e) {
			e.printStackTrace();
			System.exit(e.getFaultInfo().getErrorcode());
		} catch (JAXBException e) {
			e.printStackTrace();
			System.exit(2);
		} catch (SAXException e) {
			e.printStackTrace();
			System.exit(2);
		} catch(WebServiceException e){
			e.printStackTrace();
			System.exit(2);		
		}catch(Exception e){
			e.printStackTrace();
			System.exit(2);
		}  
		
/*		FDSInfoClient fic = new FDSInfoClient();
		
		
			URL url2;
			try {
				url2 = new URL("http://localhost:7071/fdsinfo");
				
				FDSInfoService fis = new FDSInfoService(url2);
				fic.setService(fis); 
			
			for(FlightInstanceType fit :  fic.getFlightInstances(null, null, null,1,20))
				{
					
					System.out.println("Flight: "+fit.getFlight()+" DepartureGate: "+fit.getDepartureGate()+"Delay: "+fit.getDelay()+"Status "+fit.getStatus().name()+"Date: "+fit.getDate().toString());
					System.out.println("[");
				}  
			
			XMLGregorianCalendar xgc = DatatypeFactory.newInstance().newXMLGregorianCalendar();
			xgc.setDay(4);
			xgc.setMonth(3);
			xgc.setYear(2010);
			
			for(PassengerType pt : fic.getPassengers("JQ086", xgc, 0,60))
			{
				System.out.println(pt.getName()+" "+pt.getSeat()+" "+pt.isBoarded()+" "+fic.getPassengers("JQ086", xgc, 0, 0).size());
				
			}
			
			for(FlightType ft : fic.getFlights(null, null, null,0,30))
			{				
				System.out.println("DepartureAirport: "+ft.getDepartureAirport()+" DestinationAirport: "+ft.getDestinationAirport()+" Number: "+ft.getNumber()+" DepTime: "+ft.getDepartureTime().toString());				
			} 
			
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FdsInfoError_Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DatatypeConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch(WebServiceException e){
				e.printStackTrace();
				System.exit(2);
			
			}
			
		
	
			
	/*		XMLGregorianCalendar xgc = DatatypeFactory.newInstance().newXMLGregorianCalendar();
			xgc.setDay(21);
			xgc.setMonth(2);
			xgc.setYear(2010);
			
			FlightInstanceType fit = fic.getFlightInstance("PF821", xgc);
			System.out.println(""+fit.getFlight()+fit.getDepartureGate()+fit.getAircraft()+fit.getStatus().name()); */
			
	//		FlightType asd=fic.getFlight("cristo");
	//		System.out.println(asd.getDepartureAirport()+asd.getDestinationAirport()+asd.getDepartureTime().toString());
			
		
	
	/*		Runnable r = new Runnable() {


			@Override
			public void run() {

			
			XMLGregorianCalendar xgc = null;
			try {
				xgc = DatatypeFactory.newInstance().newXMLGregorianCalendar();
				xgc.setDay(15);
				xgc.setMonth(2);
				xgc.setYear(2010);
			} catch (DatatypeConfigurationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				for(it.polito.dp2.FDS.sol4.jaxb.blist.PassengerType pt : fcc.getBoardedPassengers("HC890", xgc).getPassenger()){
				System.out.println(pt.getValue());
				}
			} catch (FdsError_Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			};
			
				for(int i=0; i<50; i++){
				new Thread(r).start();
				} */
			
			
		
			
	}
	
	public void board(String number, XMLGregorianCalendar date) throws  FdsError_Exception
	{
		
		if(fb == null) System.exit(2);		
		fb.startBoard(number, date);
		
	}
	
	public void boardPassenger(String name, String number,
			XMLGregorianCalendar date) throws FdsError_Exception
	{
		if(fb == null) System.exit(2);		
		fb.boardPassenger(name, number, date);
		
	}
	
	public BoardListType getBoardedPassengers(String number,
			XMLGregorianCalendar date) throws FdsError_Exception{
		
		if(fb == null) System.exit(2);
		BoardListType blt = new BoardListType();
		ArrayList<PassengerType> lpt = new ArrayList<PassengerType>() ;
		ArrayList<PassengerType> tmp = new ArrayList<PassengerType>();
		int i = 0;
		tmp = (ArrayList<PassengerType>) fb.getBoardedPassengers(number, date,i,40);
		while(tmp.size() != 0){
		lpt.addAll(tmp);
		i++;
		tmp = (ArrayList<PassengerType>) fb.getBoardedPassengers(number, date,i,40);
		}
		
		for(PassengerType p : lpt)
		{
			it.polito.dp2.FDS.sol4.jaxb.blist.PassengerType jaxpt = new it.polito.dp2.FDS.sol4.jaxb.blist.PassengerType();
			jaxpt.setSeat(p.getSeat());
			jaxpt.setValue(p.getName());
			blt.getPassenger().add(jaxpt);			
		}
		
		blt.setFlight(number);
		blt.setDate(date);
		
		return blt;
		
		
	}
	
		
	public BoardingType boardingFactory(String fname)
	{
		
		BoardingType bt = null; 
		
			
            JAXBContext jc;
			try {
				jc = JAXBContext.newInstance("it.polito.dp2.FDS.sol4.jaxb.fdsboarding");
			    Unmarshaller u = jc.createUnmarshaller();
				
				SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	                Schema schema = sf.newSchema(new File("xsd/fdsBoarding.xsd"));
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
	          
	            JAXBElement<BoardingType> j =  (JAXBElement<BoardingType>) u.unmarshal(new StreamSource(fname), BoardingType.class);
	            
				bt = (BoardingType) j.getValue();
				
				
			} catch (JAXBException je) {
				// TODO Auto-generated catch block
				je.printStackTrace();
			} catch (SAXException se) {
				// TODO Auto-generated catch block
				se.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        return bt;
		
	}
	
	
	public void serialize(File out, BoardListType blt) throws JAXBException, SAXException {
		
		JAXBContext jc = JAXBContext.newInstance( "it.polito.dp2.FDS.sol4.jaxb.blist" );
		JAXBElement<BoardListType> Element = (new ObjectFactory()).createBoardList(blt);
		Marshaller m = jc.createMarshaller();
		m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
		m.setProperty( Marshaller.JAXB_SCHEMA_LOCATION, "http://pad.polito.it/boardList xsd/boardList.xsd" );
		m.marshal(Element, out );
	}
	

}

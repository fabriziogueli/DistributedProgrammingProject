package it.polito.dp2.FDS.sol2;

import it.polito.dp2.FDS.Aircraft;
import it.polito.dp2.FDS.FlightInstanceReader;
import it.polito.dp2.FDS.FlightMonitor;
import it.polito.dp2.FDS.FlightMonitorFactory;
import it.polito.dp2.FDS.FlightReader;
import it.polito.dp2.FDS.PassengerReader;
import it.polito.dp2.FDS.Time;
import it.polito.dp2.FDS.sol2.jaxb.AircraftType;
import it.polito.dp2.FDS.sol2.jaxb.FlightInstanceType;
import it.polito.dp2.FDS.sol2.jaxb.FlightMonitorType;
import it.polito.dp2.FDS.sol2.jaxb.FlightType;
import it.polito.dp2.FDS.sol2.jaxb.ObjectFactory;
import it.polito.dp2.FDS.sol2.jaxb.PassengerType;
import it.polito.dp2.FDS.sol2.jaxb.StatusType;

import java.io.File;
import java.math.BigInteger;
import java.text.ParseException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.xml.sax.SAXException;
public class FDSInfoSerializer {

	private FlightMonitor monitor;
	private FlightMonitorType fmt;
			
		private FDSInfoSerializer() throws Exception {
			FlightMonitorFactory factory = FlightMonitorFactory.newInstance();
			monitor = factory.newFlightMonitor();
			
			fmt = new FlightMonitorType();
			
			flightMonitorCopy();
		}
	

		public static void main(String[] args) {
			try {
				if (args.length != 1) {
			        System.err.println("args length error");
			        System.exit(1);
				}
				
				FDSInfoSerializer serializer = new FDSInfoSerializer();
	            
				serializer.serialize(new File(args[0]));

			} catch (SAXException se) {
				se.printStackTrace();
				System.exit(1);
			}
			catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
			
		}
	
		public void addAicraftType(Aircraft a) {
			
			AircraftType at = new AircraftType();
			at.setModel(a.model);			
			
			for(String s: a.seats){
				addSeat(s, at);
			}
			fmt.getAircraft().add(at);
		}
		
		public void addSeat(String seat, AircraftType at)
		{
		    at.getSeat().add(seat);
			
		}
		
		public void addFlightType(FlightReader f) throws ParseException, DatatypeConfigurationException
		{
			Time t = f.getDepartureTime();		
			FlightType ft = new FlightType();
			ft.setDepartureAirport(f.getDepartureAirport());
			ft.setDestinationAirport(f.getDestinationAirport());
			ft.setNumber(f.getNumber());
	
			XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendarTime(t.getHour(), t.getMinute(), 0,0);			
			ft.setDepartureTime(date2);


			fmt.getFlight().add(ft);		
		}
		
		public void addFlightInstanceType(FlightInstanceReader fir) throws DatatypeConfigurationException
		{
					
			BigInteger bi = BigInteger.valueOf(fir.getDelay());
			FlightInstanceType fit = new FlightInstanceType();
			
			for(AircraftType aty : fmt.getAircraft())
			{
				if(aty.getModel().equals(fir.getAircraft().model))
				{
					fit.setAircraft(aty);
					break;
					
				}
					
					
			}
			
			for(FlightType fty : fmt.getFlight())
			{
				if(fty.getNumber().equals(fir.getFlight().getNumber()))
				{
					fit.setFlight(fty);
					break;
				}
			}
			
			if(fir.getDepartureGate() != null)
			fit.setDepartureGate(fir.getDepartureGate());
			else fit.setDepartureGate("");
	
			fit.setDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(fir.getDate()));			
			fit.setDelay(bi);
			fit.setStatus(StatusType.valueOf(fir.getStatus().toString().toUpperCase()));
			for(PassengerReader pr : fir.getPassengerReaders(null))
			{
				addPassengerType(pr, fit);
				
			}
					
			fmt.getFlightInstance().add(fit);
		
			
		}
		
		public void addPassengerType(PassengerReader pr, FlightInstanceType fit)
		{
			PassengerType pt = new PassengerType();
			pt.setName(pr.getName());
			pt.setBoarded(pr.boarded());
			if(pr.getSeat() != null)
			pt.setSeat(pr.getSeat());
			else pt.setSeat("");
			fit.getPassenger().add(pt);
		}

		public void serialize(File out) throws JAXBException, SAXException {
			
			JAXBContext jc = JAXBContext.newInstance( "it.polito.dp2.FDS.sol2.jaxb" );
			
		
			JAXBElement<FlightMonitorType> Element = (new ObjectFactory()).createFlightMonitor(fmt);
		
		
			Marshaller m = jc.createMarshaller();
			m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
			m.setProperty( Marshaller.JAXB_SCHEMA_LOCATION, "http://www.example.org/FDSInfo xsd/FDSInfo.xsd" );
			m.marshal(Element, out );
		}
		
		
		public void flightMonitorCopy() throws Exception {
			
			for(Aircraft a: monitor.getAircrafts()){
				addAicraftType(a);
			}
			
			for(FlightReader f: monitor.getFlights(null, null, null)){
				addFlightType(f);
			}
			
			for(FlightInstanceReader fi: monitor.getFlightInstances(null, null, null)){
				addFlightInstanceType(fi);
			}
			
			
		}

}

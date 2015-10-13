package it.polito.dp2.FDS.sol1;

import it.polito.dp2.FDS.Aircraft;
import it.polito.dp2.FDS.FlightInstanceReader;
import it.polito.dp2.FDS.FlightMonitor;
import it.polito.dp2.FDS.FlightMonitorException;
import it.polito.dp2.FDS.FlightMonitorFactory;
import it.polito.dp2.FDS.FlightReader;
import it.polito.dp2.FDS.PassengerReader;
import it.polito.dp2.FDS.Time;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class FDSInfoSerializer {

	private FlightMonitor monitor;
	DateFormat dateFormat;
	protected Document doc;	
	protected Element root;	
	protected Element aircrafts;
	protected Element flights;
	protected Element flightinstances;
	
	public FDSInfoSerializer(String rootname) throws FlightMonitorException, ParserConfigurationException {
		FlightMonitorFactory factory = FlightMonitorFactory.newInstance();
		monitor = factory.newFlightMonitor();
		dateFormat = new SimpleDateFormat("dd/MM/yyyy z");
		
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setValidating(true);
		domFactory.setNamespaceAware(true);
		DocumentBuilder builder = domFactory.newDocumentBuilder();

		// Create the document
		doc = builder.newDocument();

		// Create and append the root element
		root = (Element) doc.createElement(rootname);
		
		aircrafts = doc.createElement("Aircrafts");
		
		flights = doc.createElement("Flights");
		
		flightinstances = doc.createElement("FlightInstances");
		
		root.appendChild(aircrafts);
		root.appendChild(flights);
		root.appendChild(flightinstances);
		
		doc.appendChild(root);
	}

	
	public static void main(String[] args) {
		FDSInfoSerializer f;
		
		if(args.length != 1)
		{
			System.err.println("args length error");
			System.exit(2);
		}
			try {
				f = new FDSInfoSerializer("FlightMonitor");
				f.fillDocument();
				f.serialize(new File(args[0]));
			} catch (ParserConfigurationException e) {
				System.exit(1);
				e.printStackTrace();
			}
		 catch (FlightMonitorException e) {
			System.err.println("Could not instantiate flight monitor object");
			e.printStackTrace();
			System.exit(1);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	// Add Aircraft to Aircrafts
		public void addAicraft(Aircraft a) {
			
			Element aircraft = doc.createElement("Aircraft");
			aircraft.setAttribute("model", a.model);			
			
			for(String s: a.seats){
				addSeat(s, aircraft);
			}			
			aircrafts.appendChild(aircraft);
		}
		
		// Add Seat to Aircraft
		public void addSeat(String seat, Element aircraft)
		{
			Element seats = doc.createElement("Seats");
			seats.setAttribute("id_seat", seat);
			aircraft.appendChild(seats);
			
		}
		
		//Add Flight to Flights
		public void addFlight(FlightReader f)
		{
			
			Time t = f.getDepartureTime();
			String time = ""+t.getHour()+":"+t.getMinute();
			Element flight = doc.createElement("Flight");
			flight.setAttribute("DestinationAirport", f.getDestinationAirport());
			flight.setAttribute("DepartureAirport", f.getDepartureAirport());
			flight.setAttribute("DepartureTime",time);
			flight.setAttribute("Number", f.getNumber());
			
			flights.appendChild(flight);		
		}
		
		//Add FlightInstance to FlightInstances
		public void addFlightInstance(FlightInstanceReader fir)
		{
			Element flightinstance = doc.createElement("FlightInstance");
			if(fir.getDepartureGate() == null)
				flightinstance.setAttribute("DepartureGate", "");
			else
			flightinstance.setAttribute("DepartureGate", fir.getDepartureGate());
			
			flightinstance.setAttribute("Aircraft", fir.getAircraft().model);
			flightinstance.setAttribute("Flight", fir.getFlight().getNumber());
			flightinstance.setAttribute("Delay", ""+fir.getDelay());
			flightinstance.setAttribute("Date", dateFormat.format(fir.getDate().getTime()));
			flightinstance.setAttribute("Status",fir.getStatus().name());
			
			
			for(PassengerReader pr : fir.getPassengerReaders(null))
			{
				addPassenger(pr, flightinstance);
				
			}
					
			flightinstances.appendChild(flightinstance);
		
			
		}
		
		//Add Passenger to FlightInstance
		public void addPassenger(PassengerReader pr, Element flightinstance)
		{
			Element passenger = doc.createElement("Passenger");
			passenger.setAttribute("boarded", Boolean.toString(pr.boarded()));
			passenger.setAttribute("Name", pr.getName());
			
			if(pr.getSeat() == null)
				passenger.setAttribute("Seat","");
				
			else
			passenger.setAttribute("Seat", pr.getSeat());
			
			flightinstance.appendChild(passenger);
		}

	// Serializes the document to a PrintStream
		public void serialize(File out) throws TransformerException {
			TransformerFactory xformFactory = TransformerFactory.newInstance();
			Transformer idTransform;
			idTransform = xformFactory.newTransformer();
			idTransform.setOutputProperty(OutputKeys.INDENT, "yes");
			idTransform.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "flightInfo.dtd");
			Source input = new DOMSource(doc);
			Result output = new StreamResult(out);
			idTransform.transform(input, output);
		}
		
		
		public void fillDocument() throws Exception {
			
			for(Aircraft a: monitor.getAircrafts()){
				addAicraft(a);
			}
			
			for(FlightReader f: monitor.getFlights(null, null, null)){
				addFlight(f);
			}
			
			for(FlightInstanceReader fi: monitor.getFlightInstances(null, null, null)){
				addFlightInstance(fi);
			}
			
			
		}

}

package it.polito.dp2.FDS.sol1;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import it.polito.dp2.FDS.Aircraft;
import it.polito.dp2.FDS.FlightInstanceReader;
import it.polito.dp2.FDS.FlightInstanceStatus;
import it.polito.dp2.FDS.FlightMonitorException;
import it.polito.dp2.FDS.FlightReader;
import it.polito.dp2.FDS.MalformedArgumentException;
import it.polito.dp2.FDS.Time;
import it.polito.dp2.FDS.sol1.FlightInstance.NullAircraftException;
import it.polito.dp2.FDS.sol1.FlightInstance.NullFlightException;

public class FlightMonitor implements it.polito.dp2.FDS.FlightMonitor{
	
	private Set<it.polito.dp2.FDS.Aircraft> a = new HashSet<it.polito.dp2.FDS.Aircraft>();
	private List<it.polito.dp2.FDS.FlightInstanceReader> fir_list = new LinkedList<it.polito.dp2.FDS.FlightInstanceReader>();
	private List<it.polito.dp2.FDS.FlightReader> fr_list = new LinkedList<it.polito.dp2.FDS.FlightReader>();

	
	public FlightMonitor (Element element) throws MalformedArgumentException, NullFlightException, NullAircraftException, FlightMonitorException {
		if(element == null) return;
		else{
			NodeList anl = element.getElementsByTagName("Aircraft");
			NodeList fnl = element.getElementsByTagName("Flight");
			NodeList finl = element.getElementsByTagName("FlightInstance");
			
			
    		Element e;
    		Element s;
    		Aircraft aircraft;
    		Flight f;
    		FlightInstance fi;
			
    		for (int i=0; i < anl.getLength(); i++) {
                e = (Element)anl.item(i);
                HashSet<String> seat_list = new HashSet<String>();
                NodeList snl = e.getElementsByTagName("Seats");                             
                for (int j=0; j < snl.getLength(); j++) {
                	
                    s = (Element)snl.item(j);                   
                    seat_list.add(s.getAttribute("id_seat"));
        		}
                
                aircraft = new Aircraft(e.getAttribute("model"),seat_list);
                a.add(aircraft);

            }
    		
    		for (int i=0; i < fnl.getLength(); i++){
    			 e = (Element)fnl.item(i);
    			 f = new Flight(e);
    			 fr_list.add(f);
    		}
    		
    		for (int i=0; i < finl.getLength(); i++) {
    			e = (Element)finl.item(i);
    			fi = new FlightInstance(e, this);
    			fir_list.add(fi);   			
    		}
			
			
		}
		
		
	}

	@Override
	public Set<Aircraft> getAircrafts() {
		return this.a;		
	}

	@Override
	public FlightReader getFlight(String arg0)
			throws MalformedArgumentException {
		if(arg0 != null && Flight.flightNumberCheck(arg0) )
		{
			for(FlightReader fr : fr_list)
			{
				if(fr.getNumber().equals(arg0))
					return fr;				
			}
			return null;
		}
		else throw new MalformedArgumentException();
	}

	@Override
	public FlightInstanceReader getFlightInstance(String arg0,
			GregorianCalendar arg1) throws MalformedArgumentException {
		
		
		
		if(arg0 != null && Flight.flightNumberCheck(arg0) && arg1 != null)
		{
			arg1.set(Calendar.HOUR,0);
			arg1.set(Calendar.HOUR_OF_DAY,0);
			arg1.set(Calendar.AM_PM,0);
			arg1.set(Calendar.MINUTE,0);
			arg1.set(Calendar.SECOND,0);
			arg1.set(Calendar.MILLISECOND,0);
			
			for(FlightInstanceReader fi : fir_list)
			{
				if(fi.getFlight().getNumber().equals(arg0) && fi.getDate().equals(arg1))
					return fi;				
			}
			return null;
			
		}
		else throw new MalformedArgumentException();
	}

	@Override
	public List<FlightInstanceReader> getFlightInstances(String arg0,
			GregorianCalendar arg1, FlightInstanceStatus arg2)
			throws MalformedArgumentException {
		
		
		
		LinkedList<FlightInstanceReader> temp = new LinkedList<FlightInstanceReader>(fir_list);
		
			if(arg0 != null){
				
				if(!Flight.flightNumberCheck(arg0))	throw new MalformedArgumentException();
				for(Iterator<FlightInstanceReader> iter = temp.iterator(); iter.hasNext();)
				{
					FlightInstanceReader fir = iter.next();
					if(!fir.getFlight().getNumber().equals(arg0))
						iter.remove();
				}
			}
			
			
			if(arg1 != null){
				arg1.set(Calendar.HOUR,0);
				arg1.set(Calendar.HOUR_OF_DAY,0);
				arg1.set(Calendar.AM_PM,0);
				arg1.set(Calendar.MINUTE,0);
				arg1.set(Calendar.SECOND,0);
				arg1.set(Calendar.MILLISECOND,0);
				
				for(Iterator<FlightInstanceReader> iter = temp.iterator(); iter.hasNext();)
				{
					FlightInstanceReader fir = iter.next();
					if(fir.getDate().before(arg1))
						iter.remove();
				}
			}
			
			if(arg2 != null){
				for(Iterator<FlightInstanceReader> iter = temp.iterator(); iter.hasNext();)
				{
					FlightInstanceReader fir = iter.next();
					if(arg2 != fir.getStatus())
						iter.remove();
				}
			}
			return temp;
			
		
		
		
	}

	@Override
	public List<FlightReader> getFlights(String arg0, String arg1, Time arg2)
			throws MalformedArgumentException {
		LinkedList<FlightReader> tmp = new LinkedList<FlightReader>(fr_list);
		
			if(arg0 != null)
			{
				if(!Flight.airportCodeCheck(arg0)) throw new MalformedArgumentException();
				for(Iterator<FlightReader> iter = tmp.iterator(); iter.hasNext();)
				{
					FlightReader f = iter.next();
					if(!f.getDepartureAirport().equals(arg0))
						iter.remove();
				}
				
			}
			
			if(arg1 != null)
			{
				if(!Flight.airportCodeCheck(arg1))	throw new MalformedArgumentException();
				for(Iterator<FlightReader> iter = tmp.iterator(); iter.hasNext();)
				{
					FlightReader f = iter.next();
					if(!f.getDestinationAirport().equals(arg1))
						iter.remove();
				}
			}
			
			if(arg2 != null)
			{
				for(Iterator<FlightReader> iter = tmp.iterator(); iter.hasNext();)
				{
					FlightReader f = iter.next();
					if(f.getDepartureTime().precedes(arg2))
						iter.remove();
				}
			}
			
			return tmp;
		
	}
}
package it.polito.dp2.FDS.sol2;

import it.polito.dp2.FDS.Aircraft;
import it.polito.dp2.FDS.FlightInstanceReader;
import it.polito.dp2.FDS.FlightInstanceStatus;
import it.polito.dp2.FDS.FlightReader;
import it.polito.dp2.FDS.MalformedArgumentException;
import it.polito.dp2.FDS.Time;
import it.polito.dp2.FDS.sol2.jaxb.AircraftType;
import it.polito.dp2.FDS.sol2.jaxb.FlightInstanceType;
import it.polito.dp2.FDS.sol2.jaxb.FlightMonitorType;
import it.polito.dp2.FDS.sol2.jaxb.FlightType;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class FlightMonitor implements it.polito.dp2.FDS.FlightMonitor{
	
	private Set<Aircraft> a = new HashSet<Aircraft>();
	private List<FlightInstanceReader> fir_list = new LinkedList<FlightInstanceReader>();
	private List<FlightReader> fr_list = new LinkedList<FlightReader>();

	
	public FlightMonitor (FlightMonitorType fmt) throws MalformedArgumentException {
		if(fmt == null) return;
		else{
			
			for(AircraftType at : fmt.getAircraft())
			{
				Set<String> seats = new HashSet<String>();
				for(String s : at.getSeat())
				{
					seats.add(s);
				}
				a.add(new Aircraft(at.getModel(),seats));
			}
			
			for(FlightType ft : fmt.getFlight())
			{
				fr_list.add(new Flight(ft));
			}
			
			for(FlightInstanceType fit : fmt.getFlightInstance())
			{
				fir_list.add(new FlightInstance(fit, this));
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
		if(arg0 != null && Flight.flightNumberCheck(arg0) && arg1!= null)
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
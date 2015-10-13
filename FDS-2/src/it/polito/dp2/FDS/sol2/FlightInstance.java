package it.polito.dp2.FDS.sol2;

import it.polito.dp2.FDS.Aircraft;
import it.polito.dp2.FDS.FlightInstanceReader;
import it.polito.dp2.FDS.FlightInstanceStatus;
import it.polito.dp2.FDS.FlightReader;
import it.polito.dp2.FDS.MalformedArgumentException;
import it.polito.dp2.FDS.PassengerReader;
import it.polito.dp2.FDS.sol2.jaxb.AircraftType;
import it.polito.dp2.FDS.sol2.jaxb.FlightInstanceType;
import it.polito.dp2.FDS.sol2.jaxb.FlightType;
import it.polito.dp2.FDS.sol2.jaxb.PassengerType;

import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

public class FlightInstance implements FlightInstanceReader{
	
	private FlightInstanceStatus status;
	private String departure_gate;
	private FlightReader fr;
	private Aircraft aicraft;
	private int delay;
	private Set<PassengerReader> passengers_list = new HashSet<PassengerReader>();
	private GregorianCalendar date;
	
	public FlightInstance(FlightInstanceType fit,FlightMonitor fm) throws MalformedArgumentException{
		if(fit == null) return;
		else{
			if(fit.getDepartureGate().equals(""))
				setDepartureGate(null);
			else
			setDepartureGate(fit.getDepartureGate());
			
			
			setDelay(fit.getDelay().intValue());
			setFlight(fm.getFlight(((FlightType)fit.getFlight()).getNumber()));
			String model = ((AircraftType)fit.getAircraft()).getModel();
			for(Aircraft a : fm.getAircrafts())
			{
				if(a.model.equals(model)){
					setAircraft(a);
					break;
				}
			}
						setDate(fit.getDate().toGregorianCalendar());
					
			setStatus(FlightInstanceStatus.valueOf(fit.getStatus().toString().toUpperCase()));
			
		   for(PassengerType pt : fit.getPassenger())
		   {
			   passengers_list.add(new Passenger(pt, this));
		   }
					
			
		}
	}

	@Override
	public Aircraft getAircraft() {
		return this.aicraft;
	}

	@Override
	public GregorianCalendar getDate() {
		return this.date;
	}

	@Override
	public int getDelay() {
		return this.delay;
	}

	@Override
	public String getDepartureGate() {
		return this.departure_gate;
	}

	@Override
	public FlightReader getFlight() {
		return this.fr;
	}
	
	public void setStatus( FlightInstanceStatus fis)
	{
		this.status = fis;
	}
	
	public void setDepartureGate(String dg)
	{
		this.departure_gate = dg;
	}
	
	public void setFlight(FlightReader f)
	{
		this.fr = f;
	}
	
	public void setAircraft(Aircraft a)
	{
		this.aicraft = a;
	}
	
	public void setDelay(int d)
	{
		this.delay = d;
	}
	
	public void setDate(GregorianCalendar d)
	{
		this.date = d;
	}
	
	public void setPassengers(Set<PassengerReader> pr)
	{
		this.passengers_list = pr;
	}

	@Override
	public Set<PassengerReader> getPassengerReaders(String arg0) {
		HashSet<PassengerReader> tmp = new HashSet<PassengerReader>(passengers_list);
		
		if(arg0 != null)
		{
			for(PassengerReader p : tmp){
				if(!p.getName().startsWith(arg0))
					tmp.remove(p);
			}
			return tmp;
			
		}
		else return passengers_list;
	}

	@Override
	public FlightInstanceStatus getStatus() {
		return this.status;
	}

}
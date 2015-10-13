package it.polito.dp2.FDS.sol1;

import it.polito.dp2.FDS.FlightInstanceReader;
import it.polito.dp2.FDS.FlightMonitorException;

import org.w3c.dom.Element;


public class Passenger implements it.polito.dp2.FDS.PassengerReader{
	
	private boolean boarded;
	private String name;
	private String seat;
	private FlightInstanceReader fir;

	
	public Passenger(Element e, FlightInstanceReader fir) throws FlightMonitorException{
		if (e == null) return;
		else{
			setBoarded(Boolean.parseBoolean(e.getAttribute("boarded")));
			setName(e.getAttribute("Name"));
			
			if(e.getAttribute("Seat").equals(""))
				setSeat(null);
			
			else
			setSeat(e.getAttribute("Seat"));
			
			this.fir = fir;	
			if(boarded && seat == null) throw new FlightMonitorException();
		}
	}
	
	public void setBoarded(boolean b)
	{
		this.boarded = b;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setSeat(String seat)
	{
		this.seat = seat;
	}
	
	@Override
	public boolean boarded() {
		return this.boarded;
	}

	@Override
	public FlightInstanceReader getFlightInstance() {
		return this.fir;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getSeat() {
		return this.seat;
	}
	
	
	
}
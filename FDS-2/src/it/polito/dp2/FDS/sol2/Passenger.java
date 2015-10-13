package it.polito.dp2.FDS.sol2;

import it.polito.dp2.FDS.FlightInstanceReader;
import it.polito.dp2.FDS.MalformedArgumentException;
import it.polito.dp2.FDS.sol2.jaxb.PassengerType;


public class Passenger implements it.polito.dp2.FDS.PassengerReader{
	
	private boolean boarded;
	private String name;
	private String seat;
	private FlightInstanceReader fir;

	
	public Passenger(PassengerType pt, FlightInstanceReader fir) throws MalformedArgumentException{
		if (pt == null) return;
		else{
			setBoarded(pt.isBoarded());
			setName(pt.getName());
			if(pt.getSeat().equals(""))
				setSeat(null);
			else
			setSeat(pt.getSeat());
			
			this.fir = fir;		
			
			if(boarded && seat==null) throw new MalformedArgumentException();
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
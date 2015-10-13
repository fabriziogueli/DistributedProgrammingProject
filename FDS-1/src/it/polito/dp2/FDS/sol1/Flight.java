package it.polito.dp2.FDS.sol1;

import org.w3c.dom.Element;

import it.polito.dp2.FDS.FlightMonitorException;
import it.polito.dp2.FDS.MalformedArgumentException;
import it.polito.dp2.FDS.Time;


public class Flight implements it.polito.dp2.FDS.FlightReader{
	
	
	private String departure_airport;
	private Time departure_time;
	private String destination_airport;
	private String number;
	

	public Flight(Element e) throws FlightMonitorException{
		if(e == null) return;
		else{
			String number = e.getAttribute("Number");
			String departure = e.getAttribute("DepartureAirport");
			String destination = e.getAttribute("DestinationAirport");
			if(!flightNumberCheck(number)) throw new FlightMonitorException();
			setNumber(number);
			if(!airportCodeCheck(departure)) throw new FlightMonitorException();
    		setDepartureAirport(departure);
    		if(!airportCodeCheck(destination)) throw new FlightMonitorException();
    		setDestinationAirport(destination);
    		String [] time = e.getAttribute("DepartureTime").split(":");
    		Time t = new Time(Integer.parseInt(time[0]), Integer.parseInt(time[1]));
    		setDepartureTime(t);    					
		}
	}

	@Override
	public String getDepartureAirport() {
		return this.departure_airport;
	}

	@Override
	public Time getDepartureTime() {
		return this.departure_time;
	}

	@Override
	public String getDestinationAirport() {
		return this.destination_airport;
	}

	@Override
	public String getNumber() {
		return this.number;
	}
	
	public void setNumber(String number)
	{
		this.number = number;
	}
	
	public void setDestinationAirport(String destination_airport)
	{
		this.destination_airport=destination_airport;
	}
	
	public void setDepartureAirport(String departure_airport)
	{
		this.departure_airport = departure_airport;
	}
	
	public void setDepartureTime(Time departure_time)
	{
		this.departure_time = departure_time;
	}
	
	public static boolean flightNumberCheck(String number)
	{
		String regex = "[A-Z]{2}[0-9]{0,4}";
	     if(!number.matches(regex))
			return false;
		
		else
		return true;
		
	}
	
	public static boolean airportCodeCheck(String code)
	{
		String regex = "[A-Z]{3}";

		if(!code.matches(regex))
			return false;
		
		else return true;
	}
	
	
	
	
}
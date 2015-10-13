package it.polito.dp2.FDS.sol2;

import it.polito.dp2.FDS.FlightReader;
import it.polito.dp2.FDS.Time;
import it.polito.dp2.FDS.sol2.jaxb.FlightType;


public class Flight implements FlightReader{
	
	
	private String departure_airport;
	private Time departure_time;
	private String destination_airport;
	private String number;
	

	public Flight(FlightType ft){
		if(ft == null) return;
		else{
			setNumber(ft.getNumber());
    		setDepartureAirport(ft.getDepartureAirport());
    		setDestinationAirport(ft.getDestinationAirport());
    		setDepartureTime(new Time(ft.getDepartureTime().getHour(),ft.getDepartureTime().getMinute()));    					
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
		String regex = "[a-zA-Z]{3}";

		if(!code.matches(regex))
			return false;
		
		else return true;
	}
	
	
	
	
}
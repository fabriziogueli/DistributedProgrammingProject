package it.polito.dp2.FDS.sol1;

import it.polito.dp2.FDS.Aircraft;
import it.polito.dp2.FDS.FlightInstanceStatus;
import it.polito.dp2.FDS.FlightMonitorException;
import it.polito.dp2.FDS.FlightReader;
import it.polito.dp2.FDS.MalformedArgumentException;
import it.polito.dp2.FDS.PassengerReader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class FlightInstance implements it.polito.dp2.FDS.FlightInstanceReader{
	
	private FlightInstanceStatus status;
	private String departure_gate;
	private FlightReader fr;
	private Aircraft aicraft;
	private int delay;
	private Set<PassengerReader> passengers_list = new HashSet<PassengerReader>();
	private GregorianCalendar date;
	
	public FlightInstance(Element e,FlightMonitor fm) throws MalformedArgumentException, NullFlightException, NullAircraftException, FlightMonitorException{
		if(e == null) return;
		else{
			if(e.getAttribute("DepartureGate").equals(""))
				setDepartureGate(null);
			else
			setDepartureGate(e.getAttribute("DepartureGate"));
			
			
			setDelay(Integer.parseInt(e.getAttribute("Delay")));
			if(fm.getFlight(e.getAttribute("Flight")) == null) throw new NullFlightException();
			setFlight(fm.getFlight(e.getAttribute("Flight")));
			for(Aircraft a : fm.getAircrafts())
			{
				if(a.model.equals(e.getAttribute("Aircraft"))){
					setAircraft(a);
					break;					
				}
					
			}
			if(getAircraft() == null) throw new NullAircraftException();
			SimpleDateFormat format =  new SimpleDateFormat("dd/MM/yyyy z");
					Date date;
					try {
						date = format.parse(e.getAttribute("Date"));
						
						GregorianCalendar calendar = new GregorianCalendar();
						calendar.set(Calendar.HOUR,0);
						calendar.set(Calendar.HOUR_OF_DAY,0);
						calendar.set(Calendar.AM_PM,0);
						calendar.set(Calendar.MINUTE,0);
						calendar.set(Calendar.SECOND,0);
						calendar.set(Calendar.MILLISECOND,0);
						calendar.setTime(date);
						calendar.setTimeZone(format.getTimeZone());
				
						setDate(calendar);
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			setStatus(FlightInstanceStatus.valueOf(e.getAttribute("Status")));
			
			NodeList pnl = e.getElementsByTagName("Passenger");  
			Element p;
			for (int i=0; i < pnl.getLength(); i++) {
                p = (Element)pnl.item(i);
                Passenger ps = new Passenger(p,this);
                passengers_list.add(ps);
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
		}
		return tmp;
	}

	@Override
	public FlightInstanceStatus getStatus() {
		return this.status;
	}
	
	
	public class NullFlightException extends Exception{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
	}
	
	public class NullAircraftException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
	}

}
package it.polito.dp2.FDS.sol3;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.xml.ws.Holder;

import it.polito.dp2.FDS.lab3.FDSBookingClient;
import it.polito.dp2.FDS.lab3.MissingDataException;
import it.polito.dp2.FDS.lab3.OperationFailException;
import it.polito.dp2.FDS.lab3.gen.FDSBooking;
import it.polito.dp2.FDS.lab3.gen.FDSBookingService;
import it.polito.dp2.FDS.lab3.gen.FlightDateType;
import it.polito.dp2.FDS.lab3.gen.InvalidFlightInstanceFault_Exception;

public class FDSBookingClientImpl implements FDSBookingClient {
	
	private String FlightNumber;
	private GregorianCalendar gdate;
	private URL url;
	
	
	@Override
	public void setFlightNumber(String number) {
		this.FlightNumber = number;
		
	}

	@Override
	public void setDepartureDate(GregorianCalendar gdate) {
		this.gdate = gdate;
		
	}

	@Override
	public void setServiceURL(URL url) {
		this.url = url;
		
	}

	@Override
	public Set<String> book(Set<String> passengerNames,
			boolean partialBookingAllowed) throws MissingDataException,
			OperationFailException {
		
		if(this.url == null)
			try {
				this.url= new URL(System.getProperty("it.polito.dp2.FDS.sol3.URL"));
			} catch (MalformedURLException e1) {
				this.url = null;
				e1.printStackTrace();
			}
	
		if(passengerNames.size() == 0 || passengerNames == null || this.FlightNumber == null || this.gdate == null || this.url == null) throw new MissingDataException();
		
		FDSBookingService fbs = new FDSBookingService(url);
		FDSBooking fb = fbs.getFDSBookingServiceSOAPPort();
		FlightDateType fdt = new FlightDateType();
		fdt.setDay(this.gdate.get(Calendar.DAY_OF_MONTH));
		fdt.setMonth(this.gdate.get(Calendar.MONTH)+1);
		fdt.setYear(BigInteger.valueOf(this.gdate.get(Calendar.YEAR)));
		Holder<List<String>> h = new Holder<List<String>>();
		h.value = new LinkedList<String>();
		h.value.addAll(passengerNames);
		Holder<Boolean> hb = new Holder<Boolean>();
		try {
			fb.book(FlightNumber, fdt, partialBookingAllowed, h, hb);
		} catch (InvalidFlightInstanceFault_Exception e) {
			e.printStackTrace();
			throw new OperationFailException();			
		}
		
		if(hb.value == false) {
		//	System.out.println(h.value);
			throw new OperationFailException();
		}
		else
			return new HashSet<String>(h.value);
	}

	@Override
	public Set<String> getPassengers() throws MissingDataException,
			OperationFailException {
		
		if(this.url == null)
			try {
				this.url= new URL(System.getProperty("it.polito.dp2.FDS.sol3.URL"));
			} catch (MalformedURLException e1) {
				this.url = null;
				e1.printStackTrace();
			}
		
		if(this.FlightNumber == null || this.gdate == null || this.url == null) throw new MissingDataException();
		
		FDSBookingService fbs = new FDSBookingService(url);
		FDSBooking fb = fbs.getFDSBookingServiceSOAPPort();
		FlightDateType fdt = new FlightDateType();
		fdt.setDay(this.gdate.get(Calendar.DAY_OF_MONTH));
		fdt.setMonth(this.gdate.get(Calendar.MONTH)+1);
		fdt.setYear(BigInteger.valueOf(this.gdate.get(Calendar.YEAR)));
		try {
			return new HashSet<String> (fb.getPassengerList(this.FlightNumber, fdt));
		} catch (InvalidFlightInstanceFault_Exception e) {
			e.printStackTrace();
			throw new OperationFailException();
		}
		
	}

}

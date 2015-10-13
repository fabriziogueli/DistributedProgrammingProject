package it.polito.dp2.FDS.sol4.server;

import it.polito.dp2.FDS.FlightInstanceReader;
import it.polito.dp2.FDS.FlightInstanceStatus;
import it.polito.dp2.FDS.FlightMonitor;
import it.polito.dp2.FDS.MalformedArgumentException;
import it.polito.dp2.FDS.PassengerReader;
import it.polito.dp2.FDS.Random.FlightInstance;
import it.polito.dp2.FDS.Random.Passenger;
import it.polito.dp2.FDS.lab4.gen.FDSBoard;
import it.polito.dp2.FDS.lab4.gen.FdsError;
import it.polito.dp2.FDS.lab4.gen.FdsError_Exception;
import it.polito.dp2.FDS.lab4.gen.PassengerType;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.jws.WebService;
import javax.xml.datatype.XMLGregorianCalendar;

@WebService(name="FDSBoardService",
targetNamespace="http://www.example.org/FDSControl/",
endpointInterface="it.polito.dp2.FDS.lab4.gen.FDSBoard",
serviceName="FDSBoardService", 
portName="FDSBoardSOAP")


public class FDSBoardImpl implements FDSBoard{
	
	private FlightMonitor fm;
	private HashMap<FlightInstanceReader,ReentrantReadWriteLock> firmap;
	public FDSBoardImpl(FlightMonitor fm,HashMap<FlightInstanceReader,ReentrantReadWriteLock> firmap) {
		this.fm = fm;	
		this.firmap = firmap;
	}

	@Override
	public void startBoard(String number, XMLGregorianCalendar date) throws FdsError_Exception {
		FdsError ferr = new FdsError();
		ferr.setErrorcode(1);
		Lock r = null;
		try {
			FlightInstanceReader fir = fm.getFlightInstance(number, date.toGregorianCalendar());
			if(fir != null){
				r = firmap.get(fir).writeLock();
				r.lock();
					if (fir.getStatus() == FlightInstanceStatus.CHECKINGIN){
						((FlightInstance)fir).setStatus(FlightInstanceStatus.BOARDING);
						return;
					}
					else {
						throw new FdsError_Exception(number+" "+fir.getStatus().toString()+" FlightInstanceStatus is not CheckinIn", ferr);
					}


			}else {				
				throw new FdsError_Exception("Unknown Flightinstance", ferr);
			}
		} catch (MalformedArgumentException e) {
			throw new FdsError_Exception(e.getMessage(), ferr);
		}
		
		finally {if(r != null) r.unlock();}

	}

	@Override
	public void boardPassenger(String name,  String number,
			XMLGregorianCalendar date) throws FdsError_Exception {
		FdsError ferr = new FdsError();
		ferr.setErrorcode(1);
		Lock r = null;
		try {
			FlightInstanceReader fir = fm.getFlightInstance(number, date.toGregorianCalendar());
			
			if(fir != null){
				r = firmap.get(fir).writeLock();
				r.lock();
					if(fir.getStatus() != FlightInstanceStatus.BOARDING ) throw new FdsError_Exception("FlightInstanceStatus is not Boarding", ferr);
					Set<PassengerReader> spr = fir.getPassengerReaders(name);
					if(spr.size() == 0) throw new FdsError_Exception("Unknown Passenger", ferr);
					for(PassengerReader pr : spr)
					{
						if(pr.getName().equals(name)){
							if(pr.getSeat() != null){
							((Passenger)pr).setBoarded();
							return;
							}else throw new FdsError_Exception("PassengerStatus is not checkin", ferr);
						}
					}

				
			}

			else throw new FdsError_Exception("Unknown Flightinstance", ferr);
		} catch (MalformedArgumentException e) {
			throw new FdsError_Exception(e.getMessage(), ferr);
		}
		finally {if(r != null) r.unlock();}	

	}

	@Override
	public List<PassengerType> getBoardedPassengers(String number,
			XMLGregorianCalendar date,int pageNumber,int pageSize) throws FdsError_Exception {

		FdsError ferr2 = new FdsError();
		ferr2.setErrorcode(2);
		
		if(pageNumber < 0 || pageSize <0)
			throw new FdsError_Exception("pageNumber or pageSize cannot be negative", ferr2);
		
		FdsError ferr = new FdsError();
		ferr.setErrorcode(1);
		
		LinkedList<PassengerType> lp = new LinkedList<PassengerType>();
		LinkedList<PassengerReader> tmp = new LinkedList<PassengerReader>();
		FlightInstanceReader fir;
		Lock r = null;
		try {
			fir = fm.getFlightInstance(number, date.toGregorianCalendar());
			if(fir != null){
				r = firmap.get(fir).readLock();
				r.lock();
					Set<PassengerReader> spr = fir.getPassengerReaders(null);
					
					for(PassengerReader pr: spr){
						if(pr.boarded())
							tmp.add(pr);
					}					
					
					Collections.sort(tmp, new Comparator<PassengerReader>(){
						  @Override
						  public int compare(PassengerReader o1, PassengerReader o2){
						       return o1.getName().compareTo(o2.getName());	
						  }
						        
						}); 
					
					
					for(int i=(pageSize*(pageNumber)); i<(pageSize*(pageNumber+1)); i++){
						if(i >= tmp.size()) break;
						PassengerReader pr = tmp.get(i);
						if(pr.boarded()){
							PassengerType pt = new PassengerType();
							pt.setBoarded(pr.boarded());
							pt.setName(pr.getName());
							pt.setSeat(pr.getSeat());
							lp.add(pt);
						}
						
					}
					
		/*			for(PassengerReader pr : spr){
						if(pr.boarded()){
							PassengerType pt = new PassengerType();
							pt.setBoarded(pr.boarded());
							pt.setName(pr.getName());
							pt.setSeat(pr.getSeat());
							lp.add(pt);
						}


					} */
					return lp;
				

			}
			else throw new FdsError_Exception("Unknown Flightinstance", ferr);
		} catch (MalformedArgumentException e) {
			throw new FdsError_Exception(e.getMessage(), ferr);
		}
		finally {if(r != null) r.unlock();}

	}

}

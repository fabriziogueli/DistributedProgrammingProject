package it.polito.dp2.FDS.sol4.server;

import it.polito.dp2.FDS.FlightInstanceReader;
import it.polito.dp2.FDS.FlightInstanceStatus;
import it.polito.dp2.FDS.FlightMonitor;
import it.polito.dp2.FDS.FlightReader;
import it.polito.dp2.FDS.MalformedArgumentException;
import it.polito.dp2.FDS.PassengerReader;
import it.polito.dp2.FDS.Time;
import it.polito.dp2.FDS.lab4.gen.AircraftType;
import it.polito.dp2.FDS.lab4.gen.FDSInfo;
import it.polito.dp2.FDS.lab4.gen.FdsInfoError;
import it.polito.dp2.FDS.lab4.gen.FdsInfoError_Exception;
import it.polito.dp2.FDS.lab4.gen.FlightInstanceType;
import it.polito.dp2.FDS.lab4.gen.FlightType;
import it.polito.dp2.FDS.lab4.gen.PassengerType;
import it.polito.dp2.FDS.lab4.gen.StatusType;

import java.math.BigInteger;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.jws.WebService;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

@WebService(name="FDSInfoService",
	targetNamespace="http://www.example.org/FDSInfo/",
	endpointInterface="it.polito.dp2.FDS.lab4.gen.FDSInfo",
	serviceName="FDSInfoService", 
	portName="FDSInfoSOAP")

public class FDSInfoImpl implements FDSInfo {
	
	private FlightMonitor fm;
	private HashMap<FlightInstanceReader,ReentrantReadWriteLock> firmap;
	public FDSInfoImpl(FlightMonitor fm,HashMap<FlightInstanceReader,ReentrantReadWriteLock> firmap) {
		this.fm = fm;
		this.firmap = firmap;
	}

	@Override
	public List<AircraftType> getAircrafts(int pageNumber,int pageSize)
			throws FdsInfoError_Exception {
		return null;
	}

	@Override
	public List<FlightType> getFlights(String dep, String arr,
			XMLGregorianCalendar startTime,int pageNumber,int pageSize) throws FdsInfoError_Exception {
		Time t = null;
		
		FdsInfoError ferr2 = new FdsInfoError();
		ferr2.setErrorcode(2);
		
		if(pageNumber < 0 || pageSize <0)
			throw new FdsInfoError_Exception("pageNumber or pageSize cannot be negative", ferr2);
		
		if(startTime != null)
		t = new Time(startTime.getHour(), startTime.getMinute());
		
		LinkedList<FlightType> lft = new LinkedList<FlightType>();
		FdsInfoError ferr = new FdsInfoError();
		try {

			LinkedList<FlightReader> tmp = new LinkedList<FlightReader>();
			tmp.addAll(fm.getFlights(dep, arr, t));
			
			if(tmp.size() != 0){

				Collections.sort(tmp, new Comparator<FlightReader>(){

					@Override
					public int compare(FlightReader arg0, FlightReader arg1) {
						return arg0.getNumber().compareTo(arg1.getNumber());				       	
					       
					}					
				});
			}
									
			for(int i=(pageSize*(pageNumber)); i<(pageSize*(pageNumber+1)); i++)
			{
				if(i >= tmp.size()) break;
				FlightReader r = tmp.get(i);
				FlightType ft = new FlightType();
				XMLGregorianCalendar xgc = DatatypeFactory.newInstance().newXMLGregorianCalendarTime(r.getDepartureTime().getHour(), r.getDepartureTime().getMinute(), 0, 0);
				ft.setDepartureAirport(r.getDepartureAirport());
				ft.setDepartureTime(xgc);
				ft.setDestinationAirport(r.getDestinationAirport());
				ft.setNumber(r.getNumber());
				lft.add(ft);
				
			}
			return lft;
			
		} catch (MalformedArgumentException e) {
			ferr.setErrorcode(1);
			throw new FdsInfoError_Exception(e.getMessage(), ferr);
		} catch (DatatypeConfigurationException e) {
			ferr.setErrorcode(2);
			throw new FdsInfoError_Exception(e.getMessage(), ferr);
		}
		
	}

	@Override
	public FlightType getFlight(String number) throws FdsInfoError_Exception {
		FdsInfoError ferr = new FdsInfoError();
		try {
			FlightReader fr = fm.getFlight(number);
			if(fr != null){
				FlightType ft = new FlightType();
				XMLGregorianCalendar xgc = DatatypeFactory.newInstance().newXMLGregorianCalendarTime(fr.getDepartureTime().getHour(), fr.getDepartureTime().getMinute(), 0, 0);
				ft.setDepartureAirport(fr.getDepartureAirport());
				ft.setDepartureTime(xgc);
				ft.setDestinationAirport(fr.getDestinationAirport());
				ft.setNumber(fr.getNumber());
				return ft;
			}
			else return null;
		} catch (MalformedArgumentException e) {
			ferr.setErrorcode(1);
			throw new FdsInfoError_Exception(e.getMessage(), ferr);
		} catch (DatatypeConfigurationException e) {
			ferr.setErrorcode(2);
			throw new FdsInfoError_Exception(e.getMessage(), ferr);
		}
	}

	@Override
	public FlightInstanceType getFlightInstance(String number,
			XMLGregorianCalendar date) throws FdsInfoError_Exception {
		FdsInfoError ferr = new FdsInfoError();
		
		FlightInstanceType fit = new FlightInstanceType();
		GregorianCalendar gc = date.toGregorianCalendar();
		Lock r = null;
		try {			
			FlightInstanceReader fir= fm.getFlightInstance(number, gc);
			XMLGregorianCalendar xgc = DatatypeFactory.newInstance().newXMLGregorianCalendar();
			if(fir != null){
			r = firmap.get(fir).readLock();	
			
			r.lock();
			fit.setDate(date);
			fit.setDepartureGate(fir.getDepartureGate());
			fit.setDelay(BigInteger.valueOf(fir.getDelay()));
			fit.setStatus(StatusType.valueOf(fir.getStatus().toString()));
			fit.setAircraft(fir.getAircraft().model);
			xgc.setHour(fir.getFlight().getDepartureTime().getHour());
			xgc.setMinute(fir.getFlight().getDepartureTime().getMinute());
			fit.setFlight(fir.getFlight().getNumber());

			return fit;
			
		}else return null;
		
	} catch (MalformedArgumentException e) {
		ferr.setErrorcode(1);
			throw new FdsInfoError_Exception(e.getMessage(), ferr);
		} catch (DatatypeConfigurationException e) {
		ferr.setErrorcode(2);
		throw new FdsInfoError_Exception(e.getMessage(), ferr);
	}
		finally {if(r != null) r.unlock();}
	}

	@Override
	public List<FlightInstanceType> getFlightInstances(String number,
			XMLGregorianCalendar startDate, StatusType stat,int pageNumber,int pageSize)
			throws FdsInfoError_Exception {
		
		FdsInfoError ferr2 = new FdsInfoError();
		ferr2.setErrorcode(2);
		
		if(pageNumber < 0 || pageSize <0)
			throw new FdsInfoError_Exception("pageNumber or pageSize cannot be negative", ferr2);
		
		FdsInfoError ferr = new FdsInfoError();
		LinkedList<FlightInstanceType> lfit = new LinkedList<FlightInstanceType>();
		List<FlightInstanceReader> lfir;
		GregorianCalendar gca = null;
		FlightInstanceStatus status = null;
		Lock r = null;
		try {
			if(startDate != null)
			gca = startDate.toGregorianCalendar();
			
			if(stat != null)
				status = FlightInstanceStatus.valueOf(stat.toString());
			
			lfir = fm.getFlightInstances(number, gca, status);
			
			if(lfir.size() != 0){
				Collections.sort(lfir, new Comparator<FlightInstanceReader>(){

					@Override
					public int compare(FlightInstanceReader arg0,
							FlightInstanceReader arg1) {
						return arg0.getDate().compareTo(arg1.getDate());				       	
					    
					}
					 
			});
		}
			XMLGregorianCalendar xgc = DatatypeFactory.newInstance().newXMLGregorianCalendar();
			
			for(int i=(pageSize*(pageNumber)); i<(pageSize*(pageNumber+1)); i++)
			{
				if(i >= lfir.size()) break;
				FlightInstanceReader fir = lfir.get(i);
				FlightInstanceType fit = new FlightInstanceType();
				r = firmap.get(fir).readLock();
				
				try{
					r.lock();
					XMLGregorianCalendar startxgc = DatatypeFactory.newInstance().newXMLGregorianCalendar(fir.getDate());
					fit.setDate(startxgc);
					fit.setDepartureGate(fir.getDepartureGate());
					fit.setDelay(BigInteger.valueOf(fir.getDelay()));
					fit.setStatus(StatusType.valueOf(fir.getStatus().toString()));
					fit.setAircraft(fir.getAircraft().model);					
					xgc.setHour(fir.getFlight().getDepartureTime().getHour());
					xgc.setMinute(fir.getFlight().getDepartureTime().getMinute());
					fit.setFlight(fir.getFlight().getNumber());				
				
				lfit.add(fit);
				}finally {if(r != null) r.unlock();}
				
			}
			return lfit;
		} catch (MalformedArgumentException e) {
			ferr.setErrorcode(1);
			throw new FdsInfoError_Exception(e.getMessage(), ferr);
		} catch (DatatypeConfigurationException e) {
			ferr.setErrorcode(2);
			throw new FdsInfoError_Exception(e.getMessage(), ferr);
		}
		
	}

	@Override
	public List<PassengerType> getPassengers(String number,
			XMLGregorianCalendar date, int pageNumber, int pageSize)
					throws FdsInfoError_Exception {
		
		
		FdsInfoError ferr2 = new FdsInfoError();
		ferr2.setErrorcode(2);
		
		if(pageNumber < 0 || pageSize <0)
			throw new FdsInfoError_Exception("pageNumber or pageSize cannot be negative", ferr2);
	
		FdsInfoError ferr = new FdsInfoError();
		
		LinkedList<PassengerType> ptlist = new LinkedList<PassengerType>();
		
		LinkedList<PassengerReader> tmp = new LinkedList<PassengerReader>();
		GregorianCalendar gc = date.toGregorianCalendar();
		Lock r = null;
		try {			
			FlightInstanceReader fir= fm.getFlightInstance(number, gc);
			if(fir != null){
			r = firmap.get(fir).readLock();	
			
			r.lock();
			tmp.addAll(fir.getPassengerReaders(null));
			
			Collections.sort(tmp, new Comparator<PassengerReader>(){
				  @Override
				  public int compare(PassengerReader o1, PassengerReader o2){
				       return o1.getName().compareTo(o2.getName());	
				  }
				        
				}); 

			for(int i=(pageSize*(pageNumber)); i<(pageSize*(pageNumber+1)); i++){
				if(i >= tmp.size()) break;
				PassengerReader pr = tmp.get(i);
				PassengerType pt = new PassengerType();
				pt.setBoarded(pr.boarded());
				pt.setName(pr.getName());
				pt.setSeat(pr.getSeat());
				ptlist.add(pt);
				
			}
			return ptlist;
			
		}else {
			ferr.setErrorcode(1);
			throw new FdsInfoError_Exception("Unknown FlightInstance", ferr);
		}
		
	} catch (MalformedArgumentException e) {
		ferr.setErrorcode(1);
			throw new FdsInfoError_Exception(e.getMessage(), ferr);
		}
		finally {if(r != null) r.unlock();}
		
		
		
	}


}

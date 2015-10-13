package it.polito.dp2.FDS.sol4.server;

import it.polito.dp2.FDS.FlightInstanceReader;
import it.polito.dp2.FDS.FlightMonitor;
import it.polito.dp2.FDS.FlightMonitorException;
import it.polito.dp2.FDS.FlightMonitorFactory;
import it.polito.dp2.FDS.MalformedArgumentException;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.xml.ws.Endpoint;

public class FDSControlServer {

	
	public static void main(String[] args){
		FlightMonitorFactory fmf = FlightMonitorFactory.newInstance();
		
		HashMap<FlightInstanceReader,ReentrantReadWriteLock> firmap = new HashMap<FlightInstanceReader, ReentrantReadWriteLock>();
		FlightMonitor fm;
		try {
			fm = fmf.newFlightMonitor();
			
			for(FlightInstanceReader fir: fm.getFlightInstances(null, null, null))
			{
				ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
				firmap.put(fir, rwl);
			}
			
			Endpoint endpoint1;
			Endpoint endpoint2;
			endpoint1 = Endpoint.create(new FDSBoardImpl(fm,firmap));
			endpoint1.setExecutor(Executors.newFixedThreadPool(10));
			endpoint1.publish("http://localhost:7070/fdscontrol");
			
			endpoint2 = Endpoint.create(new FDSInfoImpl(fm,firmap));
			endpoint2.setExecutor(Executors.newFixedThreadPool(10));
			endpoint2.publish("http://localhost:7071/fdsinfo");
		} catch (FlightMonitorException e) {
			e.printStackTrace();
		} catch (MalformedArgumentException e) {
			e.printStackTrace();
		}
		
        
	}

}

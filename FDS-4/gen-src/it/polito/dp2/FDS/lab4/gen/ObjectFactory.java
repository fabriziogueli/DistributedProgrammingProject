
package it.polito.dp2.FDS.lab4.gen;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.polito.dp2.FDS.lab4.gen package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _FlightMonitor_QNAME = new QName("http://www.example.org/FDSInfo", "FlightMonitor");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.polito.dp2.FDS.lab4.gen
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetAircrafts }
     * 
     */
    public GetAircrafts createGetAircrafts() {
        return new GetAircrafts();
    }

    /**
     * Create an instance of {@link FdsInfoError }
     * 
     */
    public FdsInfoError createFdsInfoError() {
        return new FdsInfoError();
    }

    /**
     * Create an instance of {@link GetPassengers }
     * 
     */
    public GetPassengers createGetPassengers() {
        return new GetPassengers();
    }

    /**
     * Create an instance of {@link GetFlightResponse }
     * 
     */
    public GetFlightResponse createGetFlightResponse() {
        return new GetFlightResponse();
    }

    /**
     * Create an instance of {@link FlightType }
     * 
     */
    public FlightType createFlightType() {
        return new FlightType();
    }

    /**
     * Create an instance of {@link GetFlightsResponse }
     * 
     */
    public GetFlightsResponse createGetFlightsResponse() {
        return new GetFlightsResponse();
    }

    /**
     * Create an instance of {@link GetPassengersResponse }
     * 
     */
    public GetPassengersResponse createGetPassengersResponse() {
        return new GetPassengersResponse();
    }

    /**
     * Create an instance of {@link PassengerType }
     * 
     */
    public PassengerType createPassengerType() {
        return new PassengerType();
    }

    /**
     * Create an instance of {@link GetFlightInstances }
     * 
     */
    public GetFlightInstances createGetFlightInstances() {
        return new GetFlightInstances();
    }

    /**
     * Create an instance of {@link GetFlightInstance }
     * 
     */
    public GetFlightInstance createGetFlightInstance() {
        return new GetFlightInstance();
    }

    /**
     * Create an instance of {@link GetFlights }
     * 
     */
    public GetFlights createGetFlights() {
        return new GetFlights();
    }

    /**
     * Create an instance of {@link GetFlight }
     * 
     */
    public GetFlight createGetFlight() {
        return new GetFlight();
    }

    /**
     * Create an instance of {@link GetAircraftsResponse }
     * 
     */
    public GetAircraftsResponse createGetAircraftsResponse() {
        return new GetAircraftsResponse();
    }

    /**
     * Create an instance of {@link AircraftType }
     * 
     */
    public AircraftType createAircraftType() {
        return new AircraftType();
    }

    /**
     * Create an instance of {@link GetFlightInstancesResponse }
     * 
     */
    public GetFlightInstancesResponse createGetFlightInstancesResponse() {
        return new GetFlightInstancesResponse();
    }

    /**
     * Create an instance of {@link FlightInstanceType }
     * 
     */
    public FlightInstanceType createFlightInstanceType() {
        return new FlightInstanceType();
    }

    /**
     * Create an instance of {@link GetFlightInstanceResponse }
     * 
     */
    public GetFlightInstanceResponse createGetFlightInstanceResponse() {
        return new GetFlightInstanceResponse();
    }

    /**
     * Create an instance of {@link FlightMonitorType }
     * 
     */
    public FlightMonitorType createFlightMonitorType() {
        return new FlightMonitorType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FlightMonitorType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.example.org/FDSInfo", name = "FlightMonitor")
    public JAXBElement<FlightMonitorType> createFlightMonitor(FlightMonitorType value) {
        return new JAXBElement<FlightMonitorType>(_FlightMonitor_QNAME, FlightMonitorType.class, null, value);
    }

}

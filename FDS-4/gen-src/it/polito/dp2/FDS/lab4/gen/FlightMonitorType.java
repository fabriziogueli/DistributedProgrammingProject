
package it.polito.dp2.FDS.lab4.gen;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FlightMonitorType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FlightMonitorType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Aircraft" type="{http://www.example.org/FDSInfo}AircraftType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Flight" type="{http://www.example.org/FDSInfo}FlightType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="FlightInstance" type="{http://www.example.org/FDSInfo}FlightInstanceType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FlightMonitorType", namespace = "http://www.example.org/FDSInfo", propOrder = {
    "aircraft",
    "flight",
    "flightInstance"
})
public class FlightMonitorType {

    @XmlElement(name = "Aircraft")
    protected List<AircraftType> aircraft;
    @XmlElement(name = "Flight")
    protected List<FlightType> flight;
    @XmlElement(name = "FlightInstance")
    protected List<FlightInstanceType> flightInstance;

    /**
     * Gets the value of the aircraft property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the aircraft property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAircraft().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AircraftType }
     * 
     * 
     */
    public List<AircraftType> getAircraft() {
        if (aircraft == null) {
            aircraft = new ArrayList<AircraftType>();
        }
        return this.aircraft;
    }

    /**
     * Gets the value of the flight property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the flight property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFlight().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FlightType }
     * 
     * 
     */
    public List<FlightType> getFlight() {
        if (flight == null) {
            flight = new ArrayList<FlightType>();
        }
        return this.flight;
    }

    /**
     * Gets the value of the flightInstance property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the flightInstance property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFlightInstance().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FlightInstanceType }
     * 
     * 
     */
    public List<FlightInstanceType> getFlightInstance() {
        if (flightInstance == null) {
            flightInstance = new ArrayList<FlightInstanceType>();
        }
        return this.flightInstance;
    }

}

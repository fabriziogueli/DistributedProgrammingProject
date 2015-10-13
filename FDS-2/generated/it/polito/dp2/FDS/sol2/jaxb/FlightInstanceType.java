//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.11.13 at 06:44:06 PM CET 
//


package it.polito.dp2.FDS.sol2.jaxb;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for FlightInstanceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FlightInstanceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Passenger" type="{http://www.example.org/FDSInfo}PassengerType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="DepartureGate" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Aircraft" use="required" type="{http://www.w3.org/2001/XMLSchema}IDREF" />
 *       &lt;attribute name="Flight" use="required" type="{http://www.w3.org/2001/XMLSchema}IDREF" />
 *       &lt;attribute name="Delay" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="Date" use="required" type="{http://www.w3.org/2001/XMLSchema}date" />
 *       &lt;attribute name="Status" use="required" type="{http://www.example.org/FDSInfo}StatusType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FlightInstanceType", propOrder = {
    "passenger"
})
public class FlightInstanceType {

    @XmlElement(name = "Passenger")
    protected List<PassengerType> passenger;
    @XmlAttribute(name = "DepartureGate", required = true)
    protected String departureGate;
    @XmlAttribute(name = "Aircraft", required = true)
    @XmlIDREF
    @XmlSchemaType(name = "IDREF")
    protected Object aircraft;
    @XmlAttribute(name = "Flight", required = true)
    @XmlIDREF
    @XmlSchemaType(name = "IDREF")
    protected Object flight;
    @XmlAttribute(name = "Delay")
    protected BigInteger delay;
    @XmlAttribute(name = "Date", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar date;
    @XmlAttribute(name = "Status", required = true)
    protected StatusType status;

    /**
     * Gets the value of the passenger property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the passenger property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPassenger().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PassengerType }
     * 
     * 
     */
    public List<PassengerType> getPassenger() {
        if (passenger == null) {
            passenger = new ArrayList<PassengerType>();
        }
        return this.passenger;
    }

    /**
     * Gets the value of the departureGate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepartureGate() {
        return departureGate;
    }

    /**
     * Sets the value of the departureGate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepartureGate(String value) {
        this.departureGate = value;
    }

    /**
     * Gets the value of the aircraft property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getAircraft() {
        return aircraft;
    }

    /**
     * Sets the value of the aircraft property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setAircraft(Object value) {
        this.aircraft = value;
    }

    /**
     * Gets the value of the flight property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getFlight() {
        return flight;
    }

    /**
     * Sets the value of the flight property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setFlight(Object value) {
        this.flight = value;
    }

    /**
     * Gets the value of the delay property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getDelay() {
        return delay;
    }

    /**
     * Sets the value of the delay property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDelay(BigInteger value) {
        this.delay = value;
    }

    /**
     * Gets the value of the date property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDate(XMLGregorianCalendar value) {
        this.date = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link StatusType }
     *     
     */
    public StatusType getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatusType }
     *     
     */
    public void setStatus(StatusType value) {
        this.status = value;
    }

}

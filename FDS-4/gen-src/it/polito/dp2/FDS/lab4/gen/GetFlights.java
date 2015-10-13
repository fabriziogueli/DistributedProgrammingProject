
package it.polito.dp2.FDS.lab4.gen;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dep" type="{http://www.example.org/FDSInfo}AirportType"/>
 *         &lt;element name="arr" type="{http://www.example.org/FDSInfo}AirportType"/>
 *         &lt;element name="startTime" type="{http://www.w3.org/2001/XMLSchema}time"/>
 *         &lt;element name="pageNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="pageSize" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "dep",
    "arr",
    "startTime",
    "pageNumber",
    "pageSize"
})
@XmlRootElement(name = "getFlights")
public class GetFlights {

    @XmlElement(required = true)
    protected String dep;
    @XmlElement(required = true)
    protected String arr;
    @XmlElement(required = true)
    @XmlSchemaType(name = "time")
    protected XMLGregorianCalendar startTime;
    protected int pageNumber;
    protected int pageSize;

    /**
     * Gets the value of the dep property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDep() {
        return dep;
    }

    /**
     * Sets the value of the dep property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDep(String value) {
        this.dep = value;
    }

    /**
     * Gets the value of the arr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArr() {
        return arr;
    }

    /**
     * Sets the value of the arr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArr(String value) {
        this.arr = value;
    }

    /**
     * Gets the value of the startTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartTime() {
        return startTime;
    }

    /**
     * Sets the value of the startTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartTime(XMLGregorianCalendar value) {
        this.startTime = value;
    }

    /**
     * Gets the value of the pageNumber property.
     * 
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * Sets the value of the pageNumber property.
     * 
     */
    public void setPageNumber(int value) {
        this.pageNumber = value;
    }

    /**
     * Gets the value of the pageSize property.
     * 
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Sets the value of the pageSize property.
     * 
     */
    public void setPageSize(int value) {
        this.pageSize = value;
    }

}

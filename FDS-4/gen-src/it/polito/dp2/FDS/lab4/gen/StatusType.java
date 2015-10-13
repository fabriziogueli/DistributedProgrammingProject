
package it.polito.dp2.FDS.lab4.gen;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StatusType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="StatusType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ARRIVED"/>
 *     &lt;enumeration value="BOARDING"/>
 *     &lt;enumeration value="BOOKING"/>
 *     &lt;enumeration value="CANCELLED"/>
 *     &lt;enumeration value="CHECKINGIN"/>
 *     &lt;enumeration value="DEPARTED"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "StatusType", namespace = "http://www.example.org/FDSInfo")
@XmlEnum
public enum StatusType {

    ARRIVED,
    BOARDING,
    BOOKING,
    CANCELLED,
    CHECKINGIN,
    DEPARTED;

    public String value() {
        return name();
    }

    public static StatusType fromValue(String v) {
        return valueOf(v);
    }

}

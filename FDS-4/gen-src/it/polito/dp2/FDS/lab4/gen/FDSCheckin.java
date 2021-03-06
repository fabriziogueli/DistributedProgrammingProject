
package it.polito.dp2.FDS.lab4.gen;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Holder;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * Interface used to perform check-in operation
 * 
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "FDSCheckin", targetNamespace = "http://www.example.org/FDSControl/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface FDSCheckin {


    /**
     * 
     * @param status
     * @param seat
     * @param name
     * @param number
     * @param delay
     * @param date
     * @param gate
     * @throws FdsError_Exception
     */
    @WebMethod(operationName = "CheckIn", action = "http://www.example.org/FDSControl/CheckIn")
    @RequestWrapper(localName = "CheckIn", targetNamespace = "http://www.example.org/FDSControl/", className = "it.polito.dp2.FDS.lab4.gen.CheckIn")
    @ResponseWrapper(localName = "CheckInResponse", targetNamespace = "http://www.example.org/FDSControl/", className = "it.polito.dp2.FDS.lab4.gen.CheckInResponse")
    public void checkIn(
        @WebParam(name = "name", targetNamespace = "")
        String name,
        @WebParam(name = "number", targetNamespace = "")
        String number,
        @WebParam(name = "date", targetNamespace = "")
        XMLGregorianCalendar date,
        @WebParam(name = "seat", targetNamespace = "", mode = WebParam.Mode.OUT)
        Holder<String> seat,
        @WebParam(name = "gate", targetNamespace = "", mode = WebParam.Mode.OUT)
        Holder<String> gate,
        @WebParam(name = "delay", targetNamespace = "", mode = WebParam.Mode.OUT)
        Holder<Integer> delay,
        @WebParam(name = "status", targetNamespace = "", mode = WebParam.Mode.OUT)
        Holder<StatusType> status)
        throws FdsError_Exception
    ;

}

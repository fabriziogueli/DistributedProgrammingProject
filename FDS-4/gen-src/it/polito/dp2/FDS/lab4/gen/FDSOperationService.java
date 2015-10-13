
package it.polito.dp2.FDS.lab4.gen;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "FDSOperationService", targetNamespace = "http://www.example.org/FDSControl/", wsdlLocation = "file:/home/fabrizio/workspace/FDS-4/wsdl/FDSControl.wsdl")
public class FDSOperationService
    extends Service
{

    private final static URL FDSOPERATIONSERVICE_WSDL_LOCATION;
    private final static WebServiceException FDSOPERATIONSERVICE_EXCEPTION;
    private final static QName FDSOPERATIONSERVICE_QNAME = new QName("http://www.example.org/FDSControl/", "FDSOperationService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("file:/home/fabrizio/workspace/FDS-4/wsdl/FDSControl.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        FDSOPERATIONSERVICE_WSDL_LOCATION = url;
        FDSOPERATIONSERVICE_EXCEPTION = e;
    }

    public FDSOperationService() {
        super(__getWsdlLocation(), FDSOPERATIONSERVICE_QNAME);
    }

    public FDSOperationService(WebServiceFeature... features) {
        super(__getWsdlLocation(), FDSOPERATIONSERVICE_QNAME, features);
    }

    public FDSOperationService(URL wsdlLocation) {
        super(wsdlLocation, FDSOPERATIONSERVICE_QNAME);
    }

    public FDSOperationService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, FDSOPERATIONSERVICE_QNAME, features);
    }

    public FDSOperationService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public FDSOperationService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns FDSOperation
     */
    @WebEndpoint(name = "FDSOperationSOAP")
    public FDSOperation getFDSOperationSOAP() {
        return super.getPort(new QName("http://www.example.org/FDSControl/", "FDSOperationSOAP"), FDSOperation.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns FDSOperation
     */
    @WebEndpoint(name = "FDSOperationSOAP")
    public FDSOperation getFDSOperationSOAP(WebServiceFeature... features) {
        return super.getPort(new QName("http://www.example.org/FDSControl/", "FDSOperationSOAP"), FDSOperation.class, features);
    }

    private static URL __getWsdlLocation() {
        if (FDSOPERATIONSERVICE_EXCEPTION!= null) {
            throw FDSOPERATIONSERVICE_EXCEPTION;
        }
        return FDSOPERATIONSERVICE_WSDL_LOCATION;
    }

}

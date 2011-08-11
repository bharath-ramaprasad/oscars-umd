
/*
 * 
 */

package net.es.oscars.api.soap.gen.v06;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.2.5
 * Mon Jul 18 10:17:13 EDT 2011
 * Generated source version: 2.2.5
 * 
 */


@WebServiceClient(name = "OSCARSInternalService", 
                  wsdlLocation = "file:/home/oscars/OSCARS_DIST/common-soap/src/main/resources/api/api-internal-0.6.wsdl",
                  targetNamespace = "http://oscars.es.net/OSCARS/06") 
public class OSCARSInternalService extends Service {

    public final static URL WSDL_LOCATION;
    public final static QName SERVICE = new QName("http://oscars.es.net/OSCARS/06", "OSCARSInternalService");
    public final static QName OSCARSInternalPortType = new QName("http://oscars.es.net/OSCARS/06", "OSCARSInternalPortType");
    static {
        URL url = null;
        try {
            url = new URL("file:/home/oscars/OSCARS_DIST/common-soap/src/main/resources/api/api-internal-0.6.wsdl");
        } catch (MalformedURLException e) {
            System.err.println("Can not initialize the default wsdl from file:/home/oscars/OSCARS_DIST/common-soap/src/main/resources/api/api-internal-0.6.wsdl");
            // e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

    public OSCARSInternalService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public OSCARSInternalService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public OSCARSInternalService() {
        super(WSDL_LOCATION, SERVICE);
    }

    /**
     * 
     * @return
     *     returns OSCARSInternalPortType
     */
    @WebEndpoint(name = "OSCARSInternalPortType")
    public OSCARSInternalPortType getOSCARSInternalPortType() {
        return super.getPort(OSCARSInternalPortType, OSCARSInternalPortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns OSCARSInternalPortType
     */
    @WebEndpoint(name = "OSCARSInternalPortType")
    public OSCARSInternalPortType getOSCARSInternalPortType(WebServiceFeature... features) {
        return super.getPort(OSCARSInternalPortType, OSCARSInternalPortType.class, features);
    }

}

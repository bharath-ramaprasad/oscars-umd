
/*
 * 
 */

package net.es.oscars.lookup.soap.gen;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.2.5
 * Mon Jul 18 10:16:53 EDT 2011
 * Generated source version: 2.2.5
 * 
 */


@WebServiceClient(name = "LookupService", 
                  wsdlLocation = "file:/home/oscars/OSCARS_DIST/common-soap/src/main/resources/lookup/lookup.wsdl",
                  targetNamespace = "http://oscars.es.net/OSCARS/lookup") 
public class LookupService extends Service {

    public final static URL WSDL_LOCATION;
    public final static QName SERVICE = new QName("http://oscars.es.net/OSCARS/lookup", "LookupService");
    public final static QName LookupPort = new QName("http://oscars.es.net/OSCARS/lookup", "LookupPort");
    static {
        URL url = null;
        try {
            url = new URL("file:/home/oscars/OSCARS_DIST/common-soap/src/main/resources/lookup/lookup.wsdl");
        } catch (MalformedURLException e) {
            System.err.println("Can not initialize the default wsdl from file:/home/oscars/OSCARS_DIST/common-soap/src/main/resources/lookup/lookup.wsdl");
            // e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

    public LookupService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public LookupService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public LookupService() {
        super(WSDL_LOCATION, SERVICE);
    }

    /**
     * 
     * @return
     *     returns LookupPortType
     */
    @WebEndpoint(name = "LookupPort")
    public LookupPortType getLookupPort() {
        return super.getPort(LookupPort, LookupPortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns LookupPortType
     */
    @WebEndpoint(name = "LookupPort")
    public LookupPortType getLookupPort(WebServiceFeature... features) {
        return super.getPort(LookupPort, LookupPortType.class, features);
    }

}

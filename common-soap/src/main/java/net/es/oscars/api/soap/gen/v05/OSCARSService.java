
/*
 * 
 */

package net.es.oscars.api.soap.gen.v05;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.2.5
 * Mon Jul 18 10:17:04 EDT 2011
 * Generated source version: 2.2.5
 * 
 */


@WebServiceClient(name = "OSCARSService", 
                  wsdlLocation = "file:/home/oscars/OSCARS_DIST/common-soap/src/main/resources/api/OSCARS-0.5.wsdl",
                  targetNamespace = "http://oscars.es.net/OSCARS") 
public class OSCARSService extends Service {

    public final static URL WSDL_LOCATION;
    public final static QName SERVICE = new QName("http://oscars.es.net/OSCARS", "OSCARSService");
    public final static QName OSCARS = new QName("http://oscars.es.net/OSCARS", "OSCARS");
    static {
        URL url = null;
        try {
            url = new URL("file:/home/oscars/OSCARS_DIST/common-soap/src/main/resources/api/OSCARS-0.5.wsdl");
        } catch (MalformedURLException e) {
            System.err.println("Can not initialize the default wsdl from file:/home/oscars/OSCARS_DIST/common-soap/src/main/resources/api/OSCARS-0.5.wsdl");
            // e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

    public OSCARSService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public OSCARSService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public OSCARSService() {
        super(WSDL_LOCATION, SERVICE);
    }

    /**
     * 
     * @return
     *     returns OSCARS
     */
    @WebEndpoint(name = "OSCARS")
    public OSCARS getOSCARS() {
        return super.getPort(OSCARS, OSCARS.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns OSCARS
     */
    @WebEndpoint(name = "OSCARS")
    public OSCARS getOSCARS(WebServiceFeature... features) {
        return super.getPort(OSCARS, OSCARS.class, features);
    }

}
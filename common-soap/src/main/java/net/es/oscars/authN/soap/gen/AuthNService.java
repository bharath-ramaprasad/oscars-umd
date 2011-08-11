
/*
 * 
 */

package net.es.oscars.authN.soap.gen;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.2.5
 * Mon Jul 18 10:16:27 EDT 2011
 * Generated source version: 2.2.5
 * 
 */


@WebServiceClient(name = "AuthNService", 
                  wsdlLocation = "file:/home/oscars/OSCARS_DIST/common-soap/src/main/resources/authN/authN.wsdl",
                  targetNamespace = "http://oscars.es.net/OSCARS/authN") 
public class AuthNService extends Service {

    public final static URL WSDL_LOCATION;
    public final static QName SERVICE = new QName("http://oscars.es.net/OSCARS/authN", "AuthNService");
    public final static QName AuthNPort = new QName("http://oscars.es.net/OSCARS/authN", "AuthNPort");
    static {
        URL url = null;
        try {
            url = new URL("file:/home/oscars/OSCARS_DIST/common-soap/src/main/resources/authN/authN.wsdl");
        } catch (MalformedURLException e) {
            System.err.println("Can not initialize the default wsdl from file:/home/oscars/OSCARS_DIST/common-soap/src/main/resources/authN/authN.wsdl");
            // e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

    public AuthNService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public AuthNService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public AuthNService() {
        super(WSDL_LOCATION, SERVICE);
    }

    /**
     * 
     * @return
     *     returns AuthNPortType
     */
    @WebEndpoint(name = "AuthNPort")
    public AuthNPortType getAuthNPort() {
        return super.getPort(AuthNPort, AuthNPortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns AuthNPortType
     */
    @WebEndpoint(name = "AuthNPort")
    public AuthNPortType getAuthNPort(WebServiceFeature... features) {
        return super.getPort(AuthNPort, AuthNPortType.class, features);
    }

}
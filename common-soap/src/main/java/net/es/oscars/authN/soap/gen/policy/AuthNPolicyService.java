
/*
 * 
 */

package net.es.oscars.authN.soap.gen.policy;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.2.5
 * Mon Jul 18 10:16:32 EDT 2011
 * Generated source version: 2.2.5
 * 
 */


@WebServiceClient(name = "AuthNPolicyService", 
                  wsdlLocation = "file:/home/oscars/OSCARS_DIST/common-soap/src/main/resources/authNPolicy/authNPolicy.wsdl",
                  targetNamespace = "http://oscars.es.net/OSCARS/authNPolicy") 
public class AuthNPolicyService extends Service {

    public final static URL WSDL_LOCATION;
    public final static QName SERVICE = new QName("http://oscars.es.net/OSCARS/authNPolicy", "AuthNPolicyService");
    public final static QName AuthNPolicyPort = new QName("http://oscars.es.net/OSCARS/authNPolicy", "AuthNPolicyPort");
    static {
        URL url = null;
        try {
            url = new URL("file:/home/oscars/OSCARS_DIST/common-soap/src/main/resources/authNPolicy/authNPolicy.wsdl");
        } catch (MalformedURLException e) {
            System.err.println("Can not initialize the default wsdl from file:/home/oscars/OSCARS_DIST/common-soap/src/main/resources/authNPolicy/authNPolicy.wsdl");
            // e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

    public AuthNPolicyService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public AuthNPolicyService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public AuthNPolicyService() {
        super(WSDL_LOCATION, SERVICE);
    }

    /**
     * 
     * @return
     *     returns AuthNPolicyPortType
     */
    @WebEndpoint(name = "AuthNPolicyPort")
    public AuthNPolicyPortType getAuthNPolicyPort() {
        return super.getPort(AuthNPolicyPort, AuthNPolicyPortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns AuthNPolicyPortType
     */
    @WebEndpoint(name = "AuthNPolicyPort")
    public AuthNPolicyPortType getAuthNPolicyPort(WebServiceFeature... features) {
        return super.getPort(AuthNPolicyPort, AuthNPolicyPortType.class, features);
    }

}
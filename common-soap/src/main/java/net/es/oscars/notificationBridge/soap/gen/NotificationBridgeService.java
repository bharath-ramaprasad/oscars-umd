
/*
 * 
 */

package net.es.oscars.notificationBridge.soap.gen;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.2.5
 * Mon Jul 18 10:16:56 EDT 2011
 * Generated source version: 2.2.5
 * 
 */


@WebServiceClient(name = "NotificationBridgeService", 
                  wsdlLocation = "file:/home/oscars/OSCARS_DIST/common-soap/src/main/resources/notificationBridge/notificationBridge.wsdl",
                  targetNamespace = "http://oscars.es.net/OSCARS/notificationBridge") 
public class NotificationBridgeService extends Service {

    public final static URL WSDL_LOCATION;
    public final static QName SERVICE = new QName("http://oscars.es.net/OSCARS/notificationBridge", "NotificationBridgeService");
    public final static QName NotificationBridgePort = new QName("http://oscars.es.net/OSCARS/notificationBridge", "NotificationBridgePort");
    static {
        URL url = null;
        try {
            url = new URL("file:/home/oscars/OSCARS_DIST/common-soap/src/main/resources/notificationBridge/notificationBridge.wsdl");
        } catch (MalformedURLException e) {
            System.err.println("Can not initialize the default wsdl from file:/home/oscars/OSCARS_DIST/common-soap/src/main/resources/notificationBridge/notificationBridge.wsdl");
            // e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

    public NotificationBridgeService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public NotificationBridgeService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public NotificationBridgeService() {
        super(WSDL_LOCATION, SERVICE);
    }

    /**
     * 
     * @return
     *     returns NotificationBridgePortType
     */
    @WebEndpoint(name = "NotificationBridgePort")
    public NotificationBridgePortType getNotificationBridgePort() {
        return super.getPort(NotificationBridgePort, NotificationBridgePortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns NotificationBridgePortType
     */
    @WebEndpoint(name = "NotificationBridgePort")
    public NotificationBridgePortType getNotificationBridgePort(WebServiceFeature... features) {
        return super.getPort(NotificationBridgePort, NotificationBridgePortType.class, features);
    }

}

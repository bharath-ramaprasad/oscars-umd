package net.es.oscars.utils.clients;

import java.net.MalformedURLException;
import java.net.URL;

import net.es.oscars.logging.ModuleName;
import net.es.oscars.logging.OSCARSNetLoggerize;
import net.es.oscars.notificationBridge.soap.gen.NotificationBridgePortType;
import net.es.oscars.notificationBridge.soap.gen.NotificationBridgeService;
import net.es.oscars.utils.config.ConfigDefaults;
import net.es.oscars.utils.config.ConfigException;
import net.es.oscars.utils.config.ContextConfig;
import net.es.oscars.utils.soap.OSCARSService;
import net.es.oscars.utils.soap.OSCARSServiceException;
import net.es.oscars.utils.soap.OSCARSSoapService;
import net.es.oscars.utils.svc.ServiceNames;

@OSCARSNetLoggerize(moduleName = ModuleName.NOTIFY)
@OSCARSService (
        implementor = "net.es.oscars.notificationBridge.soap.gen.NotificationBridgeService",
        namespace = "http://oscars.es.net/OSCARS/notificationBridge", 
        serviceName = ServiceNames.SVC_NOTIFY
)
public class NotificationBridgeClient extends OSCARSSoapService<NotificationBridgeService, NotificationBridgePortType>{

    /**
     * Constructor. 
     * 
     * @param host The location of the host to contact
     * @param wsdl The location of the WSDL file for this service
     * @throws OSCARSServiceException
     */
    public NotificationBridgeClient(URL host, URL wsdl) throws OSCARSServiceException {
        super(host, wsdl, NotificationBridgePortType.class);
    }
    
    /**
     * Creates a NotificationBridgeClient object that can be used to call the Notification 
     * service.
     * 
     * @param url The location of the service to contact as a URL string
     * @param wsdl The location of the service wsdl as a URL string
     * @return A NotificationBridgeClient object that can be used to send request to the service
     * @throws OSCARSServiceException
     * @throws MalformedURLException
     */
    static public NotificationBridgeClient getClient(String url, String wsdl) throws OSCARSServiceException, MalformedURLException{
        ContextConfig cc = ContextConfig.getInstance();
        try {
            String cxfClientPath = cc.getFilePath(cc.getServiceName(), ConfigDefaults.CXF_CLIENT);
            System.out.println("NotificationBridgeClient setting BusConfiguration from " + cxfClientPath);
            OSCARSSoapService.setSSLBusConfiguration(new URL("file:" + cxfClientPath));
        } catch (ConfigException e) {
            System.out.println("NotificationBridgeClient caught ConfigException");
            e.printStackTrace();
            throw new OSCARSServiceException(e.getMessage());
        }
        if(wsdl == null){
            return NotificationBridgeClient.getClient(url);
        }
        return new NotificationBridgeClient(new URL(url), new URL(wsdl));
    }
    
    /**
     * Creates a NotificationBridgeClient object that can be used to call the notification 
     * service given only the address of the service. The location of the WSDL
     * is assumed to be <i>host</i>?wsdl.
     * 
     * @param url The location of the service to contact as a URL string
     * @return A NotificationBridgeClient object that can be used to send request to the service
     * @throws OSCARSServiceException
     * @throws MalformedURLException
     */
    static public NotificationBridgeClient getClient(String url) throws OSCARSServiceException, MalformedURLException{
        return new NotificationBridgeClient(new URL(url), new URL(url+"?wsdl"));
    }
}

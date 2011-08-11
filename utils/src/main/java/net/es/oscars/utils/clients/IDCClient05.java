package net.es.oscars.utils.clients;

import java.net.URL;
import java.net.MalformedURLException;

import net.es.oscars.utils.soap.OSCARSServiceException;
import net.es.oscars.utils.soap.OSCARSService;
import net.es.oscars.utils.soap.OSCARSSoapService;
import net.es.oscars.utils.svc.ServiceNames;
import net.es.oscars.utils.config.ConfigDefaults;
import net.es.oscars.utils.config.ConfigException;
import net.es.oscars.utils.config.ContextConfig;

import org.apache.log4j.Logger;
import org.apache.xml.utils.URI;

@OSCARSService (
        implementor = "net.es.oscars.api.soap.gen.v05.OSCARSService",
        namespace = "http://oscars.es.net/OSCARS",
        serviceName = ServiceNames.SVC_API,
        config=ConfigDefaults.CONFIG
)
public class IDCClient05 extends OSCARSSoapService<net.es.oscars.api.soap.gen.v05.OSCARSService,
                                                   net.es.oscars.api.soap.gen.v05.OSCARS> {

    private static Logger LOG = Logger.getLogger(IDCClient05.class);

    private IDCClient05 (URL host, URL wsdlFile) throws OSCARSServiceException {
        super (host, wsdlFile, net.es.oscars.api.soap.gen.v05.OSCARS.class);
    }
    
    static public IDCClient05 getClient (URL host, URL wsdl, String connType) throws MalformedURLException, OSCARSServiceException {

        // TODO: the following line is to force the class loader to load the Xalan's URI class, including its inner classes
        // which otherwise would not be loaded, and therefore, generate a Class Not Found exception.
        // Note that this is needed only for client that uses signing.
        org.apache.xml.utils.URI uri = new org.apache.xml.utils.URI();
        
        ContextConfig cc = ContextConfig.getInstance();
        LOG = Logger.getLogger(IDCClient05.class);
        if (connType.equals("UT")) {
            try {
                if (cc.getContext() != null ) {  // use new configuration method
                    String cxfClientPath = cc.getFilePath(cc.getServiceName(), ConfigDefaults.CXF_CLIENT);
                    System.out.println("IDCClient setting BusConfiguration from " + cxfClientPath);
                    OSCARSSoapService.setSSLBusConfiguration(new URL("file:" + cxfClientPath));
                } else { 
                    LOG.debug("ContextConfig not set");
                    System.exit(-1);
                }
            } catch  (ConfigException e) {
                LOG.error("IDCClient05 caught ConfigException");
                throw new OSCARSServiceException(e.getMessage());
            }
        IDCClient05 client = new IDCClient05 (host, wsdl);

        return client;
    } else if (connType.equals("x509")) {
        try {
            if (cc.getContext() != null ) {  // use new configuration method
                String cxfClientPath = cc.getFilePath(cc.getServiceName(), ConfigDefaults.CXF_CLIENT);
                System.out.println("IDCClient setting BusConfiguration from " + cxfClientPath);
                OSCARSSoapService.setSSLBusConfiguration(new URL("file:" + cxfClientPath));
            } else { 
                LOG.debug("ContextConfig not set");
                System.exit(-1);
            }
        } catch  (ConfigException e) {
            LOG.error("IDCClient06 caught ConfigException");
            throw new OSCARSServiceException(e.getMessage());
        }
        IDCClient05 client = new IDCClient05 (host, wsdl);
        return client;
    }
    
    throw new RuntimeException (connType + " is invalid.");
}

}

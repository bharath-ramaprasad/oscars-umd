package net.es.oscars.resourceManager.stubserver;

import net.es.oscars.utils.soap.OSCARSService;
import net.es.oscars.utils.soap.OSCARSSoapService;
import net.es.oscars.utils.soap.OSCARSServiceException;

import net.es.oscars.resourceManager.soap.gen.ResourceManagerService;
import net.es.oscars.resourceManager.soap.gen.RMPortType;

@OSCARSService (
		implementor = "net.es.oscars.resourceManager.stubserver.RMStubSoapHandler",
		serviceName = "ResourceManagerService",
		config = "resourceManager.yaml"
)
public class RMStubSoapServer extends OSCARSSoapService<ResourceManagerService, RMPortType> {
 
    private static RMStubSoapServer instance;

    public static RMStubSoapServer getInstance() throws OSCARSServiceException {
        if (instance == null) {
            instance = new RMStubSoapServer();
        }
        return instance;
    }

    private RMStubSoapServer() throws OSCARSServiceException {
        super();
    }
    
    public static void main(String[] args) throws Exception {
        System.out.println (">>>>>>>>> Startion Resource Manager stub server");
        RMStubSoapServer server = RMStubSoapServer.getInstance();
        server.startServer(false);       
    }
}

package net.es.oscars.api.common;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.net.MalformedURLException;

import org.apache.log4j.Logger;

import net.es.oscars.api.http.OSCARSInternalServer;
import net.es.oscars.logging.OSCARSNetLogger;
import net.es.oscars.utils.config.ConfigDefaults;
import net.es.oscars.utils.config.ConfigException;
import net.es.oscars.utils.config.ConfigHelper;
import net.es.oscars.utils.config.ContextConfig;
import net.es.oscars.utils.soap.OSCARSServiceException;
import net.es.oscars.utils.soap.OSCARSSoapService;
import net.es.oscars.utils.svc.ServiceNames;
import net.es.oscars.utils.topology.PathTools;
import net.es.oscars.utils.clients.CoordClient;
import net.es.oscars.utils.clients.AuthNClient;
import net.es.oscars.utils.clients.LookupClient;


/**
 * 
 * Singleton class that loads the configuration  information, initializes
 * and saves the clients that are needed.
 * 
 * @author lomax
 *
 */
public class OSCARSIDC implements Runnable {

    private static  Logger         LOG                = null;
    private ContextConfig          cc                 = null;
    private HashMap<String, HashMap<String, HashMap<String, String>>> manifest = null;
    private HashMap<String,Object> coordMap           = null;
    private HashMap<String,Object> lookupMap          = null;
    private HashMap<String,Object> authNMap           = null;
    private HashMap<String,Object> IDCMap             = null;
    private HashMap<String,Object> IDC_INTMap         = null;
    private CoordClient            coordClient        = null;
    private AuthNClient            authNClient        = null;
    private LookupClient           lookupClient       = null;
    private URL                    coordHost          = null;
    private URL                    authNHost          = null;
    private URL                    lookupHost         = null;
    private Map                    config             = null;
    private static boolean         internalAPIStarted = false;
    private static Thread          serverThread       = null;

    private static OSCARSIDC instance;
    
    public static OSCARSIDC getInstance() throws OSCARSServiceException {
        if (instance == null) {
            instance = new OSCARSIDC();
        }
        return instance;
    }
         
    private OSCARSIDC () throws OSCARSServiceException {
        
        LOG = Logger.getLogger(OSCARSIDC.class.getName());
        OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
        String event = "OSCARSIDC";
        cc = ContextConfig.getInstance();
        // Load configuration file
        this.loadConfigs();
        
        // Retrieve the hosts URL where the AuthNService and Coordinator service are running
        this.setModuleHosts();

        // Instantiates the CXF bus
        try {
            OSCARSSoapService.setSSLBusConfiguration(
                    new URL("file:" + cc.getFilePath(ConfigDefaults.CXF_SERVER)));
        } catch (MalformedURLException e) {
            throw new OSCARSServiceException (e);
        }  catch (ConfigException e ) {
            throw new OSCARSServiceException (e);
        }
        
        // Make sure that the Internal API Service e is running
        if (OSCARSIDC.serverThread == null) {
            OSCARSIDC.serverThread = new Thread (this);
            OSCARSIDC.serverThread.start();
        }
        
        // Instantiates Coordinator client
        try {           
            URL coordWsdl = cc.getWSDLPath(ServiceNames.SVC_COORD,null);
            LOG.info (netLogger.start(event,"Coordinator host= " + this.coordHost + " WSDL= " + coordWsdl));
            this.coordClient = CoordClient.getClient(this.coordHost,coordWsdl);
        } catch (MalformedURLException e) {
            throw new OSCARSServiceException (e);
        }
        // Instantiates AuthNStub client
        try {
            URL authNWsdl = cc.getWSDLPath(ServiceNames.SVC_AUTHN,null);
            LOG.info (netLogger.start(event,"AuthNhost= " + this.authNHost + " WSDL= " + authNWsdl));
            this.authNClient = AuthNClient.getClient(this.authNHost,authNWsdl);
        } catch (MalformedURLException e) {
            throw new OSCARSServiceException (e);
        }
        // Instantiates lookup client
        try {           
            URL lookupWsdl = cc.getWSDLPath(ServiceNames.SVC_LOOKUP,null);
            LOG.info ("Lookup host= " + this.lookupHost + " WSDL= " + lookupWsdl);
            this.lookupClient = LookupClient.getClient(this.lookupHost,lookupWsdl);
        } catch (MalformedURLException e) {
            throw new OSCARSServiceException (e);
        }
        
        // Register this IDC
        try {
            this.registerIDC();
        } catch (OSCARSServiceException e) {
            // Ignore but log the error
            LOG.error("Cannot register to the LookupService");
        }
    }
    
    public void startService() throws OSCARSServiceException {
        
    }
    
    private void loadConfigs() throws OSCARSServiceException {
        String configFile = null;

        try {
            this.manifest = cc.getManifest();
            String configFilename= null;
            // assumes all the services are running in the same context
            configFilename = cc.getFilePath(ConfigDefaults.CONFIG);
            this.IDCMap = (HashMap<String,Object>)ConfigHelper.getConfiguration(configFilename);

            configFilename = cc.getFilePath(ServiceNames.SVC_AUTHN,cc.getContext(),
                    ConfigDefaults.CONFIG);
            this.authNMap = (HashMap<String,Object>)ConfigHelper.getConfiguration(configFilename);

            configFilename = cc.getFilePath(ServiceNames.SVC_COORD,cc.getContext(),
                    ConfigDefaults.CONFIG);
            this.coordMap = (HashMap<String,Object>)ConfigHelper.getConfiguration(configFilename);

            configFilename = cc.getFilePath(ServiceNames.SVC_API_INTERNAL,cc.getContext(),
                    ConfigDefaults.CONFIG);
            this.IDC_INTMap = (HashMap<String,Object>)ConfigHelper.getConfiguration(configFilename);
            
            configFilename = cc.getFilePath(ServiceNames.SVC_LOOKUP,cc.getContext(),
                    ConfigDefaults.CONFIG);
            
            this.lookupMap = (HashMap<String,Object>)ConfigHelper.getConfiguration(configFilename);
            
        } catch (Exception e) {
            throw new OSCARSServiceException (e);
        }
    }

    private void setModuleHosts () throws OSCARSServiceException {
        HashMap<String,Object> soap = null;
        OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
        String event = "setHosts";
        try { 
            // Retrieve AuthN host 
            soap = (HashMap<String,Object>) this.authNMap.get("soap");
            if (soap == null ) {
                throw new ConfigException("soap stanza not found in authN.yaml");
            }
            this.authNHost = new URL ((String)soap.get("publishTo"));
            LOG.debug(netLogger.getMsg(event,"authN running on " + this.authNHost.toString()));
            
            // retrieve coodinator host
            soap = (HashMap<String,Object>) this.coordMap.get("soap");
            if (soap == null ){
                throw new ConfigException("soap stanza not found in coordinator.yaml");
            }
            this.coordHost = new URL ((String)soap.get("publishTo"));
            LOG.debug(netLogger.getMsg(event,"coodinator running on " + this.coordHost.toString()));
            
            // retrieve lookup host
            soap = (HashMap<String,Object>) this.lookupMap.get("soap");
            if (soap == null ){
                throw new ConfigException("soap stanza not found in lookup.yaml");
            }
            this.lookupHost = new URL ((String)soap.get("publishTo"));
            LOG.debug(netLogger.getMsg(event,"lookup running on " + this.lookupHost.toString()));
        } catch (MalformedURLException e) {
            throw new OSCARSServiceException (e);
        } catch (ConfigException e) {
            throw new OSCARSServiceException (e);
        }
    }

    public CoordClient getCoordClient() {
        return this.coordClient;
    }
    

    public AuthNClient getAuthNClient() {
        return this.authNClient;
    }
    
    public LookupClient getLookupClient() {
        return this.lookupClient;
    }
    
    private void registerIDC () throws OSCARSServiceException {
        LookupClient client = this.getLookupClient();
        if (client == null) {
            LOG.warn("Cannot register this IDC to the lookup service: no lookup service client");
            return;
        }
        String domainId = PathTools.getLocalDomainId();
        
        // TODO: needs to be defined in a config file
        String namespace = "http://oscars.es.net/OSCARS/06";
        String name = "ESnet IDC";
        String description = "es.net's IDC";
        String location = "http://localhost:9001/OSCARS";
        
        client.register (domainId, name, namespace, location, description);
    }
    

    public void run() {
        try {
            this.startInternalAPI ();
        } catch (OSCARSServiceException e) {
            LOG.error("OSCARSIDC.run caught exception:" + e.getMessage());
        }
    }

    private synchronized void startInternalAPI () throws OSCARSServiceException {
        if (! OSCARSIDC.internalAPIStarted) {
            OSCARSInternalServer.getInstance().startServer(false);
            OSCARSIDC.internalAPIStarted = true;
        }
    }
}

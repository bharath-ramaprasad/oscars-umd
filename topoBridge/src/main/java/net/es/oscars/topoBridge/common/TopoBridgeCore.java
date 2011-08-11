package net.es.oscars.topoBridge.common;

import java.util.Map;

import net.es.oscars.common.soap.gen.OSCARSFaultMessage;
import net.es.oscars.logging.ErrSev;
import net.es.oscars.logging.ModuleName;
import net.es.oscars.logging.OSCARSNetLogger;
import net.es.oscars.topoBridge.ps.PSTopoConverter;
import net.es.oscars.utils.config.ConfigDefaults;
import net.es.oscars.utils.config.ConfigException;
import net.es.oscars.utils.config.ConfigHelper;
import net.es.oscars.utils.config.ContextConfig;
import net.es.oscars.utils.soap.OSCARSServiceException;
import net.es.oscars.utils.topology.PathTools;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.output.XMLOutputter;



public class TopoBridgeCore {
    private static Logger log = Logger.getLogger(TopoBridgeCore.class);
    private static TopoBridgeCore instance = null;
    private static String localDomainId = null;
    private static ContextConfig cc = ContextConfig.getInstance();


    /**
     * Constructor - private because this is a Singleton
     */
    private TopoBridgeCore() throws ConfigException {
        String configFile = cc.getFilePath(ConfigDefaults.CONFIG);
        Map config = ConfigHelper.getConfiguration(configFile);
        assert config != null : "No configuration";
        Map topoBridgeCfg = (Map) config.get("topoBridge");
        assert topoBridgeCfg != null : "No topoBridge stanza in configuration";

        localDomainId = PathTools.getLocalDomainId();
        assert localDomainId != null : "No localDomainId in configuration";

    }

    /**
     * @return the TopoBridgeCore singleton instance
     */
    public static TopoBridgeCore getInstance() throws OSCARSServiceException {
        try {
            if (TopoBridgeCore.instance == null) {
                TopoBridgeCore.instance = new TopoBridgeCore();
            }
        } catch (ConfigException e) {
            throw new OSCARSServiceException(e.getMessage());
        }
        return TopoBridgeCore.instance;
    }

    public void getLocalTopology() throws OSCARSServiceException {
        OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
        log.debug(netLogger.start("getLocalTopology"));
        
        try {
            TopologyCache tc = TopologyCache.getInstance();

            Document domain = tc.getDomain(localDomainId, netLogger);
            // XMLOutputter outputter = new XMLOutputter();
            // PSTopoConverter.convertTopology(domain, tc.getNsUri());
            // outputter.output(domain, System.out);

            // domain = tc.getDomain("testdomain-1");
            // PSTopoConverter.convertTopology(domain, tc.getNsUri());
            // outputter.output(domain, System.out);
            log.debug(netLogger.end("getLocalTopology"));
        } catch (OSCARSServiceException e) {
            log.debug(netLogger.error("getLocalTopology", ErrSev.MAJOR, e.getMessage()));
            throw e;
        } catch (Exception e) {
            log.debug(netLogger.error("getLocalTopology", ErrSev.MAJOR, e.getMessage()));
            e.printStackTrace();
            throw new OSCARSServiceException(e.getMessage());
        }
    }

    public void shutdown() {
        OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
        log.info(netLogger.end("shutdown","not implemented"));
    }

}

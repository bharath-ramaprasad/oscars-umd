package net.es.oscars.api.forwarder;

import java.net.URL;

import org.apache.log4j.Logger;


import net.es.oscars.api.common.OSCARSIDC;
import net.es.oscars.utils.soap.OSCARSServiceException;


public class ForwarderFactory {
    private static final long      serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(ForwarderFactory.class.getName());
    
    public static Forwarder getForwarder (String domainId) throws OSCARSServiceException {
    
        // TODO: namespace needs be cleaned up / not hardcoded
        URL url = OSCARSIDC.getInstance().getLookupClient().lookup("urn:ogf:network:domain=" + domainId);
        if (url == null) {
            LOG.error("no location for IDC controlling domain " + domainId);
            throw new OSCARSServiceException ("no location for IDC controlling domain " + domainId);
        }
        
        // TODO: need to manage different namespace / backward compatibility
        Forwarder forwarder = new Forwarder06 (domainId, url);
        
        return forwarder;
    }
    
}

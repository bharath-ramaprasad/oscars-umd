
package net.es.oscars.wsnbroker.soap.gen;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.2.5
 * Mon Jul 18 10:16:59 EDT 2011
 * Generated source version: 2.2.5
 * 
 */

@WebFault(name = "ResourceUnknownFault", targetNamespace = "http://docs.oasis-open.org/wsrf/r-2")
public class ResourceUnknownFault extends Exception {
    public static final long serialVersionUID = 20110718101659L;
    
    private org.oasis_open.docs.wsrf.r_2.ResourceUnknownFaultType resourceUnknownFault;

    public ResourceUnknownFault() {
        super();
    }
    
    public ResourceUnknownFault(String message) {
        super(message);
    }
    
    public ResourceUnknownFault(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceUnknownFault(String message, org.oasis_open.docs.wsrf.r_2.ResourceUnknownFaultType resourceUnknownFault) {
        super(message);
        this.resourceUnknownFault = resourceUnknownFault;
    }

    public ResourceUnknownFault(String message, org.oasis_open.docs.wsrf.r_2.ResourceUnknownFaultType resourceUnknownFault, Throwable cause) {
        super(message, cause);
        this.resourceUnknownFault = resourceUnknownFault;
    }

    public org.oasis_open.docs.wsrf.r_2.ResourceUnknownFaultType getFaultInfo() {
        return this.resourceUnknownFault;
    }
}

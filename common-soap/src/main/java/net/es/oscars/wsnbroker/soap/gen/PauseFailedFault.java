
package net.es.oscars.wsnbroker.soap.gen;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.2.5
 * Mon Jul 18 10:17:00 EDT 2011
 * Generated source version: 2.2.5
 * 
 */

@WebFault(name = "PauseFailedFault", targetNamespace = "http://docs.oasis-open.org/wsn/b-2")
public class PauseFailedFault extends Exception {
    public static final long serialVersionUID = 20110718101700L;
    
    private org.oasis_open.docs.wsn.b_2.PauseFailedFaultType pauseFailedFault;

    public PauseFailedFault() {
        super();
    }
    
    public PauseFailedFault(String message) {
        super(message);
    }
    
    public PauseFailedFault(String message, Throwable cause) {
        super(message, cause);
    }

    public PauseFailedFault(String message, org.oasis_open.docs.wsn.b_2.PauseFailedFaultType pauseFailedFault) {
        super(message);
        this.pauseFailedFault = pauseFailedFault;
    }

    public PauseFailedFault(String message, org.oasis_open.docs.wsn.b_2.PauseFailedFaultType pauseFailedFault, Throwable cause) {
        super(message, cause);
        this.pauseFailedFault = pauseFailedFault;
    }

    public org.oasis_open.docs.wsn.b_2.PauseFailedFaultType getFaultInfo() {
        return this.pauseFailedFault;
    }
}
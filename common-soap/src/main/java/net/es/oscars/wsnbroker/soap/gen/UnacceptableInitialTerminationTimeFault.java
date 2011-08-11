
package net.es.oscars.wsnbroker.soap.gen;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.2.5
 * Mon Jul 18 10:17:00 EDT 2011
 * Generated source version: 2.2.5
 * 
 */

@WebFault(name = "UnacceptableInitialTerminationTimeFault", targetNamespace = "http://docs.oasis-open.org/wsn/b-2")
public class UnacceptableInitialTerminationTimeFault extends Exception {
    public static final long serialVersionUID = 20110718101700L;
    
    private org.oasis_open.docs.wsn.b_2.UnacceptableInitialTerminationTimeFaultType unacceptableInitialTerminationTimeFault;

    public UnacceptableInitialTerminationTimeFault() {
        super();
    }
    
    public UnacceptableInitialTerminationTimeFault(String message) {
        super(message);
    }
    
    public UnacceptableInitialTerminationTimeFault(String message, Throwable cause) {
        super(message, cause);
    }

    public UnacceptableInitialTerminationTimeFault(String message, org.oasis_open.docs.wsn.b_2.UnacceptableInitialTerminationTimeFaultType unacceptableInitialTerminationTimeFault) {
        super(message);
        this.unacceptableInitialTerminationTimeFault = unacceptableInitialTerminationTimeFault;
    }

    public UnacceptableInitialTerminationTimeFault(String message, org.oasis_open.docs.wsn.b_2.UnacceptableInitialTerminationTimeFaultType unacceptableInitialTerminationTimeFault, Throwable cause) {
        super(message, cause);
        this.unacceptableInitialTerminationTimeFault = unacceptableInitialTerminationTimeFault;
    }

    public org.oasis_open.docs.wsn.b_2.UnacceptableInitialTerminationTimeFaultType getFaultInfo() {
        return this.unacceptableInitialTerminationTimeFault;
    }
}

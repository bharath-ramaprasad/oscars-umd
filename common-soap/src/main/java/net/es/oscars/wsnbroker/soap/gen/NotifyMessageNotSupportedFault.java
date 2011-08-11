
package net.es.oscars.wsnbroker.soap.gen;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.2.5
 * Mon Jul 18 10:17:00 EDT 2011
 * Generated source version: 2.2.5
 * 
 */

@WebFault(name = "NotifyMessageNotSupportedFault", targetNamespace = "http://docs.oasis-open.org/wsn/b-2")
public class NotifyMessageNotSupportedFault extends Exception {
    public static final long serialVersionUID = 20110718101700L;
    
    private org.oasis_open.docs.wsn.b_2.NotifyMessageNotSupportedFaultType notifyMessageNotSupportedFault;

    public NotifyMessageNotSupportedFault() {
        super();
    }
    
    public NotifyMessageNotSupportedFault(String message) {
        super(message);
    }
    
    public NotifyMessageNotSupportedFault(String message, Throwable cause) {
        super(message, cause);
    }

    public NotifyMessageNotSupportedFault(String message, org.oasis_open.docs.wsn.b_2.NotifyMessageNotSupportedFaultType notifyMessageNotSupportedFault) {
        super(message);
        this.notifyMessageNotSupportedFault = notifyMessageNotSupportedFault;
    }

    public NotifyMessageNotSupportedFault(String message, org.oasis_open.docs.wsn.b_2.NotifyMessageNotSupportedFaultType notifyMessageNotSupportedFault, Throwable cause) {
        super(message, cause);
        this.notifyMessageNotSupportedFault = notifyMessageNotSupportedFault;
    }

    public org.oasis_open.docs.wsn.b_2.NotifyMessageNotSupportedFaultType getFaultInfo() {
        return this.notifyMessageNotSupportedFault;
    }
}

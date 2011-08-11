
package net.es.oscars.api.soap.gen.v05;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.2.5
 * Mon Jul 18 10:17:04 EDT 2011
 * Generated source version: 2.2.5
 * 
 */

@WebFault(name = "BSSFault", targetNamespace = "http://oscars.es.net/OSCARS")
public class BSSFaultMessage extends Exception {
    public static final long serialVersionUID = 20110718101704L;
    
    private net.es.oscars.api.soap.gen.v05.BSSFault bssFault;

    public BSSFaultMessage() {
        super();
    }
    
    public BSSFaultMessage(String message) {
        super(message);
    }
    
    public BSSFaultMessage(String message, Throwable cause) {
        super(message, cause);
    }

    public BSSFaultMessage(String message, net.es.oscars.api.soap.gen.v05.BSSFault bssFault) {
        super(message);
        this.bssFault = bssFault;
    }

    public BSSFaultMessage(String message, net.es.oscars.api.soap.gen.v05.BSSFault bssFault, Throwable cause) {
        super(message, cause);
        this.bssFault = bssFault;
    }

    public net.es.oscars.api.soap.gen.v05.BSSFault getFaultInfo() {
        return this.bssFault;
    }
}
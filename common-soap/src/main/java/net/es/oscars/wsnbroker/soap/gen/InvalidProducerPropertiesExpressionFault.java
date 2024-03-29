
package net.es.oscars.wsnbroker.soap.gen;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.2.5
 * Mon Jul 18 10:17:00 EDT 2011
 * Generated source version: 2.2.5
 * 
 */

@WebFault(name = "InvalidProducerPropertiesExpressionFault", targetNamespace = "http://docs.oasis-open.org/wsn/b-2")
public class InvalidProducerPropertiesExpressionFault extends Exception {
    public static final long serialVersionUID = 20110718101700L;
    
    private org.oasis_open.docs.wsn.b_2.InvalidProducerPropertiesExpressionFaultType invalidProducerPropertiesExpressionFault;

    public InvalidProducerPropertiesExpressionFault() {
        super();
    }
    
    public InvalidProducerPropertiesExpressionFault(String message) {
        super(message);
    }
    
    public InvalidProducerPropertiesExpressionFault(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidProducerPropertiesExpressionFault(String message, org.oasis_open.docs.wsn.b_2.InvalidProducerPropertiesExpressionFaultType invalidProducerPropertiesExpressionFault) {
        super(message);
        this.invalidProducerPropertiesExpressionFault = invalidProducerPropertiesExpressionFault;
    }

    public InvalidProducerPropertiesExpressionFault(String message, org.oasis_open.docs.wsn.b_2.InvalidProducerPropertiesExpressionFaultType invalidProducerPropertiesExpressionFault, Throwable cause) {
        super(message, cause);
        this.invalidProducerPropertiesExpressionFault = invalidProducerPropertiesExpressionFault;
    }

    public org.oasis_open.docs.wsn.b_2.InvalidProducerPropertiesExpressionFaultType getFaultInfo() {
        return this.invalidProducerPropertiesExpressionFault;
    }
}

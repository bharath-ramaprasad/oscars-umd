
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package net.es.oscars.authZ.soap.gen;

import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 2.2.5
 * Mon Jul 18 10:16:37 EDT 2011
 * Generated source version: 2.2.5
 * 
 */

@javax.jws.WebService(
                      serviceName = "AuthZService",
                      portName = "AuthZPort",
                      targetNamespace = "http://oscars.es.net/OSCARS/authZ",
                      wsdlLocation = "file:/home/oscars/OSCARS_DIST/common-soap/src/main/resources/authZ/authZ.wsdl",
                      endpointInterface = "net.es.oscars.authZ.soap.gen.AuthZPortType")
                      
public class AuthZPortTypeImpl implements AuthZPortType {

    private static final Logger LOG = Logger.getLogger(AuthZPortTypeImpl.class.getName());

    /* (non-Javadoc)
     * @see net.es.oscars.authZ.soap.gen.AuthZPortType#checkAccess(net.es.oscars.authZ.soap.gen.CheckAccessParams  checkAccessReqMsg )*
     */
    public net.es.oscars.authZ.soap.gen.CheckAccessReply checkAccess(CheckAccessParams checkAccessReqMsg) throws net.es.oscars.common.soap.gen.OSCARSFaultMessage    { 
        LOG.info("Executing operation checkAccess");
        System.out.println(checkAccessReqMsg);
        try {
            net.es.oscars.authZ.soap.gen.CheckAccessReply _return = null;
            return _return;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new net.es.oscars.common.soap.gen.OSCARSFaultMessage("OSCARSFaultMessage...");
    }

    /* (non-Javadoc)
     * @see net.es.oscars.authZ.soap.gen.AuthZPortType#checkMultiAccess(net.es.oscars.authZ.soap.gen.CheckMultiAccessParams  checkMultiAccessReqMsg )*
     */
    public net.es.oscars.authZ.soap.gen.MultiAccessPerms checkMultiAccess(CheckMultiAccessParams checkMultiAccessReqMsg) throws net.es.oscars.common.soap.gen.OSCARSFaultMessage    { 
        LOG.info("Executing operation checkMultiAccess");
        System.out.println(checkMultiAccessReqMsg);
        try {
            net.es.oscars.authZ.soap.gen.MultiAccessPerms _return = null;
            return _return;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new net.es.oscars.common.soap.gen.OSCARSFaultMessage("OSCARSFaultMessage...");
    }

}

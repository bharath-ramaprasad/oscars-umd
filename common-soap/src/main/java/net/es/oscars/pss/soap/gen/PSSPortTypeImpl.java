
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package net.es.oscars.pss.soap.gen;

import java.util.logging.Logger;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 2.2.5
 * Mon Jul 18 10:16:46 EDT 2011
 * Generated source version: 2.2.5
 * 
 */

@javax.jws.WebService(
                      serviceName = "PSSService",
                      portName = "PSSPort",
                      targetNamespace = "http://oscars.es.net/OSCARS/pss",
                      wsdlLocation = "file:/home/oscars/OSCARS_DIST/common-soap/src/main/resources/pss/pss.wsdl",
                      endpointInterface = "net.es.oscars.pss.soap.gen.PSSPortType")
                      
public class PSSPortTypeImpl implements PSSPortType {

    private static final Logger LOG = Logger.getLogger(PSSPortTypeImpl.class.getName());

    /* (non-Javadoc)
     * @see net.es.oscars.pss.soap.gen.PSSPortType#teardown(net.es.oscars.pss.soap.gen.TeardownReqContent  teardownReq )*
     */
    public void teardown(TeardownReqContent teardownReq) { 
        LOG.info("Executing operation teardown");
        System.out.println(teardownReq);
        try {
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see net.es.oscars.pss.soap.gen.PSSPortType#modify(net.es.oscars.pss.soap.gen.ModifyReqContent  modifyReq )*
     */
    public void modify(ModifyReqContent modifyReq) { 
        LOG.info("Executing operation modify");
        System.out.println(modifyReq);
        try {
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see net.es.oscars.pss.soap.gen.PSSPortType#setup(net.es.oscars.pss.soap.gen.SetupReqContent  setupReq )*
     */
    public void setup(SetupReqContent setupReq) { 
        LOG.info("Executing operation setup");
        System.out.println(setupReq);
        try {
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see net.es.oscars.pss.soap.gen.PSSPortType#status(net.es.oscars.pss.soap.gen.StatusReqContent  statusReq )*
     */
    public void status(StatusReqContent statusReq) { 
        LOG.info("Executing operation status");
        System.out.println(statusReq);
        try {
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

}

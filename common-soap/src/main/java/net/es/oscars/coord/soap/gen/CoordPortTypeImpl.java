
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package net.es.oscars.coord.soap.gen;

import java.util.logging.Logger;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 2.2.5
 * Mon Jul 18 10:16:23 EDT 2011
 * Generated source version: 2.2.5
 * 
 */

@javax.jws.WebService(
                      serviceName = "CoordService",
                      portName = "CoordPort",
                      targetNamespace = "http://oscars.es.net/OSCARS/coord",
                      wsdlLocation = "file:/home/oscars/OSCARS_DIST/common-soap/src/main/resources/coord/coord.wsdl",
                      endpointInterface = "net.es.oscars.coord.soap.gen.CoordPortType")
                      
public class CoordPortTypeImpl implements CoordPortType {

    private static final Logger LOG = Logger.getLogger(CoordPortTypeImpl.class.getName());

    /* (non-Javadoc)
     * @see net.es.oscars.coord.soap.gen.CoordPortType#createReservation(net.es.oscars.common.soap.gen.SubjectAttributes  subjectAttributes ,)net.es.oscars.api.soap.gen.v06.ResCreateContent  createResvReq )*
     */
    public net.es.oscars.api.soap.gen.v06.CreateReply createReservation(net.es.oscars.common.soap.gen.SubjectAttributes subjectAttributes,net.es.oscars.api.soap.gen.v06.ResCreateContent createResvReq) throws net.es.oscars.common.soap.gen.OSCARSFaultMessage    { 
        LOG.info("Executing operation createReservation");
        System.out.println(subjectAttributes);
        System.out.println(createResvReq);
        try {
            net.es.oscars.api.soap.gen.v06.CreateReply _return = null;
            return _return;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new net.es.oscars.common.soap.gen.OSCARSFaultMessage("OSCARSFaultMessage...");
    }

    /* (non-Javadoc)
     * @see net.es.oscars.coord.soap.gen.CoordPortType#queryReservation(net.es.oscars.common.soap.gen.SubjectAttributes  subjectAttributes ,)net.es.oscars.api.soap.gen.v06.QueryResContent  queryResvReq )*
     */
    public net.es.oscars.api.soap.gen.v06.QueryResReply queryReservation(net.es.oscars.common.soap.gen.SubjectAttributes subjectAttributes,net.es.oscars.api.soap.gen.v06.QueryResContent queryResvReq) throws net.es.oscars.common.soap.gen.OSCARSFaultMessage    { 
        LOG.info("Executing operation queryReservation");
        System.out.println(subjectAttributes);
        System.out.println(queryResvReq);
        try {
            net.es.oscars.api.soap.gen.v06.QueryResReply _return = null;
            return _return;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new net.es.oscars.common.soap.gen.OSCARSFaultMessage("OSCARSFaultMessage...");
    }

    /* (non-Javadoc)
     * @see net.es.oscars.coord.soap.gen.CoordPortType#teardownPath(net.es.oscars.common.soap.gen.SubjectAttributes  subjectAttributes ,)net.es.oscars.api.soap.gen.v06.TeardownPathContent  teardownPathReq ,)net.es.oscars.api.soap.gen.v06.ResDetails  resDetails )*
     */
    public net.es.oscars.api.soap.gen.v06.TeardownPathResponseContent teardownPath(net.es.oscars.common.soap.gen.SubjectAttributes subjectAttributes,net.es.oscars.api.soap.gen.v06.TeardownPathContent teardownPathReq,net.es.oscars.api.soap.gen.v06.ResDetails resDetails) throws net.es.oscars.common.soap.gen.OSCARSFaultMessage    { 
        LOG.info("Executing operation teardownPath");
        System.out.println(subjectAttributes);
        System.out.println(teardownPathReq);
        System.out.println(resDetails);
        try {
            net.es.oscars.api.soap.gen.v06.TeardownPathResponseContent _return = null;
            return _return;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new net.es.oscars.common.soap.gen.OSCARSFaultMessage("OSCARSFaultMessage...");
    }

    /* (non-Javadoc)
     * @see net.es.oscars.coord.soap.gen.CoordPortType#modifyReservation(net.es.oscars.common.soap.gen.SubjectAttributes  subjectAttributes ,)net.es.oscars.api.soap.gen.v06.ModifyResContent  modifyResvReq )*
     */
    public net.es.oscars.api.soap.gen.v06.ModifyResReply modifyReservation(net.es.oscars.common.soap.gen.SubjectAttributes subjectAttributes,net.es.oscars.api.soap.gen.v06.ModifyResContent modifyResvReq) throws net.es.oscars.common.soap.gen.OSCARSFaultMessage    { 
        LOG.info("Executing operation modifyReservation");
        System.out.println(subjectAttributes);
        System.out.println(modifyResvReq);
        try {
            net.es.oscars.api.soap.gen.v06.ModifyResReply _return = null;
            return _return;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new net.es.oscars.common.soap.gen.OSCARSFaultMessage("OSCARSFaultMessage...");
    }

    /* (non-Javadoc)
     * @see net.es.oscars.coord.soap.gen.CoordPortType#cancelReservation(net.es.oscars.common.soap.gen.SubjectAttributes  subjectAttributes ,)net.es.oscars.api.soap.gen.v06.CancelResContent  cancelResvReq )*
     */
    public net.es.oscars.api.soap.gen.v06.CancelResReply cancelReservation(net.es.oscars.common.soap.gen.SubjectAttributes subjectAttributes,net.es.oscars.api.soap.gen.v06.CancelResContent cancelResvReq) throws net.es.oscars.common.soap.gen.OSCARSFaultMessage    { 
        LOG.info("Executing operation cancelReservation");
        System.out.println(subjectAttributes);
        System.out.println(cancelResvReq);
        try {
            net.es.oscars.api.soap.gen.v06.CancelResReply _return = null;
            return _return;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new net.es.oscars.common.soap.gen.OSCARSFaultMessage("OSCARSFaultMessage...");
    }

    /* (non-Javadoc)
     * @see net.es.oscars.coord.soap.gen.CoordPortType#pssReply(net.es.oscars.coord.soap.gen.PSSReplyContent  pssReplyReq )*
     */
    public void pssReply(PSSReplyContent pssReplyReq) { 
        LOG.info("Executing operation pssReply");
        System.out.println(pssReplyReq);
        try {
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see net.es.oscars.coord.soap.gen.CoordPortType#createPath(net.es.oscars.common.soap.gen.SubjectAttributes  subjectAttributes ,)net.es.oscars.api.soap.gen.v06.CreatePathContent  createPathReq ,)net.es.oscars.api.soap.gen.v06.ResDetails  resDetails )*
     */
    public net.es.oscars.api.soap.gen.v06.CreatePathResponseContent createPath(net.es.oscars.common.soap.gen.SubjectAttributes subjectAttributes,net.es.oscars.api.soap.gen.v06.CreatePathContent createPathReq,net.es.oscars.api.soap.gen.v06.ResDetails resDetails) throws net.es.oscars.common.soap.gen.OSCARSFaultMessage    { 
        LOG.info("Executing operation createPath");
        System.out.println(subjectAttributes);
        System.out.println(createPathReq);
        System.out.println(resDetails);
        try {
            net.es.oscars.api.soap.gen.v06.CreatePathResponseContent _return = null;
            return _return;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new net.es.oscars.common.soap.gen.OSCARSFaultMessage("OSCARSFaultMessage...");
    }

    /* (non-Javadoc)
     * @see net.es.oscars.coord.soap.gen.CoordPortType#interDomainEvent(net.es.oscars.common.soap.gen.SubjectAttributes  subjectAttributes ,)net.es.oscars.api.soap.gen.v06.InterDomainEventContent  interDomainEvent )*
     */
    public void interDomainEvent(net.es.oscars.common.soap.gen.SubjectAttributes subjectAttributes,net.es.oscars.api.soap.gen.v06.InterDomainEventContent interDomainEvent) { 
        LOG.info("Executing operation interDomainEvent");
        System.out.println(subjectAttributes);
        System.out.println(interDomainEvent);
        try {
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see net.es.oscars.coord.soap.gen.CoordPortType#listReservations(net.es.oscars.common.soap.gen.SubjectAttributes  subjectAttributes ,)net.es.oscars.api.soap.gen.v06.ListRequest  listResvReq )*
     */
    public net.es.oscars.api.soap.gen.v06.ListReply listReservations(net.es.oscars.common.soap.gen.SubjectAttributes subjectAttributes,net.es.oscars.api.soap.gen.v06.ListRequest listResvReq) throws net.es.oscars.common.soap.gen.OSCARSFaultMessage    { 
        LOG.info("Executing operation listReservations");
        System.out.println(subjectAttributes);
        System.out.println(listResvReq);
        try {
            net.es.oscars.api.soap.gen.v06.ListReply _return = null;
            return _return;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new net.es.oscars.common.soap.gen.OSCARSFaultMessage("OSCARSFaultMessage...");
    }

}

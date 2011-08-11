package net.es.oscars.resourceManager.stubserver;

import java.util.logging.Logger;

import net.es.oscars.resourceManager.soap.gen.*;
import net.es.oscars.common.soap.gen.*;
import net.es.oscars.api.soap.gen.v06.CancelResContent;
import net.es.oscars.api.soap.gen.v06.GlobalReservationId;
import net.es.oscars.api.soap.gen.v06.ModifyResContent;
import net.es.oscars.api.soap.gen.v06.ModifyResReply;
import net.es.oscars.api.soap.gen.v06.QueryResContent;
import net.es.oscars.api.soap.gen.v06.QueryResReply;
import net.es.oscars.api.soap.gen.v06.ResDetails;
import net.es.oscars.api.soap.gen.v06.ListRequest;
import net.es.oscars.api.soap.gen.v06.ListReply;

@javax.jws.WebService(
                      serviceName = "ResourceManagerService",
                      portName = "RMPort",
                      targetNamespace = "http://oscars.es.net/OSCARS/resourceManager",
                      endpointInterface = "net.es.oscars.resourceManager.soap.gen.RMPortType")
@javax.xml.ws.BindingType(value = "http://www.w3.org/2003/05/soap/bindings/HTTP/")

public class RMStubSoapHandler implements RMPortType {
    
    private static long currentGRI = 0L;

    private static final Logger LOG =
        Logger.getLogger(RMStubSoapHandler.class.getName());

    public AssignGriRespContent assignGri(AssignGriReqContent assignGriReq)
            throws OSCARSFaultMessage { 

        LOG.info("Executing operation assignGri");
        try {
            AssignGriRespContent _return = new AssignGriRespContent();
            _return.setTransactionId(assignGriReq.getTransactionId());
            _return.setGlobalReservationId( Long.toString(++RMStubSoapHandler.currentGRI));
            return _return;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new OSCARSFaultMessage("OSCARSFaultMessage...");
    }

    public UpdateStatusRespContent
        updateStatus(UpdateStatusReqContent updateStatusReq)
           throws OSCARSFaultMessage { 

        LOG.info("Executing operation updateStatus");
        try {
            UpdateStatusRespContent _return = null;
            return _return;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new OSCARSFaultMessage("OSCARSFaultMessage...");
    }

    public StoreRespContent store(StoreReqContent storeReq)
           throws OSCARSFaultMessage    { 

        LOG.info("Executing operation store");
        try {
            StoreRespContent _return = null;
            return _return;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new OSCARSFaultMessage("OSCARSFaultMessage...");
    }

    public QueryResReply
        queryReservation(AuthConditions authConditions, QueryResContent queryReservationRequest)
            throws OSCARSFaultMessage { 

        LOG.info("Executing operation queryReservation");
        try {
            QueryResReply _return = null;
            return _return;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new OSCARSFaultMessage("OSCARSFaultMessage...");
    }

    public RMCancelRespContent cancelReservation(AuthConditions authConditions, CancelResContent cancelReservationRequest)
    throws OSCARSFaultMessage { 
        LOG.info("Executing operation cancelReservation");
        System.out.println("cancelReservation");
        try {
            RMCancelRespContent  _return = null;
            return _return;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
    
    public 
    ModifyResReply modifyReservation(AuthConditions authConditions, ModifyResContent modifyReservationRequest)
        throws OSCARSFaultMessage { 
        LOG.info("Executing operation ModifyReservation");
        System.out.println("modifyReservation");
        try {
            ModifyResReply  _return = null;
            return _return;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    public ListReply listReservations(AuthConditions authConditions, ListRequest listReservations)
            throws OSCARSFaultMessage { 

        LOG.info("Executing operation listReservations");
        System.out.println("listReservations");
        try {
            ListReply _return = null;
            return _return;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new OSCARSFaultMessage("OSCARSFaultMessage...");
    }

    public GetAuditDataRespContent
        getAuditData(GetAuditDataReqContent getAuditDataReq)
            throws OSCARSFaultMessage { 

        LOG.info("Executing operation getAuditData");
        try {
            GetAuditDataRespContent _return = null;
            return _return;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new OSCARSFaultMessage("OSCARSFaultMessage...");
    }
}

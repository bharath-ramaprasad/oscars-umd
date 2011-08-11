package net.es.oscars.resourceManager.http;

import java.util.ArrayList;
import java.util.List;
import java.lang.ThreadLocal;

import org.apache.log4j.Logger;
import org.hibernate.Session;


import net.es.oscars.resourceManager.soap.gen.*;
import net.es.oscars.api.soap.gen.v06.CancelResContent;
import net.es.oscars.api.soap.gen.v06.GlobalReservationId;
import net.es.oscars.api.soap.gen.v06.ModifyResContent;
import net.es.oscars.api.soap.gen.v06.ModifyResReply;
import net.es.oscars.api.soap.gen.v06.QueryResContent;
import net.es.oscars.api.soap.gen.v06.QueryResReply;
import net.es.oscars.api.soap.gen.v06.ResDetails;
import net.es.oscars.api.soap.gen.v06.ListRequest;
import net.es.oscars.api.soap.gen.v06.ListReply;
import net.es.oscars.api.soap.gen.v06.VlanTag;
import net.es.oscars.common.soap.gen.*;
import net.es.oscars.resourceManager.beans.Reservation;
import net.es.oscars.resourceManager.common.RMCore;
import net.es.oscars.resourceManager.common.RMException;
import net.es.oscars.resourceManager.common.ResourceManager;
import net.es.oscars.resourceManager.common.StateEngine;
import net.es.oscars.utils.soap.OSCARSFaultUtils;
import net.es.oscars.utils.svc.ServiceNames;
import net.es.oscars.logging.ModuleName;
import net.es.oscars.logging.OSCARSNetLogger;
import net.es.oscars.logging.OSCARSNetLoggerize;

@OSCARSNetLoggerize(moduleName = ModuleName.RM)
@javax.jws.WebService(
                      serviceName = ServiceNames.SVC_RM,
                      portName = "RMPort",
                      targetNamespace = "http://oscars.es.net/OSCARS/resourceManager",
                      endpointInterface = "net.es.oscars.resourceManager.soap.gen.RMPortType")
@javax.xml.ws.BindingType(value = "http://www.w3.org/2003/05/soap/bindings/HTTP/")
public class RMSoapHandler implements RMPortType {

    private static final Logger LOG =
        Logger.getLogger(RMSoapHandler.class.getName());
    private String moduleName = 
        this.getClass().getAnnotation(OSCARSNetLoggerize.class).moduleName();

    private RMCore core = RMCore.getInstance();

    public AssignGriRespContent assignGri(AssignGriReqContent assignGriReq)
            throws OSCARSFaultMessage { 

        String event = "assignGri";
        String transId = assignGriReq.getTransactionId();
        OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
        netLogger.init(moduleName,transId);
        LOG.debug(netLogger.start(event));
        ResourceManager mgr = core.getResourceManager();
        Session session = core.getSession();
        String gri = null;
        AssignGriRespContent reply = new AssignGriRespContent();
        
        LOG.debug(netLogger.getMsg(event,"transactionId is:" + transId));
        reply.setTransactionId(assignGriReq.getTransactionId());

        try {
           session.beginTransaction();
           gri = mgr.generateGRI();
           reply.setGlobalReservationId(gri);

        } catch (RMException ex) {
            OSCARSFaultUtils.handleError( ex, true, session, LOG, event);
        } catch (Exception ex) {
            OSCARSFaultUtils.handleError( ex, false, session, LOG, event);
        }
        session.getTransaction().commit();
        netLogger.setGRI(gri);
        LOG.debug(netLogger.end(event));
        return reply;
    }
    /**
     * updates the status of a reservation
     * @param udateStatusReq contains gri and  new status
     * @throws OSCARSFaultMessage if reservation is not found or state transition is not allowed
     * @seeAlso StateEngine for allowed state transitions
     */
    public UpdateStatusRespContent
        updateStatus(UpdateStatusReqContent updateStatusReq)
           throws OSCARSFaultMessage { 
        String event = "updateStatus";
        OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
        netLogger.init(moduleName, updateStatusReq.getTransactionId());
        netLogger.setGRI(updateStatusReq.getGlobalReservationId());
        LOG.debug(netLogger.start(event,
                                 " status is " + updateStatusReq.getStatus()));
        UpdateStatusRespContent response = new UpdateStatusRespContent();
        response.setTransactionId(updateStatusReq.getTransactionId());
        String retStatus = null;
        ResourceManager mgr = core.getResourceManager();
        Session session = core.getSession();
        try {
            session.beginTransaction();
            retStatus = mgr.updateStatus(updateStatusReq.getGlobalReservationId(),
                    updateStatusReq.getStatus());
        } catch (RMException ex) {
            OSCARSFaultUtils.handleError( ex, true, session, LOG, event);
        } catch (Exception ex) {
            OSCARSFaultUtils.handleError( ex, false, session, LOG, event);
        }
        session.getTransaction().commit();
        LOG.debug(netLogger.end(event));
        response.setGlobalReservationId(updateStatusReq.getGlobalReservationId());
        response.setStatus(retStatus);
        return response;
    }

    public StoreRespContent store(StoreReqContent storeReq)
           throws OSCARSFaultMessage    { 
        
        String event = "storeReservation";
        OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
        netLogger.init(moduleName, storeReq.getTransactionId());
        netLogger.setGRI(storeReq.getReservation().getGlobalReservationId());
        LOG.info(netLogger.start(event,
                                " status is " + storeReq.getReservation().getStatus()));

        StoreRespContent response = new StoreRespContent();
        ResourceManager mgr = core.getResourceManager();
        Session session = core.getSession();
        try {
            session.beginTransaction();
            mgr.store(storeReq.getReservation());
        
        } catch (RMException ex) {
            OSCARSFaultUtils.handleError( ex, true, session, LOG, event);
        } catch (Exception ex) {
            OSCARSFaultUtils.handleError( ex, false, session, LOG, event);
        }
        session.getTransaction().commit();
        LOG.debug(netLogger.end(event));
        response.setTransactionId(storeReq.getTransactionId ());
        return response;
    }
    /**
     * queryReservation returns all the information about a requested reservation
     * @param authConditions - contains any limits on what the user can query, e.g only
     *     reservations that he owns, or that start and stop at his domain
     * @param queryReservationRequest contains the GlobalReservationId of the reservation to query
     * @return QueryResPeply contains the ResDetails for the reservation
     * @throws OSCARSFaultMessage including "access denied" and "No reservation matches requested gri"
     */
    public QueryResReply
        queryReservation(AuthConditions authConditions, QueryResContent queryReservationRequest)
            throws OSCARSFaultMessage { 

        String event ="queryReservation";
        OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
        
        netLogger.init(moduleName, 
                       queryReservationRequest.getMessageProperties().getGlobalTransactionId());
        netLogger.setGRI(queryReservationRequest.getGlobalReservationId());
        LOG.info(netLogger.start(event));
 
        QueryResReply queryRep = new QueryResReply();
        queryRep.setMessageProperties(queryReservationRequest.getMessageProperties());
        ResDetails resDetails = null;
        ResourceManager mgr = core.getResourceManager();
        Session session = core.getSession();
        try {
            session.beginTransaction();
            resDetails = mgr.query(authConditions,queryReservationRequest.getGlobalReservationId());
            queryRep.setReservationDetails(resDetails);
        } catch (RMException ex) {
            OSCARSFaultUtils.handleError( ex, true, session, LOG, event);
        } catch (Exception ex) {
            OSCARSFaultUtils.handleError( ex, false, session, LOG, event);
        }
        session.getTransaction().commit();
        LOG.info(netLogger.end(event));
        return queryRep;
    }

    /**
     * cancelReservation checks to see if a cancel is allowed for this reservation; removes any
     *     pendingAction that might be in the Scheduler queue, and returns the resDetails of the
     *     requested reservation to the coordinator, so it can inform the PCEs
     * @param authConditions - contains any limits on what the user can cancel, e.g only
     *     reservations that he owns, or that start and stop at his domain
     * @param cancelReservationRequest the GlobalReservationId of the reservation to cancel
     * @return ResDetails - the information about the reservation
     * @throws OSCARSFaultMessage including "access denied" and "No reservation matches requested gri"
     */
    public RMCancelRespContent cancelReservation(AuthConditions authConditions, CancelResContent cancelReservationRequest)
            throws OSCARSFaultMessage { 

        String event ="cancelReservation";
        OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
        netLogger.init(moduleName, 
                       cancelReservationRequest.getMessageProperties().getGlobalTransactionId());
        netLogger.setGRI(cancelReservationRequest.getGlobalReservationId());
        LOG.info(netLogger.start(event ));
        ResDetails resDetails = new ResDetails();
        ResourceManager mgr = core.getResourceManager();
        Session session = core.getSession();
        try {
            session.beginTransaction();
            resDetails = mgr.cancel(authConditions,cancelReservationRequest.getGlobalReservationId());
        
        } catch (RMException ex) {
            OSCARSFaultUtils.handleError( ex, true, session, LOG, event);
        } catch (Exception ex) {
            OSCARSFaultUtils.handleError( ex, false, session, LOG, event);
        }
        session.getTransaction().commit();
        RMCancelRespContent response = new RMCancelRespContent();
        response.setTransactionId(cancelReservationRequest.getMessageProperties().getGlobalTransactionId());
        response.setReservation(resDetails);
        LOG.info(netLogger.end(event ));
        return response;
    }


    /**
     * modifyReservation checks to see if a modify is allowed for this reservation; removes any
     *     pendingAction that might be in the Scheduler queue, and returns the modified resDetails of the
     *     requested reservation to the coordinator, so it can inform the PCEs
     * @param authConditions - contains any limits on what the user can modify, e.g only
     *     reservations that he owns, or that start and stop at his domain
     * @param modifyReservationRequest the GlobalReservationId of the reservation to modify
     * @return ResDetails - the information about the reservation
     * @throws OSCARSFaultMessage including "access denied" and "No reservation matches requested gri"
     */
    public 
        ModifyResReply modifyReservation(AuthConditions authConditions, ModifyResContent modifyReservationRequest)
            throws OSCARSFaultMessage { 

        String event ="modifyReservation";
        OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
        netLogger.init(moduleName, 
                       modifyReservationRequest.getMessageProperties().getGlobalTransactionId());
        netLogger.setGRI(modifyReservationRequest.getGlobalReservationId());
        LOG.info(netLogger.start(event));
        ResDetails resDetails = new ResDetails();
        ModifyResReply response = new ModifyResReply();
        ResourceManager mgr = core.getResourceManager();
        Session session = core.getSession();
        try {
            session.beginTransaction();
            resDetails = mgr.modify(authConditions,modifyReservationRequest.getGlobalReservationId());
        
        } catch (RMException ex) {
            OSCARSFaultUtils.handleError( ex, true, session, LOG, event);
        } catch (Exception ex) {
            OSCARSFaultUtils.handleError( ex, false, session, LOG, event);
        }
        session.getTransaction().commit();
        response.setReservation(resDetails);
        response.setMessageProperties(modifyReservationRequest.getMessageProperties());
        LOG.info(netLogger.end(event));
        return response;
    }

    /**
     * List all the reservations on this IDC that meet the input constraints.
     *
     * @param username String with user's login name
     * @param institution String with the user's institution name
     *
     * @param request the listRequest. Includes an
     *  array of reservation statuses. a list of topology identifiers, a list
     *  of VLAN tags or ranges, start and end times, number of reservations
     *  requested, and offset of first reservation to return.  
     *
     * @return reply ListReply encapsulating server reply.
     * @throws OSCARSFaultMessage
     */
    public ListReply listReservations(AuthConditions authConditions, ListRequest request)
            throws OSCARSFaultMessage { 

        String event = "listReservations";
        // TODO remove the check for messageProperties = null when ConnectivityPCE fixes its call
        String transId = null;
        OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
        if (request.getMessageProperties() != null) {
            transId = request.getMessageProperties().getGlobalTransactionId();
        }
        netLogger.init(moduleName, transId);
        LOG.info(netLogger.start(event));
        ResourceManager mgr = core.getResourceManager();
        Session session = core.getSession();
        ListReply response = new ListReply();
        List<ResDetails> reservations = new ArrayList<ResDetails>();

        Long startTime = request.getStartTime();;
        Long endTime = request.getEndTime();
        List<String> linkIds = request.getLinkId();
        List<VlanTag> inVlanTags = request.getVlanTag();
        List<String> statuses = request.getResStatus();
        List<String> vlanTags = new ArrayList<String>();
        if (linkIds != null && linkIds.size() != 0) {
            for (String linkId: linkIds) {
                linkId.trim();
            }
        }
        if (inVlanTags != null && inVlanTags.size() != 0) {
            for (VlanTag v: inVlanTags) {
                if (v != null) {
                    String s = v.getValue();
                    if (s != null && !s.trim().equals("")) {
                        vlanTags.add(s.trim());
                    }
                }
            }
        }
        if (statuses != null && statuses.size() != 0) {
            for (String s: statuses) {
                s.trim();
            }
        }
        int numRequested = (request.getResRequested() != null ? request.getResRequested() : 100) ;
        int resOffset = (request.getResOffset() !=null ? request.getResOffset() : 0 );
        String description = request.getDescription();

        try {
            session.beginTransaction();
            reservations = mgr.list(authConditions, numRequested, resOffset,
                    statuses,  description,  linkIds, vlanTags, startTime, endTime);
            response.setTotalResults(reservations.size());
            for ( ResDetails res : reservations ) {
                response.getResDetails().add(res);
            }
        } catch (RMException ex) {
            OSCARSFaultUtils.handleError( ex, true, session, LOG, event);
        } catch (Exception ex) {
            OSCARSFaultUtils.handleError( ex, false, session, LOG, event);
        }
        session.getTransaction().commit();
        response.setMessageProperties(request.getMessageProperties());
        LOG.info(netLogger.end(event));
        return response;
    }

    public GetAuditDataRespContent
        getAuditData(GetAuditDataReqContent getAuditDataReq)
            throws OSCARSFaultMessage { 

        String event = "getAuditData";
        OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
        netLogger.init(moduleName, getAuditDataReq.getTransactionId());
        LOG.info(netLogger.start(event));
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

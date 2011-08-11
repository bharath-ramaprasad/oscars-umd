package net.es.oscars.api.http;

import org.apache.log4j.Logger;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.WSSecurityEngineResult;
import org.apache.ws.security.handler.WSHandlerConstants;
import org.apache.ws.security.handler.WSHandlerResult;

import java.util.*;

import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import java.security.Principal;
import java.security.cert.X509Certificate;

import net.es.oscars.api.soap.gen.v06.CancelResContent;
import net.es.oscars.api.soap.gen.v06.CancelResReply;
import net.es.oscars.api.soap.gen.v06.OSCARS;
import net.es.oscars.api.soap.gen.v06.QueryResContent;
import net.es.oscars.api.soap.gen.v06.QueryResReply;
import net.es.oscars.api.soap.gen.v06.ListReply;
import net.es.oscars.api.soap.gen.v06.ListRequest;
import net.es.oscars.api.soap.gen.v06.CreatePathResponseContent;
import net.es.oscars.api.soap.gen.v06.CreatePathContent;
import net.es.oscars.api.soap.gen.v06.GetTopologyContent;
import net.es.oscars.api.soap.gen.v06.GetTopologyResponseContent;
import net.es.oscars.api.soap.gen.v06.ResCreateContent;
import net.es.oscars.api.soap.gen.v06.ModifyResReply;
import net.es.oscars.api.soap.gen.v06.ModifyResContent;
import net.es.oscars.api.soap.gen.v06.RefreshPathContent;
import net.es.oscars.api.soap.gen.v06.RefreshPathResponseContent;
import net.es.oscars.api.soap.gen.v06.TeardownPathResponseContent;
import net.es.oscars.api.soap.gen.v06.TeardownPathContent;
import net.es.oscars.api.soap.gen.v06.CreateReply;
import net.es.oscars.api.soap.gen.v06.InterDomainEventContent;
import net.es.oscars.common.soap.gen.OSCARSFaultMessage;
import net.es.oscars.common.soap.gen.SubjectAttributes;
import net.es.oscars.common.soap.gen.MessagePropertiesType;
import net.es.oscars.logging.ErrSev;
import net.es.oscars.logging.ModuleName;
import net.es.oscars.logging.OSCARSNetLogger;
import net.es.oscars.logging.OSCARSNetLoggerize;
import net.es.oscars.authN.soap.gen.DNType;
import net.es.oscars.authN.soap.gen.VerifyDNReqType;
import net.es.oscars.authN.soap.gen.VerifyReply;
import net.es.oscars.api.common.OSCARSIDC;
import net.es.oscars.utils.soap.OSCARSServiceException;
import net.es.oscars.utils.soap.OSCARSFaultUtils;
import net.es.oscars.utils.sharedConstants.AuthZConstants;
import net.es.oscars.utils.topology.PathTools;
import net.es.oscars.utils.svc.ServiceNames;
import net.es.oscars.utils.clients.CoordClient;
import net.es.oscars.utils.clients.AuthNClient;
import net.es.oscars.utils.validator.DataValidator;
import oasis.names.tc.saml._2_0.assertion.AttributeType;

/**
 * Implementation class for the OSCARService V.06
 * Receives all incoming messages. If MessageProperties in the request message
 * is null, it is assumed that the message is from a user and not a peerIDC. In this
 * case, this class will assign a globalTransactionId, and will set the originator
 * to the loginId of the user that signed the request. This information will be added
 * to the request and then passed to other services and any subsequent IDCs.
 * 
 * @author Eric Pouyoul, Mary Thompson
 *
 */
@OSCARSNetLoggerize(moduleName = ModuleName.API)
@javax.jws.WebService(
        serviceName = ServiceNames.SVC_API,
        portName = "OSCARS",
        targetNamespace = "http://oscars.es.net/OSCARS/06",
        endpointInterface = "net.es.oscars.api.soap.gen.v06.OSCARS")
@javax.xml.ws.BindingType(value = "http://www.w3.org/2003/05/soap/bindings/HTTP/")
public class OSCARSSoapHandler06 implements OSCARS {

    @javax.annotation.Resource
    private WebServiceContext context;
    // Implements requests
    private static final Logger LOG = Logger.getLogger(OSCARSSoapHandler06.class.getName());
    private OSCARSNetLogger netLogger;
    private String moduleName = 
        this.getClass().getAnnotation(OSCARSNetLoggerize.class).moduleName();

    /**
     * 
     * @param createReservation content of the reservation to create
     * @return CreateReply contains the GRI that has been assigned to the reservation, probably
     *          a status of "RECEIVED" or "ACCEPTED" 
     * @throws OSCARSFaultMessage
     */
    public CreateReply createReservation(ResCreateContent createReservation) throws OSCARSFaultMessage    { 
        String event = "createReservation";
        this.netLogger = OSCARSNetLogger.getTlogger();
        MessagePropertiesType msgProps = createReservation.getMessageProperties();
        msgProps = this.updateMessageProperties (msgProps, event, null);
        
        this.netLogger.init(this.moduleName, msgProps.getGlobalTransactionId());
        this.netLogger.setGRI(createReservation.getGlobalReservationId());
        LOG.info(this.netLogger.start(event));
        CreateReply response = null;
        try {
            DataValidator.validate(createReservation, false);
            
            SubjectAttributes subjectAttributes = this.AuthNRequester(msgProps);
            msgProps = this.updateMessageProperties(msgProps, event, subjectAttributes);
            // update messageProperties in case it has been modified
            createReservation.setMessageProperties(msgProps);
            
            CoordClient coordClient = OSCARSIDC.getInstance().getCoordClient();
            // Build the query
            Object[] req = new Object[]{subjectAttributes, createReservation};
            Object[] res = coordClient.invoke("createReservation",req);
            if ((res == null) || (res.length == 0)) {
                OSCARSFaultUtils.handleError (new OSCARSServiceException("No response to createReservation"), true, null, LOG, event);
            }
            response = (CreateReply) res[0];
        } catch (OSCARSServiceException ex) {
            OSCARSFaultUtils.handleError( ex, true, null, LOG, event);
        } catch (Exception ex) {
            OSCARSFaultUtils.handleError( ex, false, null, LOG, event);
        }
        LOG.info(this.netLogger.end(event,response.getGlobalReservationId()));
        return response;
    }

    /**
     * 
     * @param modifyReservation  contains GRI, description and all the reservation Constraints
     * @return the ResDetails for the modified reservation
     * @throws OSCARSFaultMessage
     */
    public ModifyResReply modifyReservation(ModifyResContent modifyReservation) throws OSCARSFaultMessage    { 
        
        String event = "OSCARSSoapHandler06.modifyReservation";
        this.netLogger = OSCARSNetLogger.getTlogger();
        MessagePropertiesType msgProps = modifyReservation.getMessageProperties();
        msgProps = this.updateMessageProperties (msgProps, event, null);

        this.netLogger.init(this.moduleName, msgProps.getGlobalTransactionId());
        this.netLogger.setGRI(modifyReservation.getGlobalReservationId());
        LOG.info(this.netLogger.start(event));
        ModifyResReply response = null;
        try {
            SubjectAttributes subjectAttributes = this.AuthNRequester(msgProps);
            msgProps = this.updateMessageProperties(msgProps, event, subjectAttributes);
            // update messageProperties in case it has been modified
            modifyReservation.setMessageProperties(msgProps);
               
            CoordClient coordClient = OSCARSIDC.getInstance().getCoordClient();
            // Build the query
            Object[] req = new Object[]{subjectAttributes, modifyReservation};
            Object[] res = coordClient.invoke("modifyReservation",req);
            response = (ModifyResReply) res[0];
            
        } catch (OSCARSServiceException ex) {
            OSCARSFaultUtils.handleError( ex, true, null, LOG, event);
        } catch (Exception ex) {
            OSCARSFaultUtils.handleError( ex, false, null, LOG, event);
        }
        LOG.info(this.netLogger.end(event));
        return response;
    }

    /**
     * 
     * @param queryReservation contains GRI of reservation to query
     * @return QueryResReply- details of the reservation
     * @throws OSCARSFaultMessage
     */
    public QueryResReply queryReservation(QueryResContent queryReservation) throws OSCARSFaultMessage    { 
        String event = "OSCARSSoapHandler06.queryReservation";
        this.netLogger = OSCARSNetLogger.getTlogger();
        MessagePropertiesType msgProps = queryReservation.getMessageProperties();
        msgProps = this.updateMessageProperties (msgProps, event, null);

        this.netLogger.init(this.moduleName, msgProps.getGlobalTransactionId());
        this.netLogger.setGRI(queryReservation.getGlobalReservationId());
        LOG.info(this.netLogger.start(event));
        QueryResReply response = null;

        try {
            SubjectAttributes subjectAttributes = this.AuthNRequester(msgProps);
            List<AttributeType> attrs = subjectAttributes.getSubjectAttribute();
            //LOG.debug("QueryRes: number of attributes returned: " + attrs.size());
            msgProps = this.updateMessageProperties(msgProps, event, subjectAttributes);
            // update messageProperties in case it has been modified
            queryReservation.setMessageProperties(msgProps);
               
            CoordClient coordClient = OSCARSIDC.getInstance().getCoordClient();
            // Build the query
            Object[] req = new Object[]{subjectAttributes, queryReservation};
            Object[] res = coordClient.invoke("queryReservation",req);
            response = (QueryResReply) res[0];
        } catch (OSCARSServiceException ex) {
            OSCARSFaultUtils.handleError( ex, true, null, LOG, event);
        } catch (Exception ex) {
            OSCARSFaultUtils.handleError( ex, false, null, LOG, event);
        }
        LOG.info(this.netLogger.end(event));
        return response;
    }

    /**
     * 
     * @param cancelReservation contains GRI of the reservation to cancel
     * @return CancelResReply contains status of the reservation
     * @throws OSCARSFaultMessage
     */
    public CancelResReply cancelReservation(CancelResContent cancelReservation) throws OSCARSFaultMessage    { 
        String event = "OSCARSSoapHandler06.cancelReservation";
        this.netLogger = OSCARSNetLogger.getTlogger();
        MessagePropertiesType msgProps = cancelReservation.getMessageProperties();
        msgProps = this.updateMessageProperties (msgProps, event, null);

        this.netLogger.init(this.moduleName, msgProps.getGlobalTransactionId());
        this.netLogger.setGRI(cancelReservation.getGlobalReservationId());
        LOG.info(this.netLogger.start(event));
        CancelResReply response = null;
        try {
            SubjectAttributes subjectAttributes = this.AuthNRequester(msgProps);
            msgProps = this.updateMessageProperties(msgProps, event, subjectAttributes);
            // update messageProperties in case it has been modified
            cancelReservation.setMessageProperties(msgProps);

            CoordClient coordClient = OSCARSIDC.getInstance().getCoordClient();
            // Build the query
            Object[] req = new Object[]{subjectAttributes, cancelReservation};
            Object[] res = coordClient.invoke("cancelReservation",req);
            response = (CancelResReply) res[0];
        } catch (OSCARSServiceException ex) {
            OSCARSFaultUtils.handleError( ex, true, null, LOG, event);
        } catch (Exception ex) {
            OSCARSFaultUtils.handleError( ex, false, null, LOG, event);
        }
        LOG.info(this.netLogger.end(event));
        return response;
    }

    /**
     * 
     * @param listReservations contains parameters of reservations to list
     *      may include, startTime, endTime, status, linkId, vlan, description
     *      number of reservations to be returned, and the offset to start at.
     * @return list of Reservations  there will be a ResDetails structure for each reservation
     * @throws OSCARSFaultMessage
     */
    public ListReply listReservations(ListRequest listReservations) throws OSCARSFaultMessage    { 
        String event = "OSCARSSoapHandler06.listReservations";
        this.netLogger = OSCARSNetLogger.getTlogger();
        MessagePropertiesType msgProps = listReservations.getMessageProperties();
        msgProps = this.updateMessageProperties (msgProps, event, null);
        this.netLogger.init(this.moduleName, msgProps.getGlobalTransactionId());
        LOG.info(this.netLogger.start(event));
        ListReply response = null;
        try {
            SubjectAttributes subjectAttributes = this.AuthNRequester(msgProps);
            msgProps = this.updateMessageProperties(msgProps, event, subjectAttributes);
            // update messageProperties in case it has been modified
            listReservations.setMessageProperties(msgProps);
            CoordClient coordClient = OSCARSIDC.getInstance().getCoordClient();

            // Build the query
            Object[] req = new Object[]{subjectAttributes, listReservations};
            Object[] res = coordClient.invoke("listReservations",req);
            response = (ListReply) res[0];
            
        } catch (OSCARSServiceException ex) {
            OSCARSFaultUtils.handleError( ex, true, null, LOG, event);
        } catch (Exception ex) {
            OSCARSFaultUtils.handleError( ex, false, null, LOG, event);
        }
        LOG.info(this.netLogger.end(event));
        return response;
    }
    
    /**
     * 
     * @param createPath contains GRI and optionally the token of the path to be setup
     * @return CreatePathResponseContent  contain status and GRI
     * @throws OSCARSFaultMessage
     */
    public CreatePathResponseContent createPath(CreatePathContent createPath) throws OSCARSFaultMessage    { 
        String event = "OSCARSSoapHandler06.createPath";
        this.netLogger = OSCARSNetLogger.getTlogger();
        MessagePropertiesType msgProps = createPath.getMessageProperties();
        msgProps = this.updateMessageProperties (msgProps, event, null);
        this.netLogger.init(this.moduleName, msgProps.getGlobalTransactionId());
        this.netLogger.setGRI(createPath.getGlobalReservationId());
        LOG.info(this.netLogger.start(event));
        CreatePathResponseContent response = null;
        
        try {
            SubjectAttributes subjectAttributes = this.AuthNRequester(msgProps);
            msgProps = this.updateMessageProperties(msgProps, event, subjectAttributes);
            // update messageProperties in case it has been modified
            createPath.setMessageProperties(msgProps);

            CoordClient coordClient = OSCARSIDC.getInstance().getCoordClient();
            // Build the query 
            Object[] req = new Object[]{subjectAttributes, createPath, null};
            Object[] res = coordClient.invoke("createPath",req);
            if (res[0] == null) {
                LOG.error("coordinator returned an empty message");
                throw new OSCARSServiceException("unexpected empty message from coordinator");
            }
            response = (CreatePathResponseContent) res[0];
        } catch (OSCARSServiceException ex) {
            OSCARSFaultUtils.handleError( ex, true, null, LOG, event);
        } catch (Exception ex) {
            OSCARSFaultUtils.handleError( ex, false, null, LOG, event);
        }
        LOG.info(this.netLogger.end(event));
        return response;
    }

    /**
     * 
     * @param refreshPath GRI and optionally token for the reservation 
     * @return RefreshPathResponseContent containing the GRI and status of the reservation
     * @throws OSCARSFaultMessage
     */
    public RefreshPathResponseContent refreshPath(RefreshPathContent refreshPath) throws OSCARSFaultMessage    { 
        String event = "OSCARSSoapHandler06.refreshPath";
        this.netLogger = OSCARSNetLogger.getTlogger();
        MessagePropertiesType msgProps = refreshPath.getMessageProperties();
        msgProps = this.updateMessageProperties (msgProps, event, null);
        this.netLogger.init(this.moduleName, msgProps.getGlobalTransactionId());
        this.netLogger.setGRI(refreshPath.getGlobalReservationId());
        LOG.info(this.netLogger.start(event));
        RefreshPathResponseContent response = null;
        try {
            SubjectAttributes subjectAttributes = this.AuthNRequester(msgProps);
            msgProps = this.updateMessageProperties (msgProps, event, subjectAttributes);
            // update messageProperties in case it has been modified
            refreshPath.setMessageProperties(msgProps);

            CoordClient coordClient = OSCARSIDC.getInstance().getCoordClient();
            // Build the query
            Object[] req = new Object[]{subjectAttributes, refreshPath};
            Object[] res = coordClient.invoke("refreshPath",req);
            response = (RefreshPathResponseContent) res[0];
            
        } catch (OSCARSServiceException ex) {
            OSCARSFaultUtils.handleError( ex, true, null, LOG, event);
        } catch (Exception ex) {
            OSCARSFaultUtils.handleError( ex, false, null, LOG, event);
        }
        LOG.info(this.netLogger.end(event));
        return response;
    }

    /**
     * 
     * @param teardownPath TearDownPathContent contains GRI and optionally token for the reservation to be cancelled
     * @return the TearDownPahtContent
     * @throws OSCARSFaultMessage
     */
    public TeardownPathResponseContent teardownPath(TeardownPathContent teardownPath) throws OSCARSFaultMessage    { 
        String event = "OSCARSSoapHandler06.teardownPath";
        this.netLogger = OSCARSNetLogger.getTlogger();
        MessagePropertiesType msgProps = teardownPath.getMessageProperties();
        msgProps = this.updateMessageProperties (msgProps, event, null);
        this.netLogger.init(this.moduleName, msgProps.getGlobalTransactionId());
        this.netLogger.setGRI(teardownPath.getGlobalReservationId());
        LOG.info(this.netLogger.start(event));
        TeardownPathResponseContent response = null;
        try {
            SubjectAttributes subjectAttributes = this.AuthNRequester(msgProps);
            msgProps = this.updateMessageProperties (msgProps, event, subjectAttributes);
            // update messageProperties in case it has been modified
            teardownPath.setMessageProperties(msgProps);

            CoordClient coordClient = OSCARSIDC.getInstance().getCoordClient();
            // Build the query
            Object[] req = new Object[]{subjectAttributes, teardownPath, null};
            Object[] res = coordClient.invoke("teardownPath",req);
            response = (TeardownPathResponseContent) res[0];
        } catch (OSCARSServiceException ex) {
            OSCARSFaultUtils.handleError( ex, true, null, LOG, event);
        } catch (Exception ex) {
            OSCARSFaultUtils.handleError( ex, false, null, LOG, event);
        }
        LOG.info(this.netLogger.end(event));
        return response;
    }

    /**
     * Accept notification of an event from a peer IDC
     * 
     * @param notify
     * @return void
     */
    public void interDomainEvent(InterDomainEventContent eventContent)  { 
        String event = "OSCARSSoapHandler06.notify";
        this.netLogger = OSCARSNetLogger.getTlogger();
        MessagePropertiesType msgProps = eventContent.getMessageProperties();
        msgProps = this.updateMessageProperties (msgProps, event, null);
        this.netLogger.init(this.moduleName, msgProps.getGlobalTransactionId());
        // TODO this.netLogger.setGR()
        LOG.info(this.netLogger.start(event));
        try {
            SubjectAttributes subjectAttributes = this.AuthNRequester(msgProps);
            msgProps = this.updateMessageProperties (msgProps, event, subjectAttributes);
            // update messageProperties in case it has been modified
            eventContent.setMessageProperties(msgProps);
            
            CoordClient coordClient = OSCARSIDC.getInstance().getCoordClient();
            Object[] req = new Object[]{subjectAttributes,eventContent};
            coordClient.invoke("interDomainEvent",req);
        } catch (OSCARSServiceException ex) {
            LOG.error(this.netLogger.error(event, ErrSev.MAJOR,
                                           "interDomainEvent coordinator failed " + ex.getMessage()));
        } catch (Exception ex) {
            LOG.error(this.netLogger.error(event, ErrSev.MAJOR,
                                           "interDomainEvent coordinator caused exception " + ex.toString()));
            ex.printStackTrace();
        }
        LOG.info(this.netLogger.end(event));
    }

  
   /**
    * 
    * @param getNetworkTopology
    * @return
    * @throws OSCARSFaultMessage
    */
    public GetTopologyResponseContent getNetworkTopology(GetTopologyContent getNetworkTopology) throws OSCARSFaultMessage    { 
        String event = "OSCARSSoapHandler06.getNetworkTopology";
        this.netLogger = OSCARSNetLogger.getTlogger();
        MessagePropertiesType msgProps = getNetworkTopology.getMessageProperties();
        msgProps = this.updateMessageProperties (msgProps, event, null);
        this.netLogger.init(this.moduleName, msgProps.getGlobalTransactionId());
        LOG.info(this.netLogger.start(event));
        GetTopologyResponseContent response = null;
        try {
            SubjectAttributes subjectAttributes = this.AuthNRequester(msgProps);
            msgProps = this.updateMessageProperties (msgProps, event, subjectAttributes);
            // update messageProperties in case it has been modified
            getNetworkTopology.setMessageProperties(msgProps);

            CoordClient coordClient = OSCARSIDC.getInstance().getCoordClient();
            // Build the query
            Object[] req = new Object[]{subjectAttributes, getNetworkTopology};
            Object[] res = coordClient.invoke("getNetTopology",req);
            response = (GetTopologyResponseContent) res[0];
            
        } catch (OSCARSServiceException ex) {
            OSCARSFaultUtils.handleError( ex, true, null, LOG, event);
        } catch (Exception ex) {
            OSCARSFaultUtils.handleError( ex, false, null, LOG, event);
        }
        LOG.info(this.netLogger.end(event));
        return response;
    }

    private HashMap<String, Principal> getSecurityPrincipals() {
        String event = "getSecurityPrincipals";
        LOG.debug(this.netLogger.start(event));
        HashMap<String, Principal> result = new HashMap<String, Principal>();

        try {
            MessageContext inContext = (MessageContext) context.getMessageContext();
            if (inContext == null) {
                LOG.error(this.netLogger.error(event,ErrSev.MAJOR, "message context is NULL"));
                return null;
            }
            Vector results = (Vector) inContext.get(WSHandlerConstants.RECV_RESULTS);
            //HashMap results = (HashMap) inContext.get(MessageContext.HTTP_REQUEST_HEADERS);
            LOG.debug(this.netLogger.getMsg(event,"results size of RECV_RESULTS is " + results.size()));
            //System.out.println("results size of HTTP_REQUEST_HEADERS is " + results.size());
            
            for (int i = 0; results != null && i < results.size(); i++) {
                WSHandlerResult hResult = (WSHandlerResult) results.get(i);
                Vector hResults = hResult.getResults();
                LOG.debug(this.netLogger.getMsg(event,"handler results size is " + hResults.size()));
                for (int j = 0; j < hResults.size(); j++) {
                    WSSecurityEngineResult eResult = (WSSecurityEngineResult) hResults.get(j);
                    // A timestamp action does not have an
                    // associated principal. Only Signature and UsernameToken
                    // actions return a principal.
                    //LOG.debug("TAG_ACTION is " + ((java.lang.Integer) eResult.get(
                    //       WSSecurityEngineResult.TAG_ACTION)).intValue());
                    if ((((java.lang.Integer) eResult.get(
                            WSSecurityEngineResult.TAG_ACTION)).intValue() == WSConstants.SIGN)) {
                        Principal subjectDN = ((X509Certificate) eResult.get(WSSecurityEngineResult.TAG_X509_CERTIFICATE)).getSubjectDN();
                        Principal issuerDN = ((X509Certificate) eResult.get(WSSecurityEngineResult.TAG_X509_CERTIFICATE)).getIssuerDN();
                        result.put("subject", subjectDN);
                        result.put("issuer", issuerDN);
                    }
                    else if ((((java.lang.Integer) eResult.get(
                                WSSecurityEngineResult.TAG_ACTION)).intValue() == WSConstants.UT)) {
                        Principal subjectName = (Principal) eResult.get(
                                WSSecurityEngineResult.TAG_PRINCIPAL);
                        LOG.debug(this.netLogger.getMsg(event,"getSecurityPrincipals.getSecurityInfo, " +
                                "Principal's name from UserToken: " + subjectName));
                        result.put("userTokenName", subjectName);
                    } /*else if (((java.lang.Integer) eResult.get(
                                WSSecurityEngineResult.TAG_ACTION)).intValue() == WSConstants.TS) {
                        Timestamp TS =(Timestamp)eResult.get(WSSecurityEngineResult.TAG_TIMESTAMP);
                        LOG.debug("Timestamp created: " + TS.getCreated());
                        LOG.debug("Timestamp expires: " + TS.getExpires());
                     } */
                }
            }
        } catch (Exception e) {
            LOG.error(this.netLogger.error(event,ErrSev.MAJOR,
                                    "caught.exception: " + e.toString()));
            e.printStackTrace();
            return null;
        }
        LOG.debug(this.netLogger.end(event));
        return result;
    }
    /**
     * AuthNRequester gets the DN from the message and calls the AuthN server to get 
     * the user's attributes if any.
     * 
     * @return a list of the users attributes of the entity that signed the request. 
     *    returns null if there are no attributes
     *    if the user is registered, login_id and institution attributes will be returned.
     * @throws OSCARSServiceException if the user is not registered
     */

      private SubjectAttributes AuthNRequester (MessagePropertiesType msgProps) throws OSCARSServiceException
      {
          String event = "AuthNRequester";
          String userDN = null;
          String issuerDN= null;
          try {
              HashMap<String, Principal> principals = getSecurityPrincipals();
              userDN = principals.get("subject").getName();
              issuerDN = principals.get("issuer").getName();
              LOG.debug(this.netLogger.getMsg(event,"subject: "+ userDN));
              LOG.debug(this.netLogger.getMsg(event,"issuer: " +  issuerDN));
          } catch (Exception ex ){
              LOG.error(this.netLogger.error(event,ErrSev.MAJOR, "caught Exception: " + ex.toString()));
              throw new OSCARSServiceException(ex);
          }
          AuthNClient authNClient = OSCARSIDC.getInstance().getAuthNClient();
          VerifyDNReqType verifyDNReq = new VerifyDNReqType();
          DNType DN = new DNType();
          DN.setSubjectDN(userDN);
          DN.setIssuerDN(issuerDN);
          verifyDNReq.setDN(DN);
          verifyDNReq.setTransactionId(msgProps.getGlobalTransactionId());
          Object[] req = new Object[]{verifyDNReq};
          Object[] res = authNClient.invoke("verifyDN",req);
          VerifyReply reply = (VerifyReply)res[0];
          SubjectAttributes subjectAttrs = reply.getSubjectAttributes();
          return subjectAttrs;
      }
      
      /**
       * updateMessageProperties - if messageProperties was not included in the incomming request,
       *       create one populated by a new uniqueGlobalTransaction and an originator with the LOGIN_ID
       *       attribute of the user who signed the message. Incoming message from end users will not have
       *       messageProperties include. Those from peer IDCs should.
       */
      private  MessagePropertiesType updateMessageProperties (MessagePropertiesType msgProps, String event,SubjectAttributes subjectAttributes) {
          SubjectAttributes originator;
          if (msgProps == null) {
              msgProps = new MessagePropertiesType();
          }
          String transId = msgProps.getGlobalTransactionId();
          if (transId == null || transId.equals("")) {
                 transId = PathTools.getLocalDomainId() + "-API-" + UUID.randomUUID().toString();
                 msgProps.setGlobalTransactionId(transId);
          }        
          originator = msgProps.getOriginator();
          if ((originator == null) && (subjectAttributes != null)) {
              for (AttributeType att: subjectAttributes.getSubjectAttribute()) {
                  if (att.getName().equals(AuthZConstants.LOGIN_ID)) {
                      originator = new SubjectAttributes();
                      originator.getSubjectAttribute().add(att);
                      LOG.debug(this.netLogger.getMsg(event, "setting message Originator to " +
                                                      att.getAttributeValue()));
                  }
              }
              msgProps.setOriginator(originator);
          }
          return msgProps;
      }
}

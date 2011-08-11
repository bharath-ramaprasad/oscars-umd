package net.es.oscars.coord.workers;

import java.net.URL;

import net.es.oscars.logging.OSCARSNetLogger;
import net.es.oscars.utils.config.ContextConfig;
import net.es.oscars.utils.sharedConstants.NotifyRequestTypes;
import net.es.oscars.utils.soap.OSCARSServiceException;
import net.es.oscars.utils.svc.ServiceNames;
import net.es.oscars.api.soap.gen.v06.ResCreateContent;
import net.es.oscars.api.soap.gen.v06.InterDomainEventContent;
import net.es.oscars.common.soap.gen.MessagePropertiesType;
import net.es.oscars.api.soap.gen.v06.ResDetails;
import net.es.oscars.common.soap.gen.SubjectAttributes;
import net.es.oscars.coord.common.Coordinator;
import net.es.oscars.coord.req.CoordRequest;
import net.es.oscars.utils.clients.InternalAPIClient;
import net.es.oscars.coord.workers.InternalAPIWorker;

import org.apache.log4j.Logger;


public class InternalAPIWorker extends ModuleWorker {

    private static Logger        LOG             = Logger.getLogger(InternalAPIWorker.class.getName());
    private InternalAPIClient    internalClient  = null;
    private URL                  apiHost         = null;
    
    private static InternalAPIWorker instance;
    
    public static InternalAPIWorker getInstance() throws OSCARSServiceException {
        if (instance == null) {
            instance = new InternalAPIWorker();
        }
        return instance;
    }
    
    private InternalAPIWorker () throws OSCARSServiceException {
        LOG =  Logger.getLogger(InternalAPIWorker.class.getName());
        this.reconnect();
    }
    
    
    public InternalAPIClient getClient() {
        return this.internalClient;
    }

    public void reconnect() throws OSCARSServiceException {
        try {
            OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
            ContextConfig cc = ContextConfig.getInstance();
            this.apiHost = Coordinator.getInstance().getInternalApiHost();
            URL wsdl = cc.getWSDLPath(ServiceNames.SVC_API_INTERNAL,null);
            LOG.debug (netLogger.getMsg("InternalAPIWorker.reconnect","API host= " + this.apiHost + 
                                    " WSDL= " + wsdl));
            this.internalClient = InternalAPIClient.getClient(this.apiHost,wsdl);
        } catch (Exception e) {
            throw new OSCARSServiceException (e);
        }
    }
    
    @SuppressWarnings("unchecked")
    public void sendEventContent (CoordRequest request,
                                  ResDetails resDetails,
                                  String requestType,
                                  String destDomain) throws OSCARSServiceException {
        
        InterDomainEventContent event = new InterDomainEventContent();
        
        if (requestType.equals("pceCreate")) {
            event.setType(NotifyRequestTypes.RESV_CREATE_CONFIRMED);
        } else if ( requestType.equals("pceCommit")) {
            event.setType(NotifyRequestTypes.RESV_COMMIT_CONFIRMED);
        } else if ( requestType.equals("pceCancel")) {
            event.setType(NotifyRequestTypes.RESV_CANCEL_CONFIRMED);
        } else if ( requestType.equals("pceModify")) {
            event.setType(NotifyRequestTypes.RESV_MODIFY_CONFIRMED);
        }
        //event.setUserLogin((String) request.getAttribute(CoordRequest.LOGIN_ATTRIBUTE));
        event.setResDetails(resDetails);
        event.setErrorCode("ok");      
        event.setMessageProperties(request.getMessageProperties());

        Object[] req = new Object[] {event, destDomain};
        this.getClient().invoke("interDomainEvent", req);
        LOG.info ("sendEventContent:end " + request.getName());
    }
    
    @SuppressWarnings("unchecked")
    public void sendResCreateContent (CoordRequest request,
                                      ResDetails resDetails,
                                      String destDomain) throws OSCARSServiceException {
        
        ResCreateContent query = new ResCreateContent();
        String desc = (String) request.getAttribute(CoordRequest.DESCRIPTION_ATTRIBUTE);
        query.setDescription (desc);
        query.setGlobalReservationId(request.getGRI());
     
        
        query.setUserRequestConstraint(resDetails.getUserRequestConstraint());
        query.setReservedConstraint(resDetails.getReservedConstraint());
        if (resDetails.getOptionalConstraint() != null) {
            // Optional constraints may not be defined.
            query.getOptionalConstraint().addAll(resDetails.getOptionalConstraint());
        }
        
        MessagePropertiesType messageProperties = new MessagePropertiesType ();
        messageProperties.setOriginator((SubjectAttributes) request.getAttribute(CoordRequest.SUBJECT_ATTRIBUTES)); 
        
        query.setMessageProperties(messageProperties);
        
        Object[] req = new Object[] {query, destDomain};
        this.getClient().invoke("createReservation", req);
        LOG.info ("sendResCreateContent:end " + request.getName());
    }

}

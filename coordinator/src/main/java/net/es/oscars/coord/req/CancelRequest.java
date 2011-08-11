package net.es.oscars.coord.req;

import org.apache.log4j.Logger;

import net.es.oscars.api.soap.gen.v06.CancelResContent;
import net.es.oscars.api.soap.gen.v06.CancelResReply;
import net.es.oscars.api.soap.gen.v06.GlobalReservationId;
import net.es.oscars.api.soap.gen.v06.ResDetails;
import net.es.oscars.common.soap.gen.AuthConditions;
import net.es.oscars.coord.actions.CoordAction;
import net.es.oscars.coord.actions.PSSTeardownPathAction;
import net.es.oscars.coord.actions.RMUpdateStatusAction;
import net.es.oscars.coord.runtimepce.PCEData;
import net.es.oscars.coord.runtimepce.PCERuntimeAction;
import net.es.oscars.coord.workers.RMWorker;
import net.es.oscars.utils.clients.RMClient;
import net.es.oscars.logging.ErrSev;
import net.es.oscars.logging.OSCARSNetLogger;
import net.es.oscars.pss.soap.gen.TeardownReqContent;
import net.es.oscars.resourceManager.soap.gen.RMCancelRespContent;
import net.es.oscars.utils.soap.OSCARSServiceException;
import net.es.oscars.utils.sharedConstants.StateEngineValues;

public class CancelRequest extends CoordRequest <CancelResContent, CancelResReply>{
    
    private static final long       serialVersionUID  = 1L;
    private static final Logger     LOG = Logger.getLogger(CancelRequest.class.getName());
    
    public CancelRequest(String name,
                         AuthConditions authConditions,
                         CancelResContent cancelResReq) {
        
        super (name,cancelResReq.getMessageProperties().getGlobalTransactionId(),
               cancelResReq.getGlobalReservationId(),authConditions);
        this.setRequestData(cancelResReq);
        this.setMessageProperties(cancelResReq.getMessageProperties());
    }
    /**
     * cancelReservation  cancels a reservation
     *   if the reservation is currently active, a teardown will be initiated.
     *   All the PCEs will be notified that the reservation is canceled, in case they need to
     *   release co-scheduled resources.
     * @param authConditions set in constructor
     * @param gri set in constructor
     */
    public void execute()  {
        String method = "CancelReservationRequest.executee";
        OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
        netLogger.init(CoordRequest.moduleName,this.getTransactionId()); 
        netLogger.setGRI(this.getGRI());
        LOG.debug(netLogger.start(method));

        try {
            // call resourceManager to do its part of cancel and get resDetails for the reservation
            RMWorker rmWorker = RMWorker.getInstance();
            RMClient rmClient = rmWorker.getRMClient();
 
            CancelResContent cancelResReq = this.getRequestData();
            Object[] req = {this.getAuthConditions(), cancelResReq};
            Object[] res = rmClient.invoke("cancelReservation",req);
     
            if ((res == null) || (res[0] == null)) {
                throw new OSCARSServiceException ("No response from ResourceManager");
            }
            RMCancelRespContent response = (RMCancelRespContent) res [0];
            ResDetails resDetails = response.getReservation();
            String state = resDetails.getStatus();
            this.setAttribute(CoordRequest.DESCRIPTION_ATTRIBUTE,resDetails.getDescription());
            this.setAttribute(CoordRequest.STATE_ATTRIBUTE, state);
            LOG.debug(netLogger.getMsg(method,"received cancel for reservation in state " + state));
            
            if (!state.equals(StateEngineValues.CANCELLED)) {
                if (state.equals(StateEngineValues.ACTIVE) ||
                    state.equals(StateEngineValues.INSETUP)) {
                    // Create and execute a PSSTeaddownPathAction
                    TeardownReqContent pssReq = new TeardownReqContent();
                    pssReq.setReservation(resDetails);
                    // TODO
                    //pssReq.setMessageProperties(this.getMessageProperties());
                    PSSTeardownPathAction pathTeardownAction = new PSSTeardownPathAction (this.getName(),
                                                                                          null, pssReq);
                    pathTeardownAction.execute();
                    if (pathTeardownAction.getState() == CoordAction.State.FAILED) {
                        throw pathTeardownAction.getException();
                    }    
               }
                // TODO if state equals INPATHCALCULATION or INCOMMIT may need to abort a PCERuntimeAction
                
                // inform all PCEs of the cancel action
                PCERuntimeAction pceRuntimeAction = new PCERuntimeAction (this.getName() + "-Cancel-PCERuntimeAction",
                                                                          this,
                                                                          null,
                                                                          this.getTransactionId(),
                                                                          "pceCancel");

                PCEData pceData = new PCEData(resDetails.getUserRequestConstraint(),
                                              resDetails.getReservedConstraint(),
                                              resDetails.getOptionalConstraint(),
                                              null);

                pceRuntimeAction.setRequestData(pceData);
                this.add(pceRuntimeAction);
                
            }
            this.executed();
            
        } catch (OSCARSServiceException ex) {
            LOG.warn(netLogger.error(method, ErrSev.MINOR, "caught OSCARSServiceException"  + ex.getMessage()));
            this.notifyRMFailure(ex.getMessage());
            this.fail(new OSCARSServiceException(method + " caught exception " +
                    ex.getMessage(), ex.getType()), StateEngineValues.FAILED);
        } catch (Exception ex ) {
            String message = ex.getMessage();
            if (message == null ) {
                message = ex.toString();
            } 
            LOG.warn(netLogger.error(method, ErrSev.MINOR,"Exception message is " + message));
            this.notifyRMFailure(message);
            this.fail(new OSCARSServiceException(method + " caught exception " +
                    message, "system"), StateEngineValues.FAILED);
        }
        LOG.debug(netLogger.end(method));
    }
    
    /**
     * notify the resourceManager that the cancel has failed
     * @param String message contains message of exception that was caught
     */
    private void notifyRMFailure(String message) {
        
        RMUpdateStatusAction rmAction = new RMUpdateStatusAction(this.getName() + "-RMUpdateStatusAction",
                                                                 (CoordRequest)this,
                                                                 this.getGRI(), 
                                                                 StateEngineValues.FAILED+":INCANCEL "+message);
        rmAction.execute();
    }

}
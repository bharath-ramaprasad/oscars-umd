package net.es.oscars.coord.req;

import org.apache.log4j.Logger;

import net.es.oscars.logging.ErrSev;
import net.es.oscars.logging.OSCARSNetLogger;
import net.es.oscars.utils.soap.OSCARSServiceException;
import net.es.oscars.utils.sharedConstants.NotifyRequestTypes;
import net.es.oscars.utils.sharedConstants.StateEngineValues;

import net.es.oscars.api.soap.gen.v06.InterDomainEventContent;
import net.es.oscars.api.soap.gen.v06.ResDetails;
import net.es.oscars.coord.actions.RMStoreAction;
import net.es.oscars.coord.actions.CommittedEventAction;
import net.es.oscars.coord.actions.CoordAction;
import net.es.oscars.coord.req.CoordRequest;

public class InterDomainEventRequest extends CoordRequest <InterDomainEventContent,Object >{

    private static final long       serialVersionUID  = 1L;

    private static final Logger     LOG = Logger.getLogger(InterDomainEventRequest.class.getName());

    public InterDomainEventRequest(String name) {
        super (name);
        this.setCoordRequest(this);
    }

    public InterDomainEventRequest(String name, String transId, String gri ) {
        super (name, transId, gri);
        this.setCoordRequest(this);
    }

    public void setRequestData (InterDomainEventContent params) {
        // Set input parameter using base class method
        super.setRequestData (params);
    }

    public void execute()  {

        String method = "InterDomainEventRequest.execute";
        OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
        netLogger.init(CoordRequest.moduleName,this.getTransactionId()); 
        netLogger.setGRI(this.getGRI());
        LOG.debug(netLogger.start(method));

        try {
            InterDomainEventContent eventContent = this.getRequestData();
            if (eventContent == null) {
                throw new OSCARSServiceException (method + " Null InterDomainEventContent", "system");
            }
            ResDetails resDetails = eventContent.getResDetails();
            if (resDetails == null) {
                throw new OSCARSServiceException (method + " No ResDetails", "system");
            }

            
            /*  this code is used by eomplsPSS via pss/CoordNotify */
            if (eventContent.getType().equals(NotifyRequestTypes.PSS_SETUP_CONFIRMED)) {

                // Change the status to ACTIVE and store it
                resDetails.setStatus(StateEngineValues.ACTIVE);

                // Create, set and invoke the RMStoreAction
                RMStoreAction rmStoreAction = new RMStoreAction(this.getName() + "-RMStoreAction", 
                        this.getCoordRequest(), resDetails);

                rmStoreAction.execute();
                if (rmStoreAction.getState() == CoordAction.State.FAILED){
                    throw rmStoreAction.getException();
                }
                this.executed();

            }  else if (eventContent.getType().equals(NotifyRequestTypes.PSS_TEARDOWN_CONFIRMED)) {

                // Change the status to FINISHED and store it
                resDetails.setStatus(StateEngineValues.FINISHED);

                // Create, set and invoke the RMStoreAction
                // We may just need to update the status here -mrt
                RMStoreAction rmStoreAction = new RMStoreAction(this.getName() + "-RMStoreAction", this.getCoordRequest(), resDetails);

                rmStoreAction.execute();
                if (rmStoreAction.getState() == CoordAction.State.FAILED){
                    throw rmStoreAction.getException();
                }
                this.executed();

            } else if ((eventContent.getType().equals(NotifyRequestTypes.PSS_SETUP_FAILED)) ||
                      (eventContent.getType().equals(NotifyRequestTypes.PSS_TEARDOWN_FAILED))) {

                // Change the status to FAIL and store it
                // We may just need to update the status here -mrt
                resDetails.setStatus(StateEngineValues.FAILED);

                // Create, set and invoke the RMStoreAction
                RMStoreAction rmStoreAction = new RMStoreAction(this.getName() + "-RMStoreAction", this.getCoordRequest(), resDetails);

                rmStoreAction.execute();
                if (rmStoreAction.getState() == CoordAction.State.FAILED){
                    throw rmStoreAction.getException();
                }
                this.executed();
            }  else if (eventContent.getType().equals(NotifyRequestTypes.RESV_COMMIT_CONFIRMED)) {

                CommittedEventAction action = new CommittedEventAction(this.getName() + "-CommittedEventAction", this.getCoordRequest(), resDetails);
                this.add (action);
                action.execute();
                if (action.getState() == CoordAction.State.FAILED){
                    throw action.getException();
                }
                this.executed();
            }
 
            this.executed();
        } catch (Exception ex){
            this.fail2 (ex);
            LOG.warn(netLogger.error(method, ErrSev.MINOR, " failed with exception " + ex.getMessage()));
        }
        LOG.debug(netLogger.end(method));
    }

    public void cancel () {

    }


}

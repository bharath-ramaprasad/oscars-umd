package net.es.oscars.coord.req;

import org.apache.log4j.Logger;

import net.es.oscars.logging.ErrSev;
import net.es.oscars.logging.OSCARSNetLogger;
import net.es.oscars.utils.soap.OSCARSServiceException;
import net.es.oscars.utils.sharedConstants.StateEngineValues;
import net.es.oscars.utils.sharedConstants.PSSConstants;

import net.es.oscars.api.soap.gen.v06.ResDetails;
import net.es.oscars.api.soap.gen.v06.CreatePathResponseContent;
import net.es.oscars.coord.actions.RMStoreAction;
import net.es.oscars.coord.actions.RMUpdateStatusAction;
import net.es.oscars.coord.actions.CoordAction;
import net.es.oscars.coord.runtimepce.PCEData;
import net.es.oscars.coord.runtimepce.PCERuntimeAction;
import net.es.oscars.coord.req.CoordRequest;
import net.es.oscars.coord.req.CreatePathRequest;
import net.es.oscars.coord.soap.gen.PSSReplyContent;

/** class to handle a reply message from PSS setupPath, teardownPath, modifyPath or status
 *  Currently only setup and teardown are implemented
 * @author mrt
 *
 */
public class PSSReplyRequest extends CoordRequest <PSSReplyContent,Object >{

    private static final long       serialVersionUID  = 1L;
    private static String           requestType = null;         // is reply from setupPath or teardownPath

    private static final Logger     LOG = Logger.getLogger(PSSReplyRequest.class.getName());

    public PSSReplyRequest(String name) {
        super (name);
    }

    public PSSReplyRequest(String name, String gri, PSSReplyContent pssReply) {
        super (name, pssReply.getId(), gri);
        requestType= pssReply.getReplyType();
        this.setCoordRequest(this);
    }

    public void setRequestData (PSSReplyContent params) {
        // Set input parameter using base class method
        super.setRequestData (params);
    }

    /**
     * calls ResourceManger to update the state 
     */
    public void execute()  {

        String method = "PSSReplyRequest.execute";
        OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
        netLogger.init(CoordRequest.moduleName,this.getTransactionId()); 
        netLogger.setGRI(this.getGRI());
        LOG.debug(netLogger.start(method));

        try {
            PSSReplyContent pssReply = this.getRequestData();
            if (pssReply == null) {
                throw new OSCARSServiceException (method + " Null PSSReplyContent", "system");
            }
            String status = null;
            if (pssReply.getStatus().equals(PSSConstants.SUCCESS)) {
                if (pssReply.getReplyType().equals(PSSConstants.PSS_SETUP)) {
                    status = StateEngineValues.ACTIVE;
                } else if (pssReply.getReplyType().equals(PSSConstants.PSS_TEARDOWN)) {
                    status = StateEngineValues.RESERVED;
                }
            } else {
                status = StateEngineValues.FAILED;
            }
            String gri = pssReply.getGlobalReservationId();
            // Update reservation status
            if (status != null) {
                // Create, set and invoke the RMSUpdateStatusAction
                RMUpdateStatusAction rmUpdate = new RMUpdateStatusAction(this.getName() + "-RMUpdateStatusAction", 
                                                                         this,
                                                                         gri,
                                                                         status);
                rmUpdate.execute();
                if (rmUpdate.getState() == CoordAction.State.FAILED){
                    throw rmUpdate.getException();
                }
            }
            
            this.executed();
        } catch (Exception ex){
            this.fail (ex,StateEngineValues.FAILED);
            LOG.warn(netLogger.error(method, ErrSev.MINOR," failed with exception " + ex.getMessage()));
        }
        LOG.debug(netLogger.end(method));
    }

    public void cancel () {

    }


}

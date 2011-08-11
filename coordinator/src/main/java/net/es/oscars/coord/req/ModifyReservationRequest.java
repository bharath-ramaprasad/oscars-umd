package net.es.oscars.coord.req;

import org.apache.log4j.Logger;

import net.es.oscars.api.soap.gen.v06.GlobalReservationId;
import net.es.oscars.api.soap.gen.v06.ModifyResContent;
import net.es.oscars.api.soap.gen.v06.ModifyResReply;
import net.es.oscars.api.soap.gen.v06.ResCreateContent;
import net.es.oscars.api.soap.gen.v06.ResDetails;
import net.es.oscars.common.soap.gen.AuthConditions;
import net.es.oscars.coord.actions.CoordAction;
import net.es.oscars.coord.actions.RMGenerateGRIAction;
import net.es.oscars.coord.actions.RMStoreAction;
import net.es.oscars.coord.req.QueryReservationRequest;
import net.es.oscars.coord.runtimepce.PCEData;
import net.es.oscars.coord.runtimepce.PCERuntimeAction;
import net.es.oscars.coord.workers.RMWorker;
import net.es.oscars.logging.ErrSev;
import net.es.oscars.logging.OSCARSNetLogger;
import net.es.oscars.resourceManager.soap.gen.RMCancelRespContent;
import net.es.oscars.utils.clients.RMClient;
import net.es.oscars.utils.sharedConstants.StateEngineValues;
import net.es.oscars.utils.soap.OSCARSServiceException;

/**
 * A coordRequest to handle ModifyReservation requests. Input parameters are placed in
 * setRequestData of type ModifyResContent. SetResultData of type ModifyResReply is
 * not actually used.
 * 
 * @author lomax
 *
 * @param <ResModifyContent>  Class containing the input data for the action
 * @param <ModifyReservationResults>  Class for returning the results of the action (not used)
 */
public class ModifyReservationRequest extends CoordRequest <ModifyResContent,ModifyResReply >{
    
    private static final long       serialVersionUID  = 1L;
    private static final Logger LOG = Logger.getLogger(ModifyReservationRequest.class.getName());
    //private GlobalReservationId GRI;
    // Seems like this should be in the parent CoordRequest except there seems to be some magic with GRIs there
    // that I don't understand -mrt


    public ModifyReservationRequest(String name, 
                                    String gri,
                                    String loginName, 
                                    AuthConditions authConds, 
                                    ModifyResContent modifyResvReq) {
        super (name, modifyResvReq.getMessageProperties().getGlobalTransactionId(), gri, authConds);
        this.setRequestData(loginName, modifyResvReq);
    }
    
    public void setRequestData (String loginName, ModifyResContent modifyResvReq) {
        // Set input parameter using base class method
        super.setRequestData (modifyResvReq);
        // Add the reservation description to the attribute list of the request.
        this.setAttribute(CoordRequest.DESCRIPTION_ATTRIBUTE, modifyResvReq.getDescription());
        // Add login attribute
        this.setAttribute(CoordRequest.LOGIN_ATTRIBUTE, loginName);
    }
 
    /**
     * Called by CoordImpl to start the execution of a ModifyReservation request. 
     * Synchronous parts of the processing are done and a PCERuntime action is
     * created to start the path calculation. If it returns without throwing an exception
     * the new GRI has been placed in this CoordRequest object
     * @params were set by the constructor or by setRequestData
     * @return sets the new GRI into this request object
     * @throws OSCARSServiceException - nothing is expected, but could get runtimeError
     */
    public void execute()  {

        ModifyResContent  request = this.getRequestData();
        ModifyResReply reply = new ModifyResReply();
        String method = "ModifyReservationRequest.execute";
        OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
        netLogger.init(CoordRequest.moduleName,this.getTransactionId()); 
        netLogger.setGRI(this.getGRI());
        LOG.debug(netLogger.start(method));

        try {
            /* Call resource Manager to do its part of modifyReservation and return the resDetails
             * for the reservation. It will set the new state to INMODIFY
             * the authConditions for modify are passed through the resourceManager which will
             * check for permitted domains and permitted login conditions and
             * only return the reservation details if any such conditions are met.
             */
            RMWorker rmWorker = RMWorker.getInstance();
            RMClient rmClient = rmWorker.getRMClient();
            Object [] req = new Object[]{this.getAuthConditions(),request};
            Object[] res = rmClient.invoke("modifyReservation",req);

            if ((res == null) || (res[0] == null)) {
                throw new OSCARSServiceException ("No response from ResourceManager");
            }
            ModifyResReply response = (ModifyResReply) res [0];
            ResDetails resDetails = response.getReservation();
            String state = resDetails.getStatus();
            this.setAttribute(CoordRequest.DESCRIPTION_ATTRIBUTE,resDetails.getDescription());
            // this is the previous state, not INMODIFY
            this.setAttribute(CoordRequest.STATE_ATTRIBUTE, state);
            this.setResultData(response);
            LOG.debug(netLogger.getMsg(method,"received cancel for reservation in state " + state));

            PCERuntimeAction pceRuntimeAction = new PCERuntimeAction (this.getName() + "-Modify-PCERuntimeAction",
                                                                      this,
                                                                      null,
                                                                      this.getTransactionId(),
                                                                      "pceModify");

            //ResCreateContent resCreateContent = this.getRequestData().getResCreateContent();

            PCEData pceData = new PCEData(request.getUserRequestConstraint(),
                                          request.getReservedConstraint(),
                                          request.getOptionalConstraint(),
                                          null);

            pceRuntimeAction.setRequestData(pceData);

            this.add(pceRuntimeAction);
            this.executed();
        } catch (OSCARSServiceException ex ) {
            LOG.debug(netLogger.error(method, ErrSev.MINOR, "caught OSCARSServiceException "+ ex.getMessage()));
            this.fail(new OSCARSServiceException(method + "caught Exception: " +ex.getMessage(), "user"),
                    StateEngineValues.FAILED);           
        } catch (Exception ex ) {
            String message = ex.getMessage();
            if (message == null ) {
                message = ex.toString();
            }
            LOG.debug(netLogger.error(method, ErrSev.MINOR, "caught Exception "+ message));
            this.fail(new OSCARSServiceException(method + "caught Exception: " +message, "system"),StateEngineValues.FAILED);
        }
        LOG.debug(netLogger.end(method));
        return;
    }
}

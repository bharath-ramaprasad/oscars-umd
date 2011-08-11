package net.es.oscars.coord.req;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import net.es.oscars.api.soap.gen.v06.GlobalReservationId;
import net.es.oscars.api.soap.gen.v06.CreatePathContent;
import net.es.oscars.api.soap.gen.v06.CreatePathResponseContent;
import net.es.oscars.api.soap.gen.v06.ResDetails;
import net.es.oscars.common.soap.gen.AuthConditions;
import net.es.oscars.common.soap.gen.MessagePropertiesType;
import net.es.oscars.coord.req.QueryReservationRequest;
import net.es.oscars.coord.actions.CoordAction;
import net.es.oscars.coord.actions.PSSCreatePathAction;
import net.es.oscars.coord.actions.RMUpdateStatusAction;
import net.es.oscars.coord.common.Coordinator;
import net.es.oscars.logging.ErrSev;
import net.es.oscars.logging.OSCARSNetLogger;
import net.es.oscars.pss.soap.gen.SetupReqContent;
import net.es.oscars.utils.soap.OSCARSServiceException;
import net.es.oscars.utils.sharedConstants.StateEngineValues;
/**
 * CreatePathRequest calls the PSS server to instantiate a path.
 * parameters are set by the constructor. 
 * 
 * @author lomax,mrt
 *
 */
public class CreatePathRequest extends CoordRequest <ResDetails,CreatePathResponseContent >{
    
    private static final long       serialVersionUID  = 1L;
    private String                  token             ="";
    private static final Logger     LOG = Logger.getLogger(CreatePathRequest.class.getName());
    
    /**
     * 
     * @param name unique name of coordRequest"CreatePath-" + gri, 
     * @param token an optional token allowing path setup of this reservation  - not implemented
     * @param resDetails -contains all the information the pss needs to setup a path
     */
    public CreatePathRequest(String name,
                             String token,
                             MessagePropertiesType msgProps,
                             ResDetails resDetails) {
        super (name, msgProps.getGlobalTransactionId(),resDetails.getGlobalReservationId());
        this.setRequestData(resDetails);
        this.token             = token;
    }
    /**
     *  The reservation state is set to INSETUP, and
     *  pathCreateAction is executed to call the PSS
     */
    public void execute()  {
        String method = "CreatePathRequest.execute";
        OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
        netLogger.init(CoordRequest.moduleName,this.getTransactionId()); 
        netLogger.setGRI(this.getGRI());
        LOG.debug(netLogger.start(method));

        ResDetails resDetails = this.getRequestData();
        try {
            // Create, set and invoke the RMUpdateStatusAction
            // make all actions that are directly executed top-level actions
            RMUpdateStatusAction rmAction = new RMUpdateStatusAction(this.getName() + "-RMUpdateAction",
                            this, this.getGRI(), StateEngineValues.INSETUP);
            rmAction.execute();
            if (rmAction.getState() ==  CoordAction.State.FAILED) {
                    throw rmAction.getException();
            }
            // Create and execute a PSSCreatePathAction
            SetupReqContent pssReq = new SetupReqContent();
            Coordinator coordinator = Coordinator.getInstance();
            pssReq.setId(this.getTransactionId());
            pssReq.setCallbackEndpoint(coordinator.getCallbackEndpoint());
            pssReq.setReservation(resDetails);
            PSSCreatePathAction pathCreateAction = new PSSCreatePathAction (this.getName() + "-PSSCreatePathAction", 
                                                                            null,
                                                                            pssReq);
            pathCreateAction.execute();
            if (pathCreateAction.getState() == CoordAction.State.FAILED) {
                LOG.error(netLogger.error(method, ErrSev.MINOR,"pathCreateAction state = FAILED"));
                throw pathCreateAction.getException();
            }
            this.executed();
        } catch (Exception ex ) {
            String type = "system";
            String message = ex.getMessage();
            if (message == null ) {
                message = ex.toString();
            }
            LOG.error(netLogger.error(method,ErrSev.MINOR, "caught exception " + message));
            try { // see if the exception caught is an OSCARSServiceException with a getType method.
                Method getType = ex.getClass().getMethod("getType",null);
                type = (String) getType.invoke(ex,new Object []{});
            } catch (Exception e) {
            }
            this.fail(new OSCARSServiceException(method + "caught exception " + message, type), StateEngineValues.FAILED);
            LOG.debug(netLogger.error(method, ErrSev.MINOR, " caught exception " + message));
        };
        LOG.debug(netLogger.end(method));
    }
}
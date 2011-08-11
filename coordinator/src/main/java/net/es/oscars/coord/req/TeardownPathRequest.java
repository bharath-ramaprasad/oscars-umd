package net.es.oscars.coord.req;

import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.es.oscars.api.soap.gen.v06.GlobalReservationId;
import net.es.oscars.api.soap.gen.v06.ResDetails;
import net.es.oscars.api.soap.gen.v06.TeardownPathContent;
import net.es.oscars.api.soap.gen.v06.TeardownPathResponseContent;
import net.es.oscars.common.soap.gen.AuthConditions;
import net.es.oscars.common.soap.gen.MessagePropertiesType;
import net.es.oscars.coord.actions.PSSTeardownPathAction;
import net.es.oscars.coord.actions.RMUpdateStatusAction;
import net.es.oscars.coord.actions.CoordAction;
import net.es.oscars.coord.common.Coordinator;
import net.es.oscars.logging.ErrSev;
import net.es.oscars.logging.OSCARSNetLogger;
import net.es.oscars.pss.soap.gen.TeardownReqContent;
import net.es.oscars.utils.soap.OSCARSServiceException;
import net.es.oscars.utils.sharedConstants.StateEngineValues;
/**
 * TeardownPathRequest calls the PSS server to teardown a circuit.
 * parameters are set by the constructor. 
 * 
 * @author lomax, mrt
 *
 */
public class TeardownPathRequest extends CoordRequest <ResDetails,TeardownPathResponseContent >{

    private static final long       serialVersionUID  = 1L;
    private String                  token             = "";
    private static final Logger     LOG = Logger.getLogger(TeardownPathRequest.class.getName());
 
    /**
     * constructor 
     * @param name unique name of the request: TeardownPath-" + gri
     * @param token an optional token allowing path setup of this reservation  - not implemented
     * @param resDetails -contains all the information the pss needs to teardown a path
     */
    public TeardownPathRequest(String name,
                               String token,
                               MessagePropertiesType msgProps,
                               ResDetails resDetails) {
        super (name, msgProps.getGlobalTransactionId(), resDetails.getGlobalReservationId());
        this.setRequestData(resDetails);
        this.token             = token;
    }
    
    /**
     * The reservation state is set to INTEARDOWN, and
     * then pathCreateAction is executed to call the PSS
     */
    public void execute() {
        String method = "TeardownPathRequest.execute";
        OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
        netLogger.init(CoordRequest.moduleName,this.getTransactionId()); 
        netLogger.setGRI(this.getGRI());
        LOG.debug(netLogger.start(method));

        ResDetails resDetails = this.getRequestData();
        try {
            // Create, set and invoke the RMUpdateStatusAction
            // Set all actions that are directly executed to be a top-level action
            RMUpdateStatusAction rmUpdateAction = new RMUpdateStatusAction(this.getName() + "-RMUpdateStatusAction", 
                                                                           this, 
                                                                           this.getGRI(),
                                                                           StateEngineValues.INTEARDOWN);
            rmUpdateAction.execute();
            if (rmUpdateAction.getState() == CoordAction.State.FAILED) {
                throw rmUpdateAction.getException();
            }

            // Create and execute a PSSTeaddownPathAction
            TeardownReqContent pssReq = new TeardownReqContent();
            Coordinator coordinator = Coordinator.getInstance();
            pssReq.setId(this.getTransactionId());
            pssReq.setCallbackEndpoint(coordinator.getCallbackEndpoint());
            pssReq.setReservation(resDetails);
            PSSTeardownPathAction pathTeardownAction = new PSSTeardownPathAction (this.getName(),
                                                                                  this, pssReq);
            pathTeardownAction.execute();
            if (pathTeardownAction.getState() == CoordAction.State.FAILED) {
                throw pathTeardownAction.getException();
            }          
            this.executed();
        } catch (Exception ex) {
            String type = "system";
            String message = ex.getMessage();
            if (message == null){
                message = ex.toString();
            }
            try { // see if the exception caught is an OSCARSServiceException with a getType method.
                Method getType = ex.getClass().getMethod("getType",null);
                type = (String) getType.invoke(ex,new Object []{});
            } catch (Exception e) {
            }
            this.fail(new OSCARSServiceException(method +" caught exception " + message, type),StateEngineValues.FAILED);
            LOG.debug(netLogger.error(method, ErrSev.MINOR," caught exception " + message));
        }
        LOG.debug(netLogger.end(method));
    }
}

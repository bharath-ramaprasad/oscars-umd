package net.es.oscars.coord.actions;

import org.apache.log4j.Logger;

import net.es.oscars.coord.req.CoordRequest;
import net.es.oscars.coord.actions.CoordAction;
import net.es.oscars.coord.common.CoordImpl;

import net.es.oscars.coord.workers.AuthZWorker;
import net.es.oscars.logging.ErrSev;
import net.es.oscars.logging.ModuleName;
import net.es.oscars.logging.OSCARSNetLogger;
import net.es.oscars.utils.soap.OSCARSServiceException;

import net.es.oscars.authZ.soap.gen.CheckAccessParams;
import net.es.oscars.authZ.soap.gen.CheckAccessReply;

import net.es.oscars.utils.sharedConstants.StateEngineValues;

/**
 * AuthZCheckAccessAction
 * Calls the AuthZ service via  AuthZwoker to check the permission of the
 * subjectAttributes,resource and permission that a request has stored in its
 * requestData.
 * 
 * @author lomax
 * @param input CheckAccessParams[subjectAttributes,resource,permission] is placed in the RequestData field
 * @return output CheckAccessRply[permission,AuthConditions] is placed in the ResultData field
 *
 */
public class AuthZCheckAccessAction extends CoordAction <CheckAccessParams, CheckAccessReply> {

    private static final long       serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(AuthZCheckAccessAction.class.getName());
    private static final String moduleName = ModuleName.COORD;
    
    @SuppressWarnings("unchecked")
    public AuthZCheckAccessAction (String name, CoordRequest request) {
        super (name, request, null);
    }
    
  
    public void execute() {
        OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
        netLogger.init(moduleName, this.getRequestData().getTransactionId());
        String event = "AuthZCheckAccess.execute";
        LOG.debug(netLogger.start(event));
        // Send a query to AuthZ to check user credentials. 
        try {
            AuthZWorker authZWorker = AuthZWorker.getInstance();
            CheckAccessParams checkAccessParams = this.getRequestData();
            
            Object[] req = new Object[]{checkAccessParams};
            Object[] res = authZWorker.getAuthZClient().invoke("checkAccess",req);

            if ((res == null) || (res[0] == null)) {
                throw new OSCARSServiceException ("AuthZCheckAccessAction:No response from AuthZ module",
                        "system");
            }
            CheckAccessReply authDecision = (CheckAccessReply) res[0];
            if (authDecision == null) {
                throw new OSCARSServiceException ("AuthZCheckAccessAction:Returned AuthDecision is null",
                        "system");
            }
            // Set the answers.
            this.setResultData(authDecision);
            // checkAccess is synchronous. Call executed.
            this.executed();
            LOG.debug(netLogger.end(event));
        } catch (OSCARSServiceException ex) {
            this.fail(ex, StateEngineValues.FAILED);
        } catch (Exception e)  {
            LOG.error (netLogger.error(event, ErrSev.MINOR,"caught Exception " + e.toString()));
            //e.printStackTrace();
            this.fail(e, StateEngineValues.FAILED);
        }
    } 
}

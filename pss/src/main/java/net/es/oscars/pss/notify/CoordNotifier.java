package net.es.oscars.pss.notify;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.Map;

import org.apache.log4j.Logger;

import net.es.oscars.api.soap.gen.v06.EventContent;
import net.es.oscars.common.soap.gen.MessagePropertiesType;
import net.es.oscars.common.soap.gen.SubjectAttributes;
import net.es.oscars.coord.soap.gen.PSSReplyContent;
import net.es.oscars.pss.api.PSSAction;
import net.es.oscars.pss.api.PSSActionStatus;
import net.es.oscars.pss.api.PSSException;
import net.es.oscars.pss.api.NotifyAgent;
import net.es.oscars.pss.api.PSSRequest;
import net.es.oscars.pss.common.PSSConfigHolder;
import net.es.oscars.utils.config.ContextConfig;
import net.es.oscars.utils.svc.ServiceNames;
import net.es.oscars.utils.sharedConstants.PSSConstants;
import net.es.oscars.utils.clients.CoordClient;

/**
 * Called when a pss action fails or is completed
 * Calls Coordinator with a PSSReply message
 * @author Evangelos Chaniotakis, Mary Thompson
 *
 */
public class CoordNotifier implements NotifyAgent {
    private Logger log = Logger.getLogger(CoordNotifier.class);

    /**
     * Checks the status of the PSSAction and sends
     * a PSSReply message to the Coordinator with the results of the action
     * 
     * @param PSSAction the action that has completed or failed
     */
    @SuppressWarnings("rawtypes")
    public PSSAction process(PSSAction action) throws PSSException {
        log.info("CoordNotifier:start");
        CoordClient cl;

        try {
            String coord = null;
            if (action.getActionType() == PSSAction.PSSActionTypes.SETUP ) {
                coord = action.getRequest().getSetupReq().getCallbackEndpoint();
            } else {
                coord = action.getRequest().getTeardownReq().getCallbackEndpoint();
            }
            String wsdl = coord + "?wsdl";
            log.debug("coord is " + coord + " wsdl is " + wsdl);
            URL coordURL = new URL(coord);
            URL wsdlURL = new URL(coord + "?wsdl");
            log.debug("calling Coordinator at " + coordURL.toString());
            cl = CoordClient.getClient(coordURL, wsdlURL);
            PSSReplyContent reply = new PSSReplyContent();
            if (action.getStatus() == PSSActionStatus.SUCCESS) {
                reply.setStatus(PSSConstants.SUCCESS);
            } else {
                reply.setStatus(PSSConstants.FAIL);
            }
            if (action.getActionType().equals(PSSAction.PSSActionTypes.SETUP)) {
                reply.setGlobalReservationId(
                            action.getRequest().getSetupReq().getReservation().getGlobalReservationId());
                reply.setReplyType(PSSConstants.PSS_SETUP);
                reply.setId(action.getRequest().getSetupReq().getId());  //GlobalTransactionId
            } else if (action.getActionType().equals(PSSAction.PSSActionTypes.TEARDOWN)) {
                reply.setGlobalReservationId(
                        action.getRequest().getTeardownReq().getReservation().getGlobalReservationId());
                reply.setReplyType(PSSConstants.PSS_TEARDOWN);
                reply.setId(action.getRequest().getTeardownReq().getId()); //GlobalTransactionId
            } else {
                log.info("CoordNotifier unknown action: "+action.getActionType()+" status:"+action.getStatus()+", not sending to coordinator");
                return action;
            }

            Object[] req = new Object[]{reply};
            cl.invoke("PSSReply", req);
        } catch (Exception e) {
            if (e.getMessage() ==  null) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                log.error("caught exception " + e.toString() + sw.toString());
            } else {
                log.error("caught PSSException " + e.getMessage());
            }
            throw new PSSException(e.getMessage());
        }
        return action;
    }

    @SuppressWarnings("rawtypes")
    public void setOptions(Map options) {
        // TODO Auto-generated method stub

    }

}

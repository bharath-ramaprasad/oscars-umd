package net.es.oscars.pss.common;

import org.apache.log4j.Logger;
import java.io.PrintWriter;
import java.io.StringWriter;

import net.es.oscars.pss.api.PSSException;
import net.es.oscars.pss.api.PSSRequest;
import net.es.oscars.pss.api.PSSAction;
import net.es.oscars.pss.notify.CoordNotifier;
import net.es.oscars.coord.soap.gen.PSSReplyContent;
import net.es.oscars.pss.soap.gen.ModifyReqContent;
import net.es.oscars.pss.soap.gen.PSSPortType;
import net.es.oscars.pss.soap.gen.SetupReqContent;
import net.es.oscars.pss.soap.gen.StatusReqContent;
import net.es.oscars.pss.soap.gen.TeardownReqContent;
import net.es.oscars.utils.svc.ServiceNames;

/**
 * main entry point for PSS
 *
 * @author haniotak
 *
 */
@javax.jws.WebService(
        serviceName = ServiceNames.SVC_PSS,
        targetNamespace = "http://oscars.es.net/OSCARS/pss",
        portName = "PSSPort",
        endpointInterface = "net.es.oscars.pss.soap.gen.PSSPortType")
@javax.xml.ws.BindingType(value = "http://www.w3.org/2003/05/soap/bindings/HTTP/")
public class PSSSoapHandler implements PSSPortType {

    private static final Logger log = Logger.getLogger(PSSSoapHandler.class.getName());

    /**
     * handles setup request
     */
    public void setup(SetupReqContent setupReq)  {
        log.info("setup.start");
        System.out.println("gri: "+setupReq.getReservation().getGlobalReservationId());
        PSSReplyContent resp = new PSSReplyContent();
        PSSAction act = new PSSAction();
        CoordNotifier coordNotify = new CoordNotifier();
        try {

            PSSAgentFactory.getInstance().health();

            String gri = setupReq.getReservation().getGlobalReservationId();
            log.info("setup.gri["+gri+"].start");

            PSSRequest req = new PSSRequest();
            req.setSetupReq(setupReq);
            req.setRequestType(PSSRequest.PSSRequestTypes.SETUP);

            act.setRequest(req);
            act.setActionType(PSSAction.PSSActionTypes.SETUP);

            resp.setGlobalReservationId(gri);
            resp.setStatus("INPROGRESS");
            resp.setReplyType(PSSRequest.PSSRequestTypes.SETUP.toString());
            resp.setId(setupReq.getId());
            req.setReply(resp);

            PSSAgentFactory.getInstance().getValidationAgent().validate(req);
            PSSAgentFactory.getInstance().getWorkflowAgent().add(act);

        } catch (Exception ex) {
            String message = handleException(ex);
            resp.setStatus("FAILED: " + message);
            try {
                coordNotify.process(act);
            } catch (PSSException e) {
                log.error("failed to return failure reply " + e.getMessage());
            }
        }
    }

    /**
     * handles teardown request
     */
    public void teardown(TeardownReqContent teardownReq)  {
        log.info("teardown.start");
        System.out.println(teardownReq);
        PSSReplyContent resp = new PSSReplyContent();
        PSSAction act = new PSSAction();
        CoordNotifier coordNotify = new CoordNotifier();
        try {
            PSSAgentFactory.getInstance().health();

            String gri = teardownReq.getReservation().getGlobalReservationId();
            log.info("teardown.gri["+gri+"].start");

            PSSRequest req = new PSSRequest();
            req.setRequestType(PSSRequest.PSSRequestTypes.TEARDOWN);
            req.setTeardownReq(teardownReq);
            PSSAgentFactory.getInstance().getValidationAgent().validate(req);

            resp.setGlobalReservationId(gri);
            resp.setStatus("INPROGRESS");
            resp.setReplyType(PSSRequest.PSSRequestTypes.TEARDOWN.toString());
            resp.setId(req.getId());
            req.setReply(resp);

            act.setRequest(req);
            act.setActionType(PSSAction.PSSActionTypes.TEARDOWN);
            PSSAgentFactory.getInstance().getWorkflowAgent().add(act);

        } catch (Exception ex) {
            String message = handleException(ex);
            resp.setStatus("FAILED: " + message);
            try {
                coordNotify.process(act);
            } catch (PSSException e) {
                log.error("failed to return failure reply " + e.getMessage());
            }
        }
    }


    public void modify(ModifyReqContent modifyReq) {
        PSSReplyContent resp = new PSSReplyContent();
        PSSAction act = new PSSAction();
        String gri = modifyReq.getReservation().getGlobalReservationId();
        log.info("setup.gri["+gri+"].start");

        PSSRequest req = new PSSRequest();
        req.setModifyReq(modifyReq);
        req.setRequestType(PSSRequest.PSSRequestTypes.MODIFY);

        act.setRequest(req);
        act.setActionType(PSSAction.PSSActionTypes.MODIFY);

        resp.setGlobalReservationId(gri);
        resp.setStatus("NOTIMPLEMENTED");
        resp.setReplyType(PSSRequest.PSSRequestTypes.MODIFY.toString());
        resp.setId(req.getId());
        req.setReply(resp);
        CoordNotifier coordNotify = new CoordNotifier();
        try {
            coordNotify.process(act);
        } catch (PSSException e) {
            log.error("failed to return failure reply " + e.getMessage());
        }
    }

    public void status(StatusReqContent statusReq) {
        PSSReplyContent resp = new PSSReplyContent();
        PSSAction act = new PSSAction();
        String gri = statusReq.getReservation().getGlobalReservationId();
        log.info("setup.gri["+gri+"].start");

        PSSRequest req = new PSSRequest();
        req.setStatusReq(statusReq);
        req.setRequestType(PSSRequest.PSSRequestTypes.STATUS);

        act.setRequest(req);
        act.setActionType(PSSAction.PSSActionTypes.STATUS);

        resp.setGlobalReservationId(gri);
        resp.setStatus("NOTIMPLEMENTED");
        resp.setReplyType(PSSRequest.PSSRequestTypes.STATUS.toString());
        resp.setId(req.getId());
        req.setReply(resp);
        CoordNotifier coordNotify = new CoordNotifier();
        try {
            coordNotify.process(act); 
        } catch (PSSException e) {
            log.error("failed to return failure reply " + e.getMessage());
        }
    }


    private String handleException (Exception ex ) {
        String message = ex.getMessage();
        if (!ex.getClass().getName().equals("PSSException")) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            if (message == null ) {
                message = ex.toString();
            }
            ex.printStackTrace(pw);
            log.error("caught exception " + message + sw.toString());
        } else {
            log.error("caught PSSException " + message);
        }
        return message;
    }

}

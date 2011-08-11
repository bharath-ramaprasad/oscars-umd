package net.es.oscars.pss.stub.common;

import org.apache.log4j.Logger;
import java.net.URL;
import java.net.MalformedURLException;

import net.es.oscars.logging.ErrSev;
import net.es.oscars.logging.ModuleName;
import net.es.oscars.logging.OSCARSNetLogger;
import net.es.oscars.pss.api.PSSActionStatus;
import net.es.oscars.pss.api.PSSException;
import net.es.oscars.pss.api.PSSRequest;
import net.es.oscars.pss.api.PSSAction;
import net.es.oscars.pss.notify.CoordNotifier;
import net.es.oscars.pss.soap.gen.ModifyReqContent;
import net.es.oscars.pss.soap.gen.PSSPortType;
import net.es.oscars.pss.soap.gen.SetupReqContent;
import net.es.oscars.coord.soap.gen.PSSReplyContent;
import net.es.oscars.pss.soap.gen.StatusReqContent;
import net.es.oscars.pss.soap.gen.TeardownReqContent;
import net.es.oscars.utils.svc.ServiceNames;
import net.es.oscars.utils.clients.CoordClient;

/**
 * main entry point for PSS
 *
 * @author haniotak,mrt
 *
 */
@javax.jws.WebService(
        serviceName = ServiceNames.SVC_PSS,
        targetNamespace = "http://oscars.es.net/OSCARS/pss",
        portName = "PSSPort",
        endpointInterface = "net.es.oscars.pss.soap.gen.PSSPortType")
@javax.xml.ws.BindingType(value = "http://www.w3.org/2003/05/soap/bindings/HTTP/")
public class StubPSSSoapHandler implements PSSPortType {

    private static final Logger log = Logger.getLogger(StubPSSSoapHandler.class.getName());
    private static final String moduleName = ModuleName.PSS;

    public void setup(SetupReqContent setupReq) {
        String event = "setup";
        OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
        netLogger.init(moduleName,setupReq.getId());
        String gri = setupReq.getReservation().getGlobalReservationId();
        netLogger.setGRI(gri);
        log.info(netLogger.start(event));
 
        PSSReplyContent resp = new PSSReplyContent();
        PSSAction act = new PSSAction();
        CoordNotifier coordNotify = new CoordNotifier();
        try {
            PSSRequest req = new PSSRequest();
            req.setSetupReq(setupReq);
            req.setRequestType(PSSRequest.PSSRequestTypes.SETUP);

            act.setRequest(req);
            act.setActionType(PSSAction.PSSActionTypes.SETUP);
            act.setStatus(PSSActionStatus.SUCCESS);
            log.debug(netLogger.getMsg(event,"calling coordNotify.process"));
            coordNotify.process(act);
        } catch (PSSException e) {
            log.error(netLogger.error(event,ErrSev.MAJOR,"caught PSSException " + e.getMessage()));
        }
        log.info(netLogger.end(event));
    }

    public void teardown(TeardownReqContent teardownReq) {
        String event = "teardown";
        OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
        netLogger.init(moduleName,teardownReq.getId());
        String gri = teardownReq.getReservation().getGlobalReservationId();
        netLogger.setGRI(gri);
        log.info(netLogger.start(event));

        PSSReplyContent resp = new PSSReplyContent();
        PSSAction act = new PSSAction();
        CoordNotifier coordNotify = new CoordNotifier();
        try {
            PSSRequest req = new PSSRequest();
            req.setTeardownReq(teardownReq);
            req.setRequestType(PSSRequest.PSSRequestTypes.TEARDOWN);

            act.setRequest(req);
            act.setActionType(PSSAction.PSSActionTypes.TEARDOWN);
            act.setStatus(PSSActionStatus.SUCCESS);
            coordNotify.process(act);
        } catch (PSSException e) {
            log.error(netLogger.error(event,ErrSev.MAJOR,"caught PSSException " + e.getMessage()));
        }
        log.info(netLogger.end(event));
    }


    public  void modify(ModifyReqContent modifyReq)  {
        String event = "modify";
        OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
        netLogger.init(moduleName,modifyReq.getId());
        String gri = modifyReq.getReservation().getGlobalReservationId();
        netLogger.setGRI(gri);
        log.info(netLogger.start(event));
        
        PSSReplyContent resp = new PSSReplyContent();
        PSSAction act = new PSSAction();
        CoordNotifier coordNotify = new CoordNotifier();
        try {
            PSSRequest req = new PSSRequest();
            req.setModifyReq(modifyReq);
            req.setRequestType(PSSRequest.PSSRequestTypes.MODIFY);

            act.setRequest(req);
            act.setActionType(PSSAction.PSSActionTypes.MODIFY);
            act.setStatus(PSSActionStatus.FAIL);
            coordNotify.process(act);
        } catch (PSSException e) {
            log.error(netLogger.error(event,ErrSev.MAJOR,"caught PSSException " + e.getMessage()));
        }
        log.info(netLogger.end(event));
    }

    public void status(StatusReqContent statusReq) {
        String event = "status";
        OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
        netLogger.init(moduleName,statusReq.getId());
        String gri = statusReq.getReservation().getGlobalReservationId();
        netLogger.setGRI(gri);
        log.info(netLogger.start(event));
 
        PSSReplyContent resp = new PSSReplyContent();
        PSSAction act = new PSSAction();
        CoordNotifier coordNotify = new CoordNotifier();
        try {
            PSSRequest req = new PSSRequest();
            req.setStatusReq(statusReq);
            req.setRequestType(PSSRequest.PSSRequestTypes.STATUS);

            act.setRequest(req);
            act.setActionType(PSSAction.PSSActionTypes.STATUS);
            act.setStatus(PSSActionStatus.FAIL);
            coordNotify.process(act);
        } catch (PSSException e) {
            log.error(netLogger.error(event,ErrSev.MAJOR,"caught PSSException " + e.getMessage()));
        }
        log.info(netLogger.end(event));
    }
}

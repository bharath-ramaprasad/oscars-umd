package net.es.oscars.wbui.servlets;

/**
 * CreateReservation servlet
 *
 * @author David Robertson, Evangelos Chaniotakis
 *
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.log4j.*;
import net.sf.json.JSONObject;

import oasis.names.tc.saml._2_0.assertion.AttributeType;
import org.ogf.schema.network.topology.ctrlplane.CtrlPlaneHopContent;
import org.ogf.schema.network.topology.ctrlplane.CtrlPlanePathContent;

import net.es.oscars.logging.ErrSev;
import net.es.oscars.logging.OSCARSNetLogger;
import net.es.oscars.utils.clients.AuthZClient;
import net.es.oscars.utils.clients.AuthNPolicyClient;
import net.es.oscars.utils.clients.CoordClient;
import net.es.oscars.utils.soap.OSCARSServiceException;
import net.es.oscars.utils.svc.ServiceNames;
import net.es.oscars.utils.topology.PathTools;
import net.es.oscars.api.soap.gen.v06.ResCreateContent;
import net.es.oscars.api.soap.gen.v06.ResDetails;
import net.es.oscars.api.soap.gen.v06.PathInfo;
import net.es.oscars.api.soap.gen.v06.Layer2Info;
import net.es.oscars.api.soap.gen.v06.Layer3Info;
import net.es.oscars.api.soap.gen.v06.MplsInfo;
import net.es.oscars.api.soap.gen.v06.CreateReply;
import net.es.oscars.api.soap.gen.v06.ReservedConstraintType;
import net.es.oscars.api.soap.gen.v06.OptionalConstraintType;
import net.es.oscars.api.soap.gen.v06.UserRequestConstraintType;
import net.es.oscars.common.soap.gen.MessagePropertiesType;
import net.es.oscars.common.soap.gen.SubjectAttributes;

/**
 * Servlet to handle a createReservation request
 * Parses the servletRequest, check the session validity, gets the
 * user's attributes and calls the Coordinator client to create the reservation.
 * @author davidr,mrt
 *
 */
public class CreateReservation extends HttpServlet {
    private Logger log = Logger.getLogger(CreateReservation.class);

    /**
     * doGet
     *
     * @param request HttpServlerRequest - contains start and end times, bandwidth, description,
     *          productionType, pathinfo
     * @param response HttpServler Response - contain gri and success or error status
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {

        String methodName= "CreateReservation";
        String transId  = PathTools.getLocalDomainId() + "-WBUI-" + UUID.randomUUID().toString();
        OSCARSNetLogger netLogger = new OSCARSNetLogger();
        netLogger.init(ServiceNames.SVC_WBUI,transId);
        OSCARSNetLogger.setTlogger(netLogger);
        this.log.info(netLogger.start(methodName));

        PrintWriter out = response.getWriter();
        ServletCore core = (ServletCore)
              getServletContext().getAttribute(ServletCore.CORE);
        if (core == null) {
            ServletUtils.fatalError(out, methodName);
        }
        /* put transId in core so that the queryReservationStatus servlets that are run as a consequence 
           of createReservation will use same transId.*/
        core.setTransId(transId);
        CoordClient coordClient = core.getCoordClient();
        AuthNPolicyClient authNPolicyClient = core.getAuthNPolicyClient();

        UserSession userSession = new UserSession(core);
        CheckSessionReply sessionReply =
            userSession.checkSession(out, authNPolicyClient, request,
                                     methodName);
        if (sessionReply == null) {
            this.log.warn(netLogger.error(methodName, ErrSev.MINOR,"No user session: cookies invalid"));
            return;
        }
        String userName = sessionReply.getUserName();
        response.setContentType("application/json");
        HashMap<String, Object> outputMap = new HashMap<String, Object>();

        List<AttributeType> userAttributes = sessionReply.getAttributes(); 
        SubjectAttributes subjectAttrs = new SubjectAttributes();
        List<AttributeType> reqAttrs = subjectAttrs.getSubjectAttribute();
        for (AttributeType attr: userAttributes) {
            reqAttrs.add(attr);
        }
        MessagePropertiesType msgProps = new MessagePropertiesType();
        msgProps.setOriginator(subjectAttrs);
        msgProps.setGlobalTransactionId(transId);
        ResCreateContent createReq = null;
        String gri = null;
        try {
            createReq = this.toReservation(userName, request);
            createReq.setMessageProperties(msgProps);
            // Send a createReservation request 
            Object[] req = new Object[]{subjectAttrs,createReq};
            Object[] res = coordClient.invoke("createReservation",req);
            CreateReply coordResponse = (CreateReply) res[0];
            gri = coordResponse.getGlobalReservationId();
        } catch (Exception ex) {
            // any error will show up as an exception
            this.log.error(netLogger.error(methodName, ErrSev.MAJOR,"caught exception  invoking createReservation " + 
                                            ex.toString()));
            ex.printStackTrace();
            ServletUtils.handleFailure(out, log, ex, methodName);
            return;
        }
        outputMap.put("gri", gri);
        outputMap.put("status", "Submitted reservation with GRI " + gri);
        outputMap.put("method", methodName);
        outputMap.put("success", Boolean.TRUE);
        JSONObject jsonObject = JSONObject.fromObject(outputMap);
        out.println("{}&&" + jsonObject);
        this.log.info(netLogger.end(methodName));
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        this.doGet(request, response);
    }

    private ResCreateContent
        toReservation(String userName, HttpServletRequest request)
           throws OSCARSServiceException {
        this.log.debug("toReservation:start");

        String strParam = null;
        Integer bandwidth = null;
        Long seconds = 0L;

        ResCreateContent resv = new ResCreateContent();
        UserRequestConstraintType userCon = new UserRequestConstraintType();

        // necessary type conversions performed here; validation done in
        // ReservationManager
        strParam = request.getParameter("startSeconds");
        if (strParam != null && !strParam.trim().equals("")) {
            seconds = Long.parseLong(strParam.trim());
        } else {
            throw new OSCARSServiceException("error: start time is a required parameter");
        }
        userCon.setStartTime(seconds);

        strParam = request.getParameter("endSeconds");
        if (strParam != null && !strParam.trim().equals("")) {
            seconds = Long.parseLong(strParam.trim());
        } else {
            throw new OSCARSServiceException("error: end time is a required parameter");
        }
        userCon.setEndTime(seconds);

        strParam = request.getParameter("bandwidth");
        if (strParam != null && !strParam.trim().equals("")) {
            bandwidth = Integer.valueOf(strParam.trim());
        } else {
            throw new OSCARSServiceException("error: bandwidth is a required parameter.");
        }
        userCon.setBandwidth(bandwidth);

        String description = "";
        strParam = request.getParameter("description");
        if (strParam != null && !strParam.trim().equals("")) {
            description = strParam.trim();
        }

        strParam = request.getParameter("productionType");
        // if not blank, check box indicating production circuit was checked
        if (strParam != null && !strParam.trim().equals("")) {
            this.log.info("production reservation created");
            description = "[PRODUCTION CIRCUIT] " + description;
        } else {
            this.log.debug("non-production circuit");
        }
        resv.setDescription(description);
        userCon.setPathInfo( handlePath(request));
        resv.setUserRequestConstraint(userCon);
        this.log.debug("toReservation:end");

        return resv;
    }

    /**
     * Takes form parameters and builds Path structures.
     *
     * @param request HttpServletRequest
     * @return requestedPath a Path instance with layer 2 or 3 information
     */
    private PathInfo handlePath(HttpServletRequest request)
            throws OSCARSServiceException {

        this.log.debug("handlePath:start");
        String strParam = null;

        //List<PathElem> pathElems = new ArrayList<PathElem>();
        //PropHandler propHandler = new PropHandler("oscars.properties");
        //Properties props = propHandler.getPropertyGroup("wbui", true);
        //String defaultLayer = props.getProperty("defaultLayer");
        String defaultLayer = "2";  // that's all we are implementing for now

        PathInfo requestedPath = new PathInfo();
        String[] inHops = {};
        requestedPath.setPathType("loose");
        requestedPath.setPathSetupMode("timer-automatic");

        String explicitPath = "";
        String source = null;
        String destination = null;
        strParam = request.getParameter("source");
        if ((strParam != null) && !strParam.trim().equals("")) {
            source = strParam.trim();
        } else {
            throw new OSCARSServiceException("error:  source is a required parameter");
        }
        strParam = request.getParameter("destination");
        if ((strParam != null) && !strParam.trim().equals("")) {
            destination = strParam.trim();
        } else {
            throw new OSCARSServiceException("error:  destination is a required parameter");
        }
        CtrlPlanePathContent path = new CtrlPlanePathContent ();
        List<CtrlPlaneHopContent> pathHops = path.getHop();
        strParam = request.getParameter("explicitPath");
        if (strParam != null && !strParam.trim().equals("")) {
            explicitPath = strParam.trim();
            this.log.debug("explicit path: " + explicitPath);

            inHops = explicitPath.split("\\s+");
            for (int i = 0; i < inHops.length; i++) {
                inHops[i] = inHops[i].trim();
                if (inHops[i].equals(" ") || inHops[i].equals("")) {
                    continue;
                }
                for (String hop : inHops) {
                   CtrlPlaneHopContent cpHop = new CtrlPlaneHopContent();
                   cpHop.setLinkIdRef(hop);
                   pathHops.add(cpHop);
                }
                requestedPath.setPath(path);
            }
        }
        String srcVlan = "";
        strParam = request.getParameter("srcVlan");
        if (strParam != null && !strParam.trim().equals("")) {
            srcVlan = strParam.trim();
        }
        boolean layer2 = false;
        // TODO: support VLAN translation

        if (!srcVlan.equals("") ||
              (defaultLayer !=  null && defaultLayer.equals("2"))) {
            layer2 = true;
            this.log.debug("handlePath. in layer2 processing");

            Layer2Info layer2Info = new Layer2Info();
            srcVlan = (srcVlan == null||srcVlan.equals("") ? "any" : srcVlan);
            String destVlan = "";
            strParam = request.getParameter("destVlan");
            if (strParam != null && !strParam.trim().equals("")) {
                destVlan = strParam.trim();
            } else {
                destVlan = srcVlan;
            }
            // src and dest default to tagged
            String taggedSrcVlan = "Tagged";
            strParam = request.getParameter("taggedSrcVlan");
            if (strParam != null && !strParam.trim().equals("")) {
                taggedSrcVlan = strParam.trim();
            }
            String taggedDestVlan = "Tagged";
            strParam = request.getParameter("taggedDestVlan");
            if (strParam != null && !strParam.trim().equals("")) {
                taggedDestVlan = strParam.trim();
            }
            boolean tagged = taggedSrcVlan.equals("Tagged");
            if (!tagged) {
                srcVlan = "0";
            }
            tagged = taggedDestVlan.equals("Tagged");
            if (!tagged) {
                destVlan = "0";
            }

            layer2Info.setSrcEndpoint(source);
            layer2Info.setDestEndpoint(destination);
            requestedPath.setLayer2Info(layer2Info);

            // If no explicit path for layer 2, we must fill this in
            if (pathHops.isEmpty()) {
                    CtrlPlaneHopContent sourceHop = new CtrlPlaneHopContent();
                sourceHop.setLinkIdRef(source);
                pathHops.add(sourceHop);
                CtrlPlaneHopContent destHop = new CtrlPlaneHopContent();
                destHop.setLinkIdRef(destination);
                pathHops.add(destHop);
            }
            /* Need to set a linkContent rather than a linkIdRef in order to store link params
            PathElemParam srcVlanParam = new PathElemParam();
            srcVlanParam.setSwcap(PathElemParamSwcap.L2SC);
            srcVlanParam.setType(PathElemParamType.L2SC_VLAN_RANGE);
            srcVlanParam.setValue(srcVlan);
            PathElemParam destVlanParam = new PathElemParam();
            destVlanParam.setSwcap(PathElemParamSwcap.L2SC);
            destVlanParam.setType(PathElemParamType.L2SC_VLAN_RANGE);
            destVlanParam.setValue(destVlan);
            requestedPath.getPathElems().get(0).addPathElemParam(srcVlanParam);
            requestedPath.getPathElems().get(requestedPath.getPathElems().size()-1).addPathElemParam(destVlanParam);
            */
        }
        if (!layer2) {
            return null;
            /* not implemented yet
            Layer3Data layer3Data = new Layer3Data();
            // VLAN id wasn't supplied with layer 2 id
            if (source.startsWith("urn:ogf:network")) {
                throw new OSCARSServiceException("VLAN tag not supplied for layer 2 reservation");
            }
            layer3Data.setSrcHost(source);
            layer3Data.setDestHost(destination);

            strParam = request.getParameter("srcPort");
            if ((strParam != null) && !strParam.trim().equals("")) {
                layer3Data.setSrcIpPort(Integer.valueOf(strParam.trim()));
            } else {
                layer3Data.setSrcIpPort(0);
            }
            strParam = request.getParameter("destPort");
            if ((strParam != null) && !strParam.trim().equals("")) {
                layer3Data.setDestIpPort(Integer.valueOf(strParam.trim()));
            } else {
                layer3Data.setDestIpPort(0);
            }
            strParam = request.getParameter("protocol");
            if ((strParam != null) && !strParam.trim().equals("")) {
                layer3Data.setProtocol(strParam.trim());
            }
            strParam = request.getParameter("dscp");
            if ((strParam != null) && !strParam.trim().equals("")) {
                layer3Data.setDscp(strParam.trim());
            }
            requestedPath.setLayer3Data(layer3Data);
            */
        }
        this.log.debug("handlePath:end");
        return requestedPath;
    }
}

package net.es.oscars.wbui.servlets;


import java.io.*;
import java.util.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.log4j.Logger;
import org.ogf.schema.network.topology.ctrlplane.CtrlPlaneHopContent;
import org.ogf.schema.network.topology.ctrlplane.CtrlPlaneLinkContent;
import org.ogf.schema.network.topology.ctrlplane.CtrlPlanePathContent;
import org.ogf.schema.network.topology.ctrlplane.CtrlPlaneSwcapContent;
import org.ogf.schema.network.topology.ctrlplane.CtrlPlaneSwitchingCapabilitySpecificInfo;

import net.sf.json.*;

import oasis.names.tc.saml._2_0.assertion.AttributeType;
import net.es.oscars.logging.ErrSev;
import net.es.oscars.logging.OSCARSNetLogger;
import net.es.oscars.utils.clients.AuthNPolicyClient;
import net.es.oscars.utils.clients.AuthZClient;
import net.es.oscars.utils.clients.CoordClient;
import net.es.oscars.utils.sharedConstants.AuthZConstants;
import net.es.oscars.utils.soap.OSCARSServiceException;
import net.es.oscars.utils.svc.ServiceNames;
import net.es.oscars.utils.topology.PathTools;
import net.es.oscars.api.soap.gen.v06.Layer2Info;
import net.es.oscars.api.soap.gen.v06.Layer3Info;
import net.es.oscars.api.soap.gen.v06.MplsInfo;
import net.es.oscars.api.soap.gen.v06.ListRequest;
import net.es.oscars.api.soap.gen.v06.ListReply;
import net.es.oscars.api.soap.gen.v06.PathInfo;
import net.es.oscars.api.soap.gen.v06.QueryResContent;
import net.es.oscars.api.soap.gen.v06.QueryResReply;
import net.es.oscars.api.soap.gen.v06.ReservedConstraintType;
import net.es.oscars.api.soap.gen.v06.UserRequestConstraintType;
import net.es.oscars.api.soap.gen.v06.VlanTag;
import net.es.oscars.api.soap.gen.v06.ResCreateContent;
import net.es.oscars.api.soap.gen.v06.ResDetails;
import net.es.oscars.authZ.soap.gen.CheckAccessReply;
import net.es.oscars.common.soap.gen.AuthConditionType;
import net.es.oscars.common.soap.gen.AuthConditions;
import net.es.oscars.common.soap.gen.MessagePropertiesType;
import net.es.oscars.common.soap.gen.SubjectAttributes;
/**
 * Query reservation servlet
 *
 * @author David Robertson, Mary Thompson
 *
 */
public class QueryReservation extends HttpServlet {
    private static Logger log = Logger.getLogger(QueryReservation.class);

    /**
     * Handles QueryReservation servlet request.
     *
     * @param request HttpServletRequest contains the gri of the reservation
     * @param response HttpServletResponse contains: gri, status, user,
     *        description start, end and create times, bandwidth, vlan tag,
     *        and path information.
     */
    public void
        doGet(HttpServletRequest servletRequest, HttpServletResponse response)
            throws IOException, ServletException {

        String methodName = "QueryReservation";
        String transId  = PathTools.getLocalDomainId() + "-WBUI-" + UUID.randomUUID().toString();
        OSCARSNetLogger netLogger = new OSCARSNetLogger();
        netLogger.init(ServiceNames.SVC_WBUI,transId);
        OSCARSNetLogger.setTlogger(netLogger);
        log.info(netLogger.start(methodName));

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        ServletCore core = ServletCore.getInstance();
        getServletContext().getAttribute(ServletCore.CORE);
        if (core == null) {
            ServletUtils.fatalError(out, methodName);
        }
        CoordClient coordClient = core.getCoordClient();
        AuthNPolicyClient authNPolicyClient = core.getAuthNPolicyClient();
        AuthZClient authZClient = core.getAuthZClient();
        UserSession userSession = new UserSession(core);
        CheckSessionReply sessionReply =
            userSession.checkSession(out, authNPolicyClient, servletRequest,
                    methodName);
        if (sessionReply == null) {
            log.warn(netLogger.error(methodName,ErrSev.MINOR,"No user session: cookies invalid"));
            return;
        }
        String userName = sessionReply.getUserName();
        if (userName == null) {
            log.warn(netLogger.error(methodName,ErrSev.MINOR,"No user session: cookies invalid"));
            return;
        }

        List<AttributeType> userAttributes = sessionReply.getAttributes(); 
        SubjectAttributes subjectAttrs = new SubjectAttributes();
        List<AttributeType> reqAttrs = subjectAttrs.getSubjectAttribute();
        for (AttributeType attr: userAttributes) {
            reqAttrs.add(attr);
        }
        QueryResContent queryReq = new QueryResContent();
        MessagePropertiesType msgProps = new MessagePropertiesType();
        msgProps.setOriginator(subjectAttrs);
        msgProps.setGlobalTransactionId(transId);
        queryReq.setMessageProperties(msgProps);
        String gri = servletRequest.getParameter("gri");
        queryReq.setGlobalReservationId(gri);
        ResDetails resDetails= null;
        //QueryResReply queryRe
        String authVal = null;
        Map<String, Object> outputMap = new HashMap<String, Object>();
        try {
            // Send a queryReservation request 
            Object[] req = new Object[]{subjectAttrs,queryReq};
            Object[] res = coordClient.invoke("queryReservation",req);
            resDetails = ((QueryResReply) res[0]).getReservationDetails();

            // check to see if user is allowed to see the buttons allowing
            // reservation modification
            authVal = ServletUtils.checkPermission(authZClient,
                                                   userAttributes, 
                                                   AuthZConstants.RESERVATIONS, 
                                                   AuthZConstants.MODIFY);
            
            if (!authVal.equals(AuthZConstants.ACCESS_DENIED)) {
                outputMap.put("resvModifyDisplay", Boolean.TRUE);
                outputMap.put("resvCautionDisplay", Boolean.TRUE);
            } else {
                outputMap.put("resvModifyDisplay", Boolean.FALSE);
                outputMap.put("resvCautionDisplay", Boolean.FALSE);
            }
            // check to see if user is allowed to see the clone button, which
            // requires generic reservation create authorization
            CheckAccessReply checkAccessReply  =  ServletUtils.checkAccess(authZClient, 
                                                    userAttributes, 
                                                    AuthZConstants.RESERVATIONS, 
                                                    AuthZConstants.CREATE);
            authVal = checkAccessReply.getPermission();
            if (!authVal.equals(AuthZConstants.ACCESS_DENIED)) {
                outputMap.put("resvCloneDisplay", Boolean.TRUE);
            } else {
                outputMap.put("resvCloneDisplay", Boolean.FALSE);
            }
            AuthConditions conds = checkAccessReply.getConditions();
            boolean intHopsAllowed = false;
            for (AuthConditionType ac: conds.getAuthCondition()){
                if (ac.getName().equals(AuthZConstants.INT_HOPS_ALLOWED))
                    intHopsAllowed = true;
            }
            this.contentSection(resDetails, intHopsAllowed, outputMap);
        } catch (Exception ex) {
            // any error will show up as an exception
            ServletUtils.handleFailure(out, log, ex, methodName);
            return;
        }
        if (resDetails.getStatus() == null) {
            outputMap.put("status", "Reservation details for " + gri);
        } else {
            outputMap.put("status", resDetails.getStatus());
        }
        outputMap.put("method", methodName);
        outputMap.put("success", Boolean.TRUE);
        JSONObject jsonObject = JSONObject.fromObject(outputMap);
        out.println("{}&&" + jsonObject);
        log.info(netLogger.end(methodName));
        return;
    }

    public void
        doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        this.doGet(request, response);
    }

    public void
        contentSection(ResDetails resv, boolean intHopsAllowed,  Map<String,Object> outputMap)
            throws OSCARSServiceException {
        try {
        InetAddress inetAddress = null;
        String hostName = null;
        Long longParam = null;
        Integer intParam = null;
        String strParam = null;

        String gri = resv.getGlobalReservationId();
        UserRequestConstraintType uConstraint = resv.getUserRequestConstraint();
        PathInfo pathInfo = null;
        String pathType = null;
        ReservedConstraintType rConstraint = resv.getReservedConstraint();
        if (rConstraint !=  null) {
            pathInfo=rConstraint.getPathInfo();
            pathType = "reserved";
        } else {
            uConstraint = resv.getUserRequestConstraint();
            if (uConstraint == null) {
                throw new OSCARSServiceException("invalid reservation, no reserved or requested path");
            }
            pathInfo=uConstraint.getPathInfo();
            pathType="requested";
            System.out.println("no path reserved, using requested path ");
        }
        CtrlPlanePathContent path = null;
        Layer2Info layer2Info = null;
        Layer3Info layer3Info = null;
        MplsInfo mplsInfo = null;
        if (pathInfo != null) {
            layer2Info = pathInfo.getLayer2Info();
            layer3Info = pathInfo.getLayer3Info();
            mplsInfo = pathInfo.getMplsInfo();
            path = pathInfo.getPath();
        }
        // if not layer2Info create one from path -- hack to make rest of code work
        if (layer2Info == null && layer3Info ==  null && path != null) {
            layer2Info = createLayer2(path);
        }
        String status = resv.getStatus();
        // always blank NEW GRI field, current GRI is in griReplace's
        // innerHTML
        outputMap.put("newGri", "");
        outputMap.put("griReplace", gri);
        outputMap.put("statusReplace", status);
        outputMap.put("userReplace", resv.getLogin());
        String sanitized = resv.getDescription().replace("<", "");
        String sanitized2 = sanitized.replace(">", "");
        outputMap.put("descriptionReplace", sanitized2);

        outputMap.put("modifyStartSeconds", uConstraint.getStartTime());
        outputMap.put("modifyEndSeconds", uConstraint.getEndTime());
        outputMap.put("createdTimeConvert", resv.getCreateTime());
        // now stored in Mbps, commas added by Dojo
        outputMap.put("bandwidthReplace", Integer.toString(uConstraint.getBandwidth()));
        if (layer2Info != null) {
            outputMap.put("sourceReplace", layer2Info.getSrcEndpoint());
            outputMap.put("destinationReplace", layer2Info.getDestEndpoint());
            QueryReservation.handleVlans(path, status, layer2Info, outputMap);
        } else if (layer3Info != null) {
            strParam = layer3Info.getSrcHost();
            try {
                inetAddress = InetAddress.getByName(strParam);
                hostName = inetAddress.getHostName();
            } catch (UnknownHostException e) {
                hostName = strParam;
            }
            outputMap.put("sourceReplace", hostName);
            strParam = layer3Info.getDestHost();
            try {
                inetAddress = InetAddress.getByName(strParam);
                hostName = inetAddress.getHostName();
            } catch (UnknownHostException e) {
                hostName = strParam;
            }
            outputMap.put("destinationReplace", hostName);
            intParam = layer3Info.getSrcIpPort();
            if ((intParam != null) && (intParam != 0)) {
                outputMap.put("sourcePortReplace", intParam);
            }
            intParam = layer3Info.getDestIpPort();
            if ((intParam != null) && (intParam != 0)) {
                outputMap.put("destinationPortReplace", intParam);
            }
            strParam = layer3Info.getProtocol();
            if (strParam != null) {
                outputMap.put("protocolReplace", strParam);
            }
            strParam = layer3Info.getDscp();
            if (strParam !=  null) {
                outputMap.put("dscpReplace", strParam);
            }
        }
        if (mplsInfo != null) {
            intParam = mplsInfo.getBurstLimit();
            if (intParam != null) {
                outputMap.put("burstLimitReplace", intParam);
            }
            if (mplsInfo.getLspClass() != null) {
                outputMap.put("lspClassReplace", mplsInfo.getLspClass());
            }
        }
        QueryReservation.outputPaths(path, intHopsAllowed, outputMap);
        } catch (Exception e){
            System.out.println("caught exception in ContentSection");
            e.printStackTrace();
        }
    }

    public static void handleVlans(CtrlPlanePathContent path, String status, Layer2Info layer2Info, 
                                   Map<String,Object> outputMap) 
            throws OSCARSServiceException {

        log.debug("in handleVlans");
        List<String> vlanTags = new ArrayList<String>();
        String srcVlanTag = "";
        String destVlanTag = "";
        if (layer2Info != null ) {
            srcVlanTag = layer2Info.getSrcVtag().getValue();
            destVlanTag = layer2Info.getDestVtag().getValue();
            log.debug("srcVlanTag:" + srcVlanTag + " destVlanTag:" + destVlanTag);
        }
        if (!srcVlanTag.equals("")) {
            QueryReservation.outputVlan(srcVlanTag, outputMap, "src");
            QueryReservation.outputVlan(destVlanTag, outputMap, "dest");
        } else {
            if (status.equals("ACCEPTED") || status.equals("INPATHCALCULATION") ||
                    status.equals("INSETUP")) {
                outputMap.put("srcVlanReplace", "VLAN setup in progress");
                outputMap.put("destVlanReplace", "VLAN setup in progress");
                outputMap.put("srcTaggedReplace", "");
                outputMap.put("destTaggedReplace", "");
            } else {
                outputMap.put("srcVlanReplace",
                              "No VLAN tag was ever set up");
                outputMap.put("destVlanReplace",
                              "No VLAN tag was ever set up");
                outputMap.put("srcTaggedReplace", "");
                outputMap.put("destTaggedReplace", "");
            }
        }
        ArrayList<CtrlPlaneHopContent> hops = (ArrayList<CtrlPlaneHopContent>) path.getHop();
        for (CtrlPlaneHopContent hop: hops){
            CtrlPlaneLinkContent link = hop.getLink();
            String vlanRangeAvail = "any";
            CtrlPlaneSwcapContent swcap= link.getSwitchingCapabilityDescriptors();
            if (swcap != null) {
                CtrlPlaneSwitchingCapabilitySpecificInfo specInfo = swcap.getSwitchingCapabilitySpecificInfo();
                if (specInfo != null) {
                    vlanTags.add(specInfo.getVlanRangeAvailability()); 
                }
            }
        }
        
        if (!vlanTags.isEmpty()) {
            QueryReservation.outputVlanTable(vlanTags, "vlanInterPathReplace",
                                             outputMap);
        }
    }

    public static void outputVlan(String vlanTag, Map<String, Object> outputMap,
                                  String prefix) {

        if (vlanTag != null) {
            //If its a negative number try converting it
            //Prior to reservation completing may be a range or "any"
            int storedVlan = 0;
            try {
                storedVlan = Integer.parseInt(vlanTag);
                if(0 == storedVlan){
                    vlanTag = "unknown";
                }else{
                    vlanTag = Math.abs(storedVlan) + "";
                }
            } catch (Exception e) {}
            outputMap.put(prefix + "VlanReplace", vlanTag);
            if (storedVlan > 0) {
                outputMap.put(prefix + "TaggedReplace", "true");
            } else {
                outputMap.put(prefix + "TaggedReplace", "false");
            }
        }
    }

    public static void
    outputPaths(CtrlPlanePathContent path, boolean intHopsAllowed,
            Map<String,Object> outputMap) throws OSCARSServiceException {

        String pathStr = new String();
        log.debug("in outputPaths, intHopsAllowed is " + intHopsAllowed );
        if (path != null  ){
            log.debug("path not null");
            ArrayList<CtrlPlaneHopContent> hops = (ArrayList<CtrlPlaneHopContent>) path.getHop();
            if (hops.size() > 0) {
                if (intHopsAllowed) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("<tbody>");
                    for ( CtrlPlaneHopContent ctrlHop : hops ) {
                        CtrlPlaneLinkContent link = ctrlHop.getLink();
                        if (link != null ) {
                            sb.append("<tr><td class='innerHops'>" + link.getId()+ "</td></tr>");
                        } else {
                            String id = ctrlHop.getLinkIdRef();
                            sb.append("<tr><td class='innerHops'>" + id+ "</td></tr>");
                        }
                    }
                    sb.append("</tbody>");
                    //if (Layer2) {
                    if (true) {
                        log.debug("hop: " + sb.toString());
                        outputMap.put("interPathReplace", sb.toString());
                    } else {
                        outputMap.put("interPath3Replace", sb.toString());
                    }
                } else {
                    // don't allow non-authorized user to see internal hops
                    CtrlPlaneHopContent [] endHops = { hops.get(0), hops.get(hops.size()-1) };
                    for (CtrlPlaneHopContent hop : endHops) {
                        CtrlPlaneLinkContent link = hop.getLink();
                        if (link != null ) {
                            pathStr = link.getId() + "\n";
                            log.debug("hop: " + pathStr);
                        }else {
                            pathStr = hop.getLinkIdRef() + "\n";
                        }
                    }
                    // seems like this should be setting something in outputMap - mrt
                }
            }
        }
    }

    public static void outputVlanTable(List<String> vlanTags, String nodeId,
                                       Map<String,Object> outputMap) {
        StringBuilder sb = new StringBuilder();
        sb.append("<tbody>");
        for (String vlanTag: vlanTags) {
            if("0".equals(vlanTag)){
                vlanTag = "n/a*";
            }else if(vlanTag.startsWith("-")){
                vlanTag = vlanTag.replaceFirst("-", "");
                vlanTag += "*";
            }
            sb.append("<tr><td class='innerHops'>" + vlanTag + "</td></tr>");
        }
        sb.append("</tbody>");
        outputMap.put(nodeId, sb.toString());
    }
    /**
     * hack to create a layer2Info from a CtrlPlanPathContent 
     * @param path
     * @return
     */
    public static Layer2Info createLayer2(CtrlPlanePathContent path){
        try {
        log.debug("createLayer:start");
        Layer2Info layer2Info = new Layer2Info();
        ArrayList<CtrlPlaneHopContent> hops = (ArrayList<CtrlPlaneHopContent>) path.getHop();
        CtrlPlaneLinkContent link = hops.get(0).getLink();
        layer2Info.setSrcEndpoint(link.getId());
        String vlanRangeAvail = "any";
        CtrlPlaneSwcapContent swcap= link.getSwitchingCapabilityDescriptors();
        if (swcap != null) {
            CtrlPlaneSwitchingCapabilitySpecificInfo specInfo = swcap.getSwitchingCapabilitySpecificInfo();
            if (specInfo != null) {
                vlanRangeAvail = specInfo.getVlanRangeAvailability(); 
            }
        }
        VlanTag vtag = new VlanTag();
        vtag.setValue(vlanRangeAvail);
        layer2Info.setSrcVtag(vtag);
        // Dest
        link = hops.get(hops.size()-1).getLink();
        layer2Info.setDestEndpoint(link.getId());
        vlanRangeAvail = "any";
        swcap= link.getSwitchingCapabilityDescriptors();
        if (swcap != null) {
            CtrlPlaneSwitchingCapabilitySpecificInfo specInfo = swcap.getSwitchingCapabilitySpecificInfo();
            if (specInfo != null) {
                vlanRangeAvail = specInfo.getVlanRangeAvailability(); 
            }
        }
        vtag.setValue(vlanRangeAvail);
        layer2Info.setDestVtag(vtag);
        log.debug("createLayer:end");
        return layer2Info;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

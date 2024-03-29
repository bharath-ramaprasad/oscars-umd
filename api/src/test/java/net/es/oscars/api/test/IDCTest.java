
package net.es.oscars.api.test;

import java.net.InetAddress;
import java.net.URL;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import static java.util.Arrays.asList;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

import org.ogf.schema.network.topology.ctrlplane.CtrlPlaneLinkContent;
import org.ogf.schema.network.topology.ctrlplane.CtrlPlanePathContent;
import org.ogf.schema.network.topology.ctrlplane.CtrlPlaneHopContent;
import org.ogf.schema.network.topology.ctrlplane.CtrlPlaneSwcapContent;
import org.ogf.schema.network.topology.ctrlplane.CtrlPlaneSwitchingCapabilitySpecificInfo;


import net.es.oscars.utils.config.ConfigDefaults;
import net.es.oscars.utils.config.ConfigException;
import net.es.oscars.utils.config.ConfigHelper;
import net.es.oscars.utils.config.ContextConfig;
import net.es.oscars.utils.soap.*;
import net.es.oscars.utils.svc.ServiceNames;
import net.es.oscars.common.soap.gen.*;
import net.es.oscars.api.soap.gen.v06.CancelResContent;
import net.es.oscars.api.soap.gen.v06.CancelResReply;
import net.es.oscars.api.soap.gen.v06.CreatePathContent;
import net.es.oscars.api.soap.gen.v06.CreatePathResponseContent;
import net.es.oscars.api.soap.gen.v06.EventContent;
import net.es.oscars.api.soap.gen.v06.MplsInfo;
import net.es.oscars.api.soap.gen.v06.QueryResContent;
import net.es.oscars.api.soap.gen.v06.QueryResReply;
import net.es.oscars.api.soap.gen.v06.ResCreateContent;
import net.es.oscars.api.soap.gen.v06.CreateReply;
import net.es.oscars.api.soap.gen.v06.ModifyResContent;
import net.es.oscars.api.soap.gen.v06.ModifyResReply;
import net.es.oscars.api.soap.gen.v06.ResDetails;
import net.es.oscars.api.soap.gen.v06.GlobalReservationId;
import net.es.oscars.api.soap.gen.v06.Layer2Info;
import net.es.oscars.api.soap.gen.v06.Layer3Info;
import net.es.oscars.api.soap.gen.v06.TeardownPathContent;
import net.es.oscars.api.soap.gen.v06.TeardownPathResponseContent;
import net.es.oscars.api.soap.gen.v06.VlanTag;
import net.es.oscars.api.soap.gen.v06.ListReply;
import net.es.oscars.api.soap.gen.v06.PathInfo;
import net.es.oscars.api.soap.gen.v06.ReservedConstraintType;
import net.es.oscars.api.soap.gen.v06.UserRequestConstraintType;
import net.es.oscars.api.soap.gen.v06.ListRequest;

import net.es.oscars.utils.clients.IDCClient05;
import net.es.oscars.utils.clients.IDCClient06;

import org.apache.log4j.Logger;

public final class IDCTest {

    /**
     * IDC Test client.
     * Arguments are: <request> <protocol version> <auth type>
     * auth types can be: x509, UT
     * 
     * @param args
     */
    private static Logger LOG = null;
    public static final String DEFAULT_CONFIG_FILE = "create.yaml";
    public static String version = null;
    public static String authType = null;
    public static String request = null;
    public static String gri = null;
    public static Integer numReq = null;
    public static Integer offSet = null;
    public static String status = null;
    public static String paramFile = null;
    public static ContextConfig cc = ContextConfig.getInstance();
    private static String context = ConfigDefaults.CTX_DEVELOPMENT;
    

    
    public static void main(String args[]) {

        try {
            parseArgs (args);
            cc.setContext(context);
            cc.setServiceName(ServiceNames.SVC_API);
            try {
                System.out.println("loading manifest from ./config/"+ConfigDefaults.MANIFEST);
                cc.loadManifest("./config/"+ConfigDefaults.MANIFEST); // manifest.yaml
                cc.setLog4j();
            } catch (ConfigException ex) {
                System.out.println("caught ConfigurationException " + ex.getMessage());
                System.exit(-1);
            }
            LOG =  Logger.getLogger(IDCTest.class); 
            String configFile =cc.getFilePath(ConfigDefaults.CONFIG);
            Map config = ConfigHelper.getConfiguration(configFile);
            assert config != null : "No configuration";
            Map authN = (Map) config.get("soap");
            URL host = new URL((String)authN.get("publishTo"));
            URL wsdl = cc.getWSDLPath(null);
            System.out.println("host is " + host.toString() + "wsdl is " + wsdl.toString());

            System.out.println ("Connecting to " + host);
            if ("0.6".equals(version)) {
                IDCClient06 client = IDCClient06.getClient (host, wsdl, authType);
                if (request.equals("setupPath")) {
                    try {
                        // Send a createPath query 
                        CreatePathContent query = new CreatePathContent();
                        if (gri != null)  {
                            query.setGlobalReservationId(gri);
                        }
                        query.setToken("TestToken");
                        Object[] req = new Object[]{query};
                        Object[] res = client.invoke("createPath",req);
                        CreatePathResponseContent response = (CreatePathResponseContent) res[0];
                        LOG.debug("Response: " + response.getGlobalReservationId() + " , " + response.getStatus());
                    } catch (OSCARSServiceException ex) {
                        OSCARSFault faultInfo = (OSCARSFault) ex.getFaultInfo();
                        if (faultInfo != null) {
                            LOG.debug("OSCARSServiceExeption type is  " + faultInfo.getMsg() + " message is " + 
                                      ex.getMessage() + " " + faultInfo.getDetails());
                        } else {
                            LOG.debug("OSCARSServiceExeption " + ex.getMessage());
                        }
                    }
                } else if (request.equals("teardownPath")) {
                    try {
                        // Send a teardownPath query 
                        TeardownPathContent query = new TeardownPathContent();
                        if (gri != null)  {
                            query.setGlobalReservationId(gri);
                        }
                        query.setToken("TestToken");
                        Object[] req = new Object[]{query};
                        Object[] res = client.invoke("teardownPath",req);
                        TeardownPathResponseContent response = (TeardownPathResponseContent) res[0];
                        LOG.debug("Response: " + response.getGlobalReservationId() + " , " + response.getStatus());
                    } catch (OSCARSServiceException ex) {
                        OSCARSFault faultInfo = (OSCARSFault) ex.getFaultInfo();
                        if (faultInfo != null) {
                            LOG.debug("OSCARSServiceExeption type is  " + faultInfo.getMsg() + " message is " + 
                                      ex.getMessage() + " " + faultInfo.getDetails());
                        } else {
                            LOG.debug("OSCARSServiceExeption " + ex.getMessage());
                        }
                    }
                } else if (request.equals("query")) {
                    try {
                        QueryResContent query = new QueryResContent();
                        query.setGlobalReservationId(gri);
                        Object[] req = new Object[]{query};
                        LOG.debug("calling client.invoke");
                        Object[] res = client.invoke("queryReservation",req);
                        QueryResReply reply = (QueryResReply)res[0];
                        ResDetails details = reply.getReservationDetails();
                        printResDetails(details);
                    } catch (OSCARSServiceException ex) {
                        OSCARSFault faultInfo = (OSCARSFault) ex.getFaultInfo();
                        if (faultInfo != null) {
                            LOG.debug("OSCARSServiceExeption type is  " + faultInfo.getMsg() + " message is " + 
                                      ex.getMessage() + " " + faultInfo.getDetails());
                        } else {
                            LOG.debug("OSCARSServiceExeption " + ex.getMessage());
                        }
                    } 
                } else if (request.equals("createReservation")) {
                        try {
                            // Send a createReservation query 
                            ResCreateContent query = new ResCreateContent();
                            if (paramFile != null)  {
                                query =configure(paramFile);
                            } else {
                                UserRequestConstraintType userConstraint = new UserRequestConstraintType();
                                long currentTime = System.currentTimeMillis() / 1000;
                                userConstraint.setStartTime(currentTime + 3600); // One hour after now.
                                userConstraint.setEndTime(currentTime + 7200); // Two hours after now.
                                userConstraint.setBandwidth(100);
                                Layer2Info layer2Info = new Layer2Info();
                                layer2Info.setSrcEndpoint("urn:ogf:network:domain=es.net:node=anl-mr2:port=xe-0/1/0:link=*");
                                layer2Info.setDestEndpoint("urn:ogf:network:domain=es.net:node=sdsc-sdn2:port=xe-0/0/0:link=*");
                                VlanTag srcVtag = new VlanTag();
                                srcVtag.setValue("3042");
                                srcVtag.setTagged(true);
                                VlanTag dstVtag = new VlanTag();
                                dstVtag.setValue("3042");
                                dstVtag.setTagged(true);
                                layer2Info.setSrcVtag(srcVtag);
                                layer2Info.setDestVtag(dstVtag);
                                PathInfo pathInfo = new PathInfo ();
                                pathInfo.setLayer2Info(layer2Info);
                                CtrlPlanePathContent pathContent = new CtrlPlanePathContent();
                                pathContent.setId("fakeID");
                                pathInfo.setPath(pathContent);
                                userConstraint.setPathInfo(pathInfo);
                                query.setDescription("IDCTest");
                                query.setUserRequestConstraint(userConstraint);
                            }                        
                            Object[] req = new Object[]{query};
                            Object[] res = client.invoke("createReservation",req);
                            CreateReply response = (CreateReply) res[0];
                            LOG.debug("Response: " + response.getGlobalReservationId() + " , " + response.getStatus());
                            System.out.println ("\n[createReservation]  gri= " + response.getGlobalReservationId() + 
                                                "\n                     transactionId=" + response.getMessageProperties().getGlobalTransactionId() +
                                                "\n                     status=" + response.getStatus());
                        } catch (OSCARSServiceException ex) {
                            OSCARSFault faultInfo = (OSCARSFault) ex.getFaultInfo();
                            if (faultInfo != null) {
                                LOG.debug("OSCARSServiceExeption type is  " + faultInfo.getMsg() + " message is " + 
                                          ex.getMessage() + " " + faultInfo.getDetails());
                            } else {
                                LOG.debug("OSCARSServiceExeption " + ex.getMessage());
                            }
                        }
                } else if (request.equals("modifyReservation")) {
                    try {
                        // Send a createReservation query 
                        ResCreateContent createCon = new ResCreateContent();
                        if (paramFile != null)  {
                            createCon =configure(paramFile);
                        } else {
                            System.out.println("Modify must be called with a parameter file");
                            System.exit(-1);
                        }
                        //hack
                        ModifyResContent query = new ModifyResContent();
                        query.setDescription(createCon.getDescription());
                        query.setUserRequestConstraint(createCon.getUserRequestConstraint());
                        query.setGlobalReservationId(gri);

                        Object[] req = new Object[]{query};
                        Object[] res = client.invoke("modifyReservation",req);
                        ModifyResReply response = (ModifyResReply) res[0];
                        LOG.debug("Response: " + response.getReservation().getGlobalReservationId() + " , " + 
                                response.getReservation().getStatus());
                        System.out.println ("[modifyReservation] completed gri= " + 
                                    response.getReservation().getGlobalReservationId() + " status=" + 
                                    response.getReservation().getStatus());
                    } catch (OSCARSServiceException ex) {
                        OSCARSFault faultInfo = (OSCARSFault) ex.getFaultInfo();
                        if (faultInfo != null) {
                            LOG.debug("OSCARSServiceExeption type is  " + faultInfo.getMsg() + " message is " + 
                                      ex.getMessage() + " " + faultInfo.getDetails());
                        } else {
                            LOG.debug("OSCARSServiceExeption " + ex.getMessage());
                        }
                    }
                } else if (request.equals("cancelReservation")){
                    try {
                        CancelResContent query = new CancelResContent();
                        query.setGlobalReservationId(gri);
                        Object[] req = new Object[]{query};
                        LOG.debug("calling client.invoke");
                        Object[] res = client.invoke("cancelReservation",req);
                        CancelResReply reply = (CancelResReply) res[0];
                        String status = reply.getStatus();
                        System.out.println("return from cancel is " + status);
                    } catch (OSCARSServiceException ex) {
                        OSCARSFault faultInfo = (OSCARSFault) ex.getFaultInfo();
                        if (faultInfo != null) {
                            LOG.debug("OSCARSServiceExeption type is  " + faultInfo.getMsg() + " message is " + 
                                      ex.getMessage() + " " + faultInfo.getDetails());
                        } else {
                            LOG.debug("OSCARSServiceExeption " + ex.getMessage());
                        }
                    } 
                } else if (request.equals("list")){
                    try {
                        ListRequest listReq = new ListRequest();
                        if (numReq != null) {
                            listReq.setResRequested(numReq);
                        }
                        if (offSet != null) {
                            listReq.setResOffset(offSet);
                        }
                        if (status != null) {
                            listReq.getResStatus().add(status);
                        }
                        Object [] req = new Object[]{listReq};
                        Object [] res = client.invoke("listReservations",req);
                        ListReply reply = (ListReply) res[0];
                        List <ResDetails> resDetailsList = reply.getResDetails();
                        for (ResDetails resDetails: resDetailsList) {
                            printResDetails(resDetails);
                        }
                    } catch (OSCARSServiceException ex) {
                        OSCARSFault faultInfo = (OSCARSFault) ex.getFaultInfo();
                        if (faultInfo != null) {
                            LOG.debug("OSCARSServiceExeption type is  " + faultInfo.getMsg() + " message is " + 
                                      ex.getMessage() + " " + faultInfo.getDetails());
                        } else {
                            LOG.debug("OSCARSServiceExeption " + ex.getMessage());
                        }
                    }
                } else if (request.equals("notify")) {
                    try {
                        EventContent event =  new EventContent();
                        event.setType("test");
                        long currentTime = System.currentTimeMillis() / 1000;
                        event.setTimestamp(currentTime);
                        event.setId("12345");
                        Object [] req = new Object[]{event};
                        Object [] res = client.invoke("Notify",req);
                        LOG.debug("returned from notify");
                    } catch (OSCARSServiceException ex) {
                        OSCARSFault faultInfo = (OSCARSFault) ex.getFaultInfo();
                        if (faultInfo != null) {
                            LOG.debug("OSCARSServiceExeption type is  " + faultInfo.getMsg() + " message is " + 
                                      ex.getMessage() + " " + faultInfo.getDetails());
                        } else {
                            LOG.debug("OSCARSServiceExeption " + ex.getMessage());
                        }
                    }
                } else {
                    System.out.println("unrecognized command " + request);
                }
            } else if ("0.5".equals(version)) {
                try {
                    // Send a createPath query 
                    IDCClient05 client = IDCClient05.getClient (host, wsdl, authType);
                    net.es.oscars.api.soap.gen.v05.CreatePathContent query = new net.es.oscars.api.soap.gen.v05.CreatePathContent();
                    query.setGlobalReservationId(args[3]);
                    query.setToken("TestToken");

                    Object[] req = new Object[]{query};
                    Object[] res = client.invoke("createPath",req);

                    net.es.oscars.api.soap.gen.v05.CreatePathResponseContent response = (net.es.oscars.api.soap.gen.v05.CreatePathResponseContent) res[0];
                    LOG.debug("Response: " + response.getGlobalReservationId() + " , " + response.getStatus()); 
                } catch (OSCARSServiceException ex) {
                    OSCARSFault faultInfo = (OSCARSFault) ex.getFaultInfo();
                    if (faultInfo != null) {
                        LOG.debug("OSCARSServiceExeption type is  " + faultInfo.getMsg() + " message is " + 
                                  ex.getMessage() + " " + faultInfo.getDetails());
                    } else {
                        LOG.debug("OSCARSServiceExeption " + ex.getMessage());
                    }
                }
            }
        } catch (Exception ee) {
            //LOG.debug (ee.toString());
            System.out.println( "IDCTest Caught exception " + ee.toString());
            ee.printStackTrace();
        }
        System.exit(0);
    }
    public static void createResUsage() {
        System.out.println("usage  createReservation -pf <parameter_file>");
        System.out.println("\t parameter_file: a yaml file containing the parameters for reservation creation");
        System.out.println("\t see api/src/test/resources/autoTD1.yaml for an example file");
        System.exit(0);
    }
    public static void queryUsage() {
        System.out.println("usage  query -gri <gri> ");
        System.out.println("\t gri: global reservation id of reservation");
        System.exit(0);
    }
    public static void listResUsage() {
        System.out.println("usage  list  -n <numReq> -o <offset> -st <status>");
        System.out.println("\t numReq: number of reservations requested, optional defaults to all");
        System.out.println("\t offset: reservation at which to start list, optional defaults to 0");
        System.out.println("\t status: limit reservations to those with this status, optional defaults to all");
        System.exit(0);
    }
    public static void modifyResUsage() {
        System.out.println("usage  modifyReservation -pf <parameter_file>");
        System.out.println("\t parameter_file: a yaml file containing the parameters to modify the reservation");
        System.out.println("\t see api/src/test/resources/autoTD1.yaml for an example file, only the gri must be set to the reservation that is to be modified");
        System.exit(0);
    }
    public static void cancelResUsage() {
        System.out.println("usage  cancelReservation -gri <gri> ");
        System.out.println("\t gri: global reservation id of reservation to cancel");
        System.exit(0);
    }
    public static void setupPathUsage() {
        System.out.println("usage  setupPath -gri <gri> ");
        System.out.println("\t gri: global reservation id of reservation for which to setup path");
        System.exit(-1);
    }
    public static void teardownPathUsage() {
        System.out.println("usage  teardownPath -gri <gri> ");
        System.out.println("\t gri: global reservation id of reservation whose path is to be torndown");
        System.exit(0);
    }
    public static void parseArgs(String args[])  throws java.io.IOException {
        // create a parser
        OptionParser parser = new OptionParser();
        parser.acceptsAll( asList( "h", "?" ), "show help then exit" );
        OptionSpec<String> VERS = parser.accepts("v", "protocal version: 0.5,0.6").withRequiredArg().ofType(String.class);
        OptionSpec<String> AUTHTYPE = parser.accepts("a", "authType:x509,UT").withRequiredArg().ofType(String.class);
        OptionSpec<String> CMD = parser.accepts("c", "command:createReservation,cancelReservation,modifyReservation,setupPath,teardownPath,query,list,notify").withRequiredArg().ofType(String.class);
        OptionSpec<String> GRISPEC =  parser.accepts( "gri","globalReservationId" ).withRequiredArg().ofType( String.class );
        OptionSpec<Integer> NUM =parser.accepts( "n", "number of reservations requested").withRequiredArg().ofType( Integer.class );
        OptionSpec<Integer> OFFSET =parser.accepts( "o", "offset for reservations requested").withRequiredArg().ofType( Integer.class );
        OptionSpec<String> STATUS = parser.accepts("st", "status of reservations requested").withRequiredArg().ofType(String.class);
        OptionSpec<String> PARAMS = parser.accepts("pf", "parameter file for createReservation" ).withRequiredArg().ofType(String.class);
        OptionSpec<String> CONTEXT = parser.accepts("C", "context:UNITTEST,DEVELOPMENT,SDK,PRODUCTION").withRequiredArg().ofType(String.class);

        OptionSet options = parser.parse( args );

        if (options.has (CMD)){
            request = options.valueOf(CMD);
        } else {
            System.out.println("usage: must be called with a command option: -c <cmd>");
            System.out.println(" cmd is one of: createReservation,cancelReservation,modifyReservation,setupPath,teardownPath,query,list");
            System.exit(-1);
        }
        if ( options.has( "?" ) || options.has("h")) {
            if (request.equals("createReservation")) {
                createResUsage();
            }
            if (request.equals("cancelReservation")) {
                cancelResUsage();
            }
            if (request.equals("modifyReservation")) {
                modifyResUsage();
            }
            if (request.equals("setupPath")) {
                setupPathUsage();
            }
            if (request.equals("teardownPath")) {
                teardownPathUsage();
            }
            if (request.equals("list")) {
                listResUsage();
            }
            if (request.equals("query")) {
                queryUsage();
            }
            System.out.println("Unrecognized command: " + request);
            System.out.println(" cmd must be one of: createReservation,cancelReservation,modifyReservation,setupPath,teardownPath,query,list");
            System.exit(-1);
        }
        if (options.has(VERS)){
            version = options.valueOf(VERS);
        } else {
            version = "0.6";
        }
        if (options.has(AUTHTYPE)){
            authType = options.valueOf(AUTHTYPE);
        } else {
            authType="x509";
        }
        
        if (options.has(CONTEXT) ){
            context = options.valueOf(CONTEXT);
            if (!context.equals("UNITTEST") &&
                    !context.equals("SDK") &&
                    !context.equals("DEVELOPMENT") &&
                    !context.equals("PRODUCTION") ) 
            {
                System.out.println("unrecognized CONTEXT value: " + context);
                System.exit(-1);
            }
        } 
        if (options.has("gri")) {
            gri=options.valueOf(GRISPEC);
        }
        if (options.has("n")){
            numReq = options.valueOf(NUM);
        }
        if (options.has("o")) {
            offSet = options.valueOf(OFFSET);
        }
        if (options.has("st")){
            status = options.valueOf(STATUS);
        }
        if (options.has("pf")) {
            paramFile = options.valueOf(PARAMS);
        }
        if (request.equals("query") && gri == null){
            System.out.println("query command requires gri argument");
            System.exit(-1);
        }
    }
    
    @SuppressWarnings("unchecked")
    public static ResCreateContent configure(String configFile) {
        if (configFile == null) {
            configFile = DEFAULT_CONFIG_FILE;
        }

        Map config = ConfigHelper.getConfiguration(configFile);
        Map store = (Map) config.get("create");

        ResCreateContent resContent = new ResCreateContent();;
        String gri = (String) store.get("gri");
        String login = (String) store.get("login");
        String layer = (String)store.get("layer");
        Integer bandwidth = (Integer) store.get("bandwidth");
        Integer burstLimit = (Integer) store.get("burstLimit");
        String lspClass = (String) store.get("lspClass");
        String src = (String) store.get("src");
        String dst = (String) store.get("dst");
        String description = (String) store.get("description");
        String srcVlan = (String) store.get("srcvlan");
        String dstVlan = (String) store.get("dstvlan");
        String start_time = (String) store.get("start-time");
        String end_time = (String) store.get("end-time");
        ArrayList<String> pathArray = (ArrayList<String>) store.get("path");
        String pathSetupMode = (String) store.get("path-setup-mode");
        String pathType = (String) store.get("path-type");

        if (!layer.equals("2") && !layer.equals("3")) {
            die("Layer must be 2 or 3");
        }
        if (src == null || src.equals("")) {
            die("Source must be specified");
        }
        if (dst == null || dst.equals("")) {
            die("Destination must be specified");
        }
        if (bandwidth == null) {
            die("bandwidth must be specified");
        }
        if (description == null || description.equals("")) {
            die("description must be specified");
        }
        HashMap<String, Long> times = parseTimes(start_time, end_time);

        //if (gri != null && gri.length() != 0) {
        if (gri != null ) { 
            resContent.setGlobalReservationId(gri);
        }
        resContent.setDescription(description);
        UserRequestConstraintType uc = new UserRequestConstraintType();
        uc.setBandwidth(bandwidth);
        uc.setStartTime(times.get("start"));
        uc.setEndTime(times.get("end"));
        PathInfo pathInfo =  new PathInfo();
        pathInfo.setPathSetupMode(pathSetupMode);
        pathInfo.setPathType(pathType);
        if (layer.equals("2")) {
            Layer2Info layer2Info = new Layer2Info();
            layer2Info.setSrcEndpoint(src);
            layer2Info.setDestEndpoint(dst);
            if (srcVlan != null) {
                VlanTag vlan = new VlanTag();
                vlan.setValue(srcVlan);
                vlan.setTagged(true);
                layer2Info.setSrcVtag(vlan);
            }
            if (dstVlan != null) {
                VlanTag vlan = new VlanTag();
                vlan.setValue(dstVlan);
                vlan.setTagged(true);
                layer2Info.setDestVtag(vlan);
            }
            pathInfo.setLayer2Info(layer2Info);
        } else if (layer.equals("3")) {
            // TODO
            if (burstLimit !=  null && burstLimit != 0){
                MplsInfo mplsInfo = new MplsInfo();
                mplsInfo.setBurstLimit(burstLimit);
                mplsInfo.setLspClass(lspClass);
                pathInfo.setMplsInfo(mplsInfo);
            }
        }
        if ( pathArray != null && !pathArray.isEmpty() ) {
            CtrlPlanePathContent path = new CtrlPlanePathContent ();
            List<CtrlPlaneHopContent> hops = path.getHop();
            for (String hop : pathArray) {
               CtrlPlaneHopContent cpHop = new CtrlPlaneHopContent();
               cpHop.setLinkIdRef(hop);
               hops.add(cpHop);
            }
            pathInfo.setPath(path);
        }
        uc.setPathInfo(pathInfo);
        resContent.setUserRequestConstraint(uc);
        return resContent;
    }
     
    private static void die(String msg) {
        System.err.println(msg);
        System.exit(1);
    }
    private static HashMap<String, Long> parseTimes(String start_time, String end_time) {
        HashMap<String, Long> result = new HashMap<String, Long>();
        Long startTime = 0L;
        Long endTime = 0L;
        Long createTime= System.currentTimeMillis()/1000;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if (start_time == null || start_time.equals("now") || start_time.equals("")) {
            startTime = System.currentTimeMillis()/1000;
        } else {
            try {
                startTime = df.parse(start_time.trim()).getTime()/1000;
            } catch (java.text.ParseException ex) {
                die("Error parsing start date: "+ex.getMessage());
            }
        }
        if (end_time == null || end_time.equals("")) {
            die("No end time specified.");
        } else if (end_time.startsWith("+")) {
            String[] hm = end_time.substring(1).split("\\:");
            if (hm.length != 3) {
                die("Error parsing end date format");
            } 
            try {
                Integer seconds = Integer.valueOf(hm[0])*3600*24; //days
                seconds += Integer.valueOf(hm[1])*3600; // hours
                seconds += Integer.valueOf(hm[2])*60; // minutes
                if (seconds < 60) {
                    die("Duration must be > 60 sec");
                }
                endTime = startTime + seconds;
            } catch (NumberFormatException ex) {
                die("Error parsing end date format: "+ex.getMessage());
            }
        } else {
            try {
                endTime = df.parse(end_time.trim()).getTime()/1000;
            } catch (java.text.ParseException ex) {
                die("Error parsing emd date: "+ex.getMessage());
            }
        }
        
        
        result.put("start", startTime);
        result.put("end", endTime);
        result.put("create", createTime);
        return result;
    }

    /**
     * print out all information about this reservation
     * @param resDetails Must contain a userConstraint, may contain a reservedConstaint
     *       if reservedConstraint exists, use info from it, otherwise use userConstraint.
     *       The pathInfo element may contain one of layer2Info or layer3Info. It may also contain
     *       a path structure. If the path structure exists the info from it will be used rather than
     *       data from layer2Info. Layer3 is currently not implemented
     */
    public static void printResDetails(ResDetails resDetails) {
        System.out.println("\nGRI: " + resDetails.getGlobalReservationId());
        System.out.println("Login: " + resDetails.getLogin());
        System.out.println("Description: " + resDetails.getDescription());
        System.out.println("Status: "
                + resDetails.getStatus().toString());
        UserRequestConstraintType uConstraint = resDetails.getUserRequestConstraint();
        System.out.println("startTime: " + new Date(uConstraint.getStartTime()*1000).toString());
        System.out.println("endTime: " + new Date(uConstraint.getEndTime()*1000).toString());
        System.out.println("bandwidth: " + Integer.toString(uConstraint.getBandwidth()));
        PathInfo pathInfo = null;
        String pathType = null;
        ReservedConstraintType rConstraint = resDetails.getReservedConstraint();
        if (rConstraint !=  null) {
            pathInfo=rConstraint.getPathInfo();
            pathType = "reserved";
        } else {
            uConstraint = resDetails.getUserRequestConstraint();
            if (uConstraint ==null) {
                System.out.println("invalid reservation, no reserved or requested path");
                return;
            }
            pathInfo=uConstraint.getPathInfo();
            pathType="requested";
            System.out.println("no path reserved, using requested path ");
        }
        Layer3Info layer3Info = pathInfo.getLayer3Info();
        if (layer3Info != null) {
            System.out.println("Source host: " +
                    layer3Info.getSrcHost());
            System.out.println("Destination host: " +
                    layer3Info.getDestHost());
        }
        CtrlPlanePathContent path = pathInfo.getPath();
        if (path != null) {
            List<CtrlPlaneHopContent> hops = path.getHop();
            if (hops.size() > 0) {
                System.out.println("Hops in " + pathType + " path are:");
                for ( CtrlPlaneHopContent ctrlHop : hops ) {
                    CtrlPlaneLinkContent link = ctrlHop.getLink();
                    String vlanRangeAvail = "any";
                    if (link != null ) {
                        CtrlPlaneSwcapContent swcap= link.getSwitchingCapabilityDescriptors();
                        if (swcap != null) {
                            CtrlPlaneSwitchingCapabilitySpecificInfo specInfo = swcap.getSwitchingCapabilitySpecificInfo();
                            if (specInfo != null) {
                                vlanRangeAvail = specInfo.getVlanRangeAvailability(); 
                            }
                        }
                        System.out.println(link.getId() + " vlanRange: " + vlanRangeAvail);
                    } else {
                        String id = ctrlHop.getLinkIdRef();
                        System.out.println(id);
                    }
                }
            }
            else {
                Layer2Info layer2Info = pathInfo.getLayer2Info();
                if (layer2Info != null) {
                    String vlanRange = "any";
                    if (layer2Info.getSrcVtag() != null) {
                        vlanRange = layer2Info.getSrcVtag().getValue();
                    }
                    System.out.println("Source urn: " +
                            layer2Info.getSrcEndpoint() + " vlanTag:" + vlanRange);
                    vlanRange = "any";
                    if (layer2Info.getDestVtag() != null) {
                        vlanRange = layer2Info.getDestVtag().getValue();
                    }
                    System.out.println("Destination urn: " +
                            layer2Info.getDestEndpoint() + " vlanTag:" + vlanRange);
                }
            }
        } else {
            System.out.println("no path information in " + pathType + " constraint");
        }
    }
}

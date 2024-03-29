package net.es.oscars.coord.runtimepce;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import java.lang.ref.WeakReference;

import org.apache.log4j.Logger;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.SubnodeConfiguration;
import org.ogf.schema.network.topology.ctrlplane.CtrlPlanePathContent;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;

import net.es.oscars.coord.common.Coordinator;
import net.es.oscars.coord.common.PathComputationMutex;
import net.es.oscars.coord.jobs.PCERuntimeActionCleanerJob;

import net.es.oscars.api.soap.gen.v06.ResDetails;
import net.es.oscars.api.soap.gen.v06.ReservedConstraintType;
import net.es.oscars.api.soap.gen.v06.PathInfo;
import net.es.oscars.coord.req.CoordRequest;
import net.es.oscars.coord.runtimepce.PCEData;
import net.es.oscars.utils.sharedConstants.StateEngineValues;
import net.es.oscars.utils.config.ContextConfig;
import net.es.oscars.utils.config.ConfigException;
import net.es.oscars.utils.svc.ServiceNames;
import net.es.oscars.utils.topology.PathTools;
import net.es.oscars.utils.soap.OSCARSServiceException;
import net.es.oscars.coord.actions.CoordAction;
import net.es.oscars.coord.actions.RMUpdateStatusAction;
import net.es.oscars.coord.actions.RMStoreAction;
import net.es.oscars.coord.actions.CommittedForwarder;
import net.es.oscars.coord.actions.CreateResvForwarder;
import net.es.oscars.logging.ErrSev;
import net.es.oscars.logging.OSCARSNetLogger;
import net.es.oscars.logging.ModuleName;


/**
 * PCERuntimeAction is an Action that is responsible for reading the PCE configuration file.
 * It keeps a static hashMap of all the runTimeActions and associated GRIs.
 * It is responsible for creating proxyActions for all the Aggregators and PCEs that are described
 * in the PCE configuration file.
 *
 * @author lomax
 *
 */
public class PCERuntimeAction extends CoordAction <PCEData, PCEData> implements Runnable {

    private static final long serialVersionUID = 1439115737928915954L;
    private static final String moduleName = ModuleName.PCERUNTIME;

    public static final String PCE_CONFIGURATION = "pce-config.xml";
    private static Logger LOG = Logger.getLogger(PCERuntimeAction.class.getName());

    private OSCARSNetLogger netLogger = null;
    private static boolean pceServiceStarted = false;
    private HashMap<String, ProxyAction> pces = new HashMap<String, ProxyAction>();
    private static HashMap<String, WeakReference<PCERuntimeAction>> pceRuntimes = new HashMap<String, WeakReference<PCERuntimeAction>>();
    private static Thread serverThread = null;
    private static XMLConfiguration config = null;
    private String requestType = null;
    private String transactionId = null; // TODO put in coordAction
    private static ContextConfig cc = null;
    private static PathComputationMutex pceMutex = new PathComputationMutex();

    // Start the background thread that will prune empty entries in the actions map.
    private static long CLEANER_REPEAT = (15 * 60 * 1000); // 15 minutes
    
    static {
        SimpleTrigger jobTrigger = new SimpleTrigger("PCERuntimeAction.Cleaner",
                                                     null,
                                                     SimpleTrigger.REPEAT_INDEFINITELY,
                                                     PCERuntimeAction.CLEANER_REPEAT);
        
        JobDetail     jobDetail  = new JobDetail("PCERuntimeAction.Cleaner", null, PCERuntimeActionCleanerJob.class);
        jobDetail.setVolatility(false);
        
        try {
            Coordinator.getInstance().getScheduler().scheduleJob(jobDetail, jobTrigger);
        } catch (SchedulerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (OSCARSServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }     
    }
    
    /**
     * Constructor - called from CreateReservationRequest, cancelRequest and notifyRequest
     *
     * @param name Set by caller to identify this action : e.g., createReservation-Query-PCERuntimeAction
     * @param request the parent CoordRequest
     * @param data requestData to be passed between PCEs, if null may be set by setRequestData
     * @param transaction a global id for this IDC request, was passed to Coordinator in the request message
     * @param requestType "pceCreate", pceCommit, pceCancel
     */
    @SuppressWarnings("unchecked")
    public PCERuntimeAction (String name, 
                             CoordRequest request, 
                             PCEData data, 
                             String transactionId,
                             String requestType) {
        
        super (name, request, data);
        LOG = Logger.getLogger(PCERuntimeAction.class.getName());
        // I think this is only needed by the execute method  -- mrt
        this.netLogger = new OSCARSNetLogger(moduleName, transactionId);
        this.netLogger.setGRI(request.getGRI());
        OSCARSNetLogger.setTlogger(this.netLogger);
        LOG.debug(netLogger.start("constructor", requestType));
        this.requestType = requestType;
        this.transactionId = transactionId;
        cc = ContextConfig.getInstance();
        // Read the PCE configuration file
        try {
            PCERuntimeAction.readConfiguration(this.netLogger);
        } catch (OSCARSServiceException e) {
            LOG.error(netLogger.error("constructor",ErrSev.MAJOR,
                                       "caught OSCARSServcieException " + e.getMessage()));
            System.exit(-1);
        }
        
        // Make sure that the PCERuntimeService is running
        if (PCERuntimeAction.serverThread == null) {
            PCERuntimeAction.serverThread = new Thread (this);
            PCERuntimeAction.serverThread.start();
        }
    }

    public static ProxyAction getProxyAction (String gri, String pceName, String requestType) {

        OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
        String event = "getProxyction";
        PCERuntimeAction pceRuntime = PCERuntimeAction.getPCERuntimeAction(gri, requestType);
        if (pceRuntime == null) {
            // This should not happen.
            LOG.error(netLogger.error(event,ErrSev.MAJOR, 
                                      "ProxyAction.getProxyAction unexpected error: no PCERuntime for  gri= " + 
                                      gri + " pceName= " + pceName + " requestType= " + requestType));
            throw new RuntimeException ("PCERuntimeAction is missing for GRI: " + gri + " requestType= " + requestType);
        }

        ProxyAction pce = null;
        synchronized (pceRuntime.pces) {
            pce = pceRuntime.pces.get(gri + "-" + pceName + "-" + requestType);
            // pce may be null if it does not exist
        }
        return pce;
    }

    public static void setProxyAction (String gri, String pceName, String requestType, ProxyAction pce) {
        PCERuntimeAction pceRuntime = PCERuntimeAction.getPCERuntimeAction(gri, requestType);
        if (pceRuntime == null) {
            // This should not happen.
            throw new RuntimeException ("PCERuntimeAction is missing for GRI: " + gri + " requestType= " + requestType);
        }
        synchronized (pceRuntime.pces) {
            pce = pceRuntime.pces.put (gri + "-" + pceName+ "-" + requestType, pce);
        }
    }

    public static PCERuntimeAction getPCERuntimeAction (String gri, String requestType) {
        
        OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
        String event = "getPCERunTimeAction";
        PCERuntimeAction pceRuntime =  null;
        String key = gri + "-" + requestType;
        synchronized (PCERuntimeAction.pceRuntimes) {
            WeakReference<PCERuntimeAction> ref =  PCERuntimeAction.pceRuntimes.get(key);
            if (ref != null) {
                pceRuntime = ref.get();
                if (pceRuntime == null) {
                    LOG.error(netLogger.error(event, ErrSev.MAJOR, "No PCERuntimeAction (1) for GRI: " + 
                                              gri + " requestType= " + requestType));
                    // No runtime for this GRI This should not happen
                    throw new RuntimeException ("No PCERuntimeAction for GRI: " + 
                                                 gri + " requestType= " + requestType);
                }
            } else {
                LOG.error(netLogger.error(event, ErrSev.MAJOR,"No PCERuntimeAction (2) for GRI: " + 
                                          gri + " requestType= " + requestType));
                throw new RuntimeException ("No PCERuntimeAction for GRI: " + gri + " requestType= " + requestType);
            }
        }
        return pceRuntime;
    }

    public void setPCERuntimeAction (String requestType) {
        String key = this.getCoordRequest().getGRI() + "-" + requestType;
        synchronized (PCERuntimeAction.pceRuntimes) {
            PCERuntimeAction.pceRuntimes.put (key, new WeakReference<PCERuntimeAction>(this));
        }
    }

    /**
     * Invoke the PCE through the PCE generic client. The request is sent asynchronously.
     * The reply will be returned in a PCEReply message sent from the PCE back to PCERuntimeSoapHandler. 
     */
    public void execute() {
        // Set the netLogger for this thread from the one stored in the constructor
        OSCARSNetLogger.setTlogger(this.netLogger); 
        String event = "execute";
        LOG.debug(this.netLogger.start(event,this.requestType));
 
        // First make sure that there is not another PCERuntimeAction that holds the PCE lock
        try {
            PCERuntimeAction.pceMutex.get (this.getCoordRequest());
        } catch (InterruptedException e) {
            // Should not happen
            LOG.error(this.netLogger.error(event,ErrSev.MINOR,
                                      "PCERuntime was interrupted when trying to acquire PCE mutex: " + e));
            this.fail (e, StateEngineValues.FAILED + ":" + e.getMessage());
            return;
        }
        // Register this PCERuntime instance
        this.setPCERuntimeAction (this.requestType);
        // Create the graph of PCE modules.
        this.createPCE (PCERuntimeAction.config.configurationAt("PCE"), null);
        // Set PCE Data to the children PCEProxyAction
        PCEData pceData = this.getRequestData();
        //normalize pce data
        PCEReqFormatter.getInstance().normalize(pceData);

        for (CoordAction<PCEData,PCEData> pce : this) {
            pce.setRequestData(pceData);
        }

        // Create, set and invoke the RMUpdateStatus
        String newState = null;
        if (this.requestType.equals("pceCreate")){
            newState = StateEngineValues.INPATHCALCULATION;
        } else if (this.requestType.equals("pceCommit")){
            newState = StateEngineValues.INCOMMIT;
        } else if (this.requestType.equals("pceCancel")){
            newState = StateEngineValues.INCANCEL;
        } else if (this.requestType.equals("pceModify")){
            newState = StateEngineValues.INMODIFY;
        }
        LOG.debug(netLogger.getMsg(event,"Calling RMUpdateStatus with state "  + newState));
        RMUpdateStatusAction rmUpdateStatusAction = new RMUpdateStatusAction (this.getName() + "-RMUpdateStatusAction",
                                                                              this.getCoordRequest(),
                                                                              this.getCoordRequest().getGRI(),
                                                                              newState);
        rmUpdateStatusAction.execute();
        
        if (rmUpdateStatusAction.getResultData() != null) {
            LOG.debug(netLogger.getMsg(event,"State was set to " + rmUpdateStatusAction.getResultData().getStatus()));
        } else {
            LOG.error(netLogger.error(event,ErrSev.MINOR, "rmUpdateStatus resultData is null."));
        }
        this.executed();
        LOG.debug(this.netLogger.end(event));
    }

    public void executed() {
        super.executed();
    }
    /**
     * Creates either a PCEProxyAction, or an AggProxyAction.  Links it to the parentProxy
     * if there is one, or to the PCERuntime action if this is the first action
     *
     * @param parent -previous proxyAction, may be null if this is the first action to be executed
     * @param name - name of service, used for tracking messages, taken from configuration file
     * @param pathTag
     * @param neededTags
     * @param role - PCE or AGGREGATOR
     * @param proxyEndpoint - address of proxy callback for PCE replies
     * @param pceEndpoint - address of PCE service to be called
     * @param transactionId - global id for this IDC transaction
     * @return - either a  new PCEProxyAction or AggProxyAction
     */
    private ProxyAction createProxyAction (ProxyAction parent,
                                           String name,
                                           String pathTag,
                                           List<String> neededTags,
                                           ProxyAction.Role role,
                                           String proxyEndpoint,
                                           String pceEndpoint,
                                           String transactionId) {
        ProxyAction proxyAction = null;

        if (role == ProxyAction.Role.PCE) {
            proxyAction = new PCEProxyAction (parent,
                                              this.getCoordRequest(),
                                              this,
                                              name,
                                              pathTag,
                                              proxyEndpoint,
                                              pceEndpoint,
                                              transactionId,
                                              this.requestType);

        } else if (role == ProxyAction.Role.AGGREGATOR) {
            proxyAction = new AggProxyAction (parent,
                                              this.getCoordRequest(),
                                              this,
                                              name,
                                              pathTag,
                                              neededTags,
                                              proxyEndpoint,
                                              pceEndpoint,
                                              transactionId,
                                              this.requestType);
        }
        if (parent != null) {
            // Add this PCE Proxy to its parent
            parent.add (proxyAction);
        } else {
            // This is the top PCE. Add it to the PCERuntimeAction
            this.add (proxyAction);
        }
        return proxyAction;
    }

    /**
     * Steps through the PCE configuration (previously loaded by readConfiguration),
     * recursively creating PCEProxyActions for each of the configured PCEs or Aggregators.
     *
     * @param conf - configuration structure previously loaded by readConfiguration from PCE_CONFIGURATION
     * @param parentPce - the parent PCE proxyAction, null if this is the coordRequest
     * @param transactionId - 
     */
    @SuppressWarnings("unchecked")
    private void createPCE (HierarchicalConfiguration conf, 
                            ProxyAction parentPce) {
        if (conf == null) {
            // Safety
            return;
        }
        String pceName       = conf.getString("Bindings.Name");
        String role          = conf.getString("Bindings.Role");
        String pceEndpoint   = conf.getString("Bindings.Endpoint");
        String proxyEndpoint = conf.getString("Bindings.ProxyEndpoint");
        String pathTag       = conf.getString("Bindings.PathTag");
        List<String> neededTags = conf.getList("Bindings.NeedsPathTag");

        ProxyAction.Role pceRole = null;
        if (role.equals("Aggregator")) {
            pceRole = ProxyAction.Role.AGGREGATOR;
        } else if (role.equals("PCE")) {
            pceRole = ProxyAction.Role.PCE;
        }

        // Create the Proxy Action object for this PCE
        ProxyAction myProxyAction = createProxyAction (parentPce,
                                                       pceName,
                                                       pathTag,
                                                       neededTags,
                                                       pceRole,
                                                       proxyEndpoint,
                                                       pceEndpoint,
                                                       this.transactionId);
        SubnodeConfiguration aggNode = null;
        try {
            aggNode = conf.configurationAt("Aggregates");
        } catch (java.lang.IllegalArgumentException e) {
            // No Aggregate token
            return;
        }
        if (aggNode == null) {
            // This PCE does not have any child PCE. Return;
            return;
        }
        List<SubnodeConfiguration> pceNodes =  aggNode.configurationsAt("PCE");
        if (pceNodes == null) {
            return;
        }
        for (SubnodeConfiguration node: pceNodes) {
            this.createPCE (node, myProxyAction);
        }
    }

    private static void readConfiguration (OSCARSNetLogger netLogger) throws OSCARSServiceException {
        try {
            if (PCERuntimeAction.config == null) {
                LOG.debug(netLogger.start("readConfig","config is " + PCERuntimeAction.config));
                String fileName = cc.getFilePath(ServiceNames.SVC_PCERUNTIME, cc.getContext(),
                        PCERuntimeAction.PCE_CONFIGURATION);
                PCERuntimeAction.config = new XMLConfiguration(fileName);
            }
        } catch (ConfigException e) {
            throw new OSCARSServiceException(e.getMessage());
        } catch (ConfigurationException e){
            throw new OSCARSServiceException(e.getMessage());
        }
    }

    public void run() {
        try {
            this.startPCERuntimeService();
        } catch (OSCARSServiceException e) {
            LOG.error(this.netLogger.error("run",ErrSev.MAJOR,
                                           "PCERuntimeAction.run caught exception:" + e.getMessage()));
        }
    }

    private synchronized void startPCERuntimeService () throws OSCARSServiceException {
        if (! PCERuntimeAction.pceServiceStarted) {
            PCERuntimeSoapServer.getInstance().startServer(false);
            PCERuntimeAction.pceServiceStarted = true;
        }
    }

    /**
     * Called by PCEProxyAction or AggProxyActions when the path operation is complete.
     * The path operation should be one of pceCreate, pceCommit, pceCancel or pceModify
     * Needs to store the reservation (or status in the case of cancel)
     * in the resourceManager. 
     * If action is create or modify and this is the last domain needs to start the
     * commit phase.
     * If the reservation is interdomain and this is the last domain needs to send a Notify message 
     * that action is completed to previous domain.
     * In the interdomain case and not last domain needs to forward request to next domain.
     *
     * @param data PCEdata returned by the top pce aggregator
     * @param srcPce the PCERuntimeAction that started the path calculation
     */
    public void setResultData (PCEData data, ProxyAction srcPce) {
        String method ="PCERuntimeAction.setResultData";
        OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
        String requestType = srcPce.getRequestType();
        LOG.debug(netLogger.start(method," for " + requestType));
        //LOG.debug(netLogger.getMsg(method, " state of this PCERuntime action is " + this.getState() ));

        boolean localRes = true;
        String localDomain = PathTools.getLocalDomainId();
        boolean lastDomain = true;
        boolean firstDomain = true;
        ResDetails resDetails = null;
        CtrlPlanePathContent reservedPath = PCERuntimeAction.getReservedPathFromPceData(data);
        try {
            if (reservedPath != null) {
                localRes = PathTools.isPathLocalOnly(reservedPath);
                String domain = PathTools.getLastDomain(reservedPath);
                lastDomain = localDomain.equals(domain);
                domain = PathTools.getFirstDomain(reservedPath);
                firstDomain = localDomain.equals(domain);
            }
        } catch (OSCARSServiceException e) {
            LOG.error (netLogger.error(method, ErrSev.MINOR,"Cannot process PCEData " + e));
            this.fail(e, StateEngineValues.FAILED + ":" + e.getMessage());
            return;
        }
        LOG.debug(netLogger.getMsg(method,"\n\tlocalRes= " + (localRes ? "local-only" : "inter-domain") + "\n\tlocalDomain= " + localDomain +
                  "\n\tfirst domain= " + firstDomain + "\n\t" + "last domain= " + lastDomain));
        
        resDetails = pceData2ResDetails( data, srcPce,localRes, firstDomain, lastDomain);

        
        if (requestType.equals("pceCreate")) {   // ********* PCE CREATE *********
            if (localRes) {
                LOG.debug(netLogger.getMsg(method,"coordRequest is "+ this.getCoordRequest()));
                // This path is intra-domain, we can commit now.
                PCERuntimeAction pceRuntimeAction = new PCERuntimeAction (this.getName() + "-Commit-PCERuntimeAction",
                                                                          this.getCoordRequest(),
                                                                          null,
                                                                          this.transactionId,
                                                                          "pceCommit");
                PCEData pceData = new PCEData(resDetails.getUserRequestConstraint(),
                                              resDetails.getReservedConstraint(),
                                              resDetails.getOptionalConstraint(),
                                              null);
                pceRuntimeAction.setRequestData(pceData);
                this.add(pceRuntimeAction);
                //LOG.debug(netLogger.getMsg(method,"pceruntime action state is "+ pceRuntimeAction.getState()));
            } else {
                if (lastDomain) {
                    // This is the last domain, we can commit now.
                    PCERuntimeAction pceRuntimeAction = new PCERuntimeAction (this.getName() + "-Commit-PCERuntimeAction",
                                                                              this.getCoordRequest(),
                                                                              null,
                                                                              this.transactionId,
                                                                              "pceCommit");

                    PCEData pceData = new PCEData(resDetails.getUserRequestConstraint(),
                                                  resDetails.getReservedConstraint(),
                                                  resDetails.getOptionalConstraint(),
                                                  null);
                    
                    pceRuntimeAction.setRequestData(pceData);
                    this.add(pceRuntimeAction);                     
                } else {
                    // We need to forward to the next IDC
                    CreateResvForwarder forwarder = new CreateResvForwarder (this.getName() + "-CreateResv",
                                                                             this.getCoordRequest(),
                                                                             resDetails);
                    forwarder.execute();
                }
                
            }
        } else if (requestType.equals("pceCommit")) { // ********* PCE COMMIT *********
            if (localRes) {
                // Create, set and invoke the RMStoreAction
                RMStoreAction rmStoreAction = null;
                rmStoreAction = new RMStoreAction(this.getName() + "-RMStoreAction",
                                                  this.getCoordRequest(),
                                                  resDetails);
                rmStoreAction.execute();
                if (rmStoreAction.getState() == CoordAction.State.FAILED) {
                    LOG.error(netLogger.error(method,ErrSev.MINOR,
                              "rmStore failed in PCERuntimeAction.setResultData with exception " +
                              rmStoreAction.getException().getMessage()));
                    this.fail2(rmStoreAction.getException());
                    return;
                }             
                
            } else {
                if (firstDomain) {
                    // Create reservation has succeeded. Update RM
                    RMStoreAction rmStoreAction = null;
                    rmStoreAction = new RMStoreAction(this.getName() + "-RMStoreAction",
                                                      this.getCoordRequest(),
                                                      resDetails);
                    rmStoreAction.execute();
                    if (rmStoreAction.getState() == CoordAction.State.FAILED) {
                        LOG.error(netLogger.error(method,ErrSev.MAJOR,
                                  "rmStore failed in PCERuntimeAction.setResultData with exception " +
                                  rmStoreAction.getException().getMessage()));
                        this.fail2(rmStoreAction.getException());
                        return;
                    }       
                    
                } else {
                    // send a committed notification to previous IDC confirming the action is completed 
                    CommittedForwarder forwarder = new CommittedForwarder (this.getName() + "-CommittedForwarder",
                                                                           this.getCoordRequest(),
                                                                           resDetails);
                    forwarder.execute();
                    
                    if (forwarder.getState() == CoordAction.State.FAILED) {
                        LOG.error(netLogger.error(method,ErrSev.MAJOR,
                                                  "notifyRequest failed in PCERuntimeAction.setResultData with exception " +
                                                  forwarder.getException().getMessage()));
                        this.fail(forwarder.getException(), StateEngineValues.FAILED);
                        return;
                    }                            
                }              
            }
        } else if (requestType.equals("pceModify")) {  // ********* PCE MODIFY *********
            if (localRes) {
                // This path is intra-domain, we can commit now.
                PCERuntimeAction pceRuntimeAction = new PCERuntimeAction (this.getName() + "-Commit-PCERuntimeAction",
                                                                          this.getCoordRequest(),
                                                                          null,
                                                                          this.transactionId,
                                                                          "pceCommit");
                PCEData pceData = new PCEData(resDetails.getUserRequestConstraint(),
                                              resDetails.getReservedConstraint(),
                                              resDetails.getOptionalConstraint(),
                                              null);
                pceRuntimeAction.setRequestData(pceData);
                this.add(pceRuntimeAction);                 
            } else {
                // TODO: to be implemented
            }
        } else if (requestType.equals("pceCancel")) {  // ********* PCE CANCEL *********
            if (localRes) {
                 // Update reservation status to CANCELLED
                String state = StateEngineValues.LCANCELLED;
                if (localRes) {
                    state = StateEngineValues.CANCELLED;
                }
                RMUpdateStatusAction rmUpdateStatusAction = null;

                rmUpdateStatusAction = new RMUpdateStatusAction(this.getName() + "-RMUpdateStatusAction",
                                                                this.getCoordRequest(),
                                                                this.getCoordRequest().getGRI(), 
                                                                state);
                rmUpdateStatusAction.execute();
                if (rmUpdateStatusAction.getState() == CoordAction.State.FAILED) {
                    LOG.error(netLogger.error(method,ErrSev.MINOR,
                            "rmUpdateStatus failed in PCERuntimeAction.setResultData with exception " +
                            rmUpdateStatusAction.getException().getMessage()));
                    this.fail2 (rmUpdateStatusAction.getException());
                    return;
                }                
            } else {
             // TODO: to be implemented
            }
        }
        
        // Release the PCE mutex
        PCERuntimeAction.pceMutex.release (this.getCoordRequest().getGRI());
        // Since CoordActions have been added, it is required to re-process.
        this.process();
        LOG.debug(netLogger.end(method)); 
    }

    /**
     * creates a ResDetails structure from the PCEData and information saved in the
     * PCERuntime CoordRequest. The ResDetails is needed to update the reservation in
     * the Resource Manager
     * 
     * @param data - PCEData returned by the final PCE call
     * @param proxyAction
     * @return
     */
    @SuppressWarnings("unchecked")
    private ResDetails pceData2ResDetails (PCEData data,
                                           ProxyAction proxyAction, 
                                           boolean localRes,
                                           boolean firstDomain,
                                           boolean finalDomain) {
        
        if (localRes) {
            // Make sure to have consistent values
            finalDomain = false;
            firstDomain = false;
        }
        ResDetails resDetails = new ResDetails();
        CoordRequest request = this.getCoordRequest();
        resDetails.setCreateTime(request.getReceivedTime());
        resDetails.setGlobalReservationId(request.getGRI());
        // note: fields that are not set in resDetails will not be overwritten for an existing reservation
        // Set description
        String description = (String) request.getAttribute(CoordRequest.DESCRIPTION_ATTRIBUTE);
        if (description != null) {
            resDetails.setDescription(description);
        } else {
            resDetails.setDescription("CoordRequest.DESCRIPTION_ATTRIBUTE null in PCERuntimeAction.setResultData");
        }
        // Set login
        String login = (String) request.getAttribute(CoordRequest.LOGIN_ATTRIBUTE);
        if (login != null) {
            resDetails.setLogin(login);
        } else {
            resDetails.setLogin("CoordRequest.LOGIN_ATTRIBUTE null in PCERuntimeAction.setResultData");
        }
        // Set user constraints
        resDetails.setUserRequestConstraint(data.getUserRequestConstraint());
        ReservedConstraintType reservedConstraint = new ReservedConstraintType();
        if (data.getTopology() != null && data.getReservedConstraint() != null) {
            PathInfo pathInfo = new PathInfo();
            reservedConstraint.setStartTime(data.getReservedConstraint().getStartTime());
            reservedConstraint.setEndTime(data.getReservedConstraint().getEndTime());
            reservedConstraint.setBandwidth(data.getReservedConstraint().getBandwidth());
            pathInfo.setPath(PCEReqFormatter.getInstance().topoToPath(data.getTopology(),
                             data.getReservedConstraint().getPathInfo().getPath()));
            /* if (data.getTopology().getPath().size() != 0) {
                pathInfo.setPath(data.getTopology().getPath().get(0));
            }*/
            reservedConstraint.setPathInfo(pathInfo);
        } else if(data.getReservedConstraint() != null){
            reservedConstraint = data.getReservedConstraint();
        }
        resDetails.setReservedConstraint(reservedConstraint);
        
        if (proxyAction.getRequestType().equals("pceCreate")) {
            if (localRes || finalDomain) {
                resDetails.setStatus(StateEngineValues.PATHCALCULATED);
            } else {
                resDetails.setStatus(StateEngineValues.LPATHCALCULATED);
            }
        } else if (proxyAction.getRequestType().equals("pceModify")) {
            if (localRes || finalDomain) {
                resDetails.setStatus(StateEngineValues.PATHCALCULATED);
            } else {
                resDetails.setStatus(StateEngineValues.LPATHCALCULATED);
            }         
        } else if (proxyAction.getRequestType().equals("pceCancel")) {
            if (finalDomain) {
                resDetails.setStatus(StateEngineValues.CANCELLED);
            }               
        } else if (proxyAction.getRequestType().equals("pceCommit")) {
            if (localRes || firstDomain) {
                resDetails.setStatus(StateEngineValues.RESERVED);
            } else {
                resDetails.setStatus(StateEngineValues.LCOMMITTED);
            }             
        } 
        return resDetails;
    }

    /**
     * Fails the CoordAction. Failing a CoordAction fails the CoordRequest it is part of. 
     * The input exception will be copied to the CoordRequest.
     * @param exception is an optional Exception that can be associated to the CoordAction explaining the failure.
     */
    public void fail2 (Exception exception) {
        // Update status in RM.
        PCERuntimeAction.pceMutex.release(this.getCoordRequest().getGRI());
        super.fail2 (exception);
    }
    
    public void fail (Exception exception, String error) {
        // Update status in RM.
        PCERuntimeAction.pceMutex.release(this.getCoordRequest().getGRI());
        super.fail (exception, error);
    }
    
    
    @SuppressWarnings("unchecked")
    public void childFailed (CoordAction pce) {
        // a PCE has failed. The PCE mutex must be released.
        LOG.error(this.netLogger.error("childFailed", ErrSev.MINOR,
                                       "PCE " + pce.getName() + "  GRI= " + this.getCoordRequest().getGRI() + 
                                       " has failed"));
        PCERuntimeAction.pceMutex.release(this.getCoordRequest().getGRI());
    }
    
    public static void cleanup() {
        ArrayList<String> toBeRemoved = new ArrayList<String>();

        Set <Map.Entry<String,WeakReference<PCERuntimeAction>>> actionsSet = PCERuntimeAction.pceRuntimes.entrySet();
        for (Map.Entry<String,WeakReference<PCERuntimeAction>> entry : actionsSet) {
            WeakReference<PCERuntimeAction> ref = (WeakReference<PCERuntimeAction>) entry.getValue();
            if ((ref == null) || (ref.get() == null)) {
                // free entry
                toBeRemoved.add(entry.getKey());
            }
        } 
        
        for (String id : toBeRemoved) {
            PCERuntimeAction.pceRuntimes.remove(id);
        }
        CoordAction.cleanup();
    }
        
    private static CtrlPlanePathContent getReservedPathFromPceData (PCEData pceData) {
        
        OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
        //get path constraint
        CtrlPlanePathContent path = null;
        if ( (pceData.getReservedConstraint() != null) && 
             (pceData.getReservedConstraint().getPathInfo() != null)) {
            
            path = pceData.getReservedConstraint().getPathInfo().getPath();
            
        } else {
            String msg = "Connectivity PCE received a request with no " +
                    "reservedConstraint containing a PathInfo element";
            LOG.debug(netLogger.error("getReservedPathFromPceData",ErrSev.MINOR,"calculatePath" +  msg));
        }
        return path;
    }
 
}

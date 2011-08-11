package net.es.oscars.coord.req;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import java.lang.ref.WeakReference;

import org.apache.log4j.Logger;

import org.ogf.schema.network.topology.ctrlplane.CtrlPlanePathContent;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;

import net.es.oscars.coord.common.Coordinator;
import net.es.oscars.coord.jobs.PathCtrlRequestCleanerJob;

import net.es.oscars.coord.req.CoordRequest;
import net.es.oscars.coord.runtimepce.PCEData;
import net.es.oscars.utils.sharedConstants.StateEngineValues;
import net.es.oscars.utils.config.ContextConfig;
import net.es.oscars.utils.soap.OSCARSServiceException;
import net.es.oscars.coord.actions.CoordAction;
import net.es.oscars.coord.actions.RMUpdateStatusAction;
import net.es.oscars.logging.ErrSev;
import net.es.oscars.logging.OSCARSNetLogger;
import net.es.oscars.logging.ModuleName;



public class PathCtrlRequest extends CoordAction <PCEData, PCEData> {

    private static final long serialVersionUID = 1439115737928915954L;
    private static final String moduleName = ModuleName.PCERUNTIME;

    public static final String PCE_CONFIGURATION = "pce-config.xml";
    private static Logger LOG = Logger.getLogger(PathCtrlRequest.class.getName());

    private OSCARSNetLogger netLogger = null;
    private static HashMap<String, WeakReference<PathCtrlRequest>> instances = new HashMap<String, WeakReference<PathCtrlRequest>>();
    private String requestType = null;
    private String transactionId = null; // TODO put in coordAction
    private static ContextConfig cc = null;

    // Start the background thread that will prune empty entries in the actions map.
    private static long CLEANER_REPEAT = (5 * 60 * 1000); // 15 minutes
    
    static {
        SimpleTrigger jobTrigger = new SimpleTrigger("PathCtrlRequest.Cleaner",
                                                     null,
                                                     SimpleTrigger.REPEAT_INDEFINITELY,
                                                     PathCtrlRequest.CLEANER_REPEAT);
        
        JobDetail     jobDetail  = new JobDetail("PathCtrlRequest", null, PathCtrlRequestCleanerJob.class);
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
    public PathCtrlRequest (String name, 
                             CoordRequest request, 
                             PCEData data, 
                             String transactionId,
                             String requestType) {
        
        super (name, request, data);
        LOG = Logger.getLogger(PathCtrlRequest.class.getName());
        this.netLogger = new OSCARSNetLogger(moduleName, transactionId);
        this.netLogger.setGRI(request.getGRI());
        LOG.debug(netLogger.start("constructor", "requestType"));
        this.requestType = requestType;
        this.transactionId = transactionId;
        cc = ContextConfig.getInstance();
    }

    public static PathCtrlRequest getPathCtrlRequest (String gri, String requestType) {

        OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
        String event = "getPathCtrlRequest";
        PathCtrlRequest request =  null;
        String key = gri + "-" + requestType;
        synchronized (PathCtrlRequest.instances) {
            WeakReference<PathCtrlRequest> ref =  PathCtrlRequest.instances.get(key);
            if (ref != null) {
                request = ref.get();
                if (request == null) {
                    LOG.error(netLogger.error(event,ErrSev.MAJOR, "No PathCtrlRequest (1) for GRI: " + 
                                                gri + " requestType= " + requestType));
                    // No request for this GRI This should not happen
                    throw new RuntimeException ("No PathCtrlRequest for GRI: " + gri + " requestType= " + requestType);
                }
            } else {
                LOG.info(netLogger.error(event,ErrSev.MAJOR, "No PathCtrlRequest (2) for GRI: " + 
                                         gri + " requestType= " + requestType));
                throw new RuntimeException ("No PathCtrlRequest for GRI: " + gri + " requestType= " + requestType);
            }
        }
        return request;
    }

    public void setPCERuntimeAction (String requestType) {
        String key = this.getCoordRequest().getGRI() + "-" + requestType;
        synchronized (PathCtrlRequest.instances) {
            PathCtrlRequest.instances.put (key, new WeakReference<PathCtrlRequest>(this));
        }
    }

    /**
     * Invoke the PCE through the PCE generic client. The request is sent asynchronously.
     * The reply will be returned in a PCEReply message sent from the PCE back to PCERuntimeSoapHandler. 
     */
    public void execute() {
        String event = "execute:" + this.requestType;
        LOG.debug(this.netLogger.start(event));
        // Get the logger for this thread and initialize it
        OSCARSNetLogger.setTlogger(this.netLogger); 

        // Register this PCERuntime instance
        this.setPCERuntimeAction (this.requestType);

        // Create, set and invoke the RMUpdateStatus
        String newState = null;
        if (this.requestType.equals("pceCreate")){
            newState = StateEngineValues.INPATHCALCULATION;
        } else if (this.requestType.equals("pceCommit")){
            newState = StateEngineValues.INCOMMIT;
        } 
        LOG.debug(this.netLogger.getMsg(event,"Calling RMUpdateStatus with state "  + newState));
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
    }

    public void executed() {
        super.executed();
    }

    public void setResultData (PCEData data) {
        String method ="PCERuntimeAction.setResultData";
        LOG.debug(this.netLogger.start(method, " for " + requestType));

        LOG.debug(this.netLogger.end(method)); 
    }

    public static void cleanup() {
        ArrayList<String> toBeRemoved = new ArrayList<String>();

        Set <Map.Entry<String,WeakReference<PathCtrlRequest>>> actionsSet = PathCtrlRequest.instances.entrySet();
        for (Map.Entry<String,WeakReference<PathCtrlRequest>> entry : actionsSet) {
            WeakReference<PathCtrlRequest> ref = (WeakReference<PathCtrlRequest>) entry.getValue();
            if ((ref == null) || (ref.get() == null)) {
                // free entry
                toBeRemoved.add(entry.getKey());
            }
        } 
        
        for (String id : toBeRemoved) {
            PathCtrlRequest.instances.remove(id);
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
            LOG.debug(netLogger.end("calculatePath",  msg));
        }
        return path;
    }
 
}

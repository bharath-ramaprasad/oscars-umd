package net.es.oscars.coord.jobs;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.apache.log4j.Logger;

import net.es.oscars.coord.req.CoordRequest;
import net.es.oscars.coord.actions.CoordAction;
import net.es.oscars.logging.ErrSev;
import net.es.oscars.logging.ModuleName;
import net.es.oscars.logging.OSCARSNetLogger;

import net.es.oscars.utils.sharedConstants.StateEngineValues;
import net.es.oscars.utils.soap.OSCARSServiceException;



public class RequestTimerJob implements Job {

    private static String COORDREQUEST_ID = "CoordRequestID";
    private static final Logger LOG = Logger.getLogger(RequestTimerJob.class.getName());
    // A Long object is used so it can be synchronized
    private static Long idCounter = new Long(0L); 

    
    public RequestTimerJob() {
        
    }
    
    static public String getRequestId (JobExecutionContext context) {
        if (context == null) {
            // No context, no ID
            return null;
        }
        JobDataMap map = context.getMergedJobDataMap();
        if (map == null) {
            // No map, no ID
            return null;
        }
        String id = map.getString(RequestTimerJob.COORDREQUEST_ID);    
        return id;
    }
    
    @SuppressWarnings("unchecked")
    static public void setRequestId (JobDetail jobDetail, String id, CoordRequest request) {
        if (jobDetail == null) {
            Thread.dumpStack();
            throw new RuntimeException ("no JobDetail was provided");
        }
        JobDataMap map = new JobDataMap();
        map.put(RequestTimerJob.COORDREQUEST_ID, id);
        jobDetail.setJobDataMap(map);
    }
    
    @SuppressWarnings("unchecked")
    public void execute(JobExecutionContext context) throws JobExecutionException {
        OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
        netLogger.init(ModuleName.COORD,"null");
        String event = "RequestTimerJob";
        LOG.info(netLogger.start(event));
        // Retrieve the CoordRequest object this job must execute.
        String requestId = RequestTimerJob.getRequestId(context);
        if (requestId == null) {
            // No action is recorded. This means the action was canceled.
            LOG.error (netLogger.error(event, ErrSev.MAJOR, "watchdog - did not find id for request"));
            throw new JobExecutionException ("no request to execute for this job");
        }
        CoordAction request = CoordRequest.getCoordRequestById(requestId);
        if (request == null) {
            // No action object is recorded. The action was canceled
            LOG.error (netLogger.error(event,ErrSev.MAJOR,"watchdog - did not find CoordRequest for id " + requestId));
            throw new JobExecutionException ("no action object to execute for this job");
        }
        if (request.getCoordRequest() != null ) {
            netLogger.init(ModuleName.COORD,request.getCoordRequest().getTransactionId());
        }
        LOG.debug(netLogger.getMsg(event,"checking on " + request.getName()));
        if ( ! request.isFullyCompleted()) {
            // This request has not yet been completed. Assume an error/problem has occured. Fail the request
            request.fail(new OSCARSServiceException ("Watchdog Exception"), StateEngineValues.FAILED);
        }
        // Force the CoordRequest to be removed from the pending request list (allowing this request to be GC'ed)
        CoordRequest.forget(requestId);
    }
    
    public static String createId() {
        long id=0L;
        synchronized (RequestTimerJob.idCounter) {
            id = ++RequestTimerJob.idCounter;
        }
        return Long.toString(id);
    }
    
    
} 

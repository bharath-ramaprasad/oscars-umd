package net.es.oscars.coord.req;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.apache.log4j.Logger;

import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.JobDetail;

import net.es.oscars.common.soap.gen.AuthConditions;
import net.es.oscars.common.soap.gen.MessagePropertiesType;
import net.es.oscars.coord.actions.CoordAction;
import net.es.oscars.coord.actions.RMUpdateStatusAction;
import net.es.oscars.coord.actions.CoordAction.State;
import net.es.oscars.coord.common.Coordinator;
import net.es.oscars.coord.jobs.CoordJob;
import net.es.oscars.coord.jobs.RequestTimerJob;
import net.es.oscars.logging.ErrSev;
import net.es.oscars.logging.ModuleName;
import net.es.oscars.logging.OSCARSNetLogger;
import net.es.oscars.utils.soap.OSCARSServiceException;

/**
 * CoordRequest base class for coordinator requests that are handled asynchronously
 * stores some common parameters from the input message, implements get and set methods
 * Extended by: CreateReservationRequest, CreatePathRequest
 * 
 * @author lomax
 *
 * @param <P> Class of the Coordinator's request message content
 * @param <R> Class of the Coordinator's reply message content
 */
public class CoordRequest<P,R> extends CoordAction<P,R> implements Comparable<CoordRequest<P,R>>, Serializable {
    
    public static final String  LOCALID_PREFIX = "LocalID-";
    // Standard attribute names
    public static final String  DESCRIPTION_ATTRIBUTE = "Description";
    public static final String  LOGIN_ATTRIBUTE = "Login";
    public static final String  SUBJECT_ATTRIBUTES = "SubjectAttributes";
    public static final String  STATE_ATTRIBUTE ="ReservationState";
    protected static final String moduleName = ModuleName.COORD;
    
    private static long             currentLocalId = 0L;
    private static final long       serialVersionUID = 2L;
    private String                  gri;
    private String                  gTransId;
    private Long                    receivedTime;
    private AuthConditions          authConditions;
    private MessagePropertiesType   msgProps;
    private HashMap<String, Object> attributes = new HashMap<String, Object>(); 
    private static final Logger LOG = Logger.getLogger(CoordRequest.class.getName());
    private static HashMap<String, CoordRequest> pendingRequests = new HashMap<String, CoordRequest>();
    
    private static long WATCHDOG_TIMER = (15 * 60 * 1000); // 15 minutes

    /**
     * 
     * @param name 
     */
    public CoordRequest(String name) {
        super (name, null, null);
        this.setReceivedTime(System.currentTimeMillis()/1000L);
        this.setTransactionId("NULL"); // shouldn't happen
        // This CoordRequest does not yet have a GRI. Set a temporary local identifier.
        CoordRequest.setGRI(this);
        this.setTimer();
    }

    public CoordRequest(String name, String gTransId) {
        super (name, null, null);
        this.setTransactionId(gTransId);
        // This CoordRequest does not yet have a GRI. Set a temporary local identifier.
        CoordRequest.setGRI(this);
        this.setReceivedTime(System.currentTimeMillis()/1000L);
        this.setTimer();
    }
 
    public CoordRequest(String name, String gTransId, String gri) {
        super (name, null, null);
        this.setTransactionId(gTransId);
        this.setGRI(gri);
        this.setReceivedTime(System.currentTimeMillis()/1000L);
        this.setTimer();
    }
 
    public CoordRequest(String name, String gTransId, String gri, AuthConditions authConds) {
        super (name, null, null);
        this.setTransactionId(gTransId);
        this.setGRI(gri);
        this.setReceivedTime(System.currentTimeMillis()/1000L);
        this.setAuthConditions(authConds);
        this.setTimer();
    }
    public Object getAttribute (String attribute) {
        synchronized (this.attributes) {
            Object object = this.attributes.get(attribute);
            return object;
        }        
    }

    public Object setAttribute (String attribute, Object newObject) {
        synchronized (this.attributes) {
            Object oldObject = this.attributes.put(attribute, newObject);
            return oldObject;
        }        
    }
    public String getTransactionId() {
        return this.gTransId;
    }
    
    public synchronized void setTransactionId (String gTransId) {
        this.gTransId = gTransId;
    }
    public String getGRI() {
        return this.gri;
    }
    // TODO Shouldn't this throw an exception if a request already exist for this gri? /mrt
    public synchronized void setGRI (String gri) {
        this.gri = gri;
        // Register this action
    }
    
    // Classes extending CoordRequest should overload this method so they can implement the correct failure semantics
    public void fail (Exception exception, String status) {
        super.fail2(exception);
    }
    

    @SuppressWarnings("unchecked")
    private static synchronized void setGRI(CoordRequest request) {
        ++CoordRequest.currentLocalId;
        request.gri = CoordRequest.LOCALID_PREFIX + (new Long(CoordRequest.currentLocalId)).toString();
    }
    public AuthConditions getAuthConditions() {
        return this.authConditions;
    }

    public void setAuthConditions(AuthConditions authConds){
        this.authConditions = authConds;
    }

    public MessagePropertiesType getMessageProperties() {
        return this.msgProps;
    }
    public void setMessageProperties(MessagePropertiesType msgProps){
        this.msgProps = msgProps;
    }
    
    public void logError() {
        OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
        List<CoordAction> failedActions = this.getFailedCoordActions ();
        for (CoordAction action: failedActions) {
            Exception ex = null;
            if (action.getException() != null) {
                ex = action.getException();
                LOG.warn(netLogger.error(action.getName(), ErrSev.MINOR, " failed with exception " + ex.getMessage()));
            }
        }
    }
    public String toString() {
        return this.gri + "-" + this.getName();
    }

    @SuppressWarnings("unchecked")
    public boolean equals(Object o) {
        CoordRequest t = (CoordRequest) o;
        if (o == null) {
            return false;
        }
        return (t.getGRI().equals(this.gri));
    }

    public int compareTo(CoordRequest<P,R> o) {
        return this.receivedTime.compareTo(o.getReceivedTime());
    }

    public synchronized Long getReceivedTime() {
        return this.receivedTime;
    }

    public synchronized void setReceivedTime(Long receivedTime) {
        this.receivedTime = receivedTime;
    }
    

    private void setTimer () {
        String nameTag = RequestTimerJob.createId();
        Date timer = new Date (System.currentTimeMillis() + CoordRequest.WATCHDOG_TIMER);
        
        SimpleTrigger jobTrigger = new SimpleTrigger(this.getName() + "-watchdog-" + nameTag, 
                                                     null,
                                                     timer);
        
        JobDetail     jobDetail  = new JobDetail(this.getName() + "-watchdog-" + nameTag, null, RequestTimerJob.class);
        jobDetail.setVolatility(false);
        RequestTimerJob.setRequestId(jobDetail, nameTag, this);
        
        try {
            Coordinator.getInstance().getScheduler().scheduleJob(jobDetail, jobTrigger);
        } catch (Exception e) {
            OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
            LOG.error (netLogger.error("setTimer", ErrSev.MINOR, 
                       "Cannot create watchdog job for request: " + this.getName()));
        } 
        
        synchronized (CoordRequest.pendingRequests) {
            CoordRequest oldRef = CoordRequest.pendingRequests.put (nameTag, this);
        }       
    }
    
    static public CoordRequest getCoordRequestById (String nameTag) {
        synchronized (CoordRequest.pendingRequests) {
            CoordRequest request = CoordRequest.pendingRequests.get(nameTag);
            return request;
        }        
    }
    
    static public void forget(String nameTag) {
        synchronized (CoordRequest.pendingRequests) {
            CoordRequest request = CoordRequest.pendingRequests.remove(nameTag);
        }
        System.gc();
    }
}

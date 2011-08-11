package net.es.oscars.coord.actions;

import java.lang.Exception;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

import java.lang.ref.WeakReference;

import org.apache.log4j.Logger;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.JobDetail;

import net.es.oscars.coord.req.CoordRequest;
import net.es.oscars.coord.jobs.CoordJob;
import net.es.oscars.coord.jobs.CoordActionCleanerJob;
import net.es.oscars.coord.actions.RMUpdateStatusAction;
import net.es.oscars.coord.common.Coordinator;
import net.es.oscars.logging.ErrSev;
import net.es.oscars.logging.ModuleName;
import net.es.oscars.logging.OSCARSNetLogger;

import net.es.oscars.utils.sharedConstants.StateEngineValues;
import net.es.oscars.utils.soap.OSCARSServiceException;



/**
 * CoordAction an abstract class that can be queued for later execution
 * Implemented by: CoordRequest, ProxyAction, PCERuntimeAction, PSSCreatePathAction, PSSTeardownPathAction,
 *      AuthZCheckAccessAction, LockAction,UnlockAction, NotifyAction
 *      RMGenerateGRIAction,RMStoreAction,RMUpdateStatusAction
 *      
 * @author lomax
 *
 * @param <P>  Class containing the input data for the action
 * @param <R>  Class for returning the results of the action
 */
@SuppressWarnings("unchecked")
public abstract class CoordAction <P,R> extends LinkedList <CoordAction> {
    
    public  static String           LOCALID_PREFIX = "LocalID-";
    private static final long       serialVersionUID = 1L;
    private static long             currentLocalId = 0L;
    
    public static enum State {
        UNPROCESSED,
        PROCESSING,
        PROCESSED,
        FAILED,
        CANCELLED
    }
    
    private   CoordRequest coordRequest   = null;
    protected State        state          = State.UNPROCESSED;
    private   P            requestData    = null; // data input to coordinator request
    private   R            resultData     = null; // data to be returned in coordinator reply
    private   String       name           = "Unknown"; // a unique name based on operation and gri
    private   String       id             = null; // a unique id generated for this action
    private   CoordAction  previousAction = null; 
    protected Exception    exception      = null; // saved exception if one occurred during execution
    
    private static HashMap<String, WeakReference<CoordAction>> actions = new HashMap<String, WeakReference<CoordAction>>();
    private static final Logger LOG = Logger.getLogger(CoordAction.class.getName());
 
    // Start the background thread that will prune empty entries in the actions map.
    private static long CLEANER_REPEAT = (15 * 60 * 1000); // 15 minutes
    
    static {
        SimpleTrigger jobTrigger = new SimpleTrigger("CoordAction.Cleaner",
                                                     null,
                                                     SimpleTrigger.REPEAT_INDEFINITELY,
                                                     CoordAction.CLEANER_REPEAT);
        
        JobDetail     jobDetail  = new JobDetail("CoordAction.Cleaner", null, CoordActionCleanerJob.class);
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
     * Creates a CoordAction
     * 
     * @param name a unique identifier based on the operation name and gri
     * @param request is the CoordRequest this action is part of.
     * @param data input data of the action
     */
    public CoordAction (String name, CoordRequest request, P data) {
        this.coordRequest = request;
        this.requestData  = data;
        this.name         = name;
        this.state        = State.UNPROCESSED;
        // Generate a new ID (not GRI) for this action)
        CoordAction.setId(this);
        

        // Register this action
        synchronized (CoordAction.actions) {
            WeakReference<CoordAction> oldRef = CoordAction.actions.put (this.getId(), new WeakReference<CoordAction>(this));
            if (oldRef != null) {
                CoordAction oldAction = oldRef.get();
                if (oldAction != null) {
                    // An action already existed with the same name. This must not happen
                    throw new RuntimeException ("Fatal: CoordAction " + name + " already exist.");
                }
            }
        }
    }
    

    /**
     * Returns the CoordAction object associated to a name
     * @param name is the name of the CoordAction that was used when it was created
     * @return the CoordAction object
     */
    public static CoordAction getCoordAction (String name) {
        synchronized (CoordAction.actions) {
            WeakReference<CoordAction> ref = CoordAction.actions.get(name);
            if (ref != null) {
                CoordAction action = ref.get();
                if (action != null) {
                    // An action object of the specified name already exists. Return it.
                    return action;
                }
            }
            // No action of the specified id has been yet created. return null;
            return null;
        }        
    }
    
    /**
     * Returns the CoordRequest this action is part of.
     * @return the CoordRequest object or null if this CoordAction is not part of any CoordRequest
     */
    public CoordRequest getCoordRequest() {
        return this.coordRequest;
    }
    
    public void setCoordRequest (CoordRequest request) {
        this.coordRequest = request;
    }
    /**
     * Returns the local identifier of the CoordAction.
     * @return the action's identifier.
     */
    public String getId() {
        return this.id;
    }

    private static synchronized void setId(CoordAction action) {
        ++CoordAction.currentLocalId;
        action.id = CoordAction.LOCALID_PREFIX + (new Long(CoordAction.currentLocalId)).toString();
    }

    /**
     * Cancel the action. When an action is canceled, all children actions in the graph are also canceled.
     */
    public void cancel () {
        this.setState(State.CANCELLED);
    }
    
    /**
     * Returns the state of this CoordAction
     * @return the state of the CoordAction
     */
    public synchronized State getState() {
        return this.state;
    }
    
    /**
     * Check if this action and any of its children are completed (Processed, Failed or Cancelled).
     * @return true if the CoordAction and its children CoordAction in the graph is completed. Returns false otherwise.
     */
    public boolean isFullyCompleted () {
        
        State myState = this.getState();
        if ((myState != State.PROCESSED) && (myState != State.CANCELLED) && (myState != State.FAILED)) {
            // This action is not processed nor cancelled. No need to check children, return false already.
            return false;
        }
        if ((myState == State.FAILED) || (myState == State.CANCELLED)) {
            // If this action is either FAILED or CANCELLED, there is no need to determine the state of the
            // children CoordAction in the graph
            return true;
        }
        for (CoordAction action : this) {
            if (action.isFullyCompleted() == false) {
                // If any of the children is not fully completed, return false
                return false;
            }
        }
        // If we reach this point of the method, this means that every action is fully completed. Return true.
        return true;
    }
    
    /**
     * Sets the state of the CoordAction. This method is idempotent.
     * If the state is changed and the action in not already in process, process the action 
     * If the news state is FAILED or PROCESSED, notifies any threads that might be waiting
     * 
     * @param state is the new state of the CoordAction.
     */
    public void setState (State state) {
        String name = this.getName();
        String event = "setState";
        OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
        //LOG.debug("setState called for " + name + " " + state);
        synchronized (this) {
            if ((this.state == State.CANCELLED) || 
                (this.state == State.FAILED)) {              
                // CANCELLED and FAILED are final states
                return;
            }
        }
        State previousState = this.state;
        this.state = state;
        boolean toBeProcessed = false;    
        synchronized (this) {
            toBeProcessed = (previousState != state) && (state != State.PROCESSING);
            //LOG.debug("toBeProcessed is " + toBeProcessed);

            if (state == State.FAILED) {
                // Fail the request passing it the exception
                if (this.getCoordRequest() != null && this.getException() !=  null ) {
                    LOG.debug(netLogger.error(event,ErrSev.MINOR,"setting coordRequest to fail with exception " + 
                                              this.getException().getMessage()));
                    this.getCoordRequest().fail2 (this.getException());
                }
                // This action is failed. Unblock any thread that might be waiting.
                this.notifyAll();
                return;
            } else if ((state == State.PROCESSED) && (this.isFullyCompleted())) {
                // This action is fully completed. Notify all
                this.notifyAll();
                return;
            }
        }
        if (toBeProcessed) {
            // The state of this action has changed. Signal the CoordRequest (root action) that it must re-process
            if (this.getCoordRequest() != null) {
                // Note that CoordRequest.process is non-blocking, so it is fine to invoke it while holding the object lock
                this.getCoordRequest().process();
            } else {
                // This is the top CoordAction, likely to be  a CoordRequest. Process it.
                this.process();
            }
        }
    }
    
    /**
     * Returns the name of the CoordAction
     * @return the name of the CoordAction
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Called when a children action has failed. This method can be overloaded by implementations of CoordAction
     * that need to be notified of the failure of a child action. 
     * By default, the childFailed is propagated up the tree of CoordAction, but any implementation of CoordAction
     * is free to "break" the propagation of the childFailed.
     * @param action
     */
    public void childFailed(CoordAction action) {
        if (this.getPreviousAction() != null) {
            this.getPreviousAction().childFailed(action);
        }
    }
    
    /**
     * Fails the CoordAction. Failing a CoordAction fails the CoordRequest it is part of. 
     * The input exception will be copied to the CoordRequest.
     * @param exception is an optional Exception that can be associated to the CoordAction explaining the failure.
     */
    public void fail2 (Exception exception) {
        this.exception = exception;
        this.setState(State.FAILED);
        
        this.childFailed (this);
    }
    
    
    public void fail (Exception exception, String status) {

        OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
        if (this.getCoordRequest() == null) {
            // Sanity check. It is possible for an action to not be associated to a CoordRequest. 
            LOG.info (netLogger.start("fail",this.getName () + " is not associated to a CoordRequest"));
            
            this.fail2 (exception);
            return;
        }
        
        this.getCoordRequest().fail (exception, status);
    }
    
    private void getFailedCoordActions (ArrayList failedActions) {
        synchronized (this) {
            if (this.state == State.FAILED) {
                failedActions.add(this);
            }
        }       
        for (CoordAction action : this) { 
            action.getFailedCoordActions(failedActions);
        }                   
    }
    
    /**
     * Returns the list of failed CoordActions where this CoordAction is the root of the graph.
     * @return a list of CoordActions that have failed.
     */
    public List<CoordAction> getFailedCoordActions () {
        ArrayList<CoordAction> failedActions = new ArrayList<CoordAction>();
        this.getFailedCoordActions (failedActions);
        return failedActions;
    }
    /**
     * 
     * @return the exception associated with a failed action
     */
    public Exception getException( ) {
        return exception;
    }
    /**
     * Start the execution of the action.
     * Classes that extends this CoordAction are expected to overwrite it 
     */
    public void execute() {
        for (CoordAction action : this) {
            action.execute();
        }
    }
    
    /**
     * Sets the CoordAction as executed.
     */
    public void executed() {
        this.setState (State.PROCESSED);
    }
 
    
    /**
     * Returns the data that is used as input of the CoordAction.
     * @return the input data
     */
    public P getRequestData() {
        return this.requestData;
    }
    
    /**
     * Sets the input data of the CoordAction
     * @param data is the input data
     */
    public void setRequestData (P data) {
        this.requestData = data;
    }
    
    /**
     * Returns the output data of the CoordAction
     * @return the output data
     */
    public R getResultData() {
        return this.resultData;
    }
    
    /**
     * Sets the output data of this CoordAction
     * @param data is the output data
     */
    public void setResultData (R data) {
        this.resultData = data;
    }
    
    /**
     * Starts the processing or re-processing of the graph of CoordAction for which this CoordAction is the root.
     * This method is asynchronous. Not only this method cannot fail, it might return before the CoordAction is
     * actually executed.
     * 
     * This method is idempotent.
     */
    public synchronized void process () {
        OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
        String event = "process";
        if (this.getCoordRequest() != null) {
            netLogger.init(ModuleName.PCERUNTIME,this.getCoordRequest().getTransactionId());
        } else {
            netLogger.init(ModuleName.PCERUNTIME,"null");
        }
        // If the action is failed, do not process anything.
        if (this.state == State.FAILED) {
            this.notifyAll();
            return;
        }
        
        if (this.state == State.CANCELLED) {          
            for (CoordAction action : this) { 
                action.cancel();
            }
            this.notifyAll();
            return;
        }
        
        if (this.state == State.PROCESSING) {
            // Make sure that the CoordAction is processed only once
            return;
        }
              
        // Check if there is anything to process
        if (this.isFullyCompleted()) {
            // Nothing has to be processed. Signal any thread that might be waiting
            //LOG.debug("nothing to process " + this.getName() + " " + this.state);
            this.notifyAll();
            return;
        }

        if (this.state == State.UNPROCESSED) {
            // a job must be created for this action
            String nameTag = CoordJob.createId();
            SimpleTrigger jobTrigger = new SimpleTrigger(this.getName() + "-" + nameTag, null);
            JobDetail     jobDetail  = new JobDetail(this.getName() + "-" + nameTag, null, CoordJob.class);
            jobDetail.setVolatility(false);
            CoordJob.setRequestId(jobDetail, this);
            
            try {
                Coordinator.getInstance().getScheduler().scheduleJob(jobDetail, jobTrigger);
            } catch (SchedulerException e) {
                LOG.warn(netLogger.error(event,ErrSev.MAJOR,"schedulerExeption " + e.getMessage() + 
                                        " scheduling " + this.name));
                this.setState(State.FAILED);
            } catch (OSCARSServiceException e) {
                LOG.warn(netLogger.error(event,ErrSev.MAJOR,"OSCARSServiceExeption " + e.getMessage() + 
                                        " processing " + this.name));
                this.setState(State.FAILED);
            }
        }

        if ((this.state == State.PROCESSED) || (this.getCoordRequest() == null)) {
            // This action is processed, therefore the processing must be propagated to the children.
            // Note that if the action is the root action of the graph, it needs to be processed even when
            // it is not processed completely (the root action somewhat breaks the execution model that children
            // action can be processed only when the parent is fully processed. The root action is different than
            // all other actions because it does not have a CoordRequest associated.
            for (CoordAction action : this) { 
                action.process();
            }        
        }

    }
    
    /**
     * Starts the processing or re-processing of the graph of CoordAction for which this CoordAction is the root.
     * This method is synchronous: it returns when the CoordAction and its children CoordActions in the graph are
     * completed, cancelled, failed or when the provided timeout is reached.
     * 
     * @param timeout is the number of millisecond this method should wait until returning.
     * @throws InterruptedException when the timeout is reached before the CoordAction is completed.
     */
    public synchronized void processAndWait (long timeout) throws InterruptedException {
        this.process();
        this.wait(timeout);
    }
    
    /**
     * Adds a CoordAction as a child of this CoordAction. Each of the children CoordAction might be 
     * executed in parallel.
     * 
     * @param action is the child CoordAction
     * @return true (as per the general contract of Collection.add).
     */
    public boolean add (CoordAction action) {
        boolean ret = super.add (action);
        action.previousAction = this;
        return ret;
    }
    
    /**
     * Returns the parent CoordAction of this CoordAction.
     * @return the parent CoordAction of this CoordAction.
     */
    public CoordAction getPreviousAction() {
        return this.previousAction;
    }
    
    public static void cleanup () {
        ArrayList<String> toBeRemoved = new ArrayList<String>();

        Set <Map.Entry<String,WeakReference<CoordAction>>> actionsSet = CoordAction.actions.entrySet();
        for (Map.Entry<String,WeakReference<CoordAction>> entry : actionsSet) {
            WeakReference<CoordAction> ref = (WeakReference<CoordAction>) entry.getValue();
            if ((ref == null) || (ref.get() == null)) {
                // free entry
                toBeRemoved.add(entry.getKey());
            }
        } 

        for (String id : toBeRemoved) {
            CoordAction.actions.remove(id);
        }
        Runtime.getRuntime().gc();
    }
} 

package net.es.oscars.coord.runtimepce;

import java.net.MalformedURLException;
import java.util.HashMap;

import org.apache.log4j.Logger;

import net.es.oscars.logging.ErrSev;
import net.es.oscars.logging.ModuleName;
import net.es.oscars.logging.OSCARSNetLogger;
import net.es.oscars.pce.soap.gen.v06.PCEError;
import net.es.oscars.coord.runtimepce.PCEData;
import net.es.oscars.coord.actions.CoordAction;
import net.es.oscars.coord.req.CoordRequest;
import net.es.oscars.coord.actions.RMUpdateStatusAction;
import net.es.oscars.coord.workers.PCEWorker;
import net.es.oscars.utils.soap.OSCARSServiceException;
import net.es.oscars.utils.sharedConstants.StateEngineValues;

/**
 * PCEProxyAction is an Action that calls pceWorker to send PCECreate,PCECommit,
 * PCEModify and PCECancel messages.
 * It processes pceReply and pceErrorReply messages
 * 
 * @author lomax
 *
 */
public class PCEProxyAction extends ProxyAction {

    private static final Logger LOG = Logger.getLogger(PCEProxyAction.class.getName());
    private OSCARSNetLogger netLogger = null;

    private static final long serialVersionUID = 1439115737928915954L;

    private HashMap<String, PCEData> aggPceDataSet = new HashMap<String, PCEData>();

    /**
     * Constructor for PCEProxyAction
     * 
     * @param parentPce ProxyAction for any parentPCE
     * @param coordRequest 
     * @param name String name of PCE
     * @param pathTag
     * @param proxyEndpoint callback address to which PCE service sends PCEReply messages
     * @param pceEndpoint  address of PCE service to be called
     * @param transactionId global transaction id for this IDC request
     * @param requestType pceCreate, pceCommit, pceModify, pceCancel
     */
    public PCEProxyAction (ProxyAction parentPce,
                           CoordRequest coordRequest,
                           PCERuntimeAction pceRuntime,
                           String name,
                           String pathTag,
                           String proxyEndpoint,
                           String pceEndpoint,
                           String transactionId,
                           String requestType) {
        
        // Note that in the input data is not set when creating the object but is dynamically
        // stored at execution time.
        super (parentPce, coordRequest, pceRuntime, name,pathTag,ProxyAction.Role.PCE,proxyEndpoint,pceEndpoint,
               transactionId,requestType);
        netLogger = new OSCARSNetLogger(ModuleName.PCERUNTIME,
                                        ((coordRequest != null) ? coordRequest.getTransactionId(): null));
        netLogger.setGRI((coordRequest != null) ? coordRequest.getGRI(): null);
    }
   
    /**
     *  Uses a PCEWorker to send a PCECreate or PCEcommit to the external PCE
     */
    public void execute()  {
        String method = "PCEProxyAction.execute";
        try {
            if (this.getRequestType().equals("pceCreate")) {
                this.sendPCECreate();
            } else if (this.getRequestType().equals("pceCommit")) {
                this.sendPCECommit();
            } else if (this.getRequestType().equals("pceCancel")) {
                this.sendPCECancel();
            } else if (this.getRequestType().equals("pceModify")) {
                this.sendPCEModify();
            }
        } catch (OSCARSServiceException ex){
            this.fail (ex, StateEngineValues.FAILED + ":" + ex.getMessage());
            LOG.warn(netLogger.error(method,ErrSev.MINOR, this.getName() + " request type= " +  
                                    this.getRequestType() + " failed with exception " + ex.getMessage()));
        }
    }  
    
    public void executed() {
        super.executed();
    }
    
    /**
     * processes a PCEReply message from a PCE
     * Saves the new pceData. If there are chained PCE, update their PCEData and force processing
     * of each one. 
     * Otherwise if this action has an aggregator, update the pceData there.
     * If there is neither, just update the data in the pceRuntimeAction.
     * Finally mark this pceAction as PROCESSED
     * @param pceData - the pceData structure received from PCE
     */
    public void processReply (PCEData pceData) {
        String method = "PCEProxyAction.processReply";
        LOG.debug (netLogger.start(method , this.getRequestType()));
        OSCARSNetLogger.setTlogger(netLogger);
        if (this.getRequestType().equals("pceCreate") ||
            this.getRequestType().equals("pceCommit") ||
            this.getRequestType().equals("pceModify") ||
            this.getRequestType().equals("pceCancel")) {
            
            // Set the data to be this PCE result data
            super.setResultData(pceData);
            
            // Move the result data to the next element.
            if (this.size() == 0) {
                LOG.debug(netLogger.getMsg(method, "at last element, request Type is " + this.getRequestType()));
                // This is the last PCE of this branch. The result is sent as the input of the closest AGGRETATOR
                AggProxyAction agg = this.getAggregator();
                if (agg == null) {
                    LOG.debug(netLogger.getMsg(method,"no agg"));
                    // There is no aggregator for this PCE. Send the result to the PCERuntimeAction.
                    this.getPCERuntimeAction().setResultData (pceData, (ProxyAction)this);
                } else {
                    agg.addAggData (pceData, this);
                }
            } else {
                // In general, a PCE (not an aggregator) has only one child, but it possible for PCE to have 
                // several children PCE, using a upper level aggregator.
                for (CoordAction<PCEData,PCEData> pce : this) {
                    pce.setRequestData(pceData);
                    // force processing
                    pce.process();
                }
            }
            // This PCE is now executed
            this.executed();

            LOG.debug(netLogger.end(method ));
            return;
        }
        LOG.debug(netLogger.error(method,ErrSev.MINOR, " unknown request type " + this.getRequestType()));
        return;
    }
    /**
     * processes a PCEReply error message from a PCE
     * @param pceError - the error message received from PCE
     */
    public void processErrorReply (PCEError pceError) {
        
        String method = "PCEProxyAction.processErrorReply";
        LOG.debug (netLogger.start(method , this.getRequestType()));
        if (this.getRequestType().equals("pceCreate") ||
            this.getRequestType().equals("pceCommit") ||
            this.getRequestType().equals("pceModify")) {
            
            Exception ex = new Exception("pceError " + pceError.getMsg() + " details " + pceError.getDetails());
            // will set the state of the parent coordRequest to FAILED
            this.fail (ex,StateEngineValues.FAILED + ":" + pceError.getMsg());
            
            LOG.debug(netLogger.end(method ));
        }
        // Not sure what an error on a PCEcancel means
    }
    
    public void setResultData (PCEData data, ProxyAction srcPce) {
        super.setResultData(data);
        // Result data has been received: this PCE is now executed
        this.executed();
    }
    
    protected void sendPCECreate() throws OSCARSServiceException  {
        try {
            OSCARSNetLogger.setTlogger(netLogger);
            PCEWorker pceWorker = PCEWorker.getPCEWorker(this.getName(),
                                                         this.getProxyEndpoint(),
                                                         this.getPceEndpoint());
            pceWorker.sendPceCreate (this);
            
        } catch (MalformedURLException e) {
            throw new OSCARSServiceException("PCEProxyAction.SendPceCreate caught MalformedURLException on "
                    + this.getProxyEndpoint() +" for "+ this.getRequestType(), "system");
        }
    }

    protected void sendPCECommit() throws OSCARSServiceException {
        try {
            OSCARSNetLogger.setTlogger(netLogger);
            PCEWorker pceWorker = PCEWorker.getPCEWorker(this.getName(),
                                                         this.getProxyEndpoint(),
                                                         this.getPceEndpoint());
            pceWorker.sendPceCommit (this);
            
        } catch (MalformedURLException e) {
            throw new OSCARSServiceException("PCEProxyAction.SendPceCommit caught MalformedURLException on "
                    + this.getProxyEndpoint() +" for "+ this.getRequestType(), "system");
        }
    }
 
    protected void sendPCECancel() throws OSCARSServiceException {
        try {
            OSCARSNetLogger.setTlogger(netLogger);
            PCEWorker pceWorker = PCEWorker.getPCEWorker(this.getName(),
                                                         this.getProxyEndpoint(),
                                                         this.getPceEndpoint());
            pceWorker.sendPceCancel (this);
            
        } catch (MalformedURLException e) {
            throw new OSCARSServiceException("PCEProxyAction.SendPceCancel caught MalformedURLException on "
                    + this.getProxyEndpoint() +" for "+ this.getRequestType(), "system");
        }
    }    
    
    protected void sendPCEModify() throws OSCARSServiceException {
        try {
            OSCARSNetLogger.setTlogger(netLogger);
            PCEWorker pceWorker = PCEWorker.getPCEWorker(this.getName(),
                                                         this.getProxyEndpoint(),
                                                         this.getPceEndpoint());
            pceWorker.sendPceModify (this);
            
        } catch (MalformedURLException e) {
            throw new OSCARSServiceException("PCEProxyAction.SendPceModify caught MalformedURLException on " +
                                             this.getProxyEndpoint() +" for "+ this.getRequestType(), "system");
        }
    }
    protected void sendPCEErrorResponse() {
        
    }
}


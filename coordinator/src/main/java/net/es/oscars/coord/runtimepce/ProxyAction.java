package net.es.oscars.coord.runtimepce;

import net.es.oscars.pce.soap.gen.v06.PCEError;
import net.es.oscars.coord.actions.CoordAction;
import net.es.oscars.coord.req.CoordRequest;
import net.es.oscars.utils.soap.OSCARSServiceException;

/**
 * ProxyAction is the base class for PCEProxyAction and AggProxyAction.
 * It stores the shared parameters of the classes. The execute, processReply and 
 * processErrorReply methods must be overridden by the extended classes.
 * 
 * @author lomax
 *
 */
public class ProxyAction extends CoordAction <PCEData, PCEData> {

    private static final long serialVersionUID = 1439115737928915954L;

    public static enum Role {
        AGGREGATOR,
        PCE
    }
    
    private ProxyAction      parentPce     = null;  // parent pce in a chain of PCE, null for root PCE
    private String           pceEndpoint   = null;  // address of pce to call
    private Role             pceRole       = null;  // PCE | AGGREGATOR
    private String           proxyEndpoint = null;  // callback address to pass to PCE
    private String           transactionId = null;  // Unique Id for each IDC request
    private String           pathTag       = null;
    private String           requestType   = null;  // pceCreate, pceCommit, pceModify, pceCancel, aggregatorCreate
    private PCERuntimeAction pceRuntime    = null;
    
    /**
     * Constructor - registers this action onto the PCERuntimeAction
     * 
     * @param parentPce ProxyAction for any parentPCE
     * @param coordRequest 
     * @param name String name of PCE
     * @param pathTag
     * @param role - "AGGREGATOR" or "PCE"
     * @param proxyEndpoint callback address to which PCE service sends PCEReply messages
     * @param pceEndpoint  address of PCE service to be called
     * @param requestType  pceCreate, pceCommit, pceModify, pceCancel, aggregatorCreate
     */
    @SuppressWarnings("unchecked")
    public ProxyAction (ProxyAction       parentPce,
                        CoordRequest      coordRequest,
                        PCERuntimeAction  pceRuntime,
                        String            name,
                        String            pathTag,
                        Role              role,
                        String            proxyEndpoint,
                        String            pceEndpoint,
                        String            transactionId,
                        String            requestType) {
        
        // Note that in the input data is not set when creating the object but is dynamically
        // stored in CoordAction.requestData at execution time.
        super (name, coordRequest, null);

        this.parentPce     = parentPce;
        this.pceEndpoint   = pceEndpoint;
        this.pceRole       = role;
        this.proxyEndpoint = proxyEndpoint;
        this.transactionId = transactionId;
        this.pathTag       = pathTag;
        this.requestType   = requestType;
        this.pceRuntime    = pceRuntime;
        // Register this action onto the PCERuntimeAction
        PCERuntimeAction.setProxyAction (this.getCoordRequest().getGRI(), this.getName(), this.requestType, this);
    }
    
    public Role getRole() {
        return this.pceRole;
    }
    
    public String getPathTag() {
        return this.pathTag;
    }
 
    public String getProxyEndpoint() {
        return this.proxyEndpoint;
    }
    
    public String getPceEndpoint() {
        return this.pceEndpoint;
    }

    public String getTransactionId() {
        return this.transactionId;
    }
    
    public void execute()  {
        throw new RuntimeException ("execute called on base class ProxyAction");
    }  
    
    public void executed() {
        super.executed();
    }
    
    public ProxyAction getParentPce () {
        return this.parentPce;
    }
    
    public String getRequestType () {
        return this.requestType;
    }
    
    public PCERuntimeAction getPCERuntimeAction () {
        return this.pceRuntime;
    }
    
    /**
     * Retrieve the closest aggregator (up in the tree).  
     * @return AggProxyAction 
     */
    protected AggProxyAction getAggregator() {
        ProxyAction pce = this.getParentPce();
        while (pce != null) {
            if (pce.getRole() == Role.AGGREGATOR) {
                // Found the closes aggregator
                return (AggProxyAction) pce;
            }
            pce = pce.getParentPce();
        }
        return null;
    }
    
    public void processReply (PCEData pceData) {
        throw new RuntimeException ("processReply called on base class ProxyAction");
    }
    
    public void processErrorReply (PCEError pceError) {
        throw new RuntimeException ("processErrorReply called on base class ProxyAction");
    }
    
    public void setResultData (PCEData data, ProxyAction srcPce) {
        super.setResultData(data);
    }
    
    public void fail2 (Exception exception) {
        if (this.getPCERuntimeAction() != null) {
            // Need to notify the PCE Runtime for this GRI that a PCE has failed
            this.getPCERuntimeAction().fail2 (exception);
        }
        super.fail2 (exception);
    }
    
    public void fail (Exception exception, String error) {
        if (this.getPCERuntimeAction() != null) {
            // Need to notify the PCE Runtime for this GRI that a PCE has failed
            this.getPCERuntimeAction().fail2 (exception);
        }       
        super.fail (exception, error);
    }
   
}


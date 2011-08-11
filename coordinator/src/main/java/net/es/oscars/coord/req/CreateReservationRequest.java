package net.es.oscars.coord.req;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import net.es.oscars.api.soap.gen.v06.ResCreateContent;
import net.es.oscars.api.soap.gen.v06.ResDetails;
import net.es.oscars.api.soap.gen.v06.OptionalConstraintType;
import net.es.oscars.api.soap.gen.v06.OptionalConstraintValue;
import net.es.oscars.api.soap.gen.v06.ObjectFactory;
import net.es.oscars.coord.actions.RMGenerateGRIAction;
import net.es.oscars.coord.actions.RMStoreAction;
import net.es.oscars.coord.actions.RMUpdateStatusAction;
import net.es.oscars.coord.runtimepce.PCERuntimeAction;
import net.es.oscars.coord.runtimepce.PCEData;
import net.es.oscars.coord.actions.CoordAction;
import net.es.oscars.coord.actions.CreateReservationResults;
import net.es.oscars.coord.actions.CoordAction.State;
import net.es.oscars.logging.ErrSev;
import net.es.oscars.logging.ModuleName;
import net.es.oscars.logging.OSCARSNetLogger;
import net.es.oscars.utils.soap.OSCARSServiceException;
import net.es.oscars.utils.sharedConstants.StateEngineValues;
import javax.xml.bind.JAXBElement;

/**
 * A coordRequest to handle CreateReservation requests. Input parameters are placed in
 * setRequestData of type ResCreateContent. SetResultData of type CreateReservationResults is
 * not actually used. Instead the  new GRI is placed in the CoordRequest and read from there by
 * the caller. 
 * 
 * @author lomax
 *
 * @param <ResCreateContent>  Class containing the input data for the action
 * @param <CreateReservationResults>  Class for returning the results of the action (not used)
 */
// TODO need to add AuthConditions to deal with site admin privilege.
public class CreateReservationRequest extends CoordRequest <ResCreateContent,CreateReservationResults >{
    
    private static final long       serialVersionUID  = 1L;
    private static final Logger LOG = Logger.getLogger(CreateReservationRequest.class.getName());
    private OSCARSNetLogger netLogger = null;

    public CreateReservationRequest(String name, String loginName, ResCreateContent createResvReq) {

        super (name,createResvReq.getMessageProperties().getGlobalTransactionId());

        // hardcoded check for anycast example
        LOG.debug("Check for anycast");
        String anycastAddress = "urn:ogf:network:domain=testdomain-1:node=switch2";
        if(createResvReq.getUserRequestConstraint().getPathInfo().getLayer2Info().getDestEndpoint().equals(anycastAddress)) {
        	LOG.debug("is anycast");
        	// destination is anycast, set optional constraints
        	ObjectFactory objFactory = new ObjectFactory();
        	
        	OptionalConstraintValue destAmountValue = new OptionalConstraintValue();
        	JAXBElement destAmountObject = objFactory.createAnycastDestAmount("3");
        	destAmountValue.getAny().add(destAmountObject);
        	
        	OptionalConstraintType destAmountType = new OptionalConstraintType();
        	destAmountType.setCategory("anycastDestAmount");
        	destAmountType.setValue(destAmountValue);
        	
        	OptionalConstraintValue anycastDests = new OptionalConstraintValue();
        	JAXBElement destOneObject = objFactory.createAnycastDestEndpoint("urn:ogf:network:domain=testdomain-1:node=switch2");
        	//JAXBElement destTwoObject = objFactory.createAnycastDestEndpoint("urn:ogf:network:domain=testdomain-1:node=switch3");
        	//JAXBElement destThreeObject = objFactory.createAnycastDestEndpoint("urn:ogf:network:domain=testdomain-1:node=switch5");
        	anycastDests.getAny().add(destOneObject);
        	//anycastDests.getAny().add(destTwoObject);
        	//anycastDests.getAny().add(destThreeObject);
        	
        	OptionalConstraintType anycastDestsType = new OptionalConstraintType();
        	anycastDestsType.setCategory("anycastDests");
        	anycastDestsType.setValue(anycastDests);
        	
        	//createResvReq.getOptionalConstraint().add(destAmountType);
        	createResvReq.getOptionalConstraint().add(anycastDestsType);
        }
        
    	LOG.debug("OptionalConstraint size: " + Integer.toString(createResvReq.getOptionalConstraint().size()));
		
        this.setRequestData(loginName, createResvReq);
        this.setLog();
    }

    public CreateReservationRequest(String gri, String name, String loginName, ResCreateContent createResvReq) {
        super (name, createResvReq.getMessageProperties().getGlobalTransactionId(), gri);
        this.setRequestData(loginName,createResvReq);
        this.setLog();
    }
    
    public void setRequestData (String loginName, ResCreateContent createResvReq) {
        // Set input parameter using base class method
        super.setRequestData (createResvReq);
        // Add the reservation description to the attribute list of the request.
        this.setAttribute(CoordRequest.DESCRIPTION_ATTRIBUTE, createResvReq.getDescription());
        // Add login attribute
        this.setAttribute(CoordRequest.LOGIN_ATTRIBUTE, loginName);
        this.setLog();
    }
    /**
     * Called by CoordImpl to start the execution of a Create Reservation request. 
     * Synchronous parts of the processing are done and a PCERuntime action is
     * created to start the path calculation. If it returns without throwing an exception
     * the new GRI has been placed in this CoordRequest object
     * @params were set by the constructor or by setRequestData
     * @return sets the new GRI into this request object
     * @throws OSCARSServiceException - nothing is expected, but could get runtimeError
     */
    public void execute()  {
        String method = "CreateReservationRequest.execute";
        Boolean needToUpdateStatus = false;
                
        LOG.debug(netLogger.start(method));

        ResCreateContent  createResvReq = this.getRequestData();;
        
        /*// hardcoded check for anycast example
        LOG.debug("Check for anycast");
        String anycastAddress = "urn:ogf:network:domain=testdomain-1:node=node-1-2:port=ge-1/1/0:link=*";
        if(createResvReq.getUserRequestConstraint().getPathInfo().getLayer2Info().getDestEndpoint().equals(anycastAddress)) {
        	LOG.debug("is anycast");
        	// destination is anycast, set optional constraints
        	OptionalConstraintValue destAmountValue = new OptionalConstraintValue();
        	//String destAmountObject = new String("2");
        	destAmountValue.getAny().add(new String("2"));
        	
        	OptionalConstraintType destAmountType = new OptionalConstraintType();
        	destAmountType.setCategory(new String("destAmount"));
        	destAmountType.setValue(destAmountValue);
        	
        	createResvReq.getOptionalConstraint().add(destAmountType);
        }*/
        
        //LOG.debug("MB OptionalConstraint size: " + Integer.toString(createResvReq.getOptionalConstraint().size()));

        try {
            // First, generate a new GRI if this request does not have one yet.
            RMGenerateGRIAction rmGenerateGRIAction = null;
            if ((this.getId() == null) || (this.getId().startsWith(CoordRequest.LOCALID_PREFIX))){
                // Check if the request already has a GRI
                if (createResvReq.getGlobalReservationId() != null) {
                    this.setGRI(createResvReq.getGlobalReservationId());
                } else {
                    // No GRI yet. Ask the Resource Manager to generate one.
                    rmGenerateGRIAction = new RMGenerateGRIAction (this.getName() + "-GenerateGRI", this);
                    // synchronous call
                    rmGenerateGRIAction.execute();
                    if (rmGenerateGRIAction.getState() == CoordAction.State.FAILED ){
                        throw rmGenerateGRIAction.getException();
                    }
                    this.setGRI(rmGenerateGRIAction.getResultData());
                    netLogger.setGRI(this.getGRI());
                }
            }

            // Store the tentative path into the resource manager
            ResDetails resDetails = new ResDetails();
            resDetails.setCreateTime(this.getReceivedTime());
            resDetails.setGlobalReservationId(this.getGRI());
            // Set description
            String description = createResvReq.getDescription();
            if (description != null) {
                resDetails.setDescription(description);
            } else {
                resDetails.setDescription("No description provided");
            }
            // Set login
            String login = (String) this.getAttribute(CoordRequest.LOGIN_ATTRIBUTE);
            resDetails.setLogin(login);
            // Set user constraints
            resDetails.setUserRequestConstraint(createResvReq.getUserRequestConstraint());
            resDetails.setReservedConstraint(createResvReq.getReservedConstraint());
            // Set Status
            resDetails.setStatus(StateEngineValues.ACCEPTED);
            needToUpdateStatus = true;
            // Create, set and invoke the RMStoreAction
            RMStoreAction rmStoreAction = new RMStoreAction(this.getName() + "-RMStoreAction", this, resDetails);
            rmStoreAction.execute();
            if (rmStoreAction.getState().equals(CoordAction.State.FAILED)){
                throw rmStoreAction.getException();
            }
            
            //LOG.debug("MB1 OptionalConstraint size: " + Integer.toString(createResvReq.getOptionalConstraint().size()));

            PCERuntimeAction pceRuntimeAction = new PCERuntimeAction (this.getName() + "-Create-PCERuntimeAction",
                                            this,
                                            null,
                                            this.getTransactionId(),
                                            "pceCreate");

            //ResCreateContent resCreateContent = this.getRequestData().getResCreateContent();

            //LOG.debug("MB2 OptionalConstraint size: " + Integer.toString(createResvReq.getOptionalConstraint().size()));

            PCEData pceData = new PCEData(createResvReq.getUserRequestConstraint(),
                    createResvReq.getReservedConstraint(),
                    createResvReq.getOptionalConstraint(),
                    null);

            //LOG.debug("MB3 OptionalConstraint size: " + Integer.toString(createResvReq.getOptionalConstraint().size()));

            pceRuntimeAction.setRequestData(pceData);

            //LOG.debug("MB4 OptionalConstraint size: " + Integer.toString(createResvReq.getOptionalConstraint().size()));

            this.add(pceRuntimeAction);
            this.executed();
        } catch (Exception ex ) {
            String message = ex.getMessage();
            if (message == null ) {
                message = ex.toString();
            }
            LOG.warn(netLogger.error(method, ErrSev.MINOR, "caught Exception "+ message));
            if (needToUpdateStatus) {
                this.fail(new OSCARSServiceException(method + "caught Exception: " +message, "system"), StateEngineValues.FAILED);
            } else {
                this.fail2(new OSCARSServiceException(method + "caught Exception: " +message, "system"));
            }
        }
        LOG.debug(netLogger.end(method));
    }
    
    // When a create reservation fails, the status of the reservation must be set to FAILED.
    public void fail (Exception exception, String status) {
        String method = "CreateReservationRequest.execute";     
        LOG.error(netLogger.error(method, ErrSev.FATAL, " CreateReservation failed with "+ status));
        // update status
        RMUpdateStatusAction action = new RMUpdateStatusAction (this.getName() + "-RMStoreAction",
                                                                this,
                                                                this.getGRI(),
                                                                StateEngineValues.FAILED);
        action.execute();
        
        if (action.getState() == CoordAction.State.FAILED) {
            LOG.error(netLogger.error(method,ErrSev.MAJOR,"rmUpdateStatus failed with exception " +
                                      action.getException().getMessage()));
        } 
        this.exception = exception;
        this.setState(State.FAILED);
    }
  
    private void setLog() {
        this.netLogger = OSCARSNetLogger.getTlogger();
        this.netLogger.init(CoordRequest.moduleName,this.getTransactionId()); 
        this.netLogger.setGRI(this.getGRI());
    }

}

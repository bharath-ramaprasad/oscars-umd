package net.es.oscars.coord.actions;

import org.apache.log4j.Logger;

import net.es.oscars.coord.req.CoordRequest;
import net.es.oscars.coord.actions.CoordAction;

import net.es.oscars.coord.workers.RMWorker;
import net.es.oscars.utils.clients.RMClient;
import net.es.oscars.utils.soap.OSCARSServiceException;
import net.es.oscars.utils.sharedConstants.StateEngineValues;
import net.es.oscars.resourceManager.soap.gen.StoreReqContent;
import net.es.oscars.resourceManager.soap.gen.StoreRespContent;
import net.es.oscars.api.soap.gen.v06.ResDetails;


/**
 * RMStoreAction - uses an RMworker to send a store message to the ResourceManager
 * @author lomax
 *
 */
public class RMStoreAction extends CoordAction <StoreReqContent, StoreRespContent> {

    private static final long       serialVersionUID = 1L;

    @SuppressWarnings("unchecked")
    public RMStoreAction (String name, CoordRequest request, ResDetails resDetails )  {
        super (name, request, null);
        StoreReqContent storeContent = new StoreReqContent();
        storeContent.setReservation(resDetails);
        storeContent.setTransactionId(request.getTransactionId());
        this.setRequestData(storeContent);
    }
   /**
    * Send a store message to the Resource Manager that updates or stores a reservation
    * @param are set in the constructor: 
    */
    public void execute()  {
        // 
        try {
            RMWorker rmWorker = RMWorker.getInstance();
            RMClient rmClient = rmWorker.getRMClient();

            Object[] req = {this.getRequestData()};
            Object[] res = rmClient.invoke("store",req);
     
            if ((res == null) || (res[0] == null)) {
                throw new OSCARSServiceException ("RMStoreAction:No response from ResourceManager", "system");
            }
            StoreRespContent storeResponse = (StoreRespContent) res[0];
            this.setResultData(storeResponse);
            this.executed();            
        } catch (OSCARSServiceException ex) {
            this.fail2(ex);
        }
    } 
    
    public void executed() {
        this.setState(State.PROCESSED);
    }
}

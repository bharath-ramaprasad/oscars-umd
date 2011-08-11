package net.es.oscars.coord.actions;

import net.es.oscars.coord.req.CoordRequest;
import net.es.oscars.coord.workers.InternalAPIWorker;
import net.es.oscars.api.soap.gen.v06.ResDetails;
import net.es.oscars.utils.sharedConstants.NotifyRequestTypes;
import net.es.oscars.utils.sharedConstants.StateEngineValues;
import net.es.oscars.utils.soap.OSCARSServiceException;
import net.es.oscars.utils.topology.PathTools;


/**
 * CommittedForwarder sends a NotifyEvent message to the previous IDC (if any).
 * This is done during after a path has been calculated and is being committed.
 * 
 * @author lomax
 *
 */
public class CommittedForwarder extends ForwarderAction <ResDetails,Object> {

    private static final long       serialVersionUID = 1L;

    @SuppressWarnings("unchecked")
    public CommittedForwarder (String name, CoordRequest request, ResDetails resDetails) {
        super (name, request, resDetails);
    }
    
    public void execute()  {
        // Send Committed to the previous IDC
        try {
            String previousDomain = PathTools.getPreviousDomain(this.getRequestData().getReservedConstraint().getPathInfo().getPath(),
                                                                PathTools.getLocalDomainId());
            if ((previousDomain != null) && ( ! PathTools.getLocalDomainId().equals(previousDomain))) {
                
                InternalAPIWorker.getInstance().sendEventContent(this.getCoordRequest(), 
                                                                 this.getRequestData(), 
                                                                 "pceCommit",
                                                                 previousDomain);
            }
        } catch (OSCARSServiceException e) {
            this.fail(e, StateEngineValues.FAILED);   
        }
        this.setResultData(null);           
        this.executed();
    }  
}

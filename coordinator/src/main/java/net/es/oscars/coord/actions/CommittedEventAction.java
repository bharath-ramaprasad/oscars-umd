package net.es.oscars.coord.actions;

import net.es.oscars.coord.req.CoordRequest;
import net.es.oscars.coord.runtimepce.PCEData;
import net.es.oscars.coord.runtimepce.PCERuntimeAction;
import net.es.oscars.coord.workers.InternalAPIWorker;
import net.es.oscars.api.soap.gen.v06.ResDetails;
import net.es.oscars.utils.sharedConstants.NotifyRequestTypes;
import net.es.oscars.utils.soap.OSCARSServiceException;
import net.es.oscars.utils.topology.PathTools;


/**
 * Process a committed event from another IDC
 * 
 * @author lomax
 *
 */
public class CommittedEventAction extends CoordAction <ResDetails,Object> {

    private static final long       serialVersionUID = 1L;

    @SuppressWarnings("unchecked")
    public CommittedEventAction (String name, CoordRequest request, ResDetails resDetails) {
        super (name, request, resDetails);
    }
    
    public void execute()  {
        // Commit locally. The RuntimePCE will, in turn, when commit is completed, send the event to the previous IDC if any.
 
        ResDetails resDetails = this.getRequestData();
        
        // Start the commit phase
        PCERuntimeAction pceRuntimeAction = new PCERuntimeAction (this.getName() + "-Commit-PCERuntimeAction",
                                                                  this.getCoordRequest(),
                                                                  null,
                                                                  "ToBeChanged", // TODO: update with transaction id when avail.    
                                                                  "pceCommit");

        PCEData pceData = new PCEData(resDetails.getUserRequestConstraint(),
                                      resDetails.getReservedConstraint(),
                                      resDetails.getOptionalConstraint(),
                                      null);

        pceRuntimeAction.setRequestData(pceData);

        this.add(pceRuntimeAction);
 
        this.setResultData(null);           
        this.executed();
    }  
}

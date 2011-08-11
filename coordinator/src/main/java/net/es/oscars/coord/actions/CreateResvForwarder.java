package net.es.oscars.coord.actions;

import net.es.oscars.coord.req.CoordRequest;
import net.es.oscars.coord.workers.InternalAPIWorker;
import net.es.oscars.api.soap.gen.v06.ResDetails;
import net.es.oscars.utils.soap.OSCARSServiceException;
import net.es.oscars.utils.topology.PathTools;
import net.es.oscars.utils.sharedConstants.StateEngineValues;


/**
 * CreateResvForwarder sends a CreateReservation message to the next IDC (if any).
 * This is done when a path is calculated
 * 
 * @author lomax
 *
 */
public class CreateResvForwarder extends ForwarderAction <ResDetails,Object> {

    private static final long       serialVersionUID = 1L;

    @SuppressWarnings("unchecked")
    public CreateResvForwarder (String name, CoordRequest request, ResDetails resDetails) {
        super (name, request, resDetails);
    }
    
    public void execute()  {
        // Send ResCreate to the next IDC
        try {
            String nextDomain = PathTools.getNextDomain(this.getRequestData().getReservedConstraint().getPathInfo().getPath(),
                                                        PathTools.getLocalDomainId());
            if ((nextDomain != null) && ( ! PathTools.getLocalDomainId().equals(nextDomain))) {
                
                InternalAPIWorker.getInstance().sendResCreateContent(this.getCoordRequest(), 
                                                                     this.getRequestData(), 
                                                                     nextDomain);
            }
        } catch (OSCARSServiceException e) {
            this.fail(e, StateEngineValues.FAILED);
        }
        this.setResultData(null);           
        this.executed();
    }  
}

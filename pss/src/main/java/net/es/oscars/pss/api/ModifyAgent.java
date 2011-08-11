package net.es.oscars.pss.api;

import java.util.List;

/**
 * An agent that implements modification of existing services
 *
 * Must be able to handle being passed multiple requests for modification at the same time
 *
 * @author haniotak
 *
 */
public interface ModifyAgent extends PSSAgent {
    /**
     * Will implement the modification requests
     *
     * @param reqs the requests to implement (should be modification)
     * @param routerName the router to implement at
     * @return returns the list of requests
     * @throws PSSException
     */
    public List<PSSAction> modify(List<PSSAction> actions) throws PSSException;
}

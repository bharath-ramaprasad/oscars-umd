package net.es.oscars.pss.api;

import java.util.List;

/**
 *
 * @author haniotak
 *
 */

public interface StatusAgent extends PSSAgent {
    /**
     *
     * @param actions
     * @return
     * @throws PSSException
     */
    public List<PSSAction> status(List<PSSAction> actions) throws PSSException;
}

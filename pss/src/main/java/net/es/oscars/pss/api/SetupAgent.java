package net.es.oscars.pss.api;

import java.util.List;

/**
 * @author haniotak
 *
 */
public interface SetupAgent extends PSSAgent {
    /**
     *
     * @param actions
     * @return
     * @throws PSSException
     */
    public List<PSSAction> setup(List<PSSAction> actions) throws PSSException;
}

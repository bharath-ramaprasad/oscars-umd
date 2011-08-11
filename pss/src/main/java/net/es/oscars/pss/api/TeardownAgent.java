package net.es.oscars.pss.api;

import java.util.List;

/**
 * @author haniotak
 *
 */
public interface TeardownAgent extends PSSAgent {
    public List<PSSAction> teardown(List<PSSAction> actions) throws PSSException;
}

package net.es.oscars.pss.api;

/**
 * An agent that transforms PSS requests
 * @author haniotak
 *
 */
public interface NotifyAgent extends PSSAgent {
    /**
     *
     * processes an action and returns a transformed action as the result
     *
     * this is an interface meant to be used by pre- or post- processing agents
     *
     * @param req the request to be processed
     * @throws PSSException
     * @return the transformed request
     */
    public PSSAction process(PSSAction action) throws PSSException;

}

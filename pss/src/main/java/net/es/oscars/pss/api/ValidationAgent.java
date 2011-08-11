package net.es.oscars.pss.api;

/**
 * A validator that checks PSS requests for errors
 *
 * @author haniotak
 *
 */
public interface ValidationAgent extends PSSAgent {
    /**
     *
     * Validates request for errors
     *
     * Just return if everything is OK
     * Throw an exception if an error is detected
     *
     * @param req the request to be validated
     * @throws PSSException
     */
    public void validate(PSSRequest req) throws PSSException;

}

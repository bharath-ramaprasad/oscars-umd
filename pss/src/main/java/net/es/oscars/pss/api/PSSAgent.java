package net.es.oscars.pss.api;

import java.util.Map;
/**
 * Base interface for the various PSS java agents
 * @author haniotak
 *
 */
public interface PSSAgent {
    /**
     * Sets agent options - the semantics are agent-specific
     * @param options a Map of options
     */
    @SuppressWarnings("rawtypes")
    public void setOptions(Map options);
}

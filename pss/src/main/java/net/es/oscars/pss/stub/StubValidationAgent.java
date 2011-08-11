package net.es.oscars.pss.stub;


import java.util.Map;

import net.es.oscars.pss.api.PSSException;
import net.es.oscars.pss.api.PSSRequest;
import net.es.oscars.pss.api.ValidationAgent;

public class StubValidationAgent implements ValidationAgent {

    /**
     * the stub validator accepts all requests
     */
    public void validate(PSSRequest req) throws PSSException {
        return;
    }

    @SuppressWarnings("unchecked")
    public void setOptions(Map options) {

    }

}

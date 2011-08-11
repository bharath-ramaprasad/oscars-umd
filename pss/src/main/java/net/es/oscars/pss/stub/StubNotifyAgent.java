package net.es.oscars.pss.stub;

import java.util.Map;

import net.es.oscars.pss.api.NotifyAgent;
import net.es.oscars.pss.api.PSSAction;
import net.es.oscars.pss.api.PSSException;

public class StubNotifyAgent implements NotifyAgent {

    public PSSAction process(PSSAction action) throws PSSException {
        return action;
    }

    @SuppressWarnings("unchecked")
    public void setOptions(Map options) {
        // TODO Auto-generated method stub
    }

}

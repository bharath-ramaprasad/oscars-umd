package net.es.oscars.pss.stub;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.es.oscars.pss.api.ModifyAgent;
import net.es.oscars.pss.api.PSSAction;
import net.es.oscars.pss.api.PSSActionStatus;
import net.es.oscars.pss.common.PSSAgentFactory;

public class StubModifyAgent implements ModifyAgent {

    /**
     * Always fails (for now)
     */
    public List<PSSAction> modify(List<PSSAction> actions) {
        ArrayList<PSSAction> results = new ArrayList<PSSAction>();
        for (PSSAction action : actions) {
            action.setStatus(PSSActionStatus.FAIL);
            results.add(action);
            PSSAgentFactory.getInstance().getWorkflowAgent().update(action);
        }
        return results;
    }

    @SuppressWarnings("unchecked")
    public void setOptions(Map options) {
        // TODO Auto-generated method stub

    }

}

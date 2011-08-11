package net.es.oscars.pss.test.sim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import net.es.oscars.pss.api.PSSAction;
import net.es.oscars.pss.api.PSSActionStatus;
import net.es.oscars.pss.api.TeardownAgent;
import net.es.oscars.pss.common.PSSAgentFactory;

public class SimTeardownAgent implements TeardownAgent {
    @SuppressWarnings("unchecked")
    private HashMap options;
    private Logger log = Logger.getLogger(SimTeardownAgent.class);

    public List<PSSAction> teardown(List<PSSAction> actions) {
        int totalTime = 2;
        log.debug("teardown.start");

        ArrayList<PSSAction> results = new ArrayList<PSSAction>();
        for (PSSAction action : actions) {
            try {
                Thread.sleep(1000*totalTime);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            action.setStatus(PSSActionStatus.SUCCESS);
            results.add(action);
            PSSAgentFactory.getInstance().getWorkflowAgent().update(action);
        }
        log.debug("teardown.end");
        return results;
    }

    @SuppressWarnings("unchecked")
    public void setOptions(Map options) {
        this.options = new HashMap();
        if (options != null) {
            this.options.putAll(options);
        }
    }

}

package net.es.oscars.pss.test.sim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

import net.es.oscars.pss.api.PSSAction;
import net.es.oscars.pss.api.PSSActionStatus;
import net.es.oscars.pss.api.StatusAgent;
import net.es.oscars.pss.common.PSSAgentFactory;

public class SimStatusAgent implements StatusAgent {
    @SuppressWarnings("unchecked")
    private HashMap options;
    private Logger log = Logger.getLogger(SimStatusAgent.class);


    public List<PSSAction> status(List<PSSAction> actions) {
        log.debug("status.start");
        Random rand = new Random();
        ArrayList<PSSAction> results = new ArrayList<PSSAction>();
        for (PSSAction action : actions) {
            int timeToStatus = 1 + rand.nextInt(3);
            try {
                Thread.sleep(1000*timeToStatus);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            action.setStatus(PSSActionStatus.SUCCESS);
            results.add(action);
            PSSAgentFactory.getInstance().getWorkflowAgent().update(action);
        }
        log.debug("status.end");
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

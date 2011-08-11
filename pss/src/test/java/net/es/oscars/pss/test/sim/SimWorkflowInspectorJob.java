package net.es.oscars.pss.test.sim;

import java.util.ArrayList;

import net.es.oscars.pss.api.PSSAction;
import net.es.oscars.pss.api.PSSException;
import net.es.oscars.pss.api.WorkflowAgent;
import net.es.oscars.pss.common.PSSAgentFactory;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SimWorkflowInspectorJob implements Job {
    private Logger log = Logger.getLogger(SimWorkflowInspectorJob.class);

    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        WorkflowAgent wfAgent = PSSAgentFactory.getInstance().getWorkflowAgent();
        PSSAction next = wfAgent.next();
        if (next != null) {
            ArrayList<PSSAction> actions = new ArrayList<PSSAction>();
            actions.add(next);
            try {
                wfAgent.process(actions);
            } catch (PSSException e) {
                log.error(e);
                e.printStackTrace();
            }
            wfAgent.update(next);
        }
    }

}

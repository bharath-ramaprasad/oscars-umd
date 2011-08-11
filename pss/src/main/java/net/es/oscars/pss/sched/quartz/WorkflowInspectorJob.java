package net.es.oscars.pss.sched.quartz;

import java.util.ArrayList;

import net.es.oscars.pss.api.PSSAction;
import net.es.oscars.pss.api.PSSException;
import net.es.oscars.pss.api.WorkflowAgent;
import net.es.oscars.pss.common.PSSAgentFactory;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class WorkflowInspectorJob implements Job {
    private Logger log = Logger.getLogger(WorkflowInspectorJob.class);

    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        log.debug("starting up");
        WorkflowAgent wfAgent = PSSAgentFactory.getInstance().getWorkflowAgent();
        PSSAction next = wfAgent.next();
        if (next != null) {
            ArrayList<PSSAction> actions = new ArrayList<PSSAction>();
            actions.add(next);
            try {
                wfAgent.process(actions);
            } catch (PSSException e) {
                e.printStackTrace();
            }
            wfAgent.update(next);
        }
    }

}

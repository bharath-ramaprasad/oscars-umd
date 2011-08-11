package net.es.oscars.pss.workflow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import net.es.oscars.pss.api.NotifyAgent;
import net.es.oscars.pss.api.PSSException;
import net.es.oscars.pss.api.PSSAction;
import net.es.oscars.pss.api.PSSActionStatus;
import net.es.oscars.pss.api.WorkflowAgent;
import net.es.oscars.pss.common.PSSAgentFactory;
import net.es.oscars.pss.notify.CoordNotifier;

/**
 * a workflow that performs actions exactly one. at. a. time in
 * strict sequential order
 *
 * @author haniotak
 *
 */
public class FifoWF implements WorkflowAgent {
    private LinkedBlockingQueue<PSSAction> outstanding;
    private PSSAction running;
    private LinkedBlockingQueue<PSSAction> completed;
    private Logger log = Logger.getLogger(FifoWF.class);
    private Integer idx = 0;

    public FifoWF() {
        outstanding = new LinkedBlockingQueue<PSSAction>();
        completed = new LinkedBlockingQueue<PSSAction>();
    }

    public synchronized List<PSSAction> getCompleted() {
        ArrayList<PSSAction> result = new ArrayList<PSSAction>();
        result.addAll(completed);
        return result;
    }

    public synchronized PSSAction next() {
        if (running == null) {
            // nothing to do
            if (outstanding.isEmpty()) {
                return null;
            }
            // get first item in the queue, set it as running, return
            running = outstanding.remove();
            return running;
        } else {
            // If something is already running we have to wait for it to complete
            return null;
        }
    }

    public synchronized void add(PSSAction action) throws PSSException {
        if (action.getRequest().getId() == null) {
            action.getRequest().setId(idx.toString());
            idx++;
        }
        log.debug("adding to workflow: "+action.getActionType()+" for "+action.getRequest().getId());
        outstanding.add(action);
    }

    public synchronized void update(PSSAction action) {
        PSSActionStatus status = action.getStatus();
        if (action.equals(running)) {
            running.setStatus(status);

            if (status.equals(PSSActionStatus.SUCCESS)) {
                log.info("Success!");
                completed.add(running);
            } else if (status.equals(PSSActionStatus.FAIL)) {
                log.equals("failure :(");
                completed.add(running);
            }
            
            NotifyAgent not = PSSAgentFactory.getInstance().getNotifyAgent();
            try {
                not.process(action);
            } catch (PSSException e) {
                log.error(e);
                e.printStackTrace();
            }
            running = null;
        }

    }

    public synchronized void remove(PSSAction action) throws PSSException {
        if (action.equals(running)) {
            throw new PSSException ("Can not remove running request op");
        } else {
            if (outstanding.contains(action)) {
                outstanding.remove(action);
            }
            if (completed.contains(action)) {
                completed.remove(action);
            }
        }

    }


    @SuppressWarnings("rawtypes")
    public void setOptions(Map options) {
        // TODO Auto-generated method stub
    }

    public boolean hasOutstanding() {
        return !outstanding.isEmpty();
    }

    public synchronized void process(List<PSSAction> actions) throws PSSException {
        if (actions.isEmpty()) return;
        log.debug("processing "+actions.size()+" action(s)");
        for (PSSAction action : actions) {
            // one at a time
            ArrayList<PSSAction> oneAction = new ArrayList<PSSAction>();
            oneAction.add(action);
            switch (action.getActionType()) {
                case SETUP :
                    PSSAgentFactory.getInstance().getSetupAgent().setup(oneAction);
                break;
                case TEARDOWN:
                    PSSAgentFactory.getInstance().getTeardownAgent().teardown(oneAction);
                break;
                case MODIFY:
                    PSSAgentFactory.getInstance().getModifyAgent().modify(oneAction);
                break;
                case STATUS:
                    PSSAgentFactory.getInstance().getStatusAgent().status(oneAction);
                break;
            }
        }

    }


}

package net.es.oscars.pss.common;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import net.es.oscars.pss.api.NotifyAgent;
import net.es.oscars.pss.api.StatusAgent;
import net.es.oscars.pss.api.ModifyAgent;
import net.es.oscars.pss.api.PSSException;
import net.es.oscars.pss.api.TeardownAgent;
import net.es.oscars.pss.api.WorkflowAgent;
import net.es.oscars.pss.api.SetupAgent;
import net.es.oscars.pss.api.ValidationAgent;
import net.es.oscars.pss.stub.StubModifyAgent;
import net.es.oscars.pss.stub.StubNotifyAgent;
import net.es.oscars.pss.stub.StubSetupAgent;
import net.es.oscars.pss.stub.StubStatusAgent;
import net.es.oscars.pss.stub.StubTeardownAgent;
import net.es.oscars.pss.stub.StubValidationAgent;
import net.es.oscars.pss.workflow.FifoWF;

/**
 * responsible for configuring & loading the various PSS agent classes
 * @author haniotak
 *
 */
public class PSSAgentFactory {
    private enum CFG {
        SETUP { public String toString() { return "setup"; } },
        TEARDOWN { public String toString() { return "teardown"; } },
        MODIFY { public String toString() { return "modify"; } },
        STATUS { public String toString() { return "status"; } },
        WORKFLOW { public String toString() { return "workflow"; } },
        VALIDATE { public String toString() { return "validate"; } },
        NOTIFY { public String toString() { return "notify"; } },
    }

    private NotifyAgent notifyAgent;


	private SetupAgent setupAgent;
    private TeardownAgent teardownAgent;
    private ModifyAgent modifyAgent;
    private StatusAgent statusAgent;
    private WorkflowAgent workflowAgent;
    private ValidationAgent validationAgent;
    private static PSSAgentFactory instance;
    private Logger log = Logger.getLogger(PSSAgentFactory.class);

    private PSSAgentFactory() {
    }

    /**
     * singleton constructor
     * @return
     */
    public static PSSAgentFactory getInstance() {
        if (instance == null) {
            instance = new PSSAgentFactory();
        }
        return instance;
    }

    /**
     * will throw an exception if an agent is not set
     * @throws PSSException
     */
    public void health() throws PSSException {
        if (setupAgent == null) {
            throw new PSSException("Setup agent not set!");
        } else if (teardownAgent == null) {
            throw new PSSException("Teardown agent not set!");
        } else if (modifyAgent == null) {
            throw new PSSException("Modify agent not set!");
        } else if (statusAgent == null) {
            throw new PSSException("Status agent not set!");
        } else if (workflowAgent == null) {
            throw new PSSException("Scheduling agent not set!");
        } else if (validationAgent == null) {
            throw new PSSException("Validation agent not set!");
        } else if (notifyAgent == null) {
            throw new PSSException("Notify agent not set!");
        }
        /*
        log.debug("PSS agent factory in good health");
        log.debug(setupAgent.getClass().getCanonicalName());
        log.debug(teardownAgent.getClass().getCanonicalName());
        log.debug(modifyAgent.getClass().getCanonicalName());
        log.debug(statusAgent.getClass().getCanonicalName());
        log.debug(workflowAgent.getClass().getCanonicalName());
        log.debug(validationAgent.getClass().getCanonicalName());
        */
    }


    /**
     * configures the agent factory through YAML from the argument filename
     * loads and configures agent classes
     *
     * @param filename
     */
    @SuppressWarnings("rawtypes")
    public void configure() {

        Map pssConfig = (Map) PSSConfigHolder.getInstance().getConfig().get("pss");

        if (pssConfig == null) {
            log.error("No pss configuration stanza!");
            System.err.println("No pss configuration stanza!");
            System.exit(1);
        }
        String[] agents = { CFG.NOTIFY.toString(), CFG.SETUP.toString(), CFG.TEARDOWN.toString(), CFG.MODIFY.toString(), CFG.STATUS.toString(), CFG.WORKFLOW.toString(), CFG.VALIDATE.toString() };

        for (String agent : agents) {
            Map userConfig = (Map) pssConfig.get(agent);
            // avoid null pointers
            if (userConfig == null) {
                log.info("no config for agent: "+agent);
                userConfig = new HashMap();
            }
            String agentClass = (String) userConfig.get("agent");
            Map agentOptions = (Map) userConfig.get("options");
            loadAgent(agent, agentClass, agentOptions);
        }
    }

    /**
     * Dynamically loads an agent class
     *
     * @param agent
     * @param className
     * @param agentOptions
     */
    @SuppressWarnings("rawtypes")
    private void loadAgent(String agent, String className, Map agentOptions) {
        boolean loaded = false;
        if (className == null) {
            log.debug("agent: "+agent+" , no class name set");
        } else {
            log.debug("agent: "+agent+" , loading: "+className);
        }

        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        Class agentClass = null;
        try {
            agentClass = cl.loadClass(className);
            if (agent.equals(CFG.SETUP.toString())) {
                SetupAgent agentInstance = (SetupAgent) agentClass.newInstance();
                this.setSetupAgent(agentInstance);
            } else if (agent.equals(CFG.TEARDOWN.toString())) {
                TeardownAgent agentInstance = (TeardownAgent) agentClass.newInstance();
                this.setTeardownAgent(agentInstance);
            } else if (agent.equals(CFG.NOTIFY.toString())) {
                NotifyAgent agentInstance = (NotifyAgent) agentClass.newInstance();
                this.setNotifyAgent(agentInstance);
            } else if (agent.equals(CFG.MODIFY.toString())) {
                ModifyAgent agentInstance = (ModifyAgent) agentClass.newInstance();
                this.setModifyAgent(agentInstance);
            } else if (agent.equals(CFG.STATUS.toString())) {
                StatusAgent agentInstance = (StatusAgent) agentClass.newInstance();
                this.setStatusAgent(agentInstance);
            } else if (agent.equals(CFG.WORKFLOW.toString())) {
                WorkflowAgent agentInstance = (WorkflowAgent) agentClass.newInstance();
                this.setWorkflowAgent(agentInstance);
            } else if (agent.equals(CFG.VALIDATE.toString())) {
                ValidationAgent agentInstance = (ValidationAgent) agentClass.newInstance();
                this.setValidationAgent(agentInstance);
            }
            loaded = true;
            log.debug("agent: "+agent+" , loaded OK: "+className);

        } catch (ClassNotFoundException e) {
            log.debug("agent: "+agent+" , could not find class: "+className+ " , loading a stub");
        } catch (InstantiationException e) {
            log.debug("agent: "+agent+" , could not init class: "+className+ " , loading a stub");
        } catch (IllegalAccessException e) {
            log.debug("agent: "+agent+" , could not access class: "+className+ " , loading a stub");
        }

        if (!loaded) {
            if (agent.equals(CFG.SETUP.toString())) {
                this.setSetupAgent(new StubSetupAgent());
            } else if (agent.equals(CFG.MODIFY.toString())) {
                this.setModifyAgent(new StubModifyAgent());
            } else if (agent.equals(CFG.NOTIFY.toString())) {
                this.setNotifyAgent(new StubNotifyAgent());
            } else if (agent.equals(CFG.TEARDOWN.toString())) {
                this.setTeardownAgent(new StubTeardownAgent());
            } else if (agent.equals(CFG.STATUS.toString())) {
                this.setStatusAgent(new StubStatusAgent());
            } else if (agent.equals(CFG.WORKFLOW.toString())) {
                this.setWorkflowAgent(new FifoWF());
            } else if (agent.equals(CFG.VALIDATE.toString())) {
                this.setValidationAgent(new StubValidationAgent());
            }
        }

        if (agent.equals(CFG.SETUP.toString())) {
            this.getSetupAgent().setOptions(agentOptions);
        } else if (agent.equals(CFG.TEARDOWN.toString())) {
            this.getTeardownAgent().setOptions(agentOptions);
        } else if (agent.equals(CFG.MODIFY.toString())) {
            this.getModifyAgent().setOptions(agentOptions);
        } else if (agent.equals(CFG.NOTIFY.toString())) {
            this.getNotifyAgent().setOptions(agentOptions);
        } else if (agent.equals(CFG.STATUS.toString())) {
            this.getStatusAgent().setOptions(agentOptions);
        } else if (agent.equals(CFG.WORKFLOW.toString())) {
            this.getWorkflowAgent().setOptions(agentOptions);
        } else if (agent.equals(CFG.VALIDATE.toString())) {
            this.getValidationAgent().setOptions(agentOptions);
        }
    }




    public SetupAgent getSetupAgent() {
        return setupAgent;
    }

    public void setSetupAgent(SetupAgent setupAgent) {
        this.setupAgent = setupAgent;
    }

    public TeardownAgent getTeardownAgent() {
        return teardownAgent;
    }

    public void setTeardownAgent(TeardownAgent teardownAgent) {
        this.teardownAgent = teardownAgent;
    }

    public void setStatusAgent(StatusAgent statusAgent) {
        this.statusAgent = statusAgent;
    }

    public StatusAgent getStatusAgent() {
        return statusAgent;
    }

    public void setWorkflowAgent(WorkflowAgent workflowAgent) {
        this.workflowAgent = workflowAgent;
    }

    public WorkflowAgent getWorkflowAgent() {
        return workflowAgent;
    }

    public void setModifyAgent(ModifyAgent modifyAgent) {
        this.modifyAgent = modifyAgent;
    }

    public ModifyAgent getModifyAgent() {
        return modifyAgent;
    }

    public void setValidationAgent(ValidationAgent validationAgent) {
        this.validationAgent = validationAgent;
    }

    public ValidationAgent getValidationAgent() {
        return validationAgent;
    }
    public NotifyAgent getNotifyAgent() {
        return notifyAgent;
    }

    public void setNotifyAgent(NotifyAgent notifyAgent) {
        this.notifyAgent = notifyAgent;
    }

}

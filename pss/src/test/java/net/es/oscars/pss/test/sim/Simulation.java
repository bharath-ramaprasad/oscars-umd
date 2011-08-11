package net.es.oscars.pss.test.sim;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;

import net.es.oscars.utils.config.ConfigHelper;
import net.es.oscars.pss.api.PSSException;
import net.es.oscars.pss.common.PSSAgentFactory;
import net.es.oscars.pss.common.PSSConfigHolder;
import net.es.oscars.pss.sched.quartz.PSSScheduler;

public class Simulation {

    private String simConfigFn = "sim.yaml";
    private String pssConfigFn = "config.yaml";

    @SuppressWarnings("unchecked")
    private Map config;
    private List<SimRequest> requests;

    private boolean ready = false;
    private boolean doneSubmitting = false;

    private static Simulation instance = null;
    private Logger log = Logger.getLogger(Simulation.class);

    private Simulation() {
        this.configure();
    }

    public static Simulation getInstance() {
        if (instance == null) {
            instance = new Simulation();
        }
        return instance;
    }

    public void run() {
        log.debug("simulation.run.start");

        log.debug("starting PSS main scheduler");
        PSSScheduler sched = PSSScheduler.getInstance();
        try {
            sched.setWorkflowInspector(SimWorkflowInspectorJob.class);
            sched.start();
        } catch (PSSException ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();
        }

        PSSConfigHolder.getInstance().loadConfig(pssConfigFn);
        PSSAgentFactory.getInstance().configure();
        this.startSubmitterCreator();

        log.debug("expecting submissions..");
        while (!ready || !doneSubmitting) {
            try {
                System.out.print(".");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        log.debug("finishing remaining requests..");

        boolean done = false;
        while (!done) {
            try {
                Thread.sleep(1000);
                if (!PSSAgentFactory.getInstance().getWorkflowAgent().hasOutstanding()) {
                    done = true;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        log.debug("simulation.run.end");
        PSSScheduler.getInstance().stop();
    }



    private void startSubmitterCreator() {

        // a new job that creates submitter jobs
        Date date = new Date();
        SimpleTrigger trigger = new SimpleTrigger("SimSubmitterCreatorJob", null, date, null, 0, 0L);
        JobDetail jobDetail = new JobDetail("SimSubmitterCreatorJob", "SimSubmitterCreatorJob", SimSubmitterCreatorJob.class);
        JobDataMap dataMap = new JobDataMap();
        dataMap.put("requests", requests);
        jobDetail.setJobDataMap(dataMap);
        try {
            log.debug("starting sim scheduler");
            SimScheduler.getInstance().start();
            SimScheduler.getInstance().getScheduler().scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    private void configure() {
        config = ConfigHelper.getConfiguration(simConfigFn);
        assert config != null : "No configuration";
    }



    @SuppressWarnings("unchecked")
    public Map getConfig() {
        return config;
    }

    @SuppressWarnings("unchecked")
    public void setConfig(Map config) {
        this.config = config;
    }

    public List<SimRequest> getRequests() {
        return requests;
    }


    public void setRequests(List<SimRequest> requests) {
        this.requests = requests;
    }


    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public boolean isReady() {
        return ready;
    }

    public void setDoneSubmitting(boolean doneSubmitting) {
        this.doneSubmitting = doneSubmitting;
    }

    public boolean isDoneSubmitting() {
        return doneSubmitting;
    }


}

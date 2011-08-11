package net.es.oscars.coord.jobs;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


import net.es.oscars.coord.req.PathCtrlRequest;

public class PathCtrlRequestCleanerJob implements Job {
    
    public PathCtrlRequestCleanerJob () {
        
    }
    
    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        
        // Call the event cleaning operation
        PathCtrlRequest.cleanup();
    }

}

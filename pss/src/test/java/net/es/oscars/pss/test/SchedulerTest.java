package net.es.oscars.pss.test;

import java.util.ArrayList;
import java.util.Map;

import net.es.oscars.pss.test.sim.SimRequest;
import net.es.oscars.pss.test.sim.SimRequestGenerator;
import net.es.oscars.pss.test.sim.Simulation;
import org.testng.annotations.*;

public class SchedulerTest {

    @SuppressWarnings("unchecked")
    @Test
    public void testScheduler() {
        Simulation sim = Simulation.getInstance();
        SimRequestGenerator gen = new SimRequestGenerator();

        Map config = sim.getConfig();
        ArrayList<SimRequest> requests = gen.makeRequests(config);

        sim.setRequests(requests);
        sim.run();
    }


}

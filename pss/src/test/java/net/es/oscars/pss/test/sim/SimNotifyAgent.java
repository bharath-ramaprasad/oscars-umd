package net.es.oscars.pss.test.sim;

import java.net.URL;
import java.util.Map;

import org.apache.log4j.Logger;

import net.es.oscars.api.soap.gen.v06.EventContent;
import net.es.oscars.common.soap.gen.SubjectAttributes;
import net.es.oscars.pss.api.PSSAction;
import net.es.oscars.pss.api.PSSActionStatus;
import net.es.oscars.pss.api.PSSException;
import net.es.oscars.pss.api.NotifyAgent;
import net.es.oscars.pss.common.PSSConfigHolder;
import net.es.oscars.utils.config.ContextConfig;
import net.es.oscars.utils.svc.ServiceNames;
import net.es.oscars.utils.sharedConstants.NotifyRequestTypes;
import net.es.oscars.utils.clients.CoordClient;

public class SimNotifyAgent implements NotifyAgent {
    private Logger log = Logger.getLogger(SimNotifyAgent.class);

    @SuppressWarnings("unchecked")
    public PSSAction process(PSSAction action) throws PSSException {

        return action;
    }

    @SuppressWarnings("unchecked")
    public void setOptions(Map options) {
        // TODO Auto-generated method stub

    }

}

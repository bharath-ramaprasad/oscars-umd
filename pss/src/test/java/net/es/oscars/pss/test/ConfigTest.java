package net.es.oscars.pss.test;

import net.es.oscars.pss.api.PSSException;
import net.es.oscars.pss.common.PSSAgentFactory;
import net.es.oscars.pss.common.PSSConfigHolder;
import net.es.oscars.utils.config.ConfigDefaults;
import net.es.oscars.utils.config.ConfigException;
import net.es.oscars.utils.config.ContextConfig;
import net.es.oscars.utils.svc.ServiceNames;

import org.testng.annotations.Test;

public class ConfigTest {
    @Test (expectedExceptions = PSSException.class)
    public void testBadHealth() throws PSSException {
        System.out.println("starting testBadHealth");
        // throw exception if asked for health without any config
        PSSAgentFactory.getInstance().health();
        System.out.println("testBadHealth end");
    }

    @Test (dependsOnMethods={"testBadHealth" })
    public void testConfig() throws PSSException, ConfigException {
        // set up our configuration context
        ContextConfig cc = ContextConfig.getInstance();
        cc.loadManifest(ConfigDefaults.MANIFEST);
        cc.setContext(ConfigDefaults.CTX_TESTING);
        cc.setServiceName(ServiceNames.SVC_PSS);
        String configFilePath = cc.getFilePath(ConfigDefaults.CONFIG);

        // actually load the config
        PSSConfigHolder.getInstance().loadConfig(configFilePath);

        // if you configure first, then ask health must be OK
        PSSAgentFactory.getInstance().configure();
        PSSAgentFactory.getInstance().health();
    }
}

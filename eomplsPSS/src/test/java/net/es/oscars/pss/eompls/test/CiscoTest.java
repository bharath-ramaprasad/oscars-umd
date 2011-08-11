package net.es.oscars.pss.eompls.test;

import net.es.oscars.pss.api.PSSException;
import net.es.oscars.pss.eompls.ios.IOSSetupConfigGen;
import net.es.oscars.pss.eompls.topo.YamlAddressResolver;
import net.es.oscars.utils.config.ConfigDefaults;
import net.es.oscars.utils.config.ConfigException;
import net.es.oscars.utils.config.ContextConfig;
import net.es.oscars.utils.svc.ServiceNames;

import org.testng.annotations.Test;

@Test
public class CiscoTest {
    @Test
    public void testSetup() throws PSSException, ConfigException {
        // set up our configuration context
        ContextConfig cc = ContextConfig.getInstance();
        cc.loadManifest("./config/"+ConfigDefaults.MANIFEST);
        cc.setContext(ConfigDefaults.CTX_TESTING);
        cc.setServiceName(ServiceNames.SVC_PSS);

        String cPath = cc.getFilePath("controlplane.yaml");
        YamlAddressResolver cr = YamlAddressResolver.getInstance();
        cr.loadConfig(cPath);

        String vlan = "999";

        String[] hops = new String[2];
        hops[0] = "10.1.2.3";
        hops[1] = "10.4.5.6";
        String bandwidth = "1000000";
        String egressLoopback = "10.0.0.1";
        String ifceDescription = "IFCE DESCRIPTION";
        String ifceName = "GigabitEthernet1/0";
        String lspDestination = "10.7.8.9";
        String pathName = "PATHNAME";
        String policyName = "POLICYNAME";
        String pseudowireName = "PSEUDOWIRE_NAME";
        String tunnelDescription = "TUNNEL DESCRIPTION";
        String vcid = "VCID";

        IOSSetupConfigGen gen = new IOSSetupConfigGen();
        gen.setBandwidth(bandwidth);
        gen.setHops(hops);
        gen.setEgressLoopback(egressLoopback);
        gen.setIfceDescription(ifceDescription);
        gen.setIfceName(ifceName);
        gen.setLspDestination(lspDestination);
        gen.setPathName(pathName);
        gen.setPolicyName(policyName);
        gen.setPseudowireName(pseudowireName);
        gen.setTunnelDescription(tunnelDescription);
        gen.setVlan(vlan);
        gen.setVcid(vcid);

        System.out.println(gen.generateConfig());
    }
}

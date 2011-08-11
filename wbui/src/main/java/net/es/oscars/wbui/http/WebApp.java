package net.es.oscars.wbui.http;
  
import java.net.URL;
import java.util.Map;
import java.io.FileInputStream;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.security.SslSocketConnector;
import org.mortbay.jetty.security.SslSelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;
import org.mortbay.xml.XmlConfiguration;

 
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBusFactory;
import org.apache.log4j.Logger;

import net.es.oscars.utils.config.*;
import net.es.oscars.utils.soap.*;
import net.es.oscars.utils.svc.ServiceNames;

public class WebApp {
    public static ContextConfig cc = null;
    public static void main(String[] args) throws Exception {

        cc = ContextConfig.getInstance();
        String context = "PRODUCTION";

        if (args.length > 0) {
            context = args[1];
        }
        String warFile = null;
        Server server = new Server();

        cc.setContext(context);
        System.setProperty("context",context);
        cc.setServiceName(ServiceNames.SVC_WBUI);
        System.out.println("starting WBUI with context "+ context);
        try {
            System.out.println("loading manifest from ./config/"+ConfigDefaults.MANIFEST);
            cc.loadManifest("./config/"+ConfigDefaults.MANIFEST); // manifest.yaml
            cc.setLog4j();

            String configFile = cc.getFilePath(ConfigDefaults.CONFIG);
            Map config = ConfigHelper.getConfiguration(configFile);
            Map http = (Map) config.get("http");
            warFile = (String) http.get("warFile");
            String jettyConf = cc.getFilePath("jetty.xml");
            XmlConfiguration configuration = new XmlConfiguration(new FileInputStream(jettyConf));
            configuration.configure(server);
        } catch (ConfigException ex) {
            System.out.println("caught ConfigurationException " + ex.getMessage());
            System.exit(-1);
        }
        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath("/OSCARS");
        webapp.setWar(warFile);
        server.setHandler(webapp);
        server.start();
        server.join();
    }
}

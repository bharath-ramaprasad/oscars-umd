package net.es.oscars.authZ.test.common;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

import net.es.oscars.authZ.common.AuthZCore;
import net.es.oscars.database.hibernate.Initializer;
import net.es.oscars.database.hibernate.HibernateUtil;
import net.es.oscars.logging.ModuleName;
import net.es.oscars.logging.OSCARSNetLogger;
import net.es.oscars.utils.config.ConfigDefaults;
import net.es.oscars.utils.config.ConfigException;
import net.es.oscars.utils.config.ConfigHelper;
import net.es.oscars.utils.config.ContextConfig;
import net.es.oscars.utils.svc.ServiceNames;

/**
 * These must be run before and after all tests in the suite.
 *
 * @author David Robertson (dwrobertson@lbl.gov)
 */
@Test(groups={ "init" })
public class InitTest {
    private static ContextConfig cc = ContextConfig.getInstance();
    private static String context = ConfigDefaults.CTX_TESTING;
    private static Logger log = null;


  @BeforeSuite
    public void setUpTests() {
      System.out.println("authZ setupTests");
      cc.setContext(context);
      cc.setServiceName(ServiceNames.SVC_AUTHZ);
      try {
          System.out.println("loading manifest from ./config/"+ConfigDefaults.MANIFEST);
          cc.loadManifest("./config/"+ConfigDefaults.MANIFEST); // manifest.yaml
          cc.setLog4j();
          // need to do this after the log4j.properties file has been set
          log = Logger.getLogger(InitTest.class);
      } catch (ConfigException ex) {
          System.out.println("caught ConfigurationException " + ex.getMessage());
          System.exit(-1);
      }
      OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
      netLogger.init(ModuleName.AUTHZ,"0000");
      AuthZCore core = AuthZCore.getInstance();
      log.debug( netLogger.end("initTest","using database " + core.getDbname()));
  }

  @AfterSuite
    public void teardownTests() {
        HibernateUtil.closeSessionFactory("testauthz");
    }
}

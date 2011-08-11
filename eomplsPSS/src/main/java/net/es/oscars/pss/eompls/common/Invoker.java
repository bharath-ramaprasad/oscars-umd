package net.es.oscars.pss.eompls.common;

import static java.util.Arrays.asList;

import org.hibernate.exception.ExceptionUtils;

import net.es.oscars.pss.common.PSSConfigHolder;
import net.es.oscars.utils.config.ConfigDefaults;
import net.es.oscars.utils.config.ContextConfig;
import net.es.oscars.utils.svc.ServiceNames;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

public class Invoker {
    public static void main(String[] args) throws Exception {

        // create a parser
        OptionParser parser = new OptionParser() {
            {
                acceptsAll( asList( "h", "?" ), "show help then exit" );
                accepts( "help", "show extended help then exit" );
                accepts( "mode" , "server / client mode" ).withRequiredArg().describedAs("client / server (default)").ofType(String.class);
            }
        };

        OptionSet options = parser.parse( args );

        // check for help
        if ( options.has( "?" ) ) {
            parser.printHelpOn( System.out );
            System.exit(0);
        }

        String mode = "server";
        if (options.has("mode")) {
            String optVal = (String) options.valueOf("mode");
            if (optVal.equals("client")) {
                mode = "client";
            } else if (!optVal.equals("server")) {
                parser.printHelpOn( System.out );
                System.exit(1);
            }
        }

        ContextConfig cc = ContextConfig.getInstance();
        cc.loadManifest("./config/"+ConfigDefaults.MANIFEST);
        cc.setContext(ConfigDefaults.CTX_PRODUCTION);
        cc.setServiceName(ServiceNames.SVC_PSS);
        String configFilePath = cc.getFilePath(ConfigDefaults.CONFIG);
        PSSConfigHolder.getInstance().loadConfig(configFilePath);

        if (mode.equals("server")) {
            try {
                EoMPLSPSSSoapServer server = EoMPLSPSSSoapServer.getInstance();
                server.startServer(false);
            } catch (Exception ex) {
                System.out.println(ExceptionUtils.getFullStackTrace(ex));
            }

            System.out.println("Running!");

        } else {
            System.out.println("do client");
        }


    }


}

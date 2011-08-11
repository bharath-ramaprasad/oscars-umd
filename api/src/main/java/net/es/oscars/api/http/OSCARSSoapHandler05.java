package net.es.oscars.api.http;

import java.util.logging.Logger;

import net.es.oscars.api.soap.gen.v05.*;
import org.oasis_open.docs.wsn.b_2.Notify;
import net.es.oscars.utils.svc.ServiceNames;

@javax.jws.WebService(
        serviceName = ServiceNames.SVC_API,
        portName = "OSCARS",
        targetNamespace = "http://oscars.es.net/OSCARS",
        endpointInterface = "net.es.oscars.api.soap.gen.v05.OSCARS")
@javax.xml.ws.BindingType(value = "http://www.w3.org/2003/05/soap/bindings/HTTP/")
public class OSCARSSoapHandler05 implements OSCARS {

    // Implements requests


    private static final Logger LOG = Logger.getLogger(OSCARSSoapHandler05.class.getName());

    /* (non-Javadoc)
     * @see net.es.oscars.api.soap.gen.v05.OSCARS#queryReservation(net.es.oscars.api.soap.gen.v05.GlobalReservationId  queryReservation )*
     */
    public net.es.oscars.api.soap.gen.v05.ResDetails queryReservation(GlobalReservationId queryReservation) throws BSSFaultMessage , AAAFaultMessage    { 
        LOG.info("Executing operation queryReservation");
        System.out.println(queryReservation);
        try {
            net.es.oscars.api.soap.gen.v05.ResDetails _return = null;
            return _return;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new BSSFaultMessage("BSSFaultMessage...");
        //throw new AAAFaultMessage("AAAFaultMessage...");
    }

    /* (non-Javadoc)
     * @see net.es.oscars.api.soap.gen.v05.OSCARS#createPath(net.es.oscars.api.soap.gen.v05.CreatePathContent  createPath )*
     */
    public net.es.oscars.api.soap.gen.v05.CreatePathResponseContent createPath(CreatePathContent createPath) throws BSSFaultMessage , AAAFaultMessage    { 
        LOG.info("Executing operation createPath");
        System.out.println(createPath);
        try {
            net.es.oscars.api.soap.gen.v05.CreatePathResponseContent _return = null;
            return _return;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new BSSFaultMessage("BSSFaultMessage...");
        //throw new AAAFaultMessage("AAAFaultMessage...");
    }

    /* (non-Javadoc)
     * @see net.es.oscars.api.soap.gen.v05.OSCARS#createReservation(net.es.oscars.api.soap.gen.v05.ResCreateContent  createReservation )*
     */
    public net.es.oscars.api.soap.gen.v05.CreateReply createReservation(ResCreateContent createReservation) throws BSSFaultMessage , AAAFaultMessage    { 
        LOG.info("Executing operation createReservation");
        System.out.println(createReservation);
        try {
            net.es.oscars.api.soap.gen.v05.CreateReply _return = null;
            return _return;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new BSSFaultMessage("BSSFaultMessage...");
        //throw new AAAFaultMessage("AAAFaultMessage...");
    }

    /* (non-Javadoc)
     * @see net.es.oscars.api.soap.gen.v05.OSCARS#modifyReservation(net.es.oscars.api.soap.gen.v05.ModifyResContent  modifyReservation )*
     */
    public net.es.oscars.api.soap.gen.v05.ModifyResReply modifyReservation(ModifyResContent modifyReservation) throws BSSFaultMessage , AAAFaultMessage    { 
        LOG.info("Executing operation modifyReservation");
        System.out.println(modifyReservation);
        try {
            net.es.oscars.api.soap.gen.v05.ModifyResReply _return = null;
            return _return;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new BSSFaultMessage("BSSFaultMessage...");
        //throw new AAAFaultMessage("AAAFaultMessage...");
    }

    /* (non-Javadoc)
     * @see net.es.oscars.api.soap.gen.v05.OSCARS#notify(net.es.oscars.api.soap.gen.v05.Notify  notify )*
     */
    public void notify(Notify notify) { 
        LOG.info("Executing operation notify");
        System.out.println(notify);
        try {
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see net.es.oscars.api.soap.gen.v05.OSCARS#listReservations(net.es.oscars.api.soap.gen.v05.ListRequest  listReservations )*
     */
    public net.es.oscars.api.soap.gen.v05.ListReply listReservations(ListRequest listReservations) throws BSSFaultMessage , AAAFaultMessage    { 
        LOG.info("Executing operation listReservations");
        System.out.println(listReservations);
        try {
            net.es.oscars.api.soap.gen.v05.ListReply _return = null;
            return _return;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new BSSFaultMessage("BSSFaultMessage...");
        //throw new AAAFaultMessage("AAAFaultMessage...");
    }

    /* (non-Javadoc)
     * @see net.es.oscars.api.soap.gen.v05.OSCARS#teardownPath(net.es.oscars.api.soap.gen.v05.TeardownPathContent  teardownPath )*
     */
    public net.es.oscars.api.soap.gen.v05.TeardownPathResponseContent teardownPath(TeardownPathContent teardownPath) throws BSSFaultMessage , AAAFaultMessage    { 
        LOG.info("Executing operation teardownPath");
        System.out.println(teardownPath);
        try {
            net.es.oscars.api.soap.gen.v05.TeardownPathResponseContent _return = null;
            return _return;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new BSSFaultMessage("BSSFaultMessage...");
        //throw new AAAFaultMessage("AAAFaultMessage...");
    }

    /* (non-Javadoc)
     * @see net.es.oscars.api.soap.gen.v05.OSCARS#getNetworkTopology(net.es.oscars.api.soap.gen.v05.GetTopologyContent  getNetworkTopology )*
     */
    public net.es.oscars.api.soap.gen.v05.GetTopologyResponseContent getNetworkTopology(GetTopologyContent getNetworkTopology) throws BSSFaultMessage , AAAFaultMessage    { 
        LOG.info("Executing operation getNetworkTopology");
        System.out.println(getNetworkTopology);
        try {
            net.es.oscars.api.soap.gen.v05.GetTopologyResponseContent _return = null;
            return _return;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new BSSFaultMessage("BSSFaultMessage...");
        //throw new AAAFaultMessage("AAAFaultMessage...");
    }

    /* (non-Javadoc)
     * @see net.es.oscars.api.soap.gen.v05.OSCARS#forward(net.es.oscars.api.soap.gen.v05.Forward  forward )*
     */
    public net.es.oscars.api.soap.gen.v05.ForwardReply forward(Forward forward) throws BSSFaultMessage , AAAFaultMessage    { 
        LOG.info("Executing operation forward");
        System.out.println(forward);
        try {
            net.es.oscars.api.soap.gen.v05.ForwardReply _return = null;
            return _return;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new BSSFaultMessage("BSSFaultMessage...");
        //throw new AAAFaultMessage("AAAFaultMessage...");
    }

    /* (non-Javadoc)
     * @see net.es.oscars.api.soap.gen.v05.OSCARS#refreshPath(net.es.oscars.api.soap.gen.v05.RefreshPathContent  refreshPath )*
     */
    public net.es.oscars.api.soap.gen.v05.RefreshPathResponseContent refreshPath(RefreshPathContent refreshPath) throws BSSFaultMessage , AAAFaultMessage    { 
        LOG.info("Executing operation refreshPath");
        System.out.println(refreshPath);
        try {
            net.es.oscars.api.soap.gen.v05.RefreshPathResponseContent _return = null;
            return _return;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new BSSFaultMessage("BSSFaultMessage...");
        //throw new AAAFaultMessage("AAAFaultMessage...");
    }

    /* (non-Javadoc)
     * @see net.es.oscars.api.soap.gen.v05.OSCARS#cancelReservation(net.es.oscars.api.soap.gen.v05.GlobalReservationId  cancelReservation )*
     */
    public java.lang.String cancelReservation(GlobalReservationId cancelReservation) throws BSSFaultMessage , AAAFaultMessage    { 
        LOG.info("Executing operation cancelReservation");
        System.out.println(cancelReservation);
        try {
            java.lang.String _return = "";
            return _return;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new BSSFaultMessage("BSSFaultMessage...");
        //throw new AAAFaultMessage("AAAFaultMessage...");
    }

    
}

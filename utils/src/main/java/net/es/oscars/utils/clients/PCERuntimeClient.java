package net.es.oscars.utils.clients;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.lang.Exception;

import net.es.oscars.common.soap.gen.MessagePropertiesType;
import net.es.oscars.logging.ModuleName;
import net.es.oscars.logging.OSCARSNetLoggerize;
import net.es.oscars.pce.soap.gen.v06.PCEDataContent;
import net.es.oscars.pce.soap.gen.v06.PCEError;
import net.es.oscars.pce.soap.gen.v06.PCEPortType;
import net.es.oscars.pce.soap.gen.v06.PCEReplyContent;
import net.es.oscars.pce.soap.gen.v06.PCEService;
import net.es.oscars.utils.soap.OSCARSService;
import net.es.oscars.utils.soap.OSCARSServiceException;
import net.es.oscars.utils.soap.OSCARSSoapService;
import net.es.oscars.utils.svc.ServiceNames;
import net.es.oscars.utils.config.ConfigDefaults;

/**
 * PCERuntimeClient a singleton class used by the PCEs to send reply messages
 * 
 * @author lomax
 *
 */
@OSCARSNetLoggerize(moduleName = ModuleName.PCERUNTIME)
@OSCARSService (
        implementor = "net.es.oscars.pce.soap.gen.v06.PCEService",
        namespace   = "http://oscars.es.net/OSCARS/PCE/20090922",
        serviceName = ServiceNames.SVC_PCERUNTIME,
        config      = ConfigDefaults.CONFIG
)
public class PCERuntimeClient extends OSCARSSoapService<PCEService,PCEPortType> {
   
    private static URL wsdlURL = null;
    private static HashMap<String, PCERuntimeClient> clients = new HashMap<String, PCERuntimeClient>();
    
    private PCERuntimeClient (URL host) throws OSCARSServiceException {
        super (host, PCERuntimeClient.wsdlURL, PCEPortType.class);
    }
 
    public static PCERuntimeClient getPCERuntimeClient (String proxyEndpoint)
                                        throws MalformedURLException, OSCARSServiceException {
        
        if (PCERuntimeClient.wsdlURL == null) {
            PCERuntimeClient.wsdlURL = new URL(proxyEndpoint + "?wsdl");
        }
        
        PCERuntimeClient client = null;
        URL proxyEndpointURL = new URL (proxyEndpoint);
        synchronized (PCERuntimeClient.clients) {
            client = PCERuntimeClient.clients.get(proxyEndpoint);
            if (client == null) {
                // Need to create a new instance.
                client = new PCERuntimeClient (proxyEndpointURL);
                PCERuntimeClient.clients.put(proxyEndpoint, client);
            }
        }
        return client;
    }
 
    /**
     *  Sends a pceReply at the completion of a successful PCECreate operation
     * @param msgProps
     * @param gri
     * @param transactionId
     * @param pceName
     * @param pceData
     * @throws OSCARSServiceException
     */
    public void sendPceReply (MessagePropertiesType msgProps,
                              String gri, 
                              String transactionId, 
                              String pceName, 
                              PCEDataContent pceData)
            throws OSCARSServiceException {
        
        if (pceData == null) {
            // No PCE Data. This should not happen.
            throw new OSCARSServiceException ("no PCE data");
        }
        // Build the PCECreate reply
        PCEReplyContent replyContent = new PCEReplyContent();
        replyContent.setMessageProperties(msgProps);
        replyContent.setGlobalReservationId(gri);
        replyContent.setId(transactionId);
        replyContent.setPceName(pceName);
        replyContent.setPceData(pceData);
        Object[] req = new Object[] {replyContent};
        this.invoke("PCEReply", req);
    }

    /**
     *   Sends a pceReply error reply when PCECreate operation fails
     * @param gri
     * @param transactionId
     * @param pceName
     * @param pceData
     * @param ex
     * @throws OSCARSServiceException
     */
    public void sendPceReply (MessagePropertiesType msgProps,
                              String gri, 
                              String transactionId, 
                              String pceName, 
                              PCEDataContent pceData, 
                             Exception ex) 
            throws OSCARSServiceException {

        // Build the PCECreate reply
        PCEReplyContent replyContent = new PCEReplyContent();
        replyContent.setMessageProperties(msgProps);
        replyContent.setGlobalReservationId(gri);
        replyContent.setId(transactionId);
        replyContent.setPceName(pceName);
        if (ex != null || pceData == null) {
            PCEError pceError = new PCEError();
            if (ex != null) {
                if (ex.getMessage() != null) {
                    pceError.setMsg(ex.getMessage());
                } else {
                    pceError.setMsg(ex.toString());
                }
            } else {
                pceError.setMsg("PCEData null, but no exception thrown");
            }
            pceError.setDetails(" on reservation " + gri + " in " + pceName);
            replyContent.setPceError(pceError);
            replyContent.setPceData(null);
        } else {
            replyContent.setPceError(null);
            replyContent.setPceData(pceData);
        }
        Object[] req = new Object[] {replyContent};
        this.invoke("PCEReply", req);
    }
    
    /**
     *  Sends a pceReply at the completion of a successful PCECommit operation
     * @param msgProps
     * @param gri
     * @param transactionId
     * @param pceName
     * @param pceData
     * @throws OSCARSServiceException
     */
    public void sendCommitReply (MessagePropertiesType msgProps,
                                 String gri, 
                                 String transactionId,
                                 String pceName, 
                                 PCEDataContent pceData) 
            throws OSCARSServiceException {

        // Build the PCECommit reply
        PCEReplyContent replyContent = new PCEReplyContent();
        replyContent.setMessageProperties(msgProps);
        replyContent.setGlobalReservationId(gri);
        replyContent.setId(transactionId);
        replyContent.setPceName(pceName);
        replyContent.setPceData(pceData);
        Object[] req = new Object[] {replyContent};
        this.invoke("PCECommitReply", req);
    }
    
    /**
     *   Sends a pceReply error reply when PCECommit operation fails
     * @param gri
     * @param transactionId
     * @param pceName
     * @param pceData
     * @param ex
     * @throws OSCARSServiceException
     */
    public void sendCommitReply (MessagePropertiesType msgProps,
                                 String gri, 
                                 String transactionId,
                                 String pceName, 
                                 PCEDataContent pceData, 
                                 Exception ex )
                throws OSCARSServiceException {
        this.sendGenericReply("PCECommitReply", msgProps, gri, transactionId, pceName, pceData, ex);
    }
    
    /**
     *  Sends a pceReply at the completion of a  PCEModify operation
     * @param msgProps
     * @param gri
     * @param transactionId
     * @param pceName
     * @param pceData
     * @throws OSCARSServiceException
     */
    public void sendModifyReply(MessagePropertiesType msgProps,
                                String gri, 
                                String transactionId,
                                String pceName, 
                                PCEDataContent pceData, 
                                Exception ex )
                throws OSCARSServiceException {
        this.sendGenericReply("PCEModifyReply", msgProps, gri, transactionId, pceName, pceData, ex);
    }
    /**
     *  Sends a pceReply at the completion of a PCECancel operation
     * @param msgProps
     * @param gri
     * @param transactionId
     * @param pceName
     * @param pceData
     * @throws OSCARSServiceException
     */
    public void sendCancelReply (MessagePropertiesType msgProps,
                                 String gri, 
                                 String transactionId,
                                 String pceName, 
                                 PCEDataContent pceData, 
                                 Exception ex )
                throws OSCARSServiceException {
        sendGenericReply("PCECancelReply", msgProps, gri, transactionId, pceName, pceData, ex);
    }
    
    private void sendGenericReply (String msgType,
                                   MessagePropertiesType msgProps,
                                   String gri,
                                   String transactionId,
                                   String pceName,
                                   PCEDataContent pceData,
                                   Exception ex ) throws OSCARSServiceException {
        
        if (pceData == null) {
            // No PCE Data. This should not happen.
            throw new OSCARSServiceException ("no PCE data");
        }
        // Build the PCECommit reply
        PCEReplyContent replyContent = new PCEReplyContent();
        replyContent.setMessageProperties(msgProps);
        replyContent.setGlobalReservationId(gri);
        replyContent.setId(transactionId);
        replyContent.setPceName(pceName);
        if (ex != null || pceData == null) {
            PCEError pceError = new PCEError();
            if (ex != null) {
                if (ex.getMessage() != null) {
                    pceError.setMsg(ex.getMessage());
                } else {
                    pceError.setMsg(ex.toString());
                }
            } else {
                pceError.setMsg("PCEData null, but no exception thrown");
            }
            pceError.setDetails(" on reservation " + gri + " in " + pceName);
            replyContent.setPceError(pceError);
        } else {
            replyContent.setPceData(pceData);
        }
        Object[] req = new Object[] {replyContent};
        this.invoke(msgType, req);
    }
}



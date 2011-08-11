package net.es.oscars.pce;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.ogf.schema.network.topology.ctrlplane.CtrlPlaneDomainContent;

import net.es.oscars.common.soap.gen.MessagePropertiesType;
import net.es.oscars.logging.ErrSev;
import net.es.oscars.logging.OSCARSNetLogger;
import net.es.oscars.logging.OSCARSNetLoggerize;
import net.es.oscars.pce.PCEMessage;
import net.es.oscars.pce.PCEProtocolHandler;
import net.es.oscars.pce.soap.gen.v06.AggregatorCreateContent;
import net.es.oscars.pce.soap.gen.v06.AggregatorModifyContent;
import net.es.oscars.pce.soap.gen.v06.PCECancelContent;
import net.es.oscars.pce.soap.gen.v06.PCECancelReplyContent;
import net.es.oscars.pce.soap.gen.v06.PCECommitContent;
import net.es.oscars.pce.soap.gen.v06.PCECommitReplyContent;
import net.es.oscars.pce.soap.gen.v06.PCECreateContent;
import net.es.oscars.pce.soap.gen.v06.PCEModifyContent;
import net.es.oscars.pce.soap.gen.v06.PCEModifyReplyContent;
import net.es.oscars.pce.soap.gen.v06.PCEReplyContent;
import net.es.oscars.pce.soap.gen.v06.PCEDataContent;
import net.es.oscars.pce.soap.gen.v06.PCEError;


/**
 * A PCEProtocol handler that can be used or extended by PCE servers. Handles
 * all the  messages. Note: not used by the PCERuntime.
 * 
 * @author lomax
 *
 */
@javax.xml.ws.BindingType(value ="http://www.w3.org/2003/05/soap/bindings/HTTP/")
public class SimplePCEProtocolHandler extends PCEProtocolHandler {

    private Logger log = Logger.getLogger(this.getClass());
    private String netLogModName = null;
    
    public SimplePCEProtocolHandler(){
        if(this.getClass().isAnnotationPresent(OSCARSNetLoggerize.class)){
            OSCARSNetLoggerize anno = this.getClass().getAnnotation(OSCARSNetLoggerize.class);
            this.netLogModName = anno.moduleName();
        }
    }
    
    /**
     * handles the pceCreate message. Copies the input parameters into
     * a pceMessage, creates and queues a pceJob.
     */
    public void pceCreate(PCECreateContent pceCreate)   { 
        OSCARSNetLogger netLogger = null;
        String event = "pceCreate";
        MessagePropertiesType msgProps = pceCreate.getMessageProperties();
        String globalReservationId = pceCreate.getGlobalReservationId();
        String pceName = pceCreate.getPceName();
        String callBackEndpoint = pceCreate.getCallBackEndpoint();
        PCEDataContent pceData = pceCreate.getPceData();
        if(this.netLogModName != null){
            netLogger = OSCARSNetLogger.getTlogger();
            netLogger.init(netLogModName,msgProps.getGlobalTransactionId());
            netLogger.setGRI(globalReservationId);
            this.log.info(netLogger.start(event));
        }
        try {
            // Create a query object
            PCEMessage pceCreateMessage = new PCEMessage (msgProps,
                                                   globalReservationId,
                                                   pceCreate.getId(),
                                                   pceName,
                                                   callBackEndpoint,
                                                   "pceCreate",
                                                   pceData);
            // Add the query to the list
            SimplePCEServer.getInstance().addPceJob(pceCreateMessage, SimplePCEJob.CREATE_TYPE);
            if(netLogModName != null){
                this.log.info(netLogger.end(event));
            }
         } catch (Exception ex) {
            if(netLogModName != null){
                this.log.error(netLogger.error(event, ErrSev.MAJOR, ex.getMessage()));
            }
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /**
     * handles pceCommit message
     */
    public void pceCommit(PCECommitContent pceCommit)  { 
        OSCARSNetLogger netLogger = null;
        String event = "pceCommit";
        MessagePropertiesType msgProps = pceCommit.getMessageProperties();
        String globalReservationId = pceCommit.getGlobalReservationId();
        String pceName = pceCommit.getPceName();
        String callBackEndpoint = pceCommit.getCallBackEndpoint();
        PCEDataContent pceData = pceCommit.getPceData();
        if(this.netLogModName != null){
            netLogger = OSCARSNetLogger.getTlogger();
            netLogger.init(netLogModName,msgProps.getGlobalTransactionId());
            netLogger.setGRI(globalReservationId);
            this.log.info(netLogger.start(event));
        }
        
        try {
            PCEMessage pceCommitMessage = new PCEMessage (msgProps,
                                                   globalReservationId,
                                                   pceCommit.getId(),
                                                   pceName,
                                                   callBackEndpoint,
                                                   "pceCommit",
                                                   pceData);
            
            // Add the query to the list
            SimplePCEServer.getInstance().addPceJob(pceCommitMessage, SimplePCEJob.COMMIT_TYPE);
            
            if(netLogModName != null){
                this.log.info(netLogger.end(event));
            }
        } catch (Exception ex) {
            if(netLogModName != null){
                this.log.error(netLogger.error(event, ErrSev.MAJOR, ex.getMessage()));
            }
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
 
    public void pceModify(PCEModifyContent pceModify) { 
        OSCARSNetLogger netLogger = null;
        String event = "pceModify";
        MessagePropertiesType msgProps = pceModify.getMessageProperties();
        String globalReservationId = pceModify.getGlobalReservationId();
        String pceName = pceModify.getPceName();
        String callBackEndpoint = pceModify.getCallBackEndpoint();
        PCEDataContent pceData = pceModify.getPceData();
        if(this.netLogModName != null){
            netLogger = OSCARSNetLogger.getTlogger();
            netLogger.init(netLogModName,msgProps.getGlobalTransactionId());
            netLogger.setGRI(globalReservationId);
            this.log.info(netLogger.start(event));
        }
        try {
            PCEMessage pceModifyMessage = new PCEMessage (msgProps,
                                                          globalReservationId,
                                                          pceModify.getId(),
                                                          pceName,
                                                          callBackEndpoint,
                                                          "pceModify",
                                                          pceData);
            
            // Add the query to the list
            SimplePCEServer.getInstance().addPceJob(pceModifyMessage, SimplePCEJob.MODIFY_TYPE);
            
            if(netLogModName != null){
                this.log.info(netLogger.end(event));
            }
        } catch (Exception ex) {
            if(netLogModName != null){
                this.log.error(netLogger.error(event, ErrSev.MAJOR, ex.getMessage()));
            }
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
    
    public void pceCancel(PCECancelContent pceCancel) {
        OSCARSNetLogger netLogger = null;
        String event = "pceCancel";
        MessagePropertiesType msgProps = pceCancel.getMessageProperties();
        String globalReservationId = pceCancel.getGlobalReservationId();
        String pceName = pceCancel.getPceName();
        String callBackEndpoint = pceCancel.getCallBackEndpoint();
        PCEDataContent pceData = pceCancel.getPceData();
        if(this.netLogModName != null){
            netLogger = OSCARSNetLogger.getTlogger();
            netLogger.init(netLogModName,msgProps.getGlobalTransactionId());
            netLogger.setGRI(globalReservationId);
            this.log.info(netLogger.start(event));
        }
        try {
            PCEMessage pceModifyMessage = new PCEMessage (msgProps,
                                                          globalReservationId,
                                                          pceCancel.getId(),
                                                          pceName,
                                                          callBackEndpoint,
                                                          "pceCancel",
                                                          pceData);
            
            // Add the query to the list
            SimplePCEServer.getInstance().addPceJob(pceModifyMessage, SimplePCEJob.CANCEL_TYPE);
            if(netLogModName != null){
                this.log.info(netLogger.end(event));
            }
        } catch (Exception ex) {
            if(netLogModName != null){
                this.log.error(netLogger.error(event, ErrSev.MAJOR, ex.getMessage()));
            }
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
}
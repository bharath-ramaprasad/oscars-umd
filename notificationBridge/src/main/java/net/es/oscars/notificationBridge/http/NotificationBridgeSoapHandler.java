package net.es.oscars.notificationBridge.http;

import java.util.UUID;

import org.apache.log4j.Logger;

import net.es.oscars.api.soap.gen.v06.EventContent;
import net.es.oscars.common.soap.gen.MessagePropertiesType;
import net.es.oscars.logging.ModuleName;
import net.es.oscars.logging.OSCARSNetLogger;
import net.es.oscars.notificationBridge.soap.gen.NotificationBridgePortType;
import net.es.oscars.utils.svc.ServiceNames;

@javax.jws.WebService(
        serviceName = ServiceNames.SVC_NOTIFY,
        portName = "NotificationBridgePort",
        targetNamespace = "http://oscars.es.net/OSCARS/notificationBridge",
        endpointInterface = "net.es.oscars.notificationBridge.soap.gen.NotificationBridgePortType")
@javax.xml.ws.BindingType(value = "http://www.w3.org/2003/05/soap/bindings/HTTP/")
public class NotificationBridgeSoapHandler implements NotificationBridgePortType{
    Logger log = Logger.getLogger(NotificationBridgeSoapHandler.class);
    public void notify(EventContent event) {
        OSCARSNetLogger netLogger = this.initNetLogger(event.getMessageProperties());
        this.log.info(netLogger.start("notify"));
        this.log.info(netLogger.end("notify"));
    }

    private OSCARSNetLogger initNetLogger(MessagePropertiesType msgProps) {
        String guid = null;
        if(msgProps != null && msgProps.getGlobalTransactionId() != null){
            guid = msgProps.getGlobalTransactionId();
        }else{
            guid = UUID.randomUUID().toString();
        }
        OSCARSNetLogger netLogger = new OSCARSNetLogger(ModuleName.NOTIFY, guid);
        OSCARSNetLogger.setTlogger(netLogger);
        
        return netLogger;
    }
}

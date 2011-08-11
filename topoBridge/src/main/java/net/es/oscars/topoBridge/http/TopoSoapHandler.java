package net.es.oscars.topoBridge.http;

import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.ogf.schema.network.topology.ctrlplane.CtrlPlaneDomainContent;
import org.ogf.schema.network.topology.ctrlplane.CtrlPlaneTopologyContent;

import net.es.oscars.common.soap.gen.OSCARSFaultMessage;
import net.es.oscars.logging.ErrSev;
import net.es.oscars.logging.ModuleName;
import net.es.oscars.logging.OSCARSNetLogger;
import net.es.oscars.logging.OSCARSNetLoggerize;
import net.es.oscars.topoBridge.common.TopologyCache;
import net.es.oscars.topoBridge.ps.PSTopoConverter;
import net.es.oscars.topoBridge.soap.gen.GetTopologyRequestType;
import net.es.oscars.topoBridge.soap.gen.GetTopologyResponseType;
import net.es.oscars.topoBridge.soap.gen.TopoBridgePortType;
import net.es.oscars.utils.soap.OSCARSServiceException;
import net.es.oscars.utils.svc.ServiceNames;

@OSCARSNetLoggerize(moduleName = ModuleName.TOPO)
@javax.jws.WebService(
                      serviceName = ServiceNames.SVC_TOPO,
                      portName = "TopoBridgePort",
                      targetNamespace = "http://oscars.es.net/OSCARS/topoBridge",
                      endpointInterface = "net.es.oscars.topoBridge.soap.gen.TopoBridgePortType")
@javax.xml.ws.BindingType(value = "http://www.w3.org/2003/05/soap/bindings/HTTP/")

public class TopoSoapHandler implements TopoBridgePortType {

    private static final Logger log = Logger.getLogger(TopoSoapHandler.class.getName());

    public GetTopologyResponseType getTopology(
            GetTopologyRequestType getTopologyRequest) throws OSCARSFaultMessage {
        OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
        String event = "getTopology";
        String transId = getTopologyRequest.getMessageProperties().getGlobalTransactionId();
        netLogger.init(ModuleName.TOPO, transId);
        log.info(netLogger.start(event));
        
        String netLoggerErrMsg = "[";
        try {
            GetTopologyResponseType response = null;
            response = new GetTopologyResponseType();

            TopologyCache tc = TopologyCache.getInstance();
            List<String> domainIds = getTopologyRequest.getDomainId();
            for (String domainId : domainIds) {
                //catch exception so no single domain fails the entire request
                try{
                    Document doc = tc.getDomain(domainId, netLogger);
                    CtrlPlaneDomainContent domainContent = PSTopoConverter.convert(doc, tc.getNsUri());
                    CtrlPlaneTopologyContent topoContent = new CtrlPlaneTopologyContent();
                    topoContent.getDomain().add(domainContent);
                    response.getTopology().add(topoContent);
                }catch(Exception e){
                    if(netLoggerErrMsg.length() > 1){
                        netLoggerErrMsg += ",";
                    }
                    netLoggerErrMsg += "\"" + e.getMessage() + "\"";
                }
            }
            netLoggerErrMsg += "]";
            
            if(response.getTopology().isEmpty()){
                log.error(netLogger.end(event, ErrSev.MAJOR, netLoggerErrMsg));
                throw new OSCARSFaultMessage("Unable to find a topology for " +
                        "domains " + OSCARSNetLogger.serializeList(domainIds));
            }
            
            log.info(netLogger.end(event));
            return response;
        } catch (OSCARSServiceException e){
            log.error(netLogger.error(event, ErrSev.MAJOR, e.getMessage()));
            throw new OSCARSFaultMessage(e.getMessage());
        } catch (Exception ex) {
            log.error(netLogger.error(event, ErrSev.MAJOR, ex.toString()));
            throw new RuntimeException(ex);
        }
    }
}

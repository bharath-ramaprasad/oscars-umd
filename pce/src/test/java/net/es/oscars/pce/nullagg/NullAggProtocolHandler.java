package net.es.oscars.pce.nullagg;

import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import net.es.oscars.common.soap.gen.MessagePropertiesType;
import net.es.oscars.logging.OSCARSNetLogger;
import net.es.oscars.pce.AggMessage;
import net.es.oscars.pce.PCEProtocolHandler;
import net.es.oscars.pce.soap.gen.v06.AggregatorCreateContent;
import net.es.oscars.pce.soap.gen.v06.AggregatorModifyContent;
import net.es.oscars.pce.soap.gen.v06.AggregatorCommitContent;
import net.es.oscars.pce.soap.gen.v06.AggregatorCancelContent;
import net.es.oscars.pce.soap.gen.v06.PCECancelContent;
import net.es.oscars.pce.soap.gen.v06.PCECommitContent;
import net.es.oscars.pce.soap.gen.v06.PCEDataContent;
import net.es.oscars.pce.soap.gen.v06.TagDataContent;

@javax.xml.ws.BindingType(value ="http://www.w3.org/2003/05/soap/bindings/HTTP/")
public class NullAggProtocolHandler extends PCEProtocolHandler {

    private static final Logger LOG = Logger.getLogger(PCEProtocolHandler.class.getName());
    private static String moduleName= "nullAggProtocolHandler";

    /* (non-Javadoc)
     * @see net.es.oscars.pce.soap.gen.v06.PCEPortType#aggregatorCreate(java.lang.String  globalReservationId ,)java.lang.String  pceName ,)java.lang.String  callBackEndpoint ,)java.util.List<net.es.oscars.pce.soap.gen.v06.TagDataContent>  pceData )*
     */
    public void aggregatorCreate(AggregatorCreateContent aggCreate) { 
        OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
        MessagePropertiesType msgProps = aggCreate.getMessageProperties();
        String gri = aggCreate.getGlobalReservationId();
        netLogger.init(moduleName,msgProps.getGlobalTransactionId());
        netLogger.setGRI(gri);
        String pceName = aggCreate.getPceName();
        String callBackEndpoint = aggCreate.getCallBackEndpoint();
        List<TagDataContent> pceData = aggCreate.getPceData();
        try {
            LOG.info (netLogger.start("aggregatorCreate"," pceName= " + pceName + " callback URL= " + callBackEndpoint));
            // Create a query object
            AggMessage pceQuery = new AggMessage (msgProps,
                                                  gri,
                                                  aggCreate.getId(),
                                                  pceName,
                                                  callBackEndpoint,
                                                  "pceCreate",
                                                  pceData);
            // Add the query to the list
            NullAgg.getInstance().addPceQuery(pceQuery);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    public void pceCommit(PCECommitContent pceCommit) { 
        OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
        MessagePropertiesType msgProps = pceCommit.getMessageProperties();
        String event = "pceCommit";
        netLogger.init(moduleName,msgProps.getGlobalTransactionId());
        String gri = pceCommit.getGlobalReservationId();
        netLogger.setGRI(gri);
        String pceName = pceCommit.getPceName();
        String callBackEndpoint = pceCommit.getCallBackEndpoint();
        PCEDataContent pceData = pceCommit.getPceData();
        try {
            LOG.info (netLogger.start(event, " pceName= " + pceName + " callback URL= " + callBackEndpoint));
            // Create a query object
            List<TagDataContent> pceTagData = new ArrayList <TagDataContent>();
            TagDataContent tagData = new TagDataContent ();
            tagData.setConstraints(pceData);
            tagData.setTag("NoTag"); // there is no need for a tag for the commit phase.
            pceTagData.add(tagData);
            AggMessage pceQuery = new AggMessage (msgProps,
                                                  gri,
                                                  pceCommit.getId(),
                                                  pceName,
                                                  callBackEndpoint,
                                                  "pceCommit",
                                                  pceTagData);

            // Add the query to the list
            NullAgg.getInstance().addPceQuery(pceQuery);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
    public void pceCancel(PCECancelContent pceCancel) {
        OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
        MessagePropertiesType msgProps = pceCancel.getMessageProperties();
        String event = "pceCommit";
        netLogger.init(moduleName,msgProps.getGlobalTransactionId());
        String gri = pceCancel.getGlobalReservationId();
        netLogger.setGRI(gri);
        String pceName = pceCancel.getPceName();
        String callBackEndpoint = pceCancel.getCallBackEndpoint();
        PCEDataContent pceData = pceCancel.getPceData();
        try {
            LOG.info (netLogger.start(event, " pceName= " + pceName + " callback URL= " + callBackEndpoint));
            // Create a query object
            List<TagDataContent> pceTagData = new ArrayList <TagDataContent>();
            TagDataContent tagData = new TagDataContent ();
            tagData.setConstraints(pceData);
            tagData.setTag("NoTag"); // there is no need for a tag for cancel.
            pceTagData.add(tagData);
            AggMessage pceQuery = new AggMessage (msgProps,
                                                  gri,
                                                  pceCancel.getId(),
                                                  pceName,
                                                  callBackEndpoint,
                                                  "pceCancel",
                                                  pceTagData);
//          Add the query to the list
            NullAgg.getInstance().addPceQuery(pceQuery);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }                   
    }
    public void aggregatorModify(AggregatorModifyContent aggModify) { 
        OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
        MessagePropertiesType msgProps = aggModify.getMessageProperties();
        String event = "aggregatorModify";
        netLogger.init(moduleName,msgProps.getGlobalTransactionId());
        String gri = aggModify.getGlobalReservationId();
        netLogger.setGRI(gri);
        String pceName = aggModify.getPceName();
        String callBackEndpoint = aggModify.getCallBackEndpoint();
        List<TagDataContent> pceData = aggModify.getPceData();
        try {
            LOG.info (netLogger.start(event, " pceName= " + pceName + " callback URL= " + callBackEndpoint));
//          Create a query object
            AggMessage pceQuery = new AggMessage (msgProps,
                                                  gri,
                                                  aggModify.getId(),
                                                  pceName,
                                                  callBackEndpoint,
                                                  "pceModify",
                                                  pceData);
//          Add the query to the list
            NullAgg.getInstance().addPceQuery(pceQuery);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }                   
    }
    public void aggregatorCommit(AggregatorCommitContent aggCommit) { 
        OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
        MessagePropertiesType msgProps = aggCommit.getMessageProperties();
        String event = "aggregatorCommi";
        netLogger.init(moduleName,msgProps.getGlobalTransactionId());
        String gri = aggCommit.getGlobalReservationId();
        netLogger.setGRI(gri);
        String pceName = aggCommit.getPceName();
        String callBackEndpoint = aggCommit.getCallBackEndpoint();
        List<TagDataContent> pceData = aggCommit.getPceData();
        try {
            LOG.info (netLogger.start(event, " pceName= " + pceName + " callback URL= " + callBackEndpoint));
//          Create a query object
            AggMessage pceQuery = new AggMessage (msgProps,
                                                  gri,
                                                  aggCommit.getId(),
                                                  pceName,
                                                  callBackEndpoint,
                                                  "pceCommit",
                                                  pceData);
//          Add the query to the list
            NullAgg.getInstance().addPceQuery(pceQuery);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }                   
    }
    public void aggregatorCancel(AggregatorCancelContent aggCancel) { 
        OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
        MessagePropertiesType msgProps = aggCancel.getMessageProperties();
        String event = "aggregatorCancel";
        netLogger.init(moduleName,msgProps.getGlobalTransactionId());
        String gri = aggCancel.getGlobalReservationId();
        netLogger.setGRI(gri);
        String pceName = aggCancel.getPceName();
        String callBackEndpoint = aggCancel.getCallBackEndpoint();
        List<TagDataContent> pceData = aggCancel.getPceData();
        try {
            LOG.info (netLogger.start(event, " pceName= " + pceName + " callback URL= " + callBackEndpoint));
//          Create a query object
            AggMessage pceQuery = new AggMessage (msgProps,
                                                  gri,
                                                  aggCancel.getId(),
                                                  pceName,
                                                  callBackEndpoint,
                                                  "pceCancel",
                                                  pceData);
//          Add the query to the list
            NullAgg.getInstance().addPceQuery(pceQuery);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }                   
    }
}

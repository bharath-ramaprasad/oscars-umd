package net.es.oscars.wsnbroker.client;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.oasis_open.docs.wsn.b_2.MessageType;
import org.oasis_open.docs.wsn.b_2.NotificationMessageHolderType;
import org.oasis_open.docs.wsn.b_2.Notify;
import org.oasis_open.docs.wsn.b_2.TopicExpressionType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import net.es.oscars.api.soap.gen.v06.EventContent;
import net.es.oscars.api.soap.gen.v06.ObjectFactory;
import net.es.oscars.api.soap.gen.v06.ResDetails;
import net.es.oscars.logging.ErrSev;
import net.es.oscars.logging.OSCARSNetLogger;
import net.es.oscars.utils.clients.WSNBrokerClient;
import net.es.oscars.utils.config.ConfigException;
import net.es.oscars.utils.config.ContextConfig;
import net.es.oscars.utils.soap.OSCARSServiceException;
import net.es.oscars.utils.svc.ServiceNames;

/**
 * Sends notification of an OSCARS event to the notification service.
 * It should be used by the coordinator to trigger events as they happen.
 * It takes care of determining what topic an event belongs and allows you to 
 * provide even details via various "send" methods. 
 * 
 * Determining the topic is where most of the complexity of this class lives. The 
 * topic is loaded from a set of XML files in the utils directory that follow the 
 * WS-Topic specification. The loaded topics define an XPath expression that 
 * evaluates to true if an event belongs to that topic.
 */
public class NotifySender {
    private Logger log = Logger.getLogger(NotifySender.class);
    private WSNBrokerClient client;
    private String url;
    private static HashMap<String, String> topics = null;
    private static Map<String, String> namespaceMap = null;
    
    final private String TOPIC_SET_FILE = "topicset.xml";
    final private String TOPIC_NAMESPACE_FILE = "topicnamespace.xml";
    final private String TOPIC_NS_URI = "http://docs.oasis-open.org/wsn/t-1";
    
    /**
     * Constructor that accepts URL of notification service
     * 
     * @param url the URL of the notification service
     * @throws MalformedURLException
     * @throws OSCARSServiceException
     */
    public NotifySender(String url) throws MalformedURLException, OSCARSServiceException{
        this.client = WSNBrokerClient.getClient(url);
        this.url = url;
        synchronized(this){
            if(topics == null){
                this.loadTopics();
            }
            if(namespaceMap == null){
                namespaceMap = NotifyNSUtil.getNamespaceMap();
            }
        }
    }
    
    /**
     * Method that sends a non-error event given the eventType, originator and resDetails
     * 
     * @param eventType the type of event being sent
     * @param originator the user that sent the request that triggered the event
     * @param resDetails the details of the reservation affected by the event
     * @throws OSCARSServiceException
     */
    public void send(String eventType, String originator, ResDetails resDetails) throws OSCARSServiceException{
        EventContent event = new EventContent();
        event.setId(UUID.randomUUID().toString());
        event.setTimestamp(System.currentTimeMillis()/1000);
        event.setType(eventType);
        event.setResDetails(resDetails);
        this.send(event);
    }
    
    /**
     * Method that sends an error event to the notification service
     * 
     * @param eventType the type of event being sent
     * @param originator the user that sent the request that triggered the event
     * @param errMsg a message describing the error
     * @param errSource the domain that caused the error
     * @param resDetails the details of the reservation affected by the event.
     * @throws OSCARSServiceException
     */
    public void sendError(String eventType, String originator, String errMsg, 
            String errSource, ResDetails resDetails) throws OSCARSServiceException{
        EventContent event = new EventContent();
        event.setId(UUID.randomUUID().toString());
        event.setTimestamp(System.currentTimeMillis()/1000);
        event.setType(eventType);
        event.setErrorMessage(errMsg);
        event.setResDetails(resDetails);
        this.send(event);
    }
    
    public void send(EventContent event) throws OSCARSServiceException{
        OSCARSNetLogger netLog = OSCARSNetLogger.getTlogger();
        this.log.debug(netLog.start("NotifySender.send"));
        
        //Set event
        MessageType msg = new MessageType();
        ObjectFactory objFactory = new ObjectFactory();
        Document eventDoc = null;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            eventDoc = db.newDocument();
            //TODO: Should be a better way than hard-coding class
            JAXBContext jaxbContext = JAXBContext.newInstance("net.es.oscars.api.soap.gen.v06");
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.marshal(objFactory.createEvent(event), eventDoc);
            msg.getAny().add(eventDoc.getDocumentElement());
        } catch (Exception e) {
            this.log.debug(netLog.error("NotifySender.send", ErrSev.CRITICAL, e.getMessage(), this.url));
            e.printStackTrace();
            throw new OSCARSServiceException(e.getMessage());
        }
        
        //set topics
        Notify notifyMsg = new Notify();
        NotificationMessageHolderType notifyMsgHolder = new NotificationMessageHolderType();
        try{
            notifyMsgHolder.setTopic(this.generateTopicExpression(eventDoc.getDocumentElement()));
        }catch(Exception e){
            this.log.debug(netLog.error("NotifySender.send", ErrSev.CRITICAL, e.getMessage(), this.url));
            e.printStackTrace();
            throw new OSCARSServiceException(e.getMessage());
        }
        
        
        notifyMsgHolder.setMessage(msg);
        notifyMsg.getNotificationMessage().add(notifyMsgHolder);
        
        //send to broker
        try{
            this.client.getPortType().notify(notifyMsg);
        }catch(Exception e){
            this.log.error(netLog.error("NotifySender.send", ErrSev.CRITICAL, e.getMessage(), this.url));
            e.printStackTrace();
            throw new OSCARSServiceException(e.getMessage());
        }
        this.log.debug(netLog.start("NotifySender.send", null, this.url));
    }
    
    /**
     * Loads Topics from an XML file. The file must follow the WS-Topics standard
     * listed by OASIS (http://docs.oasis-open.org/wsn/wsn-ws_topics-1.3-spec-os.pdf).
     * It does NOT support the option "Extension Topics" listed in section 6.1 of the
     * WS-Topics specification. This is intended to allow for lightweight activation/deactivation
     * of notifications about certain topics.
     *
     * @param topicSetFile file that contains supported topics (TopicSet)
     * @param topicNamesapce file that describes all possible topics (TopicNamespace)
     * @throws OSCARSServiceException 
     */
    private void loadTopics() throws OSCARSServiceException{
        topics = new HashMap<String, String>();
        
        //Step 0: Load topic config file names and namespaces
        ContextConfig cc = ContextConfig.getInstance();
        String topicSetFilename = null;
        String topicNsFilename = null;
        try {
            topicSetFilename = cc.getFilePath(ServiceNames.SVC_UTILS,cc.getContext(),
                    TOPIC_SET_FILE);
            topicNsFilename = cc.getFilePath(ServiceNames.SVC_UTILS,cc.getContext(),
                    TOPIC_NAMESPACE_FILE);
        } catch (ConfigException e) {
            throw new OSCARSServiceException("Unable to load topic set XML file: " + e.getMessage());
        }
        Map<String, String> prefixMap = NotifyNSUtil.getPrefixMap();
        
        //Step 1: Figure out which Topics are supported
        //open topicset
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder db = null;
        Document topicSetDoc = null;
        try {
            db = dbf.newDocumentBuilder();
            topicSetDoc = db.parse(new File(topicSetFilename));
        } catch (Exception e) {
            e.printStackTrace();
            throw new OSCARSServiceException(e.getMessage());
        }
        
        Element topicSetRoot = topicSetDoc.getDocumentElement();
        ArrayList<Element> topicSetElems = new ArrayList<Element>();
        ArrayList<String> topicSetParents = new ArrayList<String>();
        HashMap<String, Boolean> supportedTopics = new HashMap<String, Boolean>();
        //initialize topic set
        topicSetElems.addAll(0, this.nodesToList(topicSetRoot.getChildNodes()));
        //load this.topics with name,xpath entries
        while(!topicSetElems.isEmpty()){
            Element currElem = topicSetElems.get(0);
            String name = currElem.getLocalName();
            NodeList children = currElem.getChildNodes();
            //check if working way back up tree
            if((!topicSetParents.isEmpty()) && name.equals(topicSetParents.get(0))){
                topicSetElems.remove(0);
                topicSetParents.remove(0);
                continue;
            }
            String namespaceURI = currElem.getNamespaceURI();
            String prefix = prefixMap.get(namespaceURI);
            String isTopic = currElem.getAttributeNS(TOPIC_NS_URI, "topic");
            if("true".equals(isTopic)){
                String completeName = prefix + ":";
                for(int i = (topicSetParents.size() - 1); i >= 0; i--){
                    completeName += (topicSetParents.get(i) + "/");
                }
                completeName += name;
                supportedTopics.put(completeName, true);
            }
            
            if(children.getLength() == 0){
                topicSetElems.remove(0);
            }else{
                topicSetParents.add(0, name);
                topicSetElems.addAll(0, this.nodesToList(children));
            }
        }

        //Step 2: Find supported topics in topic namespace
        //load file
        Document topicNSDoc = null;
        try {
            topicNSDoc = db.parse(new File(topicNsFilename));
        } catch (Exception e) {
            e.printStackTrace();
            throw new OSCARSServiceException(e.getMessage());
        }
        Element topicNSRoot = topicNSDoc.getDocumentElement();
        String targetNamespace = topicNSRoot.getAttribute("targetNamespace");
        String prefix = prefixMap.get(targetNamespace);
        ArrayList<Element> topicNSElems = new ArrayList<Element>();
        ArrayList<String> parents = new ArrayList<String>();
        //topicNSElems.addAll(0, topicNSRoot.getChildren("Topic", wstop));
        topicNSElems.addAll(0, this.getChildren(topicNSRoot, TOPIC_NS_URI, "Topic"));
        while(!topicNSElems.isEmpty()){
            Element currElem = topicNSElems.get(0);
            String completeName = prefix + ":";
            String name = currElem.getAttributeNS(TOPIC_NS_URI, "name");
            if(name == null){
                topicNSElems.remove(0);
                continue;
            }
            
            List<Element> children = this.getChildren(currElem, TOPIC_NS_URI, "Topic");
            //check if working way back up tree
            if((!parents.isEmpty()) && name.equals(parents.get(0))){
                topicNSElems.remove(0);
                parents.remove(0);
                continue;
            }

            for(int i = (parents.size() - 1); i >= 0; i--){
                completeName += (parents.get(i) + "/");
            }
            completeName += name;
            if(!supportedTopics.containsKey(completeName)){
                topicNSElems.remove(0);
                continue;
            }
            if(children.isEmpty()){
                topicNSElems.remove(0);
            }else{
                parents.add(0, name);
                topicNSElems.addAll(0, children);
            }
            
            List<Element> msgChilds = this.getChildren(currElem, TOPIC_NS_URI, "MessagePattern");
            if(msgChilds == null || msgChilds.isEmpty()){
                continue;
            }
            String dialect = msgChilds.get(0).getAttributeNS(TOPIC_NS_URI, "Dialect");
            if(TopicDialect.XPATH.equals(dialect)){
                String xpath = msgChilds.get(0).getTextContent();
                topics.put(completeName, xpath);
            }
        }
    }

    private List<Element> getChildren(Element parentElem, String namespaceUri,
            String tagName) {
        ArrayList<Element> tmpList = new ArrayList<Element>();
        NodeList childNodes =  parentElem.getChildNodes();
        for(int i = 0; i < childNodes.getLength(); i++){
            //ignore text nodes and extraneous data
            if(!(childNodes.item(i) instanceof Element)){
                continue;
            }
            Element child = (Element) childNodes.item(i);
            if(tagName.equals(child.getLocalName()) && 
                namespaceUri.equals(child.getNamespaceURI())){
                tmpList.add(child);
            }
        }
        return tmpList;
    }

    private List<Element> nodesToList(NodeList childNodes) {
        ArrayList<Element> tmpList = new ArrayList<Element>();
        for(int i = 0; i < childNodes.getLength(); i++){
            //ignore text nodes and extraneous data
            if(childNodes.item(i) instanceof Element){
                tmpList.add((Element) childNodes.item(i));
            }
        }
        return tmpList;
    }
    
    /**
     * Matches an event to a topic then returns a TopicExpression
     *
     * @param event the event to classify as belonging to a topic
     * @return a topic expression for the given event
     */
    private TopicExpressionType generateTopicExpression(Element event)
                    throws OSCARSServiceException{
        TopicExpressionType topicExpr = new TopicExpressionType();
        topicExpr.setDialect(TopicDialect.FULL);
        String topicString = "";
        boolean firstMatch = true;
        for(String topic : topics.keySet()){
            String xpathStr = topics.get(topic);
            /* Prepare message for parsing by adding outer element to appease axis2 */
            XPath xpath = XPathFactory.newInstance().newXPath();
            xpath.setNamespaceContext(new FilterNamespaceContext(namespaceMap));
            XPathExpression xpathExpr = null;
            Boolean xpathResult = null;
            try {
                xpathExpr = xpath.compile(xpathStr);
                xpathResult = (Boolean) xpathExpr.evaluate(event, XPathConstants.BOOLEAN);
            } catch (XPathExpressionException e) {
                e.printStackTrace();
                throw new OSCARSServiceException(e.getMessage());
            }
            if(xpathResult){
                topicString += (firstMatch ? "" : "|");
                topicString += (topic);
                firstMatch = false;
            }
        }
        if("".equals(topicString)){
            return null;
        }
        topicExpr.setValue(topicString);

        return topicExpr;
    }
}

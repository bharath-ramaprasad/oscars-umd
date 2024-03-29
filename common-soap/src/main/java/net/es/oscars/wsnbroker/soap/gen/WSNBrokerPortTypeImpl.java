
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package net.es.oscars.wsnbroker.soap.gen;

import java.util.logging.Logger;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 2.2.5
 * Mon Jul 18 10:17:00 EDT 2011
 * Generated source version: 2.2.5
 * 
 */

@javax.jws.WebService(
                      serviceName = "WSNBrokerService",
                      portName = "WSNBrokerPort",
                      targetNamespace = "http://oscars.es.net/OSCARS/wsnbroker",
                      wsdlLocation = "file:/home/oscars/OSCARS_DIST/common-soap/src/main/resources/wsnbroker/wsnbroker.wsdl",
                      endpointInterface = "net.es.oscars.wsnbroker.soap.gen.WSNBrokerPortType")
                      
public class WSNBrokerPortTypeImpl implements WSNBrokerPortType {

    private static final Logger LOG = Logger.getLogger(WSNBrokerPortTypeImpl.class.getName());

    /* (non-Javadoc)
     * @see net.es.oscars.wsnbroker.soap.gen.WSNBrokerPortType#destroyRegistration(org.oasis_open.docs.wsn.br_2.DestroyRegistration  destroyRegistrationRequest )*
     */
    public org.oasis_open.docs.wsn.br_2.DestroyRegistrationResponse destroyRegistration(org.oasis_open.docs.wsn.br_2.DestroyRegistration destroyRegistrationRequest) throws ResourceUnknownFault , ResourceNotDestroyedFault    { 
        LOG.info("Executing operation destroyRegistration");
        System.out.println(destroyRegistrationRequest);
        try {
            org.oasis_open.docs.wsn.br_2.DestroyRegistrationResponse _return = null;
            return _return;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new ResourceUnknownFault("ResourceUnknownFault...");
        //throw new ResourceNotDestroyedFault("ResourceNotDestroyedFault...");
    }

    /* (non-Javadoc)
     * @see net.es.oscars.wsnbroker.soap.gen.WSNBrokerPortType#unsubscribe(org.oasis_open.docs.wsn.b_2.Unsubscribe  unsubscribeRequest )*
     */
    public org.oasis_open.docs.wsn.b_2.UnsubscribeResponse unsubscribe(org.oasis_open.docs.wsn.b_2.Unsubscribe unsubscribeRequest) throws UnableToDestroySubscriptionFault , ResourceUnknownFault    { 
        LOG.info("Executing operation unsubscribe");
        System.out.println(unsubscribeRequest);
        try {
            org.oasis_open.docs.wsn.b_2.UnsubscribeResponse _return = null;
            return _return;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new UnableToDestroySubscriptionFault("UnableToDestroySubscriptionFault...");
        //throw new ResourceUnknownFault("ResourceUnknownFault...");
    }

    /* (non-Javadoc)
     * @see net.es.oscars.wsnbroker.soap.gen.WSNBrokerPortType#renew(org.oasis_open.docs.wsn.b_2.Renew  renewRequest )*
     */
    public org.oasis_open.docs.wsn.b_2.RenewResponse renew(org.oasis_open.docs.wsn.b_2.Renew renewRequest) throws UnacceptableTerminationTimeFault , ResourceUnknownFault    { 
        LOG.info("Executing operation renew");
        System.out.println(renewRequest);
        try {
            org.oasis_open.docs.wsn.b_2.RenewResponse _return = null;
            return _return;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new UnacceptableTerminationTimeFault("UnacceptableTerminationTimeFault...");
        //throw new ResourceUnknownFault("ResourceUnknownFault...");
    }

    /* (non-Javadoc)
     * @see net.es.oscars.wsnbroker.soap.gen.WSNBrokerPortType#registerPublisher(org.oasis_open.docs.wsn.br_2.RegisterPublisher  registerPublisherRequest )*
     */
    public org.oasis_open.docs.wsn.br_2.RegisterPublisherResponse registerPublisher(org.oasis_open.docs.wsn.br_2.RegisterPublisher registerPublisherRequest) throws UnacceptableInitialTerminationTimeFault , TopicNotSupportedFault , PublisherRegistrationRejectedFault , InvalidTopicExpressionFault , ResourceUnknownFault , PublisherRegistrationFailedFault    { 
        LOG.info("Executing operation registerPublisher");
        System.out.println(registerPublisherRequest);
        try {
            org.oasis_open.docs.wsn.br_2.RegisterPublisherResponse _return = null;
            return _return;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new UnacceptableInitialTerminationTimeFault("UnacceptableInitialTerminationTimeFault...");
        //throw new TopicNotSupportedFault("TopicNotSupportedFault...");
        //throw new PublisherRegistrationRejectedFault("PublisherRegistrationRejectedFault...");
        //throw new InvalidTopicExpressionFault("InvalidTopicExpressionFault...");
        //throw new ResourceUnknownFault("ResourceUnknownFault...");
        //throw new PublisherRegistrationFailedFault("PublisherRegistrationFailedFault...");
    }

    /* (non-Javadoc)
     * @see net.es.oscars.wsnbroker.soap.gen.WSNBrokerPortType#pauseSubscription(org.oasis_open.docs.wsn.b_2.PauseSubscription  pauseSubscriptionRequest )*
     */
    public org.oasis_open.docs.wsn.b_2.PauseSubscriptionResponse pauseSubscription(org.oasis_open.docs.wsn.b_2.PauseSubscription pauseSubscriptionRequest) throws ResourceUnknownFault , PauseFailedFault    { 
        LOG.info("Executing operation pauseSubscription");
        System.out.println(pauseSubscriptionRequest);
        try {
            org.oasis_open.docs.wsn.b_2.PauseSubscriptionResponse _return = null;
            return _return;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new ResourceUnknownFault("ResourceUnknownFault...");
        //throw new PauseFailedFault("PauseFailedFault...");
    }

    /* (non-Javadoc)
     * @see net.es.oscars.wsnbroker.soap.gen.WSNBrokerPortType#resumeSubscription(org.oasis_open.docs.wsn.b_2.ResumeSubscription  resumeSubscriptionRequest )*
     */
    public org.oasis_open.docs.wsn.b_2.ResumeSubscriptionResponse resumeSubscription(org.oasis_open.docs.wsn.b_2.ResumeSubscription resumeSubscriptionRequest) throws ResumeFailedFault , ResourceUnknownFault    { 
        LOG.info("Executing operation resumeSubscription");
        System.out.println(resumeSubscriptionRequest);
        try {
            org.oasis_open.docs.wsn.b_2.ResumeSubscriptionResponse _return = null;
            return _return;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new ResumeFailedFault("ResumeFailedFault...");
        //throw new ResourceUnknownFault("ResourceUnknownFault...");
    }

    /* (non-Javadoc)
     * @see net.es.oscars.wsnbroker.soap.gen.WSNBrokerPortType#notify(org.oasis_open.docs.wsn.b_2.Notify  notify )*
     */
    public void notify(org.oasis_open.docs.wsn.b_2.Notify notify) { 
        LOG.info("Executing operation notify");
        System.out.println(notify);
        try {
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see net.es.oscars.wsnbroker.soap.gen.WSNBrokerPortType#subscribe(org.oasis_open.docs.wsn.b_2.Subscribe  subscribeRequest )*
     */
    public org.oasis_open.docs.wsn.b_2.SubscribeResponse subscribe(org.oasis_open.docs.wsn.b_2.Subscribe subscribeRequest) throws TopicExpressionDialectUnknownFault , SubscribeCreationFailedFault , UnacceptableInitialTerminationTimeFault , TopicNotSupportedFault , InvalidMessageContentExpressionFault , UnrecognizedPolicyRequestFault , InvalidProducerPropertiesExpressionFault , UnsupportedPolicyRequestFault , InvalidTopicExpressionFault , ResourceUnknownFault , NotifyMessageNotSupportedFault , InvalidFilterFault    { 
        LOG.info("Executing operation subscribe");
        System.out.println(subscribeRequest);
        try {
            org.oasis_open.docs.wsn.b_2.SubscribeResponse _return = null;
            return _return;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new TopicExpressionDialectUnknownFault("TopicExpressionDialectUnknownFault...");
        //throw new SubscribeCreationFailedFault("SubscribeCreationFailedFault...");
        //throw new UnacceptableInitialTerminationTimeFault("UnacceptableInitialTerminationTimeFault...");
        //throw new TopicNotSupportedFault("TopicNotSupportedFault...");
        //throw new InvalidMessageContentExpressionFault("InvalidMessageContentExpressionFault...");
        //throw new UnrecognizedPolicyRequestFault("UnrecognizedPolicyRequestFault...");
        //throw new InvalidProducerPropertiesExpressionFault("InvalidProducerPropertiesExpressionFault...");
        //throw new UnsupportedPolicyRequestFault("UnsupportedPolicyRequestFault...");
        //throw new InvalidTopicExpressionFault("InvalidTopicExpressionFault...");
        //throw new ResourceUnknownFault("ResourceUnknownFault...");
        //throw new NotifyMessageNotSupportedFault("NotifyMessageNotSupportedFault...");
        //throw new InvalidFilterFault("InvalidFilterFault...");
    }

}

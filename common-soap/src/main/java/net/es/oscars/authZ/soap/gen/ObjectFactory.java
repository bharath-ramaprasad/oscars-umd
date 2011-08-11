
package net.es.oscars.authZ.soap.gen;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the net.es.oscars.authZ.soap.gen package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _CheckAccessRequest_QNAME = new QName("http://oscars.es.net/OSCARS/authZ", "checkAccessRequest");
    private final static QName _CheckAccessResponse_QNAME = new QName("http://oscars.es.net/OSCARS/authZ", "checkAccessResponse");
    private final static QName _CheckMultiAccessRequest_QNAME = new QName("http://oscars.es.net/OSCARS/authZ", "checkMultiAccessRequest");
    private final static QName _CheckMultiAccessResponse_QNAME = new QName("http://oscars.es.net/OSCARS/authZ", "checkMultiAccessResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: net.es.oscars.authZ.soap.gen
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ReqPermType }
     * 
     */
    public ReqPermType createReqPermType() {
        return new ReqPermType();
    }

    /**
     * Create an instance of {@link CheckMultiAccessParams }
     * 
     */
    public CheckMultiAccessParams createCheckMultiAccessParams() {
        return new CheckMultiAccessParams();
    }

    /**
     * Create an instance of {@link CheckAccessParams }
     * 
     */
    public CheckAccessParams createCheckAccessParams() {
        return new CheckAccessParams();
    }

    /**
     * Create an instance of {@link CheckAccessReply }
     * 
     */
    public CheckAccessReply createCheckAccessReply() {
        return new CheckAccessReply();
    }

    /**
     * Create an instance of {@link MultiAccessPerms }
     * 
     */
    public MultiAccessPerms createMultiAccessPerms() {
        return new MultiAccessPerms();
    }

    /**
     * Create an instance of {@link MultiAccessPerm }
     * 
     */
    public MultiAccessPerm createMultiAccessPerm() {
        return new MultiAccessPerm();
    }

    /**
     * Create an instance of {@link PermType }
     * 
     */
    public PermType createPermType() {
        return new PermType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckAccessParams }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authZ", name = "checkAccessRequest")
    public JAXBElement<CheckAccessParams> createCheckAccessRequest(CheckAccessParams value) {
        return new JAXBElement<CheckAccessParams>(_CheckAccessRequest_QNAME, CheckAccessParams.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckAccessReply }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authZ", name = "checkAccessResponse")
    public JAXBElement<CheckAccessReply> createCheckAccessResponse(CheckAccessReply value) {
        return new JAXBElement<CheckAccessReply>(_CheckAccessResponse_QNAME, CheckAccessReply.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckMultiAccessParams }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authZ", name = "checkMultiAccessRequest")
    public JAXBElement<CheckMultiAccessParams> createCheckMultiAccessRequest(CheckMultiAccessParams value) {
        return new JAXBElement<CheckMultiAccessParams>(_CheckMultiAccessRequest_QNAME, CheckMultiAccessParams.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MultiAccessPerms }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authZ", name = "checkMultiAccessResponse")
    public JAXBElement<MultiAccessPerms> createCheckMultiAccessResponse(MultiAccessPerms value) {
        return new JAXBElement<MultiAccessPerms>(_CheckMultiAccessResponse_QNAME, MultiAccessPerms.class, null, value);
    }

}

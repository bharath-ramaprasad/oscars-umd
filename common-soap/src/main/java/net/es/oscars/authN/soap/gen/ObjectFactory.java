
package net.es.oscars.authN.soap.gen;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the net.es.oscars.authN.soap.gen package. 
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

    private final static QName _VerifyDNReq_QNAME = new QName("http://oscars.es.net/OSCARS/authN", "verifyDNReq");
    private final static QName _VerifyResp_QNAME = new QName("http://oscars.es.net/OSCARS/authN", "verifyResp");
    private final static QName _VerifyLoginReq_QNAME = new QName("http://oscars.es.net/OSCARS/authN", "verifyLoginReq");
    private final static QName _VerifyOriginatorReq_QNAME = new QName("http://oscars.es.net/OSCARS/authN", "verifyOriginatorReq");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: net.es.oscars.authN.soap.gen
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link VerifyLoginReqType }
     * 
     */
    public VerifyLoginReqType createVerifyLoginReqType() {
        return new VerifyLoginReqType();
    }

    /**
     * Create an instance of {@link VerifyOrigReqType }
     * 
     */
    public VerifyOrigReqType createVerifyOrigReqType() {
        return new VerifyOrigReqType();
    }

    /**
     * Create an instance of {@link VerifyDNReqType }
     * 
     */
    public VerifyDNReqType createVerifyDNReqType() {
        return new VerifyDNReqType();
    }

    /**
     * Create an instance of {@link LoginId }
     * 
     */
    public LoginId createLoginId() {
        return new LoginId();
    }

    /**
     * Create an instance of {@link VerifyReply }
     * 
     */
    public VerifyReply createVerifyReply() {
        return new VerifyReply();
    }

    /**
     * Create an instance of {@link DNType }
     * 
     */
    public DNType createDNType() {
        return new DNType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VerifyDNReqType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authN", name = "verifyDNReq")
    public JAXBElement<VerifyDNReqType> createVerifyDNReq(VerifyDNReqType value) {
        return new JAXBElement<VerifyDNReqType>(_VerifyDNReq_QNAME, VerifyDNReqType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VerifyReply }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authN", name = "verifyResp")
    public JAXBElement<VerifyReply> createVerifyResp(VerifyReply value) {
        return new JAXBElement<VerifyReply>(_VerifyResp_QNAME, VerifyReply.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VerifyLoginReqType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authN", name = "verifyLoginReq")
    public JAXBElement<VerifyLoginReqType> createVerifyLoginReq(VerifyLoginReqType value) {
        return new JAXBElement<VerifyLoginReqType>(_VerifyLoginReq_QNAME, VerifyLoginReqType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VerifyOrigReqType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authN", name = "verifyOriginatorReq")
    public JAXBElement<VerifyOrigReqType> createVerifyOriginatorReq(VerifyOrigReqType value) {
        return new JAXBElement<VerifyOrigReqType>(_VerifyOriginatorReq_QNAME, VerifyOrigReqType.class, null, value);
    }

}

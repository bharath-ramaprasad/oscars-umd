
package net.es.oscars.pss.soap.gen;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the net.es.oscars.pss.soap.gen package. 
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

    private final static QName _SetupReq_QNAME = new QName("http://oscars.es.net/OSCARS/pss", "setupReq");
    private final static QName _StatusReq_QNAME = new QName("http://oscars.es.net/OSCARS/pss", "statusReq");
    private final static QName _TeardownReq_QNAME = new QName("http://oscars.es.net/OSCARS/pss", "teardownReq");
    private final static QName _ModifyReq_QNAME = new QName("http://oscars.es.net/OSCARS/pss", "modifyReq");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: net.es.oscars.pss.soap.gen
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TeardownReqContent }
     * 
     */
    public TeardownReqContent createTeardownReqContent() {
        return new TeardownReqContent();
    }

    /**
     * Create an instance of {@link StatusReqContent }
     * 
     */
    public StatusReqContent createStatusReqContent() {
        return new StatusReqContent();
    }

    /**
     * Create an instance of {@link ModifyReqContent }
     * 
     */
    public ModifyReqContent createModifyReqContent() {
        return new ModifyReqContent();
    }

    /**
     * Create an instance of {@link SetupReqContent }
     * 
     */
    public SetupReqContent createSetupReqContent() {
        return new SetupReqContent();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetupReqContent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/pss", name = "setupReq")
    public JAXBElement<SetupReqContent> createSetupReq(SetupReqContent value) {
        return new JAXBElement<SetupReqContent>(_SetupReq_QNAME, SetupReqContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StatusReqContent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/pss", name = "statusReq")
    public JAXBElement<StatusReqContent> createStatusReq(StatusReqContent value) {
        return new JAXBElement<StatusReqContent>(_StatusReq_QNAME, StatusReqContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TeardownReqContent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/pss", name = "teardownReq")
    public JAXBElement<TeardownReqContent> createTeardownReq(TeardownReqContent value) {
        return new JAXBElement<TeardownReqContent>(_TeardownReq_QNAME, TeardownReqContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyReqContent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/pss", name = "modifyReq")
    public JAXBElement<ModifyReqContent> createModifyReq(ModifyReqContent value) {
        return new JAXBElement<ModifyReqContent>(_ModifyReq_QNAME, ModifyReqContent.class, null, value);
    }

}

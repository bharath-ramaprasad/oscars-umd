
package net.es.oscars.coord.soap.gen;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the net.es.oscars.coord.soap.gen package. 
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

    private final static QName _PSSReplyReq_QNAME = new QName("http://oscars.es.net/OSCARS/coord", "PSSReplyReq");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: net.es.oscars.coord.soap.gen
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PSSReplyContent }
     * 
     */
    public PSSReplyContent createPSSReplyContent() {
        return new PSSReplyContent();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PSSReplyContent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/coord", name = "PSSReplyReq")
    public JAXBElement<PSSReplyContent> createPSSReplyReq(PSSReplyContent value) {
        return new JAXBElement<PSSReplyContent>(_PSSReplyReq_QNAME, PSSReplyContent.class, null, value);
    }

}

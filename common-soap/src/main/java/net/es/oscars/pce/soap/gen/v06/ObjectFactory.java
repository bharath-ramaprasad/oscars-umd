
package net.es.oscars.pce.soap.gen.v06;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the net.es.oscars.pce.soap.gen.v06 package. 
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

    private final static QName _PCECancel_QNAME = new QName("http://oscars.es.net/OSCARS/PCE/20090922", "PCECancel");
    private final static QName _AggregatorCancel_QNAME = new QName("http://oscars.es.net/OSCARS/PCE/20090922", "AggregatorCancel");
    private final static QName _AggregatorCreate_QNAME = new QName("http://oscars.es.net/OSCARS/PCE/20090922", "AggregatorCreate");
    private final static QName _PCECreate_QNAME = new QName("http://oscars.es.net/OSCARS/PCE/20090922", "PCECreate");
    private final static QName _PCECommitReply_QNAME = new QName("http://oscars.es.net/OSCARS/PCE/20090922", "PCECommitReply");
    private final static QName _PCEModifyReply_QNAME = new QName("http://oscars.es.net/OSCARS/PCE/20090922", "PCEModifyReply");
    private final static QName _AggregatorModify_QNAME = new QName("http://oscars.es.net/OSCARS/PCE/20090922", "AggregatorModify");
    private final static QName _AggregatorCommit_QNAME = new QName("http://oscars.es.net/OSCARS/PCE/20090922", "AggregatorCommit");
    private final static QName _PCECancelReply_QNAME = new QName("http://oscars.es.net/OSCARS/PCE/20090922", "PCECancelReply");
    private final static QName _PCEReply_QNAME = new QName("http://oscars.es.net/OSCARS/PCE/20090922", "PCEReply");
    private final static QName _PCEModify_QNAME = new QName("http://oscars.es.net/OSCARS/PCE/20090922", "PCEModify");
    private final static QName _PCECommit_QNAME = new QName("http://oscars.es.net/OSCARS/PCE/20090922", "PCECommit");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: net.es.oscars.pce.soap.gen.v06
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PCEError }
     * 
     */
    public PCEError createPCEError() {
        return new PCEError();
    }

    /**
     * Create an instance of {@link AggregatorCancelContent }
     * 
     */
    public AggregatorCancelContent createAggregatorCancelContent() {
        return new AggregatorCancelContent();
    }

    /**
     * Create an instance of {@link PCECreateContent }
     * 
     */
    public PCECreateContent createPCECreateContent() {
        return new PCECreateContent();
    }

    /**
     * Create an instance of {@link PCECommitContent }
     * 
     */
    public PCECommitContent createPCECommitContent() {
        return new PCECommitContent();
    }

    /**
     * Create an instance of {@link AggregatorCreateContent }
     * 
     */
    public AggregatorCreateContent createAggregatorCreateContent() {
        return new AggregatorCreateContent();
    }

    /**
     * Create an instance of {@link AggregatorModifyContent }
     * 
     */
    public AggregatorModifyContent createAggregatorModifyContent() {
        return new AggregatorModifyContent();
    }

    /**
     * Create an instance of {@link PCEReplyContent }
     * 
     */
    public PCEReplyContent createPCEReplyContent() {
        return new PCEReplyContent();
    }

    /**
     * Create an instance of {@link PCECancelReplyContent }
     * 
     */
    public PCECancelReplyContent createPCECancelReplyContent() {
        return new PCECancelReplyContent();
    }

    /**
     * Create an instance of {@link AggregatorCommitContent }
     * 
     */
    public AggregatorCommitContent createAggregatorCommitContent() {
        return new AggregatorCommitContent();
    }

    /**
     * Create an instance of {@link PCEDataContent }
     * 
     */
    public PCEDataContent createPCEDataContent() {
        return new PCEDataContent();
    }

    /**
     * Create an instance of {@link PCEModifyReplyContent }
     * 
     */
    public PCEModifyReplyContent createPCEModifyReplyContent() {
        return new PCEModifyReplyContent();
    }

    /**
     * Create an instance of {@link PCEModifyContent }
     * 
     */
    public PCEModifyContent createPCEModifyContent() {
        return new PCEModifyContent();
    }

    /**
     * Create an instance of {@link PCECancelContent }
     * 
     */
    public PCECancelContent createPCECancelContent() {
        return new PCECancelContent();
    }

    /**
     * Create an instance of {@link TagDataContent }
     * 
     */
    public TagDataContent createTagDataContent() {
        return new TagDataContent();
    }

    /**
     * Create an instance of {@link PCECommitReplyContent }
     * 
     */
    public PCECommitReplyContent createPCECommitReplyContent() {
        return new PCECommitReplyContent();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PCECancelContent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/PCE/20090922", name = "PCECancel")
    public JAXBElement<PCECancelContent> createPCECancel(PCECancelContent value) {
        return new JAXBElement<PCECancelContent>(_PCECancel_QNAME, PCECancelContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AggregatorCancelContent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/PCE/20090922", name = "AggregatorCancel")
    public JAXBElement<AggregatorCancelContent> createAggregatorCancel(AggregatorCancelContent value) {
        return new JAXBElement<AggregatorCancelContent>(_AggregatorCancel_QNAME, AggregatorCancelContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AggregatorCreateContent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/PCE/20090922", name = "AggregatorCreate")
    public JAXBElement<AggregatorCreateContent> createAggregatorCreate(AggregatorCreateContent value) {
        return new JAXBElement<AggregatorCreateContent>(_AggregatorCreate_QNAME, AggregatorCreateContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PCECreateContent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/PCE/20090922", name = "PCECreate")
    public JAXBElement<PCECreateContent> createPCECreate(PCECreateContent value) {
        return new JAXBElement<PCECreateContent>(_PCECreate_QNAME, PCECreateContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PCECommitReplyContent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/PCE/20090922", name = "PCECommitReply")
    public JAXBElement<PCECommitReplyContent> createPCECommitReply(PCECommitReplyContent value) {
        return new JAXBElement<PCECommitReplyContent>(_PCECommitReply_QNAME, PCECommitReplyContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PCEModifyReplyContent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/PCE/20090922", name = "PCEModifyReply")
    public JAXBElement<PCEModifyReplyContent> createPCEModifyReply(PCEModifyReplyContent value) {
        return new JAXBElement<PCEModifyReplyContent>(_PCEModifyReply_QNAME, PCEModifyReplyContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AggregatorModifyContent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/PCE/20090922", name = "AggregatorModify")
    public JAXBElement<AggregatorModifyContent> createAggregatorModify(AggregatorModifyContent value) {
        return new JAXBElement<AggregatorModifyContent>(_AggregatorModify_QNAME, AggregatorModifyContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AggregatorCommitContent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/PCE/20090922", name = "AggregatorCommit")
    public JAXBElement<AggregatorCommitContent> createAggregatorCommit(AggregatorCommitContent value) {
        return new JAXBElement<AggregatorCommitContent>(_AggregatorCommit_QNAME, AggregatorCommitContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PCECancelReplyContent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/PCE/20090922", name = "PCECancelReply")
    public JAXBElement<PCECancelReplyContent> createPCECancelReply(PCECancelReplyContent value) {
        return new JAXBElement<PCECancelReplyContent>(_PCECancelReply_QNAME, PCECancelReplyContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PCEReplyContent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/PCE/20090922", name = "PCEReply")
    public JAXBElement<PCEReplyContent> createPCEReply(PCEReplyContent value) {
        return new JAXBElement<PCEReplyContent>(_PCEReply_QNAME, PCEReplyContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PCEModifyContent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/PCE/20090922", name = "PCEModify")
    public JAXBElement<PCEModifyContent> createPCEModify(PCEModifyContent value) {
        return new JAXBElement<PCEModifyContent>(_PCEModify_QNAME, PCEModifyContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PCECommitContent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/PCE/20090922", name = "PCECommit")
    public JAXBElement<PCECommitContent> createPCECommit(PCECommitContent value) {
        return new JAXBElement<PCECommitContent>(_PCECommit_QNAME, PCECommitContent.class, null, value);
    }

}

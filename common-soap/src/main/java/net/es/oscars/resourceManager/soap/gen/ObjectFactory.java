
package net.es.oscars.resourceManager.soap.gen;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the net.es.oscars.resourceManager.soap.gen package. 
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

    private final static QName _GetAuditDataReq_QNAME = new QName("http://oscars.es.net/OSCARS/resourceManager", "getAuditDataReq");
    private final static QName _UpdateStatusResp_QNAME = new QName("http://oscars.es.net/OSCARS/resourceManager", "updateStatusResp");
    private final static QName _GetAuditDataResp_QNAME = new QName("http://oscars.es.net/OSCARS/resourceManager", "getAuditDataResp");
    private final static QName _AssignGriReq_QNAME = new QName("http://oscars.es.net/OSCARS/resourceManager", "assignGriReq");
    private final static QName _StoreResp_QNAME = new QName("http://oscars.es.net/OSCARS/resourceManager", "storeResp");
    private final static QName _UpdateStatusReq_QNAME = new QName("http://oscars.es.net/OSCARS/resourceManager", "updateStatusReq");
    private final static QName _StoreReq_QNAME = new QName("http://oscars.es.net/OSCARS/resourceManager", "storeReq");
    private final static QName _AssignGriResp_QNAME = new QName("http://oscars.es.net/OSCARS/resourceManager", "assignGriResp");
    private final static QName _RMCancelResResponse_QNAME = new QName("http://oscars.es.net/OSCARS/resourceManager", "RMCancelResResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: net.es.oscars.resourceManager.soap.gen
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RMCancelRespContent }
     * 
     */
    public RMCancelRespContent createRMCancelRespContent() {
        return new RMCancelRespContent();
    }

    /**
     * Create an instance of {@link UpdateStatusRespContent }
     * 
     */
    public UpdateStatusRespContent createUpdateStatusRespContent() {
        return new UpdateStatusRespContent();
    }

    /**
     * Create an instance of {@link GetAuditDataRespContent }
     * 
     */
    public GetAuditDataRespContent createGetAuditDataRespContent() {
        return new GetAuditDataRespContent();
    }

    /**
     * Create an instance of {@link StoreReqContent }
     * 
     */
    public StoreReqContent createStoreReqContent() {
        return new StoreReqContent();
    }

    /**
     * Create an instance of {@link AuditDetails }
     * 
     */
    public AuditDetails createAuditDetails() {
        return new AuditDetails();
    }

    /**
     * Create an instance of {@link GetAuditDataReqContent }
     * 
     */
    public GetAuditDataReqContent createGetAuditDataReqContent() {
        return new GetAuditDataReqContent();
    }

    /**
     * Create an instance of {@link AssignGriReqContent }
     * 
     */
    public AssignGriReqContent createAssignGriReqContent() {
        return new AssignGriReqContent();
    }

    /**
     * Create an instance of {@link UpdateStatusReqContent }
     * 
     */
    public UpdateStatusReqContent createUpdateStatusReqContent() {
        return new UpdateStatusReqContent();
    }

    /**
     * Create an instance of {@link AssignGriRespContent }
     * 
     */
    public AssignGriRespContent createAssignGriRespContent() {
        return new AssignGriRespContent();
    }

    /**
     * Create an instance of {@link StoreRespContent }
     * 
     */
    public StoreRespContent createStoreRespContent() {
        return new StoreRespContent();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAuditDataReqContent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/resourceManager", name = "getAuditDataReq")
    public JAXBElement<GetAuditDataReqContent> createGetAuditDataReq(GetAuditDataReqContent value) {
        return new JAXBElement<GetAuditDataReqContent>(_GetAuditDataReq_QNAME, GetAuditDataReqContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateStatusRespContent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/resourceManager", name = "updateStatusResp")
    public JAXBElement<UpdateStatusRespContent> createUpdateStatusResp(UpdateStatusRespContent value) {
        return new JAXBElement<UpdateStatusRespContent>(_UpdateStatusResp_QNAME, UpdateStatusRespContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAuditDataRespContent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/resourceManager", name = "getAuditDataResp")
    public JAXBElement<GetAuditDataRespContent> createGetAuditDataResp(GetAuditDataRespContent value) {
        return new JAXBElement<GetAuditDataRespContent>(_GetAuditDataResp_QNAME, GetAuditDataRespContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AssignGriReqContent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/resourceManager", name = "assignGriReq")
    public JAXBElement<AssignGriReqContent> createAssignGriReq(AssignGriReqContent value) {
        return new JAXBElement<AssignGriReqContent>(_AssignGriReq_QNAME, AssignGriReqContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StoreRespContent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/resourceManager", name = "storeResp")
    public JAXBElement<StoreRespContent> createStoreResp(StoreRespContent value) {
        return new JAXBElement<StoreRespContent>(_StoreResp_QNAME, StoreRespContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateStatusReqContent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/resourceManager", name = "updateStatusReq")
    public JAXBElement<UpdateStatusReqContent> createUpdateStatusReq(UpdateStatusReqContent value) {
        return new JAXBElement<UpdateStatusReqContent>(_UpdateStatusReq_QNAME, UpdateStatusReqContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StoreReqContent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/resourceManager", name = "storeReq")
    public JAXBElement<StoreReqContent> createStoreReq(StoreReqContent value) {
        return new JAXBElement<StoreReqContent>(_StoreReq_QNAME, StoreReqContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AssignGriRespContent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/resourceManager", name = "assignGriResp")
    public JAXBElement<AssignGriRespContent> createAssignGriResp(AssignGriRespContent value) {
        return new JAXBElement<AssignGriRespContent>(_AssignGriResp_QNAME, AssignGriRespContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RMCancelRespContent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/resourceManager", name = "RMCancelResResponse")
    public JAXBElement<RMCancelRespContent> createRMCancelResResponse(RMCancelRespContent value) {
        return new JAXBElement<RMCancelRespContent>(_RMCancelResResponse_QNAME, RMCancelRespContent.class, null, value);
    }

}

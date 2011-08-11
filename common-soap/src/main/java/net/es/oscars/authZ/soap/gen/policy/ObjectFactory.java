
package net.es.oscars.authZ.soap.gen.policy;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import net.es.oscars.authCommonPolicy.soap.gen.AttrDetails;
import net.es.oscars.authCommonPolicy.soap.gen.ListAttrsReply;
import net.es.oscars.authCommonPolicy.soap.gen.ModifyAttrDetails;
import net.es.oscars.common.soap.gen.EmptyArg;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the net.es.oscars.authZ.soap.gen.policy package. 
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

    private final static QName _ModifyAttrRequest_QNAME = new QName("http://oscars.es.net/OSCARS/authZPolicy", "modifyAttrRequest");
    private final static QName _ListPermissionsRequest_QNAME = new QName("http://oscars.es.net/OSCARS/authZPolicy", "listPermissionsRequest");
    private final static QName _ModifyAttrResponse_QNAME = new QName("http://oscars.es.net/OSCARS/authZPolicy", "modifyAttrResponse");
    private final static QName _RemoveAuthRequest_QNAME = new QName("http://oscars.es.net/OSCARS/authZPolicy", "removeAuthRequest");
    private final static QName _RemoveAttrResponse_QNAME = new QName("http://oscars.es.net/OSCARS/authZPolicy", "removeAttrResponse");
    private final static QName _ModifyAuthResponse_QNAME = new QName("http://oscars.es.net/OSCARS/authZPolicy", "modifyAuthResponse");
    private final static QName _AddAuthResponse_QNAME = new QName("http://oscars.es.net/OSCARS/authZPolicy", "addAuthResponse");
    private final static QName _ListAuthsRequest_QNAME = new QName("http://oscars.es.net/OSCARS/authZPolicy", "listAuthsRequest");
    private final static QName _ListAuthsResponse_QNAME = new QName("http://oscars.es.net/OSCARS/authZPolicy", "listAuthsResponse");
    private final static QName _ListResourcesResponse_QNAME = new QName("http://oscars.es.net/OSCARS/authZPolicy", "listResourcesResponse");
    private final static QName _ModifyAuthRequest_QNAME = new QName("http://oscars.es.net/OSCARS/authZPolicy", "modifyAuthRequest");
    private final static QName _ListAttrsRequest_QNAME = new QName("http://oscars.es.net/OSCARS/authZPolicy", "listAttrsRequest");
    private final static QName _ListRpcsResponse_QNAME = new QName("http://oscars.es.net/OSCARS/authZPolicy", "listRpcsResponse");
    private final static QName _RemoveAttrRequest_QNAME = new QName("http://oscars.es.net/OSCARS/authZPolicy", "removeAttrRequest");
    private final static QName _ListConstraintsRequest_QNAME = new QName("http://oscars.es.net/OSCARS/authZPolicy", "listConstraintsRequest");
    private final static QName _ListConstraintsResponse_QNAME = new QName("http://oscars.es.net/OSCARS/authZPolicy", "listConstraintsResponse");
    private final static QName _ListRpcsRequest_QNAME = new QName("http://oscars.es.net/OSCARS/authZPolicy", "listRpcsRequest");
    private final static QName _ListResourcesRequest_QNAME = new QName("http://oscars.es.net/OSCARS/authZPolicy", "listResourcesRequest");
    private final static QName _RemoveAuthResponse_QNAME = new QName("http://oscars.es.net/OSCARS/authZPolicy", "removeAuthResponse");
    private final static QName _AddAttrResponse_QNAME = new QName("http://oscars.es.net/OSCARS/authZPolicy", "addAttrResponse");
    private final static QName _ListAttrsResponse_QNAME = new QName("http://oscars.es.net/OSCARS/authZPolicy", "listAttrsResponse");
    private final static QName _AddAttrRequest_QNAME = new QName("http://oscars.es.net/OSCARS/authZPolicy", "addAttrRequest");
    private final static QName _AddAuthRequest_QNAME = new QName("http://oscars.es.net/OSCARS/authZPolicy", "addAuthRequest");
    private final static QName _ListPermissionsResponse_QNAME = new QName("http://oscars.es.net/OSCARS/authZPolicy", "listPermissionsResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: net.es.oscars.authZ.soap.gen.policy
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ConstraintDetails }
     * 
     */
    public ConstraintDetails createConstraintDetails() {
        return new ConstraintDetails();
    }

    /**
     * Create an instance of {@link ListAuthsParams }
     * 
     */
    public ListAuthsParams createListAuthsParams() {
        return new ListAuthsParams();
    }

    /**
     * Create an instance of {@link ModifyAuthDetails }
     * 
     */
    public ModifyAuthDetails createModifyAuthDetails() {
        return new ModifyAuthDetails();
    }

    /**
     * Create an instance of {@link ListConstraintsReply }
     * 
     */
    public ListConstraintsReply createListConstraintsReply() {
        return new ListConstraintsReply();
    }

    /**
     * Create an instance of {@link RpcDetails }
     * 
     */
    public RpcDetails createRpcDetails() {
        return new RpcDetails();
    }

    /**
     * Create an instance of {@link ResourceDetails }
     * 
     */
    public ResourceDetails createResourceDetails() {
        return new ResourceDetails();
    }

    /**
     * Create an instance of {@link ListAuthsReply }
     * 
     */
    public ListAuthsReply createListAuthsReply() {
        return new ListAuthsReply();
    }

    /**
     * Create an instance of {@link ListResourcesReply }
     * 
     */
    public ListResourcesReply createListResourcesReply() {
        return new ListResourcesReply();
    }

    /**
     * Create an instance of {@link ListPermissionsReply }
     * 
     */
    public ListPermissionsReply createListPermissionsReply() {
        return new ListPermissionsReply();
    }

    /**
     * Create an instance of {@link PermissionDetails }
     * 
     */
    public PermissionDetails createPermissionDetails() {
        return new PermissionDetails();
    }

    /**
     * Create an instance of {@link AuthDetails }
     * 
     */
    public AuthDetails createAuthDetails() {
        return new AuthDetails();
    }

    /**
     * Create an instance of {@link ListRpcsReply }
     * 
     */
    public ListRpcsReply createListRpcsReply() {
        return new ListRpcsReply();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyAttrDetails }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authZPolicy", name = "modifyAttrRequest")
    public JAXBElement<ModifyAttrDetails> createModifyAttrRequest(ModifyAttrDetails value) {
        return new JAXBElement<ModifyAttrDetails>(_ModifyAttrRequest_QNAME, ModifyAttrDetails.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EmptyArg }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authZPolicy", name = "listPermissionsRequest")
    public JAXBElement<EmptyArg> createListPermissionsRequest(EmptyArg value) {
        return new JAXBElement<EmptyArg>(_ListPermissionsRequest_QNAME, EmptyArg.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EmptyArg }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authZPolicy", name = "modifyAttrResponse")
    public JAXBElement<EmptyArg> createModifyAttrResponse(EmptyArg value) {
        return new JAXBElement<EmptyArg>(_ModifyAttrResponse_QNAME, EmptyArg.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AuthDetails }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authZPolicy", name = "removeAuthRequest")
    public JAXBElement<AuthDetails> createRemoveAuthRequest(AuthDetails value) {
        return new JAXBElement<AuthDetails>(_RemoveAuthRequest_QNAME, AuthDetails.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EmptyArg }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authZPolicy", name = "removeAttrResponse")
    public JAXBElement<EmptyArg> createRemoveAttrResponse(EmptyArg value) {
        return new JAXBElement<EmptyArg>(_RemoveAttrResponse_QNAME, EmptyArg.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EmptyArg }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authZPolicy", name = "modifyAuthResponse")
    public JAXBElement<EmptyArg> createModifyAuthResponse(EmptyArg value) {
        return new JAXBElement<EmptyArg>(_ModifyAuthResponse_QNAME, EmptyArg.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EmptyArg }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authZPolicy", name = "addAuthResponse")
    public JAXBElement<EmptyArg> createAddAuthResponse(EmptyArg value) {
        return new JAXBElement<EmptyArg>(_AddAuthResponse_QNAME, EmptyArg.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListAuthsParams }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authZPolicy", name = "listAuthsRequest")
    public JAXBElement<ListAuthsParams> createListAuthsRequest(ListAuthsParams value) {
        return new JAXBElement<ListAuthsParams>(_ListAuthsRequest_QNAME, ListAuthsParams.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListAuthsReply }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authZPolicy", name = "listAuthsResponse")
    public JAXBElement<ListAuthsReply> createListAuthsResponse(ListAuthsReply value) {
        return new JAXBElement<ListAuthsReply>(_ListAuthsResponse_QNAME, ListAuthsReply.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListResourcesReply }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authZPolicy", name = "listResourcesResponse")
    public JAXBElement<ListResourcesReply> createListResourcesResponse(ListResourcesReply value) {
        return new JAXBElement<ListResourcesReply>(_ListResourcesResponse_QNAME, ListResourcesReply.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyAuthDetails }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authZPolicy", name = "modifyAuthRequest")
    public JAXBElement<ModifyAuthDetails> createModifyAuthRequest(ModifyAuthDetails value) {
        return new JAXBElement<ModifyAuthDetails>(_ModifyAuthRequest_QNAME, ModifyAuthDetails.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EmptyArg }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authZPolicy", name = "listAttrsRequest")
    public JAXBElement<EmptyArg> createListAttrsRequest(EmptyArg value) {
        return new JAXBElement<EmptyArg>(_ListAttrsRequest_QNAME, EmptyArg.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListRpcsReply }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authZPolicy", name = "listRpcsResponse")
    public JAXBElement<ListRpcsReply> createListRpcsResponse(ListRpcsReply value) {
        return new JAXBElement<ListRpcsReply>(_ListRpcsResponse_QNAME, ListRpcsReply.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authZPolicy", name = "removeAttrRequest")
    public JAXBElement<String> createRemoveAttrRequest(String value) {
        return new JAXBElement<String>(_RemoveAttrRequest_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EmptyArg }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authZPolicy", name = "listConstraintsRequest")
    public JAXBElement<EmptyArg> createListConstraintsRequest(EmptyArg value) {
        return new JAXBElement<EmptyArg>(_ListConstraintsRequest_QNAME, EmptyArg.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListConstraintsReply }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authZPolicy", name = "listConstraintsResponse")
    public JAXBElement<ListConstraintsReply> createListConstraintsResponse(ListConstraintsReply value) {
        return new JAXBElement<ListConstraintsReply>(_ListConstraintsResponse_QNAME, ListConstraintsReply.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EmptyArg }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authZPolicy", name = "listRpcsRequest")
    public JAXBElement<EmptyArg> createListRpcsRequest(EmptyArg value) {
        return new JAXBElement<EmptyArg>(_ListRpcsRequest_QNAME, EmptyArg.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EmptyArg }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authZPolicy", name = "listResourcesRequest")
    public JAXBElement<EmptyArg> createListResourcesRequest(EmptyArg value) {
        return new JAXBElement<EmptyArg>(_ListResourcesRequest_QNAME, EmptyArg.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EmptyArg }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authZPolicy", name = "removeAuthResponse")
    public JAXBElement<EmptyArg> createRemoveAuthResponse(EmptyArg value) {
        return new JAXBElement<EmptyArg>(_RemoveAuthResponse_QNAME, EmptyArg.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EmptyArg }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authZPolicy", name = "addAttrResponse")
    public JAXBElement<EmptyArg> createAddAttrResponse(EmptyArg value) {
        return new JAXBElement<EmptyArg>(_AddAttrResponse_QNAME, EmptyArg.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListAttrsReply }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authZPolicy", name = "listAttrsResponse")
    public JAXBElement<ListAttrsReply> createListAttrsResponse(ListAttrsReply value) {
        return new JAXBElement<ListAttrsReply>(_ListAttrsResponse_QNAME, ListAttrsReply.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AttrDetails }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authZPolicy", name = "addAttrRequest")
    public JAXBElement<AttrDetails> createAddAttrRequest(AttrDetails value) {
        return new JAXBElement<AttrDetails>(_AddAttrRequest_QNAME, AttrDetails.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AuthDetails }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authZPolicy", name = "addAuthRequest")
    public JAXBElement<AuthDetails> createAddAuthRequest(AuthDetails value) {
        return new JAXBElement<AuthDetails>(_AddAuthRequest_QNAME, AuthDetails.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListPermissionsReply }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authZPolicy", name = "listPermissionsResponse")
    public JAXBElement<ListPermissionsReply> createListPermissionsResponse(ListPermissionsReply value) {
        return new JAXBElement<ListPermissionsReply>(_ListPermissionsResponse_QNAME, ListPermissionsReply.class, null, value);
    }

}

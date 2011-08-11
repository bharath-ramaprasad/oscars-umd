
package net.es.oscars.authN.soap.gen.policy;

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
 * generated in the net.es.oscars.authN.soap.gen.policy package. 
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

    private final static QName _AddUserResponse_QNAME = new QName("http://oscars.es.net/OSCARS/authNPolicy", "addUserResponse");
    private final static QName _RemoveAttrResponse_QNAME = new QName("http://oscars.es.net/OSCARS/authNPolicy", "removeAttrResponse");
    private final static QName _QueryUserRequest_QNAME = new QName("http://oscars.es.net/OSCARS/authNPolicy", "queryUserRequest");
    private final static QName _ListInstsRequest_QNAME = new QName("http://oscars.es.net/OSCARS/authNPolicy", "listInstsRequest");
    private final static QName _QueryUserResponse_QNAME = new QName("http://oscars.es.net/OSCARS/authNPolicy", "queryUserResponse");
    private final static QName _ModifyInstResponse_QNAME = new QName("http://oscars.es.net/OSCARS/authNPolicy", "modifyInstResponse");
    private final static QName _AddUserRequest_QNAME = new QName("http://oscars.es.net/OSCARS/authNPolicy", "addUserRequest");
    private final static QName _AddAttrRequest_QNAME = new QName("http://oscars.es.net/OSCARS/authNPolicy", "addAttrRequest");
    private final static QName _RemoveInstResponse_QNAME = new QName("http://oscars.es.net/OSCARS/authNPolicy", "removeInstResponse");
    private final static QName _ModifyAttrResponse_QNAME = new QName("http://oscars.es.net/OSCARS/authNPolicy", "modifyAttrResponse");
    private final static QName _RemoveInstRequest_QNAME = new QName("http://oscars.es.net/OSCARS/authNPolicy", "removeInstRequest");
    private final static QName _RemoveUserRequest_QNAME = new QName("http://oscars.es.net/OSCARS/authNPolicy", "removeUserRequest");
    private final static QName _SetSessionResponse_QNAME = new QName("http://oscars.es.net/OSCARS/authNPolicy", "setSessionResponse");
    private final static QName _ListAttrsRequest_QNAME = new QName("http://oscars.es.net/OSCARS/authNPolicy", "listAttrsRequest");
    private final static QName _AddInstRequest_QNAME = new QName("http://oscars.es.net/OSCARS/authNPolicy", "addInstRequest");
    private final static QName _ListAttrsResponse_QNAME = new QName("http://oscars.es.net/OSCARS/authNPolicy", "listAttrsResponse");
    private final static QName _ModifyUserResponse_QNAME = new QName("http://oscars.es.net/OSCARS/authNPolicy", "modifyUserResponse");
    private final static QName _ValidSessionResponse_QNAME = new QName("http://oscars.es.net/OSCARS/authNPolicy", "validSessionResponse");
    private final static QName _RemoveUserResponse_QNAME = new QName("http://oscars.es.net/OSCARS/authNPolicy", "removeUserResponse");
    private final static QName _ModifyAttrRequest_QNAME = new QName("http://oscars.es.net/OSCARS/authNPolicy", "modifyAttrRequest");
    private final static QName _ListInstsResponse_QNAME = new QName("http://oscars.es.net/OSCARS/authNPolicy", "listInstsResponse");
    private final static QName _ModifyUserRequest_QNAME = new QName("http://oscars.es.net/OSCARS/authNPolicy", "modifyUserRequest");
    private final static QName _SetSessionRequest_QNAME = new QName("http://oscars.es.net/OSCARS/authNPolicy", "setSessionRequest");
    private final static QName _ListUsersRequest_QNAME = new QName("http://oscars.es.net/OSCARS/authNPolicy", "listUsersRequest");
    private final static QName _ValidSessionRequest_QNAME = new QName("http://oscars.es.net/OSCARS/authNPolicy", "validSessionRequest");
    private final static QName _AddInstResponse_QNAME = new QName("http://oscars.es.net/OSCARS/authNPolicy", "addInstResponse");
    private final static QName _ListUsersResponse_QNAME = new QName("http://oscars.es.net/OSCARS/authNPolicy", "listUsersResponse");
    private final static QName _RemoveAttrRequest_QNAME = new QName("http://oscars.es.net/OSCARS/authNPolicy", "removeAttrRequest");
    private final static QName _AddAttrResponse_QNAME = new QName("http://oscars.es.net/OSCARS/authNPolicy", "addAttrResponse");
    private final static QName _ModifyInstRequest_QNAME = new QName("http://oscars.es.net/OSCARS/authNPolicy", "modifyInstRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: net.es.oscars.authN.soap.gen.policy
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ListUsersReply }
     * 
     */
    public ListUsersReply createListUsersReply() {
        return new ListUsersReply();
    }

    /**
     * Create an instance of {@link ListUsersParams }
     * 
     */
    public ListUsersParams createListUsersParams() {
        return new ListUsersParams();
    }

    /**
     * Create an instance of {@link ModifyInstParams }
     * 
     */
    public ModifyInstParams createModifyInstParams() {
        return new ModifyInstParams();
    }

    /**
     * Create an instance of {@link ListInstsReply }
     * 
     */
    public ListInstsReply createListInstsReply() {
        return new ListInstsReply();
    }

    /**
     * Create an instance of {@link SessionOpParams }
     * 
     */
    public SessionOpParams createSessionOpParams() {
        return new SessionOpParams();
    }

    /**
     * Create an instance of {@link AttrReply }
     * 
     */
    public AttrReply createAttrReply() {
        return new AttrReply();
    }

    /**
     * Create an instance of {@link QueryUserReply }
     * 
     */
    public QueryUserReply createQueryUserReply() {
        return new QueryUserReply();
    }

    /**
     * Create an instance of {@link UserDetails }
     * 
     */
    public UserDetails createUserDetails() {
        return new UserDetails();
    }

    /**
     * Create an instance of {@link FullUserParams }
     * 
     */
    public FullUserParams createFullUserParams() {
        return new FullUserParams();
    }

    /**
     * Create an instance of {@link ListAttrsRequest }
     * 
     */
    public ListAttrsRequest createListAttrsRequest() {
        return new ListAttrsRequest();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EmptyArg }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authNPolicy", name = "addUserResponse")
    public JAXBElement<EmptyArg> createAddUserResponse(EmptyArg value) {
        return new JAXBElement<EmptyArg>(_AddUserResponse_QNAME, EmptyArg.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EmptyArg }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authNPolicy", name = "removeAttrResponse")
    public JAXBElement<EmptyArg> createRemoveAttrResponse(EmptyArg value) {
        return new JAXBElement<EmptyArg>(_RemoveAttrResponse_QNAME, EmptyArg.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authNPolicy", name = "queryUserRequest")
    public JAXBElement<String> createQueryUserRequest(String value) {
        return new JAXBElement<String>(_QueryUserRequest_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EmptyArg }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authNPolicy", name = "listInstsRequest")
    public JAXBElement<EmptyArg> createListInstsRequest(EmptyArg value) {
        return new JAXBElement<EmptyArg>(_ListInstsRequest_QNAME, EmptyArg.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryUserReply }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authNPolicy", name = "queryUserResponse")
    public JAXBElement<QueryUserReply> createQueryUserResponse(QueryUserReply value) {
        return new JAXBElement<QueryUserReply>(_QueryUserResponse_QNAME, QueryUserReply.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EmptyArg }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authNPolicy", name = "modifyInstResponse")
    public JAXBElement<EmptyArg> createModifyInstResponse(EmptyArg value) {
        return new JAXBElement<EmptyArg>(_ModifyInstResponse_QNAME, EmptyArg.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FullUserParams }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authNPolicy", name = "addUserRequest")
    public JAXBElement<FullUserParams> createAddUserRequest(FullUserParams value) {
        return new JAXBElement<FullUserParams>(_AddUserRequest_QNAME, FullUserParams.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AttrDetails }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authNPolicy", name = "addAttrRequest")
    public JAXBElement<AttrDetails> createAddAttrRequest(AttrDetails value) {
        return new JAXBElement<AttrDetails>(_AddAttrRequest_QNAME, AttrDetails.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EmptyArg }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authNPolicy", name = "removeInstResponse")
    public JAXBElement<EmptyArg> createRemoveInstResponse(EmptyArg value) {
        return new JAXBElement<EmptyArg>(_RemoveInstResponse_QNAME, EmptyArg.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EmptyArg }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authNPolicy", name = "modifyAttrResponse")
    public JAXBElement<EmptyArg> createModifyAttrResponse(EmptyArg value) {
        return new JAXBElement<EmptyArg>(_ModifyAttrResponse_QNAME, EmptyArg.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authNPolicy", name = "removeInstRequest")
    public JAXBElement<String> createRemoveInstRequest(String value) {
        return new JAXBElement<String>(_RemoveInstRequest_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authNPolicy", name = "removeUserRequest")
    public JAXBElement<String> createRemoveUserRequest(String value) {
        return new JAXBElement<String>(_RemoveUserRequest_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EmptyArg }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authNPolicy", name = "setSessionResponse")
    public JAXBElement<EmptyArg> createSetSessionResponse(EmptyArg value) {
        return new JAXBElement<EmptyArg>(_SetSessionResponse_QNAME, EmptyArg.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListAttrsRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authNPolicy", name = "listAttrsRequest")
    public JAXBElement<ListAttrsRequest> createListAttrsRequest(ListAttrsRequest value) {
        return new JAXBElement<ListAttrsRequest>(_ListAttrsRequest_QNAME, ListAttrsRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authNPolicy", name = "addInstRequest")
    public JAXBElement<String> createAddInstRequest(String value) {
        return new JAXBElement<String>(_AddInstRequest_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListAttrsReply }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authNPolicy", name = "listAttrsResponse")
    public JAXBElement<ListAttrsReply> createListAttrsResponse(ListAttrsReply value) {
        return new JAXBElement<ListAttrsReply>(_ListAttrsResponse_QNAME, ListAttrsReply.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EmptyArg }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authNPolicy", name = "modifyUserResponse")
    public JAXBElement<EmptyArg> createModifyUserResponse(EmptyArg value) {
        return new JAXBElement<EmptyArg>(_ModifyUserResponse_QNAME, EmptyArg.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AttrReply }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authNPolicy", name = "validSessionResponse")
    public JAXBElement<AttrReply> createValidSessionResponse(AttrReply value) {
        return new JAXBElement<AttrReply>(_ValidSessionResponse_QNAME, AttrReply.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EmptyArg }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authNPolicy", name = "removeUserResponse")
    public JAXBElement<EmptyArg> createRemoveUserResponse(EmptyArg value) {
        return new JAXBElement<EmptyArg>(_RemoveUserResponse_QNAME, EmptyArg.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyAttrDetails }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authNPolicy", name = "modifyAttrRequest")
    public JAXBElement<ModifyAttrDetails> createModifyAttrRequest(ModifyAttrDetails value) {
        return new JAXBElement<ModifyAttrDetails>(_ModifyAttrRequest_QNAME, ModifyAttrDetails.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListInstsReply }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authNPolicy", name = "listInstsResponse")
    public JAXBElement<ListInstsReply> createListInstsResponse(ListInstsReply value) {
        return new JAXBElement<ListInstsReply>(_ListInstsResponse_QNAME, ListInstsReply.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FullUserParams }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authNPolicy", name = "modifyUserRequest")
    public JAXBElement<FullUserParams> createModifyUserRequest(FullUserParams value) {
        return new JAXBElement<FullUserParams>(_ModifyUserRequest_QNAME, FullUserParams.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SessionOpParams }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authNPolicy", name = "setSessionRequest")
    public JAXBElement<SessionOpParams> createSetSessionRequest(SessionOpParams value) {
        return new JAXBElement<SessionOpParams>(_SetSessionRequest_QNAME, SessionOpParams.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListUsersParams }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authNPolicy", name = "listUsersRequest")
    public JAXBElement<ListUsersParams> createListUsersRequest(ListUsersParams value) {
        return new JAXBElement<ListUsersParams>(_ListUsersRequest_QNAME, ListUsersParams.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SessionOpParams }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authNPolicy", name = "validSessionRequest")
    public JAXBElement<SessionOpParams> createValidSessionRequest(SessionOpParams value) {
        return new JAXBElement<SessionOpParams>(_ValidSessionRequest_QNAME, SessionOpParams.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EmptyArg }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authNPolicy", name = "addInstResponse")
    public JAXBElement<EmptyArg> createAddInstResponse(EmptyArg value) {
        return new JAXBElement<EmptyArg>(_AddInstResponse_QNAME, EmptyArg.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListUsersReply }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authNPolicy", name = "listUsersResponse")
    public JAXBElement<ListUsersReply> createListUsersResponse(ListUsersReply value) {
        return new JAXBElement<ListUsersReply>(_ListUsersResponse_QNAME, ListUsersReply.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authNPolicy", name = "removeAttrRequest")
    public JAXBElement<String> createRemoveAttrRequest(String value) {
        return new JAXBElement<String>(_RemoveAttrRequest_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EmptyArg }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authNPolicy", name = "addAttrResponse")
    public JAXBElement<EmptyArg> createAddAttrResponse(EmptyArg value) {
        return new JAXBElement<EmptyArg>(_AddAttrResponse_QNAME, EmptyArg.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyInstParams }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/authNPolicy", name = "modifyInstRequest")
    public JAXBElement<ModifyInstParams> createModifyInstRequest(ModifyInstParams value) {
        return new JAXBElement<ModifyInstParams>(_ModifyInstRequest_QNAME, ModifyInstParams.class, null, value);
    }

}

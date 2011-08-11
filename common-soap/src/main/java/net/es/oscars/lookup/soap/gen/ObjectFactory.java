
package net.es.oscars.lookup.soap.gen;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the net.es.oscars.lookup.soap.gen package. 
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

    private final static QName _AdminViewRegistrationsRequest_QNAME = new QName("http://oscars.es.net/OSCARS/lookup/admin", "adminViewRegistrationsRequest");
    private final static QName _AdminViewRegistrationsResponse_QNAME = new QName("http://oscars.es.net/OSCARS/lookup/admin", "adminViewRegistrationsResponse");
    private final static QName _AdminAddCacheEntryRequest_QNAME = new QName("http://oscars.es.net/OSCARS/lookup/admin", "adminAddCacheEntryRequest");
    private final static QName _AdminModifyCacheEntryResponse_QNAME = new QName("http://oscars.es.net/OSCARS/lookup/admin", "adminModifyCacheEntryResponse");
    private final static QName _RegisterResponse_QNAME = new QName("http://oscars.es.net/OSCARS/lookup", "registerResponse");
    private final static QName _AdminAddRegistrationRequest_QNAME = new QName("http://oscars.es.net/OSCARS/lookup/admin", "adminAddRegistrationRequest");
    private final static QName _LookupResponse_QNAME = new QName("http://oscars.es.net/OSCARS/lookup", "lookupResponse");
    private final static QName _AdminModifyRegistrationRequest_QNAME = new QName("http://oscars.es.net/OSCARS/lookup/admin", "adminModifyRegistrationRequest");
    private final static QName _AdminModifyCacheEntryRequest_QNAME = new QName("http://oscars.es.net/OSCARS/lookup/admin", "adminModifyCacheEntryRequest");
    private final static QName _AdminViewCacheResponse_QNAME = new QName("http://oscars.es.net/OSCARS/lookup/admin", "adminViewCacheResponse");
    private final static QName _LookupRequest_QNAME = new QName("http://oscars.es.net/OSCARS/lookup", "lookupRequest");
    private final static QName _AdminViewCacheRequest_QNAME = new QName("http://oscars.es.net/OSCARS/lookup/admin", "adminViewCacheRequest");
    private final static QName _RegisterRequest_QNAME = new QName("http://oscars.es.net/OSCARS/lookup", "registerRequest");
    private final static QName _AdminDeleteCacheEntryRequest_QNAME = new QName("http://oscars.es.net/OSCARS/lookup/admin", "adminDeleteCacheEntryRequest");
    private final static QName _AdminAddCacheEntryResponse_QNAME = new QName("http://oscars.es.net/OSCARS/lookup/admin", "adminAddCacheEntryResponse");
    private final static QName _AdminDeleteCacheEntryResponse_QNAME = new QName("http://oscars.es.net/OSCARS/lookup/admin", "adminDeleteCacheEntryResponse");
    private final static QName _AdminDeleteRegistrationRequest_QNAME = new QName("http://oscars.es.net/OSCARS/lookup/admin", "adminDeleteRegistrationRequest");
    private final static QName _AdminModifyRegistrationResponse_QNAME = new QName("http://oscars.es.net/OSCARS/lookup/admin", "adminModifyRegistrationResponse");
    private final static QName _AdminDeleteRegistrationResponse_QNAME = new QName("http://oscars.es.net/OSCARS/lookup/admin", "adminDeleteRegistrationResponse");
    private final static QName _AdminAddRegistrationResponse_QNAME = new QName("http://oscars.es.net/OSCARS/lookup/admin", "adminAddRegistrationResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: net.es.oscars.lookup.soap.gen
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AddRegistrationRequestType }
     * 
     */
    public AddRegistrationRequestType createAddRegistrationRequestType() {
        return new AddRegistrationRequestType();
    }

    /**
     * Create an instance of {@link ModifyCacheEntryRequestType }
     * 
     */
    public ModifyCacheEntryRequestType createModifyCacheEntryRequestType() {
        return new ModifyCacheEntryRequestType();
    }

    /**
     * Create an instance of {@link RegisterResponseContent }
     * 
     */
    public RegisterResponseContent createRegisterResponseContent() {
        return new RegisterResponseContent();
    }

    /**
     * Create an instance of {@link LookupFault }
     * 
     */
    public LookupFault createLookupFault() {
        return new LookupFault();
    }

    /**
     * Create an instance of {@link GeoLocation }
     * 
     */
    public GeoLocation createGeoLocation() {
        return new GeoLocation();
    }

    /**
     * Create an instance of {@link LookupResponseContent }
     * 
     */
    public LookupResponseContent createLookupResponseContent() {
        return new LookupResponseContent();
    }

    /**
     * Create an instance of {@link AdminSuccessResponseType }
     * 
     */
    public AdminSuccessResponseType createAdminSuccessResponseType() {
        return new AdminSuccessResponseType();
    }

    /**
     * Create an instance of {@link Protocol }
     * 
     */
    public Protocol createProtocol() {
        return new Protocol();
    }

    /**
     * Create an instance of {@link RegistrationType }
     * 
     */
    public RegistrationType createRegistrationType() {
        return new RegistrationType();
    }

    /**
     * Create an instance of {@link AddCacheEntryRequestType }
     * 
     */
    public AddCacheEntryRequestType createAddCacheEntryRequestType() {
        return new AddCacheEntryRequestType();
    }

    /**
     * Create an instance of {@link AdminViewRequestType }
     * 
     */
    public AdminViewRequestType createAdminViewRequestType() {
        return new AdminViewRequestType();
    }

    /**
     * Create an instance of {@link ViewRegistrationsResponseType }
     * 
     */
    public ViewRegistrationsResponseType createViewRegistrationsResponseType() {
        return new ViewRegistrationsResponseType();
    }

    /**
     * Create an instance of {@link DeleteCacheEntryRequestType }
     * 
     */
    public DeleteCacheEntryRequestType createDeleteCacheEntryRequestType() {
        return new DeleteCacheEntryRequestType();
    }

    /**
     * Create an instance of {@link ViewCacheResponseType }
     * 
     */
    public ViewCacheResponseType createViewCacheResponseType() {
        return new ViewCacheResponseType();
    }

    /**
     * Create an instance of {@link RegisterRequestContent }
     * 
     */
    public RegisterRequestContent createRegisterRequestContent() {
        return new RegisterRequestContent();
    }

    /**
     * Create an instance of {@link ServiceType }
     * 
     */
    public ServiceType createServiceType() {
        return new ServiceType();
    }

    /**
     * Create an instance of {@link DeleteRegistrationRequestType }
     * 
     */
    public DeleteRegistrationRequestType createDeleteRegistrationRequestType() {
        return new DeleteRegistrationRequestType();
    }

    /**
     * Create an instance of {@link ModifyRegistrationRequestType }
     * 
     */
    public ModifyRegistrationRequestType createModifyRegistrationRequestType() {
        return new ModifyRegistrationRequestType();
    }

    /**
     * Create an instance of {@link LookupRequestContent }
     * 
     */
    public LookupRequestContent createLookupRequestContent() {
        return new LookupRequestContent();
    }

    /**
     * Create an instance of {@link Relationship }
     * 
     */
    public Relationship createRelationship() {
        return new Relationship();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AdminViewRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/lookup/admin", name = "adminViewRegistrationsRequest")
    public JAXBElement<AdminViewRequestType> createAdminViewRegistrationsRequest(AdminViewRequestType value) {
        return new JAXBElement<AdminViewRequestType>(_AdminViewRegistrationsRequest_QNAME, AdminViewRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ViewRegistrationsResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/lookup/admin", name = "adminViewRegistrationsResponse")
    public JAXBElement<ViewRegistrationsResponseType> createAdminViewRegistrationsResponse(ViewRegistrationsResponseType value) {
        return new JAXBElement<ViewRegistrationsResponseType>(_AdminViewRegistrationsResponse_QNAME, ViewRegistrationsResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddCacheEntryRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/lookup/admin", name = "adminAddCacheEntryRequest")
    public JAXBElement<AddCacheEntryRequestType> createAdminAddCacheEntryRequest(AddCacheEntryRequestType value) {
        return new JAXBElement<AddCacheEntryRequestType>(_AdminAddCacheEntryRequest_QNAME, AddCacheEntryRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AdminSuccessResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/lookup/admin", name = "adminModifyCacheEntryResponse")
    public JAXBElement<AdminSuccessResponseType> createAdminModifyCacheEntryResponse(AdminSuccessResponseType value) {
        return new JAXBElement<AdminSuccessResponseType>(_AdminModifyCacheEntryResponse_QNAME, AdminSuccessResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegisterResponseContent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/lookup", name = "registerResponse")
    public JAXBElement<RegisterResponseContent> createRegisterResponse(RegisterResponseContent value) {
        return new JAXBElement<RegisterResponseContent>(_RegisterResponse_QNAME, RegisterResponseContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddRegistrationRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/lookup/admin", name = "adminAddRegistrationRequest")
    public JAXBElement<AddRegistrationRequestType> createAdminAddRegistrationRequest(AddRegistrationRequestType value) {
        return new JAXBElement<AddRegistrationRequestType>(_AdminAddRegistrationRequest_QNAME, AddRegistrationRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LookupResponseContent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/lookup", name = "lookupResponse")
    public JAXBElement<LookupResponseContent> createLookupResponse(LookupResponseContent value) {
        return new JAXBElement<LookupResponseContent>(_LookupResponse_QNAME, LookupResponseContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyRegistrationRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/lookup/admin", name = "adminModifyRegistrationRequest")
    public JAXBElement<ModifyRegistrationRequestType> createAdminModifyRegistrationRequest(ModifyRegistrationRequestType value) {
        return new JAXBElement<ModifyRegistrationRequestType>(_AdminModifyRegistrationRequest_QNAME, ModifyRegistrationRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyCacheEntryRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/lookup/admin", name = "adminModifyCacheEntryRequest")
    public JAXBElement<ModifyCacheEntryRequestType> createAdminModifyCacheEntryRequest(ModifyCacheEntryRequestType value) {
        return new JAXBElement<ModifyCacheEntryRequestType>(_AdminModifyCacheEntryRequest_QNAME, ModifyCacheEntryRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ViewCacheResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/lookup/admin", name = "adminViewCacheResponse")
    public JAXBElement<ViewCacheResponseType> createAdminViewCacheResponse(ViewCacheResponseType value) {
        return new JAXBElement<ViewCacheResponseType>(_AdminViewCacheResponse_QNAME, ViewCacheResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LookupRequestContent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/lookup", name = "lookupRequest")
    public JAXBElement<LookupRequestContent> createLookupRequest(LookupRequestContent value) {
        return new JAXBElement<LookupRequestContent>(_LookupRequest_QNAME, LookupRequestContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AdminViewRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/lookup/admin", name = "adminViewCacheRequest")
    public JAXBElement<AdminViewRequestType> createAdminViewCacheRequest(AdminViewRequestType value) {
        return new JAXBElement<AdminViewRequestType>(_AdminViewCacheRequest_QNAME, AdminViewRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegisterRequestContent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/lookup", name = "registerRequest")
    public JAXBElement<RegisterRequestContent> createRegisterRequest(RegisterRequestContent value) {
        return new JAXBElement<RegisterRequestContent>(_RegisterRequest_QNAME, RegisterRequestContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteCacheEntryRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/lookup/admin", name = "adminDeleteCacheEntryRequest")
    public JAXBElement<DeleteCacheEntryRequestType> createAdminDeleteCacheEntryRequest(DeleteCacheEntryRequestType value) {
        return new JAXBElement<DeleteCacheEntryRequestType>(_AdminDeleteCacheEntryRequest_QNAME, DeleteCacheEntryRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AdminSuccessResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/lookup/admin", name = "adminAddCacheEntryResponse")
    public JAXBElement<AdminSuccessResponseType> createAdminAddCacheEntryResponse(AdminSuccessResponseType value) {
        return new JAXBElement<AdminSuccessResponseType>(_AdminAddCacheEntryResponse_QNAME, AdminSuccessResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AdminSuccessResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/lookup/admin", name = "adminDeleteCacheEntryResponse")
    public JAXBElement<AdminSuccessResponseType> createAdminDeleteCacheEntryResponse(AdminSuccessResponseType value) {
        return new JAXBElement<AdminSuccessResponseType>(_AdminDeleteCacheEntryResponse_QNAME, AdminSuccessResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteRegistrationRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/lookup/admin", name = "adminDeleteRegistrationRequest")
    public JAXBElement<DeleteRegistrationRequestType> createAdminDeleteRegistrationRequest(DeleteRegistrationRequestType value) {
        return new JAXBElement<DeleteRegistrationRequestType>(_AdminDeleteRegistrationRequest_QNAME, DeleteRegistrationRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AdminSuccessResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/lookup/admin", name = "adminModifyRegistrationResponse")
    public JAXBElement<AdminSuccessResponseType> createAdminModifyRegistrationResponse(AdminSuccessResponseType value) {
        return new JAXBElement<AdminSuccessResponseType>(_AdminModifyRegistrationResponse_QNAME, AdminSuccessResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AdminSuccessResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/lookup/admin", name = "adminDeleteRegistrationResponse")
    public JAXBElement<AdminSuccessResponseType> createAdminDeleteRegistrationResponse(AdminSuccessResponseType value) {
        return new JAXBElement<AdminSuccessResponseType>(_AdminDeleteRegistrationResponse_QNAME, AdminSuccessResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AdminSuccessResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS/lookup/admin", name = "adminAddRegistrationResponse")
    public JAXBElement<AdminSuccessResponseType> createAdminAddRegistrationResponse(AdminSuccessResponseType value) {
        return new JAXBElement<AdminSuccessResponseType>(_AdminAddRegistrationResponse_QNAME, AdminSuccessResponseType.class, null, value);
    }

}

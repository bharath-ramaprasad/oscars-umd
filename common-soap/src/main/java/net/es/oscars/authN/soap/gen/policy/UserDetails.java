
package net.es.oscars.authN.soap.gen.policy;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for userDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="userDetails">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="login" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="certIssuer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certSubject" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lastName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="firstName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="emailPrimary" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="phonePrimary" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="emailSecondary" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="phoneSecondary" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="institution" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "userDetails", propOrder = {
    "login",
    "certIssuer",
    "certSubject",
    "lastName",
    "firstName",
    "emailPrimary",
    "phonePrimary",
    "password",
    "description",
    "emailSecondary",
    "phoneSecondary",
    "institution"
})
public class UserDetails {

    @XmlElement(required = true)
    protected String login;
    protected String certIssuer;
    protected String certSubject;
    @XmlElement(required = true)
    protected String lastName;
    @XmlElement(required = true)
    protected String firstName;
    @XmlElement(required = true)
    protected String emailPrimary;
    @XmlElement(required = true)
    protected String phonePrimary;
    protected String password;
    protected String description;
    protected String emailSecondary;
    protected String phoneSecondary;
    @XmlElement(required = true)
    protected String institution;

    /**
     * Gets the value of the login property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets the value of the login property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogin(String value) {
        this.login = value;
    }

    /**
     * Gets the value of the certIssuer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertIssuer() {
        return certIssuer;
    }

    /**
     * Sets the value of the certIssuer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertIssuer(String value) {
        this.certIssuer = value;
    }

    /**
     * Gets the value of the certSubject property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertSubject() {
        return certSubject;
    }

    /**
     * Sets the value of the certSubject property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertSubject(String value) {
        this.certSubject = value;
    }

    /**
     * Gets the value of the lastName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the value of the lastName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastName(String value) {
        this.lastName = value;
    }

    /**
     * Gets the value of the firstName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the value of the firstName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstName(String value) {
        this.firstName = value;
    }

    /**
     * Gets the value of the emailPrimary property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailPrimary() {
        return emailPrimary;
    }

    /**
     * Sets the value of the emailPrimary property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailPrimary(String value) {
        this.emailPrimary = value;
    }

    /**
     * Gets the value of the phonePrimary property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhonePrimary() {
        return phonePrimary;
    }

    /**
     * Sets the value of the phonePrimary property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhonePrimary(String value) {
        this.phonePrimary = value;
    }

    /**
     * Gets the value of the password property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the value of the password property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the emailSecondary property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailSecondary() {
        return emailSecondary;
    }

    /**
     * Sets the value of the emailSecondary property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailSecondary(String value) {
        this.emailSecondary = value;
    }

    /**
     * Gets the value of the phoneSecondary property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhoneSecondary() {
        return phoneSecondary;
    }

    /**
     * Sets the value of the phoneSecondary property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhoneSecondary(String value) {
        this.phoneSecondary = value;
    }

    /**
     * Gets the value of the institution property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstitution() {
        return institution;
    }

    /**
     * Sets the value of the institution property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstitution(String value) {
        this.institution = value;
    }

}

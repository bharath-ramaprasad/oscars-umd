
package net.es.oscars.authN.soap.gen.policy;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for queryUserReply complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="queryUserReply">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="userDetails" type="{http://oscars.es.net/OSCARS/authNPolicy}userDetails"/>
 *         &lt;element name="userAttributes" type="{http://oscars.es.net/OSCARS/authNPolicy}attrReply"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "queryUserReply", propOrder = {
    "userDetails",
    "userAttributes"
})
public class QueryUserReply {

    @XmlElement(required = true)
    protected UserDetails userDetails;
    @XmlElement(required = true)
    protected AttrReply userAttributes;

    /**
     * Gets the value of the userDetails property.
     * 
     * @return
     *     possible object is
     *     {@link UserDetails }
     *     
     */
    public UserDetails getUserDetails() {
        return userDetails;
    }

    /**
     * Sets the value of the userDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserDetails }
     *     
     */
    public void setUserDetails(UserDetails value) {
        this.userDetails = value;
    }

    /**
     * Gets the value of the userAttributes property.
     * 
     * @return
     *     possible object is
     *     {@link AttrReply }
     *     
     */
    public AttrReply getUserAttributes() {
        return userAttributes;
    }

    /**
     * Sets the value of the userAttributes property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttrReply }
     *     
     */
    public void setUserAttributes(AttrReply value) {
        this.userAttributes = value;
    }

}

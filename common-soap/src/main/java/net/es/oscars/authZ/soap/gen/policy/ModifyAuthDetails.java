
package net.es.oscars.authZ.soap.gen.policy;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for modifyAuthDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="modifyAuthDetails">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="oldAuthInfo" type="{http://oscars.es.net/OSCARS/authZPolicy}authDetails"/>
 *         &lt;element name="modAuthInfo" type="{http://oscars.es.net/OSCARS/authZPolicy}authDetails"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "modifyAuthDetails", propOrder = {
    "oldAuthInfo",
    "modAuthInfo"
})
public class ModifyAuthDetails {

    @XmlElement(required = true)
    protected AuthDetails oldAuthInfo;
    @XmlElement(required = true)
    protected AuthDetails modAuthInfo;

    /**
     * Gets the value of the oldAuthInfo property.
     * 
     * @return
     *     possible object is
     *     {@link AuthDetails }
     *     
     */
    public AuthDetails getOldAuthInfo() {
        return oldAuthInfo;
    }

    /**
     * Sets the value of the oldAuthInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuthDetails }
     *     
     */
    public void setOldAuthInfo(AuthDetails value) {
        this.oldAuthInfo = value;
    }

    /**
     * Gets the value of the modAuthInfo property.
     * 
     * @return
     *     possible object is
     *     {@link AuthDetails }
     *     
     */
    public AuthDetails getModAuthInfo() {
        return modAuthInfo;
    }

    /**
     * Sets the value of the modAuthInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuthDetails }
     *     
     */
    public void setModAuthInfo(AuthDetails value) {
        this.modAuthInfo = value;
    }

}

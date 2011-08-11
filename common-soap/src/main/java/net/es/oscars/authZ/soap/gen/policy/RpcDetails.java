
package net.es.oscars.authZ.soap.gen.policy;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for rpcDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="rpcDetails">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="resourceName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="permissionName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="constraintName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="constraintType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rpcDetails", propOrder = {
    "resourceName",
    "permissionName",
    "constraintName",
    "constraintType"
})
public class RpcDetails {

    @XmlElement(required = true)
    protected String resourceName;
    @XmlElement(required = true)
    protected String permissionName;
    @XmlElement(required = true)
    protected String constraintName;
    @XmlElement(required = true)
    protected String constraintType;

    /**
     * Gets the value of the resourceName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResourceName() {
        return resourceName;
    }

    /**
     * Sets the value of the resourceName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResourceName(String value) {
        this.resourceName = value;
    }

    /**
     * Gets the value of the permissionName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPermissionName() {
        return permissionName;
    }

    /**
     * Sets the value of the permissionName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPermissionName(String value) {
        this.permissionName = value;
    }

    /**
     * Gets the value of the constraintName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConstraintName() {
        return constraintName;
    }

    /**
     * Sets the value of the constraintName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConstraintName(String value) {
        this.constraintName = value;
    }

    /**
     * Gets the value of the constraintType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConstraintType() {
        return constraintType;
    }

    /**
     * Sets the value of the constraintType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConstraintType(String value) {
        this.constraintType = value;
    }

}

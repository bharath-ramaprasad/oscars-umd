
package net.es.oscars.authZ.soap.gen;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import net.es.oscars.common.soap.gen.SubjectAttributes;


/**
 * <p>Java class for checkAccessParams complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="checkAccessParams">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="transactionId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="subjectAttrs" type="{http://oscars.es.net/OSCARS/authParams}subjectAttributes"/>
 *         &lt;element name="resourceName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="permissionName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "checkAccessParams", propOrder = {
    "transactionId",
    "subjectAttrs",
    "resourceName",
    "permissionName"
})
public class CheckAccessParams {

    @XmlElement(required = true)
    protected String transactionId;
    @XmlElement(required = true)
    protected SubjectAttributes subjectAttrs;
    @XmlElement(required = true)
    protected String resourceName;
    @XmlElement(required = true)
    protected String permissionName;

    /**
     * Gets the value of the transactionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * Sets the value of the transactionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransactionId(String value) {
        this.transactionId = value;
    }

    /**
     * Gets the value of the subjectAttrs property.
     * 
     * @return
     *     possible object is
     *     {@link SubjectAttributes }
     *     
     */
    public SubjectAttributes getSubjectAttrs() {
        return subjectAttrs;
    }

    /**
     * Sets the value of the subjectAttrs property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubjectAttributes }
     *     
     */
    public void setSubjectAttrs(SubjectAttributes value) {
        this.subjectAttrs = value;
    }

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

}

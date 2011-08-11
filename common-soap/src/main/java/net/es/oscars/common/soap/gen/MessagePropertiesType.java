
package net.es.oscars.common.soap.gen;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for messagePropertiesType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="messagePropertiesType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="globalTransactionId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="originator" type="{http://oscars.es.net/OSCARS/authParams}subjectAttributes"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "messagePropertiesType", propOrder = {
    "globalTransactionId",
    "originator"
})
public class MessagePropertiesType {

    @XmlElement(required = true)
    protected String globalTransactionId;
    @XmlElement(required = true)
    protected SubjectAttributes originator;

    /**
     * Gets the value of the globalTransactionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGlobalTransactionId() {
        return globalTransactionId;
    }

    /**
     * Sets the value of the globalTransactionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGlobalTransactionId(String value) {
        this.globalTransactionId = value;
    }

    /**
     * Gets the value of the originator property.
     * 
     * @return
     *     possible object is
     *     {@link SubjectAttributes }
     *     
     */
    public SubjectAttributes getOriginator() {
        return originator;
    }

    /**
     * Sets the value of the originator property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubjectAttributes }
     *     
     */
    public void setOriginator(SubjectAttributes value) {
        this.originator = value;
    }

}

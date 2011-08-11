
package net.es.oscars.authN.soap.gen;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import net.es.oscars.common.soap.gen.SubjectAttributes;


/**
 * <p>Java class for verifyReply complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="verifyReply">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="transactionId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="subjectAttributes" type="{http://oscars.es.net/OSCARS/authParams}subjectAttributes"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "verifyReply", propOrder = {
    "transactionId",
    "subjectAttributes"
})
public class VerifyReply {

    @XmlElement(required = true)
    protected String transactionId;
    @XmlElement(required = true)
    protected SubjectAttributes subjectAttributes;

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
     * Gets the value of the subjectAttributes property.
     * 
     * @return
     *     possible object is
     *     {@link SubjectAttributes }
     *     
     */
    public SubjectAttributes getSubjectAttributes() {
        return subjectAttributes;
    }

    /**
     * Sets the value of the subjectAttributes property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubjectAttributes }
     *     
     */
    public void setSubjectAttributes(SubjectAttributes value) {
        this.subjectAttributes = value;
    }

}

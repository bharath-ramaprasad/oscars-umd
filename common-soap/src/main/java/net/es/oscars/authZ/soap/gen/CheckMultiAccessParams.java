
package net.es.oscars.authZ.soap.gen;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import net.es.oscars.common.soap.gen.SubjectAttributes;


/**
 * <p>Java class for checkMultiAccessParams complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="checkMultiAccessParams">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="transactionId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="subjectAttrs" type="{http://oscars.es.net/OSCARS/authParams}subjectAttributes"/>
 *         &lt;element name="reqPermissions" type="{http://oscars.es.net/OSCARS/authZ}reqPermType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "checkMultiAccessParams", propOrder = {
    "transactionId",
    "subjectAttrs",
    "reqPermissions"
})
public class CheckMultiAccessParams {

    @XmlElement(required = true)
    protected String transactionId;
    @XmlElement(required = true)
    protected SubjectAttributes subjectAttrs;
    @XmlElement(required = true)
    protected List<ReqPermType> reqPermissions;

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
     * Gets the value of the reqPermissions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the reqPermissions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReqPermissions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReqPermType }
     * 
     * 
     */
    public List<ReqPermType> getReqPermissions() {
        if (reqPermissions == null) {
            reqPermissions = new ArrayList<ReqPermType>();
        }
        return this.reqPermissions;
    }

}

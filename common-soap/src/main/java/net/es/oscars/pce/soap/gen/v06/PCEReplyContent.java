
package net.es.oscars.pce.soap.gen.v06;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import net.es.oscars.common.soap.gen.MessagePropertiesType;


/**
 * <p>Java class for PCEReplyContent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PCEReplyContent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="messageProperties" type="{http://oscars.es.net/OSCARS/authParams}messagePropertiesType"/>
 *         &lt;element name="globalReservationId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="pceName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="pceData" type="{http://oscars.es.net/OSCARS/PCE/20090922}PCEDataContent" minOccurs="0"/>
 *         &lt;element name="pceError" type="{http://oscars.es.net/OSCARS/PCE/20090922}PCEError" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PCEReplyContent", propOrder = {
    "messageProperties",
    "globalReservationId",
    "pceName",
    "pceData",
    "pceError"
})
public class PCEReplyContent {

    @XmlElement(required = true)
    protected MessagePropertiesType messageProperties;
    @XmlElement(required = true)
    protected String globalReservationId;
    @XmlElement(required = true)
    protected String pceName;
    protected PCEDataContent pceData;
    protected PCEError pceError;
    @XmlAttribute(required = true)
    protected String id;

    /**
     * Gets the value of the messageProperties property.
     * 
     * @return
     *     possible object is
     *     {@link MessagePropertiesType }
     *     
     */
    public MessagePropertiesType getMessageProperties() {
        return messageProperties;
    }

    /**
     * Sets the value of the messageProperties property.
     * 
     * @param value
     *     allowed object is
     *     {@link MessagePropertiesType }
     *     
     */
    public void setMessageProperties(MessagePropertiesType value) {
        this.messageProperties = value;
    }

    /**
     * Gets the value of the globalReservationId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGlobalReservationId() {
        return globalReservationId;
    }

    /**
     * Sets the value of the globalReservationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGlobalReservationId(String value) {
        this.globalReservationId = value;
    }

    /**
     * Gets the value of the pceName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPceName() {
        return pceName;
    }

    /**
     * Sets the value of the pceName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPceName(String value) {
        this.pceName = value;
    }

    /**
     * Gets the value of the pceData property.
     * 
     * @return
     *     possible object is
     *     {@link PCEDataContent }
     *     
     */
    public PCEDataContent getPceData() {
        return pceData;
    }

    /**
     * Sets the value of the pceData property.
     * 
     * @param value
     *     allowed object is
     *     {@link PCEDataContent }
     *     
     */
    public void setPceData(PCEDataContent value) {
        this.pceData = value;
    }

    /**
     * Gets the value of the pceError property.
     * 
     * @return
     *     possible object is
     *     {@link PCEError }
     *     
     */
    public PCEError getPceError() {
        return pceError;
    }

    /**
     * Sets the value of the pceError property.
     * 
     * @param value
     *     allowed object is
     *     {@link PCEError }
     *     
     */
    public void setPceError(PCEError value) {
        this.pceError = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

}


package net.es.oscars.pce.soap.gen.v06;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import net.es.oscars.common.soap.gen.MessagePropertiesType;


/**
 * <p>Java class for AggregatorCreateContent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AggregatorCreateContent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="messageProperties" type="{http://oscars.es.net/OSCARS/authParams}messagePropertiesType"/>
 *         &lt;element name="globalReservationId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="pceName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="callBackEndpoint" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="pceData" type="{http://oscars.es.net/OSCARS/PCE/20090922}TagDataContent" maxOccurs="unbounded"/>
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
@XmlType(name = "AggregatorCreateContent", propOrder = {
    "messageProperties",
    "globalReservationId",
    "pceName",
    "callBackEndpoint",
    "pceData"
})
public class AggregatorCreateContent {

    @XmlElement(required = true)
    protected MessagePropertiesType messageProperties;
    @XmlElement(required = true)
    protected String globalReservationId;
    @XmlElement(required = true)
    protected String pceName;
    @XmlElement(required = true)
    protected String callBackEndpoint;
    @XmlElement(required = true)
    protected List<TagDataContent> pceData;
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
     * Gets the value of the callBackEndpoint property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCallBackEndpoint() {
        return callBackEndpoint;
    }

    /**
     * Sets the value of the callBackEndpoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCallBackEndpoint(String value) {
        this.callBackEndpoint = value;
    }

    /**
     * Gets the value of the pceData property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pceData property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPceData().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TagDataContent }
     * 
     * 
     */
    public List<TagDataContent> getPceData() {
        if (pceData == null) {
            pceData = new ArrayList<TagDataContent>();
        }
        return this.pceData;
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

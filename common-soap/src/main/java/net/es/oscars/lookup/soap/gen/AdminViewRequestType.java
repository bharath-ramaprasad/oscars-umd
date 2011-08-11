
package net.es.oscars.lookup.soap.gen;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import net.es.oscars.common.soap.gen.MessagePropertiesType;


/**
 * <p>Java class for adminViewRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="adminViewRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="messageProperties" type="{http://oscars.es.net/OSCARS/authParams}messagePropertiesType"/>
 *         &lt;element name="maxResults" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="offset" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "adminViewRequestType", propOrder = {
    "messageProperties",
    "maxResults",
    "offset"
})
public class AdminViewRequestType {

    @XmlElement(required = true)
    protected MessagePropertiesType messageProperties;
    protected Integer maxResults;
    protected Integer offset;

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
     * Gets the value of the maxResults property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMaxResults() {
        return maxResults;
    }

    /**
     * Sets the value of the maxResults property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaxResults(Integer value) {
        this.maxResults = value;
    }

    /**
     * Gets the value of the offset property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOffset() {
        return offset;
    }

    /**
     * Sets the value of the offset property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOffset(Integer value) {
        this.offset = value;
    }

}

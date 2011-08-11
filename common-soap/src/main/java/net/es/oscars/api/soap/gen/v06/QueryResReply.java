
package net.es.oscars.api.soap.gen.v06;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import net.es.oscars.common.soap.gen.MessagePropertiesType;


/**
 * <p>Java class for queryResReply complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="queryResReply">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="messageProperties" type="{http://oscars.es.net/OSCARS/authParams}messagePropertiesType" minOccurs="0"/>
 *         &lt;element name="reservationDetails" type="{http://oscars.es.net/OSCARS/06}resDetails"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "queryResReply", propOrder = {
    "messageProperties",
    "reservationDetails"
})
public class QueryResReply {

    protected MessagePropertiesType messageProperties;
    @XmlElement(required = true)
    protected ResDetails reservationDetails;

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
     * Gets the value of the reservationDetails property.
     * 
     * @return
     *     possible object is
     *     {@link ResDetails }
     *     
     */
    public ResDetails getReservationDetails() {
        return reservationDetails;
    }

    /**
     * Sets the value of the reservationDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResDetails }
     *     
     */
    public void setReservationDetails(ResDetails value) {
        this.reservationDetails = value;
    }

}

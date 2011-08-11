
package net.es.oscars.lookup.soap.gen;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import net.es.oscars.common.soap.gen.MessagePropertiesType;


/**
 * <p>Java class for lookupRequestContent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="lookupRequestContent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="messageProperties" type="{http://oscars.es.net/OSCARS/authParams}messagePropertiesType"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;choice>
 *           &lt;element name="hasLocation" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="hasRelationship" type="{http://oscars.es.net/OSCARS/lookup}relationship"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "lookupRequestContent", namespace = "http://oscars.es.net/OSCARS/lookup", propOrder = {
    "messageProperties",
    "type",
    "hasLocation",
    "hasRelationship"
})
public class LookupRequestContent {

    @XmlElement(required = true)
    protected MessagePropertiesType messageProperties;
    @XmlElement(required = true)
    protected String type;
    protected String hasLocation;
    protected Relationship hasRelationship;

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
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the hasLocation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHasLocation() {
        return hasLocation;
    }

    /**
     * Sets the value of the hasLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHasLocation(String value) {
        this.hasLocation = value;
    }

    /**
     * Gets the value of the hasRelationship property.
     * 
     * @return
     *     possible object is
     *     {@link Relationship }
     *     
     */
    public Relationship getHasRelationship() {
        return hasRelationship;
    }

    /**
     * Sets the value of the hasRelationship property.
     * 
     * @param value
     *     allowed object is
     *     {@link Relationship }
     *     
     */
    public void setHasRelationship(Relationship value) {
        this.hasRelationship = value;
    }

}

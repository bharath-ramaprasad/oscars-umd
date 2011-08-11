
package net.es.oscars.lookup.soap.gen;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import net.es.oscars.common.soap.gen.MessagePropertiesType;


/**
 * <p>Java class for addCacheEntryRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="addCacheEntryRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="messageProperties" type="{http://oscars.es.net/OSCARS/authParams}messagePropertiesType"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="expires" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="protocol" type="{http://oscars.es.net/OSCARS/lookup}protocol" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="relationship" type="{http://oscars.es.net/OSCARS/lookup}relationship" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addCacheEntryRequestType", propOrder = {
    "messageProperties",
    "type",
    "expires",
    "protocol",
    "relationship"
})
public class AddCacheEntryRequestType {

    @XmlElement(required = true)
    protected MessagePropertiesType messageProperties;
    protected String type;
    protected boolean expires;
    protected List<Protocol> protocol;
    protected List<Relationship> relationship;

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
     * Gets the value of the expires property.
     * 
     */
    public boolean isExpires() {
        return expires;
    }

    /**
     * Sets the value of the expires property.
     * 
     */
    public void setExpires(boolean value) {
        this.expires = value;
    }

    /**
     * Gets the value of the protocol property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the protocol property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProtocol().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Protocol }
     * 
     * 
     */
    public List<Protocol> getProtocol() {
        if (protocol == null) {
            protocol = new ArrayList<Protocol>();
        }
        return this.protocol;
    }

    /**
     * Gets the value of the relationship property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the relationship property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRelationship().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Relationship }
     * 
     * 
     */
    public List<Relationship> getRelationship() {
        if (relationship == null) {
            relationship = new ArrayList<Relationship>();
        }
        return this.relationship;
    }

}

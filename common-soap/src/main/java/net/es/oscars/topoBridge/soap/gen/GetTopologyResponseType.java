
package net.es.oscars.topoBridge.soap.gen;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import net.es.oscars.common.soap.gen.MessagePropertiesType;
import org.ogf.schema.network.topology.ctrlplane.CtrlPlaneTopologyContent;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="messageProperties" type="{http://oscars.es.net/OSCARS/authParams}messagePropertiesType"/>
 *         &lt;element name="topology" type="{http://ogf.org/schema/network/topology/ctrlPlane/20080828/}CtrlPlaneTopologyContent" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "messageProperties",
    "topology"
})
@XmlRootElement(name = "getTopologyResponseType")
public class GetTopologyResponseType {

    @XmlElement(required = true)
    protected MessagePropertiesType messageProperties;
    protected List<CtrlPlaneTopologyContent> topology;

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
     * Gets the value of the topology property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the topology property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTopology().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CtrlPlaneTopologyContent }
     * 
     * 
     */
    public List<CtrlPlaneTopologyContent> getTopology() {
        if (topology == null) {
            topology = new ArrayList<CtrlPlaneTopologyContent>();
        }
        return this.topology;
    }

}


package net.es.oscars.api.soap.gen.v06;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import net.es.oscars.common.soap.gen.MessagePropertiesType;
import org.ogf.schema.network.topology.ctrlplane.CtrlPlaneTopologyContent;


/**
 * <p>Java class for getTopologyResponseContent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getTopologyResponseContent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="messageProperties" type="{http://oscars.es.net/OSCARS/authParams}messagePropertiesType" minOccurs="0"/>
 *         &lt;element ref="{http://ogf.org/schema/network/topology/ctrlPlane/20080828/}topology"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getTopologyResponseContent", propOrder = {
    "messageProperties",
    "topology"
})
public class GetTopologyResponseContent {

    protected MessagePropertiesType messageProperties;
    @XmlElement(namespace = "http://ogf.org/schema/network/topology/ctrlPlane/20080828/", required = true)
    protected CtrlPlaneTopologyContent topology;

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
     * @return
     *     possible object is
     *     {@link CtrlPlaneTopologyContent }
     *     
     */
    public CtrlPlaneTopologyContent getTopology() {
        return topology;
    }

    /**
     * Sets the value of the topology property.
     * 
     * @param value
     *     allowed object is
     *     {@link CtrlPlaneTopologyContent }
     *     
     */
    public void setTopology(CtrlPlaneTopologyContent value) {
        this.topology = value;
    }

}


package net.es.oscars.resourceManager.soap.gen;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.ogf.schema.network.topology.ctrlplane.CtrlPlaneHopContent;


/**
 * <p>Java class for auditDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="auditDetails">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="urn" type="{http://ogf.org/schema/network/topology/ctrlPlane/20080828/}CtrlPlaneHopContent"/>
 *         &lt;element name="bandwidth" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="startTime" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="endTime" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "auditDetails", propOrder = {
    "urn",
    "bandwidth",
    "startTime",
    "endTime"
})
public class AuditDetails {

    @XmlElement(required = true)
    protected CtrlPlaneHopContent urn;
    protected long bandwidth;
    protected long startTime;
    protected long endTime;

    /**
     * Gets the value of the urn property.
     * 
     * @return
     *     possible object is
     *     {@link CtrlPlaneHopContent }
     *     
     */
    public CtrlPlaneHopContent getUrn() {
        return urn;
    }

    /**
     * Sets the value of the urn property.
     * 
     * @param value
     *     allowed object is
     *     {@link CtrlPlaneHopContent }
     *     
     */
    public void setUrn(CtrlPlaneHopContent value) {
        this.urn = value;
    }

    /**
     * Gets the value of the bandwidth property.
     * 
     */
    public long getBandwidth() {
        return bandwidth;
    }

    /**
     * Sets the value of the bandwidth property.
     * 
     */
    public void setBandwidth(long value) {
        this.bandwidth = value;
    }

    /**
     * Gets the value of the startTime property.
     * 
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * Sets the value of the startTime property.
     * 
     */
    public void setStartTime(long value) {
        this.startTime = value;
    }

    /**
     * Gets the value of the endTime property.
     * 
     */
    public long getEndTime() {
        return endTime;
    }

    /**
     * Sets the value of the endTime property.
     * 
     */
    public void setEndTime(long value) {
        this.endTime = value;
    }

}

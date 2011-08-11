
package net.es.oscars.pce.soap.gen.v06;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import net.es.oscars.api.soap.gen.v06.OptionalConstraintType;
import net.es.oscars.api.soap.gen.v06.ReservedConstraintType;
import net.es.oscars.api.soap.gen.v06.UserRequestConstraintType;
import org.ogf.schema.network.topology.ctrlplane.CtrlPlaneTopologyContent;


/**
 * <p>Java class for PCEDataContent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PCEDataContent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="userRequestConstraint" type="{http://oscars.es.net/OSCARS/06}userRequestConstraintType"/>
 *         &lt;element name="reservedConstraint" type="{http://oscars.es.net/OSCARS/06}reservedConstraintType" minOccurs="0"/>
 *         &lt;element name="optionalConstraint" type="{http://oscars.es.net/OSCARS/06}optionalConstraintType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="topology" type="{http://ogf.org/schema/network/topology/ctrlPlane/20080828/}CtrlPlaneTopologyContent" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PCEDataContent", propOrder = {
    "userRequestConstraint",
    "reservedConstraint",
    "optionalConstraint",
    "topology"
})
public class PCEDataContent {

    @XmlElement(required = true)
    protected UserRequestConstraintType userRequestConstraint;
    protected ReservedConstraintType reservedConstraint;
    protected List<OptionalConstraintType> optionalConstraint;
    protected CtrlPlaneTopologyContent topology;

    /**
     * Gets the value of the userRequestConstraint property.
     * 
     * @return
     *     possible object is
     *     {@link UserRequestConstraintType }
     *     
     */
    public UserRequestConstraintType getUserRequestConstraint() {
        return userRequestConstraint;
    }

    /**
     * Sets the value of the userRequestConstraint property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserRequestConstraintType }
     *     
     */
    public void setUserRequestConstraint(UserRequestConstraintType value) {
        this.userRequestConstraint = value;
    }

    /**
     * Gets the value of the reservedConstraint property.
     * 
     * @return
     *     possible object is
     *     {@link ReservedConstraintType }
     *     
     */
    public ReservedConstraintType getReservedConstraint() {
        return reservedConstraint;
    }

    /**
     * Sets the value of the reservedConstraint property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReservedConstraintType }
     *     
     */
    public void setReservedConstraint(ReservedConstraintType value) {
        this.reservedConstraint = value;
    }

    /**
     * Gets the value of the optionalConstraint property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the optionalConstraint property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOptionalConstraint().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OptionalConstraintType }
     * 
     * 
     */
    public List<OptionalConstraintType> getOptionalConstraint() {
        if (optionalConstraint == null) {
            optionalConstraint = new ArrayList<OptionalConstraintType>();
        }
        return this.optionalConstraint;
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

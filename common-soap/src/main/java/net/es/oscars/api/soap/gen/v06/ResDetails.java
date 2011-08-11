
package net.es.oscars.api.soap.gen.v06;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for resDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="resDetails">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="globalReservationId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="login" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="createTime" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="userRequestConstraint" type="{http://oscars.es.net/OSCARS/06}userRequestConstraintType"/>
 *         &lt;element name="reservedConstraint" type="{http://oscars.es.net/OSCARS/06}reservedConstraintType" minOccurs="0"/>
 *         &lt;element name="optionalConstraint" type="{http://oscars.es.net/OSCARS/06}optionalConstraintType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resDetails", propOrder = {
    "globalReservationId",
    "login",
    "description",
    "createTime",
    "status",
    "userRequestConstraint",
    "reservedConstraint",
    "optionalConstraint"
})
public class ResDetails {

    @XmlElement(required = true)
    protected String globalReservationId;
    @XmlElement(required = true)
    protected String login;
    @XmlElement(required = true)
    protected String description;
    protected long createTime;
    @XmlElement(required = true)
    protected String status;
    @XmlElement(required = true)
    protected UserRequestConstraintType userRequestConstraint;
    protected ReservedConstraintType reservedConstraint;
    protected List<OptionalConstraintType> optionalConstraint;

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
     * Gets the value of the login property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets the value of the login property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogin(String value) {
        this.login = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the createTime property.
     * 
     */
    public long getCreateTime() {
        return createTime;
    }

    /**
     * Sets the value of the createTime property.
     * 
     */
    public void setCreateTime(long value) {
        this.createTime = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

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

}

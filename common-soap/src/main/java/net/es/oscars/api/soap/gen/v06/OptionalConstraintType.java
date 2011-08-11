
package net.es.oscars.api.soap.gen.v06;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for optionalConstraintType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="optionalConstraintType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="value" type="{http://oscars.es.net/OSCARS/06}optionalConstraintValue"/>
 *       &lt;/sequence>
 *       &lt;attribute name="category" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "optionalConstraintType", propOrder = {
    "value"
})
public class OptionalConstraintType {

    @XmlElement(required = true)
    protected OptionalConstraintValue value;
    @XmlAttribute(required = true)
    protected String category;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link OptionalConstraintValue }
     *     
     */
    public OptionalConstraintValue getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link OptionalConstraintValue }
     *     
     */
    public void setValue(OptionalConstraintValue value) {
        this.value = value;
    }

    /**
     * Gets the value of the category property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the value of the category property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCategory(String value) {
        this.category = value;
    }

}

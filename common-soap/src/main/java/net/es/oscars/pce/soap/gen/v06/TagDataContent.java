
package net.es.oscars.pce.soap.gen.v06;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TagDataContent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TagDataContent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Tag" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Constraints" type="{http://oscars.es.net/OSCARS/PCE/20090922}PCEDataContent"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TagDataContent", propOrder = {
    "tag",
    "constraints"
})
public class TagDataContent {

    @XmlElement(name = "Tag", required = true)
    protected String tag;
    @XmlElement(name = "Constraints", required = true)
    protected PCEDataContent constraints;

    /**
     * Gets the value of the tag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTag() {
        return tag;
    }

    /**
     * Sets the value of the tag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTag(String value) {
        this.tag = value;
    }

    /**
     * Gets the value of the constraints property.
     * 
     * @return
     *     possible object is
     *     {@link PCEDataContent }
     *     
     */
    public PCEDataContent getConstraints() {
        return constraints;
    }

    /**
     * Sets the value of the constraints property.
     * 
     * @param value
     *     allowed object is
     *     {@link PCEDataContent }
     *     
     */
    public void setConstraints(PCEDataContent value) {
        this.constraints = value;
    }

}

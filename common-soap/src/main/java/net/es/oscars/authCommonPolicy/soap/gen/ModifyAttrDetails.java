
package net.es.oscars.authCommonPolicy.soap.gen;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for modifyAttrDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="modifyAttrDetails">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="oldAttrInfo" type="{http://oscars.es.net/OSCARS/authCommonPolicy}attrDetails"/>
 *         &lt;element name="modAttrInfo" type="{http://oscars.es.net/OSCARS/authCommonPolicy}attrDetails"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "modifyAttrDetails", propOrder = {
    "oldAttrInfo",
    "modAttrInfo"
})
public class ModifyAttrDetails {

    @XmlElement(required = true)
    protected AttrDetails oldAttrInfo;
    @XmlElement(required = true)
    protected AttrDetails modAttrInfo;

    /**
     * Gets the value of the oldAttrInfo property.
     * 
     * @return
     *     possible object is
     *     {@link AttrDetails }
     *     
     */
    public AttrDetails getOldAttrInfo() {
        return oldAttrInfo;
    }

    /**
     * Sets the value of the oldAttrInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttrDetails }
     *     
     */
    public void setOldAttrInfo(AttrDetails value) {
        this.oldAttrInfo = value;
    }

    /**
     * Gets the value of the modAttrInfo property.
     * 
     * @return
     *     possible object is
     *     {@link AttrDetails }
     *     
     */
    public AttrDetails getModAttrInfo() {
        return modAttrInfo;
    }

    /**
     * Sets the value of the modAttrInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttrDetails }
     *     
     */
    public void setModAttrInfo(AttrDetails value) {
        this.modAttrInfo = value;
    }

}

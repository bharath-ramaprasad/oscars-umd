
package org.oasis_open.docs.wsrf.bf_2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for ErrorCodeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ErrorCodeType">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *       &lt;attribute name="dialect" use="required" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ErrorCodeType", propOrder = {
    "value"
})
public class ErrorCodeType {

    @XmlValue
    protected String value;
    @XmlAttribute(required = true)
    @XmlSchemaType(name = "anyURI")
    protected String dialect;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the dialect property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDialect() {
        return dialect;
    }

    /**
     * Sets the value of the dialect property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDialect(String value) {
        this.dialect = value;
    }

}

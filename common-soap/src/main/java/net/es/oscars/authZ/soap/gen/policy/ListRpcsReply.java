
package net.es.oscars.authZ.soap.gen.policy;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for listRpcsReply complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="listRpcsReply">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="rpc" type="{http://oscars.es.net/OSCARS/authZPolicy}rpcDetails" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "listRpcsReply", propOrder = {
    "rpc"
})
public class ListRpcsReply {

    protected List<RpcDetails> rpc;

    /**
     * Gets the value of the rpc property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rpc property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRpc().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RpcDetails }
     * 
     * 
     */
    public List<RpcDetails> getRpc() {
        if (rpc == null) {
            rpc = new ArrayList<RpcDetails>();
        }
        return this.rpc;
    }

}

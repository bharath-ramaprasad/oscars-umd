package net.es.oscars.template.soap.gen.v06;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 2.2.5
 * Mon Jul 18 10:17:16 EDT 2011
 * Generated source version: 2.2.5
 * 
 */
 
@WebService(targetNamespace = "http://oscars.es.net/OSCARS/template/06", name = "TemplatePortType")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface TemplatePortType {

    @WebResult(name = "Response", targetNamespace = "", partName = "Response")
    @WebMethod(operationName = "Query", action = "http://oscars.es.net/OSCARS/template/Query")
    public java.lang.String query(
        @WebParam(partName = "Request", name = "RequestType", targetNamespace = "http://oscars.es.net/OSCARS/template/06")
        RequestType request
    );
}

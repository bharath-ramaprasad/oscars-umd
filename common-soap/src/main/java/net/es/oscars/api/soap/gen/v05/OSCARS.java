package net.es.oscars.api.soap.gen.v05;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.2.5
 * Mon Jul 18 10:17:04 EDT 2011
 * Generated source version: 2.2.5
 * 
 */
 
@WebService(targetNamespace = "http://oscars.es.net/OSCARS", name = "OSCARS")
@XmlSeeAlso({ObjectFactory.class,org.oasis_open.docs.wsrf.r_2.ObjectFactory.class,org.oasis_open.docs.wsn.b_2.ObjectFactory.class,org.oasis_open.docs.wsrf.bf_2.ObjectFactory.class,org.ogf.schema.network.topology.ctrlplane.ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface OSCARS {

    @WebResult(name = "cancelReservationResponse", targetNamespace = "http://oscars.es.net/OSCARS", partName = "cancelReservationResponse")
    @WebMethod(action = "http://oscars.es.net/OSCARS/cancelReservation")
    public java.lang.String cancelReservation(
        @WebParam(partName = "cancelReservation", name = "cancelReservation", targetNamespace = "http://oscars.es.net/OSCARS")
        GlobalReservationId cancelReservation
    ) throws AAAFaultMessage, BSSFaultMessage;

    @WebResult(name = "createReservationResponse", targetNamespace = "http://oscars.es.net/OSCARS", partName = "createReservationResponse")
    @WebMethod(action = "http://oscars.es.net/OSCARS/createReservation")
    public CreateReply createReservation(
        @WebParam(partName = "createReservation", name = "createReservation", targetNamespace = "http://oscars.es.net/OSCARS")
        ResCreateContent createReservation
    ) throws AAAFaultMessage, BSSFaultMessage;

    @WebResult(name = "queryReservationResponse", targetNamespace = "http://oscars.es.net/OSCARS", partName = "queryReservationResponse")
    @WebMethod(action = "http://oscars.es.net/OSCARS/queryReservation")
    public ResDetails queryReservation(
        @WebParam(partName = "queryReservation", name = "queryReservation", targetNamespace = "http://oscars.es.net/OSCARS")
        GlobalReservationId queryReservation
    ) throws AAAFaultMessage, BSSFaultMessage;

    @WebResult(name = "refreshPathResponse", targetNamespace = "http://oscars.es.net/OSCARS", partName = "refreshPathResponse")
    @WebMethod(action = "http://oscars.es.net/OSCARS/refreshPath")
    public RefreshPathResponseContent refreshPath(
        @WebParam(partName = "refreshPath", name = "refreshPath", targetNamespace = "http://oscars.es.net/OSCARS")
        RefreshPathContent refreshPath
    ) throws AAAFaultMessage, BSSFaultMessage;

    @WebResult(name = "teardownPathResponse", targetNamespace = "http://oscars.es.net/OSCARS", partName = "teardownPathResponse")
    @WebMethod(action = "http://oscars.es.net/OSCARS/teardownPath")
    public TeardownPathResponseContent teardownPath(
        @WebParam(partName = "teardownPath", name = "teardownPath", targetNamespace = "http://oscars.es.net/OSCARS")
        TeardownPathContent teardownPath
    ) throws AAAFaultMessage, BSSFaultMessage;

    @WebResult(name = "createPathResponse", targetNamespace = "http://oscars.es.net/OSCARS", partName = "createPathResponse")
    @WebMethod(action = "http://oscars.es.net/OSCARS/createPath")
    public CreatePathResponseContent createPath(
        @WebParam(partName = "createPath", name = "createPath", targetNamespace = "http://oscars.es.net/OSCARS")
        CreatePathContent createPath
    ) throws AAAFaultMessage, BSSFaultMessage;

    @WebResult(name = "getNetworkTopologyResponse", targetNamespace = "http://oscars.es.net/OSCARS", partName = "getNetworkTopologyResponse")
    @WebMethod(action = "http://oscars.es.net/OSCARS/getNetworkTopology")
    public GetTopologyResponseContent getNetworkTopology(
        @WebParam(partName = "getNetworkTopology", name = "getNetworkTopology", targetNamespace = "http://oscars.es.net/OSCARS")
        GetTopologyContent getNetworkTopology
    ) throws AAAFaultMessage, BSSFaultMessage;

    @WebResult(name = "modifyReservationResponse", targetNamespace = "http://oscars.es.net/OSCARS", partName = "modifyReservationResponse")
    @WebMethod(action = "http://oscars.es.net/OSCARS/modifyReservation")
    public ModifyResReply modifyReservation(
        @WebParam(partName = "modifyReservation", name = "modifyReservation", targetNamespace = "http://oscars.es.net/OSCARS")
        ModifyResContent modifyReservation
    ) throws AAAFaultMessage, BSSFaultMessage;

    @Oneway
    @WebMethod(operationName = "Notify", action = "http://oscars.es.net/OSCARS/Notify")
    public void notify(
        @WebParam(partName = "Notify", name = "Notify", targetNamespace = "http://docs.oasis-open.org/wsn/b-2")
        org.oasis_open.docs.wsn.b_2.Notify notify
    );

    @WebResult(name = "forwardResponse", targetNamespace = "http://oscars.es.net/OSCARS", partName = "forwardResponse")
    @WebMethod(action = "http://oscars.es.net/OSCARS/forward")
    public ForwardReply forward(
        @WebParam(partName = "forward", name = "forward", targetNamespace = "http://oscars.es.net/OSCARS")
        Forward forward
    ) throws AAAFaultMessage, BSSFaultMessage;

    @WebResult(name = "listReservationsResponse", targetNamespace = "http://oscars.es.net/OSCARS", partName = "listReservationsResponse")
    @WebMethod(action = "http://oscars.es.net/OSCARS/listReservations")
    public ListReply listReservations(
        @WebParam(partName = "listReservations", name = "listReservations", targetNamespace = "http://oscars.es.net/OSCARS")
        ListRequest listReservations
    ) throws AAAFaultMessage, BSSFaultMessage;
}
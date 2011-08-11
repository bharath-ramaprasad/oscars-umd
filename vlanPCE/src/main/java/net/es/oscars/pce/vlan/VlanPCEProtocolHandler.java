package net.es.oscars.pce.vlan;

import net.es.oscars.logging.OSCARSNetLoggerize;
import net.es.oscars.pce.SimplePCEProtocolHandler;

/**
 * Handles incoming SOAP requests. Extends SimplePCEProtocolHandler and 
 * annotates it to enable NetLogger for this VLAN PCE.
 * 
 * @author Andy Lake <andy@es.net>
 */
@OSCARSNetLoggerize(moduleName = "net.es.oscars.pce.VLAN")
@javax.xml.ws.BindingType(value ="http://www.w3.org/2003/05/soap/bindings/HTTP/")
public class VlanPCEProtocolHandler extends SimplePCEProtocolHandler{}

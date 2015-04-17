## A SOAP 1.2 message is not valid when sent to a SOAP 1.1 only endpoint. ##

Full error from connPCE.out:

```
org.apache.cxf.binding.soap.SoapFault: A SOAP 1.2 message is not valid when sent to a SOAP 1.1 only endpoint.
	at org.apache.cxf.binding.soap.interceptor.ReadHeadersInterceptor.handleMessage(ReadHeadersInterceptor.java:136)
	at org.apache.cxf.binding.soap.interceptor.ReadHeadersInterceptor.handleMessage(ReadHeadersInterceptor.java:60)
	at org.apache.cxf.phase.PhaseInterceptorChain.doIntercept(PhaseInterceptorChain.java:236)
	at org.apache.cxf.endpoint.ClientImpl.onMessage(ClientImpl.java:671)
	at org.apache.cxf.transport.http.HTTPConduit$WrappedOutputStream.handleResponseInternal(HTTPConduit.java:2177)
	at org.apache.cxf.transport.http.HTTPConduit$WrappedOutputStream.handleResponse(HTTPConduit.java:2057)
	at org.apache.cxf.transport.http.HTTPConduit$WrappedOutputStream.close(HTTPConduit.java:1982)
	at org.apache.cxf.io.CacheAndWriteOutputStream.postClose(CacheAndWriteOutputStream.java:47)
	at org.apache.cxf.io.CachedOutputStream.close(CachedOutputStream.java:188)
	at org.apache.cxf.transport.AbstractConduit.close(AbstractConduit.java:66)
	at org.apache.cxf.transport.http.HTTPConduit.close(HTTPConduit.java:637)
	at org.apache.cxf.interceptor.MessageSenderInterceptor$MessageSenderEndingInterceptor.handleMessage(MessageSenderInterceptor.java:62)
	at org.apache.cxf.phase.PhaseInterceptorChain.doIntercept(PhaseInterceptorChain.java:236)
	at org.apache.cxf.endpoint.ClientImpl.invoke(ClientImpl.java:483)
	at org.apache.cxf.endpoint.ClientImpl.invoke(ClientImpl.java:318)
	at net.es.oscars.utils.soap.OSCARSSoapService.invoke(OSCARSSoapService.java:384)
	at net.es.oscars.pce.connectivity.ConnectivityPCE.processDomains(ConnectivityPCE.java:401)
	at net.es.oscars.pce.connectivity.ConnectivityPCE.calculatePath(ConnectivityPCE.java:259)
	at net.es.oscars.pce.connectivity.ConnectivityPCEServer.calculatePath(ConnectivityPCEServer.java:108)
	at net.es.oscars.pce.SimplePCEServer.processJob(SimplePCEServer.java:75)
	at net.es.oscars.pce.SimplePCEServer.run(SimplePCEServer.java:181)
	at java.lang.Thread.run(Thread.java:636)
 WARN ts=2011-11-16T09:21:41.320000Z event=net.es.oscars.pce.Connectivity.invoke-client.end guid=testdomain-1-API-de29917f-eff1-4b11-a3aa-53f9f3c0d4a2 gri=testdomain-1-166776 errSeverity=MINOR status=-1 msg="OSCARSSoapService.invoke:Exception connecting to getTopology on http://localhost:9019/topoBridge Message is: A SOAP 1.2 message is not valid when sent to a SOAP 1.1 only endpoint." 
DEBUG ts=2011-11-16T09:21:41.320000Z event=net.es.oscars.pce.Connectivity.getTopology.end guid=testdomain-1-API-de29917f-eff1-4b11-a3aa-53f9f3c0d4a2 gri=testdomain-1-166776 errSeverity=MAJOR status=-1 topos=[testdomain-1] msg="OSCARSSoapService.invoke:Exception connecting to getTopology on http://localhost:9019/topoBridge Message is: A SOAP 1.2 message is not valid when sent to a SOAP 1.1 only endpoint." url=http://localhost:9019/topoBridge 
ERROR ts=2011-11-16T09:21:41.321000Z event=net.es.oscars.pce.Connectivity.calculatePath.end guid=testdomain-1-API-de29917f-eff1-4b11-a3aa-53f9f3c0d4a2 gri=testdomain-1-166776 errSeverity=MAJOR status=-1 msg="Error from topoBridge: OSCARSSoapService.invoke:Exception connecting to getTopology on http://localhost:9019/topoBridge Message is: A SOAP 1.2 message is not valid when sent to a SOAP 1.1 only endpoint." 
exception thrown by pceCreate: net.es.oscars.utils.soap.OSCARSServiceException: Error from topoBridge: OSCARSSoapService.invoke:Exception connecting to getTopology on http://localhost:9019/topoBridge Message is: A SOAP 1.2 message is not valid when sent to a SOAP 1.1 only endpoint.
```

### History ###

Started receiving error after replacing the topology file that OSCARS was using.

### Solution(s) ###

Inspecting the topoBridge.out file indicated that there was an error in the topology config file.  The topoBridge.out file should indicate the exact line of the error in the topology config file.  Fix that, restart the server and try again.
package net.es.oscars.wsnbroker.utils;

import java.net.MalformedURLException;
import java.util.Arrays;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import net.es.oscars.utils.clients.WSNBrokerClient;
import net.es.oscars.utils.soap.OSCARSServiceException;

import org.oasis_open.docs.wsn.b_2.ResumeSubscription;
import org.oasis_open.docs.wsn.b_2.ResumeSubscriptionResponse;

public class ResumeUtil {
    WSNBrokerClient client;
    
    public ResumeUtil(String url) throws MalformedURLException, OSCARSServiceException{
        this.client = WSNBrokerClient.getClient(url);
    }
    
    public ResumeSubscriptionResponse resume(String id) throws OSCARSServiceException{
        ResumeSubscription resumeRequest = new ResumeSubscription();
        resumeRequest.setSubscriptionReference(WSAddrParser.createAddress(id));
        try {
            return this.client.getPortType().resumeSubscription(resumeRequest);
        } catch (Exception e){
            throw new OSCARSServiceException(e.getMessage());
        }
    }
    
    public static void main(String[] args){
        String url = "http://localhost:9013/OSCARS/wsnbroker";
        OptionParser parser = new OptionParser(){
            {
                acceptsAll(Arrays.asList("h", "help"), "prints this help screen");
                acceptsAll(Arrays.asList("u", "url"), "the URL of the OSCARS notification service to contact").withRequiredArg().ofType(String.class);
                acceptsAll(Arrays.asList("i", "id"), "the id of the subscription to resume").withRequiredArg().ofType(String.class);
            }
        };
        
        OptionSet opts = parser.parse(args);
        if(opts.has("h")){
            try{
                parser.printHelpOn(System.out);
            }catch(Exception e){}
            System.exit(0);
        }
        
        if(opts.has("u")){
            url = (String)opts.valueOf("u");
        }
        
        String id = null;
        if(opts.has("i")){
            id = (String) opts.valueOf("i");
        }else{
            System.err.println("Must provide a subscription id");
            try{
                parser.printHelpOn(System.out);
            }catch(Exception e){}
            System.exit(1);
        }
        
        try {
            ResumeUtil util = new ResumeUtil(url);
            util.resume(id);
            System.out.println("Successfully resumed subscription " + id);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (OSCARSServiceException e) {
            e.printStackTrace();
        }
    }
}

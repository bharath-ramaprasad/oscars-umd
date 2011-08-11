package net.es.oscars.utils.soap;

import org.hibernate.Session;
import org.apache.log4j.Logger;

import net.es.oscars.common.soap.gen.OSCARSFaultMessage;
import net.es.oscars.common.soap.gen.OSCARSFault;
import net.es.oscars.logging.ErrSev;
import net.es.oscars.logging.OSCARSNetLogger;

/**
 * OSCARSFaultUtils called when a server catches an exception. It handles database cleanup,logging of
 * unexpected errors, maps the exception to an OSCARSFaultMessage, which is then thrown.
 *
 * @author  Mary Thompson
 */
public class OSCARSFaultUtils {

    /**
     * Handles exceptions in servers
     * 
     * @param ex Exception
     * @param session Session - if not null, current open session
     * @param log Logger - log in which to record system errors
     * @parma method String containing the method that was called
     * @throws OSCARSFaultMessage
     */
    public  static Void handleError(OSCARSServiceException ex, Session session, Logger log, String method ) throws OSCARSFaultMessage
    { 
        Boolean isUser = false;
        if (ex.getType().equals("user")) {
            isUser = true;
        }
        // it wouldn't compile without the return. Doesn't make sense to me
       return OSCARSFaultUtils.handleError(ex,isUser,session,log,method);
    }
 
    /**
     * Handles exceptions in servers
     * 
     * @param ex Exception
     * @param user Boolean true if exception is caused by user input
     * @param session Session - if not null, current open session
     * @param log Logger - log in which to record system errors
     * @param method String containing the method that was called
     * @throws OSCARSFaultMessage
     */
    public  static Void handleError(Exception ex, Boolean user, Session session, Logger log, String method ) throws OSCARSFaultMessage
    {
        if (session != null) {
            session.getTransaction().rollback();
        }
        OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
        String event = "handleError";
        OSCARSFault of = new OSCARSFault();
        of.setDetails("in " + method);
        String message = ex.getMessage();
        if (user) {
            of.setMsg("user");
        } else { // system error
            if (log !=  null){
                log.error(netLogger.error(event,ErrSev.MAJOR,"Exception thrown by " + method));
            }
            ex.printStackTrace();
            of.setMsg("system");
            if (message == null ){
                // some runtime errors do not set message
                message=ex.toString();
            }
        }
        throw new OSCARSFaultMessage(message,of);
    }
}

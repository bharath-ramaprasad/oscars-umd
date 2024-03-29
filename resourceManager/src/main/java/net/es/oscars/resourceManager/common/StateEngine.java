package net.es.oscars.resourceManager.common;

import java.util.*;

import net.es.oscars.logging.OSCARSNetLogger;
import net.es.oscars.resourceManager.beans.Reservation;
import net.es.oscars.resourceManager.dao.ReservationDAO;
import net.es.oscars.utils.sharedConstants.StateEngineValues;

import org.apache.log4j.Logger;
import org.hibernate.*;

public class StateEngine {

    /* allowed transitions
     *  initial state = ACCEPTED - in CreateReservation if the reservation request is valid, and authorized a gri is assigned 
     *     reservation is initially stored with status ACCEPTED. Otherwise nothing is stored and CreateResevation
     *     returns an error.
     * ACCEPTED -> INPATHCALCULATION, CANCELLED
     * INPATHCALCULATION -> PATHCALCULATION, FAILED, INCANCEL
     * PATHCALCULATED -> INCOMMIT, INMODIFY, INCANCEL
     * INCOMMIT -> COMMITTED, RESERVED, FAILED, INCANCEL
     * COMMITTED -> RESERVED, INMODIFY, INCANCEL
     * RESERVED -> INSETUP, INMODIFY, INCANCEL -- looks to me if 0.5 ReservationManager doesn't implement this correctly --mrt
     * INSETUP -> ACTIVE, FAILED, INMODIFY?, INCANCEL
     * ACTIVE -> INTEARDOWN, INMODIFY, INCANCEL
     * INTEARDOWN -> RESERVED, FINISHED, INCANCEL, INMODIFY
     * INCANCEL -> CANCELLED
     * INMODIFY -> RESERVED, ACTIVE
     * final states: FAILED, FINISHED, CANCELLED
     */

 

    private String dbname;
    private static HashMap<String, String> statusMap = new HashMap<String, String>();
    private static HashMap<String, Integer> localStatusMap = new HashMap<String, Integer>();
    private Logger log;

    public StateEngine(String dbname) {
        this.log = Logger.getLogger(this.getClass());
        this.dbname = dbname;
    }

    public synchronized String updateStatus(Reservation resv, String newStatus) throws RMException {
        
        String gri = resv.getGlobalReservationId();
        String event = "updateStatus";
        OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
        
        // set value to null to cause a re-initialization on next call.
        // Used when status is reset by a RMStore action
        if (newStatus == null)  {
            StateEngine.statusMap.put(gri,null);
            return null;
        }

        String status = this.getStatus(resv);
        
        log.debug(netLogger.start(event,"request state change from " + status + " to newStatus " + newStatus));
        // at the end of a teardown see if endtime has been reached
        if (newStatus.equals(StateEngineValues.RESERVED)){
            // check to see if end time has been reached
            if (resv.getEndTime() <= System.currentTimeMillis()/1000) {
                newStatus = StateEngineValues.FINISHED;
            }
        }

        if (newStatus == StateEngineValues.FINISHED) {
            this.doFinish(resv,status);
            newStatus = resv.getStatus();  //may now be FAILED
        }
        else {
            // throws an RMException if transition is not allowed
            newStatus = StateEngine.canModifyStatus(status, newStatus);
        }
        
        log.debug(netLogger.end(event, "changing state from " + status + " to newStatus " + newStatus));
        status = newStatus;
        resv.setStatus(status);
        ReservationDAO resvDAO = new ReservationDAO(this.dbname);
        resvDAO.update(resv);
        StateEngine.statusMap.put(gri, status);
        return status;
    }

/* This is intentionally not synchronized, we do not want to block when reading the status
    public static void canUpdateStatus(Reservation resv, String newStatus) throws RMException {
        String status = StateEngine.getStatus(resv);
        StateEngine.canModifyStatus(status, newStatus);
    }
*/
    
//  Business / state diagram logic goes here
    public static String canModifyStatus(String status, String newStatus) throws RMException {
        boolean allowed = true;
        String retStatus = newStatus;
        if (newStatus.equals(status)) {
            // no-ops always allowed
            //TODO get rid of SUBMITTED state when coordinator is change
        } else if (newStatus.equals(StateEngineValues.SUBMITTED)) {
            newStatus = StateEngineValues.ACCEPTED;
            retStatus = StateEngineValues.ACCEPTED;
            // always allowed, must not abuse..
        } else if (newStatus.equals(StateEngineValues.ACCEPTED)) {
            // always allowed, must not abuse..
        } else if (newStatus.equals(StateEngineValues.FAILED)) {
            // always allowed, must not abuse..
        } else if (newStatus.equals(StateEngineValues.INPATHCALCULATION )) {
            //TODO get rid of SUBMITTED state when coordinator is changed
            if (!status.equals(StateEngineValues.ACCEPTED) && 
                    !status.equals(StateEngineValues.SUBMITTED)) {
                allowed = false;
            }
        } else if (newStatus.equals(StateEngineValues.PATHCALCULATED)) {
            if (status.equals(StateEngineValues.INMODIFY) ) {
                retStatus = StateEngineValues.MOD_PATHCALCULATED;
            } else if (!status.equals(StateEngineValues.INPATHCALCULATION )) {
                allowed = false;
            }

        } else if (newStatus.equals(StateEngineValues.INCOMMIT)) {// Probably only PATHCACULATED should be allowed
            if (status.equals(StateEngineValues.MOD_PATHCALCULATED)) {
                retStatus = StateEngineValues.MOD_INCOMMIT;
            } else if (!status.equals(StateEngineValues.PATHCALCULATED) && 
                    !status.equals(StateEngineValues.INPATHCALCULATION)) {
                allowed = false;
            } 
        } else if (newStatus.equals(StateEngineValues.COMMITTED)) { 
            if (!status.equals(StateEngineValues.INCOMMIT )) {
                allowed = false;
            }
        } else if (newStatus.equals(StateEngineValues.RESERVED)) {
            if (!status.equals(StateEngineValues.COMMITTED ) && 
                    !status.equals(StateEngineValues.INCOMMIT) &&
                    !status.equals(StateEngineValues.INTEARDOWN) && 
                    !status.equals(StateEngineValues.MOD_INCOMMIT)) {
                allowed = false;
            }
        } else if (newStatus.equals(StateEngineValues.INMODIFY)) {
            // TODO maybe add PATHCALCULATED, COMMITTED   -see how cancel works out for these states
            if (!status.equals(StateEngineValues.RESERVED) &&
                    !status.equals(StateEngineValues.ACTIVE)) {
                allowed = false;
            }
        } else if (newStatus.equals(StateEngineValues.INSETUP)) {
            if (!status.equals(StateEngineValues.RESERVED)) {
                allowed = false;
            }
        } else if (newStatus.equals(StateEngineValues.ACTIVE)) {
            if (!status.equals(StateEngineValues.INSETUP) && 
                    !status.equals(StateEngineValues.MOD_INCOMMIT)) {
                allowed = false;
            }
        } else if (newStatus.equals(StateEngineValues.INTEARDOWN)) {
            if (!status.equals(StateEngineValues.ACTIVE) &&
                    !status.equals(StateEngineValues.INSETUP)) {
                allowed = false;
            }
        } else if (newStatus.equals(StateEngineValues.INCANCEL)) {
            if (status.equals(StateEngineValues.ACCEPTED) || 
                    status.equals(StateEngineValues.FINISHED) || 
                    status.equals(StateEngineValues.FAILED)) {
                allowed = false;
            }
        } else if (newStatus.equals(StateEngineValues.CANCELLED)) {
            if (!status.equals(StateEngineValues.ACCEPTED) &&
                    !status.equals(StateEngineValues.INCANCEL) ) {
                allowed = false;
            }
        }
        if (!allowed) {
            throw new RMException("Current status is "+status+"; cannot change to "+newStatus);
        }
        return retStatus;
    }
    /**
     * set the state for an expired reservation
     * @param resv
     */
    private void doFinish(Reservation resv, String status){
        if (status.equals(StateEngineValues.CANCELLED) ||
             status.equals(StateEngineValues.FAILED) ||
             status.equals(StateEngineValues.FINISHED)){
            // do nothing
        } else if (status.equals(StateEngineValues.RESERVED) ||
                status.equals(StateEngineValues.INTEARDOWN)) {
            resv.setStatus(StateEngineValues.FINISHED);
        } else if (status.equals(StateEngineValues.ACTIVE)) {
            /* teardown should have been scheduled, maybe we need a new statevalue 
             * for teardown scheduled, as INTEARDOWN is only set when the coord responds
             * For now just ignore this case.
             */
        } else {
            resv.setStatus(StateEngineValues.FAILED);
            String description = resv.getDescription();
            String newDescription = description + 
                   " *** FAILED when endtime was reached when the reservation was in state: " + status;
            resv.setDescription(newDescription);
        }
    }
    /**
     * @return the status
     */
    public static String getStatus(Reservation resv) {
        String gri = resv.getGlobalReservationId();
        String status = null;
        // if the state engine has not been initialized, return what is in the Reservation object
        if (StateEngine.statusMap.get(gri) != null) {
            status = StateEngine.statusMap.get(gri);
        } else {
            status = resv.getStatus();
            StateEngine.statusMap.put(gri,status);
        }

        return status;
    }

    /**
     * @return the localStatus
     */
    public static Integer getLocalStatus(Reservation resv) {
        String gri = resv.getGlobalReservationId();
        Integer status = null;
        // if the state engine has not been initialized, return what is in the Reservation object
        if (StateEngine.localStatusMap.get(gri) != null) {
            status = StateEngine.localStatusMap.get(gri);
        } else {
            status = resv.getLocalStatus();
        }

        return status;
    }

    /**
     * Utility function for verifying the status is up-to-date before committing a 
     * Hibernate session. The statusMap is synchronized so will always be the most 
     * accurate representation of the status. During interdomain path setup this call should 
     * ALWAYS be used to commit the transaction. There is a time between when a path 
     * receives a notification and makes a change where a race condition exists without this.
     * 
     * @param reservations the reservations to save
     * @param bss the Hibernate session to commit
    */
public synchronized void safeHibernateCommit(List<Reservation> reservations,
        Session session) {
    for(Reservation resv : reservations){
        //sets the reservation status to the value of the status map
        resv.setStatus(StateEngine.getStatus(resv));
        //sets the reservation local status to the value of the local status map
        resv.setLocalStatus(StateEngine.getLocalStatus(resv));
    }
    session.getTransaction().commit();
}

/**
     * Convenience method for calling safeHibernateCommit(List&lt;Reservation&gt; reservations,
     * Session bss) with only one reservation.
     * 
     * @param resv the reservations to save
     * @param bss the Hibernate session to commit
     */
public synchronized void safeHibernateCommit(Reservation resv, Session session){
    List<Reservation> reservations = new ArrayList<Reservation>();
    reservations.add(resv);
    this.safeHibernateCommit(reservations, session);
}
 
}

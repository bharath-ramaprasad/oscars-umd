/**
 * 
 */
package net.es.oscars.resourceManager.common;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import net.es.oscars.api.soap.gen.v06.ResDetails;
import net.es.oscars.logging.ErrSev;
import net.es.oscars.logging.OSCARSNetLogger;
import net.es.oscars.resourceManager.beans.ConstraintType;
import net.es.oscars.resourceManager.beans.Reservation;
import net.es.oscars.resourceManager.dao.ReservationDAO;
import net.es.oscars.resourceManager.scheduler.RMReservationScheduler;
import net.es.oscars.resourceManager.scheduler.ReservationScheduler;
import net.es.oscars.utils.sharedConstants.StateEngineValues;

import org.apache.log4j.Logger;

/**
 * @author mrt, lomax
 *
 */
public class ScanReservations {
     private RMCore core;
     private static ScanReservations instance = null;
     private  int scanInterval;
     private  int lookAhead;
     private  long lastScanned;
     private static Logger LOG = Logger.getLogger(ScanReservations.class.getName());
     private String dbname;

     /**
      * Constructor
      * @param dbname if not null is the test database name
      *        if null use the dbname in the RMCore
      */
     private ScanReservations(String dbname){
         //LOG.debug("constructor called with dbname " + dbname);
         this.core = RMCore.getInstance();
         if (dbname == null) {
             this.core = RMCore.getInstance();
             this.dbname = core.getDbname();
         } else {
             //LOG.debug("setting this.dbname to " + dbname);
             this.dbname = dbname;
         }
         this.scanInterval = core.getScanInterval();
         this.lookAhead = core.getLookAhead();
         this.lastScanned =0;
     }
     
     /**
      * return singleton ScanReservations
      * @param dbname if not null is the test database name
      *        if null use the dbname in the RMCore
      * @return
      */
     public static ScanReservations getInstance(String dbname) {
         //LOG.debug("getInstance called with dbname " + dbname);
         if (ScanReservations.instance == null) {
             ScanReservations.instance = new ScanReservations(dbname);
         }
         return instance;
     }
     
     public long getLastScanned() {
         return lastScanned;
     }
     /** 
      * scheduled to run at RMCore.scanInterval to look for timer-automatic path setup and teardown
      * and expired reservations that need to have their status updated.
      * Also called by ResourceManager.store when a RESERVED reservation is stored
      * TODO looks like modifyReservation is not handled yet.
      */
     public void scan () {
         String event = "scanReservations";
         OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
         LOG.debug(netLogger.start(event));
         synchronized (RMReservationScheduler.schedLock) {
             this.lastScanned = System.currentTimeMillis();
             RMReservationScheduler.schedLock =  "scanning";
             ReservationDAO resDAO = new ReservationDAO(this.dbname);
             ResourceManager manager = new ResourceManager();
             Session session = this.core.getSession();
             boolean sessionOpen = false;
             try {
                 if (session.getTransaction() != null ) {
                     sessionOpen = true;
                     // was called from ResourceManager rather than scheduler
                 }
             } catch (HibernateException ex) {
                 // probably means there is no transaction
             }
             session.beginTransaction();
             RMReservationScheduler rmSched = RMReservationScheduler.getInstance();
             List<String> griList = new ArrayList<String>();
             try {
                 // scan for timer-automatic reservations that should start in the lookAhead interval
                 List<Reservation> setUpResList = resDAO.pendingReservations(lookAhead);
                 LOG.debug(netLogger.getMsg(event,"found "  + setUpResList.size() + " setup reservations"));
                 for (Reservation res: setUpResList) {
                     if (res.getConstraint(ConstraintType.USER).getPath().getPathSetupMode().equals("timer-automatic")) {
                         ResDetails resDetails = manager.res2resDetails(res,true,netLogger, event);  // internalHopAllowed
                         if (resDetails.getReservedConstraint() == null) {
                             LOG.warn(netLogger.error(event,ErrSev.MINOR,"skipping reservation " + 
                                     resDetails.getGlobalReservationId()  +
                                     ": status is RESERVED but no reservedConstraint"));
                             continue;
                         }
                         LOG.debug(netLogger.getMsg(event,"scheduling setup for " + resDetails.getGlobalReservationId()));
                         rmSched.scheduleSetup(resDetails);
                     }
                 }
                 // scan for reservations that are ACTIVE 
                 // and will expire in the lookAhead interval
                 List<Reservation> tearDownRes = resDAO.activeExpiringReservations(lookAhead);
                 LOG.debug(netLogger.getMsg(event,"found "  + tearDownRes.size() + " tear down reservations"));
                 for ( Reservation res: tearDownRes) {
                     // tear it down even it it signal-xml
                     ResDetails resDetails = manager.res2resDetails(res,true,netLogger, event);  // internalHopAllowed
                     if (resDetails.getReservedConstraint() == null) {
                         LOG.warn(netLogger.getMsg(event,"" +
                                                   " skipping reservation " + resDetails.getGlobalReservationId()  +
                                                   ": status is RESERVED but no reservedConstraint")); 
                         continue;
                     }
                     rmSched.scheduleTeardown(resDetails);
                     griList.add(resDetails.getGlobalReservationId());
                 }
                 // scan for all reservations that are expired where STATE is reserved or active
                 List<Reservation> finishRes = resDAO.unfinishedExpiredReservations(0);
                 LOG.debug(netLogger.getMsg(event, "found "  + finishRes.size() + " unfinished expired reservations"));
                 for ( Reservation res: finishRes) {
                   //don't schedule same reservation for both teardown and finish
                     if (!griList.contains(res.getGlobalReservationId())) { 
                         ResDetails resDetails = manager.res2resDetails(res,true,netLogger,event);  // internalHopAllowed
                         LOG.debug("scheduling finish for " + resDetails.getGlobalReservationId());
                         rmSched.scheduleFinish(resDetails);
                     }
                 }
             } catch (RMException ex) {
                 // Found an invalid reservation in table: no user constraint or no reserved constraint
                 LOG.warn(netLogger.error(event, ErrSev.MINOR,"Exception: " + ex.getMessage()));
             }
             if (sessionOpen == false ){
                 session.getTransaction().commit();
             }
             //RMReservationScheduler.schedLock.unlock();
             RMReservationScheduler.schedLock = "unlocked";
             LOG.debug(netLogger.end(event));
         }
     }
}

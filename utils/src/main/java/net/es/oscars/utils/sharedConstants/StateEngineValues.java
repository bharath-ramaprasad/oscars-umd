package net.es.oscars.utils.sharedConstants;

public class StateEngineValues {

    public final static String SUBMITTED = "SUBMITTED";     // unnecessary
    public final static String ACCEPTED = "ACCEPTED";       // createReservation is authorized, gri is assigned
    public final static String INPATHCALCULATION = "INPATHCALCULATION";   //start local path calculation
    public final static String LPATHCALCULATED  = "LOCALPATHCALCULATED"; // local path calculation done
    public final static String PATHCALCULATED  = "PATHCALCULATED"; // whole path calculation done
    public final static String INCOMMIT = "INCOMMIT";       //in commit phase for calculated path
    public final static String LCOMMITTED = "LOCALPATHCOMMITTED";     // local path resources committed
    public final static String COMMITTED = "COMMITTED";     // whole path resources committed
    public final static String RESERVED = "RESERVED";       // all domains have committed resources
    public final static String INSETUP = "INSETUP";         // circuit setup has been started
    public final static String LACTIVE = "LOCALCIRCUITACTIVE"; // Local part of circuit has been setup
    public final static String ACTIVE = "ACTIVE";           // entirre circuit has been setup
    public final static String INTEARDOWN = "INTEARDOWN";   // circuit teardown has been started
    public final static String LFINISHED = "LOCALLYFINISHED";// reservation endtime reached with no errors, circuit has been torndown
    public final static String FINISHED = "FINISHED";       // reservation endtime reached with no errors, circuit has been torndown
    public final static String LCANCELLED = "LOCALLYCANCELLED"; // reservation has been canceled locally, no circuit
    public final static String CANCELLED = "CANCELLED";     // complete reservation has been canceled, no circuit
    public final static String FAILED = "FAILED";           // reservation failed at some point, no circuit
    public final static String INMODIFY = "INMODIFY";       // reservation is being modified locally
    public final static String INCANCEL = "INCANCEL";       // reservation is being modified locally
    public final static String MOD_PATHCALCULATED = "MOD-PATHCALCULATED"; // in MODIFY Path has been recalculated
    public final static String MOD_INCOMMIT = "MOD_INCOMMIT";   // in MODIFY COMMIT Phase

    /*local statuses -- so far unchanged from 0.5
    public final static int LOCAL_INIT = 0;
    public final static int CONFIRMED = 1;
    public final static int MODIFY_ACTIVE = 2;
    public final static int DOWN_CONFIRMED = 2;
    public final static int UP_CONFIRMED = 4;
    public final static int COMPLETED = 7;
    public final static int NEXT_STATUS = 24; //the next status bits
    public final static int NEXT_STATUS_RESERVED = 0;
    public final static int NEXT_STATUS_CANCEL = 8;
    public final static int NEXT_STATUS_FINISHED = 16;
    public final static int NEXT_STATUS_FAILED = 24;
    */
}
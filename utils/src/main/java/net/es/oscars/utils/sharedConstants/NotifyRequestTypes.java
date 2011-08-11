package net.es.oscars.utils.sharedConstants;

public class NotifyRequestTypes {
    // TODO: Perhaps not the best place to define those, but until there is a class defining event type this is acceptable.
    public static String RESV_CREATE_CONFIRMED  = "RESERVATION_CREATE_CONFIRMED";
    public static String RESV_COMMIT_CONFIRMED  = "RESERVATION_COMMIT_CONFIRMED";
    public static String RESV_MODIFY_CONFIRMED  = "RESERVATION_MODIFY_CONFIRMED";
    public static String RESV_CANCEL_CONFIRMED  = "RESERVATION_CANCEL_CONFIRMED";
    public static String PSS_SETUP_CONFIRMED    = "PSS_SETUP_CONFIRMED";
    public static String PSS_SETUP_FAILED       = "PSS_SETUP_FAILED";
    public static String PSS_TEARDOWN_CONFIRMED = "PSS_TEARDOWN_CONFIRMED";
    public static String PSS_TEARDOWN_FAILED    = "PSS_TEARDOWN_FAILED";
}

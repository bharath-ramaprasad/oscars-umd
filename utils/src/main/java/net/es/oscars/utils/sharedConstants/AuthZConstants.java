package net.es.oscars.utils.sharedConstants;

/**
 * specifies constants shared by the AuthN service, the AuthZ service, the Coordinator service and the 
 * user admin wbui
 * 
 * @author mrt
 *
 */
public class AuthZConstants {
    
    /* permission values */
    public static final String ACCESS_DENIED       = "DENIED";
    public static final String ACCESS_ALLUSERS     = "ALLUSERS";
    public static final String ACCESS_SELFONLY     = "SELFONLY";
    public static final String ACCESS_MYSITE       = "MYSITE";
    
    /* attribute types */
    public static final String LOGIN_ID             = "loginId";
    public static final String INSTITUTION          = "institution";
    public static final String ROLE                 = "role";
    public static final String PRIVILEGE            = "privilege";
    
    /* resource  names - need to match AuthZ resources table*/
    public static final String RESERVATIONS         = "Reservations";
    public static final String USERS                = "Users";
    public static final String DOMAINS              = "Domains";
    public static final String AAA                  = "AAA";
    public static final String PUBLISHERS           = "Publishers";
    public static final String SUBSCRIPTIONS        = "Subscriptions";
    
    /* action names - need to match AuthZ permissions table */
    public static final String LIST                 = "list";
    public static final String QUERY                = "query";
    public static final String MODIFY               = "modify";
    public static final String CREATE               = "create";
    public static final String SIGNAL               = "signal";
    
    /* constraint/Condition names,constraints are stored in the authZ database
     *   AuthConditionTypes are passed from AuthZ to policy enforcement points, e.g CoordinatorImpl
     */
    public static final String NONE                 = "none";       // constraint name
    public static final String MY_SITE              = "my-site";    // constraint name
    public static final String PERMITTED_DOMAINS    = "permitted-domains";  // AuthCondition name
    public static final String ALL_USERS            = "all-users";          // constraint name
    public static final String PERMITTED_LOGIN      = "permitted-loginId";   // AuthCondition name
    public static final String MAX_BANDWIDTH        = "max-bandwidth";      // constraint and AuthCondition name
    public static final String MAX_DURATION         = "max-duration";       // constraint and AuthCondition name
    public static final String INT_HOPS_ALLOWED     = "specify-path-elements";  // constraint and AuthCondition name
    public static final String SPECIFY_GRI          = "specify-gri";        // constraint and AuthCondition name

}
package net.es.oscars.authZ.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import net.es.oscars.authZ.beans.Attribute;
import net.es.oscars.authZ.beans.Authorization;
import net.es.oscars.authZ.beans.Permission;
import net.es.oscars.authZ.beans.Resource;
import net.es.oscars.authZ.dao.AttributeDAO;
import net.es.oscars.authZ.dao.AuthorizationDAO;
import net.es.oscars.authZ.dao.PermissionDAO;
import net.es.oscars.authZ.dao.ResourceDAO;
import net.es.oscars.authZ.dao.SiteDAO;
import net.es.oscars.authZ.soap.gen.CheckAccessReply;
import net.es.oscars.common.soap.gen.AuthConditionType;
import net.es.oscars.common.soap.gen.AuthConditions;
import net.es.oscars.logging.OSCARSNetLogger;
import net.es.oscars.logging.ErrSev;
import net.es.oscars.utils.sharedConstants.AuthZConstants;


/**
 * AuthZManager handles all authorization related method calls.
 *
 * @author David Robertson, Mary Thompson
 */
public class AuthZManager {
    private Logger log;
    private String dbname;

    public AuthZManager(String dbname) {
        this.log = Logger.getLogger(this.getClass());
        this.dbname = dbname;
    }

    /**
     * Checks to make sure that that user has the permission to use the
     *     resource by checking for the corresponding triplet in the
     *     authorizations table. 
     *     The least restrained permission that any of the user's attributes grants
     *     is returned, where ALLUSERS > SELFONLY > DENIED. The default ois SELFONLY 
     *     if there is no all_users constraint.
     *     All constraints that apply to any of the user's attributes are returned in
     *     authConditions. This may be more restrictive than is desired -mrt
     *
     * @param attrs list of attributes possessed by the user, must include LOGINID and INSTITUTION
     * @param resourceName a string identifying a resource
     * @param permissionName a string identifying a permission
     * @return checkAccessReply which contains a permission of either DENIED, ALLUSERS, or SELFONLY
     *      and optionally a set of conditions
     */
    public CheckAccessReply checkAccess(List<Attribute> attrs, String resourceName,
                    String permissionName) {

        OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
        String event = "checkACCESS";
        this.log.debug(netLogger.start(event));
        CheckAccessReply retVal = new CheckAccessReply();
        AuthConditions authConditions = new AuthConditions();

        List<Authorization> auths = null;
        AuthorizationDAO authDAO = new AuthorizationDAO(this.dbname);

        Boolean site = false;
        Boolean self = false;
        Boolean allUsers = false;
        String loginId = null;
        String institution = null;
        
        int i=0;
        for ( Attribute attr : attrs){
            if (attr.getAttrId().equals(AuthZConstants.LOGIN_ID)) {
                loginId = attr.getValue();
            }
            if (attr.getAttrId().equals(AuthZConstants.INSTITUTION)){
                institution = attr.getValue();
            }
        }
        if (loginId == null || institution == null) {
            this.log.error(netLogger.error(event,ErrSev.MINOR,"Missing loginId or institution attribute"));
            return retVal;
        }

        Map<String, Object> arp = this.getARPObjects(attrs, resourceName, permissionName);
        if (arp == null) {
            // user has  no attributes or resource or permission is not valid
            this.log.info(netLogger.error(event,ErrSev.MINOR,"access denied to " + 
                                          permissionName +" " + resourceName));
            retVal.setPermission(AuthZConstants.ACCESS_DENIED);
            return retVal;
        }

        Permission permission = (Permission) arp.get("permission");
        Resource resource = (Resource) arp.get("resource");

        /* check to see if any of the users attributes grants this
         * authorization, and look for a selfOnly or MySite constraint
         */
        List<Attribute> attributes = (List<Attribute>) arp.get("attributes");
        for (Attribute attribute : attributes) {
            auths = authDAO.query(attribute.getId(), resource.getId(), permission.getId());
            if (auths.isEmpty()) {
               // this.log.debug("attrId:  no authorization" );
                continue;
            }
            for (Authorization auth : auths) {
                String constraintName = auth.getConstraint().getName();
                if (constraintName.equals(AuthZConstants.NONE)) {
                    // found an authorization with no constraints,
                    // user is allowed for self only
                    // this.log.debug("attrId: authorized for SELFONLY");
                    self = true;
                } else if (constraintName.equals(AuthZConstants.MY_SITE)) {
                    AuthConditionType authCond = new AuthConditionType();
                    authCond.setName(AuthZConstants.PERMITTED_DOMAINS);
                    List <String> domains = getDomains(institution);
                    for ( String domain : domains) {
                        authCond.getConditionValue().add(domain);
                    }
                    authConditions.getAuthCondition().add(authCond);
                   self = true;
                   // this.log.debug("checkAccess MYSITE access");
                } else if (constraintName.equals(AuthZConstants.ALL_USERS)) {
                    // leave this test in for historic reasons, the value should always be true
                    if (auth.getConstraintValue().equals("true")) {
                        // found an authorization with allUsers allowed,
                        // highest level access, so return it
                        this.log.debug(netLogger.getMsg(event,"checkAccess: " + resourceName + 
                                                        ":" + permissionName + ":ALLUSERS"));
                        allUsers = true;
                        
                    } else {
                        // found a self-only constrained authorization, remember it
                        self=true;
                    }
                } else  { // pass all other constraints back as conditions
                    self = true;
                    this.log.debug(netLogger.getMsg(event, constraintName + " found for attribute " + 
                                                    attribute.getId()));
                    AuthConditionType authCond = new AuthConditionType();
                    authCond.setName(constraintName);
                    authCond.getConditionValue().add(auth.getConstraintValue());
                    authConditions.getAuthCondition().add(authCond);
                } 
            } // end of all authorizations for this attribute
        } // end of all attributes
        if (allUsers) {
            retVal.setPermission(AuthZConstants.ACCESS_ALLUSERS);
        } else if (self) {
            retVal.setPermission(AuthZConstants.ACCESS_SELFONLY);
            AuthConditionType authCond = new AuthConditionType();
            authCond.setName(AuthZConstants.PERMITTED_LOGIN);
            authCond.getConditionValue().add(loginId);
            authConditions.getAuthCondition().add(authCond);
        } else {
            this.log.info(netLogger.getMsg(event,"checkAccess: no auth found for " + 
                                           resourceName + ":" + permissionName));
            retVal.setPermission(AuthZConstants.ACCESS_DENIED);
        }
        if (! allUsers ) {  // let allUsers override site constraints
            retVal.setConditions(authConditions);
        } else {
            // return empty (but not null) conditions
            retVal.setConditions(new AuthConditions());
        }
        this.log.debug(netLogger.getMsg(event,"checkAccess: " + resourceName + ":" + 
                                        permissionName + ":" + retVal.getPermission()));
        for (AuthConditionType ac: authConditions.getAuthCondition()) {
            this.log.debug(netLogger.getMsg(event,"condition: " + ac.getName() + ": " + 
                                            ac.getConditionValue()));
        }
        
        return retVal;
    }
    /**
     * Finds all the domains that are controlled by the institution
     * @param institution String name of an institution
     * @return an array of all the domains at controlled by institution 
     */
    public List<String> getDomains(String institution) {
        SiteDAO siteDAO = new SiteDAO(this.dbname);
       
        return siteDAO.getDomains(institution);
    }
    /**
     *  Checks that the attributes, resource and permission all exist.
     *
     * @param attributes list of attributes for the user
     * @param resourceName a string identifying a resource
     * @param permissionName a string identifying a permission
     * @return Map with matching entries from the database.
     */
    private Map<String, Object>
        getARPObjects(List<Attribute> attributes, String resourceName,
                      String permissionName) {

        Map<String, Object> result = new HashMap<String, Object>();
        List<Attribute> dbattrs = new ArrayList<Attribute>();
        AttributeDAO attributeDAO = new AttributeDAO(this.dbname);
        // TODO:  query by attrId as well
        for (Attribute attr: attributes) {
            Attribute dbattr = (Attribute) attributeDAO.queryByParam("value", attr.getValue());
            if (dbattr != null) { 
                dbattrs.add(dbattr);
            }
        }
        result.put("attributes", dbattrs);

        ResourceDAO resourceDAO = new ResourceDAO(this.dbname);
        Resource resource = (Resource) resourceDAO.queryByParam("name", resourceName);
        if (resource == null) { return null; }
        result.put("resource", resource);

        PermissionDAO permissionDAO = new PermissionDAO(this.dbname);
        Permission permission = (Permission) permissionDAO.queryByParam("name", permissionName);
        if (permission == null) { return null; }
        result.put("permission", permission);
        return result;
    }
}

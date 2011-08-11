package net.es.oscars.resourceManager.common;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import net.es.oscars.api.soap.gen.v06.GlobalReservationId;
import net.es.oscars.api.soap.gen.v06.ListReply;
import net.es.oscars.api.soap.gen.v06.ListRequest;
import net.es.oscars.api.soap.gen.v06.OptionalConstraintType;
import net.es.oscars.api.soap.gen.v06.ResDetails;
import net.es.oscars.api.soap.gen.v06.ReservedConstraintType;
import net.es.oscars.api.soap.gen.v06.UserRequestConstraintType;
import net.es.oscars.common.soap.gen.AuthConditions;
import net.es.oscars.common.soap.gen.AuthConditionType;
import net.es.oscars.utils.sharedConstants.StateEngineValues;
import net.es.oscars.utils.sharedConstants.AuthZConstants;
import net.es.oscars.logging.ErrSev;
import net.es.oscars.logging.OSCARSNetLogger;
import net.es.oscars.resourceManager.common.URNParser;
import net.es.oscars.resourceManager.beans.PathElem;
import net.es.oscars.resourceManager.beans.ConstraintType;
import net.es.oscars.resourceManager.beans.PathType;
import net.es.oscars.resourceManager.beans.Path;
import net.es.oscars.resourceManager.beans.Reservation;
import net.es.oscars.resourceManager.beans.StdConstraint;
import net.es.oscars.resourceManager.dao.IdSequenceDAO;
import net.es.oscars.resourceManager.dao.ReservationDAO;
import net.es.oscars.resourceManager.http.WSDLTypeConverter;
import net.es.oscars.resourceManager.scheduler.RMReservationScheduler;
import net.es.oscars.resourceManager.scheduler.ReservationScheduler;

import oasis.names.tc.saml._2_0.assertion.AttributeType;

/* 
 *  This the methods in this class are called by RMSoapHandler which begins and commits
 *  the hibernate transactions. The object is stored in RMCore and used as a singleton
 */
public class ResourceManager {
    private Logger log;
    private StateEngine stateEngine;
    private String dbname;
    private String localDomainName;

    /**
     * Normal constructor
     * Only called by RMCore.getResourceManager which saves the object 
     * thus making this a singleton class
     * RMCore initializes dbname from resourceManager.yaml
     */
    public ResourceManager() {
        this.log = Logger.getLogger(this.getClass());
        RMCore core = RMCore.getInstance();
        this.dbname = core.getDbname();
        this.localDomainName = core.getLocalDomainId();
        this.stateEngine = core.getStateEngine();
    }
    /**
     * Constructor used by unit testing to override dbname to testrm
     * May not be necessary since tests are now configured to use in-memory sql
     * @param dbname
    */
    
    public ResourceManager(String dbname) {
        this.log = Logger.getLogger(this.getClass());
        RMCore core = RMCore.getInstance(dbname);
        this.dbname = dbname;
        this.localDomainName = core.getLocalDomainId();
        this.stateEngine = core.getStateEngine();
    }

    /**
     * Generates the next Global Resource Identifier, created from the local
     *  Domain's topology identifier and the next unused index in the IdSequence table.
     *  
     * @return a new GlobalReservationId
     * @throws RMException
     */
    public String generateGRI() throws RMException {
        
        String gri = null;
        IdSequenceDAO idDAO = new IdSequenceDAO(this.dbname);

        if (this.localDomainName == null) {
            throw new RMException ("no domain name configured ");
        }
        int id = idDAO.getNewId();
        gri = this.localDomainName + "-" + id;
        OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
        this.log.debug(netLogger.end("mgrGenerateGRI",gri));

        return gri;
    }
    
    /**
     * updateStatus
     *
     * @param gri GlobalReservationId of the reservation to update
     * @param insStatus new status string. May be of form "FAILED:reason"
     * @throws RMException if reservation is not found or state transition is not allowed
     */
    public String updateStatus(String gri, String inStatus) throws RMException {
        String status = inStatus;
        String errorMsg = null;
        if (inStatus.startsWith(StateEngineValues.FAILED+":")){
            // extract the reason 
            status = StateEngineValues.FAILED;
            errorMsg = inStatus.substring(7);
        }
        RMCore core = RMCore.getInstance();
        ReservationDAO resvDAO = new ReservationDAO(this.dbname);
        Reservation res = resvDAO.query(gri);
        if (errorMsg != null) {
            // append error message to description field
            String description = res.getDescription()+" FAILED: " + errorMsg;
            res.setDescription(description);
        }
        String outStatus = this.stateEngine.updateStatus(res,status);
        if (outStatus.equals(StateEngineValues.RESERVED) ||
                outStatus.equals(StateEngineValues.ACTIVE)) {
            // check to see if it should be scheduled
            ScanReservations scanRes = ScanReservations.getInstance(null);
            scanRes.scan();
        }
        return outStatus;
    }
    
    /**
     * store Stores (new) or updates (existing) a reservation
     * 
     * @param resDetails
     * @throws RMException
     */
    public void store(ResDetails resDetails) throws RMException {
        String event = "mgrStoreReservation";
        OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
        this.log.debug(netLogger.start(event));
        String gri = resDetails.getGlobalReservationId();
        ReservationDAO resvDAO = new ReservationDAO(this.dbname);
        Reservation res = null;
        boolean newRes = false;
        try {
            res = resvDAO.query(gri);
        } catch (RMException ex ) {
            if (res == null) {
                res = new Reservation();
                newRes = true;
            } 
        }
        try {
            if (newRes == false) {
                this.log.debug(netLogger.getMsg(event,"Request to change status from " + res.getStatus() + 
                              " to " + resDetails.getStatus()));
                String newStatus = stateEngine.canModifyStatus(res.getStatus(),resDetails.getStatus());
                this.log.debug(netLogger.getMsg(event,"Changing to status to " + newStatus));
                resDetails.setStatus(newStatus);
                update(res, resDetails);
            } else { // storing reservation for first time
                StdConstraint constraint = null;
                Map <String,StdConstraint> conMap = res.getConstraintMap();

                res.setGlobalReservationId(resDetails.getGlobalReservationId());
                res.setLogin(resDetails.getLogin());
                res.setCreatedTime(resDetails.getCreateTime());
                res.setDescription(resDetails.getDescription());
                res.setStatus(resDetails.getStatus());

                UserRequestConstraintType userConstraint = resDetails.getUserRequestConstraint();
                if (userConstraint == null){
                    throw new RMException("null UserConstraint not allowed");
                }
                constraint = WSDLTypeConverter.userRequest2StdConstraint(userConstraint);
                conMap.put(ConstraintType.USER,constraint);
                Long mbps = userConstraint.getBandwidth() * 1000000L; 
                res.setBandwidth(mbps);
                res.setStartTime(userConstraint.getStartTime());
                res.setEndTime(userConstraint.getEndTime());

                ReservedConstraintType reservedConstraint = resDetails.getReservedConstraint();
                if (reservedConstraint != null){
                    constraint = WSDLTypeConverter.reserved2StdConstraint(reservedConstraint);
                    conMap.put(ConstraintType.RESERVED,constraint);
                }
                res.setConstraintMap(conMap);
                /* Assume that on the initial store, a reservedConstraint will not have different
                 * times or bandwidth than the userConstraint
                 */

                List <OptionalConstraintType> oclist = resDetails.getOptionalConstraint();
                if (oclist != null && !oclist.isEmpty()) {
                    for (OptionalConstraintType oc: oclist) {
                        res.addOptConstraint(WSDLTypeConverter.OptionalConstraintType2OptConstraint(oc));
                    }
                }
            }
            resvDAO.update(res);
            if (res.getStatus().equals(StateEngineValues.RESERVED)) {
                // check to see if it should be scheduled
                ScanReservations scanRes = ScanReservations.getInstance(null);
                this.log.debug(netLogger.getMsg(event,"RMStore calling scan"));
                scanRes.scan();
            }
        } catch (RMException ex) {
            this.log.error(netLogger.error(event, ErrSev.MAJOR,ex.getMessage()));
            throw ex;
        }
        this.log.debug(netLogger.end(event));
    }
    /**
     * updates an existing reservation. If a field or element in resDetails is
     *      empty or null, does not update that element in the existing reservation
     * @param Res Reservation object that will be updated
     * @param resDetails Contains elements of the reservation to be modified
     */
    private void update(Reservation res, ResDetails resDetails) throws RMException {
        StdConstraint constraint = null;
        Map <String,StdConstraint> conMap = res.getConstraintMap();
        String event = "mgrUpdateReservaton";
        OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();

        this.log.debug(netLogger.start(event, "gri = " + res.getGlobalReservationId()));
        String input = null;
        long longInput = 0;

        if ((input=resDetails.getLogin()) != null) {
            res.setLogin(input);
        }
        if ((longInput=resDetails.getCreateTime()) != 0){
            res.setCreatedTime(longInput);
        }
        if ((input=resDetails.getDescription()) != null){
            res.setDescription(input);
        }
        if ((input=resDetails.getStatus()) != null) {
            res.setStatus(input);
        }
        UserRequestConstraintType userConstraint = resDetails.getUserRequestConstraint();
        if (userConstraint != null){
            constraint = conMap.get(ConstraintType.USER);
            if (constraint == null) {
                throw new RMException("stored reservation has null user constraint");
            }
            WSDLTypeConverter.updateStdConstraint (constraint, userConstraint, this.dbname);
            // update duplicated values in reservation in case  they were changed
            Long mbps = userConstraint.getBandwidth() * 1000000L; 
            if (mbps !=  null || mbps != 0) {
                res.setBandwidth(mbps);
            }
            if ((longInput=userConstraint.getStartTime()) != 0 ){
                res.setStartTime(longInput);
            }
            if ((longInput=userConstraint.getEndTime()) !=0){
                res.setEndTime(longInput);
            }
        }
        ReservedConstraintType reservedConstraint = resDetails.getReservedConstraint();
        if (reservedConstraint != null){
            constraint = conMap.get(ConstraintType.RESERVED);
            if (constraint == null) {
                constraint = WSDLTypeConverter.reserved2StdConstraint(reservedConstraint);
                conMap.put(ConstraintType.RESERVED,constraint);
            } else {
                WSDLTypeConverter.updateStdConstraint(constraint, reservedConstraint, this.dbname);
            }
            // update duplicated values in reservation in case  they were changed
            Long mbps = reservedConstraint.getBandwidth() * 1000000L; 
            if (mbps !=  null || mbps != 0) {
                res.setBandwidth(mbps);
            }
            if ((longInput=reservedConstraint.getStartTime()) != 0 ){
                res.setStartTime(longInput);
            }
            if ((longInput=reservedConstraint.getEndTime()) !=0){
                res.setEndTime(longInput);
            }
        }
        res.setConstraintMap(conMap);

        List <OptionalConstraintType> oclist = resDetails.getOptionalConstraint();
        if (oclist != null && !oclist.isEmpty()) {
            for (OptionalConstraintType oc: oclist) {
                res.addOptConstraint(WSDLTypeConverter.OptionalConstraintType2OptConstraint(oc));
            }
        }
        this.log.debug(netLogger.end(event));
    }

    /**
     * query returns details about the reservation specified by the gri
     * @param authConditions AuthConditions that may include restrictions on
     *     which reservations may be query. 
     * @param gri GlobalReservationId of reservation to be queried
     * @return ResDetails structure for the reservation
     * @throws RMException
     */
    public ResDetails query(AuthConditions authConditions,String gri) throws RMException , Exception{

        String event = "msgQueryReservation";
        OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
        this.log.debug(netLogger.start(event));
        //ResDetails resDetails = new ResDetails();
        ReservationDAO resvDAO = new ReservationDAO(this.dbname);
        Reservation res = null;
        StdConstraint constraint = null;
        Map <String,StdConstraint> conMap = new HashMap<String,StdConstraint>();
        Boolean internalPathAuthorized;
        // throws RMException if no reservation is found
        res = resvDAO.query(gri);
        String loginId = this.getPermittedLogin(authConditions);
        List<String> domains = getPermittedDomains(authConditions);
        if (loginId != null) {
            if (!res.getLogin().equals(loginId)){
                throw new RMException("access denied");
            }
        }
        if (domains != null ){
            if (!checkDomains(res,domains,netLogger,event)){
                throw new RMException("access denied");
            }
        }
        internalPathAuthorized = internalHopsAllowed(authConditions);
        ResDetails resDetails = res2resDetails(res, internalPathAuthorized, netLogger, event);
 
        this.log.debug(netLogger.end(event));
        return resDetails;
    }
    
    /**
     * cancel called by Coordinator via RMSoapHandler. Checks that user is authorized to cancel 
     * this reservation and that the reservation is in a state that allows cancellation.
     * Returns details about the reservation to the coordinator. Reservation will be removed
     * from the RMReservationScheduler queue if it is there. 
     *  
     * @param authConditions AuthConditions that may include restrictions on
     *     which reservations may be canceled. 
     * @param gri GlobalReservationId of reservation to be canceled
     * @return ResDetails structure for the reservation
     * @throws RMException
     */
    public ResDetails cancel(AuthConditions authConditions,String gri) throws RMException , Exception{

        String event = "mgrCancelReservation";
        OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
        this.log.debug(netLogger.start(event));
        ResDetails resDetails = null;
        ReservationDAO resvDAO = new ReservationDAO(this.dbname);
        Reservation res = null;
        StdConstraint constraint = null;
        Map <String,StdConstraint> conMap = new HashMap<String,StdConstraint>();
        Boolean internalPathAuthorized = true; // Coord needs complete path to send to PCECancel
        // throws RMException if no reservation is found
        synchronized (RMReservationScheduler.schedLock){
            RMReservationScheduler.schedLock = "cancelReservation";
            res = resvDAO.query(gri);
            String loginId = this.getPermittedLogin(authConditions);
            List<String> domains = getPermittedDomains(authConditions);
            if (loginId != null) {
                if (!res.getLogin().equals(loginId)){
                    throw new RMException("access denied");
                }
            }
            if (domains != null ){
                if (!checkDomains(res,domains,netLogger, event)){
                    this.log.info(netLogger.getMsg(event,"user " + loginId + " not allowed to cancel this reservation: " + gri));
                    throw new RMException("access denied");
                }
            }

            String status = res.getStatus();
            boolean finished = false;
            String newStatus = null;
            try {
                newStatus = StateEngine.canModifyStatus(status,StateEngineValues.INCANCEL);

            } catch (RMException ex) {
                if (status.equals(StateEngineValues.ACCEPTED)) {
                    stateEngine.updateStatus(res, StateEngineValues.CANCELLED);
                    finished = true;
                } else {
                    throw ex;  // status is FAILED or FINISHED
                }
            }
            resDetails = res2resDetails(res, internalPathAuthorized,netLogger, event);
            if (!finished) {
                RMReservationScheduler.getInstance().forget(resDetails);
            }
            RMReservationScheduler.schedLock = "unlocked";
        } // end synchronized block
        
        this.log.debug(netLogger.end(event));
        return resDetails;
    }
    
    /**
     * modifyReservation called by Coordinator. Checks that user is authorized to modify this reservation and
     * that the reservation is in a state that allows modification.. 
     *  returns details about the reservation to the coordinator
     *  Finally sets the state to INMODIFY
     * @param authConditions AuthConditions that may include restrictions on
     *     which reservations may be modified. 
     * @param gri GlobalReservationId of reservation to be queried
     * @return ResDetails structure for the reservation
     * @throws RMException
     */
    public ResDetails modify(AuthConditions authConditions,String gri) throws RMException , Exception{

        String event = "mgrModifyReservation";
        OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
        this.log.debug(netLogger.start(event));
        ResDetails resDetails = null;
        ReservationDAO resvDAO = new ReservationDAO(this.dbname);
        Reservation res = null;
        StdConstraint constraint = null;
        Map <String,StdConstraint> conMap = new HashMap<String,StdConstraint>();
        Boolean internalPathAuthorized = true; // Coord needs complete path to send to PCEModify
        synchronized (RMReservationScheduler.schedLock){
            RMReservationScheduler.schedLock = "modifyReservation";
            // throws RMException if no reservation is found
            res = resvDAO.query(gri);
            String loginId = this.getPermittedLogin(authConditions);
            List<String> domains = getPermittedDomains(authConditions);
            if (loginId != null) {
                if (!res.getLogin().equals(loginId)){
                    throw new RMException("access denied");
                }
            }
            if (domains != null ){
                if (!checkDomains(res,domains,netLogger, event)){
                    this.log.info(netLogger.getMsg(event,"user " + loginId + " not allowed to modify this reservation: " + gri));
                    throw new RMException("access denied");
                }
            }

            String status = res.getStatus();
            boolean finished = false;
            String newStatus = null;
            try {
                newStatus = StateEngine.canModifyStatus(status,StateEngineValues.INMODIFY);
            } catch (RMException ex) {
                throw ex; // may want to do something else as well
            }
            resDetails = res2resDetails(res, internalPathAuthorized, netLogger, event);

            if (!finished) {
                ReservationScheduler scheduler = RMReservationScheduler.getInstance();
                scheduler.forget(resDetails);
                stateEngine.updateStatus(res,newStatus);
            }
            RMReservationScheduler.schedLock = "unlocked";
        }// end synchronized block
        this.log.debug(netLogger.end(event));
        return resDetails;
    }
    /**
     * list returns details about the reservations that match the input constraints
     * @param authConditions AuthConditions that may include restrictions on
     *     which reservations may be listed. 
     * @param numRequested int with the number of reservations to return
     * @param resOffset int with the offset into the list
     *
     * @param statuses a list of reservation statuses. If not null or empty,
     * results will only include reservations with one of these statuses.
     * If null / empty, results will include reservations with any status.
     *
     * @param linkIds a list of link id's. If not null / empty, results will
     * only include reservations whose path includes at least one of the links.
     * If null / empty, results will include reservations with any path.
     *
     * @param vlanTags a list of VLAN tags.  If not null or empty,
     * results will only include reservations where (currently) the first link
     * in the path has a VLAN tag from the list (or ranges in the list).  If
     * null / empty, results will include reservations with any associated
     * VLAN.
     *
     * @param startTime the start of the time window to look in; null for
     * everything before the endTime
     *
     * @param endTime the end of the time window to look in; null for everything after the startTime,
     * leave both start and endTime null to disregard time
     * 
     * @return reservations list of reservations that user is allowed to see. List
     *     may be empty
     * @throws RMException
     */
    
    public List<ResDetails> list(AuthConditions authConditions, int numRequested, int resOffset,
            List<String> statuses, String description, List<String> linkIds,
            List<String> vlanTags,  Long startTime, Long endTime)
            throws RMException {

        String event = "mgrListReservations";
        OSCARSNetLogger netLogger = OSCARSNetLogger.getTlogger();
        String reqLogin = null;
        List<String> reqDomains = null;
        List<Reservation> reservations = null;
        Boolean internalPathAuthorized = false;
        List<String> loginIds = new ArrayList<String>();
        List<Reservation> authResv = new ArrayList<Reservation>();
        List<ResDetails> resDetailsList = new ArrayList<ResDetails>();
        this.log.debug(netLogger.start(event));

        reqLogin = getPermittedLogin(authConditions);
        if (reqLogin != null){
            // get only reservations that belong to this user
            loginIds.add(reqLogin);
        }
        ReservationDAO dao = new ReservationDAO(this.dbname);
        reservations = dao.list(numRequested, resOffset, loginIds, statuses,
                description, vlanTags, startTime, endTime); 
        if (linkIds != null && !linkIds.isEmpty()) {
            Map<String, Pattern> patterns = new HashMap<String,Pattern>();
            for (String id: linkIds) {
                patterns.put(id, Pattern.compile(".*" + id + ".*"));
            }
            ArrayList<Reservation> removeThese = new ArrayList<Reservation>();
            for (Reservation rsv : reservations) {
                Path path = this.getPath(rsv);
               
                boolean matches =this.matches(path, patterns);
                if (!matches) {
                    this.log.debug(netLogger.getMsg(event,"not returning: " +
                            rsv.getGlobalReservationId()));
                    removeThese.add(rsv);
                }
            }
            for (Reservation rsv : removeThese) {
                reservations.remove(rsv);
            }
        }
        reqDomains = getPermittedDomains(authConditions);
        if (reqDomains != null) {
            ArrayList<Reservation> removeThese = new ArrayList<Reservation>();
            // keep reservations that start or terminate at institution
            // or belong to this user
            this.log.debug(netLogger.getMsg(event,"Checking " + reservations.size() + 
                            " reservations for site"));
            for (Reservation resv : reservations) {
                if (!checkDomains(resv,reqDomains,netLogger, event)) {
                    removeThese.add(resv);
                }
            }
            for (Reservation rsv : removeThese) {
                reservations.remove(rsv);
            }
        }
        for ( Reservation resv : reservations ){
            resDetailsList.add(res2resDetails(resv,internalPathAuthorized,netLogger, event));
        }
        this.log.debug(netLogger.end(event));
        return resDetailsList;
    }

    /**
     * Returns the reserved path for a reservation if there is a reservedConstraint
     * otherwise returns the requested path.
     * @param resv
     * @return Either the reserved path if one exists or else the requested path
     * @throws RMException
     */
    public Path getPath(Reservation resv) throws RMException {

        StdConstraint constraint = resv.getConstraint(ConstraintType.RESERVED);
        if (constraint != null) {
            return constraint.getPath();
        }
        constraint = resv.getConstraint(ConstraintType.USER);
        if (constraint != null) {
            return constraint.getPath();
        }
        return null;
    }
    /**
     * Matches if any hop in the path has a topology identifier that at
     * least partially matches a link id.
     *
     * @param path path to check
     * @param patterns map from linkIds to compiled Patterns
     */
    public boolean matches(Path path, Map<String, Pattern> patterns) {
     // TODO probably needs work
        List<PathElem> pathElems = path.getPathElems();
        StringBuilder sb = new StringBuilder();
        for (PathElem pathElem: pathElems) {
            //String topologyIdent = pathElem.getLink().getFQTI();
            String topologyIdent = pathElem.getUrn();
            sb.append(topologyIdent);
            String localIdent = URNParser.abbreviate(topologyIdent);
            sb.append(localIdent);
        }
        for (Pattern pattern: patterns.values()) {
            Matcher matcher = pattern.matcher(sb.toString());
            if (matcher.matches()) {
                return true;
            }
        }
        return false;
    }
    /**
     *  Check to see if either the src or destination of the reservation is in an allowed domain
     * @param res reservation 
     * @param domains String array containing all the domains allowed for this request
     * @return true, if this reservation is allowed for this request, false otherwise
     */
    public boolean checkDomains(Reservation res, List<String> domains,OSCARSNetLogger netLogger,String event) throws RMException
    {
        Path reqPath = this.getPath(res);
        List<PathElem> pathElems = reqPath.getPathElems();
        String srcURN = pathElems.get(0).getUrn();
        String src = parseURN(srcURN,"domain");
        String destURN = pathElems.get(pathElems.size()-1).getUrn();
        String dest = parseURN(destURN,"domain");
        if (dest == null || src == null ) {
            log.debug( netLogger.error(event,ErrSev.MINOR,
                        "incorrect path in database for reservation res.getGlobalReservationId()"));
        }
        for (String dom : domains) {
            if (dom.equals(src) ||
                    dom.equals(dest)) {
                return true;
            }
        }
        return false;
    }
    private String parseURN(String urn, String value){
        String parts[] = urn.split(":");
        for (int i= 0; i < parts.length; i++){
            if (parts[i].startsWith( value + "=")) {
                return parts[i].substring(value.length() +1);
            }
        }
        return null;
    }
    /**
     *  Convert a reservation to a ResDetails for the soap message reply
     * @param resv input reservation
     * @return ResDetails structure
     */
    public ResDetails res2resDetails(Reservation res, Boolean internalPathAuthorized, 
                                     OSCARSNetLogger netLogger,String event) 
        throws RMException {

        ResDetails resDetails = new ResDetails();
        StdConstraint constraint = null;
        Map <String,StdConstraint> conMap = new HashMap<String,StdConstraint>();
        String gri = res.getGlobalReservationId();
        resDetails.setGlobalReservationId(gri);
        resDetails.setLogin(res.getLogin());
        resDetails.setCreateTime(res.getCreatedTime()); 
        resDetails.setDescription(res.getDescription());
        resDetails.setStatus(res.getStatus());
        conMap = res.getConstraintMap();
        constraint = conMap.get(ConstraintType.USER);
        if (constraint == null) {
            this.log.error(netLogger.error(event,ErrSev.MINOR,
                        "no USER constraint for reservation in database: " + gri));
            throw  new RMException("no USER constraint for reservation: " + gri);
        }
        UserRequestConstraintType uc = WSDLTypeConverter.stdConstraint2UserRequest(constraint,
                internalPathAuthorized,this.localDomainName);
        resDetails.setUserRequestConstraint(uc);
        StdConstraint resvConstraint = conMap.get(ConstraintType.RESERVED);
        if (resvConstraint != null) {
            ReservedConstraintType rt = WSDLTypeConverter.stdConstraint2ReservedConstraint(resvConstraint,
                    internalPathAuthorized,this.localDomainName);
            resDetails.setReservedConstraint(rt);
        }
        return resDetails;
    }
    
    /**
     *  Check to see if there is an authConditions requiring a specific loginId
     * @param authConditions
     * @return required Login Id, or null if there is not one
     */
    public String getPermittedLogin(AuthConditions authConditions) {
        String reqLoginId = null;
        if (authConditions == null) {
            return reqLoginId;
        }
        for (AuthConditionType authCond: authConditions.getAuthCondition()){
            if (authCond.getName().equals("permittedLoginId")) {
                reqLoginId = authCond.getConditionValue().get(0);
            }
        }
        return reqLoginId;
    }
    /**
     *  check to see if there is a authConditions requiring specific domains
     * @param authConditions
     * @return required domains, or null if there is not one
     */
    public List<String> getPermittedDomains(AuthConditions authConditions) {
        List<String> reqDomains = null;
        if (authConditions == null ){
            return reqDomains;
        }
        for (AuthConditionType authCond: authConditions.getAuthCondition()){
            if (authCond.getName().equals(AuthZConstants.PERMITTED_DOMAINS)) {
                reqDomains = authCond.getConditionValue();
                break;
            }
        }
        return reqDomains;
    }
    
    /**
     *  Check to see if there is an authConditions that allows user to see internal hops
     * @param authConditions
     * @return true if such a condition exists, false otherwise
     */
    public Boolean internalHopsAllowed(AuthConditions authConditions) {
        Boolean hopsAllowed = false;
        if (authConditions == null ){
            return hopsAllowed;
        }
        for (AuthConditionType authCond: authConditions.getAuthCondition()){
            if (authCond.getName().equals(AuthZConstants.INT_HOPS_ALLOWED)) {
                if ( authCond.getConditionValue().get(0).equals("true") )
                    hopsAllowed = true;
                break;
            }
        }
        return hopsAllowed;
    }
}
package net.es.oscars.pss.validate;

import java.util.Map;

import org.ogf.schema.network.topology.ctrlplane.CtrlPlaneHopContent;
import org.ogf.schema.network.topology.ctrlplane.CtrlPlanePathContent;

import net.es.oscars.api.soap.gen.v06.PathInfo;
import net.es.oscars.pss.api.PSSException;
import net.es.oscars.pss.api.PSSRequest;
import net.es.oscars.pss.api.ValidationAgent;
import net.es.oscars.pss.util.URNParser;
import net.es.oscars.pss.util.URNParserResult;
import net.es.oscars.pss.util.URNParserResult.URNType;

public class SimpleValidator implements ValidationAgent {
    private String localDomainId;

    public String getLocalDomainId() {
        return localDomainId;
    }

    public void setLocalDomainId(String localDomainId) {
        this.localDomainId = localDomainId;
    }

    public void validate(PSSRequest req) throws PSSException {
        if (localDomainId == null) {
            throw new PSSException("Local domain ID not set");
        }
        String gri = null;
        PathInfo pathInfo = null;
        if (req.getSetupReq() != null) {
            gri = req.getSetupReq().getReservation().getGlobalReservationId();
            pathInfo = req.getSetupReq().getReservation().getReservedConstraint().getPathInfo();
        } else if (req.getTeardownReq() != null) {
            gri = req.getTeardownReq().getReservation().getGlobalReservationId();
            pathInfo = req.getTeardownReq().getReservation().getReservedConstraint().getPathInfo();
        } else {
            return;
        }

        if (gri == null) {
            throw new PSSException("GRI must be set");
        }


        if (pathInfo == null ) {
            throw new PSSException("PathInfos must be defined and not empty");
        }
        CtrlPlanePathContent path = pathInfo.getPath();
        if (path == null || path.getHop() == null || path.getHop().isEmpty() || path.getHop().size() < 2) {
            throw new PSSException("path must be defined, not empty, and with at least 2 hops");
        }

        for (CtrlPlaneHopContent hop : path.getHop()) {
            URNParserResult parseRes = URNParser.parseTopoIdent(hop.getLinkIdRef(), localDomainId);
            if (!parseRes.getType().equals(URNType.LINK)) {
                throw new PSSException("path must comprise of link ids");
            }
        }
        return;
    }

    @SuppressWarnings("unchecked")
    public void setOptions(Map options) {
        // TODO Auto-generated method stub

    }

}

package net.es.oscars.pss.api;

public class PSSAction {
    public enum PSSActionTypes {
        SETUP,
        TEARDOWN,
        MODIFY,
        STATUS,
    }

    private PSSActionTypes actionType;
    private PSSRequest request;
    private PSSActionStatus status;

    public boolean equals(PSSAction other) {
        if (other == null) return false;
        if (other.getRequest().equals(request)) {
            return other.getActionType().equals(actionType);
        } else {
            return false;
        }
    }

    public void setActionType(PSSActionTypes opType) {
        this.actionType = opType;
    }
    public PSSActionTypes getActionType() {
        return actionType;
    }
    public void setRequest(PSSRequest request) {
        this.request = request;
    }
    public PSSRequest getRequest() {
        return request;
    }
    public void setStatus(PSSActionStatus status) {
        this.status = status;
    }
    public PSSActionStatus getStatus() {
        return status;
    }

}

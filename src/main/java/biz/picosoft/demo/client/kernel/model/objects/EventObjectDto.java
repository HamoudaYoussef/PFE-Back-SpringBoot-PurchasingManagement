package biz.picosoft.demo.client.kernel.model.objects;

import java.time.ZonedDateTime;

public class EventObjectDto {
    private ZonedDateTime evtDate;
    private String eventType;
    private String eventLabel;
    private Boolean sendEmail;
    private Boolean CallRest;
    private Boolean sendNotif;
    private Boolean fctCall;
    private Boolean rpCall;

    public Boolean getSendEmail() {
        return sendEmail;
    }

    public void setSendEmail(Boolean sendEmail) {
        this.sendEmail = sendEmail;
    }

    public Boolean getCallRest() {
        return CallRest;
    }

    public void setCallRest(Boolean callRest) {
        CallRest = callRest;
    }

    public Boolean getSendNotif() {
        return sendNotif;
    }

    public void setSendNotif(Boolean sendNotif) {
        this.sendNotif = sendNotif;
    }

    public Boolean getFctCall() {
        return fctCall;
    }

    public void setFctCall(Boolean fctCall) {
        this.fctCall = fctCall;
    }

    public Boolean getRpCall() {
        return rpCall;
    }

    public void setRpCall(Boolean rpCall) {
        this.rpCall = rpCall;
    }

    public ZonedDateTime getEvtDate() {
        return evtDate;
    }

    public void setEvtDate(ZonedDateTime evtDate) {
        this.evtDate = evtDate;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventLabel() {
        return eventLabel;
    }

    public void setEventLabel(String eventLabel) {
        this.eventLabel = eventLabel;
    }
}

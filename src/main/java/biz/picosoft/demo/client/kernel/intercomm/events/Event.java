package biz.picosoft.demo.client.kernel.intercomm.events;

import biz.picosoft.demo.config.audit.Auditable;
import org.springframework.data.geo.Point;

import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * A Event.
 */

public class Event extends Auditable implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private ZonedDateTime evtDate;

    private ZonedDateTime planDate;

    private Long objectId;

    private String data;

    private Boolean sendEmail = false;

    private Boolean CallRest = false;

    private Boolean sendNotif = false;

    private Boolean fctCall = false;

    private Boolean rpCall = false;

    private Boolean process = false;

    private int nbrRestCallEvents;

    private int nbrNotifications;

    private int nbrEmailEvents;

    private int nbrFctCallEvents;

    private int nbrJrxmlEvents;

    private String modulePackageName;

    private String className;

    private String eventType;

    private RefEventType refEventTypeEvent;

    private Point position;

    private Event event;

    public Event() {

    }

    public Event(Long objectId, String data, @Null ZonedDateTime planDate) {
        this.objectId = objectId;
        this.data = data;
        if (planDate != null) {
            this.planDate = planDate;
            this.process = false;
        } else {
            this.evtDate = ZonedDateTime.now();
            this.process = true;
        }
    }

    public static String toString(List<Event> eventList) {
        String to = "";
        for (Event event : eventList) to.concat(event.toString());
        return to;
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

    public ZonedDateTime getPlanDate() {
        return planDate;
    }

    public void setPlanDate(ZonedDateTime planDate) {
        this.planDate = planDate;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getEvtDate() {
        return evtDate;
    }

    public void setEvtDate(ZonedDateTime evtDate) {
        this.evtDate = evtDate;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Event evtDate(ZonedDateTime evtDate) {
        this.evtDate = evtDate;
        return this;
    }

    public int getNbrRestCallEvents() {
        return nbrRestCallEvents;
    }

    public void setNbrRestCallEvents(int nbrRestCallEvents) {
        this.nbrRestCallEvents = nbrRestCallEvents;
    }

    public int getNbrNotifications() {
        return nbrNotifications;
    }

    public void setNbrNotifications(int nbrNotifications) {
        this.nbrNotifications = nbrNotifications;
    }

    public int getNbrEmailEvents() {
        return nbrEmailEvents;
    }

    public void setNbrEmailEvents(int nbrEmailEvents) {
        this.nbrEmailEvents = nbrEmailEvents;
    }

    public int getNbrFctCallEvents() {
        return nbrFctCallEvents;
    }

    public void setNbrFctCallEvents(int nbrFctCallEvents) {
        this.nbrFctCallEvents = nbrFctCallEvents;
    }

    public int getNbrJrxmlEvents() {
        return nbrJrxmlEvents;
    }

    public void setNbrJrxmlEvents(int nbrJrxmlEvents) {
        this.nbrJrxmlEvents = nbrJrxmlEvents;
    }

    public String getModulePackageName() {
        return modulePackageName;
    }

    public void setModulePackageName(String modulePackageName) {
        this.modulePackageName = modulePackageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Boolean isProcess() {
        return process;
    }

    public Event process(Boolean process) {
        this.process = process;
        return this;
    }

    public RefEventType getRefEventTypeEvent() {
        return refEventTypeEvent;
    }

    public void setRefEventTypeEvent(RefEventType refEventType) {
        this.refEventTypeEvent = refEventType;
    }

    public Event refEventTypeEvent(RefEventType refEventType) {
        this.refEventTypeEvent = refEventType;
        return this;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

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

    public String getData() {
        return data;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public void setData(String data) {
        this.data = data;
    }

    public Boolean getProcess() {
        return process;
    }

    public void setProcess(Boolean process) {
        this.process = process;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Event)) {
            return false;
        }
        return id != null && id.equals(((Event) o).id);
    }

    public ZonedDateTime getRemindDate() {
        return planDate;
    }

    public void setRemindDate(ZonedDateTime remindDate) {
        this.planDate = remindDate;
    }
}

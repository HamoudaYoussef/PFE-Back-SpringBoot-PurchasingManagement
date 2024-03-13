package biz.picosoft.demo.client.kernel.model.objects;


import biz.picosoft.demo.config.audit.Auditable;

import java.io.Serializable;

/**
 * A StateMetric.
 */




public class StateMetric extends Auditable implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long classId;

    private Long objectId;

    private Long statId;

    private String classeName;

    private String stateLabel;

    private Long duration;


    public StateMetric(Long classId, Long objectId, Long statId, String classeName, String stateLabel, Long duration) {
        this.classId = classId;
        this.objectId = objectId;
        this.statId = statId;
        this.classeName = classeName;
        this.stateLabel = stateLabel;
        this.duration = duration;
    }

    public StateMetric() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public Long getStatId() {
        return statId;
    }

    public void setStatId(Long statId) {
        this.statId = statId;
    }

    public String getClasseName() {
        return classeName;
    }

    public void setClasseName(String classeName) {
        this.classeName = classeName;
    }

    public String getStateLabel() {
        return stateLabel;
    }

    public void setStateLabel(String stateLabel) {
        this.stateLabel = stateLabel;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StateMetric{" +
            "id=" + getId() +
            ", classId=" + getClassId() +
            ", objectId=" + getObjectId() +
            ", statId=" + getStatId() +
            ", classeName='" + getClasseName() + "'" +
            ", stateLabel='" + getStateLabel() + "'" +
            ", duration='" + getDuration() + "'" +
            "}";
    }
}

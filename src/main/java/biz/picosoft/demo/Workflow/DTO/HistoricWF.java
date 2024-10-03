package biz.picosoft.demo.Workflow.DTO;

import java.time.ZonedDateTime;


public class HistoricWF {
    private String activityName;
    private String decisionName;
    private String activityAssignee;
    private ZonedDateTime activityStartTime;
    private ZonedDateTime activityEndTime;
    private String activityDescription;
    private String doneBy;
    private String effectiveUser;

    public HistoricWF(String activityName, String decisionName, String activityAssignee, ZonedDateTime activityStartTime,
                      ZonedDateTime activityEndTime, String activityDescription, String doneBy,String effectiveUser) {
        this.activityName = activityName;
        this.decisionName = decisionName;
        this.activityAssignee = activityAssignee;
        this.activityStartTime = activityStartTime;
        this.activityEndTime = activityEndTime;
        this.activityDescription = activityDescription;
        this.doneBy = doneBy;
        this.effectiveUser=effectiveUser;
    }

    public String getDoneBy() {
        return doneBy;
    }

    public void setDoneBy(String doneBy) {
        this.doneBy = doneBy;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getDecisionName() {
        return decisionName;
    }

    public void setDecisionName(String decisionName) {
        this.decisionName = decisionName;
    }

    public String getActivityAssignee() {
        return activityAssignee;
    }

    public void setActivityAssignee(String activityAssignee) {
        this.activityAssignee = activityAssignee;
    }

    public ZonedDateTime getActivityStartTime() {
        return activityStartTime;
    }

    public void setActivityStartTime(ZonedDateTime activityStartTime) {
        this.activityStartTime = activityStartTime;
    }

    public ZonedDateTime getActivityEndTime() {
        return activityEndTime;
    }

    public void setActivityEndTime(ZonedDateTime activityEndTime) {
        this.activityEndTime = activityEndTime;
    }

    public String getActivityDescription() {
        return activityDescription;
    }

    public void setActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription;
    }

    public String getEffectiveUser() {
        return effectiveUser;
    }

    public void setEffectiveUser(String effectiveUser) {
        this.effectiveUser = effectiveUser;
    }
}

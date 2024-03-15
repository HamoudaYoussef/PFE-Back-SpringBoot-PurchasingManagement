package biz.picosoft.demo.client.kernel.model.objects;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;


public class WFDTO implements Serializable {

    private List<HistoricWF> historicWF;

    private List<String> decisionsWF;

    private List<String> activityAuthor;

    private List<String> activityReader;

    private List<String> processAuthor;

    private List<String> processReader;

    private String activityName;

    private String assignee;

    private ZonedDateTime activitystartDate;

    private ZonedDateTime processStartDate;
    private ZonedDateTime processEndDate;


    private String wfProcessID;


    public List<HistoricWF> getHistoricWF() {
        return historicWF;
    }

    public void setHistoricWF(List<HistoricWF> historicWF) {
        this.historicWF = historicWF;
    }

    public List<String> getDecisionsWF() {
        return decisionsWF;
    }

    public void setDecisionsWF(List<String> decisionsWF) {
        this.decisionsWF = decisionsWF;
    }

    public List<String> getActivityAuthor() {
        return activityAuthor;
    }

    public void setActivityAuthor(List<String> activityAuthor) {
        this.activityAuthor = activityAuthor;
    }

    public List<String> getActivityReader() {
        return activityReader;
    }

    public void setActivityReader(List<String> activityReader) {
        this.activityReader = activityReader;
    }

    public List<String> getProcessAuthor() {
        return processAuthor;
    }

    public void setProcessAuthor(List<String> processAuthor) {
        this.processAuthor = processAuthor;
    }

    public List<String> getProcessReader() {
        return processReader;
    }

    public void setProcessReader(List<String> processReader) {
        this.processReader = processReader;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public ZonedDateTime getActivitystartDate() {
        return activitystartDate;
    }

    public void setActivitystartDate(ZonedDateTime activitystartDate) {
        this.activitystartDate = activitystartDate;
    }

    public ZonedDateTime getProcessStartDate() {
        return processStartDate;
    }

    public void setProcessStartDate(ZonedDateTime processStartDate) {
        this.processStartDate = processStartDate;
    }

    public String getWfProcessID() {
        return wfProcessID;
    }

    public void setWfProcessID(String wfProcessID) {
        this.wfProcessID = wfProcessID;
    }

    public ZonedDateTime getProcessEndDate() {
        return processEndDate;
    }

    public void setProcessEndDate(ZonedDateTime processEndDate) {
        this.processEndDate = processEndDate;
    }
}

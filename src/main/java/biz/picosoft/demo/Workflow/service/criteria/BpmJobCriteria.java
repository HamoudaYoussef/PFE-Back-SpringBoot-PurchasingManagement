package biz.picosoft.demo.Workflow.service.criteria;





import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

import java.io.Serializable;


public class BpmJobCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter processID;
    private StringFilter activityName;
    private StringFilter assignee;
    private ZonedDateTimeFilter activityDueDate;
    private LongFilter objectID;
    private LongFilter classID;
    private BooleanFilter endProcess;

    public BpmJobCriteria(){
    }

    public BpmJobCriteria(BpmJobCriteria other){
        this.id = other.id == null ? null : other.id.copy();

    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getProcessID() {
        return processID;
    }

    public void setProcessID(StringFilter processID) {
        this.processID = processID;
    }

    public StringFilter getActivityName() {
        return activityName;
    }

    public void setActivityName(StringFilter activityName) {
        this.activityName = activityName;
    }

    public StringFilter getAssignee() {
        return assignee;
    }

    public void setAssignee(StringFilter assignee) {
        this.assignee = assignee;
    }

    public ZonedDateTimeFilter getActivityDueDate() {
        return activityDueDate;
    }

    public void setActivityDueDate(ZonedDateTimeFilter activityDueDate) {
        this.activityDueDate = activityDueDate;
    }

    public LongFilter getObjectID() {
        return objectID;
    }

    public void setObjectID(LongFilter objectID) {
        this.objectID = objectID;
    }

    public LongFilter getClassID() {
        return classID;
    }

    public void setClassID(LongFilter classID) {
        this.classID = classID;
    }

    public BooleanFilter getEndProcess() {
        return endProcess;
    }

    public void setEndProcess(BooleanFilter endProcess) {
        this.endProcess = endProcess;
    }

    @Override
    public BpmJobCriteria copy() {
        return new BpmJobCriteria(this);
    }

}

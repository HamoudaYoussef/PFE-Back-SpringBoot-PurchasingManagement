package biz.picosoft.demo.Workflow.domain;


import biz.picosoft.demo.config.audit.Auditable;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.envers.Audited;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Entity
@Table(name = "bpm_job", uniqueConstraints = @UniqueConstraint(columnNames = {"object_id", "class_id"}))
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Audited
@EntityListeners(AuditingEntityListener.class)
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
public class BpmJob extends Auditable implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "process_id", nullable = false, unique = true)
    private String processID;
    @Column(name = "activity_name")
    private String activityName;
    @Column(name = "assignee")
    private String assignee;
    @Column(name = "activity_due_date")
    private ZonedDateTime activityDueDate;
    @Column(name = "object_id")
    private Long objectID;
    @Column(name = "class_id")
    private Long classID;
    @Column(name = "end_process")
    private Boolean endProcess;


    @Type(type = "jsonb")
    @Column(name = "data", columnDefinition = "json")
    private String data;
    @Transient
    private Object dataObject;
    @Transient
    private List<String> authors;
    @Transient
    private List<String> readers;
    @Transient
    private String processName;

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public Long getId() {
          return id;
      }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProcessID() {
        return processID;
    }

    public void setProcessID(String processID) {
        this.processID = processID;
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

    public ZonedDateTime getActivityDueDate() {
        return activityDueDate;
    }

    public void setActivityDueDate(ZonedDateTime activityDueDate) {
        this.activityDueDate = activityDueDate;
    }

    public Long getObjectID() {
        return objectID;
    }

    public void setObjectID(Long objectID) {
        this.objectID = objectID;
    }

    public Long getClassID() {
        return classID;
    }

    public void setClassID(Long classID) {
        this.classID = classID;
    }

    public Boolean getEndProcess() {
        return endProcess;
    }

    public void setEndProcess(Boolean endProcess) {
        this.endProcess = endProcess;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public List<String> getReaders() {
        return readers;
    }

    public void setReaders(List<String> readers) {
        this.readers = readers;
    }

    public Object getDataObject() {
        return dataObject;
    }

    public void setDataObject(Object dataObject) {
        this.dataObject = dataObject;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        object.put("id", this.id != null? this.id : JSONObject.NULL);
        object.put("processID", this.processID != null? this.processID : JSONObject.NULL);
        object.put("activityName", this.activityName != null? this.activityName : JSONObject.NULL);
        object.put("assignee", this.assignee != null? this.assignee : JSONObject.NULL);
        object.put("activityDueDate", this.activityDueDate != null? this.activityDueDate : JSONObject.NULL);
        object.put("objectID", this.objectID != null? this.objectID : JSONObject.NULL);
        object.put("classID", this.classID != null? this.classID : JSONObject.NULL);
        object.put("endProcess", this.endProcess != null? this.endProcess : JSONObject.NULL);
        object.put("data", this.data != null? new JSONObject(this.data) : JSONObject.NULL);
        return object;
    }

    public org.json.simple.JSONObject toJSONSimple() throws ParseException {
        org.json.simple.JSONObject object = new org.json.simple.JSONObject();
        object.put("id", this.id != null? this.id : null);
        object.put("processID", this.processID != null? this.processID : null);
        object.put("activityName", this.activityName != null? this.activityName : null);
        object.put("assignee", this.assignee != null? this.assignee : null);
        object.put("activityDueDate", this.activityDueDate != null? this.activityDueDate : null);
        object.put("objectID", this.objectID != null? this.objectID : null);
        object.put("classID", this.classID != null? this.classID : null);
        object.put("endProcess", this.endProcess != null? this.endProcess : null);
        object.put("data", this.data != null? ((org.json.simple.JSONObject) new JSONParser().parse(this.data)) : null);
        return object;
    }

    public Map<String, Object> toVariables() throws ParseException {
        Map<String, Object> object = new HashMap<>();
        object.put("id", this.id != null? this.id : null);
        object.put("processID", this.processID != null? this.processID : null);
        object.put("activityName", this.activityName != null? this.activityName : null);
        object.put("assignee", this.assignee != null? this.assignee : null);
        object.put("activityDueDate", this.activityDueDate != null? this.activityDueDate : null);
        object.put("objectID", this.objectID != null? this.objectID : null);
        object.put("classID", this.classID != null? this.classID : null);
        object.put("endProcess", this.endProcess != null? this.endProcess : null);
        object.put("data", this.data != null? ((org.json.simple.JSONObject) new JSONParser().parse(this.data)) : null);
        return object;
    }
}


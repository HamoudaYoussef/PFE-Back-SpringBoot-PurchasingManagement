package biz.picosoft.demo.client.kernel.model.objects;

import biz.picosoft.demo.client.kernel.model.pm.ActivityType;

import java.io.Serializable;
import java.time.ZonedDateTime;


public class UserActivityDTO implements Serializable {

    private Long id;

    private ZonedDateTime activitydatetime;

    private String activityName;

    private ActivityType activityType;

    private String cgiRemoteAddress;

    private String cgiLocalAddress;

    private String activityData;

    private String samaccountname;

    private String mail;

    private String displayname;

    private String distinguishedname;

    private String employeetype;

    private String employeeid;

    private Long classId;

    private Long objectId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getActivitydatetime() {
        return activitydatetime;
    }

    public void setActivitydatetime(ZonedDateTime activitydatetime) {
        this.activitydatetime = activitydatetime;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    public String getCgiRemoteAddress() {
        return cgiRemoteAddress;
    }

    public void setCgiRemoteAddress(String cgiRemoteAddress) {
        this.cgiRemoteAddress = cgiRemoteAddress;
    }

    public String getCgiLocalAddress() {
        return cgiLocalAddress;
    }

    public void setCgiLocalAddress(String cgiLocalAddress) {
        this.cgiLocalAddress = cgiLocalAddress;
    }

    public String getActivityData() {
        return activityData;
    }

    public void setActivityData(String activityData) {
        this.activityData = activityData;
    }

    public String getSamaccountname() {
        return samaccountname;
    }

    public void setSamaccountname(String samaccountname) {
        this.samaccountname = samaccountname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public String getDistinguishedname() {
        return distinguishedname;
    }

    public void setDistinguishedname(String distinguishedname) {
        this.distinguishedname = distinguishedname;
    }

    public String getEmployeetype() {
        return employeetype;
    }

    public void setEmployeetype(String employeetype) {
        this.employeetype = employeetype;
    }

    public String getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(String employeeid) {
        this.employeeid = employeeid;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserActivityDTO)) {
            return false;
        }

        return id != null && id.equals(((UserActivityDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }


}

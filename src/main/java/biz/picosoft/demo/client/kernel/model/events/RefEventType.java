package biz.picosoft.demo.client.kernel.model.events;


import biz.picosoft.demo.client.kernel.model.acl.AclClass;
import biz.picosoft.demo.config.audit.Auditable;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A RefEventType.
 */

public class RefEventType extends Auditable implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String evtName;

    private String comment;

    private String alias;

    private Boolean publicEvent = false;

    private Boolean enabled = false;

    private Boolean system = true;

    private Boolean auto = true;

    private String categorie;

    private String dataTest;

    private ZonedDateTime planDateTest;

    private Long objectIdTest;

    private Boolean sendEmailTest = false;

    private Boolean CallRestTest = false;

    private Boolean sendNotifTest = false;

    private Boolean fctCallTest = false;

    private Boolean rpCallTest = false;

    private Boolean processTest = false;

    private Boolean syncroneRpCallEvent = true;

    private Boolean syncroneFunctionCall = true;

    private Boolean syncroneRestCall = true;

    private Boolean syncroneEmailEvent = true;

    private Boolean syncroneNotification = true;

    private AclClass classes;

    private String modulePackageName;
    private String className;

    private RefEventType nextEventType;

    private String readerFM;

    private String authorFM;

    private String createSIDName;

    private String schema;

    public RefEventType() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public RefEventType getNextEventType() {
        return nextEventType;
    }

    public void setNextEventType(RefEventType nextEventType) {
        this.nextEventType = nextEventType;
    }

    public Boolean getSystem() {
        return system;
    }

    public void setSystem(Boolean system) {
        this.system = system;
    }

    public Boolean getAuto() {
        return auto;
    }

    public void setAuto(Boolean auto) {
        this.auto = auto;
    }

    public Boolean getPublicEvent() {
        return publicEvent;
    }

    public void setPublicEvent(Boolean publicEvent) {
        this.publicEvent = publicEvent;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public AclClass getClasses() {
        return classes;
    }

    public void setClasses(AclClass classes) {
        this.classes = classes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getEvtName() {
        return evtName;
    }

    public void setEvtName(String evtName) {
        this.evtName = evtName;
    }

    public RefEventType evtName(String evtName) {
        this.evtName = evtName;
        return this;
    }

    public String getDataTest() {
        return dataTest;
    }

    public void setDataTest(String dataTest) {
        this.dataTest = dataTest;
    }

    public ZonedDateTime getPlanDateTest() {
        return planDateTest;
    }

    public void setPlanDateTest(ZonedDateTime planDateTest) {
        this.planDateTest = planDateTest;
    }

    public Long getObjectIdTest() {
        return objectIdTest;
    }

    public void setObjectIdTest(Long objectIdTest) {
        this.objectIdTest = objectIdTest;
    }

    public Boolean getSendEmailTest() {
        return sendEmailTest;
    }

    public void setSendEmailTest(Boolean sendEmailTest) {
        this.sendEmailTest = sendEmailTest;
    }

    public Boolean getCallRestTest() {
        return CallRestTest;
    }

    public void setCallRestTest(Boolean callRestTest) {
        CallRestTest = callRestTest;
    }

    public Boolean getSendNotifTest() {
        return sendNotifTest;
    }

    public void setSendNotifTest(Boolean sendNotifTest) {
        this.sendNotifTest = sendNotifTest;
    }

    public Boolean getFctCallTest() {
        return fctCallTest;
    }

    public void setFctCallTest(Boolean fctCallTest) {
        this.fctCallTest = fctCallTest;
    }

    public Boolean getRpCallTest() {
        return rpCallTest;
    }

    public void setRpCallTest(Boolean rpCallTest) {
        this.rpCallTest = rpCallTest;
    }

    public Boolean getProcessTest() {
        return processTest;
    }

    public void setProcessTest(Boolean processTest) {
        this.processTest = processTest;
    }

    public Boolean isPublicEvent() {
        return publicEvent;
    }

    public RefEventType publicEvent(Boolean publicEvent) {
        this.publicEvent = publicEvent;
        return this;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public RefEventType enabledt(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RefEventType)) {
            return false;
        }
        return id != null && id.equals(((RefEventType) o).id);
    }


    public String getReaderFM() {
        return readerFM;
    }

    public void setReaderFM(String readerFM) {
        this.readerFM = readerFM;
    }

    public String getAuthorFM() {
        return authorFM;
    }

    public void setAuthorFM(String authorFM) {
        this.authorFM = authorFM;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Boolean getSyncroneRpCallEvent() {
        return syncroneRpCallEvent;
    }

    public void setSyncroneRpCallEvent(Boolean syncroneRpCallEvent) {
        this.syncroneRpCallEvent = syncroneRpCallEvent;
    }

    public Boolean getSyncroneFunctionCall() {
        return syncroneFunctionCall;
    }

    public void setSyncroneFunctionCall(Boolean syncroneFunctionCall) {
        this.syncroneFunctionCall = syncroneFunctionCall;
    }

    public Boolean getSyncroneRestCall() {
        return syncroneRestCall;
    }

    public void setSyncroneRestCall(Boolean syncroneRestCall) {
        this.syncroneRestCall = syncroneRestCall;
    }

    public Boolean getSyncroneEmailEvent() {
        return syncroneEmailEvent;
    }

    public void setSyncroneEmailEvent(Boolean syncroneEmailEvent) {
        this.syncroneEmailEvent = syncroneEmailEvent;
    }

    public Boolean getSyncroneNotification() {
        return syncroneNotification;
    }

    public void setSyncroneNotification(Boolean syncroneNotification) {
        this.syncroneNotification = syncroneNotification;
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

    public String getCreateSIDName() {
        return createSIDName;
    }

    public void setCreateSIDName(String createSIDName) {
        this.createSIDName = createSIDName;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }
}

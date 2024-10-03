package biz.picosoft.demo.client.kernel.model.common;//package picosoft.biz.anf.kernel.model.common;
//
//import picosoft.biz.anf.config.audit.Auditable;
//import picosoft.biz.anf.kernel.model.acl.AclSid;
//import picosoft.biz.anf.kernel.model.events.Event;
//import picosoft.biz.anf.kernel.model.events.runtime.EmailEvent;
//import picosoft.biz.anf.kernel.model.events.runtime.FctCallEvent;
//import picosoft.biz.anf.kernel.model.events.runtime.JRXMLEvent;
//import picosoft.biz.anf.kernel.model.events.runtime.RestCallEvent;
//import picosoft.biz.anf.kernel.model.events.template.EmailTemplate;
//import picosoft.biz.anf.kernel.model.events.template.FctCallTemplate;
//
//import javax.persistence.Basic;
//import javax.persistence.FetchType;
//import java.io.Serializable;
//import java.time.ZonedDateTime;
//
//public class Files  extends Auditable implements Serializable,Cloneable{
//
//    private Long id;
//
//    private String fileName;
//
//    private String size;
//
//    private String fileType;
//
//    private String checksum;
//
//    private byte[] thumbnail;
//
//    private boolean useCms = false;
//
//    private String cmsId;
//
//    private String emplacement;
//
//    private Long objectId;
//
//    private Long classId;
//
//    private Integer version = 1;
//
//    @Basic(fetch= FetchType.LAZY)
//    private byte[] data;
//
//    private boolean isLocked = false;
//
//    private boolean isCloned = false;
//
//    private boolean isTransferable = true;
//
//    private Integer securiteLevel;
//
//    private Event event;
//
//    private FctCallEvent fctCallEvent;
//
//    private EmailEvent emailEvent;
//
//    private EmailTemplate emailTemplate;
//
//    private RestCallEvent restCallEvent;
//
//    private FctCallTemplate fctCallTemplate;
//
//    private JRXMLEvent jrxmlEvent;
//
//    private AclSid responsable;
//
//    private Boolean executeOCR ;
//
//    private String anomalieDescription;
//
//    private ZonedDateTime anomalieDate;
//
//    private String absctract;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getFileName() {
//        return fileName;
//    }
//
//    public void setFileName(String fileName) {
//        this.fileName = fileName;
//    }
//
//    public String getSize() {
//        return size;
//    }
//
//    public void setSize(String size) {
//        this.size = size;
//    }
//
//    public String getFileType() {
//        return fileType;
//    }
//
//    public void setFileType(String fileType) {
//        this.fileType = fileType;
//    }
//
//    public String getChecksum() {
//        return checksum;
//    }
//
//    public void setChecksum(String checksum) {
//        this.checksum = checksum;
//    }
//
//    public byte[] getThumbnail() {
//        return thumbnail;
//    }
//
//    public void setThumbnail(byte[] thumbnail) {
//        this.thumbnail = thumbnail;
//    }
//
//    public boolean isUseCms() {
//        return useCms;
//    }
//
//    public void setUseCms(boolean useCms) {
//        this.useCms = useCms;
//    }
//
//    public String getCmsId() {
//        return cmsId;
//    }
//
//    public void setCmsId(String cmsId) {
//        this.cmsId = cmsId;
//    }
//
//    public String getEmplacement() {
//        return emplacement;
//    }
//
//    public void setEmplacement(String emplacement) {
//        this.emplacement = emplacement;
//    }
//
//    public Long getObjectId() {
//        return objectId;
//    }
//
//    public void setObjectId(Long objectId) {
//        this.objectId = objectId;
//    }
//
//    public Long getClassId() {
//        return classId;
//    }
//
//    public void setClassId(Long classId) {
//        this.classId = classId;
//    }
//
//    public Integer getVersion() {
//        return version;
//    }
//
//    public void setVersion(Integer version) {
//        this.version = version;
//    }
//
//    public byte[] getData() {
//        return data;
//    }
//
//    public void setData(byte[] data) {
//        this.data = data;
//    }
//
//    public boolean isLocked() {
//        return isLocked;
//    }
//
//    public void setLocked(boolean locked) {
//        isLocked = locked;
//    }
//
//    public boolean isCloned() {
//        return isCloned;
//    }
//
//    public void setCloned(boolean cloned) {
//        isCloned = cloned;
//    }
//
//    public boolean isTransferable() {
//        return isTransferable;
//    }
//
//    public void setTransferable(boolean transferable) {
//        isTransferable = transferable;
//    }
//
//    public Integer getSecuriteLevel() {
//        return securiteLevel;
//    }
//
//    public void setSecuriteLevel(Integer securiteLevel) {
//        this.securiteLevel = securiteLevel;
//    }
//
//    public Event getEvent() {
//        return event;
//    }
//
//    public void setEvent(Event event) {
//        this.event = event;
//    }
//
//    public FctCallEvent getFctCallEvent() {
//        return fctCallEvent;
//    }
//
//    public void setFctCallEvent(FctCallEvent fctCallEvent) {
//        this.fctCallEvent = fctCallEvent;
//    }
//
//    public EmailEvent getEmailEvent() {
//        return emailEvent;
//    }
//
//    public void setEmailEvent(EmailEvent emailEvent) {
//        this.emailEvent = emailEvent;
//    }
//
//    public EmailTemplate getEmailTemplate() {
//        return emailTemplate;
//    }
//
//    public void setEmailTemplate(EmailTemplate emailTemplate) {
//        this.emailTemplate = emailTemplate;
//    }
//
//    public RestCallEvent getRestCallEvent() {
//        return restCallEvent;
//    }
//
//    public void setRestCallEvent(RestCallEvent restCallEvent) {
//        this.restCallEvent = restCallEvent;
//    }
//
//    public FctCallTemplate getFctCallTemplate() {
//        return fctCallTemplate;
//    }
//
//    public void setFctCallTemplate(FctCallTemplate fctCallTemplate) {
//        this.fctCallTemplate = fctCallTemplate;
//    }
//
//    public JRXMLEvent getJrxmlEvent() {
//        return jrxmlEvent;
//    }
//
//    public void setJrxmlEvent(JRXMLEvent jrxmlEvent) {
//        this.jrxmlEvent = jrxmlEvent;
//    }
//
//    public AclSid getResponsable() {
//        return responsable;
//    }
//
//    public void setResponsable(AclSid responsable) {
//        this.responsable = responsable;
//    }
//
//    public Boolean getExecuteOCR() {
//        return executeOCR;
//    }
//
//    public void setExecuteOCR(Boolean executeOCR) {
//        this.executeOCR = executeOCR;
//    }
//
//    public String getAnomalieDescription() {
//        return anomalieDescription;
//    }
//
//    public void setAnomalieDescription(String anomalieDescription) {
//        this.anomalieDescription = anomalieDescription;
//    }
//
//    public ZonedDateTime getAnomalieDate() {
//        return anomalieDate;
//    }
//
//    public void setAnomalieDate(ZonedDateTime anomalieDate) {
//        this.anomalieDate = anomalieDate;
//    }
//
//    public String getAbsctract() {
//        return absctract;
//    }
//
//    public void setAbsctract(String absctract) {
//        this.absctract = absctract;
//    }
//}

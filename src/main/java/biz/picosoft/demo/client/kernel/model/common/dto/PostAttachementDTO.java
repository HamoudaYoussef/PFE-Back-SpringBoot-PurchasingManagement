package biz.picosoft.demo.client.kernel.model.common.dto;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public class PostAttachementDTO {
    private Long id;
    private MultipartFile multipartFiles;
    private LocalDate expirationDate;
    private LocalDate issueDate;
    private String issueAdress;
    private String docId;
    private LocalDate docDate;
    private Integer docCopies;
    private byte[] data;
    private String reqFileDefName;
    private String fileName;
    private String docTitle;
    private Long objectId;
    private Long classId;
    private Boolean locked = false;
    private Boolean transferable=false;
    private Integer securiteLevel=0;

    private String responsableSid;
    private Long responsableSidId;

    private String preferenceName;

    public MultipartFile getMultipartFiles() {
        return multipartFiles;
    }

    public void setMultipartFiles(MultipartFile multipartFiles) {
        this.multipartFiles = multipartFiles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPreferenceName() {
        return preferenceName;
    }

    public void setPreferenceName(String preferenceName) {
        this.preferenceName = preferenceName;
    }

    public Integer getSecuriteLevel() {
        return securiteLevel;
    }

    public void setSecuriteLevel(Integer securiteLevel) {
        this.securiteLevel = securiteLevel;
    }

    public String getResponsableSid() {
        return responsableSid;
    }

    public void setResponsableSid(String responsableSid) {
        this.responsableSid = responsableSid;
    }

    public Long getResponsableSidId() {
        return responsableSidId;
    }

    public void setResponsableSidId(Long responsableSidId) {
        this.responsableSidId = responsableSidId;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public String getIssueAdress() {
        return issueAdress;
    }

    public void setIssueAdress(String issueAdress) {
        this.issueAdress = issueAdress;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public LocalDate getDocDate() {
        return docDate;
    }

    public void setDocDate(LocalDate docDate) {
        this.docDate = docDate;
    }

    public Integer getDocCopies() {
        return docCopies;
    }

    public void setDocCopies(Integer docCopies) {
        this.docCopies = docCopies;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getReqFileDefName() {
        return reqFileDefName;
    }

    public void setReqFileDefName(String reqFileDefName) {
        this.reqFileDefName = reqFileDefName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDocTitle() {
        return docTitle;
    }

    public void setDocTitle(String docTitle) {
        this.docTitle = docTitle;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Boolean getTransferable() {
        return transferable;
    }

    public void setTransferable(Boolean transferable) {
        this.transferable = transferable;
    }
}

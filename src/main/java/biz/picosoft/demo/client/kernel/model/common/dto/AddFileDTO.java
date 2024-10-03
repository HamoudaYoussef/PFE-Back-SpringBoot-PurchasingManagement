package biz.picosoft.demo.client.kernel.model.common.dto;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.ZonedDateTime;

public class AddFileDTO {

    private Long id;
    private String fileName;

    private String fileType;

    private String checksum;

    private boolean useCms;

    private String cmsId;

    private String emplacement;

    @NotNull
    private Long objectId;
    @NotNull
    private Long classId;

    private boolean isLocked = false;

    private boolean isCloned = false;

    private boolean isTransferable = false;

    private  Integer responsableSidId;

    private String displayNameResponsable;

    private String responsableSid;

    private Integer version = 1;


    private ZonedDateTime sysdateCreated;

    private ZonedDateTime sysdateUpdated;

    private String syscreatedBy;

    private String sysupdatedBy;

    private String size;

    private Integer securiteLevel;

    private FilesTypeDTO filesTypeDTO;

    private Boolean executeOCR = true;
    @Null
    private String preferenceName;

    private MultipartFile file;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public boolean isUseCms() {
        return useCms;
    }

    public void setUseCms(boolean useCms) {
        this.useCms = useCms;
    }

    public String getCmsId() {
        return cmsId;
    }

    public void setCmsId(String cmsId) {
        this.cmsId = cmsId;
    }

    public String getEmplacement() {
        return emplacement;
    }

    public void setEmplacement(String emplacement) {
        this.emplacement = emplacement;
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

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public String getPreferenceName() {
        return preferenceName;
    }

    public void setPreferenceName(String preferenceName) {
        this.preferenceName = preferenceName;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public boolean isCloned() {
        return isCloned;
    }

    public void setCloned(boolean cloned) {
        isCloned = cloned;
    }

    public boolean isTransferable() {
        return isTransferable;
    }

    public void setTransferable(boolean transferable) {
        isTransferable = transferable;
    }

    public Integer getResponsableSidId() {
        return responsableSidId;
    }

    public void setResponsableSidId(Integer responsableSidId) {
        this.responsableSidId = responsableSidId;
    }

    public String getDisplayNameResponsable() {
        return displayNameResponsable;
    }

    public void setDisplayNameResponsable(String displayNameResponsable) {
        this.displayNameResponsable = displayNameResponsable;
    }

    public String getResponsableSid() {
        return responsableSid;
    }

    public void setResponsableSid(String responsableSid) {
        this.responsableSid = responsableSid;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public ZonedDateTime getSysdateCreated() {
        return sysdateCreated;
    }

    public void setSysdateCreated(ZonedDateTime sysdateCreated) {
        this.sysdateCreated = sysdateCreated;
    }

    public ZonedDateTime getSysdateUpdated() {
        return sysdateUpdated;
    }

    public void setSysdateUpdated(ZonedDateTime sysdateUpdated) {
        this.sysdateUpdated = sysdateUpdated;
    }

    public String getSyscreatedBy() {
        return syscreatedBy;
    }

    public void setSyscreatedBy(String syscreatedBy) {
        this.syscreatedBy = syscreatedBy;
    }

    public String getSysupdatedBy() {
        return sysupdatedBy;
    }

    public void setSysupdatedBy(String sysupdatedBy) {
        this.sysupdatedBy = sysupdatedBy;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getSecuriteLevel() {
        return securiteLevel;
    }

    public void setSecuriteLevel(Integer securiteLevel) {
        this.securiteLevel = securiteLevel;
    }

    public FilesTypeDTO getFilesTypeDTO() {
        return filesTypeDTO;
    }

    public void setFilesTypeDTO(FilesTypeDTO filesTypeDTO) {
        this.filesTypeDTO = filesTypeDTO;
    }

    public Boolean getExecuteOCR() {
        return executeOCR;
    }

    public void setExecuteOCR(Boolean executeOCR) {
        this.executeOCR = executeOCR;
    }
}

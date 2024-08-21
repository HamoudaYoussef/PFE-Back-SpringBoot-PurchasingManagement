package biz.picosoft.demo.client.kernel.model.acl;


import biz.picosoft.demo.client.kernel.model.global.StateWorkflow;
import biz.picosoft.demo.config.audit.Auditable;

public class AclClass extends Auditable {


    private Long id;

    private String classe; //fullname

    private String simpleName;

    private String label;

    private String tableName;

    private String auditTableName;

    private String modulePackageName;

    private String mandatoryTemplateFileName;

    private String optionalTemplateFileName;

    private String officeTemplateFileName;

    private String emailTemplateFileName;

    private String readFormNameFM; //

    private String editFormNameFM; //

    private String templateReport;

    private String fwProcess;

    private String sequenceNameFM;

    private String other;

    private Integer securiteLevel = 0;
    private StateWorkflow defaultState;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getAuditTableName() {
        return auditTableName;
    }

    public void setAuditTableName(String auditTableName) {
        this.auditTableName = auditTableName;
    }

    public String getModulePackageName() {
        return modulePackageName;
    }

    public void setModulePackageName(String modulePackageName) {
        this.modulePackageName = modulePackageName;
    }

    public String getMandatoryTemplateFileName() {
        return mandatoryTemplateFileName;
    }

    public void setMandatoryTemplateFileName(String mandatoryTemplateFileName) {
        this.mandatoryTemplateFileName = mandatoryTemplateFileName;
    }

    public String getOptionalTemplateFileName() {
        return optionalTemplateFileName;
    }

    public void setOptionalTemplateFileName(String optionalTemplateFileName) {
        this.optionalTemplateFileName = optionalTemplateFileName;
    }

    public String getOfficeTemplateFileName() {
        return officeTemplateFileName;
    }

    public void setOfficeTemplateFileName(String officeTemplateFileName) {
        this.officeTemplateFileName = officeTemplateFileName;
    }

    public String getEmailTemplateFileName() {
        return emailTemplateFileName;
    }

    public void setEmailTemplateFileName(String emailTemplateFileName) {
        this.emailTemplateFileName = emailTemplateFileName;
    }

    public String getReadFormNameFM() {
        return readFormNameFM;
    }

    public void setReadFormNameFM(String readFormNameFM) {
        this.readFormNameFM = readFormNameFM;
    }

    public String getEditFormNameFM() {
        return editFormNameFM;
    }

    public void setEditFormNameFM(String editFormNameFM) {
        this.editFormNameFM = editFormNameFM;
    }

    public String getTemplateReport() {
        return templateReport;
    }

    public void setTemplateReport(String templateReport) {
        this.templateReport = templateReport;
    }

    public String getFwProcess() {
        return fwProcess;
    }

    public void setFwProcess(String fwProcess) {
        this.fwProcess = fwProcess;
    }

    public String getSequenceNameFM() {
        return sequenceNameFM;
    }

    public void setSequenceNameFM(String sequenceNameFM) {
        this.sequenceNameFM = sequenceNameFM;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public Integer getSecuriteLevel() {
        return securiteLevel;
    }

    public void setSecuriteLevel(Integer securiteLevel) {
        this.securiteLevel = securiteLevel;
    }

    public StateWorkflow getDefaultState() {
        return defaultState;
    }

    public void setDefaultState(StateWorkflow defaultState) {
        this.defaultState = defaultState;
    }

}


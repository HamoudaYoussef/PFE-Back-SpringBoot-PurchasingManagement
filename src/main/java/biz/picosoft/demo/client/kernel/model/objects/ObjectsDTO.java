package biz.picosoft.demo.client.kernel.model.objects;




import biz.picosoft.demo.client.kernel.model.common.dto.AttachementDTO;
import biz.picosoft.demo.client.kernel.model.common.dto.GetRequestFileDefinitionDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ObjectsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String className;
    private Long classId;
    private String simpleClassName;
    private String labelClass;
    private List<AttachementDTO> attachements = new ArrayList<>();
    private EventsDetails events;
    private List<UserActivityDTO> userActivity = new ArrayList<>();
    private String userPermission;
    private StateObjectDto currentState;
    private String formSource;
    private WFDTO workflow;
    private List<GetRequestFileDefinitionDTO> remaingRequestFileDefinitions;
    private Security security;
    private List<Component> components;

    private String MandatoryTemplateFileName;
    private String OptionalTemplateFileName;
    private String officeTemplateFileName;
    private String emailTemplateFileName;
    private List<String>  DefaultTemplateFileName;
    private String readFormNameFM; //
    private String editFormNameFM; //

    private String readFormName;
    private String editFormName;

    private String wfProcessName;


    public ObjectsDTO() {
    }
    public ObjectsDTO(ObjectsDTO objectsDTO) {
        if (objectsDTO != null) {
            this.className = objectsDTO.getClassName();
            this.classId = objectsDTO.getClassId();
            this.attachements = objectsDTO.getAttachements();
            this.events = objectsDTO.getEvents();
            this.userActivity = objectsDTO.getUserActivity();
            this.userPermission = objectsDTO.getUserPermission();
            this.currentState = objectsDTO.getCurrentState();
            this.workflow = objectsDTO.getWorkflow();
            this.wfProcessName = objectsDTO.getWfProcessName();
            this.formSource = objectsDTO.getFormSource();
            this.security=objectsDTO.getSecurity();
            this.components=objectsDTO.getComponents();
        }
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

    public String getReadFormName() {
        return readFormName;
    }

    public void setReadFormName(String readFormName) {
        this.readFormName = readFormName;
    }

    public String getEditFormName() {
        return editFormName;
    }

    public void setEditFormName(String editFormName) {
        this.editFormName = editFormName;
    }

    public String getMandatoryTemplateFileName() {
        return MandatoryTemplateFileName;
    }

    public void setMandatoryTemplateFileName(String mandatoryTemplateFileName) {
        MandatoryTemplateFileName = mandatoryTemplateFileName;
    }

    public String getOptionalTemplateFileName() {
        return OptionalTemplateFileName;
    }

    public void setOptionalTemplateFileName(String optionalTemplateFileName) {
        OptionalTemplateFileName = optionalTemplateFileName;
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

    public List<String> getDefaultTemplateFileName() {
        return DefaultTemplateFileName;
    }

    public void setDefaultTemplateFileName(List<String> defaultTemplateFileName) {
        DefaultTemplateFileName = defaultTemplateFileName;
    }
    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }

    public Security getSecurity() {
        return security;
    }

    public void setSecurity(Security security) {
        this.security = security;
    }

    public String getFormSource() {
        return formSource;
    }

    public void setFormSource(String formSource) {
        this.formSource = formSource;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public List<AttachementDTO> getAttachements() {
        return attachements;
    }

    public void setAttachements(List<AttachementDTO> attachements) {
        this.attachements = attachements;
    }


    public EventsDetails getEvents() {
        return events;
    }

    public void setEvents(EventsDetails events) {
        this.events = events;
    }

    public List<UserActivityDTO> getUserActivity() {
        return userActivity;
    }

    public void setUserActivity(List<UserActivityDTO> userActivity) {
        this.userActivity = userActivity;
    }

    public String getUserPermission() {
        return userPermission;
    }

    public void setUserPermission(String userPermission) {
        this.userPermission = userPermission;
    }

    public StateObjectDto getCurrentState() {
        return currentState;
    }

    public void setCurrentState(StateObjectDto currentState) {
        this.currentState = currentState;
    }

    public WFDTO getWorkflow() {
        return workflow;
    }

    public void setWorkflow(WFDTO workflow) {
        this.workflow = workflow;
    }

    public List<GetRequestFileDefinitionDTO> getRemaingRequestFileDefinitions() {
        return remaingRequestFileDefinitions;
    }

    public void setRemaingRequestFileDefinitions(List<GetRequestFileDefinitionDTO> remaingRequestFileDefinitions) {
        this.remaingRequestFileDefinitions = remaingRequestFileDefinitions;
    }

    public String getSimpleClassName() {
        return simpleClassName;
    }

    public void setSimpleClassName(String simpleClassName) {
        this.simpleClassName = simpleClassName;
    }

    public String getLabelClass() {
        return labelClass;
    }

    public void setLabelClass(String labelClass) {
        this.labelClass = labelClass;
    }

    public String getWfProcessName() {
        return wfProcessName;
    }

    public void setWfProcessName(String wfProcessName) {
        this.wfProcessName = wfProcessName;
    }
}

package biz.picosoft.demo.client.kernel.model.objects;

import biz.picosoft.demo.client.kernel.intercomm.KernelService;
import biz.picosoft.demo.config.BeanUtil;
import biz.picosoft.demo.client.kernel.intercomm.KernelInterface;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;


@Service
public class WFDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Logger log = LoggerFactory.getLogger(WFDTO.class);

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

    private String wfProcessName;

    private String wfProcessID;


    public String getWfProcessName() {
        return wfProcessName;
    }

    public void setWfProcessName(String wfProcessName) {
        this.wfProcessName = wfProcessName;
    }

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


    public WFDTO syncDataWF(Object objBeforeUpdate) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String stringObject=BeanUtil.getBean(KernelService.class).mapObjectToString(objBeforeUpdate);
        //JsonNode to parse a large JSON object
        JsonNode object = objectMapper.readTree(stringObject);
        if ((object.has("id") && !object.get("id").isNull()) &&
                (object.has("classId") && !object.get("classId").isNull())
                && (object.has("wfProcessID") && !object.get("wfProcessID").isNull())) {

            //get data updated from WF
            ObjectDTO objectDTO = new ObjectDTO();
            objectDTO.setObjectId(object.get("id").asLong());
            objectDTO.setObject(objBeforeUpdate);
            objectDTO.setWfProcessId(object.get("wfProcessID").asText());
            objectDTO.setClassId(object.get("classId").asLong());
            ObjectsDTO g = BeanUtil.getBean(KernelInterface.class).getobjectsDto(objectDTO);

            if (g != null && g.getWorkflow() != null) {
                return g.getWorkflow();
            }

        }

        return null;
    }
}

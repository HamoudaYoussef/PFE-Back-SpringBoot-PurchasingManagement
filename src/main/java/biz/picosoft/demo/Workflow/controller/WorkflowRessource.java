package biz.picosoft.demo.Workflow.controller;

import biz.picosoft.demo.Workflow.DTO.HistoricWF;
import biz.picosoft.demo.Workflow.service.WorkflowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.event.EventLogEntry;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


//@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping(path = "/api/workflow")
@Api(tags = "Mail Manager")

public class WorkflowRessource {

    @Autowired
    WorkflowService workflowService;

    @Autowired
    protected RuntimeService runtimeService;

    @Autowired
    protected RepositoryService repositoryService;

    @Autowired
    protected HistoryService historyService;



    /**
     *
     * @param variables
     *     A MetaData of {@link Map} : a JSON of MetaData.  contains the initiator name of the process instance and the process key.
     * @return
     *     A respnse of {@link String} : return the created process instance id
     */
    @RequestMapping(value="/startProcessInstance", method= RequestMethod.POST)
    @ApiOperation(value = "Recover a JSON of metaData and return a String id", notes = "take StartProcessRepresentation :  process key and initiator name")
    public JSONObject startProcessInstance(@RequestBody Map<String, Object> variables) throws Exception {
        return workflowService.startProcessInstance(variables);
    }

    /**
     *
     * @param variables
     *     A MetaData of {@link Map} : a JSON of MetaData.  contains the initiator name of the process instance and the process key.
     * @return
     *     A respnse of {@link String} : return the created process instance id
     */
    @RequestMapping(value="/startAndRootProcessInstance", method= RequestMethod.POST)
    @ApiOperation(value = "Recover a JSON of metaData and return a String id", notes = "take StartProcessRepresentation :  process key and initiator name")
    public JSONObject startAndRootProcessInstance(@RequestBody Map<String, Object> variables) throws Exception {
        return workflowService.initInstance_nextTask(variables);
    }

    /**
     *
     * @param variables
     *     A MetaData of {@link Map} : a JSON of MetaData.  contains the initiator name of the process instance and the process key.
     * @return
     *     A respnse of {@link String} : return the created process instance id
     */
    @RequestMapping(value="/startAndRootProcessInstanceWithoutEvent", method= RequestMethod.POST)
    @ApiOperation(value = "Recover a JSON of metaData and return a String id", notes = "take StartProcessRepresentation :  process key and initiator name")
    public JSONObject startAndRootProcessInstanceWithoutEvent(@RequestBody Map<String, Object> variables) throws Exception {
        return workflowService.initInstance_nextTaskWithoutEvent(variables);
    }

    /**
     *
     * @param processInstanceId
     *     A MetaData of {@link String} : process instance id.
     * @return
     *     A respnse of {@link String} : return list of instance historics
     */
    @RequestMapping(value="/getHistoricProcessInstance", method= RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public List<HistoricWF> getHistoricProcessInstance(@RequestParam("processInstanceId") String processInstanceId) {
        return workflowService.getHistoricProcess(processInstanceId);
    }


    /**
     *
     * @param processInstanceId
     *     A MetaData of {@link String} : process instance id.
     * @return
     *     A respnse of {@link String} : return list of instance historics
     */
    @RequestMapping(value="/getProcessInstanceExtensionProperties", method= RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public JSONObject getProcessInstanceExtensionProperties(@RequestParam("processInstanceId") String processInstanceId) {
        return workflowService.getProcessInstanceExtensionProperties(processInstanceId);
    }

    /**
     *
     * @param processKey
     *     A MetaData of {@link String} : process instance id.
     * @return
     *     A respnse of {@link String} : return list of instance historics
     */
    @RequestMapping(value="/getAllActivitiesByProcesKey", method= RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public ArrayList getAllActivitiesByProcesKey(@RequestParam("processKey") String processKey) throws FileNotFoundException {
        return workflowService.getAllActivitiesByProcesKey(processKey);
    }


    /**
     *
     * @param processInstanceId
     *     A MetaData of {@link String} : process instance id.
     * @return
     *     A respnse of {@link String} : return list of instance historics
     */
    @RequestMapping(value="/findAllProcessInstances", method= RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public List<HistoricProcessInstance> findAllHistoricProcessInstance(Pageable page, @RequestParam("processInstanceId") String processInstanceId) {
        return workflowService.findAllHistoricProcessInstance(page, processInstanceId);
    }


    @RequestMapping(value="/getActiveTaskInstance", method= RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public String getActiveTaskInstance(@RequestParam("processInstanceId") String processInstanceId) {
        return workflowService.getActifTaskOfProcessInstance(processInstanceId).getName();
    }


    /**
     *
     * @param processInstanceId
     *     A MetaData of {@link String} : process instance id.
     * @return
     *     A respnse of {@link String} : return list of decisions
     */
    @RequestMapping(value = "/gatewayDecision", method = RequestMethod.GET)
    public List gatewayDecision(@RequestParam("processInstanceId") String processInstanceId) {
        return workflowService.getGatewayDecision(processInstanceId);
    }

    /**
     *
     * @param processInstanceId
     *     A MetaData of {@link String} : process instance id.
     * @return
     *     A respnse of {@link String} : return list of decisions
     */
    @RequestMapping(value = "/getGatewayDecisionFromModel", method = RequestMethod.POST)
    public List getGatewayDecisionFromModel(@RequestParam("processInstanceId") String processInstanceId) throws FileNotFoundException {
        return workflowService.getGatewayDecisionFromModel(processInstanceId);
    }


    /**
     *
     *     A hash map of {@link Map} : contains a list of variables
     * @return
     *     A respnse of {@link JSONObject} : Return historic instance list.
     */
    @RequestMapping(value = "/_nextTask", method = RequestMethod.POST,produces={MediaType.APPLICATION_JSON_VALUE,MediaType.TEXT_PLAIN_VALUE})
    public JSONObject _nextTask(@RequestBody Map<String, Object> variables) throws Exception {
        return workflowService._nextTask(variables);
    }

    /**
     * A hash map of {@link Map} : contains a list of variables
     *
     * @return A respnse of {@link JSONObject} : Return historic instance list.
     */
    @RequestMapping(value = "/_nextTask_Batch", method = RequestMethod.POST,produces={MediaType.APPLICATION_JSON_VALUE,MediaType.TEXT_PLAIN_VALUE})
    public List<JSONObject> _nextTaskBatch(@RequestBody Map<String, Object> variables) throws Exception {
        return workflowService._nextTaskBatch(variables);
    }

    /**
     * A hash map of {@link Map} : contains a list of variables
     *
     * @return A respnse of {@link JSONObject} : Return historic instance list.
     */
    @RequestMapping(value = "/_nextTask_BatchWithoutEvent", method = RequestMethod.POST,produces={MediaType.APPLICATION_JSON_VALUE,MediaType.TEXT_PLAIN_VALUE})
    public List<JSONObject> _nextTaskBatchWithoutEvent(@RequestBody Map<String, Object> variables) throws Exception {
        return workflowService._nextTaskBatchWithoutEvent(variables);
    }

    @RequestMapping(value = "/delegateTask", method = RequestMethod.POST,produces={MediaType.APPLICATION_JSON_VALUE,MediaType.TEXT_PLAIN_VALUE})
    public void delegateTask(@RequestBody Map<String, Object> variables) throws Exception {
        workflowService.delegateTask(variables);
    }
//    @RequestMapping(value = "/delegateTask", method = RequestMethod.GET,produces={MediaType.APPLICATION_JSON_VALUE,MediaType.TEXT_PLAIN_VALUE})
//    public void delegateTask(@RequestParam("processInstanceId") String processInstanceId, @RequestParam("userId") String userId) throws Exception {
//       workflowService.delegateTask(processInstanceId, userId);
//    }

    @RequestMapping(value = "/resolveTask", method = RequestMethod.POST,produces={MediaType.APPLICATION_JSON_VALUE,MediaType.TEXT_PLAIN_VALUE})
    public void resolveTask(@RequestParam("processInstanceId") String processInstanceId, @RequestBody Map<String, Object> variables) throws Exception {
        workflowService.resolveTask(processInstanceId, variables);
    }






    @PatchMapping("/getlastVersions")
    public List<JSONObject> getListFinalProcess() {

        return workflowService.getListFinalProcess();
    }


    @CrossOrigin
    @PatchMapping(
            value = "/deployProcess",
            consumes = "multipart/form-data"
    )
    public List<Object> deployeProcess(@RequestParam("file") MultipartFile BpmnFile) throws Exception {

        return workflowService.deployeProcess(BpmnFile);
    }

    @CrossOrigin
    @PatchMapping(value = "/deployProcess")
    public List<Object> deployeProcess(@RequestBody String BpmnFile) throws Exception {
        return workflowService.deployeProcess(BpmnFile);
    }

    @PatchMapping(value = "/runtime/process-instances/{processInstanceId}/diagram")
    public ResponseEntity<byte[]> getProcessInstanceDiagram(@PathVariable String processInstanceId, HttpServletResponse response) {

        return workflowService.getProcessInstanceDiagram(processInstanceId);
    }

    @PatchMapping(value = "/getListProcessVersions")
    public List<JSONObject> getListVersionsProcess(@RequestParam("key") String key) {

        return workflowService.getListVersionsProcess(key);
    }

    @PatchMapping(value = "/instance/{processInstance}/json")
    public List<Object> getInstanceEditorJson(@PathVariable String processInstance) throws Exception {
        return workflowService.getInstanceEditorJson(processInstance);
    }

    @PatchMapping(value = "/definition/{processDefinition}/json")
    public List<Object> getDefinitionEditorJson(@PathVariable String processDefinition) throws Exception {
        return workflowService.getDefinitionEditorJson(processDefinition);
    }

    @PatchMapping(value="/getBpmnModelXml")
    public String getBpmnModelXml(@RequestParam("processDefinitionId") String processDefinitionId) throws IOException {
        return workflowService.getBpmnModelXml(processDefinitionId);
    }

    @PatchMapping(value="/deployeBpmnModel")
    public Boolean deployeBpmnModel(@RequestBody JSONObject json, @RequestParam("name") String name) throws IOException {
        return workflowService.deployeBpmnModel((String) json.get("data"), name);
    }





    @RequestMapping(value="/checkProcessToUndeploy/{processDefinitionId}", method= RequestMethod.GET)
    public Boolean checkProcessToUndeploy(@PathVariable("processDefinitionId") String processDefinitionId) throws IOException {
        return workflowService.checkProcessToUndeploy(processDefinitionId);
    }

    @RequestMapping(value="/undeployeBpmnModel/{processDefinitionId}/{force}", method= RequestMethod.DELETE)
    public Boolean undeployeBpmnModel(@PathVariable("processDefinitionId") String processDefinitionId,@PathVariable("force") Boolean force) throws IOException {
        return workflowService.undeployeProcess(processDefinitionId, force);
    }

    @RequestMapping(value="/deleteProcessInstance/{processInstanceId}", method= RequestMethod.DELETE)
    public void deleteProcessInstance(@PathVariable("processInstanceId") String processInstanceId) throws IOException {
         workflowService.deleteInstance(processInstanceId);
    }

    @RequestMapping(value="/deleteProcessInstanceHistoric/{processInstanceId}", method= RequestMethod.DELETE)
    public void deleteProcessInstanceHistoric(@PathVariable("processInstanceId") String processInstanceId) throws IOException {
        workflowService.deleteInstanceHistoric(processInstanceId);
    }

    @RequestMapping(value="/forceDeleteProcessInstance/{processInstanceId}", method= RequestMethod.DELETE)
    public void forceDeleteProcessInstance(@PathVariable("processInstanceId") String processInstanceId) throws IOException {
        workflowService.deleteInstance(processInstanceId);
        workflowService.deleteInstanceHistoric(processInstanceId);
    }

    @RequestMapping(value="/validateMigration/source_definition/{sourceProcessDefinitionId}/target_definition/{targetProcessDefinitionId}", method= RequestMethod.GET)
    public JSONObject validateMigration(@PathVariable("sourceProcessDefinitionId") String sourceProcessDefinitionId, @PathVariable("targetProcessDefinitionId") String targetProcessDefinitionId) throws IOException {
        return workflowService.validateMigration(sourceProcessDefinitionId, targetProcessDefinitionId);
    }

    @RequestMapping(value="/migrateInstances/source_definition/{sourceProcessDefinitionId}/target_definition/{targetProcessDefinitionId}", method= RequestMethod.GET)
    public Boolean migrateInstances(@PathVariable("sourceProcessDefinitionId") String sourceProcessDefinitionId, @PathVariable("targetProcessDefinitionId") String targetProcessDefinitionId) throws IOException {
        return workflowService.migrateProcessInstances(sourceProcessDefinitionId, targetProcessDefinitionId);
    }

    @RequestMapping(value="/findAllLogs/processInstanceId/{processInstanceId}", method= RequestMethod.GET)
    public List<EventLogEntry> getAllInstanceLogs(@PathVariable("processInstanceId") String processInstanceId) throws IOException {
        return workflowService.getAllInstanceLogs(processInstanceId);
    }

    @RequestMapping(value="/findAllInstanceTasks/processInstanceId/{processInstanceId}", method= RequestMethod.GET)
    public List<HistoricTaskInstance> findAllInstanceTasks(@PathVariable("processInstanceId") String processInstanceId) throws IOException {
        return workflowService.findAllInstanceTasks(processInstanceId);
    }
    @RequestMapping(value="/findAllInstanceVariables/processInstanceId/{processInstanceId}", method= RequestMethod.GET)
    public List<HistoricVariableInstance> findAllInstanceVariables(@RequestParam("processInstanceId") String processInstanceId) throws IOException {
        return workflowService.findAllInstanceVariables(processInstanceId);
    }
    @RequestMapping(value="/deleteVariables", method= RequestMethod.GET)
    public void deleteVariable(@RequestParam("variableId") String variableId) throws IOException {
        workflowService.deleteVariables(variableId);
    }
    @RequestMapping(value="/createVariables", method= RequestMethod.POST)
    public void createVariables(@RequestBody Object object, @RequestParam("variableName") String variableName) throws IOException {
        workflowService.createVariables(variableName, object);
    }

    @GetMapping("/initalizeProxy")
    public Object initalizeProxy(@RequestBody Object o) {
        return workflowService.initalizeProxy(o);
    }


    @RequestMapping(value = "/getGatewayDecisionByProcessKey", method = RequestMethod.GET)
    public List gatewayDecisionTest(@RequestParam("processKey") String processKey) throws FileNotFoundException {
        return workflowService.getGatewayDecisionByKey(processKey);
    }

    @RequestMapping(value = "/getFirstActivityNameByProcessKey", method = RequestMethod.GET)
    public String getFirstActivityNameWithoutProcessInstance(@RequestParam("processName") String processKey) throws FileNotFoundException {
        return workflowService.getFirstActivityNameByKey(processKey);
    }

    @GetMapping(value = "/extractProcessInstancesIds")
    public JSONObject getList(Date dateDeb, Date dateOut, String sid, String processKey) throws FileNotFoundException, ParseException {
        return workflowService.extractProcessInstancesIds(dateDeb,  dateOut,  sid,processKey);
    }


    @GetMapping(value = "/getCandidateGroups")
    public List getCandidateGroups(@RequestParam("taskId") String taskId) throws FileNotFoundException, ParseException {
        return workflowService._getCandidateGroups(taskId);
    }

    @GetMapping(value = "/getCandidateUsers")
    public List getCandidateUsers(@RequestParam("taskId") String taskId) throws FileNotFoundException, ParseException {
        return workflowService._getCandidateUsers(taskId);
    }

    @GetMapping(value = "/getCandidates")
    public JSONObject getCandidates(@RequestParam("taskId") String taskId) throws FileNotFoundException, ParseException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("authors", workflowService._getCandidateGroups(taskId)) ;
        jsonObject.put("readers", workflowService._getCandidateUsers(taskId)) ;

        return jsonObject;
    }




}


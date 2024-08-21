package biz.picosoft.demo.Workflow.service;


import biz.picosoft.demo.Workflow.DTO.HistoricWF;
import biz.picosoft.demo.Workflow.domain.BpmJob;
import biz.picosoft.demo.Workflow.repository.BpmJobRepository;
import biz.picosoft.demo.client.kernel.model.acl.AclClass;
import biz.picosoft.demo.config.SecurityConstants;
import biz.picosoft.demo.client.kernel.intercomm.KernelInterface;
import biz.picosoft.demo.client.kernel.model.global.CurrentUser;
import biz.picosoft.demo.controller.errors.BadRequestAlertException;
import biz.picosoft.demo.domain.InstantTypeAdapter;
import biz.picosoft.demo.domain.LocalDateTypeAdapter;
import biz.picosoft.demo.domain.ZonedDateTimeTypeAdapter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.flowable.bpmn.BpmnAutoLayout;
import org.flowable.bpmn.model.*;
import org.flowable.bpmn.model.Process;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.editor.language.json.converter.BpmnJsonConverter;
import org.flowable.engine.*;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.event.EventLogEntry;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.history.NativeHistoricActivityInstanceQuery;
import org.flowable.engine.history.NativeHistoricProcessInstanceQuery;
import org.flowable.engine.impl.context.Context;
import org.flowable.engine.impl.migration.ProcessInstanceMigrationValidationResult;
import org.flowable.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.flowable.engine.runtime.ActivityInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.identitylink.api.IdentityLinkInfo;
import org.flowable.identitylink.api.IdentityLinkType;
import org.flowable.identitylink.api.history.HistoricIdentityLink;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.NativeTaskQuery;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.service.history.NativeHistoricTaskInstanceQuery;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.flowable.variable.api.history.NativeHistoricVariableInstanceQuery;
import org.flowable.variable.api.types.VariableTypes;
import org.flowable.variable.service.impl.persistence.entity.HistoricVariableInstanceEntity;
import org.flowable.variable.service.impl.persistence.entity.HistoricVariableInstanceEntityManager;
import org.flowable.variable.service.impl.persistence.entity.VariableInstanceEntityManager;
import org.flowable.variable.service.impl.util.CommandContextUtil;
import org.hibernate.proxy.HibernateProxy;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.mapstruct.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.inject.Inject;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Named("workflowService")
@Service
public class WorkflowService {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final Logger log = LoggerFactory.getLogger(WorkflowService.class);

   private final String bpmInit="bpmInit";
   private final String bpmUpdate="bpmUpdate";
  @Autowired
  private HistoryService historyService;

  @Autowired
  private RuntimeService runtimeService;

  @Autowired
  private TaskService taskService;

  @Autowired
  private RepositoryService repositoryService;

  @Autowired
  private IdentityService identityService;

  private final CurrentUser currentUser;

  @Autowired
  protected ProcessEngineConfiguration processEngineConfiguration;

  @Autowired
  protected ObjectMapper objectMapper;

  @Autowired
  private WebApplicationContext appContext;

  @Autowired
  private BpmJobRepository bpmJobRepository;

  @Autowired
  ManagementService managementService;

  @Autowired
  BpmJobService bpmJobService;

  @Value("${workflow.waitForResponse}")
  private Boolean waitForResponse;

  @Autowired
  private Configuration config;

  @Autowired
  private KernelInterface kernelInterface;

  @Inject
  public WorkflowService(CurrentUser currentUser) {
    this.currentUser = currentUser;
  }


    /**
   *
   * @param variables
   *     A String of {@link Map} : process definition key.
   * @return
   *     A respnse of {@link String} : Return instance id.
   */
  public JSONObject startProcessInstance(Map<String, Object> variables) throws Exception {

    // create new process instance using the process key and list of variables
    ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(variables.get("processKey").toString(), variables);

    //commented by IH with instructions from WA on 29/03/2022
    // recuperate the first task of instance
    Task task = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).active().singleResult();

    //persist the comment in workflow variables
      //DISPLAYNAME OF USER AUTHENTIFIED
      taskService.setVariable(task.getId(), task.getId() + " :authentifier", currentUser.getDisplayName());
      //DISPLAYNAME OF EFFECTIVE USER
      if(currentUser.getEffectiveUser()!=null && currentUser.getEffectiveUser().getDisplayName()!=null)
          taskService.setVariable(task.getId(), task.getId() + " :effectiveUser", currentUser.getEffectiveUser().getDisplayName());


      JSONObject object = new JSONObject();

    // recuperate the object from flowable
    object.putAll((Map) taskService.getVariable(task.getId(), "data"));

    // recuperate list of authors
    List<String> listAuthors = _getCandidateGroups(task.getId());

    // recuperate list of readers
    List<String> listReaders = _getCandidateUsers(task.getId());

    // add authors to object
    object.put("authors",listAuthors);

    // add readers to object
    object.put("readers",listReaders);

      // Perciste BpmJob data case instance unfinished

      BpmJob bpmJob = bpmJobService._persisteBpmJob(object, task.getName(), task.getAssignee(), false, task.getProcessInstanceId(), Long.valueOf((Integer) object.get("id")), Long.valueOf((Integer) object.get("classId")));

      // parse bpmJob to JSONObject
      org.json.JSONObject bpmJobJSONObject = bpmJob.toJSON();

      // add authorization to bpmJobJSONObject
      bpmJobJSONObject.put(SecurityConstants.HEADER_STRING_AUTHORIZATION, currentUser.getToken());
      System.out.println("processInstance.getProcessDefinitionName()   Initialisation Workflow  " + processInstance.getProcessDefinitionName());
      bpmJobJSONObject.put("processName", processInstance.getProcessDefinitionName());


      // create new BpmUpdate Event
      kernelInterface.createEvent(bpmInit, bpmJobJSONObject, bpmJob.getObjectID(), object.get("className").toString(), null, null,null, null);

    return object;
  }

    /**
     * @return A respnse of {@link String} : Return instance id.
     */
    public BpmJob startProcessInstanceWithoutEvent(String currentUserSid, String processKey, Object objet) throws Exception {

        // initiate variables
        Map<String, Object> variables = new HashMap<>();

        variables.put("initiator", currentUserSid);

        variables.put("processKey", processKey);

        variables.put("data", objet);

        variables.put("sids", currentUser.getSid());

        // create new process instance using the process key and list of variables
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(variables.get("processKey").toString(), variables);

        //commented by IH with instructions from WA on 29/03/2022
        // recuperate the first task of instance
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).active().singleResult();

        //persist the comment in workflow variables
        //DISPLAYNAME OF USER AUTHENTIFIED
        taskService.setVariable(task.getId(), task.getId() + " :authentifier", currentUser.getDisplayName());

        //DISPLAYNAME OF EFFECTIVE USER
        if(currentUser.getEffectiveUser()!=null && currentUser.getEffectiveUser().getDisplayName()!=null)
            taskService.setVariable(task.getId(), task.getId() + " :effectiveUser", currentUser.getEffectiveUser().getDisplayName());


        JSONObject object = new JSONObject();

        // recuperate the object from flowable

//        object.putAll((Map) new JSONParser().parse(new Gson().toJson(taskService.getVariable(task.getId(), "data"))));

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeTypeAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .create();
        object.putAll((Map) new JSONParser().parse(gson.toJson(taskService.getVariable(task.getId(), "data"))));

        // recuperate list of authors
        List<String> listAuthors = _getCandidateGroups(task.getId());

        // recuperate list of readers
        List<String> listReaders = _getCandidateUsers(task.getId());

        // add authors to object
        object.put("authors",listAuthors);

        // add readers to object
        object.put("readers",listReaders);

        // Perciste BpmJob data case instance unfinished

        BpmJob bpmJob = bpmJobService._persisteBpmJob(object, task.getName(), task.getAssignee(), false, task.getProcessInstanceId(), Long.valueOf(object.get("id").toString()), Long.valueOf(object.get("classId").toString()));

        bpmJob.setProcessName(processInstance.getProcessDefinitionName());

        bpmJob.setProcessID(processInstance.getProcessInstanceId());

        bpmJob.setDataObject(taskService.getVariable(task.getId(), "data"));

        bpmJob.setAuthors(listAuthors);

        bpmJob.setReaders(listReaders);

        return bpmJob;
    }

    /**
     *
     * @param processInstanceId
     *     A String of {@link String} : process instance id.
     * @return
     *     A respnse of {@link HistoricActivityInstance} : Return activity instance object.
     */
    public HistoricActivityInstance getParentActivityInstance(String processInstanceId){
        List<HistoricActivityInstance> result=historyService.createNativeHistoricActivityInstanceQuery().sql("SELECT * FROM act_hi_actinst WHERE act_type_ = 'callActivity' AND call_proc_inst_id_ = '"+processInstanceId+"'").list();
        if(!result.isEmpty())
         return result.get(0);
        else
            return null;
    }

  /**
   *
   * @param initiator
   *     A String of {@link String} : process instance initiator name.
   * @param processKey
   *     A String of {@link String} : process definition key.
   * @param identifiant
   *     A String of {@link String} : identifiant du l'objet principale.
   * @return
   *     A respnse of {@link String} : Return instance id.
   */
  public String startProcessInstance(String initiator, String processKey, String identifiant) {

    // initialize map of variables to create new instance
    Map<String, Object> variables = new HashMap<String, Object>();
    variables.put("person", initiator);
    variables.put("initiator", initiator);
    variables.put("identifiant", identifiant);

    // create new process instance using the process key and list of variables
    ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processKey, variables);

    // recuperate the first task of instance
    Task task = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).list().get(0);

    // assignate the initiator to the first task
    taskService.setAssignee(task.getId(), initiator);

    // return the process instance id
    return processInstance.getId();
  }

  /**
   *
   * @param processInstanceId
   *     A String of {@link String} : process instance id.
   * @return
   *     A respnse of {@link ArrayList} : Return instance id.
   */
  public List getGatewayDecision(String processInstanceId) {

    try {
      // recuperate current task
      Task task = getActifTaskOfProcessInstance(processInstanceId);

      // extract current activity name
      String task_name = task.getName();

      // recuperate process model
      Process process = getProcessModel(processInstanceId);

      // extract list of flow sequences from process model
      List<SequenceFlow> sequenceFlows = process.findFlowElementsOfType(SequenceFlow.class);

      // initialize list of decisions
      List<String> decisionlist = new ArrayList<>();
      SequenceFlow sequenceFlowTemp = null;
      // iterate and calculate decisions
      for (SequenceFlow sequenceFlow : sequenceFlows) {
        sequenceFlowTemp = sequenceFlow;
        if (sequenceFlow.getSourceRef().equals(task.getTaskDefinitionKey())) {
          if (sequenceFlow.getTargetFlowElement().getClass().equals(UserTask.class) || sequenceFlow.getTargetFlowElement().getClass().equals(EndEvent.class) || sequenceFlow.getTargetFlowElement().getClass().equals(ServiceTask.class) || sequenceFlow.getTargetFlowElement().getClass().equals(CallActivity.class)) {
            if (sequenceFlow.getName().indexOf("sys_") == -1) {
              decisionlist.add(sequenceFlow.getName());
            }
          } else {
            Boolean test = false;
            for (SequenceFlow sequenceFlow2 : sequenceFlows) {
              if (sequenceFlow2.getSourceRef().equals(sequenceFlow.getTargetFlowElement().getId())) {
                if (sequenceFlow2.getName().indexOf("sys_") == -1) {
                  if(sequenceFlow2.getTargetFlowElement().getClass().equals(ExclusiveGateway.class)){
                    test = true;

                  }
                }
              }
            }
            if(test){
              if(sequenceFlowTemp.getName() != null) {
                if (!sequenceFlowTemp.getName().trim().equals("")) {
                  return Collections.singletonList(sequenceFlowTemp.getName());
                }
              }
            }
            for (SequenceFlow sequenceFlow2 : sequenceFlows) {
              if (sequenceFlow2.getSourceRef().equals(sequenceFlow.getTargetFlowElement().getId())) {
                if (sequenceFlow2.getName().indexOf("sys_") == -1) {
                  if(sequenceFlow2.getExtensionElements().size() != 0){
                    if(sequenceFlow2.getExtensionElements().containsKey("properties")){
                      for(ExtensionElement extensionElement: sequenceFlow2.getExtensionElements().get("properties")) {
                        for(ExtensionElement extensionElementChild: extensionElement.getChildElements().get("property")) {
                          if(extensionElementChild.getAttributes().get("name").get(0).getValue().equals("hidden")) {
                            String expressionFM = extensionElementChild.getAttributes().get("value").get(0).getValue();
                            boolean hidden = false;
                              Map<String, Object> variables=taskService.getVariables(task.getId());
                              variables.put("currentUser", currentUser);
                              Template t = new Template(null, expressionFM, config);
                              //result must be true
                              try {
                                  String result = FreeMarkerTemplateUtils.processTemplateIntoString(t, variables);
                                  hidden=Boolean.parseBoolean(result);
                              }
                              catch (Exception e)
                              {

                              }
                            if (!hidden) {
                              decisionlist.add(sequenceFlow2.getName());
                            }
                          }
                        }
                      }
                    }else{
                      decisionlist.add(sequenceFlow2.getName());
                    }
                  }else{
                    decisionlist.add(sequenceFlow2.getName());
                  }
                }
              }
            }
          }

        }
      }

      // return list of decisions
      return decisionlist;

    }catch (Exception e){
      return  new ArrayList();
    }
  }

  /**
   *
   * @param processInstanceId
   *     A String of {@link String} : process definition key.
   * @return
   *     A respnse of {@link ArrayList} : Return instance id.
   */
  public List<String> getGatewayDecisionFromModel(String processInstanceId) throws FileNotFoundException {


    String processKey = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).list().get(0).getProcessDefinitionKey();


    // recuperate list process definitions by key
    ProcessDefinitionQuery listprocess = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processKey).latestVersion();

    // recuperate BpmnModel
    BpmnModel bpmnModel = repositoryService.getBpmnModel(listprocess.list().get(0).getId());

    // recuperate process model
    Process process = bpmnModel.getProcessById(processKey);

    // extract list of flow sequences from process model
    List<SequenceFlow> sequenceFlows = process.findFlowElementsOfType(SequenceFlow.class);

    // initialize list of decisions
    List<String> decisionlist = new ArrayList<>();

    // iterate flow sequences and calculate decisions
    for (SequenceFlow sequenceFlow : sequenceFlows) {
      for (SequenceFlow sequenceFlow1 : sequenceFlows) {
        if (sequenceFlow1.getTargetFlowElement().getId().equals(sequenceFlow.getSourceFlowElement().getId()) && sequenceFlow1.getSourceFlowElement().getClass().equals(StartEvent.class)) {
          if (sequenceFlow.getTargetFlowElement().getClass().equals(UserTask.class) || sequenceFlow.getTargetFlowElement().getClass().equals(EndEvent.class) || sequenceFlow.getTargetFlowElement().getClass().equals(ServiceTask.class)) {
            if (sequenceFlow.getName().indexOf("sys_") == -1) {
              decisionlist.add(sequenceFlow.getName());
            }
          } else {
            for (SequenceFlow sequenceFlow2 : sequenceFlows) {
              if (sequenceFlow2.getSourceRef().equals(sequenceFlow.getTargetFlowElement().getId())) {
                if (sequenceFlow2.getName().indexOf("sys_") == -1) {
                  decisionlist.add(sequenceFlow2.getName());
                }
              }
            }
          }
        }
      }
    }
    // return decisions list
    return decisionlist;
  }



  /**
   *
   * @param processInstanceId
   *     A String of {@link String} : process instance id.
   * @return
   *     A respnse of {@link ArrayList} : Return historic instance list.
   */
  public List<HistoricProcessInstance> findAllHistoricProcessInstance(Pageable page, String processInstanceId) {
    if(!processInstanceId.equals("null")) {
      return historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).list();
    }else{
      return historyService.createHistoricProcessInstanceQuery().orderByProcessInstanceStartTime().desc().list();
    }
  }

  /**
   *
   * @param processInstanceId
   *     A String of {@link String} : process instance id.
   * @return
   *     A respnse of {@link ArrayList} : Return historic instance list.
   */
  public Object getOutput(String processInstanceId,String name,String type) {


    Set<String> listActTypes = new HashSet<>();
    listActTypes.add("userTask");
    listActTypes.add("endEvent");
    HistoricActivityInstance act = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).activityTypes(listActTypes).orderByHistoricActivityInstanceStartTime().desc().list().get(0);

    // recuperate process model
    Process process = getProcessModel(processInstanceId);

    // extract list of flow sequences from process model
    List<Activity> activities = process.findFlowElementsOfType(Activity.class);


    for(Activity activity : activities){
      if(activity.getId().equals(act.getActivityId())){
        if(activity.getExtensionElements().size() != 0) {
          if (activity.getExtensionElements().containsKey("inputOutput")) {
            for (ExtensionElement extensionElement : activity.getExtensionElements().get("inputOutput")) {
              for (ExtensionElement extensionElementChild : extensionElement.getChildElements().get("outputParameter")) {
                 if(extensionElementChild.getAttributes().get("name").get(0).getValue().equals(name) && extensionElementChild.getChildElements().get(type)!=null)
                 {
                     if(type.equals("map"))
                     {
                         Map<String, String> result  = new HashMap<String, String>();
                         for (ExtensionElement extensionElementChild2 : extensionElementChild.getChildElements().get(type).get(0).getChildElements().get("entry"))
                         {

                             result.put(extensionElementChild2.getAttributes().get("key").get(0).getValue(),extensionElementChild2.getElementText());

                         }
                         return result;
                     }
                     else if(type.equals("list"))
                     {
                         List<String> result =new ArrayList<>();
                         for (ExtensionElement extensionElementChild2 : extensionElementChild.getChildElements().get(type).get(0).getChildElements().get("value"))
                         {

                             result.add(extensionElementChild2.getElementText());

                         }
                         return result;

                     }


                 }
                 else if(type.equals("string"))
                 {
                     String result=extensionElementChild.getElementText();
                     return result;
                 }



              }
            }
          }
        }
      }
    }

    return null;
  }




    public Object getInput(String processInstanceId,String name,String type) {


//        Set<String> listActTypes = new HashSet<>();
//        listActTypes.add("userTask");
//        listActTypes.add("endEvent");
//        listActTypes.add("callActivity");
//        HistoricActivityInstance act = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).activityTypes(listActTypes).orderByHistoricActivityInstanceStartTime().desc().list().get(0);
        HistoricActivityInstance act = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).orderByHistoricActivityInstanceEndTime().desc().list().get(0);

        // recuperate process model


        Process process = repositoryService.getBpmnModel(act.getProcessDefinitionId()).getMainProcess();

        // extract list of flow sequences from process model
        if(act.getActivityType().equals("endEvent")){
            List<EndEvent> activities = process.findFlowElementsOfType(EndEvent.class);
            for(EndEvent activity : activities){
                if(activity.getId().equals(act.getActivityId())){
                    if(activity.getExtensionElements().size() != 0) {
                        if (activity.getExtensionElements().containsKey("inputOutput")) {
                            for (ExtensionElement extensionElement : activity.getExtensionElements().get("inputOutput")) {
                                for (ExtensionElement extensionElementChild : extensionElement.getChildElements().get("inputParameter")) {
                                    if(extensionElementChild.getAttributes().get("name").get(0).getValue().equals(name) )
                                    {
                                        if(extensionElementChild.getChildElements().get(type)!=null){
                                            if (type.equals("map")) {
                                            Map<String, String> result = new HashMap<String, String>();
                                            for (ExtensionElement extensionElementChild2 : extensionElementChild.getChildElements().get(type).get(0).getChildElements().get("entry")) {

                                                result.put(extensionElementChild2.getAttributes().get("key").get(0).getValue(), extensionElementChild2.getElementText());

                                            }
                                            return result;
                                        } else if (type.equals("list")) {
                                            List<String> result = new ArrayList<>();
                                            for (ExtensionElement extensionElementChild2 : extensionElementChild.getChildElements().get(type).get(0).getChildElements().get("value")) {

                                                result.add(extensionElementChild2.getElementText());

                                            }
                                            return result;

                                        }


                                    } else if (type.equals("string")) {
                                        String result = extensionElementChild.getElementText();
                                        return result;
                                    }
                                }



                                }
                            }
                        }
                    }
                }
            }
        }else{
            List<Activity> activities = process.findFlowElementsOfType(Activity.class);
            for(Activity activity : activities){
                if(activity.getId().equals(act.getActivityId())){
                    if(activity.getExtensionElements().size() != 0) {
                        if (activity.getExtensionElements().containsKey("inputOutput")) {
                            for (ExtensionElement extensionElement : activity.getExtensionElements().get("inputOutput")) {
                                for (ExtensionElement extensionElementChild : extensionElement.getChildElements().get("inputParameter")) {
                                    if(extensionElementChild.getAttributes().get("name").get(0).getValue().equals(name) )
                                    {
                                        if(extensionElementChild.getChildElements().get(type)!=null)
                                        {
                                            if(type.equals("map"))
                                            {
                                                Map<String, String> result  = new HashMap<String, String>();
                                                for (ExtensionElement extensionElementChild2 : extensionElementChild.getChildElements().get(type).get(0).getChildElements().get("entry"))
                                                {

                                                    result.put(extensionElementChild2.getAttributes().get("key").get(0).getValue(),extensionElementChild2.getElementText());

                                                }
                                                return result;
                                            }
                                            else if(type.equals("list"))
                                            {
                                                List<String> result =new ArrayList<>();
                                                for (ExtensionElement extensionElementChild2 : extensionElementChild.getChildElements().get(type).get(0).getChildElements().get("value"))
                                                {

                                                    result.add(extensionElementChild2.getElementText());

                                                }
                                                return result;

                                            }


                                        }
                                        else if(type.equals("string"))
                                        {
                                            String result=extensionElementChild.getElementText();
                                            return result;
                                        }
                                    }




                                }
                            }
                        }
                    }
                }
            }
        }





        return null;
    }






  public JSONObject getProcessInstanceExtensionProperties(String processInstanceId) {


    Set<String> listActTypes = new HashSet<>();
    listActTypes.add("userTask");
    listActTypes.add("endEvent");
    HistoricActivityInstance act = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).activityTypes(listActTypes).orderByHistoricActivityInstanceStartTime().desc().list().get(0);

    // recuperate process model
    Process process = getProcessModel(processInstanceId);

    // extract list of flow sequences from process model
    List<Activity> activities = process.findFlowElementsOfType(Activity.class);

    JSONObject jsonObject= new JSONObject();

    for(Activity activity : activities){
      if(activity.getId().equals(act.getActivityId())){
        if(activity.getDataOutputAssociations().size() != 0) {
          if (activity.getExtensionElements().containsKey("properties")) {
            for (ExtensionElement extensionElement : activity.getExtensionElements().get("properties")) {
              for (ExtensionElement extensionElementChild : extensionElement.getChildElements().get("property")) {
                String name = extensionElementChild.getAttributes().get("name").get(0).getValue().trim();
                String value = extensionElementChild.getAttributes().get("value").get(0).getValue().trim();
                jsonObject.put(name, value);
              }
            }
          }
        }
      }
    }
    return jsonObject;
  }

  /**
   *
   * @param processInstanceId
   *     A String of {@link String} : process instance id.
   * @return
   *     A respnse of {@link ArrayList} : Return historic instance list.
   */
  public List<HistoricWF> getHistoricProcess(String processInstanceId) {

    // initialize the return list of historics
    List<HistoricWF> his = new ArrayList<HistoricWF>();

    // cretae historic task instace query
    NativeHistoricTaskInstanceQuery taskQuery = historyService.createNativeHistoricTaskInstanceQuery();
    taskQuery.sql("SELECT * FROM act_hi_taskinst WHERE PROC_INST_ID_='" + processInstanceId + "' AND END_TIME_ IS NOT NULL ORDER BY START_TIME_ ASC");

    // initialize temp list of historics
    List<HistoricTaskInstance> copietasks = new ArrayList<HistoricTaskInstance>();

    // iterate query result and push to the temp list
    for (int d = 0; d < taskQuery.list().size(); d++)
      copietasks.add(taskQuery.list().get(d));

    List<HistoricTaskInstance> tasks = copietasks;

    // iterate historic and build the return list
    for (HistoricTaskInstance taskk : tasks) {
      NativeHistoricVariableInstanceQuery historicQuery = historyService.createNativeHistoricVariableInstanceQuery();
        String decision = "";
      if(historicQuery.sql("SELECT TEXT_ FROM act_hi_varinst WHERE NAME_ = '" + taskk.getId() + " :decision" + "'").list().size() > 0) {
          HistoricVariableInstance decisionVariable = historicQuery.sql("SELECT TEXT_ FROM act_hi_varinst WHERE NAME_ = '" + taskk.getId() + " :decision" + "'").list().get(0);
           decision = String.valueOf(decisionVariable).substring(String.valueOf(decisionVariable).lastIndexOf("=") + 1, String.valueOf(decisionVariable).lastIndexOf("]"));
      }else{
          decision = "Automatique";
      }
      String description = "";
      try {
        HistoricVariableInstance descriptionVariable = historicQuery.sql("SELECT TEXT_ FROM act_hi_varinst WHERE NAME_ = '" + taskk.getId() + " :description" + "'").list().get(0);
        description = String.valueOf(descriptionVariable).substring(String.valueOf(descriptionVariable).lastIndexOf("=") + 1, String.valueOf(descriptionVariable).lastIndexOf("]"));
      } catch (Exception e) { }

      String authentifier = "";
      if(historicQuery.sql("SELECT TEXT_ FROM act_hi_varinst WHERE NAME_ = '" + taskk.getId() + " :authentifier" + "'").list().size() > 0) {
          try {
              HistoricVariableInstance authentifierVariable = historicQuery.sql("SELECT TEXT_ FROM act_hi_varinst WHERE NAME_ = '" + taskk.getId() + " :authentifier" + "'").list().get(0);
              authentifier = String.valueOf(authentifierVariable).substring(String.valueOf(authentifierVariable).lastIndexOf("=") + 1, String.valueOf(authentifierVariable).lastIndexOf("]"));
          } catch (Exception e) {
          }
      }else{
          authentifier = "Systeme";
      }
        String effectiveUser = "";
        try {
            HistoricVariableInstance effectiveUserVariable = historicQuery.sql("SELECT TEXT_ FROM act_hi_varinst WHERE NAME_ = '" + taskk.getId() + " :effectiveUser" + "'").list().get(0);
            effectiveUser = String.valueOf(effectiveUserVariable).substring(String.valueOf(effectiveUserVariable).lastIndexOf("=") + 1, String.valueOf(effectiveUserVariable).lastIndexOf("]"));
        } catch (Exception e) { }

        HistoricWF historicWF = new HistoricWF(taskk.getName(),
                decision,
                taskk.getAssignee(),
                ZonedDateTime.ofInstant(taskk.getCreateTime().toInstant(), ZoneId.systemDefault()),
                ZonedDateTime.ofInstant(taskk.getEndTime().toInstant(), ZoneId.systemDefault()),
                description,
                authentifier,effectiveUser);
        his.add(historicWF);
    }

    // return likst of historic
    return his;
  }




  /**
   *
   * @param processInstanceId
   *     A String of {@link String} : process instance id.
   * @return
   *     A respnse of {@link ArrayList} : Return historic activity instance list.
   */
  public HistoricActivityInstance getActifActOfProcessInstance(String processInstanceId){
    return historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).unfinished().list().get(0);
  }


  /**
   * @Param
   *     A hash map of {@link Map} : contains a list of variables
   * @return
   *     A respnse of {@link JSONObject} : Return historic instance list.
   */
  //@Transactional
  //TODO AG
  public JSONObject _nextTask(Map<String, Object> variables) throws Exception {

    // extract process instance id
    String processInstanceId = ((Map)( (Map) variables.get("data")).get("workflow")).get("wfProcessID").toString();

    // recuperate the current task before rooting
      Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).active().singleResult();

      // complete the current instance
      _finishTask(task.getId(), variables);

      HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();

      // processing the new wf state
      JSONObject object = _processingProcessInstanceChanges(processInstanceId, processInstance.getSuperProcessInstanceId() == null);

      // recuperate BpmJob Object
      BpmJob bpmJob = bpmJobRepository.findByObjectIDAndClassID(Long.valueOf((Integer) object.get("id")), Long.valueOf((Integer) object.get("classId"))).get();

      // parse bpmJob to JSONObject
      org.json.JSONObject bpmJobJSONObject = bpmJob.toJSON();

      // add authorization to bpmJobJSONObject
      bpmJobJSONObject.put(SecurityConstants.HEADER_STRING_AUTHORIZATION, currentUser.getToken());
      System.out.println("processInstance.getProcessDefinitionName()   Routage Workflow  " + processInstance.getProcessDefinitionName());
      bpmJobJSONObject.put("processName", processInstance.getProcessDefinitionName());
      // create new BpmUpdate Event

      kernelInterface.createEvent(bpmUpdate, bpmJobJSONObject, Long.valueOf((Integer) object.get("id")), object.get("className").toString(), null, null, null, null);

      return bpmJob.toJSONSimple();
  }

  /**
   * @Param
   *     A hash map of {@link Map} : contains a list of variables
   * @return
   *     A respnse of {@link List< JSONObject>} : Return historic instance list.
   */
  //@Transactional
  //TODO AG
  public List<JSONObject> _nextTaskBatch(Map<String, Object> variables) throws Exception {

      // initialize the list of result objects
      List<JSONObject> bpmJobsResult = new ArrayList<>();

      if(variables.containsKey("processInstanceIds"))
          throw new BadRequestAlertException("empty selected instances", "WORKFLOW", "InstanceExists");

      // extract process instance id's
      List<String> processInstanceIds = (List<String>) variables.get("processInstanceIds");

      Task firsttask = taskService.createTaskQuery().processInstanceId(processInstanceIds.get(0)).active().singleResult();

      String taskDefinitionId = firsttask.getTaskDefinitionId();

      for(String processInstanceId : processInstanceIds){

          // recuperate the current task before rooting
          Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).active().singleResult();

          if(!(task.getTaskDefinitionId().equals(taskDefinitionId)))
              throw new BadRequestAlertException("Different instances states", "WORKFLOW", "DiffrentStates");
      }

      // iterate the list of process instances id's
      for(String processInstanceId : processInstanceIds){

          // recuperate the current task before rooting
          Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).active().singleResult();

          // extract la list des variables de la derniere activité
          Map<String, Object> extractedVariables =  task.getProcessVariables();

          // put the new decision in the extracted set of variables
          extractedVariables.put("Decision", variables.get("Decision"));

          // put the new description or comment in the extracted set of variables
          extractedVariables.put("description", variables.get("description"));

          // complete the current instance
          _finishTask(task.getId(), variables);

          // extract l'historic of the process instance
          HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();

          // processing the new wf state
          JSONObject object = _processingProcessInstanceChanges(processInstanceId, processInstance.getSuperProcessInstanceId() == null);

          // recuperate BpmJob Object
          BpmJob bpmJob = bpmJobRepository.findByObjectIDAndClassID(Long.valueOf((Integer) object.get("id")), Long.valueOf((Integer) object.get("classId"))).get();

          // parse bpmJob to JSONObject
          org.json.JSONObject bpmJobJSONObject = bpmJob.toJSON();

          // add authorization to bpmJobJSONObject
          bpmJobJSONObject.put(SecurityConstants.HEADER_STRING_AUTHORIZATION, currentUser.getToken());
          System.out.println("processInstance.getProcessDefinitionName()   Routage Workflow  " + processInstance.getProcessDefinitionName());
          bpmJobJSONObject.put("processName", processInstance.getProcessDefinitionName());
          // create new BpmUpdate Event

          kernelInterface.createEvent(bpmUpdate, bpmJobJSONObject, Long.valueOf((Integer) object.get("id")), object.get("className").toString(), null, null, null, null);

          bpmJobsResult.add(bpmJob.toJSONSimple());

      }

      return bpmJobsResult;
  }

  /**
   * @Param
   *     A hash map of {@link Map} : contains a list of variables
   * @return
   *     A respnse of {@link List< JSONObject>} : Return historic instance list.
   */
  //@Transactional
  //TODO AG
  public List<JSONObject> _nextTaskBatchWithoutEvent(Map<String, Object> variables) throws Exception {

      // initialize the list of result objects
      List<JSONObject> bpmJobsResult = new ArrayList<>();

      if(variables.containsKey("processInstanceIds"))
          throw new BadRequestAlertException("empty selected instances", "WORKFLOW", "InstanceExists");

      // extract process instance id's
      List<String> processInstanceIds = (List<String>) variables.get("processInstanceIds");

      Task firsttask = taskService.createTaskQuery().processInstanceId(processInstanceIds.get(0)).active().singleResult();

      String taskDefinitionId = firsttask.getTaskDefinitionId();

      for(String processInstanceId : processInstanceIds){

          // recuperate the current task before rooting
          Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).active().singleResult();

          if(!(task.getTaskDefinitionId().equals(taskDefinitionId)))
              throw new BadRequestAlertException("Different instances states", "WORKFLOW", "DiffrentStates");
      }

      // iterate the list of process instances id's
      for(String processInstanceId : processInstanceIds){

          // recuperate the current task before rooting
          Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).active().singleResult();

          // extract la list des variables de la derniere activité
          Map<String, Object> extractedVariables =  task.getProcessVariables();

          // put the new decision in the extracted set of variables
          extractedVariables.put("Decision", variables.get("Decision"));

          // put the new description or comment in the extracted set of variables
          extractedVariables.put("description", variables.get("description"));

          // complete the current instance
          _finishTask(task.getId(), variables);

          // extract l'historic of the process instance
          HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();

          // processing the new wf state
          JSONObject object = _processingProcessInstanceChanges(processInstanceId, processInstance.getSuperProcessInstanceId() == null);

          // recuperate BpmJob Object
          BpmJob bpmJob = bpmJobRepository.findByObjectIDAndClassID(Long.valueOf((Integer) object.get("id")), Long.valueOf((Integer) object.get("classId"))).get();

          // parse bpmJob to JSONObject
          JSONObject bpmJobJSONObject = bpmJob.toJSONSimple();

          // add authorization to bpmJobJSONObject
          bpmJobJSONObject.put("processName", processInstance.getProcessDefinitionName());

          bpmJobsResult.add(bpmJobJSONObject);

      }

      return bpmJobsResult;
  }

 /**
  * @return A respnse of {@link JSONObject} : Return historic instance list.
  * @Param A hash map of {@link Map} : contains a list of variables
  */
  //@Transactional
  //TODO AG
  public BpmJob _nextTaskWithoutEvent(String processInstanceId, String decision, String wfComment, Object data, AclClass aclClass) throws Exception {

      // initialize the set of variables to send it to flowable
      Map<String, Object> variables = new HashMap<>();

      variables.put("Decision", decision);

      variables.put("description", wfComment);

      variables.put("data", data);

      variables.put("sids", currentUser.getSid());

      // recuperate the current task before rooting
      Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).active().singleResult();

      // complete the current instance
      _finishTask(task.getId(), variables);

      HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();

      JSONObject object = new JSONObject();

      if(processInstance.getEndTime() != null) {

          HistoricActivityInstance activityInstance = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).activityId(processInstance.getEndActivityId()).singleResult();

          Object dataObj = historyService.createHistoricVariableInstanceQuery().variableName("data").processInstanceId(processInstance.getId()).singleResult().getValue();
          Gson gson = new GsonBuilder()
                  .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeTypeAdapter())
                  .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                  .registerTypeAdapter(Instant.class, new InstantTypeAdapter())
                  .create();
          // recuperate the object from flowable
          object.putAll((Map) new JSONParser().parse(gson.toJson(dataObj)));

          BpmJob bpmJob = bpmJobService._persisteBpmJob(object, activityInstance.getActivityName(), activityInstance.getAssignee(), false, activityInstance.getProcessInstanceId(), Long.valueOf(object.get("id").toString()), Long.valueOf(aclClass.getId().toString()));

          bpmJob.setProcessName(processInstance.getProcessDefinitionName());

          bpmJob.setProcessID(processInstance.getId());

          bpmJob.setDataObject(dataObj);

          bpmJob.setAuthors(new ArrayList<>());

          bpmJob.setReaders(new ArrayList<>());

          return bpmJob;

      }else {

          task = taskService.createTaskQuery().processInstanceId(processInstanceId).active().singleResult();

          Object dataObj = taskService.getVariable(task.getId(), "data");
          Gson gson = new GsonBuilder()
                  .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeTypeAdapter())
                  .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                  .create();

          // recuperate the object from flowable
          object.putAll((Map) new JSONParser().parse(gson.toJson(dataObj)));

          // recuperate list of authors
          List<String> listAuthors = _getCandidateGroups(task.getId());

          // recuperate list of readers
          List<String> listReaders = _getCandidateUsers(task.getId());

          BpmJob bpmJob = bpmJobService._persisteBpmJob(object, task.getName(), task.getAssignee(), false, task.getProcessInstanceId(), Long.valueOf(object.get("id").toString()), Long.valueOf(aclClass.getId().toString()));

          bpmJob.setProcessName(processInstance.getProcessDefinitionName());

          bpmJob.setProcessID(processInstance.getId());

          bpmJob.setDataObject(dataObj);

          bpmJob.setAuthors(listAuthors);

          bpmJob.setReaders(listReaders);

          return bpmJob;
      }



  }


  /**
   * @Param
   *     A hash map of {@link Map} : contains a list of variables
   * @Param
   *     A hash map of {@link List} : contains a set of decisions
   * @return
   *     A respnse of {@link JSONObject} : Return historic instance list.
   */
  //@Transactional
  //TODO AG
  public JSONObject initInstance_nextTask(Map<String, Object> variables) throws Exception {

    // create new process instance using the process key and list of variables
    ProcessInstance initiedProcessInstance = runtimeService.startProcessInstanceByKey(variables.get("processKey").toString(), variables);

    // extract process instance id
    String processInstanceId = initiedProcessInstance.getProcessInstanceId();

    // foreach all decisions
    for(String decision : (List<String>) variables.get("decisons")) {

        Map<String, Object> data = (Map<String, Object>) historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).variableName("data").singleResult().getValue();

        // recuperate the current task before rooting
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).active().singleResult();

        // set decisions to set of variables
        variables.put("Decision", decision);

        // identify the executer of the decision
        variables.put("autentifier", currentUser.getDisplayName());

        // set data object
        variables.put("data", data);

        // complete the current instance
        _finishTask(task.getId(), variables);
    }

      HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();

      // processing the new wf state
      JSONObject object = _processingProcessInstanceChanges(processInstanceId, processInstance.getSuperProcessInstanceId() == null);

      // recuperate BpmJob Object
      BpmJob bpmJob = bpmJobRepository.findByObjectIDAndClassID(Long.valueOf((Integer) object.get("id")), Long.valueOf((Integer) object.get("classId"))).get();

      // parse bpmJob to JSONObject
      org.json.JSONObject bpmJobJSONObject = bpmJob.toJSON();


      // add authorization to bpmJobJSONObject
      bpmJobJSONObject.put(SecurityConstants.HEADER_STRING_AUTHORIZATION, currentUser.getToken());
      System.out.println("processInstance.getProcessDefinitionName()   Routage Workflow  " + processInstance.getProcessDefinitionName());

      bpmJobJSONObject.put("processName", processInstance.getProcessDefinitionName());

      // create new BpmUpdate Event
      kernelInterface.createEvent(bpmUpdate, bpmJobJSONObject, Long.valueOf((Integer) object.get("id")), object.get("className").toString(), null, null, null, null);

      return bpmJob.toJSONSimple();
  }

  /**
   * @Param
   *     A hash map of {@link Map} : contains a list of variables
   * @Param
   *     A hash map of {@link List} : contains a set of decisions
   * @return
   *     A respnse of {@link JSONObject} : Return historic instance list.
   */
  //@Transactional
  //TODO AG
  public JSONObject initInstance_nextTaskWithoutEvent(Map<String, Object> variables) throws Exception {

    // create new process instance using the process key and list of variables
    ProcessInstance initiedProcessInstance = runtimeService.startProcessInstanceByKey(variables.get("processKey").toString(), variables);

    // extract process instance id
    String processInstanceId = initiedProcessInstance.getProcessInstanceId();

    // foreach all decisions
    for(String decision : (List<String>) variables.get("decisons")) {

        Map<String, Object> data = (Map<String, Object>) historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).variableName("data").singleResult().getValue();

        // recuperate the current task before rooting
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).active().singleResult();

        // set decisions to set of variables
        variables.put("Decision", decision);

        // identify the executer of the decision
        variables.put("autentifier", currentUser.getDisplayName());

        // set data object
        variables.put("data", data);

        // complete the current instance
        _finishTask(task.getId(), variables);
    }

      HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();

      // processing the new wf state
      JSONObject object = _processingProcessInstanceChanges(processInstanceId, processInstance.getSuperProcessInstanceId() == null);

      // recuperate BpmJob Object
      BpmJob bpmJob = bpmJobRepository.findByObjectIDAndClassID(Long.valueOf((Integer) object.get("id")), Long.valueOf((Integer) object.get("classId"))).get();

      // parse bpmJob to JSONObject
      JSONObject bpmJobJSONObject = bpmJob.toJSONSimple();
      bpmJobJSONObject.put("processName", processInstance.getProcessDefinitionName());

      return bpmJobJSONObject;
  }

  /**
   *
   * @param taskId
   *     A String of {@link Task} : current activity instance id.
   * @return
   *     A respnse of {@link ArrayList} : Return list of authors.
   */
  public List<String> _getCandidateGroups(String taskId) {

    // initialize a new list of groups
    List<String> Groups = new ArrayList<String>();

    // extract all actors of the current task
    List<? extends IdentityLinkInfo> identityLinks = taskService.getIdentityLinksForTask(taskId);

    // iterate the extracted list of actors
    for (IdentityLinkInfo identityLink : identityLinks) {

      // check the type of actor (is a candidate & and is a group)
      if (IdentityLinkType.CANDIDATE.equals(identityLink.getType()) && identityLink.getGroupId() != null) {

        // pushed to the result list
        Groups.add(identityLink.getGroupId());

      }
    }

    // return the list of candidate groups
    return  Groups;
  }


  /**
   *
   * @param taskId
   *     A String of {@link Task} : current activity instance id.
   * @return
   *     A respnse of {@link ArrayList} : Return list of readers.
   */
  public List<String> _getCandidateUsers(String taskId) {

    // initialize a new list of groups
    List<String> users = new ArrayList<String>();

    // extract all actors of the current task
    List<? extends IdentityLinkInfo> identityLinks = taskService.getIdentityLinksForTask(taskId);

    // iterate the extracted list of actors
    for (IdentityLinkInfo identityLink : identityLinks) {

      // check the type of actor (is a candidate & and is a user)
      if (IdentityLinkType.CANDIDATE.equals(identityLink.getType()) && identityLink.getUserId() != null) {

        // pushed to the result list
        users.add(identityLink.getUserId());

      }
    }

    // return the list of candidate groups
    return  users;
  }

  /**
   *
   * @param processInstanceId
   *     A String of {@link String} : current process instance id.
   * @return
   *     A respnse of {@link Void} : Return task Object.
   */
  public JSONObject _processingProcessInstanceChanges(String processInstanceId,Boolean isParrentProcess) throws ParseException {

    // initialize new JSONObject
       JSONObject object = new JSONObject();

      // recuperate the current task after rooting
      HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();

    // verify that process instance state not finished
    if (processInstance.getEndTime() == null) {

      object = _processingActivityInstanceChanges(processInstance.getId(),isParrentProcess);

    }else{

      if(processInstance.getSuperProcessInstanceId() != null){

        object.putAll((Map) historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstance.getId()).variableName("data").singleResult().getValue());

        JSONObject parentObject = new JSONObject();

        parentObject = _processingProcessInstanceChanges(processInstance.getSuperProcessInstanceId(),isParrentProcess);

        // recuperate BpmJob Object
        BpmJob bpmJob = bpmJobRepository.findByObjectIDAndClassID(Long.valueOf((Integer) parentObject.get("id")), Long.valueOf((Integer) parentObject.get("classId"))).get();

        object.put("parent", bpmJob.toJSON());

        // add authors to object
        object.put("authors", new ArrayList<>());

        // add readers to object
        object.put("readers", new ArrayList<>());

        // Perciste BpmJob data case sub instance finished
        bpmJobService._persisteBpmJob(object, historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstance.getId()).activityId(processInstance.getEndActivityId()).singleResult().getActivityName(), null, true, processInstance.getId(), Long.valueOf((Integer) object.get("id")), Long.valueOf((Integer) object.get("classId")));

      }else{

        object.putAll((Map) historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstance.getId()).variableName("data").singleResult().getValue());

        // add authors to object
        object.put("authors", new ArrayList<>());

        // add readers to object
        object.put("readers", new ArrayList<>());

        // Perciste BpmJob data case parent instance finished
        bpmJobService._persisteBpmJob(object, historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstance.getId()).activityId(processInstance.getEndActivityId()).singleResult().getActivityName(), null, true, processInstance.getId(), Long.valueOf((Integer) object.get("id")), Long.valueOf((Integer) object.get("classId")));

      }

    }

    return object;
  }

  /**
   *
   * @param processInstanceId
   *     A String of {@link String} : current process instance id.
   * @return
   *     A respnse of {@link Void} : Return task Object.
   */
  public JSONObject _processingProcessInstanceChangesTest(String processInstanceId,Boolean isParrentProcess) throws ParseException {

    // initialize new JSONObject
       JSONObject object = new JSONObject();

      // recuperate the current task after rooting
      HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();

    // verify that process instance state not finished
    if (processInstance.getEndTime() == null) {

      object = _processingActivityInstanceChangesTest(processInstance.getId(),isParrentProcess);

    }else{

      if(processInstance.getSuperProcessInstanceId() != null){

        object.putAll((Map) new JSONParser().parse(new Gson().toJson(historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstance.getId()).variableName("data").singleResult().getValue())) );

        JSONObject parentObject = new JSONObject();

        parentObject = _processingProcessInstanceChanges(processInstance.getSuperProcessInstanceId(),isParrentProcess);

        // recuperate BpmJob Object
        BpmJob bpmJob = bpmJobRepository.findByObjectIDAndClassID(Long.valueOf((Integer) parentObject.get("id")), Long.valueOf((Integer) parentObject.get("classId"))).get();

        object.put("parent", bpmJob.toJSON());

        // add authors to object
        object.put("authors", new ArrayList<>());

        // add readers to object
        object.put("readers", new ArrayList<>());

        // Perciste BpmJob data case sub instance finished
        bpmJobService._persisteBpmJob(object, historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstance.getId()).activityId(processInstance.getEndActivityId()).singleResult().getActivityName(), null, true, processInstance.getId(), Long.valueOf((Integer) object.get("id")), Long.valueOf((Integer) object.get("classId")));

      }else{

        object.putAll((Map) new JSONParser().parse(new Gson().toJson(historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstance.getId()).variableName("data").singleResult().getValue())) );

        // add authors to object
        object.put("authors", new ArrayList<>());

        // add readers to object
        object.put("readers", new ArrayList<>());

        // Perciste BpmJob data case parent instance finished
        bpmJobService._persisteBpmJob(object, historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstance.getId()).activityId(processInstance.getEndActivityId()).singleResult().getActivityName(), null, true, processInstance.getId(), Long.valueOf((Integer) object.get("id")), Long.valueOf((Integer) object.get("classId")));

      }

    }

    return object;
  }


  /**
   *
   * @param processInstanceId
   *     A String of {@link String} : current process instance id.
   * @return
   *     A respnse of {@link Void} : Return task Object.
   */
  public JSONObject _processingActivityInstanceChanges(String processInstanceId,Boolean isParrentProcess) throws ParseException {

    // recuperate current activity
    List<ActivityInstance> activityInstances = runtimeService.createActivityInstanceQuery().processInstanceId(processInstanceId).unfinished().list();

    List<ActivityInstance> activityInstances1 = new ArrayList<>();
      activityInstances1.add(activityInstances.get(0));
      activityInstances = activityInstances1;
    // initialize new JSONObject
    JSONObject object = new JSONObject();

    for(ActivityInstance activityInstance :activityInstances) {

      // check current activity type
      switch (activityInstance.getActivityType()) {

        // case current activity is a human task
        case "userTask": {

          // recuperate list of authors
          List<String> listAuthors = _getCandidateGroups(activityInstance.getTaskId());

          // recuperate list of readers
          List<String> listReaders = _getCandidateUsers(activityInstance.getTaskId());

          // recuperate the object from flowable
          object.putAll((Map) taskService.getVariable(activityInstance.getTaskId(), "data"));

          // add authors to object
          object.put("authors", listAuthors);

          // add readers to object
          object.put("readers", listReaders);

          // Perciste BpmJob data case instance unfinished
          bpmJobService._persisteBpmJob(object, activityInstance.getActivityName(), activityInstance.getAssignee(), false, activityInstance.getProcessInstanceId(), Long.valueOf((Integer) object.get("id")), Long.valueOf((Integer) object.get("classId")));

          break;
        }
        case "callActivity": {


          // recuperate all sub process instacnes
          List<HistoricProcessInstance> subProcessInstances = historyService.createHistoricProcessInstanceQuery().superProcessInstanceId(processInstanceId).list();

          List<JSONObject> subObjects = new ArrayList<>();
          if(isParrentProcess)
          {
              for (HistoricProcessInstance processInstance : subProcessInstances) {

            JSONObject subObject = _processingProcessInstanceChanges(processInstance.getId(),isParrentProcess);

            // recuperate BpmJob Object
            BpmJob bpmJob = bpmJobRepository.findByObjectIDAndClassID(Long.valueOf((Integer) subObject.get("id")), Long.valueOf((Integer) subObject.get("classId"))).get();

            subObject.putAll(bpmJob.toJSONSimple());

            subObject.put("wfProcessID", processInstance.getId());

            JSONObject workflow = new JSONObject();

            workflow.put("wfProcessID", processInstance.getId());

            subObject.put("workflow", workflow);

            // recuperate current activity
            List<ActivityInstance> subactivityInstances = runtimeService.createActivityInstanceQuery().processInstanceId(processInstance.getId()).unfinished().list();

            for(ActivityInstance act : subactivityInstances) {
              // recuperate list of authors
              List<String> listAuthors = _getCandidateGroups(act.getTaskId());

              // recuperate list of readers
              List<String> listReaders = _getCandidateUsers(act.getTaskId());

              // add authors to object
              object.put("authors", listAuthors);

              // add readers to object
              object.put("readers", listReaders);
            }

            // Perciste BpmJob data case sub instance finished
            bpmJobService._persisteBpmJob(subObject, bpmJob.getActivityName(), bpmJob.getAssignee(), bpmJob.getEndProcess(), bpmJob.getProcessID(), bpmJob.getObjectID(), bpmJob.getClassID());

            subObjects.add(subObject);
          }
          }


          object.putAll((Map) historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).variableName("data").singleResult().getValue());

          // add authors to object
          object.put("mmTaskByIdDTO", subObjects);

          // add authors to object
          object.put("authors", new ArrayList<>());

          // add readers to object
          object.put("readers", new ArrayList<>());

          // Perciste BpmJob data case unfinished instance has subProcess
          bpmJobService._persisteBpmJob(object, activityInstance.getActivityName(), activityInstance.getAssignee(), false, activityInstance.getProcessInstanceId(), Long.valueOf((Integer) object.get("id")), Long.valueOf((Integer) object.get("classId")));

          break;
        }

      }

    }


    return object;
  }


  /**
   *
   * @param processInstanceId
   *     A String of {@link String} : current process instance id.
   * @return
   *     A respnse of {@link Void} : Return task Object.
   */
  public JSONObject _processingActivityInstanceChangesTest(String processInstanceId,Boolean isParrentProcess) throws ParseException {

    // recuperate current activity
    List<ActivityInstance> activityInstances = runtimeService.createActivityInstanceQuery().processInstanceId(processInstanceId).unfinished().list();

    List<ActivityInstance> activityInstances1 = new ArrayList<>();
      activityInstances1.add(activityInstances.get(0));
      activityInstances = activityInstances1;
    // initialize new JSONObject
    JSONObject object = new JSONObject();

    for(ActivityInstance activityInstance :activityInstances) {

      // check current activity type
      switch (activityInstance.getActivityType()) {

        // case current activity is a human task
        case "userTask": {

          // recuperate list of authors
          List<String> listAuthors = _getCandidateGroups(activityInstance.getTaskId());

          // recuperate list of readers
          List<String> listReaders = _getCandidateUsers(activityInstance.getTaskId());

          // recuperate the object from flowable
          object.putAll((Map) taskService.getVariable(activityInstance.getTaskId(), "data"));

          // add authors to object
          object.put("authors", listAuthors);

          // add readers to object
          object.put("readers", listReaders);

          // Perciste BpmJob data case instance unfinished
          bpmJobService._persisteBpmJob(object, activityInstance.getActivityName(), activityInstance.getAssignee(), false, activityInstance.getProcessInstanceId(), Long.valueOf((Integer) object.get("id")), Long.valueOf((Integer) object.get("classId")));

          break;
        }
        case "callActivity": {


          // recuperate all sub process instacnes
          List<HistoricProcessInstance> subProcessInstances = historyService.createHistoricProcessInstanceQuery().superProcessInstanceId(processInstanceId).list();

          List<JSONObject> subObjects = new ArrayList<>();
          if(isParrentProcess)
          {
              for (HistoricProcessInstance processInstance : subProcessInstances) {

            JSONObject subObject = _processingProcessInstanceChanges(processInstance.getId(),isParrentProcess);

            // recuperate BpmJob Object
            BpmJob bpmJob = bpmJobRepository.findByObjectIDAndClassID(Long.valueOf((Integer) subObject.get("id")), Long.valueOf((Integer) subObject.get("classId"))).get();

            subObject.putAll(bpmJob.toJSONSimple());

            subObject.put("wfProcessID", processInstance.getId());

            JSONObject workflow = new JSONObject();

            workflow.put("wfProcessID", processInstance.getId());

            subObject.put("workflow", workflow);

            // recuperate current activity
            List<ActivityInstance> subactivityInstances = runtimeService.createActivityInstanceQuery().processInstanceId(processInstance.getId()).unfinished().list();

            for(ActivityInstance act : subactivityInstances) {
              // recuperate list of authors
              List<String> listAuthors = _getCandidateGroups(act.getTaskId());

              // recuperate list of readers
              List<String> listReaders = _getCandidateUsers(act.getTaskId());

              // add authors to object
              object.put("authors", listAuthors);

              // add readers to object
              object.put("readers", listReaders);
            }

            // Perciste BpmJob data case sub instance finished
            bpmJobService._persisteBpmJob(subObject, bpmJob.getActivityName(), bpmJob.getAssignee(), bpmJob.getEndProcess(), bpmJob.getProcessID(), bpmJob.getObjectID(), bpmJob.getClassID());

            subObjects.add(subObject);
          }
          }


          object.putAll((Map) historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).variableName("data").singleResult().getValue());

          // add authors to object
          object.put("mmTaskByIdDTO", subObjects);

          // add authors to object
          object.put("authors", new ArrayList<>());

          // add readers to object
          object.put("readers", new ArrayList<>());

          // Perciste BpmJob data case unfinished instance has subProcess
          bpmJobService._persisteBpmJob(object, activityInstance.getActivityName(), activityInstance.getAssignee(), false, activityInstance.getProcessInstanceId(), Long.valueOf((Integer) object.get("id")), Long.valueOf((Integer) object.get("classId")));

          break;
        }

      }

    }


    return object;
  }





  /**
   *
   *     A String of {@link String} : old task executionId
   *     A String of {@link String} : old task definition key
   * @return
   *     A respnse of {@link Void}.
   */
  public void _rollBackProcess( String executionId, String taskDefinitionKey) throws Exception {

    runtimeService.createChangeActivityStateBuilder()
            .moveExecutionToActivityId(executionId, taskDefinitionKey).changeState();

  }



  public List<JSONObject> getListFinalProcess() {


    List<JSONObject> listProcessActive = new ArrayList<>();

    List<ProcessDefinition> listActiveProcess = repositoryService.createProcessDefinitionQuery().latestVersion().list();

    for (ProcessDefinition activeDefinition : listActiveProcess) {
      try {
        Deployment Deployement = repositoryService.createDeploymentQuery().deploymentId(activeDefinition.getDeploymentId()).list().get(0);
        Long countProcInst = historyService.createHistoricProcessInstanceQuery().processDefinitionId(activeDefinition.getId()).count();
        Long countProcInstEnCours = historyService.createHistoricProcessInstanceQuery().processDefinitionId(activeDefinition.getId()).unfinished().count();
        Long countProcInstClosed = historyService.createHistoricProcessInstanceQuery().processDefinitionId(activeDefinition.getId()).finished().count();

        JSONObject jsonResult = new JSONObject();
        jsonResult.put("id", activeDefinition.getId());
        jsonResult.put("name", activeDefinition.getName());
        jsonResult.put("key", activeDefinition.getKey());
        jsonResult.put("deployement_time", Deployement.getDeploymentTime());
        jsonResult.put("totalProcesses", countProcInst);
        jsonResult.put("nbreEnCours", countProcInstEnCours);
        jsonResult.put("nbreFermer", countProcInstClosed);
        jsonResult.put("version", activeDefinition.getVersion());


        listProcessActive.add(jsonResult);
      } catch (Exception e) {
        log.error("Erreur process");
      }
    }

    return listProcessActive;
  }


  public void findProcessByKey(String key)
  {
      List<ProcessDefinition> listActiveProcess = repositoryService.createProcessDefinitionQuery().latestVersion().list();
  }



  /**
   *
   * @param processInstanceId
   *     A String of {@link String} : process instance id.
   * @return
   *     A respnse of {@link Task} : Return task Object.
   */
  public Task getActifTaskOfProcessInstance(String processInstanceId){
    try {
      return taskService.createTaskQuery().processInstanceId(processInstanceId).active().list().get(0);
    }catch(Exception e){
      return null;
    }
  }

  /**
   *
   * @param processInstanceId
   *     A String of {@link String} : process instance id.
   * @return
   *     A respnse of {@link ProcessInstance} : Return task Object.
   */
  public HistoricProcessInstance getProcessInstance(String processInstanceId){
    try {
      return historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
    }catch(Exception e){
      return null;
    }
  }

  /**
   *
   * @param processInstanceId
   *     A String of {@link String} : process instance id.
   * @return
   *     A respnse of {@link Task} : Return task Object.
   */
  public List<Task> getAllActifTaskOfSubProcessInstance(String processInstanceId){
    List<Task> tasks = new ArrayList<>();
    try {
      List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery().superProcessInstanceId(processInstanceId).list();
      for(ProcessInstance processInstance: processInstances) {
        tasks.addAll(taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).active().list());
      }
      return tasks;
    }catch(Exception e){
      return new ArrayList<>();
    }
  }

  /**
   *
   * @param processInstanceId
   *     A String of {@link String} : process instance id.
   * @return
   *     A respnse of {@link Task} : Return task Object.
   */
  public List<ActivityInstance> getAllActifActivityOfSubProcessInstance(String processInstanceId){
    List<ActivityInstance> tasks = new ArrayList<>();
    try {
      List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery().superProcessInstanceId(processInstanceId).list();
      for(ProcessInstance processInstance: processInstances) {
        tasks.addAll(runtimeService.createActivityInstanceQuery().processInstanceId(processInstance.getProcessInstanceId()).unfinished().list());
      }
      return tasks;
    }catch(Exception e){
      return new ArrayList<>();
    }
  }

  /**
   *
   * @param processInstanceId
   *     A String of {@link String} : process instance id.
   * @return
   *     A respnse of {@link Boolean} : Return active or not.
   */
  public Boolean checkProcessInstance(String processInstanceId){
    try {
      return taskService.createTaskQuery().processInstanceId(processInstanceId).active().list().size() != 0;
    }catch(Exception e){
      return false;
    }
  }

  /**
   *
   * @param processInstanceId
   *     A String of {@link String} : process instance id.
   * @return
   *     A respnse of {@link HistoricTaskInstance} : Return task Object.
   */
  public HistoricTaskInstance getLatestHistoricTaskOfProcessInstance(String processInstanceId){
    try {
      return historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).orderByTaskCreateTime().desc().list().get(0);
    }catch(Exception e){
      return null;
    }
  }

    /**
     *
     * @param processInstanceId
     *     A String of {@link String} : process instance id.
     * @return
     *     A respnse of {@link HistoricTaskInstance} : Return task Object.
     */
    public HistoricActivityInstance getLatestHistoricActivityOfProcessInstance(String processInstanceId){
        try {
            return historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).orderByHistoricActivityInstanceStartTime().desc().list().get(0);
        }catch(Exception e){
            return null;
        }
    }

  /**
   *
   * @param taskId
   *     A String of {@link String} : current activity instance id.
   * @param variable
   *     A String of {@link Map} : list of params to passed to workflow.
   * @return
   *     A respnse of {@link Void} : Return task Object.
   */
  //@Transactional
  public void _finishTask(String taskId, Map<String, Object> variable) throws JSONException, ParseException {

    //persist the decision in workflow variables
    taskService.setVariable(taskId, taskId + " :decision", variable.get("Decision"));

    //persist the commetaskService.setVariable(taskId, data.taskDtoList, task
    taskService.setVariable(taskId, taskId + " :description", variable.get("description"));

    //persist the comment in workflow variables
//    taskService.setVariable(taskId, taskId + " :authentifier", variable.get("authentifier"));

      // ajouter par ameni à modifer par displayname ofUser
    taskService.setVariable(taskId, taskId + " :authentifier", currentUser.getDisplayName());

      //initialize variables to to passing them to workflow
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
    LocalDateTime localDateTime=LocalDateTime.now();
    String formattedString = localDateTime.format(formatter);
    variable.put("currentDate",formattedString );

    // finish th current task
    taskService.complete(taskId, variable);
  }

  /**
   *
   * @param taskId
   *     A String of {@link String} : current activity instance id.
   * @param decision
   *     A String of {@link String} : selected decision.
   * @param description
   *     A String of {@link String} : comment value.
   * @param Params
   *     A String of {@link ArrayList} : list of params to passed to workflow.
   * @return
   *     A respnse of {@link Void} : Return task Object.
   */

  public void finishTask(String taskId, String decision, String description, JSONObject Params) throws JSONException, ParseException {
    // persist the decision in workflow variables
    taskService.setVariable(taskId, taskId + " :decision", decision);

    // persist the comment in workflow variables
    taskService.setVariable(taskId, taskId + " :description", description);

    //initialize variables to to passing them to workflow
    Map<String, Object> variable = new HashMap<String, Object>();
    variable.put("Decision", new String(decision.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));



    // map params to our list of variables

    for (Iterator iterator = Params.keySet().iterator(); iterator.hasNext(); ) {
      String key = (String) iterator.next();
      variable.put(key, Params.get(key));
    }

    // finish th current task
    taskService.complete(taskId, variable);
  }


  /**
   *
   * @param task
   *     A String of {@link Task} : current activity instance id.
   * @return
   *     A respnse of {@link ArrayList} : Return list of authors.
   */
  public List<String> getCandidateGroups(Task task) {
    List<String> Groups = new ArrayList<String>();


    try {
      List<? extends IdentityLinkInfo> identityLinks = taskService.getIdentityLinksForTask(task.getId());

      for (IdentityLinkInfo identityLink : identityLinks) {
        String type = identityLink.getType();
        String groupId = identityLink.getGroupId();
        if (IdentityLinkType.CANDIDATE.equals(type) && groupId != null) {
          Groups.add(groupId);
        }
      }
    }catch(Exception e){
        log.error("condidate group Not Fouwnd");
//      System.out.println("condidate group Not Fouwnd");
    }

    return  Groups;
  }

  /**
   *
   * @param definitionId
   *     A String of {@link String} : current activity instance id.
   * @return
   *     A respnse of {@link ArrayList} : Return list of authors.
   */
  public List<String> getPotentialCandidateGroups(String definitionId) {
    List<String> Groups = new ArrayList<String>();

    BpmnModel bpmnModel=repositoryService.getBpmnModel(definitionId);

    List<Process> listaProcesos=bpmnModel.getProcesses();

    for(Process process:listaProcesos){

      ///get the new properties of restriction added in the modeler app by process
      Groups=process.getCandidateStarterGroups();

    }

    return  Groups;
  }

  /**
   *
   * @param definitionId
   *     A String of {@link String} : current activity instance id.
   * @return
   *     A respnse of {@link ArrayList} : Return list of authors.
   */
  public List<String> getPotentialCandidateUsers(String definitionId) {
    List<String> Users = new ArrayList<String>();

    BpmnModel bpmnModel=repositoryService.getBpmnModel(definitionId);

    List<Process> listaProcesos=bpmnModel.getProcesses();

    for(Process process:listaProcesos){

      ///get the new properties of restriction added in the modeler app by process
      Users=process.getCandidateStarterUsers();

    }

    return  Users;
  }
    /**
     *
     * @param instanceId
     *     A String of {@link String} : current process instance id.
     */
    public void deleteInstance(String instanceId) {

        runtimeService.deleteProcessInstance(instanceId, "deleted by user");
    }

    /**
     *
     * @param instanceId
     *     A String of {@link String} : current process instance id.
     */
    public void deleteInstanceHistoric(String instanceId) {

        historyService.deleteHistoricProcessInstance(instanceId);
    }


  /**
   *
   * @param taskId
   *     A String of {@link Task} : current activity instance id.
   * @return
   *     A respnse of {@link ArrayList} : Return list of authors.
   */
  public List<String> getCandidateGroups(String taskId) {
    List<String> Groups = new ArrayList<String>();


    try {
      List<? extends IdentityLinkInfo> identityLinks = taskService.getIdentityLinksForTask(taskId);

      for (IdentityLinkInfo identityLink : identityLinks) {
        String type = identityLink.getType();
        String groupId = identityLink.getGroupId();
        if (IdentityLinkType.CANDIDATE.equals(type) && groupId != null) {
          Groups.add(groupId);
        }
      }
    }catch(Exception e){
        log.error("condidate group Not Fouwnd");
    }

    return  Groups;
  }

  public List<String> filterCandidateGroups(Task task, String oldTaskName) {
    //recuperate list of authors of new activity
    List<String> listAuthors = new ArrayList<>();
    for(String author: Filter(getCandidateGroups(task))){
      if(author.contains("?")){
        if(author.substring(author.indexOf("?")+1).equals(oldTaskName)){
          listAuthors.add(author.substring(0, author.indexOf("?")));
        }
      }else{
        listAuthors.add(author);
      }
    }
    return listAuthors;
  }

  public List<String> filterCandidateGroups(String taskId, String oldTaskName) {
    //recuperate list of authors of new activity
    List<String> listAuthors = new ArrayList<>();
    for(String author: Filter(getCandidateGroups(taskId))){
      if(author.contains("?")){
        if(author.substring(author.indexOf("?")+1).equals(oldTaskName)){
          listAuthors.add(author.substring(0, author.indexOf("?")));
        }
      }else{
        listAuthors.add(author);
      }
    }
    return listAuthors;
  }




  public List<String> filterCandidateUsers(Task task, String oldTaskName) {
    //recuperate list of readers of new activity
    List<String> listReaders = new ArrayList<>();
    for(String reader: Filter(getCandidateUsers(task))){
      if(reader.contains("?")){
        if(reader.substring(reader.indexOf("?")+1).equals(oldTaskName)){
          listReaders.add(reader.substring(0, reader.indexOf("?")));
        }
      }else{
        listReaders.add(reader);
      }
    }
    return listReaders;
  }

  public List<String> filterCandidateUsers(String taskId, String oldTaskName) {
    //recuperate list of readers of new activity
    List<String> listReaders = new ArrayList<>();
    for(String reader: Filter(getCandidateUsers(taskId))){
      if(reader.contains("?")){
        if(reader.substring(reader.indexOf("?")+1).equals(oldTaskName)){
          listReaders.add(reader.substring(0, reader.indexOf("?")));
        }
      }else{
        listReaders.add(reader);
      }
    }
    return listReaders;
  }



  public void addCandidateGroups(Task task, List<String> authors) {
    if(authors != null) {
      for (String author : authors) {
        taskService.addCandidateGroup(task.getId(), author);
      }
    }
  }

  public void addCandidateGroups(String taskId, List<String> authors) {
    if(authors != null) {
      for (String author : authors) {
        taskService.addCandidateGroup(taskId, author);
      }
    }
  }

  public void addCandidateUsers(Task task, List<String> readers) {
    if(readers != null) {
      for (String reader : readers) {
        taskService.addCandidateUser(task.getId(), reader);
      }
    }
  }


  public void addCandidateUsers(String taskId, List<String> readers) {
    if(readers != null) {
      for (String reader : readers) {
        taskService.addCandidateUser(taskId, reader);
      }
    }
  }

  /**
   *
   * @param task
   *     A String of {@link Task} : current activity instance id.
   * @return
   *     A respnse of {@link ArrayList} : Return list of readers.
   */
  public List<String> getCandidateUsers(Task task) {

    List<String> User = new ArrayList<String>();
    try{
      List<? extends IdentityLinkInfo> identityLinks = task.getIdentityLinks();

      for (IdentityLinkInfo identityLink : identityLinks) {
        String type = identityLink.getType();
        String userId = identityLink.getUserId();
        if (IdentityLinkType.CANDIDATE.equals(type) && userId != null) {
          User.add(userId);
        }
      }
    }catch(Exception e){
        log.error("condidate users Not Fouwnd");
//      System.out.println("condidate users Not Fouwnd");
    }

    return  User;
  }

  /**
   *
   * @param taskId
   *     A String of {@link Task} : current activity instance id.
   * @return
   *     A respnse of {@link ArrayList} : Return list of readers.
   */
  public List<String> getCandidateUsers(String taskId) {

    List<String> User = new ArrayList<String>();
    try{
      List<? extends IdentityLinkInfo> identityLinks = taskService.getIdentityLinksForTask(taskId);

      for (IdentityLinkInfo identityLink : identityLinks) {
        String type = identityLink.getType();
        String userId = identityLink.getUserId();
        if (IdentityLinkType.CANDIDATE.equals(type) && userId != null) {
          User.add(userId);
        }
      }
    }catch(Exception e){
        log.error("condidate users Not Fouwnd");
    }

    return  User;
  }

  /**
   *
   * @param processInstance
   *     A String of {@link String} : current activity instance id.
   * @return
   *     A respnse of {@link ArrayList} : Return list of readers.
   */
  public String getEndActivityName(String processInstance) {

    NativeHistoricActivityInstanceQuery query = historyService.createNativeHistoricActivityInstanceQuery().sql("SELECT * FROM act_hi_actinst WHERE PROC_INST_ID_='" + processInstance + "' AND act_type_ = 'endEvent' ORDER BY START_TIME_ DESC");
    HistoricActivityInstance task = query.singleResult();

    return task.getActivityName();
  }


  /**
   *
   * @param list
   *     A String of {@link ArrayList} : list of candidates.
   * @return
   *     A respnse of {@link ArrayList} : Return list of readers.
   */
  public List<String> Filter(List<String> list) {
    for (int i = 0; i < list.size(); i++) {
      for (int j = 0; j < list.size(); j++) {
        if ((list.get(i).equals(list.get(j))) && (i != j)) {
          list.remove(i);
        }
      }
    }

    return list;
  }

  /**
   *
   * @param taskid
   *     A String of {@link String} : current activity instance id.
   * @return
   *     A respnse of {@link ArrayList} : Return the process definition id.
   */
  public String getprocessInstanceOftask(String taskid) {
    NativeTaskQuery query = taskService.createNativeTaskQuery().sql("SELECT * FROM act_hi_taskinst where ID_='" + taskid + "'");
    Task task = query.list().get(0);
    String processInstanceId = task.getProcessInstanceId();
    ProcessInstance processInstance =
            runtimeService.createProcessInstanceQuery()
                    .processInstanceId(processInstanceId).singleResult();

    return processInstance.getProcessDefinitionId();

  }

  /**
   *
   * @param dob
   *     A String of {@link LocalDateTime} : start date and time of activity .
   * @param now
   *     A String of {@link LocalDateTime} : current date and time .
   * @return
   *     A respnse of {@link String} : Return the due date.
   */
  public String getTime(LocalDateTime dob, LocalDateTime now) {
    LocalDateTime today = LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), dob.getHour(), dob.getMinute(), dob.getSecond());
    Duration duration = Duration.between(today, now);
    long seconds = duration.getSeconds();
    long hours = seconds / Duration.ofHours(1).getSeconds();
    long minutes = ((seconds % Duration.ofHours(1).getSeconds()) / Duration.ofMinutes(1).getSeconds());
    long secs = (seconds % Duration.ofMinutes(1).getSeconds());
    return hours + "H " + minutes + "M " + secs + "S";
  }

  /**
   *
   * @param taskId
   *     A String of {@link String} : current activity instance id.
   * @return
   *     A respnse of {@link ArrayList} : Return the process model.
   */
  public Process getProcess(String taskId) {
    Process process = repositoryService.getBpmnModel(getprocessInstanceOftask(taskId)).getMainProcess();
    return process;
  }

  public Process getProcessModel(String processInstanceId) {
    Process process = repositoryService.getBpmnModel(runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).list().get(0).getProcessDefinitionId()).getMainProcess();
    return process;
  }

  public HistoricActivityInstance getHistoricActivityInstance(String processInstanceId) {
    HistoricActivityInstance historicActivityInstance = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).orderByHistoricActivityInstanceStartTime().desc().list().get(0);
    return historicActivityInstance;
  }

  public Object initalizeProxy(Object obj) {
    if (obj instanceof HibernateProxy) {
      return ((HibernateProxy) obj).getHibernateLazyInitializer().getImplementation();
    }
    return obj;
  }


  //recuperer la liste des paramaitres necessaire pour router a la prochain activité
  public List getListParams(String taskId) {

    // recuperation de l'activité actuelle avec un id prenand en paramaitre
    NativeTaskQuery query = taskService.createNativeTaskQuery().sql("SELECT * FROM act_hi_taskinst WHERE ID_='" + taskId + "' ORDER BY START_TIME_ DESC");
    Task task = query.list().get(0);

    // extracter du nom de l'activité
    String task_name = query.list().get(0).getName();

    // recuperation du l'objet process avec l'Id de l'activité
    Process process = getProcess(taskId);

    // extracter la liste de tous les links de tous less activité from l'objet process
    List<SequenceFlow> sequenceFlows = process.findFlowElementsOfType(SequenceFlow.class);

    // initialisation de la liste des params de notre retour
    List<String> listParam = new ArrayList<>();

    // parcourir la list des liens et tester si il'ont relier a notre activité courante et qu'il s'agit de type exclusive gateway
    for (SequenceFlow sequenceFlow : sequenceFlows) {

      if (sequenceFlow.getConditionExpression() != null) {

        // recuperation de condition de chaque lien
        String condition = sequenceFlow.getConditionExpression();

        // extracter le paramaitre de la condition

        String param = null;
        if (condition.indexOf("==") >= 0) {
          param = condition.substring(condition.indexOf("${") + 2, condition.indexOf("=="));
        }
        if (condition.indexOf("!=") >= 0) {
          param = condition.substring(condition.indexOf("${") + 2, condition.indexOf("!="));
        }
        // puis tester qu'il s'agit d'une nouvelle paramaitre diffrent de Decision
        if (!param.equals("Decision")) {

          // remplir notre liste par ses paramaitres
          listParam.add(param);
        }

      }


    }

    // envoyer notre liste finale comme retour
    return listParam;
  }



  public List<String> calculate_decisions(String[] activitys,String taskId) {

    List<String> decisions = new ArrayList<>();

    Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

    String processDefId = task.getProcessDefinitionId();

    Process process = repositoryService.getBpmnModel(processDefId).getMainProcess();

    List<SequenceFlow> sequenceFlows = process.findFlowElementsOfType(SequenceFlow.class);

    String taskDefinitionKey = task.getTaskDefinitionKey();
    for(String activity : activitys) {
      for (SequenceFlow sequenceFlow : sequenceFlows) {
        if (sequenceFlow.getSourceRef().equals(taskDefinitionKey)) {
          if ((sequenceFlow.getTargetFlowElement().getClass().equals(TaskService.class) || sequenceFlow.getSourceFlowElement().getClass().equals(UserTask.class)) && sequenceFlow.getTargetFlowElement().getClass().equals(EndEvent.class)) {
            if (sequenceFlow.getTargetFlowElement().getName().equals(activity)) {
              if(sequenceFlow.getName().indexOf("sys_") == -1) {
                decisions.add(sequenceFlow.getName());
                taskDefinitionKey = sequenceFlow.getTargetRef();
              }
            }
          }else if(sequenceFlow.getTargetFlowElement().getClass().equals(ExclusiveGateway.class)){
            for (SequenceFlow sequenceFlow2 : sequenceFlows) {
              if (sequenceFlow2.getSourceRef().equals(sequenceFlow.getTargetFlowElement().getId())) {
                if (sequenceFlow2.getTargetFlowElement().getName().equals(activity)) {
                  if(sequenceFlow2.getName().indexOf("sys_") == -1) {
                    decisions.add(sequenceFlow2.getName());
                    taskDefinitionKey = sequenceFlow2.getTargetRef();
                  }
                }
              }
            }
          }
        }
      }
    }
    return decisions;
  }




  //ajouter la list des auteurs et des lecteurs dans la liste des condidates groups et users dans lidentityLinks
  public void setCandidates(String taskid, List<String> authors, List<String> readers) {
    for (String author : authors) {
      taskService.deleteCandidateGroup(taskid, author);
      taskService.addCandidateGroup(taskid, author);
    }
    for (String reader : readers) {
      taskService.deleteCandidateGroup(taskid, reader);
      taskService.addCandidateUser(taskid, reader);
    }

  }


  public List<JSONObject> getListVersionsProcess(String key) {

    List<JSONObject> listProcessActive = new ArrayList<>();

    List<ProcessDefinition> listActiveProcess = repositoryService.createProcessDefinitionQuery().processDefinitionKey(key).list();

    for (ProcessDefinition activeDefinition : listActiveProcess) {
      try {
        Deployment Deployement = repositoryService.createDeploymentQuery().deploymentId(activeDefinition.getDeploymentId()).list().get(0);
        Long countProcInst =  historyService.createHistoricProcessInstanceQuery().processDefinitionId(activeDefinition.getId()).count();
        Long countProcInstEnCours =  historyService.createHistoricProcessInstanceQuery().processDefinitionId(activeDefinition.getId()).unfinished().count();
        Long countProcInstClosed =  historyService.createHistoricProcessInstanceQuery().processDefinitionId(activeDefinition.getId()).finished().count();

        JSONObject jsonResult = new JSONObject();
        jsonResult.put("id", activeDefinition.getId());
        jsonResult.put("name", activeDefinition.getName());
        jsonResult.put("key", activeDefinition.getKey());
        jsonResult.put("deployement_time", Deployement.getDeploymentTime());
        jsonResult.put("totalProcesses", countProcInst);
        jsonResult.put("nbreEnCours", countProcInstEnCours);
        jsonResult.put("nbreFermer", countProcInstClosed);
        jsonResult.put("version", activeDefinition.getVersion());


        listProcessActive.add(jsonResult);
      }catch (Exception e){
          log.error("Erreur process");
//        System.out.println("Erreur process");
      }
    }

    return listProcessActive;
  }

  public List getDerivatorsListParams(String taskId) {

    // recuperation de l'activité actuelle avec un id prenand en paramaitre
    NativeTaskQuery query = taskService.createNativeTaskQuery().sql("SELECT * FROM act_hi_taskinst WHERE ID_='" + taskId + "' ORDER BY START_TIME_ DESC");
    Task task = query.list().get(0);

    // extracter du nom de l'activité
    String task_name = query.list().get(0).getName();

    // recuperation du l'objet process avec l'Id de l'activité
    Process process = getProcess(taskId);

    // extracter la liste de tous les links de tous less activité from l'objet process
    List<SequenceFlow> sequenceFlows = process.findFlowElementsOfType(SequenceFlow.class);

    // initialisation de la liste des params de notre retour
    List<String> listParam = new ArrayList<>();

    // parcourir la list des liens et tester si il'ont relier a notre activité courante et qu'il s'agit de type exclusive gateway
    for (SequenceFlow sequenceFlow : sequenceFlows) {
      if (sequenceFlow.getSourceRef().equals(task.getTaskDefinitionKey())) {

        // recuperation de l'activité courante
        FlowElement currentTask = sequenceFlow.getTargetFlowElement();
        if (currentTask.getClass().equals(ExclusiveGateway.class)) {

          // parcourir la list des liens et tester si il'ont relier au prochain activité et qu'il s'agit de type exclusive gateway
          for (SequenceFlow sequenceFlow2 : sequenceFlows) {
            if (sequenceFlow2.getSourceRef().equals(currentTask.getId())) {

              //boucle les meme traitement et incrémenter a chaque fois les deux variabler currentTask et nextTask on testant qu'il son de type exclusive gateway
              FlowElement nextTask = sequenceFlow2.getTargetFlowElement();
              if (nextTask.getClass().equals(ExclusiveGateway.class)) {
                while (nextTask.getClass().equals(ExclusiveGateway.class)) {

                  // parcourir la list des liens et tester si il'ont relier au prochain activité et qu'il s'agit de type exclusive gateway puis extracter les decision on l'ajoutant a notre resultat
                  for (SequenceFlow sequenceFlow3 : sequenceFlows) {
                    if (sequenceFlow3.getSourceRef().equals(nextTask.getId())) {
                      if (currentTask.getClass().equals(ExclusiveGateway.class)) {
                        if (nextTask.getClass().equals(ExclusiveGateway.class)) {
                          if (sequenceFlow3.getConditionExpression() != null) {

                            // recuperation de condition de chaque lien
                            String condition = sequenceFlow3.getConditionExpression();

                            // extracter le paramaitre de la condition
                            String param = condition.substring(condition.indexOf("${") + 2, condition.indexOf("=="));

                            // puis tester qu'il s'agit d'une nouvelle paramaitre diffrent de Decision
                            if (!param.equals("Decision")) {
                              // remplir notre liste par ses paramaitres
                              listParam.add(param);
                            }

                          }
                        }
                      }
                      currentTask = nextTask;
                      nextTask = sequenceFlow3.getTargetFlowElement();
                    }

                  }
                }
              }
            }
          }
        }
      }
    }
    // envoyer notre liste finale comme retour
    return listParam;
  }


  public Boolean checkProcessToUndeploy(String processDefinitionId) throws IOException {
    List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery().processDefinitionId(processDefinitionId).list();
    return processInstances.size() <= 0;
  }

  public List<JSONObject> getActivitiesByProcesKey(String processId) throws FileNotFoundException {

    List<JSONObject> global = new ArrayList<>();
    ProcessDefinitionQuery listprocess = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processId);


    BpmnModel bpmnModel = repositoryService.getBpmnModel(listprocess.list().get(0).getId());

    Process process = bpmnModel.getProcessById(processId);

    List<UserTask> activities = process.findFlowElementsOfType(UserTask.class);


    for (UserTask activity : activities) {

      JSONObject temp = new JSONObject();
      temp.put("name",activity.getName());
      temp.put("authors",activity.getCandidateGroups());
      temp.put("readers",activity.getCandidateUsers());
      temp.put("key",activity.getId());

      global.add(temp);

    }

    return global;
  }

  public List<JSONObject> getActivityByProcesKey(String processId, String activite) throws FileNotFoundException {

    List<JSONObject> global = new ArrayList<>();
    ProcessDefinitionQuery listprocess = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processId);


    BpmnModel bpmnModel = repositoryService.getBpmnModel(listprocess.list().get(0).getId());

    Process process = bpmnModel.getProcessById(processId);

    List<UserTask> activities = process.findFlowElementsOfType(UserTask.class);


    for (UserTask activity : activities) {
      if(activite.equals(activite)) {
        JSONObject temp = new JSONObject();
        temp.put("name", activity.getName());
        temp.put("authors", activity.getCandidateGroups());
        temp.put("readers", activity.getCandidateUsers());
        temp.put("key", activity.getId());

        global.add(temp);
      }

    }

    return global;
  }

  public ArrayList getAllActivitiesByProcesKey(String processId) throws FileNotFoundException {

    ArrayList global = new ArrayList<>();
    ProcessDefinitionQuery listprocess = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processId).orderByProcessDefinitionVersion().desc();


    BpmnModel bpmnModel = repositoryService.getBpmnModel(listprocess.list().get(0).getId());

    Process process = bpmnModel.getProcessById(processId);

    List<Activity> activities = process.findFlowElementsOfType(Activity.class);

    List<EndEvent> events = process.findFlowElementsOfType(EndEvent.class);


    for (EndEvent event : events) {

      JSONObject jsonObject= new JSONObject();

      jsonObject.put("name", event.getName());

      JSONObject jsonProp= new JSONObject();

      if(event.getExtensionElements().size() != 0) {
        if (event.getExtensionElements().containsKey("properties")) {
          for (ExtensionElement extensionElement : event.getExtensionElements().get("properties")) {
            for (ExtensionElement extensionElementChild : extensionElement.getChildElements().get("property")) {
              String name = extensionElementChild.getAttributes().get("name").get(0).getValue().trim();
              String value = extensionElementChild.getAttributes().get("value").get(0).getValue().trim();
              jsonProp.put(name, value);
            }
          }
        }
      }
      jsonObject.put("props", jsonProp);

      global.add(jsonObject);
    }

    for (Activity activity : activities) {

      JSONObject jsonObject= new JSONObject();

      jsonObject.put("name", activity.getName());

      JSONObject jsonProp= new JSONObject();

      if(activity.getExtensionElements().size() != 0) {
        if (activity.getExtensionElements().containsKey("properties")) {
          for (ExtensionElement extensionElement : activity.getExtensionElements().get("properties")) {
            for (ExtensionElement extensionElementChild : extensionElement.getChildElements().get("property")) {
              String name = extensionElementChild.getAttributes().get("name").get(0).getValue().trim();
              String value = extensionElementChild.getAttributes().get("value").get(0).getValue().trim();
              jsonProp.put(name, value);
            }
          }
        }
      }
      jsonObject.put("props", jsonProp);

      global.add(jsonObject);
    }

    return global;
  }

  public Boolean undeployeProcess(String processDefinitionId, Boolean force) throws IOException {

    try {
      String deployementId =  repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).list().get(0).getDeploymentId();
      repositoryService.deleteDeployment(deployementId, force);
      return true;
    }catch (Exception e){
      return false;
    }

  }

  public List<String> get_activities_by_process(String processDefinitionKey) {
    List<String> activitys = new ArrayList<>();

    List liste = new ArrayList<String>();

    ProcessDefinitionQuery listprocess = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processDefinitionKey);


    BpmnModel bpmnModel = repositoryService.getBpmnModel(listprocess.list().get(0).getId());

    Process process = bpmnModel.getProcessById(processDefinitionKey);

    List<UserTask> tasks = process.findFlowElementsOfType(UserTask.class);

    for(UserTask userTask : tasks){
      activitys.add(userTask.getName());
    }

    return  activitys;
  }



  public List getFirstActivityNameWithoutProcessInstance(String processId) throws FileNotFoundException {

    ProcessDefinitionQuery listprocess = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processId).orderByProcessDefinitionVersion().desc();


    BpmnModel bpmnModel = repositoryService.getBpmnModel(listprocess.list().get(0).getId());

    Process process = bpmnModel.getProcessById(processId);

    List<SequenceFlow> sequenceFlows = process.findFlowElementsOfType(SequenceFlow.class);

    List<String> activity = new ArrayList<>();


    for (SequenceFlow sequenceFlow : sequenceFlows) {
      if (sequenceFlow.getSourceFlowElement().getClass().equals(StartEvent.class)) {
        activity.add(sequenceFlow.getTargetFlowElement().getName());
      }
    }

    return activity;
  }

  public ResponseEntity<byte[]> getProcessInstanceDiagram(String processId) {

    HistoricProcessInstance processInstance1 = historyService.createHistoricProcessInstanceQuery().processInstanceId(processId).list().get(0);

    ProcessDefinition pde = repositoryService.getProcessDefinition(processInstance1.getProcessDefinitionId());

    List<String> listLinkIds = new ArrayList<String>();
    List<HistoricActivityInstance> historicLinkInstanceList = historyService.createHistoricActivityInstanceQuery().activityType("sequenceFlow").processInstanceId(processId).list();
    for (HistoricActivityInstance historicLinkInstance : historicLinkInstanceList) {
      listLinkIds.add(historicLinkInstance.getActivityId());
    }

    Set<String> hash_Set = new HashSet<String>();
    hash_Set.add("endEvent");
    hash_Set.add("userTask");


    List<String> listActivitysIds = new ArrayList<String>();
    List<HistoricActivityInstance> historicActivityInstanceList = historyService.createHistoricActivityInstanceQuery().processInstanceId(processId).activityTypes(hash_Set).orderByHistoricActivityInstanceStartTime().desc().list();
    log.info(historicActivityInstanceList.get(0).getActivityId());
    listActivitysIds.add(historicActivityInstanceList.get(0).getActivityId());


    if (pde != null && pde.hasGraphicalNotation()) {
      BpmnModel bpmnModel = repositoryService.getBpmnModel(pde.getId());
      ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
      InputStream resource = diagramGenerator.generateDiagram(bpmnModel, "png", listActivitysIds, listLinkIds,
              processEngineConfiguration.getActivityFontName(), processEngineConfiguration.getLabelFontName(),
              processEngineConfiguration.getAnnotationFontName(), processEngineConfiguration.getClassLoader(), 1.0, true);

      HttpHeaders responseHeaders = new HttpHeaders();
      responseHeaders.set("Content-Type", "image/png");
      try {
        return new ResponseEntity<>(IOUtils.toByteArray(resource), responseHeaders, HttpStatus.OK);
      } catch (Exception e) {
        throw new FlowableIllegalArgumentException("Error exporting diagram", e);
      }

    } else {
      throw new FlowableIllegalArgumentException("Process instance with id '" + processInstance1.getId() + "' has no graphical notation defined.");
    }
  }

  public List<Object> deployeProcess(@RequestBody MultipartFile BpmnFile) throws IOException, URISyntaxException {
    List<Object> result = new ArrayList<>();

    try {

      Deployment deployment = repositoryService.createDeployment().addInputStream(BpmnFile.getOriginalFilename(),BpmnFile.getInputStream())
              .deploy();
      result.add(true);
      result.add("deployement du processus effectué avec succes");



    } catch (Exception e) {
      result.add(false);
      result.add("echec de deployement du processus");
      result.add(e.getMessage());
    }
    return result;
  }


    public List<Object> deployeProcess(String BpmnFile) throws IOException, URISyntaxException {
        List<Object> result = new ArrayList<>();

        try {
            JSONObject object = (JSONObject) new JSONParser().parse(BpmnFile);
            Deployment deployment = repositoryService.createDeployment().addInputStream(object.get("filename").toString(),
                    new ByteArrayInputStream(Base64.getDecoder().decode(object.get("content").toString())))
                    .deploy();
            result.add(true);
            result.add("deployement du processus effectué avec succes");
        } catch (Exception e) {
            result.add(false);
            result.add("echec de deployement du processus");
            result.add(e.getMessage());
        }
        return result;
    }



    public List<JSONObject> getListProcess() throws FileNotFoundException {


    List<JSONObject> listProcessActive = new ArrayList<>();

    List<ProcessDefinition> listActiveProcess = repositoryService.createProcessDefinitionQuery().latestVersion().list();

    for (ProcessDefinition activeDefinition : listActiveProcess) {

      Deployment Deployement = repositoryService.createDeploymentQuery().deploymentId(activeDefinition.getDeploymentId()).list().get(0);
      Process Mpdel = repositoryService.getBpmnModel(activeDefinition.getId()).getMainProcess();


      List<JSONObject> listActivityProcess = new ArrayList<>();

      JSONObject jsonResult = new JSONObject();
      jsonResult.put("id", activeDefinition.getId());
      jsonResult.put("name", activeDefinition.getName());
      jsonResult.put("key", activeDefinition.getKey());
      jsonResult.put("deployement_time", Deployement.getDeploymentTime());
      jsonResult.put("condidate_starter_groups", Mpdel.getCandidateStarterGroups());
      jsonResult.put("condidate_starter_users", Mpdel.getCandidateStarterUsers());
      jsonResult.put("condidate_starter_users", Mpdel.getCandidateStarterUsers());
      jsonResult.put("version", activeDefinition.getVersion());


      List<ProcessDefinition> listUnactiveProcess = repositoryService.createProcessDefinitionQuery().processDefinitionKey(activeDefinition.getKey()).list();
      for (ProcessDefinition unactiveDefinition : listUnactiveProcess) {
        Deployment unactiveDeployement = repositoryService.createDeploymentQuery().deploymentId(unactiveDefinition.getDeploymentId()).list().get(0);
        Process unactiveMpdel = repositoryService.getBpmnModel(unactiveDefinition.getId()).getMainProcess();

        JSONObject jsonUnactiveProcesses = new JSONObject();
        jsonUnactiveProcesses.put("id", unactiveDefinition.getId());
        jsonUnactiveProcesses.put("name", unactiveDefinition.getName());
        jsonUnactiveProcesses.put("key", unactiveDefinition.getKey());
        jsonUnactiveProcesses.put("deployement_time", unactiveDeployement.getDeploymentTime());
        jsonUnactiveProcesses.put("condidate_starter_groups", unactiveMpdel.getCandidateStarterGroups());
        jsonUnactiveProcesses.put("condidate_starter_users", unactiveMpdel.getCandidateStarterUsers());
        jsonUnactiveProcesses.put("condidate_starter_users", unactiveMpdel.getCandidateStarterUsers());
        jsonUnactiveProcesses.put("version", unactiveDefinition.getVersion());
        listActivityProcess.add(jsonUnactiveProcesses);

      }
      jsonResult.put("activitys", listActivityProcess);

//            List<UserTask> listActivitys = Mpdel.findFlowElementsOfType(UserTask.class);
//            for (UserTask userTask : listActivitys) {
//                JSONObject jsonActivitys = new JSONObject();
//                jsonActivitys.put("id",userTask.getId());
//                jsonActivitys.put("name",userTask.getName());
//                jsonActivitys.put("assigne",userTask.getAssignee());
//                jsonActivitys.put("condidate_groups",userTask.getCandidateGroups());
//                jsonActivitys.put("condidate_users",userTask.getCandidateUsers());
//                listActivityProcess.add(jsonActivitys);
//            }
//            jsonResult.put("activitys" , listActivityProcess);
      listProcessActive.add(jsonResult);
    }
    return listProcessActive;

  }

  //    ***********************************************Adpatation Workflow************************************************

  public List<JSONObject> getListProcessNonDeploye() throws URISyntaxException, IOException {

    URI uriRessource = getClass().getProtectionDomain().getCodeSource().getLocation().toURI();

//    System.out.println("URIRESSOURCE::::::"+uriRessource.getPath());

    new File(uriRessource.getPath() + "processes_undeployed/").mkdirs();
    File directory = new File(uriRessource.getPath() + "processes_undeployed");


    List<JSONObject> listProcessNonDeploye = new ArrayList<>();
    // get all the files from a directory
    File[] fList = directory.listFiles();

    for (File file : fList) {
      if (file.isFile()) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",file.getName());
        jsonObject.put("name", file.getName());
        String content = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

        Document doc = convertStringToXMLDocument(content);
        NodeList nodeList = doc.getElementsByTagName("process");
        Node node  = nodeList.item(0);
        NamedNodeMap Attribute = node.getAttributes();

        jsonObject.put("id", Attribute.getNamedItem("id").getNodeValue());
        jsonObject.put("name", Attribute.getNamedItem("name").getNodeValue());
        jsonObject.put("key", Attribute.getNamedItem("id").getNodeValue());
        BasicFileAttributes attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
        jsonObject.put("createdDate", attr.creationTime().toString());
        jsonObject.put("updatedDate", attr.lastModifiedTime().toString());
        jsonObject.put("path", file.getPath());

        listProcessNonDeploye.add(jsonObject);

      }
    }

    return listProcessNonDeploye;


  }

  public List<JSONObject> getListProcesses() {

    List<JSONObject> listProcessActive = new ArrayList<>();

    List<ProcessDefinition> listActiveProcess = repositoryService.createProcessDefinitionQuery().latestVersion().list();

    for (ProcessDefinition activeDefinition : listActiveProcess) {
      try {

        Deployment Deployement = repositoryService.createDeploymentQuery().deploymentId(activeDefinition.getDeploymentId()).list().get(0);
        Process Mpdel = repositoryService.getBpmnModel(activeDefinition.getId()).getMainProcess();
        Long countProcInst =  historyService.createHistoricProcessInstanceQuery().processDefinitionId(activeDefinition.getId()).count();
        Long countProcInstEnCours =  historyService.createHistoricProcessInstanceQuery().processDefinitionId(activeDefinition.getId()).unfinished().count();
        Long countProcInstClosed =  historyService.createHistoricProcessInstanceQuery().processDefinitionId(activeDefinition.getId()).finished().count();
        List<JSONObject> listActivityProcess = new ArrayList<>();

        JSONObject jsonResult = new JSONObject();
        jsonResult.put("id", activeDefinition.getId());
        jsonResult.put("name", activeDefinition.getName());
        jsonResult.put("key", activeDefinition.getKey());
        jsonResult.put("deployement_time", Deployement.getDeploymentTime());
        jsonResult.put("condidate_starter_groups", Mpdel.getCandidateStarterGroups());
        jsonResult.put("condidate_starter_users", Mpdel.getCandidateStarterUsers());
        jsonResult.put("condidate_starter_users", Mpdel.getCandidateStarterUsers());
        jsonResult.put("totalProcesses", countProcInst);
        jsonResult.put("nbreEnCours", countProcInstEnCours);
        jsonResult.put("nbreFermer", countProcInstClosed);
        jsonResult.put("version", activeDefinition.getVersion());


        List<ProcessDefinition> listUnactiveProcess = repositoryService.createProcessDefinitionQuery().processDefinitionKey(activeDefinition.getKey()).orderByProcessDefinitionVersion().desc().list();
        ProcessDefinition unactiveDefinition = listUnactiveProcess.get(0);
        Deployment unactiveDeployement = repositoryService.createDeploymentQuery().deploymentId(unactiveDefinition.getDeploymentId()).list().get(0);
        Process unactiveMpdel = repositoryService.getBpmnModel(unactiveDefinition.getId()).getMainProcess();

        JSONObject jsonUnactiveProcesses = new JSONObject();
        jsonUnactiveProcesses.put("id", unactiveDefinition.getId());
        jsonUnactiveProcesses.put("name", unactiveDefinition.getName());
        jsonUnactiveProcesses.put("key", unactiveDefinition.getKey());
        jsonUnactiveProcesses.put("deployement_time", unactiveDeployement.getDeploymentTime());
        jsonUnactiveProcesses.put("condidate_starter_groups", unactiveMpdel.getCandidateStarterGroups());
        jsonUnactiveProcesses.put("condidate_starter_users", unactiveMpdel.getCandidateStarterUsers());
        jsonUnactiveProcesses.put("condidate_starter_users", unactiveMpdel.getCandidateStarterUsers());
        jsonUnactiveProcesses.put("version", unactiveDefinition.getVersion());
        listActivityProcess.add(jsonUnactiveProcesses);

        jsonResult.put("activitys", listActivityProcess);

        listProcessActive.add(jsonResult);
      }catch (Exception e){
          log.error("Erreur process");
//        System.out.println("Erreur process");
      }
    }

    return listProcessActive;
  }


  public Boolean saveBpmnModel(String xml, String name) throws IOException {

    try {
      URI uriRessource = getClass().getProtectionDomain().getCodeSource().getLocation().toURI();
      new File(uriRessource.getPath() + "processes_undeployed/").mkdirs();
      File initialFile = new File(uriRessource.getPath() + "processes_undeployed/" + name);
      if (initialFile.exists())
      {
        if(name.contains("-v")){
          initialFile = new File(uriRessource.getPath() + "processes_undeployed/" + name.substring(0, name.indexOf("-v")) + "-v"+  (Integer.parseInt(name.substring(name.indexOf("-v")+2,name.indexOf(".bpmn20.xml")))+1) + ".bpmn20.xml");

        }else if(name.contains("-V")){
          initialFile = new File(uriRessource.getPath() + "processes_undeployed/" + name.substring(0, name.indexOf("-V")) + "-v"+  (Integer.parseInt(name.substring(name.indexOf("-V")+2,name.indexOf(".bpmn20.xml")))+1) + ".bpmn20.xml");
        }else{
          initialFile = new File(uriRessource.getPath() + "processes_undeployed/" + name.substring(0, name.indexOf(".bpmn20.xml")) + "-v1" + ".bpmn20.xml");
        }
      }
      initialFile.createNewFile();
      //Definir le contenu du nouveau fichier bpmn
      FileOutputStream DocOut = new FileOutputStream(initialFile);
      DocOut.write(xml.getBytes());
      DocOut.close();

      return true;

    }

    catch (Exception e)
    {
      return false;
    }


  }


  public List<String> getListParamsV1_1(ProcessInstance processInstance){
    Process process = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId()).getMainProcess();

    // extracter la liste de tous les links de tous less activité from l'objet process
    List<SequenceFlow> sequenceFlows = process.findFlowElementsOfType(SequenceFlow.class);

    // initialisation de la liste des params de notre retour
    List<String> listParam = new ArrayList<>();

    // parcourir la list des liens et tester si il'ont relier a notre activité courante et qu'il s'agit de type exclusive gateway
    for (SequenceFlow sequenceFlow : sequenceFlows) {

      if (sequenceFlow.getConditionExpression() != null) {

        // recuperation de condition de chaque lien
        String condition = sequenceFlow.getConditionExpression();

        // extracter le paramaitre de la condition

        String param = null;
        if(condition.indexOf("==")>=0) {
          param = condition.substring(condition.indexOf("${") + 2, condition.indexOf("=="));
        }else if(condition.indexOf("!=")>=0) {
          param = condition.substring(condition.indexOf("${") + 2, condition.indexOf("!="));
        }else{
          param = condition.substring(condition.indexOf("${") + 2, condition.indexOf("}"));
        }
        // puis tester qu'il s'agit d'une nouvelle paramaitre diffrent de Decision
        if (!param.equals("Decision")) {

          // remplir notre liste par ses paramaitres
          listParam.add(param);
        }

      }


    }

    // envoyer notre liste finale comme retour
    return listParam;

  }


  public Boolean deployeBpmnModel(String xml, String name) throws IOException {
    try {

      //Deployer le processus a partir du fichier bpmn
      Deployment deployment = repositoryService.createDeployment()
              .addInputStream(name, new ByteArrayInputStream(xml.getBytes()))
              .deploy();

      return true;

    }catch (Exception e){
        e.printStackTrace();
      return false;
    }


  }




  public String getBpmnModelXmlByPath(String path) throws IOException {
    return  StreamUtils.copyToString(new FileInputStream(new File(path)), StandardCharsets.UTF_8);

  }

  public String getBpmnModelXml(String processDefinitionId) throws IOException {
    return  StreamUtils.copyToString(repositoryService.getProcessModel(processDefinitionId), StandardCharsets.UTF_8);

  }

    public Boolean copyBpmn(String processDefinitionKey,String name) throws IOException, TransformerException {
        ProcessDefinition processDefinition =repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(processDefinitionKey)
                .latestVersion()
                .singleResult();
        Document document = convertStringToXMLDocument(StreamUtils.copyToString(repositoryService.getProcessModel(processDefinition.getId()), StandardCharsets.UTF_8));
        document.getDocumentElement().getElementsByTagName("process").item(0).getAttributes().getNamedItem("id").setNodeValue(name);
        document.getDocumentElement().getElementsByTagName("process").item(0).getAttributes().getNamedItem("name").setNodeValue(name);
        document.getDocumentElement().getElementsByTagName("participant").item(0).getAttributes().getNamedItem("processRef").setNodeValue(name);
        document.getDocumentElement().getElementsByTagName("participant").item(0).getAttributes().getNamedItem("name").setNodeValue(name);
        DOMSource domSource = new DOMSource(document);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.transform(domSource, result);


      return deployeBpmnModel(result.toString(), name);
    }




//    public List<Object> deployeProcess(@RequestBody MultipartFile BpmnFile) throws IOException, URISyntaxException {
//        List<Object> result= new ArrayList<>();
//
//        try {
//            URI uriRessource = getClass().getProtectionDomain().getCodeSource().getLocation().toURI();
//
//            File initialFile = new File((uriRessource.getPath()).substring(1) + "processes/" + BpmnFile.getOriginalFilename());
//            if (initialFile.exists()) {
//
//                initialFile = new File((uriRessource.getPath()).substring(1) + "processes/" + BpmnFile.getOriginalFilename().substring(0, BpmnFile.getOriginalFilename().indexOf(".bpmn20")) + "(1)" + ".bpmn20.xml");
//
//                //Definir le contenu du nouveau fichier bpmn
//                FileOutputStream DocOut = new FileOutputStream(initialFile);
//                DocOut.write(BpmnFile.getBytes());
//                DocOut.close();
//
//                //Deployer le processus a partir du fichier bpmn
//                Deployment deployment = repositoryService.createDeployment()
//                        .addClasspathResource("processes/" + initialFile.getName())
//                        .deploy();
//
//            }
//
//            else {
//
//                //Creation d'un nouveau fichier bpmn
//                initialFile.createNewFile();
//
//                //Definir le contenu du nouveau fichier bpmn
//                FileOutputStream DocOut = new FileOutputStream(initialFile);
//                DocOut.write(BpmnFile.getBytes());
//                DocOut.close();
//
//                //Deployer le processus a partir du fichier bpmn
//                Deployment deployment = repositoryService.createDeployment()
//                        .addClasspathResource("processes/" + BpmnFile.getOriginalFilename())
//                        .deploy();
//
//
//                result.add(true);
//                result.add("succes de deployement du processus");
//            }
//        }catch (Exception e){
//            result.add(false);
//            result.add("echec de deployement du processus");
//            result.add(e.getMessage());
//        }
//        return result;
//    }

  public Boolean deleteSavedBpmnModel(String path) throws IOException {
    try {
      new File(path).delete();
      return true;
    }catch (Exception e){
      return false;
    }

  }

  private static Document convertStringToXMLDocument(String xmlString)
  {
    //Parser that produces DOM object trees from XML content
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    //API to obtain DOM Document instance
    DocumentBuilder builder = null;
    try
    {
      //Create DocumentBuilder with default configuration
      builder = factory.newDocumentBuilder();

      //Parse the content to Document object
      Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
      return doc;
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }

  public List<Object> getInstanceEditorJson(String processInstance) throws Exception {
    BpmnModel bpmnModel = repositoryService.getBpmnModel(historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstance).list().get(0).getProcessDefinitionId());
    if (bpmnModel.getLocationMap().size() == 0) {
      BpmnAutoLayout bpmnLayout = new BpmnAutoLayout(bpmnModel);
      bpmnLayout.execute();
    }
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();
    ObjectNode modelNode = bpmnJsonConverter.convertToJson(bpmnModel);
    List<Object> result = new ArrayList<>();
    ObjectNode jsonNode = (ObjectNode) this.objectMapper.readTree(modelNode.toString());

    JSONParser parser = new JSONParser();
    JSONObject jsonObjectParsed = (JSONObject) parser.parse(modelNode.toString());


    List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstance).list();
    List<HistoricActivityInstance> historicActInstances = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstance).activityType("sequenceFlow").list();
    List<HistoricActivityInstance> historicstartEventInstances = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstance).activityType("startEvent").list();
    List<HistoricActivityInstance> historicendEventInstances = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstance).activityType("endEvent").list();
    List<HistoricActivityInstance> historicexclusiveGatewayInstances = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstance).activityType("exclusiveGateway").list();


    jsonObjectParsed.put("childShapes", addHistoricToJSON((List<JSONObject>) jsonObjectParsed.get("childShapes"), historicTaskInstances, historicstartEventInstances, historicendEventInstances, historicexclusiveGatewayInstances));
    // extracter la liste de tous les links de tous less activité from l'objet process
    List<SequenceFlow> sequenceFlows = bpmnModel.getMainProcess().findFlowElementsOfType(SequenceFlow.class);

    List<JSONObject> listFlows = new ArrayList<>();
    for(SequenceFlow sequenceFlow: sequenceFlows ){
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("id",sequenceFlow.getId());
      jsonObject.put("name",sequenceFlow.getName());
      List<JSONObject> listProps = new ArrayList<>();

      JSONObject jsonObjectTemp = new JSONObject();
      for(HistoricActivityInstance historicActivityInstance : historicActInstances) {
        if (sequenceFlow.getId().equals(historicActivityInstance.getActivityId())) {
          jsonObjectTemp = new JSONObject();
          jsonObjectTemp.put("name","Date Début");
          String startDate = formatter.format(ZonedDateTime.ofInstant(historicActivityInstance.getStartTime().toInstant(), ZoneId.systemDefault()));
          jsonObjectTemp.put("value",startDate);
          listProps.add(jsonObjectTemp);
        }
      }
      jsonObjectTemp = new JSONObject();
      jsonObjectTemp.put("name","Condition expression");
      jsonObjectTemp.put("value",sequenceFlow.getConditionExpression());
      listProps.add(jsonObjectTemp);
      jsonObject.put("properties",listProps);
      jsonObject.put("sourceRef",sequenceFlow.getSourceRef());
      jsonObject.put("targetRef",sequenceFlow.getTargetRef());
      jsonObject.put("type","sequenceFlow");
      List<JSONObject> jsonArrayTemp = new ArrayList<>();
      List<Integer> list = sequenceFlow.getWaypoints();
      for(int i = 0; i < list.size(); i = i + 2){
        JSONObject jsonTemp = new JSONObject();
        jsonTemp.put("x",list.get(i));
        jsonTemp.put("y",list.get(i+1));
        jsonArrayTemp.add(jsonTemp);
      }
      jsonObject.put("waypoints",jsonArrayTemp);
      for(HistoricActivityInstance historicActivityInstance : historicActInstances) {
        if (sequenceFlow.getId().equals(historicActivityInstance.getActivityId())) {
          jsonObject.put("active", true);
        }
      }
      listFlows.add(jsonObject);
    }
    result.add(jsonObjectParsed);
    result.add(listFlows);
    return result;
  }

  public List<Object> getDefinitionEditorJson(String processDefinition) throws Exception {
    BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinition);
    if (bpmnModel.getLocationMap().size() == 0) {
      BpmnAutoLayout bpmnLayout = new BpmnAutoLayout(bpmnModel);
      bpmnLayout.execute();
    }
    BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();
    ObjectNode modelNode = bpmnJsonConverter.convertToJson(bpmnModel);
    List<Object> result = new ArrayList<>();
    ObjectNode jsonNode = (ObjectNode)this.objectMapper.readTree(modelNode.toString());

    JSONParser parser = new JSONParser();
    JSONObject jsonObjectParsed = (JSONObject) parser.parse(modelNode.toString());

    // extracter la liste de tous les links de tous less activité from l'objet process
    List<SequenceFlow> sequenceFlows = bpmnModel.getMainProcess().findFlowElementsOfType(SequenceFlow.class);

    List<JSONObject> listFlows = new ArrayList<>();
    for(SequenceFlow sequenceFlow: sequenceFlows ){
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("id",sequenceFlow.getId());
      jsonObject.put("name",sequenceFlow.getName());
      List<JSONObject> listProps = new ArrayList<>();
      JSONObject jsonObjectTemp = new JSONObject();
      jsonObjectTemp.put("name","Condition expression");
      jsonObjectTemp.put("value",sequenceFlow.getConditionExpression());
      listProps.add(jsonObjectTemp);
      jsonObject.put("properties",listProps);
      jsonObject.put("sourceRef",sequenceFlow.getSourceRef());
      jsonObject.put("targetRef",sequenceFlow.getTargetRef());
      jsonObject.put("type","sequenceFlow");
      List<JSONObject> jsonArrayTemp = new ArrayList<>();
      List<Integer> list = sequenceFlow.getWaypoints();
      for(int i = 0; i < list.size(); i = i + 2){
        JSONObject jsonTemp = new JSONObject();
        jsonTemp.put("x",list.get(i));
        jsonTemp.put("y",list.get(i+1));
        jsonArrayTemp.add(jsonTemp);
      }
      jsonObject.put("waypoints",jsonArrayTemp);
      listFlows.add(jsonObject);
    }
    result.add(jsonObjectParsed);
    result.add(listFlows);
    return result;
  }

    public List<JSONObject> addHistoricToJSON(List<JSONObject> jsonNode, List<HistoricTaskInstance> historicTaskInstances, List<HistoricActivityInstance> historicstartEventInstances, List<HistoricActivityInstance> historicendEventInstances, List<HistoricActivityInstance> historicexclusiveGatewayInstances){

        List<JSONObject> jsonNodeTemp = new ArrayList<>();
        for (JSONObject objectNode : jsonNode) {
            int index = 0;
            if (((JSONObject) objectNode.get("stencil")).get("id").toString().equals("UserTask")) {
                for (HistoricTaskInstance historicTaskInstance : historicTaskInstances) {

                    if (objectNode.get("resourceId").toString().equals(historicTaskInstance.getTaskDefinitionKey())) {
                        JSONObject nodePropertiesOriginal = ((JSONObject) objectNode.get("properties"));
                        JSONObject nodeProperties = new JSONObject();

                        JSONObject nodePropertieUserTaskAssinment = new JSONObject();
                        JSONObject nodePropertieUserTaskAssinmentAssignment =  new JSONObject();

                        if(!nodePropertieUserTaskAssinmentAssignment.containsKey("Assignee")) {
                            nodePropertieUserTaskAssinmentAssignment.put("Assignee",new ArrayList<>());
                        }
                        List<JSONObject> nodePropertieUserTaskAssinmentAssignmentAssignee = new ArrayList<>();
                        JSONObject Assignee = new JSONObject();
                        Assignee.put("value",historicTaskInstance.getAssignee());
                        nodePropertieUserTaskAssinmentAssignmentAssignee.add(Assignee);
                        nodePropertieUserTaskAssinmentAssignment.put("Assignee", nodePropertieUserTaskAssinmentAssignmentAssignee);

                        if(!nodePropertieUserTaskAssinmentAssignment.containsKey("actionaire")) {
                            nodePropertieUserTaskAssinmentAssignment.put("actionaire",new ArrayList<>());
                        }
                        List<JSONObject> nodePropertieUserTaskAssinmentAssignmentActionaire = new ArrayList<>();
                        JSONObject actionaire = new JSONObject();
                        String actionnaire = "";
                        if(historyService.createHistoricVariableInstanceQuery().variableName(historicTaskInstance.getId() + " :authentifier").list().size() > 0) {
                            HistoricVariableInstance Actionaire = historyService.createHistoricVariableInstanceQuery().variableName(historicTaskInstance.getId() + " :authentifier").list().get(0);
                            actionnaire = Actionaire != null? Actionaire.getValue().toString() : null;
                        }
                        actionaire.put("value", actionnaire);
                        nodePropertieUserTaskAssinmentAssignmentActionaire.add(actionaire);
                        nodePropertieUserTaskAssinmentAssignment.put("actionaire", nodePropertieUserTaskAssinmentAssignmentActionaire);

                        if(!nodePropertieUserTaskAssinmentAssignment.containsKey("startDate")) {
                            nodePropertieUserTaskAssinmentAssignment.put("startDate",new ArrayList<>());
                        }
                        List<JSONObject> nodePropertieUserTaskAssinmentAssignmentStartDate = new ArrayList<>();
                        JSONObject startDate = new JSONObject();
                        String startDateVal = formatter.format(ZonedDateTime.ofInstant(historicTaskInstance.getCreateTime().toInstant(), ZoneId.systemDefault()));
                        startDate.put("value", startDateVal);
                        nodePropertieUserTaskAssinmentAssignmentStartDate.add(startDate);
                        nodePropertieUserTaskAssinmentAssignment.put("startDate", nodePropertieUserTaskAssinmentAssignmentStartDate);

                        if(!nodePropertieUserTaskAssinmentAssignment.containsKey("endDate")) {
                            nodePropertieUserTaskAssinmentAssignment.put("endDate",new ArrayList<>());
                        }
                        List<JSONObject> nodePropertieUserTaskAssinmentAssignmentEndDate = new ArrayList<>();
                        JSONObject endDate = new JSONObject();
                        String endDateVal = null;
                        if(historicTaskInstance.getEndTime() != null) {
                            endDateVal = formatter.format(ZonedDateTime.ofInstant(historicTaskInstance.getEndTime().toInstant(), ZoneId.systemDefault()));
                        }
                        endDate.put("value", endDateVal);
                        nodePropertieUserTaskAssinmentAssignmentEndDate.add(endDate);
                        nodePropertieUserTaskAssinmentAssignment.put("endDate", nodePropertieUserTaskAssinmentAssignmentEndDate);


                        if(!nodePropertieUserTaskAssinmentAssignment.containsKey("candidateGroups")) {
                            nodePropertieUserTaskAssinmentAssignment.put("candidateGroups",new ArrayList<>());
                        }
                        List<JSONObject> nodePropertieUserTaskAssinmentAssignmentCandidateGroups = new ArrayList<>();
                        JSONObject candidateGroup = new JSONObject();
                        try {
                            List<HistoricIdentityLink> identitys = historyService.getHistoricIdentityLinksForTask(historicTaskInstance.getId());
                            if (identitys != null)
                                for (HistoricIdentityLink grp : identitys) {
                                    if (grp.getGroupId() != null && !grp.getGroupId().isEmpty()) {
                                        candidateGroup.put("value", grp.getGroupId());
                                        nodePropertieUserTaskAssinmentAssignmentCandidateGroups.add(candidateGroup);
                                    }
                                }
                        }catch (Exception e){
                            log.error("condidate user Not Fouwnd");
//              System.out.println("condidate user Not Fouwnd");

                        }
                        nodePropertieUserTaskAssinmentAssignment.put("candidateGroups", nodePropertieUserTaskAssinmentAssignmentCandidateGroups);

                        if(!nodePropertieUserTaskAssinmentAssignment.containsKey("candidateUsers")) {
                            nodePropertieUserTaskAssinmentAssignment.put("candidateUsers",new ArrayList<>());
                        }
                        List<JSONObject> nodePropertieUserTaskAssinmentAssignmentCandidateUsers = new ArrayList<>();
                        JSONObject candidateUser = new JSONObject();
                        try{
                            List<HistoricIdentityLink> identitys = historyService.getHistoricIdentityLinksForTask(historicTaskInstance.getId());
                            if(identitys != null)
                                for(HistoricIdentityLink usr : identitys){
                                    if(usr.getUserId() != null && !usr.getUserId().isEmpty()){
                                        if(!candidateGroup.containsValue(usr.getUserId())) {
                                            candidateUser.put("value", usr.getUserId());
                                            nodePropertieUserTaskAssinmentAssignmentCandidateUsers.add(candidateUser);
                                        }
                                    }
                                }
                        }catch (Exception e){
                            log.error("condidate user Not Fouwnd");
//              System.out.println("condidate user Not Fouwnd");
                        }
                        nodePropertieUserTaskAssinmentAssignment.put("candidateUsers", nodePropertieUserTaskAssinmentAssignmentCandidateUsers);
                        nodePropertieUserTaskAssinment.put("assignment", nodePropertieUserTaskAssinmentAssignment);
                        nodeProperties.put("usertaskassignment", nodePropertieUserTaskAssinment);
                        if (!nodePropertiesOriginal.containsKey("current") || (nodePropertiesOriginal.containsKey("current") && !((Boolean) nodePropertiesOriginal.get("current"))))
                            if (historicTaskInstance.getEndTime() == null) {
                                nodePropertiesOriginal.put("current", true);
                            } else {
                                nodePropertiesOriginal.put("current", false);
                            }
                        nodePropertiesOriginal.put("active",true);
                        objectNode.put("properties", nodePropertiesOriginal);
                        objectNode.put("properties"+index, nodeProperties);
                        index++;
                    }

                }
                objectNode.put("childShapes", addHistoricToJSON((List<JSONObject>)objectNode.get("childShapes"), historicTaskInstances, historicstartEventInstances, historicendEventInstances, historicexclusiveGatewayInstances));
                jsonNodeTemp.add(objectNode);
            }else if(((JSONObject)objectNode.get("stencil")).get("id").toString().equals("EndNoneEvent")){
                for(HistoricActivityInstance historicActivityInstance : historicendEventInstances){
                    if(((JSONObject)objectNode.get("properties")).get("overrideid").toString().equals(historicActivityInstance.getActivityId())){
                        JSONObject nodeProperties = ((JSONObject)objectNode.get("properties"));
                        nodeProperties.put("active",true);
                        objectNode.put("properties", nodeProperties);
                    }
                }
                objectNode.put("childShapes", addHistoricToJSON((List<JSONObject>)objectNode.get("childShapes"), historicTaskInstances, historicstartEventInstances, historicendEventInstances, historicexclusiveGatewayInstances));
                jsonNodeTemp.add(objectNode);

            }else if(((JSONObject)objectNode.get("stencil")).get("id").toString().equals("ExclusiveGateway")){
                for(HistoricActivityInstance historicActivityInstance : historicexclusiveGatewayInstances){
                    if(((JSONObject)objectNode.get("properties")).get("overrideid").toString().equals(historicActivityInstance.getActivityId())){
                        JSONObject nodeProperties = ((JSONObject)objectNode.get("properties"));
                        nodeProperties.put("active",true);
                        objectNode.put("properties", nodeProperties);
                    }
                }
                objectNode.put("childShapes", addHistoricToJSON((List<JSONObject>)objectNode.get("childShapes"), historicTaskInstances, historicstartEventInstances, historicendEventInstances, historicexclusiveGatewayInstances));
                jsonNodeTemp.add(objectNode);

            }else if(((JSONObject)objectNode.get("stencil")).get("id").toString().equals("StartNoneEvent")){
                for(HistoricActivityInstance historicActivityInstance : historicstartEventInstances){
                    if(((JSONObject)objectNode.get("properties")).get("overrideid").toString().equals(historicActivityInstance.getActivityId())){
                        JSONObject nodeProperties = ((JSONObject)objectNode.get("properties"));
                        nodeProperties.put("active",true);
                        objectNode.put("properties", nodeProperties);
                    }
                }
                objectNode.put("childShapes", addHistoricToJSON((List<JSONObject>)objectNode.get("childShapes"), historicTaskInstances, historicstartEventInstances, historicendEventInstances, historicexclusiveGatewayInstances));
                jsonNodeTemp.add(objectNode);
            }else{
                objectNode.put("childShapes", addHistoricToJSON((List<JSONObject>)objectNode.get("childShapes"), historicTaskInstances, historicstartEventInstances, historicendEventInstances, historicexclusiveGatewayInstances));
                jsonNodeTemp.add(objectNode);
            }
        }
        return jsonNodeTemp;
    }

  public boolean verifyOperation(String operation){

    ScriptEngineManager manager = new ScriptEngineManager();
    ScriptEngine se = manager.getEngineByName("JavaScript");
    try {
      Object result = se.eval(operation);
      return (boolean) result;
    } catch (ScriptException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return false;
    }


  }


  public JSONObject validateMigration(String sourceProcessDefinition, String targetProcessDefinition) {
    List<JSONObject> result = new ArrayList<>();
    Boolean isValidMigration = true;
    int countValid = 0;
    int countNotValid = 0;

    for(ProcessInstance processInstance : runtimeService.createProcessInstanceQuery().processDefinitionId(sourceProcessDefinition).list()){

      ProcessInstanceMigrationValidationResult validationResult = runtimeService.createProcessInstanceMigrationBuilder()
              .migrateToProcessDefinition(targetProcessDefinition)
              .validateMigration(processInstance.getId());

      if(!validationResult.isMigrationValid()){
        isValidMigration = false;
        countNotValid++;
      }else{
        countValid++;
      }
      JSONObject globalObject = new JSONObject();
      globalObject.put("sourceProcessInstanceId", processInstance.getId());
      globalObject.put("targetProcessDefinitionId",targetProcessDefinition);
      globalObject.put("isValidMigration",validationResult.isMigrationValid());
      globalObject.put("message",validationResult.getValidationMessages());

      result.add(globalObject);
    }
    JSONObject globalObject = new JSONObject();
    globalObject.put("count_valid_instances", countValid);
    globalObject.put("count_invalid_instances", countNotValid);
    globalObject.put("result_validation", isValidMigration);
    globalObject.put("details", result);


    return globalObject;
  }


  public Boolean migrateProcessInstances(String sourceProcessDefinition, String targetProcessDefinition) {

    try {
      List<ProcessInstanceMigrationValidationResult> result = new ArrayList<>();
      for (ProcessInstance processInstance : runtimeService.createProcessInstanceQuery().processDefinitionId(sourceProcessDefinition).list()) {
        runtimeService.createProcessInstanceMigrationBuilder().migrateToProcessDefinition(targetProcessDefinition).migrate(processInstance.getId());
      }
      return true;
    }catch(Exception e){
      return false;
    }
  }


  public void delegateTask(Map<String, Object> variables) throws Exception {

      // extract process instance id
      String processInstanceId = ((Map) ((Map) variables.get("data")).get("workflow")).get("wfProcessID").toString();
      Task task = getActifTaskOfProcessInstance(processInstanceId);
      HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();

      taskService.delegateTask(task.getId(), variables.get("actorSID").toString());

      //persist the comment in workflow variables
      taskService.setVariable(task.getId(), task.getId() + " :authentifier", variables.get("authentifier"));

      JSONObject object = new JSONObject();

      // recuperate the object from flowable
      object.putAll((Map) taskService.getVariable(task.getId(), "data"));

    // recuperate list of authors
    List<String> listAuthors = _getCandidateGroups(task.getId());

    // recuperate list of readers
    List<String> listReaders = _getCandidateUsers(task.getId());

      // add authors to object
      object.put("authors", listAuthors);

      // add readers to object
      object.put("readers", listReaders);

      // Perciste BpmJob data case instance unfinished

      BpmJob bpmJob = bpmJobService._persisteBpmJob(object, task.getName(), task.getAssignee(), false, task.getProcessInstanceId(), Long.valueOf((Integer) object.get("id")), Long.valueOf((Integer) object.get("classId")));

      // parse bpmJob to JSONObject
      org.json.JSONObject bpmJobJSONObject = bpmJob.toJSON();

      // add authorization to bpmJobJSONObject
      bpmJobJSONObject.put(SecurityConstants.HEADER_STRING_AUTHORIZATION, currentUser.getToken());
      System.out.println("processInstance.getProcessDefinitionName()   Routage Workflow  " + processInstance.getProcessDefinitionName());
      bpmJobJSONObject.put("processName", processInstance.getProcessDefinitionName());
      // create new BpmUpdate Event
      kernelInterface.createEvent("BPMUpdate", bpmJobJSONObject, bpmJob.getObjectID(), object.get("className").toString(), null, null, null, null);


  }

  public void resolveTask(String processInstanceId, Map<String, Object> variables){
    Task task = getActifTaskOfProcessInstance(processInstanceId);
    taskService.resolveTask(task.getId(),variables);

  }



  public ActivityInstance getCurrentActivity(DelegateExecution execution){
    return runtimeService.createActivityInstanceQuery().unfinished().activityId(execution.getCurrentActivityId()).list().get(0);

  }

  public String getEndEventActivityName(DelegateExecution execution){
    return ((ExecutionEntityImpl) execution).getActivityName();

  }

  public void synchronizehistory(DelegateExecution execution){
//    System.out.println(execution.getVariable("data"));
    managementService.executeCommand(new Command<Void>() {

      @SneakyThrows
      @Override
      public Void execute(CommandContext commandContext) {


        try {
          HistoricVariableInstanceEntityManager historicVariableInstanceEntityManager = CommandContextUtil.getHistoricVariableInstanceEntityManager(commandContext);
          HistoricVariableInstanceEntity historicVariableInstanceByVariableInstanceId = historicVariableInstanceEntityManager.findHistoricVariableInstanceByVariableInstanceId(historyService.createHistoricVariableInstanceQuery().processInstanceId(execution.getProcessInstanceId()).variableName("data").list().get(0).getId());
          ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
          ObjectOutputStream out = new ObjectOutputStream(byteOut);
          out.writeObject(execution.getVariable("data"));
          historicVariableInstanceByVariableInstanceId.setBytes(byteOut.toByteArray());
          historicVariableInstanceEntityManager.update(historicVariableInstanceByVariableInstanceId, false);

        } catch (IOException e) {
          e.printStackTrace();
        }


        return null;
      }
    });
  }


  /**
   *
   * @param processInstanceId
   *     A String of {@link String} : process instance id.
   * @return
   *     A respnse of {@link ArrayList} : Return Logs instance list.
   */
  public List<EventLogEntry> getAllInstanceLogs(String processInstanceId) {

    return managementService.getEventLogEntriesByProcessInstanceId(processInstanceId);
  }

  /**
   *
   * @param processInstanceId
   *     A String of {@link String} : process instance id.
   * @return
   *     A respnse of {@link ArrayList} : Return Logs instance list.
   */
  public List<HistoricTaskInstance> findAllInstanceTasks(String processInstanceId){

    return historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).list();
  }

  /**
   *
   * @param processInstanceId
   *     A String of {@link String} : process instance id.
   * @return
   *     A respnse of {@link ArrayList} : Return Logs instance list.
   */
  public List<HistoricVariableInstance> findAllInstanceVariables(String processInstanceId){

    return historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).list();
  }

  public void deleteVariables(String variableId){
    managementService.executeCommand(new Command<Void>() {

      @Override
      public Void execute(CommandContext commandContext) {

        VariableInstanceEntityManager variableInstanceEntityManager = CommandContextUtil.getVariableInstanceEntityManager(commandContext);
        variableInstanceEntityManager.delete(variableId);
        HistoricVariableInstanceEntityManager historicVariableInstanceEntityManager =  CommandContextUtil.getHistoricVariableInstanceEntityManager(commandContext);
        historicVariableInstanceEntityManager.delete(variableId);
        return null;
      }
    });
  }

  public void createVariables(String variableName, Object value){
    managementService.executeCommand(new Command<Void>() {

      @Override
      public Void execute(CommandContext commandContext) {

        VariableInstanceEntityManager variableInstanceEntityManager = CommandContextUtil.getVariableInstanceEntityManager(commandContext);
        VariableTypes variableTypes = Context.getProcessEngineConfiguration().getVariableTypes();
        variableTypes.findVariableType(value);
        variableInstanceEntityManager.create(variableName, variableTypes.findVariableType(value), value);
        return null;

      }
    });
  }

  public List getGatewayDecisionByKey(String processKey) throws FileNotFoundException {

    List liste = new ArrayList<String>();

    ProcessDefinitionQuery listprocess = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processKey).latestVersion();


    BpmnModel bpmnModel = repositoryService.getBpmnModel(listprocess.list().get(0).getId());

    Process process = bpmnModel.getProcessById(processKey);

    List<SequenceFlow> sequenceFlows = process.findFlowElementsOfType(SequenceFlow.class);

    List<String> decisionlist = new ArrayList<>();


    for (SequenceFlow sequenceFlow : sequenceFlows) {
      for (SequenceFlow sequenceFlow1 : sequenceFlows) {
        if (sequenceFlow1.getTargetFlowElement().getId().equals(sequenceFlow.getSourceFlowElement().getId()) && sequenceFlow1.getSourceFlowElement().getClass().equals(StartEvent.class)) {
          if (sequenceFlow.getTargetFlowElement().getClass().equals(UserTask.class) || sequenceFlow.getTargetFlowElement().getClass().equals(EndEvent.class) || sequenceFlow.getTargetFlowElement().getClass().equals(ServiceTask.class)) {
            if (sequenceFlow.getName().indexOf("sys_") == -1) {
              decisionlist.add(sequenceFlow.getName());
            }
          } else {
            for (SequenceFlow sequenceFlow2 : sequenceFlows) {
              if (sequenceFlow2.getSourceRef().equals(sequenceFlow.getTargetFlowElement().getId())) {
                if (sequenceFlow2.getName().indexOf("sys_") == -1) {
                  decisionlist.add(sequenceFlow2.getName());
                }
              }
            }
          }
        }

      }
    }

    return decisionlist;
  }



  public String getFirstActivityNameByKey(String processKey) throws FileNotFoundException {

    ProcessDefinitionQuery listprocess = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processKey).orderByProcessDefinitionVersion().desc();


    BpmnModel bpmnModel = repositoryService.getBpmnModel(listprocess.list().get(0).getId());

    Process process = bpmnModel.getProcessById(processKey);

    List<SequenceFlow> sequenceFlows = process.findFlowElementsOfType(SequenceFlow.class);

    List<String> activity = new ArrayList<>();


    for (SequenceFlow sequenceFlow : sequenceFlows) {
      if (sequenceFlow.getSourceFlowElement().getClass().equals(StartEvent.class)) {
        activity.add(sequenceFlow.getTargetFlowElement().getName());
      }
    }

    return activity.get(0);
  }


  public JSONObject extractProcessInstancesIds(Date dateDeb, Date dateOut, String sid, String processKey) throws FileNotFoundException, ParseException {


    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-dd-MM");

    NativeHistoricProcessInstanceQuery initiated = historyService.createNativeHistoricProcessInstanceQuery();

    JSONObject jsonObject = new JSONObject();

    if(processKey != null) {

      String query = "";

      if (dateDeb != null)
        query += "AND date(a.end_time_) = '" + formatter.format(dateDeb) + "' ";
//
//      if (dateOut != null)
//        query += "AND a.end_time_ <= '" + formatter.format(dateOut) + "' ";

      if (sid != null)
        if (!sid.isEmpty() && !sid.isBlank())
          query += "AND '" + sid + "' in (SELECT b.group_id_ FROM act_hi_identitylink b WHERE b.task_id_ IN (SELECT c.id_ FROM act_hi_taskinst c WHERE c.proc_inst_id_ = a.id_ ) )";

      jsonObject.put("process", processKey);
      jsonObject.put("terminé", initiated.sql("SELECT a.id_ FROM act_hi_procinst a WHERE a.end_time_ is not null AND a.proc_def_id_ IN (SELECT f.id_ FROM act_re_procdef f WHERE key_ = '" + processKey + "') " + query).list().stream().map(e -> e.getId()).collect(Collectors.toSet()));

    }


    return jsonObject;
  }

}


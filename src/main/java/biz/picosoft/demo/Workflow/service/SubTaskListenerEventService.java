package biz.picosoft.demo.Workflow.service;

import com.google.gson.Gson;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;


@Service
@Named("subTaskListenerEventService")
public class SubTaskListenerEventService {

  @Autowired
  private WorkflowService workflowService;



  public void execute(DelegateExecution execution) throws Exception {

    JSONObject object = new JSONObject();

    object.putAll((Map)new JSONParser().parse(new Gson().toJson(((ExecutionEntityImpl) execution).getRootProcessInstance().getVariable("data"))) );
    JSONObject subobject = new JSONObject();
    subobject.putAll((Map) new JSONParser().parse(new Gson().toJson(execution.getVariable("data"))));

    if(subobject.get("mmTaskDTOList") != null)
    object.put("mmTaskDTOList",subobject.get("mmTaskDTOList"));
    else
      object.put("mmTaskDTOList",new ArrayList<>());

    ((ExecutionEntityImpl) execution).getRootProcessInstance().setVariable("data",object);
    ((ExecutionEntityImpl) execution).getRootProcessInstance().setVariable("DecisionOut","Out");


  }




}


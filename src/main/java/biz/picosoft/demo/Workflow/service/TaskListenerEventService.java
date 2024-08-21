package biz.picosoft.demo.Workflow.service;

import org.flowable.engine.delegate.DelegateExecution;
import org.mapstruct.Named;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Named("taskListenerEventService")
public class TaskListenerEventService {
  private final org.slf4j.Logger log = LoggerFactory.getLogger(TaskListenerEventService.class);

  @Autowired
  private WorkflowService workflowService;



  public void execute(DelegateExecution execution, String events) throws Exception {
    if(execution.getVariable("objectID") != null) {

//      workflowService.processObject((String) execution.getVariable("className"), (Long) execution.getVariable("objectID"), (Long) execution.getVariable("classID"), execution.getProcessInstanceId(), events, ((ExecutionEntity) execution), Integer.valueOf(0), (String) execution.getVariable("url"), false, null);

    }else{
      log.error("=> NO EVENT TO SAVE");
    }

  }




}


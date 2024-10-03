package biz.picosoft.demo.Workflow.service;


import biz.picosoft.demo.client.kernel.intercomm.KernelInterface;
import com.google.gson.Gson;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.impl.persistence.entity.ExecutionEntity;
import org.flowable.identitylink.service.impl.persistence.entity.IdentityLinkEntity;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.mapstruct.Named;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import biz.picosoft.demo.client.kernel.model.global.CurrentUser;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@Named("createEvent")
public class createEvent {
  private final org.slf4j.Logger log = LoggerFactory.getLogger(createEvent.class);

  @Autowired
  WorkflowService workflowService;

  @Autowired
  private CurrentUser currentUser;

  @Autowired
  private KernelInterface kernelInterface;

  public void execute(DelegateExecution execution, String event) throws Exception {
    if(execution.getVariable("objectID") != null) {



      // extract the object identifier
      Long objectId = Long.valueOf((Integer) execution.getVariable("objectID"));

      // extract the object class identifier
      Long classId = Long.valueOf((Integer) execution.getVariable("classID"));

      // extract the object class name
      String className = (String)  execution.getVariable("className");

      // extract the object simple class name
      String simpleClassName = (String)  execution.getVariable("simpleClassName");

      // extract the object url
      String url = (String)  execution.getVariable("url");

      if(event.equals("ReportRFMODM")) {
        JSONObject data = new JSONObject( new Gson().toJson(execution.getVariable("customdata")));

        kernelInterface.createEvent(event, data, objectId, className, null, null, null, null);
      }else{

        // initialize list authors and readers mails
        List<String> listReaders = new ArrayList<>();
        List<String> listAuthors = new ArrayList<>();
        List<String> listDestNotif = new ArrayList<>();

        // extract list authors and readers mails
        ExecutionEntity executionEntity = ((ExecutionEntity) execution);
        if(executionEntity.getTasks().size() > 0) {
          for (IdentityLinkEntity identityLink : executionEntity.getTasks().get(0).getIdentityLinks()) {
            if (identityLink.getGroupId() != null) {
              listAuthors.addAll(kernelInterface.findEmail(identityLink.getGroupId()));
              listDestNotif.add( identityLink.getGroupId());
            } else if (identityLink.getUserId() != null) {
              listReaders.addAll(kernelInterface.findEmail(identityLink.getUserId()));
            }
          }
        }
        String listAuthorsMails = listAuthors.toString().substring(1, listAuthors.toString().length() - 1);
        String listReadersMails = listReaders.toString().substring(1, listReaders.toString().length() - 1);

        // initialize and prepare event Object
        JSONObject objectEvt = new JSONObject( new Gson().toJson(execution.getVariable("data")));

        objectEvt.put("authors", listAuthorsMails);
        objectEvt.put("readers", listReadersMails);
        objectEvt.put("destNotif", Filter(listDestNotif));
        objectEvt.put("className", className);
        objectEvt.put("url", url);
        kernelInterface.createEvent(event, objectEvt, objectId, className, null, null, null, null);
      }
  
//      System.out.println("// Event Saved Successfuly");
    }else{
      log.error("=> NO EVENT TO SAVE");
    }

  }


  //add event in process
  public void _execute(DelegateExecution execution, String eventTypeAlias) throws Exception {
    if (execution.getVariable("data") != null) {


      // initialize list authors mails
      List<String> listReaders = new ArrayList<>();

      // initialize list authors mails
      List<String> listAuthors = new ArrayList<>();

      // extract execution entity from execution
        ExecutionEntity executionEntity = ((ExecutionEntity) execution);

        // initialize temp simple.JSONObject
        org.json.simple.JSONObject object = new org.json.simple.JSONObject();

        // extract object from variables
       object.putAll((Map) new JSONParser().parse(new Gson().toJson(execution.getVariable("data"))));

      // extract Object from flowabled
      JSONObject objectEvt = new JSONObject(object);

      // invoke add event to create event
      kernelInterface.addEvent(
              eventTypeAlias,
              object.toJSONString(),
              Long.valueOf(objectEvt.get("id").toString()),
              objectEvt.get("className").toString(), null, null, null);



//      System.out.println("// Event Saved Successfuly");
    }else{
      log.error("=> NO EVENT TO SAVE");
//      System.out.println("=> NO EVENT TO SAVE");
    }

  }

  public void callWebService(String url, String method, Object requestBody) {
    RestTemplate restTemplate = new RestTemplate();

    // Prepare the request headers
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);


    headers.setBearerAuth(currentUser.getToken());


    headers.add("Application", "PremiereInstance");

    // Create the request entity with the request body
    HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody, headers);

    // Make the HTTP request based on the specified method
    ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(url, HttpMethod.resolve(method), requestEntity, new ParameterizedTypeReference<Map<String, Object>>() {});

  }

  public List<String> Filter(List<String> list) {
    for (int i = 0; i < list.size(); i++) {
      for (int j = 0; j < list.size(); j++) {
        if(list.get(i) != null) {
          if ((list.get(i).equals(list.get(j))) && (i != j)) {
            list.remove(i);
          }
        }else{
          list.remove(i);
        }
      }
    }

    return list;
  }

}

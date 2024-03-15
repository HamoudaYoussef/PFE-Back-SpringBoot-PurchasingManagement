package biz.picosoft.demo.client.kernel.intercomm;

import biz.picosoft.demo.client.config.FeignConfig;
import biz.picosoft.demo.client.kernel.model.acl.AclClass;
import biz.picosoft.demo.client.kernel.model.events.Event;
import biz.picosoft.demo.client.kernel.model.global.*;
import biz.picosoft.demo.client.kernel.model.objects.*;
import biz.picosoft.demo.client.kernel.model.pm.ActivityType;
import biz.picosoft.demo.client.kernel.model.pm.Role;
import biz.picosoft.demo.client.kernel.model.pm.UserActivity;
import org.json.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@FeignClient(value = "${feign.kernel.name}", url = "${feign.kernel.url}", configuration = FeignConfig.class)
public interface KernelInterface {

    @RequestMapping(method = RequestMethod.PUT, value = "/adjustAttachmentSecurity")
    void adjustAttachmentSecurity(@RequestParam("classId")Long classId,
                                  @RequestParam("objectId") Long objectId,
                                  @RequestParam("objectDatasecuriteLevel")Integer objectDatasecuriteLevel);

    @RequestMapping(method = RequestMethod.GET, value = "/attachements/count")
    Long countAttachements(@RequestParam(value = "objectId") Long objectId,
                           @RequestParam(value = "classId") Long classId);
    @RequestMapping(method = RequestMethod.GET, value = "/applySecurity")
    Boolean applySecurity(
            @RequestParam("clazz") String clazz, @RequestParam("id") Long id,
            @RequestParam("authors") List<String> authors,
            @RequestParam("readers") List<String> readers,
            @RequestParam("tempReaders") List<String> tempReaders,
            @RequestParam(value = "clazzParent", required = false) String clazzParent,
            @RequestParam(value = "idParent", required = false) Long idParent,
            @RequestParam("isCreated") Boolean isCreated, @RequestParam("isCumulative") Boolean isCumulative);

    @RequestMapping(method = RequestMethod.GET, value = "/objectState")
    Optional<ObjectState> getObjectState(@RequestParam String businessClass, @RequestParam Long objectId);
    @RequestMapping(method = RequestMethod.POST, value = "/save-objectState")
    ObjectState saveObjectState(@RequestBody ObjectState objectState);
    @RequestMapping(method = RequestMethod.GET, value = "/checkSecurity")
    String checkSecurity(@RequestParam("simpleName") String simpleName, @RequestParam("id") Long id, @RequestParam("sids") List<String> sids);
    @RequestMapping(method = RequestMethod.GET, value = "/getInput")
    String getInput(@RequestParam("processInstanceId")String processInstanceId, @RequestParam("name")String name,@RequestParam("type")String type);

    @GetMapping(value = "/rulesByName")
    RulesDTO rulesByName(@RequestParam String ruleName);
    @RequestMapping(method = RequestMethod.POST, value = "/workflow/_nextTask")
    org.json.simple.JSONObject _nextTask(@RequestBody Map<String, Object> variables);

    @RequestMapping(method = RequestMethod.GET, value = "/roles_name")
    List<Role> findAllByProfiles(@RequestParam String name);
    @RequestMapping(method = RequestMethod.POST, value = "/objects")
    ObjectsDTO getobjectsDto(@RequestBody ObjectDTO objectDTO);

    @RequestMapping(method = RequestMethod.GET, value = "/getToken")
    String getToken(@RequestParam("apiKey") String apiKey);

    @RequestMapping(method = RequestMethod.POST, value = "/workflow/startProcessInstance")
    String startProcessInstance(@RequestBody Map<String, Object> variables);

    @RequestMapping(method = RequestMethod.GET, value = "/currentUser")
    CurrentUser getCurrentUser();

    @PostMapping(value = "/Authenticate")
    String Authorize(@RequestBody AuthUser a);

    @RequestMapping(method = RequestMethod.POST, value = "/events")
    Event addEvent(
            @RequestParam String eventName, @RequestBody(required = false) String data,
            @RequestParam Long objectID, @RequestParam String classname, @RequestParam(required = false) String[] filePath,
            @RequestParam(required = false) MultipartFile[] multipartFiles,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime remindDate);

    @RequestMapping(method = RequestMethod.GET, value = "/variables-by-name")
    String getValeur(@RequestParam String variableName);

    @RequestMapping(method = RequestMethod.POST, value = "/user-activitie")
    UserActivity addUserActivity(@RequestParam ActivityType activityType, @RequestParam Long objectId, @RequestParam String classe,
                                 @RequestParam String localAdr, @RequestParam String remoteAdr);

    @RequestMapping(method = RequestMethod.GET, value = "/defaultState")
    Optional<StateMetric> getDefaultState(@RequestParam Long classId, String label);


    @RequestMapping(method = RequestMethod.POST, value = "/init-class")
    void initClass(@RequestBody InitClass initClass,
                   @RequestParam("tableName") String tableName,
                   @RequestParam("shemaName") String shemaName);

    @RequestMapping(method = RequestMethod.POST, value = "/init-var")
    void initVaraible(@RequestBody InitVariable initVariable);

    @RequestMapping(method = RequestMethod.GET, value = "/sequence_number")
    String getSequenceNumber(@RequestParam("jsonObject") JSONObject jsonObject, @RequestParam("fullClassName") String fullClassName);


    @PostMapping("/ref-sequence-formats")
    ResponseEntity<RefSequenceFormat> createRefSequenceFormat(@RequestBody RefSequenceFormat refSequenceFormat);

    @RequestMapping(method = RequestMethod.POST, value = "/init-module")
    void initModule(@RequestBody InitModule initModule);

    @RequestMapping(method = RequestMethod.GET, value = "/acl-class-by-classname")
    AclClass getaclClassByClassName(@RequestParam String classname);

    @GetMapping("/all-string-resources")
    List<StringResourceDTO> getAllStringResources();

    @PostMapping("/setDefaultState")
    void setDefaultState(@RequestParam Long objectId, @RequestParam String className);

    @GetMapping("/getCurrentState")
    StateWorkflow getCurrentState(@RequestParam String className, @RequestParam Long objectId);
}



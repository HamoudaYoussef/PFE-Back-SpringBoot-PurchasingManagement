package biz.picosoft.demo.Workflow.service;


import biz.picosoft.demo.client.kernel.intercomm.KernelInterface;
import biz.picosoft.demo.controller.errors.BadRequestAlertException;
import org.flowable.engine.delegate.DelegateExecution;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Named("serviceTaskCalculeDecision")
public class ServiceTaskCalculeDecision {


  @Autowired
  private KernelInterface kernelInterface;



    public List<String> execute(DelegateExecution execution) {

      try {
        List<String> sid = kernelInterface.computValidator();
        execution.setVariable("chef", sid);
        return sid;

      } catch (Exception e) {
        throw new BadRequestAlertException("Enable to find director of '"+ execution.getVariable("initiator") +"'", "Workflow", "director not found");
      }



    }

  public List<String> check(DelegateExecution execution) {

    try {
      List<String> sid = kernelInterface.computValidator();
      execution.setVariable("chef", sid);
      return sid;

    } catch (Exception e) {
      return null;
    }



  }

  public List<String> find(DelegateExecution execution) {

    try {
      List<String> sid = kernelInterface.computValidator();
      execution.setVariable("chef", sid);
      return sid;

    } catch (Exception e) {
      throw new BadRequestAlertException("Enable to find director of '"+ execution.getVariable("initiator") +"'", "Workflow", "director not found");
    }



  }

}

package biz.picosoft.demo.Workflow.service;


import biz.picosoft.demo.client.kernel.intercomm.KernelInterface;
import biz.picosoft.demo.controller.errors.BadRequestAlertException;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Named("delegateSousCouvertService")
public class DelegateSousCouvertService implements JavaDelegate {

    @Autowired
    private KernelInterface kernelInterface;

    public void execute(DelegateExecution execution) {

        try {
            List<String> sid = kernelInterface.computValidator();
            execution.setVariable("chef", sid);


            execution.setVariable("Calculated", "true");


        } catch (Exception e) {
            throw new BadRequestAlertException("Enable to find director of '"+ execution.getVariable("initiator") +"'", "Workflow", "director not found");
        }

    }

}




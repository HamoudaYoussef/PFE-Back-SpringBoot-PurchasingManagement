package biz.picosoft.demo.controller;

import biz.picosoft.demo.client.kernel.intercomm.KernelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    KernelService kernelService;
    private final Logger log = LoggerFactory.getLogger(Controller.class);

    @GetMapping("/build-number")
    public String getBuildNumber() {
        return kernelService.getBuildNumber();
    }

}

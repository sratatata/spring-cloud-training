package pl.training.cloud.core.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoreController {

    @RequestMapping("status")
    public String getStatus() {
        return "Core microservice is running...";
    }

}

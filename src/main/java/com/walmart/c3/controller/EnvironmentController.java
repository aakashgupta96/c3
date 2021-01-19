package com.walmart.c3.controller;

import com.walmart.c3.services.EnvironmentService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/environments")
public class EnvironmentController {

    @Autowired
    private EnvironmentService environmentService;

    @GetMapping
    public List<Document> getEnvironments(@RequestParam("businessServiceName") String businessServiceName) {
        return environmentService.getEnvironments(businessServiceName);
    }

    @PostMapping
    public Document createEnvironments(@RequestBody Map<String, Object> body) {
        return environmentService.createEnvironment(body);
    }
}

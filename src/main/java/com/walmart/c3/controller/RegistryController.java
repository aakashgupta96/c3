package com.walmart.c3.controller;

import com.walmart.c3.services.RegistryService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/services")
public class RegistryController {


    @Autowired
    private RegistryService registryService;

    @GetMapping
    public List<Document> getServices() {
        return registryService.getServices();
    }

    @PostMapping
    public Document insertServices(@RequestBody Map<String, String> body) {
        return registryService.createService(body);
    }
}

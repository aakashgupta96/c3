package com.walmart.c3.services;

import com.walmart.c3.common.ApplicationConstants;
import com.walmart.c3.model.PlatformType;
import com.walmart.c3.services.platform.PlatformService;
import com.walmart.c3.services.platform.PlatformServiceFactory;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ApplicationService {

    @Autowired
    private PlatformServiceFactory platformServiceFactory;

    public void populateStatus(List<Document> applications) {
        for (Document application : applications) {
            PlatformType type = PlatformType.valueOf(application.getString(ApplicationConstants.PLATFORM_TYPE).toUpperCase());
            PlatformService platformService = platformServiceFactory.getService(type);
            platformService.addStatus(application);
        }
        return;
    }

    public void startApp(Map<String, String> application) {
        PlatformType type = PlatformType.valueOf(application.get(ApplicationConstants.PLATFORM_TYPE).toUpperCase());
        PlatformService platformService = platformServiceFactory.getService(type);
        platformService.startApplication(application);
    }
}

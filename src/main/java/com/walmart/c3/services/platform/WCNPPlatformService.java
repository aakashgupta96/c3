package com.walmart.c3.services.platform;

import com.walmart.c3.common.ApplicationConstants;
import com.walmart.c3.model.ApplicationState;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WCNPPlatformService implements PlatformService {
    @Override
    public void addStatus(Document application) {
        application.append(ApplicationConstants.APPLICATION_STATE, ApplicationState.STOPPED.getParameterName());
    }

    @Override
    public void startApplication(Map<String, String> application) {

    }

    @Override
    public void stopApplication(Map<String, String> application) {

    }
}

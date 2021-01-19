package com.walmart.c3.services.platform;


import org.bson.Document;

import java.util.Map;

public interface PlatformService {
    void addStatus(Document application);

    void startApplication(Map<String, String> application);

    void stopApplication(Map<String, String> application);
}

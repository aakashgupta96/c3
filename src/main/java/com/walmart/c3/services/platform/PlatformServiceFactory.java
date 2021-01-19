package com.walmart.c3.services.platform;

import com.walmart.c3.model.PlatformType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlatformServiceFactory {

    @Autowired
    private AzurePlatformService azurePlatformService;

    @Autowired
    private WCNPPlatformService wcnpPlatformService;

    public PlatformService getService(PlatformType type) {
        switch (type) {
            case WCNP:
                return wcnpPlatformService;
            case AZURE:
                return azurePlatformService;
            default:
                throw new IllegalArgumentException("Invalid Platform type");
        }
    }
}

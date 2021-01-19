package com.walmart.c3.services.platform;

import com.fasterxml.jackson.databind.JsonNode;
import com.walmart.c3.common.ApplicationConstants;
import com.walmart.c3.model.ApplicationState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;


@Service
@Slf4j
public class AzurePlatformService implements PlatformService {

    private RestTemplate restTemplate = new RestTemplate();

    @Value("${azure.token}")
    private String token;

    @Value("${azure.urls.fetch}")
    private String fetchURL;

    @Value("${azure.urls.action}")
    private String actionURL;

    private HttpEntity getRequestEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CACHE_CONTROL, CacheControl.noCache().getHeaderValue());
        headers.add(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", token));
        return new HttpEntity(headers);
    }

    @Override
    public void addStatus(Document application) {
        HttpEntity<JsonNode> httpEntity = getRequestEntity();
        String url = fetchURL
                .replace(String.format("{%s}", ApplicationConstants.SUBSCRIPTION_ID),
                        application.getString(ApplicationConstants.SUBSCRIPTION_ID))
                .replace(String.format("{%s}", ApplicationConstants.RESOURCE_GROUP),
                        application.getString(ApplicationConstants.RESOURCE_GROUP))
                .replace(String.format("{%s}", ApplicationConstants.NAME),
                        application.getString(ApplicationConstants.NAME));
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        try {
            ResponseEntity<JsonNode> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, httpEntity, JsonNode.class);
            application.append(ApplicationConstants.APPLICATION_STATE, responseEntity.getBody().at("/properties/state").textValue());
        } catch (Exception ex) {
            log.error("Exception received: {}", ex.getMessage());
            application.append(ApplicationConstants.APPLICATION_STATE, ApplicationState.NOT_DEFINED.getParameterName());
        }
        return;
    }

    @Override
    public void startApplication(Map<String, String> application) {
        changeState(application, ApplicationAction.START);
    }

    @Override
    public void stopApplication(Map<String, String> application) {
        changeState(application, ApplicationAction.STOP);
    }

    private void changeState(Map<String, String> application, ApplicationAction toState) {
        HttpEntity<JsonNode> httpEntity = getRequestEntity();
        String url = actionURL
                .replace(String.format("{%s}", ApplicationConstants.SUBSCRIPTION_ID),
                        application.get(ApplicationConstants.SUBSCRIPTION_ID))
                .replace(String.format("{%s}", ApplicationConstants.RESOURCE_GROUP),
                        application.get(ApplicationConstants.RESOURCE_GROUP))
                .replace(String.format("{%s}", ApplicationConstants.NAME),
                        application.get(ApplicationConstants.NAME))
                .replace("{action}", toState.getParameterName());
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        try {
            ResponseEntity<JsonNode> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, httpEntity, JsonNode.class);
        } catch (Exception ex) {
            log.error("Could not do action {} for application : ", toState.getParameterName(), application);
        }
        return;
    }

    @AllArgsConstructor
    private enum ApplicationAction {
        STOP("Stop"),
        START("Start");

        @Getter
        private String parameterName;
    }
}

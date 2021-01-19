package com.walmart.c3.services;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.walmart.c3.common.ApplicationConstants;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class EnvironmentService {

    @Autowired
    private MongoDatabase database;

    @Value("${mongo.collections.environments}")
    private String environmentsCollection;

    @Autowired
    private ApplicationService applicationService;

    public Document createEnvironment(Map<String, Object> body) {
        String id = body.get(ApplicationConstants.BUSINESS_SERVICE_NAME) + "|" + body.get(ApplicationConstants.ENV_NAME);
        Document environment = new Document();
        environment.append(ApplicationConstants.ID, id);
        environment.append(ApplicationConstants.ENV_NAME, body.get(ApplicationConstants.ENV_NAME));
        environment.append(ApplicationConstants.BUSINESS_SERVICE_NAME, body.get(ApplicationConstants.BUSINESS_SERVICE_NAME));

        List<Document> applications = new ArrayList<>();
        List<Map<String, String>> requestApplications = (List) (body.get(ApplicationConstants.APPLICATIONS));
        for (Map<String, String> requestApp : requestApplications) {
            Document application = new Document();
            application.putAll(requestApp);
            applications.add(application);
        }
        environment.append(ApplicationConstants.APPLICATIONS, applications);

        database.getCollection(environmentsCollection).insertOne(environment);
        return environment;
    }

    public List<Document> getEnvironments(String serviceName) {
        Bson filter = Filters.eq(ApplicationConstants.BUSINESS_SERVICE_NAME, serviceName);
        MongoCursor<Document> results = database.getCollection(environmentsCollection).find(filter).iterator();
        List<Document> environments = new ArrayList<>();
        while (results.hasNext()) {
            Document env = results.next();
            environments.add(env);
            applicationService.populateStatus((List<Document>) env.get(ApplicationConstants.APPLICATIONS));
        }
        return environments;
    }
}

package com.walmart.c3.services;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.walmart.c3.common.ApplicationConstants;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RegistryService {

    @Autowired
    private MongoDatabase database;

    @Value("${mongo.collections.services}")
    private String servicesCollection;

    public List<Document> getServices() {
        List<Document> result = new ArrayList<>();

        MongoCursor<Document> nodes = database.getCollection(servicesCollection).find().iterator();
        while (nodes.hasNext()) {
            result.add(nodes.next());
        }
        return result;
    }

    public Document createService(Map<String, String> body) {
        Document service = new Document();
        service.append(ApplicationConstants.ID, body.get(ApplicationConstants.BUSINESS_SERVICE_NAME));
        service.append(ApplicationConstants.BUSINESS_SERVICE_NAME, body.get(ApplicationConstants.BUSINESS_SERVICE_NAME));
        service.append(ApplicationConstants.TRR_PRODUCT_ID, body.getOrDefault(ApplicationConstants.TRR_PRODUCT_ID, null));
        service.append(ApplicationConstants.AD_GROUP, body.getOrDefault(ApplicationConstants.AD_GROUP, null));
        database.getCollection(servicesCollection).insertOne(service);
        return service;
    }
}


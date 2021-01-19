package com.walmart.c3.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MongoConfig {

    @Value("${mongo.uri}")
    private String mongoUri;

    @Value("${mongo.db}")
    private String dbName;

    @Bean
    @Primary
    public MongoClient mongoClient() {
        MongoClientOptions.Builder optionsBuilder = new MongoClientOptions.Builder();
        MongoClientURI uri = new MongoClientURI(mongoUri, optionsBuilder);
        return new MongoClient(uri);
    }

    @Bean
    @Primary
    public MongoDatabase mongoDatabase(@Autowired @Qualifier("mongoClient") MongoClient mongoClient) {
        return mongoClient.getDatabase(dbName);
    }

}
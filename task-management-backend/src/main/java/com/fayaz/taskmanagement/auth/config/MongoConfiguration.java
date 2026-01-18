package com.fayaz.taskmanagement.auth.config;

import java.util.ArrayList;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfiguration {

    @Bean
    ApplicationRunner mongoStartupCheck(MongoTemplate mongoTemplate) {
        return args -> {
            mongoTemplate.getDb().listCollectionNames().first();
        };
    }
} 

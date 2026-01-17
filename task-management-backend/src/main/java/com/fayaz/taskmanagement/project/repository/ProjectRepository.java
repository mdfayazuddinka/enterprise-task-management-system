package com.fayaz.taskmanagement.project.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fayaz.taskmanagement.project.entity.ProjectEntity;


public interface ProjectRepository extends MongoRepository<ProjectEntity, String> {
    
}

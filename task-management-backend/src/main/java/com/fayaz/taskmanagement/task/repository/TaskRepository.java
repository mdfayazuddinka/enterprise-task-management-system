package com.fayaz.taskmanagement.task.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fayaz.taskmanagement.task.entity.TaskEntity;

public interface TaskRepository extends MongoRepository<TaskEntity, String> {

    List<TaskEntity> findByAssignedTo(String assignedTo);

    Optional<TaskEntity> findByTaskId(String taskId);

    List<TaskEntity> findByProjectId(String projectId);

    
}


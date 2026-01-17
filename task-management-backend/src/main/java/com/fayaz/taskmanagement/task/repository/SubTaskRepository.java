package com.fayaz.taskmanagement.task.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fayaz.taskmanagement.task.entity.SubTaskEntity;

@Repository
public interface SubTaskRepository extends MongoRepository<SubTaskEntity, String> {
    Optional<SubTaskEntity> findById(String subTaskId);
    List<SubTaskEntity> findByParentTaskId(String parentTaskId);
    void delete(SubTaskEntity subTask);
}

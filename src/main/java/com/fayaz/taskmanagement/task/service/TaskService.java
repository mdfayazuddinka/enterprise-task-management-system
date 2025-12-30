package com.fayaz.taskmanagement.task.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.fayaz.taskmanagement.task.dto.TaskCreationDto;
import com.fayaz.taskmanagement.task.dto.TaskDto;
import com.fayaz.taskmanagement.task.dto.TaskUpsertDto;
import com.fayaz.taskmanagement.task.entity.TaskEntity;


public interface TaskService {
    Page<TaskEntity> getAllTasks(int page, String assignedTo, String status, String title, String sortOrder);

    TaskDto createTask(TaskCreationDto task);

    Optional<TaskDto> getTaskById(String taskId);

    TaskDto updateTask(String taskId, TaskUpsertDto task);

    void deleteTask(String taskId);
}

package com.fayaz.taskmanagement.task.dto;

import com.fayaz.taskmanagement.task.enums.TaskStatusEnum;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskUpsertDto {
    private String taskId;
    private String description;
    private TaskStatusEnum status;
    private String assignedTo;
    private String comments;
}

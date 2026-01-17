package com.fayaz.taskmanagement.task.dto;

import com.fayaz.taskmanagement.task.enums.StatusEnum;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskUpsertDto {
    private String taskId;
    private String description;
    private StatusEnum status;
    private String assignedTo;
    private String comments;
}

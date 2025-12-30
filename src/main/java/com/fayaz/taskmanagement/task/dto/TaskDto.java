package com.fayaz.taskmanagement.task.dto;

import com.fayaz.taskmanagement.task.enums.TaskStatusEnum;
import com.fayaz.taskmanagement.utils.AuditDto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TaskDto {
    private String id;
    private String title;
    private String description;
    private TaskStatusEnum status;
    private String createdBy;
    private String assignedTo;
    private String comments;
    private boolean deleted;
    private AuditDto audit;
}

package com.fayaz.taskmanagement.task.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskCreationDto {
    private String title;
    private String description;
    private String createdBy;
    private String assignedTo;
    private String comments;
}

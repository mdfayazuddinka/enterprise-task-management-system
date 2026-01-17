package com.fayaz.taskmanagement.task.dto;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubTaskCreationDto {
    private String title;
    private String parentTaskId;
    private String description;
    private String assignedTo;
    private String priority;
    private String status;
    private String type;
    private Date dueDate;
}

package com.fayaz.taskmanagement.task.dto;

import java.util.Date;

import com.fayaz.taskmanagement.task.enums.PriorityEnum;
import com.fayaz.taskmanagement.task.enums.StatusEnum;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskCreationDto {
    private String title;
    private String description;
    private String assignedTo;
    private PriorityEnum priority;
    private StatusEnum status;
    private Date createdDate;
    private Date dueDate;
    private String comments;
    private String projectId;
}

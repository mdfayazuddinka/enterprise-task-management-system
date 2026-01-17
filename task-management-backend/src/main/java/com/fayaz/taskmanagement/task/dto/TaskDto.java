package com.fayaz.taskmanagement.task.dto;

import java.util.Date;

import com.fayaz.taskmanagement.task.enums.PriorityEnum;
import com.fayaz.taskmanagement.task.enums.StatusEnum;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TaskDto {
    private String id;
    private String title;
    private String description;
    private PriorityEnum priority;
    private StatusEnum status;
    private String createdBy;
    private Date createdDate;
    private Date dueDate;
    private String assignedTo;
    private String comments;
    private String projectId;
}

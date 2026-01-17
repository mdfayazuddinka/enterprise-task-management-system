package com.fayaz.taskmanagement.task.entity;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fayaz.taskmanagement.task.enums.PriorityEnum;
import com.fayaz.taskmanagement.task.enums.StatusEnum;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection = "tasks")
public class TaskEntity {
    private String _id;
    private String taskId;
    private String title;
    private String description;
    private String createdBy;
    private String assignedTo;
    private String comments;
    private PriorityEnum priority;
    private StatusEnum status;
    private Date createdDate;
    private Date dueDate;
    private String projectId;
    private Date lastModifiedDate;
}

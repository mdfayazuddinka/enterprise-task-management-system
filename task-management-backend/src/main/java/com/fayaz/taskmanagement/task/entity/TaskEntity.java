package com.fayaz.taskmanagement.task.entity;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fayaz.taskmanagement.task.enums.TaskPriorityEnum;
import com.fayaz.taskmanagement.task.enums.TaskStatusEnum;
import com.fayaz.taskmanagement.utils.AuditDto;

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
    private boolean deleted;
    private String comments;
    private TaskPriorityEnum priority;
    private TaskStatusEnum status;
    private AuditDto audit;
    private Date createdDate;
    private Date dueDate;
    private String projectName;

}

package com.fayaz.taskmanagement.task.entity;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fayaz.taskmanagement.utils.AuditDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection = "subTasks")
public class SubTaskEntity {
    private String id;
    private String title;
    private String subTaskId;
    private String parentTaskId;
    private String description;
    private String assignedTo;
    private String status;
    private String priority;
    private String type;
    private Date dueDate;
    private AuditDto audit;
}

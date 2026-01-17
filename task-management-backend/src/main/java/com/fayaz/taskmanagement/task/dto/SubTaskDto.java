package com.fayaz.taskmanagement.task.dto;

import java.util.Date;

import com.fayaz.taskmanagement.utils.AuditDto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SubTaskDto {
    private String id;
    private String title;
    private String description;
    private String priority;
    private String status;
    private Date dueDate;
    private String assignedTo;
    private String comments;
    private String projectId;
    private AuditDto audit;
    private String parentTaskId;
}

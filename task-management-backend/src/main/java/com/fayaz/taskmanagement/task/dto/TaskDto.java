package com.fayaz.taskmanagement.task.dto;

import java.util.Date;
import java.util.List;

import com.fayaz.taskmanagement.task.enums.PriorityEnum;
import com.fayaz.taskmanagement.task.enums.StatusEnum;
import com.fayaz.taskmanagement.utils.AuditDto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TaskDto {
    private String id;
    private String taskId;
    private String title;
    private String description;
    private PriorityEnum priority;
    private String status;
    private Date dueDate;
    private String type;
    private String assignedTo;
    private String comments;
    private String projectId;
    private AuditDto audit;
    private List<SubTaskDto> subTasks;
}

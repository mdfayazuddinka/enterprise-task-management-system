package com.fayaz.taskmanagement.task.dto;

import com.fayaz.taskmanagement.task.enums.PriorityEnum;
import com.fayaz.taskmanagement.task.enums.StatusEnum;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskUpsertDto {
    private String title;
    private String description;
    private PriorityEnum priority;
    private String assignedTo;
    private String type;
    private String status;
}

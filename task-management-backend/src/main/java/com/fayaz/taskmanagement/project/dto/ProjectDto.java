package com.fayaz.taskmanagement.project.dto;

import com.fayaz.taskmanagement.task.enums.PriorityEnum;
import com.fayaz.taskmanagement.utils.AuditDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectDto {
    private String _id;
    private String projectCode;
    private String name;
    private String type;
    private PriorityEnum priority; 
    private String category;
    private String subCategory;
    private String status;
    private String startDate;
    private String dueDate;
    private AuditDto audit;
}

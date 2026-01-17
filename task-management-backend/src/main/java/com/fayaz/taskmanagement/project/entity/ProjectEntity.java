package com.fayaz.taskmanagement.project.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fayaz.taskmanagement.task.enums.PriorityEnum;
import com.fayaz.taskmanagement.utils.AuditDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection = "project")
public class ProjectEntity {
    @Id
    private String _id;
    private String name;
    private String projectCode;
    private String type;
    private PriorityEnum priority; 
    private String category;
    private String subCategory;
    private String status;
    private String startDate;
    private String dueDate;
    private AuditDto audit;
}

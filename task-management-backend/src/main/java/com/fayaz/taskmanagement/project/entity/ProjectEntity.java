package com.fayaz.taskmanagement.project.entity;

import org.springframework.data.annotation.Id;

import com.fayaz.taskmanagement.task.enums.PriorityEnum;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
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
}

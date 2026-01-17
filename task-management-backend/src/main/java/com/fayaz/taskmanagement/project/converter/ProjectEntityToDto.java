package com.fayaz.taskmanagement.project.converter;

import com.fayaz.taskmanagement.project.dto.ProjectDto;
import com.fayaz.taskmanagement.project.entity.ProjectEntity;

public class ProjectEntityToDto {
    public static ProjectDto convert(ProjectEntity projectEntity) {
        if (projectEntity == null) {
            return null;
        }

        return ProjectDto.builder()
            ._id(projectEntity.get_id())
            .name(projectEntity.getName())
            .projectCode(projectEntity.getProjectCode())
            .category(projectEntity.getCategory())
            .subCategory(projectEntity.getSubCategory())
            .startDate(projectEntity.getStartDate())
            .priority(projectEntity.getPriority())
            .status(projectEntity.getStatus())
            .dueDate(projectEntity.getDueDate())
            .type(projectEntity.getType())
            .audit(projectEntity.getAudit())
            .build();
    }
}

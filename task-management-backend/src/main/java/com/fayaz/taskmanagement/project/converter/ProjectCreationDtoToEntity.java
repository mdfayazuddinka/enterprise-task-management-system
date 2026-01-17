package com.fayaz.taskmanagement.project.converter;

import org.springframework.beans.factory.annotation.Autowired;

import com.fayaz.taskmanagement.project.dto.ProjectCreationDto;
import com.fayaz.taskmanagement.project.entity.ProjectEntity;
import com.fayaz.taskmanagement.utils.SequenceEnum;
import com.fayaz.taskmanagement.utils.SequenceGeneratorService;

public abstract class ProjectCreationDtoToEntity {

    @Autowired
    private static SequenceGeneratorService sequenceGeneratorService;

    public static ProjectEntity convert(ProjectCreationDto projectCreationDto) {
        if (projectCreationDto == null) {
            return null;
        }

         ProjectEntity projectEntity = ProjectEntity.builder()
                .name(projectCreationDto.getName())
                .category(projectCreationDto.getCategory())
                .projectCode(getProjectCode())
                .dueDate(projectCreationDto.getDueDate())
                .startDate(projectCreationDto.getStartDate())
                .priority(projectCreationDto.getPriority())
                .subCategory(projectCreationDto.getSubCategory())
                .type(projectCreationDto.getType())
                .status(projectCreationDto.getStatus())
                .build();

        return projectEntity;
    }

    private static String getProjectCode() {
        return String.format("PJT-%05d", sequenceGeneratorService.getNextSequence(
                SequenceEnum.PROJECT_SEQUENCE.getSequenceName()));
    }
}

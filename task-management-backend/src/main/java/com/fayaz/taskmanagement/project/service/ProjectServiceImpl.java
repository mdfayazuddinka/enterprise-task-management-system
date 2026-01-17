package com.fayaz.taskmanagement.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fayaz.taskmanagement.project.converter.ProjectEntityToDto;
import com.fayaz.taskmanagement.project.dto.ProjectCreationDto;
import com.fayaz.taskmanagement.project.dto.ProjectDto;
import com.fayaz.taskmanagement.project.entity.ProjectEntity;
import com.fayaz.taskmanagement.utils.SequenceEnum;
import com.fayaz.taskmanagement.utils.SequenceGeneratorService;
import com.fayaz.taskmanagement.project.repository.ProjectRepository;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Override
    public List<ProjectDto> getAllProjects() {
        return projectRepository.findAll()
                .stream()
                .map(ProjectEntityToDto::convert)
                .toList();
    }

    @Override
    public ProjectDto addProject(ProjectCreationDto projectCreationDto) {

        String projectCode = String.format("PJT-%05d", sequenceGeneratorService.getNextSequence(
                SequenceEnum.PROJECT_SEQUENCE.getSequenceName()));

        ProjectEntity projectEntity = ProjectEntity.builder()
                .name(projectCreationDto.getName())
                .category(projectCreationDto.getCategory())
                .projectCode(projectCode)
                .dueDate(projectCreationDto.getDueDate())
                .startDate(projectCreationDto.getStartDate())
                .priority(projectCreationDto.getPriority())
                .subCategory(projectCreationDto.getSubCategory())
                .type(projectCreationDto.getType())
                .status(projectCreationDto.getStatus())
                .build();
        ProjectEntity savedEntity = save(projectEntity);
        return ProjectEntityToDto.convert(savedEntity);
    }

    private ProjectEntity save(ProjectEntity task) {
        return projectRepository.save(task);
    }
}

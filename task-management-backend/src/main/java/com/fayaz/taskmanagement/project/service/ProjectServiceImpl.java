package com.fayaz.taskmanagement.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fayaz.taskmanagement.project.converter.ProjectCreationDtoToEntity;
import com.fayaz.taskmanagement.project.converter.ProjectEntityToDto;
import com.fayaz.taskmanagement.project.dto.ProjectCreationDto;
import com.fayaz.taskmanagement.project.dto.ProjectDto;
import com.fayaz.taskmanagement.project.entity.ProjectEntity;
import com.fayaz.taskmanagement.project.repository.ProjectRepository;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectCreationDtoToEntity projectCreationDtoToEntity;

    @Override
    public List<ProjectDto> getAllProjects() {
        return projectRepository.findAll()
                .stream()
                .map(ProjectEntityToDto::convert)
                .toList();
    }

    @Override
    public ProjectDto addProject(ProjectCreationDto projectCreationDto) {
        ProjectEntity projectEntity = projectCreationDtoToEntity.convert(projectCreationDto);
        ProjectEntity savedEntity = save(projectEntity);
        return ProjectEntityToDto.convert(savedEntity);
    }

    private ProjectEntity save(ProjectEntity task) {
        return projectRepository.save(task);
    }
}

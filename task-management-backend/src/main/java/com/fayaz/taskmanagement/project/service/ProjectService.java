package com.fayaz.taskmanagement.project.service;

import java.util.List;

import com.fayaz.taskmanagement.project.dto.ProjectCreationDto;
import com.fayaz.taskmanagement.project.dto.ProjectDto;

public interface ProjectService  {

    List<ProjectDto> getAllProjects();

    ProjectDto addProject(ProjectCreationDto projectCreationDto);
}

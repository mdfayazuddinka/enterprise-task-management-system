package com.fayaz.taskmanagement.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fayaz.taskmanagement.project.dto.ProjectCreationDto;
import com.fayaz.taskmanagement.project.dto.ProjectDto;
import com.fayaz.taskmanagement.project.service.ProjectService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("${endpoint.project}")
@RequiredArgsConstructor
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("/add-project")
    public ResponseEntity<ProjectDto> addProject(@RequestBody ProjectCreationDto projectCreationDto) {
        ProjectDto projectDto = projectService.addProject(projectCreationDto);
        return ResponseEntity.ok(projectDto);
    }

    @GetMapping("/getAllProjects")
    public List<ProjectDto> getAllProjects() {
        return projectService.getAllProjects();
    } 
}

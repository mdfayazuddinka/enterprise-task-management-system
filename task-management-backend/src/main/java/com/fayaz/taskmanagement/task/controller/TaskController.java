package com.fayaz.taskmanagement.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fayaz.taskmanagement.task.dto.TaskCreationDto;
import com.fayaz.taskmanagement.task.dto.TaskDto;
import com.fayaz.taskmanagement.task.dto.TaskUpsertDto;
import com.fayaz.taskmanagement.task.entity.TaskEntity;
import com.fayaz.taskmanagement.task.service.TaskService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("${endpoint.tasks}")
@RequiredArgsConstructor
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<Page<TaskEntity>> getTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String assignedTo,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "desc") String sortOrder) {
        return ResponseEntity.ok(taskService.getAllTasks(
                page,
                assignedTo,
                status,
                name,
                sortOrder));
    }

    @PostMapping("/createTask")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskCreationDto task) {
        TaskDto responseTaskDto = taskService.createTask(task);
        return ResponseEntity.ok(responseTaskDto);
    }

    @GetMapping("/{taskId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable String taskId) {
        return taskService.getTaskById(taskId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{taskId}/update")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<TaskDto> updateTask(@PathVariable String taskId, @RequestBody TaskUpsertDto task) {
        TaskDto updated = taskService.updateTask(taskId, task);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{taskId}/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteTask(@PathVariable String taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }
}
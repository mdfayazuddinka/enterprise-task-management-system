package com.fayaz.taskmanagement.task.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.fayaz.taskmanagement.CustomException.TaskGenericException;
import com.fayaz.taskmanagement.CustomException.TaskNotFoundException;
import com.fayaz.taskmanagement.task.converter.SubTaskCreationDtoToEntity;
import com.fayaz.taskmanagement.task.converter.SubTaskEntityToDto;
import com.fayaz.taskmanagement.task.converter.TaskCreationDtoToEntity;
import com.fayaz.taskmanagement.task.converter.TaskEntityToDto;
import com.fayaz.taskmanagement.task.dto.SubTaskCreationDto;
import com.fayaz.taskmanagement.task.dto.SubTaskDto;
import com.fayaz.taskmanagement.task.dto.TaskCreationDto;
import com.fayaz.taskmanagement.task.dto.TaskDto;
import com.fayaz.taskmanagement.task.dto.TaskUpsertDto;
import com.fayaz.taskmanagement.task.entity.SubTaskEntity;
import com.fayaz.taskmanagement.task.entity.TaskEntity;
import com.fayaz.taskmanagement.task.enums.StatusEnum;
import com.fayaz.taskmanagement.task.repository.SubTaskRepository;
import com.fayaz.taskmanagement.task.repository.TaskRepository;

@Service
public class TaskServiceImpl implements TaskService {
    
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private SubTaskRepository subTaskRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private TaskCreationDtoToEntity taskCreationDtoToEntity;
    
    @Autowired
    private SubTaskCreationDtoToEntity subTaskCreationDtoToEntity;

    public Page<TaskEntity> getAllTasks(int page, String assignedTo, String status, String name, String sortOrder) {

        List<AggregationOperation> operations = new ArrayList<>();
        List<Criteria> criteriaList = new ArrayList<>();

        if (assignedTo != null) {
            criteriaList.add(Criteria.where("assignedTo").is(assignedTo));
        }

        if (status != null) {
            criteriaList.add(Criteria.where("status").is(status));
        }

        if (name != null) {
            criteriaList.add(
                    Criteria.where("name").regex(name, "i"));
        }

        if (!criteriaList.isEmpty()) {
            operations.add(Aggregation.match(
                    new Criteria().andOperator(criteriaList)));
        }

        Sort.Direction direction = "asc".equalsIgnoreCase(sortOrder)
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        operations.add(Aggregation.sort(direction, "createdDate"));
        operations.add(Aggregation.skip((long) page * 20));
        operations.add(Aggregation.limit(20));

        Aggregation aggregation = Aggregation.newAggregation(operations);

        List<TaskEntity> tasks = mongoTemplate.aggregate(
                aggregation,
                "tasks",
                TaskEntity.class).getMappedResults();

        Pageable pageable = PageRequest.of(page, 20);
        return new PageImpl<>(tasks, pageable, 0);
    }


    public TaskDto createTask(TaskCreationDto task) {
        TaskEntity convertedEntity = taskCreationDtoToEntity.convert(task);
        TaskEntity savedEntity = save(convertedEntity);
        return TaskEntityToDto.convert(savedEntity);
    }

    public SubTaskDto createSubTask(SubTaskCreationDto subTask) {
        SubTaskEntity convertedEntity = subTaskCreationDtoToEntity.convert(subTask);
        SubTaskEntity savedEntity = subTaskRepository.save(convertedEntity);
        return SubTaskEntityToDto.convert(savedEntity);
    }

    public List<TaskDto> getTasksByProjectId(String projectId) {
        List<TaskEntity> tasks = taskRepository.findByProjectId(projectId);
        return tasks.stream()
                .map(TaskEntityToDto::convert)
                .collect(Collectors.toList());
    }

    public Optional<TaskDto> getTaskById(String taskId) {
        Optional<TaskEntity> taskEntity = taskRepository.findById(taskId);
        if (!taskEntity.isPresent()) {
            return null;
        }
        TaskDto taskDto = TaskEntityToDto.convert(taskEntity.get());
        return Optional.of(taskDto);
    }

    public List<String> getAssignees() {
        return taskRepository.findAll().stream()
                .map(TaskEntity::getAssignedTo)
                .distinct()
                .collect(Collectors.toList());
    }

    public TaskDto updateTask(String taskId, TaskUpsertDto taskUpsertDto) {
        Optional<TaskEntity> taskEntity = taskRepository.findByTaskId(taskId);
        if (!canEditTask(taskEntity.get())) {
            throw new TaskGenericException("User is not authorized to update this task: " + taskId);
        }

        taskEntity.get().setTitle(taskUpsertDto.getTitle());
        taskEntity.get().setType(taskUpsertDto.getType());
        taskEntity.get().setPriority(taskUpsertDto.getPriority());
        taskEntity.get().setStatus(StatusEnum.fromValue(taskUpsertDto.getStatus()).name());
        taskEntity.get().setAssignedTo(taskUpsertDto.getAssignedTo());
        taskEntity.get().setDescription(taskUpsertDto.getDescription());
        TaskEntity savedEntity = taskRepository.save(taskEntity.get());
        return TaskEntityToDto.convert(savedEntity);
    }

    public void deleteTask(String taskId) {
        Optional<TaskEntity> taskEntity =  taskRepository.findByTaskId(taskId);
        if (!taskEntity.isPresent()) {
            throw new TaskNotFoundException("Task not found with id: " + taskId);
        }
        taskRepository.delete(taskEntity.get());
    }

    private TaskEntity save(TaskEntity task) {
        return taskRepository.save(task);
    }

    private boolean canEditTask(TaskEntity taskEntity) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        return taskEntity != null && taskEntity.getAssignedTo().equals(userName) || auth.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ADMIN") || role.getAuthority().equals("PROJECT_MANAGER"));
    }
}

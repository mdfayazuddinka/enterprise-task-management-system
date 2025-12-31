package com.fayaz.taskmanagement.task.service;

import java.util.ArrayList;
import java.util.Date;
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

import com.fayaz.taskmanagement.CustomException.TaskCreationException;
import com.fayaz.taskmanagement.CustomException.TaskGenericException;
import com.fayaz.taskmanagement.CustomException.TaskNotFoundException;
import com.fayaz.taskmanagement.auth.RoleEnum;
import com.fayaz.taskmanagement.auth.entity.UserEntity;
import com.fayaz.taskmanagement.auth.service.AuthService;
import com.fayaz.taskmanagement.task.converter.TaskEntityToDto;
import com.fayaz.taskmanagement.task.dto.TaskCreationDto;
import com.fayaz.taskmanagement.task.dto.TaskDto;
import com.fayaz.taskmanagement.task.dto.TaskUpsertDto;
import com.fayaz.taskmanagement.task.entity.TaskEntity;
import com.fayaz.taskmanagement.task.enums.TaskStatusEnum;
import com.fayaz.taskmanagement.task.repository.TaskRepository;
import com.fayaz.taskmanagement.utils.AuditDto;
import com.fayaz.taskmanagement.utils.SequenceEnum;
import com.fayaz.taskmanagement.utils.SequenceGeneratorService;

@Service
public class TaskServiceImpl implements TaskService {
    
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private AuthService authService;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    public Page<TaskEntity> getAllTasks(
            int page,
            String assignedTo,
            String status,
            String name,
            String sortOrder) {

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
        String createdBy = task.getCreatedBy();
        String assignedTo = task.getAssignedTo();
        UserEntity user = authService.getUserById(createdBy).get();

        if (!createdBy.startsWith("USR") && !assignedTo.startsWith("USR") ||
        !doesUserExists(List.of(createdBy, assignedTo))) {
            throw new TaskNotFoundException("Invalid user IDs provided.");
        }

        if (!user.getRole().contains(RoleEnum.ROLE_ADMIN) && !user.getUserId().equals(assignedTo)) {
            throw new TaskCreationException("User is not authorized to create tasks for others.");
        }

        String taskId = String.format("TSK-%05d", sequenceGeneratorService.getNextSequence(
            SequenceEnum.TASK_SEQUENCE.getSequenceName()));

        TaskEntity taskEntity = TaskEntity.builder()
        .title(task.getTitle())
        .taskId(taskId)
        .description(task.getDescription())
        .createdBy(task.getCreatedBy())
        .assignedTo(task.getAssignedTo())
        .deleted(false)
        .comments(task.getComments())
        .status(TaskStatusEnum.TODO)
        .audit(setAuditDto(user)).build();
        TaskEntity savedEntity = save(taskEntity);
        return TaskEntityToDto.convert(savedEntity);
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

    public TaskDto updateTask(String taskId, TaskUpsertDto task) {
        Optional<TaskEntity> taskEntity = taskRepository.findByTaskId(taskId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        if (!taskEntity.get().getAssignedTo().equals(userName)) {
            throw new TaskGenericException("User is not authorized to update this task: " + taskId);
        }
        taskEntity.get().setComments(task.getComments());
        taskEntity.get().setAssignedTo(task.getAssignedTo());
        taskEntity.get().setStatus(task.getStatus());
        taskEntity.get().setDescription(task.getDescription());
        taskEntity.get().getAudit().setLastModifiedDate(new Date());
        TaskEntity savedEntity = taskRepository.save(taskEntity.get());
        return TaskEntityToDto.convert(savedEntity);
    }

    public void deleteTask(String taskId) {
        Optional<TaskEntity> taskEntity =  taskRepository.findByTaskId(taskId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        if (!taskEntity.get().getAssignedTo().equals(userName)) {
            throw new TaskGenericException("User is not authorized to delete this task: " + taskId);
        }
        if (!taskEntity.isPresent()) {
            throw new TaskNotFoundException("Task not found with id: " + taskId);

        }
        taskRepository.delete(taskEntity.get());
    }

    private TaskEntity save(TaskEntity task) {
        return taskRepository.save(task);
    }

    private boolean doesUserExists(List<String> userId) {
        return userId.stream().allMatch(id -> authService.doesUserExists(id));
    }

    private AuditDto setAuditDto(UserEntity user) {
        return AuditDto.builder()
                .createdBy(user.getUserId())
                .createdDate(new Date())
                .lastModifiedDate(new Date())
                .lastModifiedBy(user.getUserId())
                .roles(user.getRole())
                .userId(user.getUserId())
                .build();
    }
}

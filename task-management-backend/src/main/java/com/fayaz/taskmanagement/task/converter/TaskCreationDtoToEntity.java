package com.fayaz.taskmanagement.task.converter;

import java.io.ObjectInputFilter.Status;
import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.fayaz.taskmanagement.auth.entity.OwnerEntity;
import com.fayaz.taskmanagement.auth.entity.UserEntity;
import com.fayaz.taskmanagement.auth.service.AuthService;
import com.fayaz.taskmanagement.task.dto.TaskCreationDto;
import com.fayaz.taskmanagement.task.entity.TaskEntity;
import com.fayaz.taskmanagement.task.enums.StatusEnum;
import com.fayaz.taskmanagement.utils.AuditDto;
import com.fayaz.taskmanagement.utils.SequenceEnum;
import com.fayaz.taskmanagement.utils.SequenceGeneratorService;

@Component
public class TaskCreationDtoToEntity {

    private final SequenceGeneratorService sequenceGeneratorService;
    private final AuthService authService;

    public TaskCreationDtoToEntity(SequenceGeneratorService sequenceGeneratorService, AuthService authService) {
        this.sequenceGeneratorService = sequenceGeneratorService;
        this.authService = authService;
    }

    public TaskEntity convert(TaskCreationDto taskCreationDto) {
        if (taskCreationDto == null) {
            return null;
        }

        return TaskEntity.builder()
                .title(taskCreationDto.getTitle())
                .taskId(getTaskId())
                .description(taskCreationDto.getDescription())
                .assignedTo(taskCreationDto.getAssignedTo())
                .comments(taskCreationDto.getComments())
                .status(StatusEnum.fromValue(taskCreationDto.getStatus()).name())
                .type(taskCreationDto.getType())
                .priority(taskCreationDto.getPriority())
                .dueDate(taskCreationDto.getDueDate())
                .projectId(taskCreationDto.getProjectId())
                .audit(getAuditDto(taskCreationDto))
                .build();
    }

    private String getTaskId() {
        return String.format("TSK-%05d", 
            sequenceGeneratorService.getNextSequence(SequenceEnum.TASK_SEQUENCE.getSequenceName()));
    }

    private AuditDto getAuditDto(TaskCreationDto taskCreationDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = authService.getUserById(auth.getPrincipal().toString()).get();
        OwnerEntity ownerEntity = OwnerEntity.builder()
                .userId(userEntity.getUserId())
                .userName(userEntity.getUserName())
                .roles(userEntity.getRole())
                .email(userEntity.getEmail())
                .build();

        return AuditDto.builder()
                .owner(ownerEntity)
                .createdDate(taskCreationDto.getCreatedDate() != null ? taskCreationDto.getCreatedDate() : new Date())
                .lastModifiedBy(ownerEntity)
                .lastModifiedDate(new Date())
                .build();
    }
}

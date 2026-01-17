package com.fayaz.taskmanagement.task.converter;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fayaz.taskmanagement.auth.entity.UserEntity;
import com.fayaz.taskmanagement.auth.service.AuthService;
import com.fayaz.taskmanagement.task.dto.TaskCreationDto;
import com.fayaz.taskmanagement.task.entity.TaskEntity;
import com.fayaz.taskmanagement.utils.AuditDto;
import com.fayaz.taskmanagement.utils.SequenceEnum;
import com.fayaz.taskmanagement.utils.SequenceGeneratorService;

public class TaskCreationDtoToEntity {

    @Autowired
    private static SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    private static AuthService authService;

    public static TaskEntity convert(TaskCreationDto taskCreationDto) {
        if (taskCreationDto == null) {
            return null;
        }

        return TaskEntity.builder()
                .title(taskCreationDto.getTitle())
                .taskId(getTaskId())
                .description(taskCreationDto.getDescription())
                .assignedTo(taskCreationDto.getAssignedTo())
                .comments(taskCreationDto.getComments())
                .status(taskCreationDto.getStatus())
                .priority(taskCreationDto.getPriority())
                .dueDate(taskCreationDto.getDueDate())
                .projectId(taskCreationDto.getProjectId())
                .audit(getAuditDto())
                .build();
    }

    private static String getTaskId() {
        return String.format("TSK-%05d", sequenceGeneratorService.getNextSequence(
                SequenceEnum.TASK_SEQUENCE.getSequenceName()));
    }

    private static AuditDto getAuditDto() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = authService.getUserByName(auth.getName()).get();
        return AuditDto.builder()
                .owner(userEntity)
                .createdDate(new Date())
                .lastModifiedBy(userEntity)
                .lastModifiedDate(new Date())
                .build();
    }
}

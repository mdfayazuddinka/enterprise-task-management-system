package com.fayaz.taskmanagement.task.converter;

import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.fayaz.taskmanagement.auth.entity.OwnerEntity;
import com.fayaz.taskmanagement.auth.entity.UserEntity;
import com.fayaz.taskmanagement.auth.service.AuthService;
import com.fayaz.taskmanagement.task.dto.SubTaskCreationDto;
import com.fayaz.taskmanagement.task.entity.SubTaskEntity;
import com.fayaz.taskmanagement.utils.AuditDto;
import com.fayaz.taskmanagement.utils.SequenceEnum;
import com.fayaz.taskmanagement.utils.SequenceGeneratorService;

@Component
public class SubTaskCreationDtoToEntity {

    private final SequenceGeneratorService sequenceGeneratorService;
    private final AuthService authService;

    public SubTaskCreationDtoToEntity(SequenceGeneratorService sequenceGeneratorService, AuthService authService) {
        this.sequenceGeneratorService = sequenceGeneratorService;
        this.authService = authService;
    }

    public SubTaskEntity convert(SubTaskCreationDto subTaskCreationDto) {
        if (subTaskCreationDto == null) {
            return null;
        }

        return SubTaskEntity.builder()
                .title(subTaskCreationDto.getTitle())
                .subTaskId(getSubTaskId())
                .description(subTaskCreationDto.getDescription())
                .assignedTo(subTaskCreationDto.getAssignedTo())
                .status(subTaskCreationDto.getStatus())
                .priority(subTaskCreationDto.getPriority())
                .dueDate(subTaskCreationDto.getDueDate())
                .parentTaskId(subTaskCreationDto.getParentTaskId())
                .audit(getAuditDto(subTaskCreationDto))
                .build();
    }

    private String getSubTaskId() {
        return String.format("SUB-%05d", 
            sequenceGeneratorService.getNextSequence(SequenceEnum.SUB_TASK_SEQUENCE.getSequenceName()));
    }

    private AuditDto getAuditDto(SubTaskCreationDto subTaskCreationDto) {
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
                .createdDate(new Date())
                .lastModifiedBy(ownerEntity)
                .lastModifiedDate(new Date())
                .build();
    }
    
}

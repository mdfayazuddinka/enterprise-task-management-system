package com.fayaz.taskmanagement.task.converter;

import com.fayaz.taskmanagement.task.dto.TaskDto;
import com.fayaz.taskmanagement.task.entity.TaskEntity;

public class TaskEntityToDto {
    public static TaskDto convert(TaskEntity taskEntity) {
        if (taskEntity == null) {
            return null;
        }

        return TaskDto.builder()
                .id(taskEntity.get_id())
                .title(taskEntity.getTitle())
                .description(taskEntity.getDescription())
                .createdBy(taskEntity.getCreatedBy())
                .assignedTo(taskEntity.getAssignedTo())
                .deleted(taskEntity.isDeleted())
                .comments(taskEntity.getComments())
                .status(taskEntity.getStatus())
                .audit(taskEntity.getAudit())
                .build();
    }
}

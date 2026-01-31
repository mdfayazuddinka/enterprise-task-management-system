package com.fayaz.taskmanagement.task.converter;

import com.fayaz.taskmanagement.task.dto.TaskDto;
import com.fayaz.taskmanagement.task.entity.TaskEntity;
import com.fayaz.taskmanagement.task.enums.StatusEnum;

public class TaskEntityToDto {
    public static TaskDto convert(TaskEntity taskEntity) {
        if (taskEntity == null) {
            return null;
        }

        return TaskDto.builder()
                .id(taskEntity.get_id())
                .title(taskEntity.getTitle())
                .taskId(taskEntity.getTaskId())
                .description(taskEntity.getDescription())
                .assignedTo(taskEntity.getAssignedTo())
                .priority(taskEntity.getPriority())
                .dueDate(taskEntity.getDueDate())
                .type(taskEntity.getType())
                .comments(taskEntity.getComments())
                .status(StatusEnum.valueOf(taskEntity.getStatus().toUpperCase()).getStatus())
                .projectId(taskEntity.getProjectId())
                .audit(taskEntity.getAudit())
                .build();

    }
}

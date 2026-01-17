package com.fayaz.taskmanagement.task.converter;

import com.fayaz.taskmanagement.task.dto.SubTaskDto;
import com.fayaz.taskmanagement.task.entity.SubTaskEntity;

public class SubTaskEntityToDto {
    public static SubTaskDto convert(SubTaskEntity entity) {
        if (entity == null) {
            return null;
        }

        SubTaskDto subTaskDto = SubTaskDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .priority(entity.getPriority())
                .dueDate(entity.getDueDate())
                .parentTaskId(entity.getParentTaskId())
                .audit(entity.getAudit())
                .build();

        return subTaskDto;
    }
}

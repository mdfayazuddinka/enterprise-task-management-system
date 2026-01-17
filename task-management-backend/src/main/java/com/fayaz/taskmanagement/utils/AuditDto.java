package com.fayaz.taskmanagement.utils;

import java.util.Date;

import com.fayaz.taskmanagement.auth.entity.OwnerEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditDto {
    private OwnerEntity owner;
    private Date createdDate;
    private Date lastModifiedDate;
    private OwnerEntity lastModifiedBy;
}

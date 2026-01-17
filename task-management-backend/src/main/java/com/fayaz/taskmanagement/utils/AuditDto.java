package com.fayaz.taskmanagement.utils;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.fayaz.taskmanagement.auth.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditDto {
    @CreatedBy
    private UserEntity owner;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date lastModifiedDate;

    @LastModifiedBy
    private UserEntity lastModifiedBy;
}

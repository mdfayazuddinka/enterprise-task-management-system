package com.fayaz.taskmanagement.utils;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection = "counters")
public class CounterEntity {
    @Id
    private String id;

    private long seq;   
}
